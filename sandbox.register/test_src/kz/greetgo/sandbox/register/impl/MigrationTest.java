package kz.greetgo.sandbox.register.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import java.io.File;
import java.io.IOException;
import java.security.InvalidParameterException;
import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
//  public BeanGetter<ClientDao> clientDao;

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
//    assertThat(testAccount.registered_at).isEqualTo(account.registered_at);
  }

  @Test
  public void insertTempTransaction() {
    ClientAccountTransaction transaction = randomEntity.get().createTransaction();
    migrationTestDao.get().insertMigrationTransaction(transaction);
    //
    //
    ClientAccountTransaction testTransaction = migrationTestDao.get()
        .selectTransaction(transaction.accountNumber);
    //
    //
    assertThat(testTransaction.account).isEqualTo(transaction.account);
    assertThat(testTransaction.type).isEqualTo(transaction.type);
    assertThat(testTransaction.money).isEqualTo(transaction.money);
    assertThat(testTransaction.finishedAt).isEqualTo(transaction.finishedAt);

  }

  //2step
  @Test
  public void checkNullSurname() throws IOException, SAXException {
    xmlReader.setContentHandler(handler);
    xmlReader.parse(String.valueOf(new File("test4.xml")));
    File testFile = new File("testLog.log");
    assertThat(testFile.length()).isGreaterThan(0);
  }

  @Test
  public void checkNullName() throws Exception {

    xmlReader.setContentHandler(handler);
    xmlReader.parse(String.valueOf(new File("test1.xml")));
    File testFile = new File("testLog.log");
    assertThat(testFile.length()).isGreaterThan(0);

  }

  @Test
  public void checkCorrectDate() throws IOException, SAXException {
    xmlReader.setContentHandler(handler);
    xmlReader.parse(String.valueOf(new File("test2.xml")));

    File testFile = new File("testLog.log");
    assertThat(testFile.length()).isGreaterThan(0);
  }

  @Test
  public void chechNullciaId() throws IOException, SAXException {
    xmlReader.setContentHandler(handler);
    xmlReader.parse(String.valueOf(new File("test3.xml")));

    File testFile = new File("testLog.log");
    assertThat(testFile.length()).isGreaterThan(0);
  }

  @Test
  public void insertClient() {
    Client client = randomEntity.get().createClientForMigrate();

    migrationTestDao.get().insertMigrationClient(client);

//    inMigrationWorker.migrate();

  }

  @Test
  public void updateClient() {

  }

  @Test
  public void insertAccount() {}

  @Test
  public void updateAccount() {}

}
