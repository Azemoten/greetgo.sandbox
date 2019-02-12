package kz.greetgo.sandbox.register.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.msoffice.docx.Run;
import kz.greetgo.msoffice.docx.RunTab;
import kz.greetgo.sandbox.controller.errors.NullCiaIdException;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientAccount;
import kz.greetgo.sandbox.controller.model.ClientAccountTransaction;
import kz.greetgo.sandbox.controller.model.ClientAddr;
import kz.greetgo.sandbox.controller.model.ClientPhone;
import kz.greetgo.sandbox.register.dao.ClientDao;
import kz.greetgo.sandbox.register.migration.ConnectionToDB;
import kz.greetgo.sandbox.register.migration.InMigration;
import kz.greetgo.sandbox.register.migration.InMigrationWorker;
import kz.greetgo.sandbox.register.migration.XMLHandler;
import kz.greetgo.sandbox.register.test.beans.RandomMigrationEntity;
import kz.greetgo.sandbox.register.test.dao.MigrationDaoTest;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

public class MigrationTest extends ParentTestNg {

  public BeanGetter<MigrationDaoTest> migrationTestDao;
  public BeanGetter<RandomMigrationEntity> randomEntity;
  public BeanGetter<ClientDao> clientDao;

  SAXParserFactory factory = SAXParserFactory.newInstance();
  SAXParser parser = factory.newSAXParser();
  XMLReader xmlReader = parser.getXMLReader();
  XMLHandler handler = new XMLHandler();


  public MigrationTest() throws Exception {}


  //1 step
  @Test
  public void InsertTempClient() {
    migrationTestDao.get().deactualClients();

    Client client = randomEntity.get().createClient();
    migrationTestDao.get().insertMigrationClient(client);

    //
    //
    Client testClient = migrationTestDao.get().selectClient(client.ciaId);
    //
    //
    assertThat(testClient.gender).isEqualTo(client.gender);
    assertThat(testClient.surname).isEqualTo(client.surname);
    assertThat(testClient.patronymic).isEqualTo(client.patronymic);
    assertThat(testClient.birthDate).isEqualTo(client.birthDate);
  }

  @Test
  public void insertTempAddress() {
    migrationTestDao.get().deactualAddrs();

    ClientAddr clientAddr = randomEntity.get().createAddr();
    migrationTestDao.get().insertMigrationAddress(clientAddr);
    //
    //
    ClientAddr testAddr = migrationTestDao.get().selectAddr(clientAddr.ciaId);
    //
    //
    assertThat(testAddr.flat).isEqualTo(clientAddr.flat);
    assertThat(testAddr.street).isEqualTo(clientAddr.street);
    assertThat(testAddr.house).isEqualTo(clientAddr.house);
    assertThat(testAddr.type).isEqualTo(clientAddr.type);
  }

  @Test
  public void insertTempPhone() {
    migrationTestDao.get().deactualPhones();

    ClientPhone clientPhone = randomEntity.get().createPhone();
    migrationTestDao.get().insertMigrationPhone(clientPhone);
    //
    //
    ClientPhone testPhone = migrationTestDao.get().selectPhone(clientPhone.ciaId);
    //
    //
    assertThat(testPhone.number).isEqualTo(clientPhone.number);
    assertThat(testPhone.type).isEqualTo(clientPhone.type);
  }

  @Test
  public void insertTempAccount() {

    ClientAccount account = randomEntity.get().createAccount();
    migrationTestDao.get().insertMigrationAccount(account);
    //
    //
    ClientAccount testAccount = migrationTestDao.get().selectAccount(account.ciaId);
    //
    //
    assertThat(testAccount.money).isEqualTo(account.money);
    assertThat(testAccount.number).isEqualTo(account.number);
  }



  //2step
  @Test
  public void checkNullSurname() throws IOException, SAXException {
    xmlReader.setContentHandler(handler);
    xmlReader.parse(String.valueOf(new File("test4.xml")));

    assertThat(parseFile("testLog.log", "Invalid surname, name, or id")).isNotEqualTo(-1);
  }

  @Test
  public void checkNullName() throws Exception {

    xmlReader.setContentHandler(handler);
    xmlReader.parse(String.valueOf(new File("test1.xml")));
    assertThat(parseFile("testLog.log", "Invalid surname, name, or id")).isNotEqualTo(-1);

  }

  @Test
  public void checkCorrectDate() throws IOException, SAXException {
    xmlReader.setContentHandler(handler);
    xmlReader.parse(String.valueOf(new File("test2.xml")));

    assertThat(parseFile("testLog.log", "DateTimeException")).isNotEqualTo(-1);
  }

  @Test
  public void chechNullciaId() throws IOException, SAXException {
    xmlReader.setContentHandler(handler);
    xmlReader.parse(String.valueOf(new File("test3.xml")));

    assertThat(parseFile("testLog.log", "Invalid surname, name, or id")).isNotEqualTo(-1);
  }

  @Test
  public void insertClient() throws Exception {
    Client client = randomEntity.get().createClientForMigrate();

    migrationTestDao.get().insertMigrationClient(client);
    new InMigration().execute();

    Client client1 = clientDao.get().getClientByCiaId(client.ciaId);

    assertThat(client1.name).isEqualTo(client.name);
    assertThat(client1.patronymic).isEqualTo(client.patronymic);

//    inMigrationWorker.migrate();

  }

  @Test
  public void updateClient() throws Exception {
    Client client = randomEntity.get().createClientForMigrate();

    migrationTestDao.get().insertMigrationClient(client);
    client.surname = "UPDATED";
    client.name = "UPDATED";
    migrationTestDao.get().insertMigrationClient(client);
    new InMigration().execute();

    Client client1 = clientDao.get().getClientByCiaId(client.ciaId);

    assertThat(client1.surname).isEqualTo(client.surname);
    assertThat(client1.name).isEqualTo(client.name);

  }

  @Test
  public void insertAccount() throws Exception {
    Client client = randomEntity.get().createClientForMigrate();
    ClientAccount clientAccount = randomEntity.get().createAccount(client.ciaId);
    migrationTestDao.get().insertMigrationClient(client);
    migrationTestDao.get().insertMigrationAccount(clientAccount);

    new InMigration().execute();

    ClientAccount clientAccount1 = clientDao.get().getAccount(client.ciaId);

    assertThat(clientAccount1.number).isEqualTo(clientAccount.number);
    assertThat(clientAccount1.money).isEqualTo(clientAccount.money);
  }



  public static int parseFile(String fileName,String searchStr)
      throws FileNotFoundException {
    Scanner scan = new Scanner(new File(fileName));
    while(scan.hasNext()){
      String line = scan.nextLine().toLowerCase();
      if(line.toLowerCase().indexOf(searchStr.toLowerCase())!=-1){
        return 1;
      }
    }
    return  -1;
  }
}

