package kz.greetgo.sandbox.register.migration;

import static kz.greetgo.sandbox.register.util.SqlProvider.sqlInsertClient;

import java.security.InvalidParameterException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
  //insert into client_addr(client, type, street, house, flat) values(#{client}, #{type}::address, #{street}, #{house}, #{flat}) \n"
  //          +
  //          "on conflict(client, type) do update set (client, type, street, house, flat ) = (#{client}, #{type}::address, #{street}, #{house}, #{flat})
  // ciaId, name, existSurname, patronymic, gender, birth, charm, status
  Connection conn = new ConnectionToDB().getConnection();
  String sqlInsertCharm = "with s as (\n"
      + "    select id\n"
      + "    from migration_charm\n"
      + "    where name = ?\n"
      + "), i as (\n"
      + "    insert into migration_charm (id, name)\n"
      + "    select nextval('charm_id_seq'), ?\n"
      + "    where not exists (select 1 from s)\n"
      + "    returning id\n"
      + ")\n"
      + "select id\n"
      + "from i\n"
      + "union all\n"
      + "select id\n"
      + "from s\n"
      + "limit 1";
  String sqlInsertPhone = "insert into migration_phone(cia_id, number, type) "
      + "values(?, ?, ?)";
  String sqlInsertAddress = "insert into migration_address(cia_id, type, street, house, flat) values(?, ?, ?, ?, ?)";


  Map<String, AddressType> factOrRegisterType = new HashMap<String, AddressType>() {{
    put("fact", AddressType.FACT);
    put("register", AddressType.REG);
  }};

  public XMLHandler() throws Exception {
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
        checkExistanceClientAndDeactualPhonesAndAddrsIfNeed();
        insertCharmIfDTExist();
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
      }

      clientDetail = new ClientDetail(new Client(), new ArrayList<ClientAddr>(),
                                      new ArrayList<ClientPhone>());
      existSurname = false;
      ciaId = null;
      existClient = false;
      clientStatus = 0;
    }
  }

  private void insertAddress() {
    for (ClientAddr addr : clientDetail.addrs) {
      //ciaId, type, street, house, flat
      try (PreparedStatement ps = conn.prepareStatement(sqlInsertAddress)) {
        ps.setString(1, addr.ciaId);
        ps.setString(2, String.valueOf(addr.type));
        ps.setString(3, addr.street);
        ps.setString(4, addr.house);
        ps.setString(5, addr.flat);
        ps.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private void insertPhone() {
    for (ClientPhone phone : clientDetail.phones) {
      try (PreparedStatement ps = conn.prepareStatement(sqlInsertPhone)) {
        ps.setString(1, phone.ciaId);
        ps.setString(2, phone.number);
        ps.setString(3, String.valueOf(phone.type));
        ps.executeUpdate();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }

  private void checkExistanceClientAndDeactualPhonesAndAddrsIfNeed() {
    try (PreparedStatement ps = conn.prepareStatement(
        "select exists(select 1 from migration_client where cia_id=?)")) {
      ps.setString(1, clientDetail.client.ciaId);
      try (ResultSet rs = ps.executeQuery()) {
        if (rs.next()) {
          existClient = rs.getBoolean("exists");
          if (existClient) {
//            clientStatus = 3;
            deactualData();
          }
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void insertCharmIfDTExist() {
    try {
      try (PreparedStatement ps = conn.prepareStatement(sqlInsertCharm)) {
        ps.setString(1, charm);
        ps.setString(2, charm);
        try (ResultSet rs = ps.executeQuery()) {
          if (rs.next()) {
            clientDetail.client.charm = rs.getInt("id");
          }
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void insertClient() {
    try (PreparedStatement ps = conn
        .prepareStatement(sqlInsertClient(clientDetail.client.patronymic))) {
      //ciaId, name, existSurname, gender, birth, charm"
      ps.setString(1, clientDetail.client.ciaId);
      ps.setString(2, clientDetail.client.name);
      ps.setString(3, clientDetail.client.surname);
      ps.setString(4, String.valueOf(clientDetail.client.gender));
      ps.setString(5, clientDetail.client.birthDate.toString());
      ps.setInt(6, clientDetail.client.charm);
      ps.setInt(7, clientStatus);
      if (Objects.nonNull(clientDetail.client.patronymic)) {
        ps.setString(8, clientDetail.client.patronymic);
      }
      ps.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
      System.out.println("ID CLIENT " + ciaId);
    }
  }

  private void deactualData() throws SQLException {
    deactualPhones();
    deactualAddrs();
  }

  private void deactualAddrs() throws SQLException {
    try (PreparedStatement ps = conn
        .prepareStatement("update migration_address set actual=0\n where cia_id=?")) {
      ps.setString(1, ciaId);
      ps.executeUpdate();
    }
  }

  private void deactualPhones() throws SQLException {
    try (PreparedStatement ps = conn
        .prepareStatement("update migration_phone set actual=0\n where cia_id=?")) {
      ps.setString(1, ciaId);
      ps.executeUpdate();
    }
  }


  @Override
  public void endDocument() throws SAXException {
    System.out.println(clientDetail.client.ciaId);
    try {
      conn.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}