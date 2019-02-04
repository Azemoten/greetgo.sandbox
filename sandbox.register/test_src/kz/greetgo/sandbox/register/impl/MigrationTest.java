package kz.greetgo.sandbox.register.impl;

import static org.fest.assertions.api.Assertions.assertThat;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientAccount;
import kz.greetgo.sandbox.controller.model.ClientAccountTransaction;
import kz.greetgo.sandbox.controller.model.ClientAddr;
import kz.greetgo.sandbox.controller.model.ClientPhone;
import kz.greetgo.sandbox.register.test.beans.RandomMigrationEntity;
import kz.greetgo.sandbox.register.test.dao.MigrationDaoTest;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

public class MigrationTest extends ParentTestNg {

  public BeanGetter<MigrationDaoTest> migrationTestDao;
  public BeanGetter<RandomMigrationEntity> randomEntity;

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
    ClientAccountTransaction testTransaction = migrationTestDao.get().selectTransaction(transaction.accountNumber);
    //
    //
    assertThat(testTransaction.account).isEqualTo(transaction.account);
    assertThat(testTransaction.type).isEqualTo(transaction.type);
    assertThat(testTransaction.money).isEqualTo(transaction.money);
    assertThat(testTransaction.finishedAt).isEqualTo(transaction.finishedAt);

  }

  //2step
  @Test
  public void checkNullSurname(){

  }




}
