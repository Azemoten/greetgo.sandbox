package kz.greetgo.sandbox.register.migration;

import static kz.greetgo.sandbox.register.util.SqlProvider.sqlUpsertClient;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import kz.greetgo.sandbox.controller.model.AddressType;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientAddr;
import kz.greetgo.sandbox.controller.model.ClientDetail;
import kz.greetgo.sandbox.controller.model.ClientPhone;
import kz.greetgo.sandbox.controller.model.Gender;
import kz.greetgo.sandbox.controller.model.PhoneType;
//import org.json.XML;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

public class XmlReader {

  static int count = 0;
  static int all = 0;

  public static void main(String[] args)
      throws Exception {
    SAXParserFactory factory = SAXParserFactory.newInstance();
    SAXParser parser = factory.newSAXParser();
    XMLReader xmlReader = parser.getXMLReader();

    XMLHandler handler = new XMLHandler();
    xmlReader.setContentHandler(handler);
    xmlReader.parse(String.valueOf(new File("exam2.xml")));


  }

  private static class XMLHandler extends DefaultHandler {

    ClientDetail clientDetail = new ClientDetail(new Client(), new ArrayList<ClientAddr>(),
                                                 new ArrayList<ClientPhone>());


    String ciaId = null;
    String charm = null;
    boolean existClient = false;
    boolean workPhone = false;
    boolean homePhone = false;
    boolean mobilePhone = false;
    boolean invalidVal = false;
    //insert into client_addr(client, type, street, house, flat) values(#{client}, #{type}::address, #{street}, #{house}, #{flat}) \n"
    //          +
    //          "on conflict(client, type) do update set (client, type, street, house, flat ) = (#{client}, #{type}::address, #{street}, #{house}, #{flat})
    // ciaId, name, surname, patronymic, gender, birth, charm, status
    Connection conn = new ConnectionToDB().getConnection();
    String sqlInsertCharm = "with s as (\n"
        + "    select id\n"
        + "    from migration_charm\n"
        + "    where name = 'WWW'\n"
        + "), i as (\n"
        + "    insert into migration_charm (id, name)\n"
        + "    select nextval('charm_id_seq'), 'WWW'\n"
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

    private XMLHandler() throws Exception {}

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes)
        throws SAXException {
      if ("client".equals(qName)) {
        ciaId = attributes.getValue("id");
        clientDetail.client.ciaId = ciaId;
      } else if ("name".equals(qName)) {
        clientDetail.client.name = attributes.getValue("value");
      } else if ("surname".equals(qName)) {
        clientDetail.client.surname = attributes.getValue("value");
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
        } catch (ParseException e) {
          invalidVal = true;
          e.printStackTrace();
        }


      } else if ("fact".equals(qName) || "register".equals(qName)) {
        ClientAddr addr = new ClientAddr();
        addr.street = attributes.getValue("street");
        addr.house = attributes.getValue("house");
        addr.flat = attributes.getValue("flat");
        addr.type = factOrRegisterType.get(qName);
        addr.cia_id = ciaId;
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
        all++;
        if (!invalidVal) {
          count++;

//          insertCharm();
//
//          upsertClient();
//
//          checkExistanceClient();
//
//
//          insertPhone(clientDetail.phones);
//
//          insertAddress(clientDetail.addrs);

        } else {
          invalidVal = false;
        }

        clientDetail = new ClientDetail(new Client(), new ArrayList<ClientAddr>(),
                                        new ArrayList<ClientPhone>());
        ciaId = null;
        existClient = false;
      }
    }

    private void insertAddress(List<ClientAddr> addrs) {
      for (ClientAddr addr : clientDetail.addrs) {
        //cia_id, type, street, house, flat
        try (PreparedStatement ps = conn.prepareStatement(sqlInsertAddress)) {
          ps.setString(1, addr.cia_id);
          ps.setString(2, String.valueOf(addr.type));
          ps.setString(3, addr.house);
          ps.setString(4, addr.flat);
          ps.executeUpdate();
        } catch (SQLException e) {
          e.printStackTrace();
        }
      }
    }

    private void insertPhone(List<ClientPhone> phones) {
      for (ClientPhone phone : phones) {
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

    private void checkExistanceClient() {
      try (PreparedStatement ps = conn.prepareStatement(
          "select exists(select 1 from migration_client where cia_id=?)")) {
        ps.setString(1, clientDetail.client.ciaId);
        try (ResultSet rs = ps.executeQuery()) {
          if (rs.next()) {
            existClient = rs.getBoolean("exists");
            if (existClient) {
              deactualData();
            }
          }
        }
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    private void insertCharm() {
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

    private void upsertClient() {
      try (PreparedStatement ps = conn
          .prepareStatement(sqlUpsertClient(clientDetail.client.patronymic))) {
        //cia_id, name, surname, gender, birth, charm"
        ps.setString(1, clientDetail.client.ciaId);
        ps.setString(2, clientDetail.client.name);
        ps.setString(3, clientDetail.client.surname);
        ps.setString(4, String.valueOf(clientDetail.client.gender));
        ps.setString(5, clientDetail.client.birthDate.toString());
        ps.setInt(6, clientDetail.client.charm);
        if (Objects.nonNull(clientDetail.client.patronymic)) {
          ps.setString(7, clientDetail.client.patronymic);
        }
        ps.executeUpdate();

      } catch (SQLException e) {
        e.printStackTrace();
      }
    }

    private void deactualData() throws SQLException {
      deactualPhones();
      deactualAddrs();
    }

    private void deactualAddrs() throws SQLException {
      try (PreparedStatement ps = conn
          .prepareStatement("update migration_address set actual=0\n")) {
        ps.executeUpdate();
      }
    }

    private void deactualPhones() throws SQLException {
      try (PreparedStatement ps = conn
          .prepareStatement("update migration_phone set actual=0\n")) {
        ps.executeUpdate();
      }
    }

    @Override
    public void endDocument() throws SAXException {
      System.out.println("CHECK last moves " + count);
      System.out.println("ALL, " + all);
      System.out.println(clientDetail.client.ciaId);
      try {
        conn.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
  }


}
