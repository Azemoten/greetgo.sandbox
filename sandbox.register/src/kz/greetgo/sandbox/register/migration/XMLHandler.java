package kz.greetgo.sandbox.register.migration;

import static kz.greetgo.sandbox.register.util.SqlProvider.sqlInsertClient;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import kz.greetgo.sandbox.controller.model.AddressType;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientAddr;
import kz.greetgo.sandbox.controller.model.ClientDetail;
import kz.greetgo.sandbox.controller.model.ClientPhone;
import kz.greetgo.sandbox.controller.model.Gender;
import kz.greetgo.sandbox.controller.model.PhoneType;
import kz.greetgo.sandbox.register.util.SQL;
import org.apache.ibatis.javassist.bytecode.stackmap.TypeData.ClassName;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class XMLHandler extends DefaultHandler {

  ClientDetail clientDetail = new ClientDetail(new Client(), new ArrayList<ClientAddr>(),
                                               new ArrayList<ClientPhone>());

  private static final Logger logger = Logger.getLogger(ClassName.class.getName());
  FileHandler fh;
  FileHandler realLog;


  String ciaId = null;
  String charm = null;
  boolean existClient = false;
  boolean workPhone = false;
  boolean homePhone = false;
  boolean existSurname = false;
  boolean mobilePhone = false;
  boolean invalidVal = false;
  boolean invalidAge = false;
  int clientStatus;
  int currentSize = 0;
  int batchSize = 10000;
  Connection conn = new ConnectionToDB().getConnection();

  Statement stmt = conn.createStatement();

  // ciaId, name, existSurname, patronymic, gender, birth, charm, status
  //          "on conflict(client, type) do update set (client, type, street, house, flat ) = (#{client}, #{type}::address, #{street}, #{house}, #{flat})
  //          +
  //insert into client_addr(client, type, street, house, flat) values(#{client}, #{type}::address, #{street}, #{house}, #{flat}) \n"

//  String sqlInsertPhone = "insert into migration_phone(cia_id, number, type) "
//      + "values(?, ?, ?)";
//  String sqlInsertAddress = "insert into migration_address(cia_id, type, street, house, flat) values(?, ?, ?, ?, ?)";


  Map<String, AddressType> factOrRegisterType = new HashMap<String, AddressType>() {{
    put("fact", AddressType.FACT);
    put("register", AddressType.REG);
  }};

  public XMLHandler() throws Exception {

    conn.setAutoCommit(false);

    fh = new FileHandler("testLog.log", false);
    realLog = new FileHandler("logs.log", true);
    logger.addHandler(fh);
    logger.addHandler(realLog);
    SimpleFormatter formatter = new SimpleFormatter();
    fh.setFormatter(formatter);
    realLog.setFormatter(formatter);
  }

  @Override
  public void startElement(String uri, String localName, String qName, Attributes attributes)
      throws SAXException {
    if ("client".equals(qName)) {
      if (Objects.nonNull(attributes.getValue("id"))) {
        if (attributes.getValue("id").equals("")) {
          invalidVal = true;
        }
        ciaId = attributes.getValue("id");
        clientDetail.client.ciaId = ciaId;
      } else {
        invalidVal = true;
      }
    } else if ("name".equals(qName)) {
      if (Objects.nonNull(attributes.getValue("value"))) {
        if (attributes.getValue("value").equals("")) {
          invalidVal = true;
        }
        clientDetail.client.name = attributes.getValue("value");
      } else {
        invalidVal = true;
      }
    } else if ("surname".equals(qName)) {
      if (Objects.nonNull(attributes.getValue("value"))) {
        if (attributes.getValue("value").equals("")) {
          invalidVal = true;
        }
        clientDetail.client.surname = attributes.getValue("value");
      } else {
        invalidVal = true;
      }
      existSurname = true;
    } else if ("patronymic".equals(qName)) {
      clientDetail.client.patronymic = attributes.getValue("value");
    } else if ("gender".equals(qName)) {
      clientDetail.client.gender = Gender.valueOf(attributes.getValue("value"));
    } else if ("charm".equals(qName)) {
      charm = attributes.getValue("value");
    } else if ("birth".equals(qName)) {

      try {
        clientDetail.client.birthDate = new SimpleDateFormat("yyyy-mm-dd")
            .parse(attributes.getValue("value"));
        LocalDate forAge = clientDetail.client.birthDate.toInstant().atZone(ZoneId.systemDefault())
            .toLocalDate();
        LocalDate now = LocalDate.now();
        int a = Period.between(forAge, now).getYears();
        if (a < 3 || a > 1000) {
          throw new DateTimeException("");
        }
      } catch (DateTimeException | ParseException e) {
        invalidAge = true;
      }


    } else if ("fact".equals(qName) || "register".equals(qName)) {
      ClientAddr addr = new ClientAddr();
      addr.street = attributes.getValue("street");
      addr.house = attributes.getValue("house");
      addr.flat = attributes.getValue("flat");
      addr.type = factOrRegisterType.get(qName);
      addr.ciaId = ciaId;
      clientDetail.addrs.add(addr);
    } else if ("workPhone".equalsIgnoreCase(qName)) {
      workPhone = true;
    } else if ("homePhone".equalsIgnoreCase(qName)) {
      homePhone = true;
    } else if ("mobilePhone".equalsIgnoreCase(qName)) {
      mobilePhone = true;
    }

  }


  @Override
  public void characters(char[] ch, int start, int length) throws SAXException {
    if (workPhone) {
      clientDetail.phones
          .add(new ClientPhone(new String(ch, start, length), PhoneType.WORK, ciaId));
      workPhone = false;
    }
    if (homePhone) {
      clientDetail.phones
          .add(new ClientPhone(new String(ch, start, length), PhoneType.HOME, ciaId));
      homePhone = false;
    }
    if (mobilePhone) {
      clientDetail.phones
          .add(new ClientPhone(new String(ch, start, length), PhoneType.MOBILE, ciaId));
      mobilePhone = false;
    }
  }

  @Override
  public void endElement(String uri, String localName, String qName) throws SAXException {
    if (qName.equalsIgnoreCase("client")) {
      System.out.println("End CLIENT ...");
      try {
        if (invalidVal || !existSurname) {
          throw new InvalidParameterException("Invalid surname, name, or id");
        }
        if (invalidAge) {
          throw new DateTimeException("Errors");
        }
        deactualData();
        insertClient();
        insertAddress();
        insertPhone();

        System.out.println("Ok...");

      } catch (InvalidParameterException | DateTimeException e) {
        invalidVal = false;
        invalidAge = false;
        if (ciaId != null) {
          logger.info("Id of user: " + ciaId);
        }
        logger.info(e + "");
      } catch (SQLException e) {
        e.printStackTrace();
      }

      clientDetail = new ClientDetail(new Client(), new ArrayList<ClientAddr>(),
                                      new ArrayList<ClientPhone>());
      existSurname = false;
      ciaId = null;
      existClient = false;
      clientStatus = 0;

    }
  }

  private void insertAddress() throws SQLException {
    for (ClientAddr addr : clientDetail.addrs) {
      //ciaId, type, street, house, flat
      SQL sql = new SQL();
      sql.insert_into("migration_address");
      sql.values("cia_id", "'" + addr.ciaId + "'");
      sql.values("type", "'" + String.valueOf(addr.type) + "'");
      sql.values("street", "'" + addr.street + "'");
      sql.values("house", "'" + addr.house + "'");
      sql.values("flat", "'" + addr.flat + "'");
      stmt.addBatch(sql.toString());
      executeBatch();
    }
  }

  private void insertPhone() throws SQLException {
    for (ClientPhone phone : clientDetail.phones) {
      SQL sql = new SQL();
      sql.insert_into("migration_phone");
      sql.values("cia_id", "'" + phone.ciaId + "'");
      sql.values("number", "'" + phone.number + "'");
      sql.values("type", "'" + String.valueOf(phone.type) + "'");
      stmt.addBatch(sql.toString());
      executeBatch();
    }
  }

  private void executeBatch() throws SQLException {
    currentSize++;
    if (currentSize == batchSize) {
      stmt.executeBatch();
      currentSize = 0;
    }
  }


  private void insertClient() throws SQLException {
    SQL sql = new SQL();
    sql.insert_into("migration_client");
    sql.values("cia_id", "'" + clientDetail.client.ciaId + "'");
    sql.values("name", "'" + clientDetail.client.name + "'");
    sql.values("surname", "'" + clientDetail.client.surname + "'");
    sql.values("gender", "'" + String.valueOf(clientDetail.client.gender) + "'");
    sql.values("birth", "'" + clientDetail.client.birthDate.toString() + "'");
    sql.values("charm_text", "'" + charm + "'");
    if (Objects.nonNull(clientDetail.client.patronymic)) {
      sql.values("patronymic", "'" + clientDetail.client.patronymic + "'");
    }
    stmt.addBatch(sql.toString());
    executeBatch();
  }

  private void deactualData() throws SQLException {
    deactualPhones();
    deactualAddrs();
  }

  private void deactualAddrs() throws SQLException {
    StringBuilder sb = new StringBuilder();
    sb.append("update migration_address set actual=0\n where cia_id='");
    sb.append(ciaId);
    sb.append("';");
    stmt.addBatch(sb.toString());
    executeBatch();

  }

  private void deactualPhones() throws SQLException {
    StringBuilder sb = new StringBuilder();
    sb.append("update migration_phone set actual=0\n where cia_id='");
    sb.append(ciaId);
    sb.append("';");
    stmt.addBatch(sb.toString());
    executeBatch();
  }


  @Override
  public void endDocument() throws SAXException {
    System.out.println(clientDetail.client.ciaId);
    try {
      stmt.executeBatch();
      conn.commit();
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}