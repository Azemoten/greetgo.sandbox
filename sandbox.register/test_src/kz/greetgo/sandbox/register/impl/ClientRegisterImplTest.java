package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.test.beans.RandomEntity;
import kz.greetgo.sandbox.register.test.dao.ClientDaoTest;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;


import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {

  public BeanGetter<ClientDaoTest> clientTestDao;
  public BeanGetter<ClientRegister> clientRegister;
  public BeanGetter<RandomEntity> randomEntity;


  @Test
  public void numberOfPageAfterFiltering() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client = randomEntity.get().client(charm);
    Client client2 = randomEntity.get().client(charm);
    Client client3 = randomEntity.get().client(charm);
    Client client4 = randomEntity.get().client(charm);
    Client client5 = randomEntity.get().client(charm);
    Client client6 = randomEntity.get().client(charm);
    ClientFilter clientFilter1 = new ClientFilter();
    ClientFilter clientFilter2 = new ClientFilter();
    clientFilter1.name = client.name;
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client);
    clientTestDao.get().insertClient(client2);
    clientTestDao.get().insertClient(client3);
    clientTestDao.get().insertClient(client4);
    clientTestDao.get().insertClient(client5);
    clientTestDao.get().insertClient(client6);
    //
    //
    int pageBeforeSorting = clientRegister.get().numPage(clientFilter1);
    int pageAfter = clientRegister.get().numPage(clientFilter2);
    //
    //

    assertThat(pageBeforeSorting).isNotEqualTo(pageAfter);
  }

  @Test
  public void nextPageAfterFirst() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client = randomEntity.get().client(charm);
    Client client2 = randomEntity.get().client(charm);
    Client client3 = randomEntity.get().client(charm);
    Client client4 = randomEntity.get().client(charm);
    Client client5 = randomEntity.get().client(charm);
    Client client6 = randomEntity.get().client(charm);
    Client client7 = randomEntity.get().client(charm);
    Client client8 = randomEntity.get().client(charm);
    Client client9 = randomEntity.get().client(charm);
    Client client10 = randomEntity.get().client(charm);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.page = 1;
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client);
    clientTestDao.get().insertClient(client2);
    clientTestDao.get().insertClient(client3);
    clientTestDao.get().insertClient(client4);
    clientTestDao.get().insertClient(client5);
    clientTestDao.get().insertClient(client6);
    clientTestDao.get().insertClient(client7);
    clientTestDao.get().insertClient(client8);
    clientTestDao.get().insertClient(client9);
    clientTestDao.get().insertClient(client10);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list.size()).isEqualTo(5);

  }


  @Test
  public void lastPage_size() {
    clientTestDao.get().deactual();
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.page = clientRegister.get().numPage(clientFilter);
    int numberOfClients = clientTestDao.get().getNumOfClients();
    int numberOfLastPage = randomEntity.get().numOfLastPage(numberOfClients);
    //
    //

    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list.size()).isEqualTo(numberOfLastPage);
  }

  @Test
  public void nextAfterLastPage() {
    clientTestDao.get().deactual();

    ClientFilter clientFilter = new ClientFilter();
    clientFilter.page = clientRegister.get().numPage(clientFilter) + 1;
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list).isEqualTo(Arrays.asList());
  }

  @Test
  public void update_insert_Client() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client = randomEntity.get().client(charm);
    ClientPhone clientPhone = new ClientPhone();
    clientPhone.client = client.id;
    clientPhone.type = PhoneType.HOME;
    clientPhone.number = "4444444";
    ClientAddr clientAddr = new ClientAddr();
    clientAddr.client = client.id;
    clientAddr.type = AddressType.FACT;
    clientAddr.flat = "asdasd";
    clientAddr.street = "street";
    clientAddr.house = "asqqqqq";
    //
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client);
    clientTestDao.get().insertPhone(clientPhone);
    clientTestDao.get().insertAddress(clientAddr);
    //
    //
    clientPhone.number = "5555555";
    clientAddr.flat = "Changed";
    client.surname = "ChangedName";
    ClientDetail clientDetail = new ClientDetail();
    clientDetail.phones = Arrays.asList(clientPhone);
    clientDetail.addrs = Arrays.asList(clientAddr);
    clientDetail.client = client;
    //
    //
    clientRegister.get().update(clientDetail);
    //
    //
    ClientDetail getClientDetail = clientRegister.get().clientDetails(client.id);

    assertThat(getClientDetail.client.id).isEqualTo(client.id);
    assertThat(getClientDetail.phones.get(0).number).isEqualTo(clientPhone.number);
    assertThat(getClientDetail.addrs.get(0).flat).isEqualTo(clientAddr.flat);
  }

  @Test
  public void deleteClient() {
    Charm charm = randomEntity.get().charm();
    Client client = randomEntity.get().client(charm);
    //
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client);
    //
    //
    clientRegister.get().remove(client.id);
    boolean deleted = clientTestDao.get().checkDel(client.id);
    //
    //
    assertThat(deleted).isEqualTo(true);

  }

  @Test
  public void list_size_filterByName() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client = randomEntity.get().client(charm);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.name = client.name;
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list).isNotNull();
    assertThat(list).hasSize(1);
    assertThat(list.get(0).id).isEqualTo(client.id);
  }


  @Test
  public void filterBySurname() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client = randomEntity.get().client(charm);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.surname = client.surname;
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //

    assertThat(list).isNotNull();
    assertThat(list).hasSize(1);
    assertThat(list.get(0).id).isEqualTo(client.id);
    //
    //
    //
    clientRegister.get().remove(client.id);

  }

  @Test
  public void filterByPatronymic() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client = randomEntity.get().client(charm);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.patronymic = client.patronymic;
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //

    assertThat(list).isNotNull();
    assertThat(list).hasSize(1);
    assertThat(list.get(0).id).isEqualTo(client.id);
  }

  @Test
  public void sortByNameAsc() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client1 = randomEntity.get().client("AAAAAA", charm);
    Client client2 = randomEntity.get().client("AAAAAD", charm);
    Client client3 = randomEntity.get().client("AAAAAC", charm);
    Client client4 = randomEntity.get().client("AAAAAB", charm);
    Client client5 = randomEntity.get().client("AAAAAE", charm);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.sort = "name";
    clientFilter.order = false;
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client1);
    clientTestDao.get().insertClient(client2);
    clientTestDao.get().insertClient(client3);
    clientTestDao.get().insertClient(client4);
    clientTestDao.get().insertClient(client5);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list.get(0).name).isEqualTo(client1.name);
    assertThat(list.get(1).name).isEqualTo(client4.name);
    assertThat(list.get(2).name).isEqualTo(client3.name);
    assertThat(list.get(3).name).isEqualTo(client2.name);
    assertThat(list.get(4).name).isEqualTo(client5.name);
  }

  @Test
  public void sortByNameDesc() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client1 = randomEntity.get().client("ZZZZZZ", charm);
    Client client2 = randomEntity.get().client("ZZZZZW", charm);
    Client client3 = randomEntity.get().client("ZZZZZX", charm);
    Client client4 = randomEntity.get().client("ZZZZZY", charm);
    Client client5 = randomEntity.get().client("ZZZZZV", charm);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.sort = "name";
    clientFilter.order = true;
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client1);
    clientTestDao.get().insertClient(client2);
    clientTestDao.get().insertClient(client3);
    clientTestDao.get().insertClient(client4);
    clientTestDao.get().insertClient(client5);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list.get(0).name).isEqualTo(client1.name);
    assertThat(list.get(1).name).isEqualTo(client4.name);
    assertThat(list.get(2).name).isEqualTo(client3.name);
    assertThat(list.get(3).name).isEqualTo(client2.name);
    assertThat(list.get(4).name).isEqualTo(client5.name);
  }

  @Test
  public void sortByCommonMoneyDesc() {
    clientTestDao.get().deactual(); ;

    Charm charm = randomEntity.get().charm();
    Client client1 = randomEntity.get().client(charm);
    Client client2 = randomEntity.get().client(charm);
    Client client3 = randomEntity.get().client(charm);
    Client client4 = randomEntity.get().client(charm);
    Client client5 = randomEntity.get().client(charm);
    ClientAccount clientAccount1 = randomEntity.get().account(client1, 18000000.0);
    ClientAccount clientAccount2 = randomEntity.get().account(client2, 15000000.0);
    ClientAccount clientAccount3 = randomEntity.get().account(client3, 17000000.0);
    ClientAccount clientAccount4 = randomEntity.get().account(client4, 10000000.0);
    ClientAccount clientAccount5 = randomEntity.get().account(client5, 20000000.0);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.sort = "commonMoney";
    clientFilter.order = true;
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client1);
    clientTestDao.get().insertClient(client2);
    clientTestDao.get().insertClient(client3);
    clientTestDao.get().insertClient(client4);
    clientTestDao.get().insertClient(client5);
    clientTestDao.get().insertAccount(clientAccount1);
    clientTestDao.get().insertAccount(clientAccount2);
    clientTestDao.get().insertAccount(clientAccount3);
    clientTestDao.get().insertAccount(clientAccount4);
    clientTestDao.get().insertAccount(clientAccount5);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list.get(0).commonMoney).isEqualTo(clientAccount5.money);
    assertThat(list.get(1).commonMoney).isEqualTo(clientAccount1.money);
    assertThat(list.get(2).commonMoney).isEqualTo(clientAccount3.money);
    assertThat(list.get(3).commonMoney).isEqualTo(clientAccount2.money);
    assertThat(list.get(4).commonMoney).isEqualTo(clientAccount4.money);
  }

  @Test
  public void sortByCommonMoneyAsc() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client1 = randomEntity.get().client(charm);
    Client client2 = randomEntity.get().client(charm);
    Client client3 = randomEntity.get().client(charm);
    Client client4 = randomEntity.get().client(charm);
    Client client5 = randomEntity.get().client(charm);
    ClientAccount clientAccount1 = randomEntity.get().account(client1, -400.0);
    ClientAccount clientAccount2 = randomEntity.get().account(client2, -450.0);
    ClientAccount clientAccount3 = randomEntity.get().account(client3, -600.0);
    ClientAccount clientAccount4 = randomEntity.get().account(client4, -500.0);
    ClientAccount clientAccount5 = randomEntity.get().account(client5, -550.0);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.sort = "commonMoney";
    clientFilter.order = false;
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client1);
    clientTestDao.get().insertClient(client2);
    clientTestDao.get().insertClient(client3);
    clientTestDao.get().insertClient(client4);
    clientTestDao.get().insertClient(client5);
    clientTestDao.get().insertAccount(clientAccount1);
    clientTestDao.get().insertAccount(clientAccount2);
    clientTestDao.get().insertAccount(clientAccount3);
    clientTestDao.get().insertAccount(clientAccount4);
    clientTestDao.get().insertAccount(clientAccount5);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list.get(0).commonMoney).isEqualTo(clientAccount3.money);
    assertThat(list.get(1).commonMoney).isEqualTo(clientAccount5.money);
    assertThat(list.get(2).commonMoney).isEqualTo(clientAccount4.money);
    assertThat(list.get(3).commonMoney).isEqualTo(clientAccount2.money);
    assertThat(list.get(4).commonMoney).isEqualTo(clientAccount1.money);
  }

  @Test
  public void sortByAgeAsc() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    GregorianCalendar d1 = new GregorianCalendar(2350, 12, 12);
    GregorianCalendar d2 = new GregorianCalendar(2400, 12, 12);
    GregorianCalendar d3 = new GregorianCalendar(2500, 12, 12);
    GregorianCalendar d4 = new GregorianCalendar(2450, 12, 12);
    GregorianCalendar d5 = new GregorianCalendar(2300, 12, 12);
    Client client1 = randomEntity.get().client(d1, charm);
    Client client2 = randomEntity.get().client(d2, charm);
    Client client3 = randomEntity.get().client(d3, charm);
    Client client4 = randomEntity.get().client(d4, charm);
    Client client5 = randomEntity.get().client(d5, charm);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.sort = "age";
    clientFilter.order = false;
    int currYear = randomEntity.get().CurrYear();
    //
    //
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client1);
    clientTestDao.get().insertClient(client2);
    clientTestDao.get().insertClient(client3);
    clientTestDao.get().insertClient(client4);
    clientTestDao.get().insertClient(client5);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list.get(0).age).isEqualTo(currYear - 2500);
    assertThat(list.get(1).age).isEqualTo(currYear - 2450);
    assertThat(list.get(2).age).isEqualTo(currYear - 2400);
    assertThat(list.get(3).age).isEqualTo(currYear - 2350);
    assertThat(list.get(4).age).isEqualTo(currYear - 2300);
  }

  @Test
  public void sortByAgeDesc() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    GregorianCalendar d1 = new GregorianCalendar(1000, 12, 12);
    GregorianCalendar d2 = new GregorianCalendar(1100, 12, 12);
    GregorianCalendar d3 = new GregorianCalendar(1050, 12, 12);
    GregorianCalendar d4 = new GregorianCalendar(1150, 12, 12);
    GregorianCalendar d5 = new GregorianCalendar(1200, 12, 12);
    Client client1 = randomEntity.get().client(d1, charm);
    Client client2 = randomEntity.get().client(d2, charm);
    Client client3 = randomEntity.get().client(d3, charm);
    Client client4 = randomEntity.get().client(d4, charm);
    Client client5 = randomEntity.get().client(d5, charm);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.sort = "age";
    clientFilter.order = true;
    int currYear = randomEntity.get().CurrYear();
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client1);
    clientTestDao.get().insertClient(client2);
    clientTestDao.get().insertClient(client3);
    clientTestDao.get().insertClient(client4);
    clientTestDao.get().insertClient(client5);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list.get(0).age).isEqualTo(currYear - 1000);
    assertThat(list.get(1).age).isEqualTo(currYear - 1050);
    assertThat(list.get(2).age).isEqualTo(currYear - 1100);
    assertThat(list.get(3).age).isEqualTo(currYear - 1150);
    assertThat(list.get(4).age).isEqualTo(currYear - 1200);
  }


  @Test
  public void sortByMaxMoneyAsc() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client1 = randomEntity.get().client(charm);
    Client client2 = randomEntity.get().client(charm);
    Client client3 = randomEntity.get().client(charm);
    Client client4 = randomEntity.get().client(charm);
    Client client5 = randomEntity.get().client(charm);
    ClientAccount clientAccount1 = randomEntity.get().account(client1, -400.0);
    ClientAccount clientAccount2 = randomEntity.get().account(client2, -450.0);
    ClientAccount clientAccount3 = randomEntity.get().account(client3, -600.0);
    ClientAccount clientAccount4 = randomEntity.get().account(client4, -500.0);
    ClientAccount clientAccount5 = randomEntity.get().account(client5, -550.0);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.sort = "maxMoney";
    clientFilter.order = false;
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client1);
    clientTestDao.get().insertClient(client2);
    clientTestDao.get().insertClient(client3);
    clientTestDao.get().insertClient(client4);
    clientTestDao.get().insertClient(client5);
    clientTestDao.get().insertAccount(clientAccount1);
    clientTestDao.get().insertAccount(clientAccount2);
    clientTestDao.get().insertAccount(clientAccount3);
    clientTestDao.get().insertAccount(clientAccount4);
    clientTestDao.get().insertAccount(clientAccount5);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list.get(0).commonMoney).isEqualTo(clientAccount3.money);
    assertThat(list.get(1).commonMoney).isEqualTo(clientAccount5.money);
    assertThat(list.get(2).commonMoney).isEqualTo(clientAccount4.money);
    assertThat(list.get(3).commonMoney).isEqualTo(clientAccount2.money);
    assertThat(list.get(4).commonMoney).isEqualTo(clientAccount1.money);
  }

  @Test
  public void sortByMaxMoneyDesc() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client1 = randomEntity.get().client(charm);
    Client client2 = randomEntity.get().client(charm);
    Client client3 = randomEntity.get().client(charm);
    Client client4 = randomEntity.get().client(charm);
    Client client5 = randomEntity.get().client(charm);
    ClientAccount clientAccount1 = randomEntity.get().account(client1, 10000000.0);
    ClientAccount clientAccount2 = randomEntity.get().account(client2, 11000000.0);
    ClientAccount clientAccount3 = randomEntity.get().account(client3, 12000000.0);
    ClientAccount clientAccount4 = randomEntity.get().account(client4, 15000000.0);
    ClientAccount clientAccount5 = randomEntity.get().account(client5, 13300000.0);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.sort = "maxMoney";
    clientFilter.order = true;
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client1);
    clientTestDao.get().insertClient(client2);
    clientTestDao.get().insertClient(client3);
    clientTestDao.get().insertClient(client4);
    clientTestDao.get().insertClient(client5);
    clientTestDao.get().insertAccount(clientAccount1);
    clientTestDao.get().insertAccount(clientAccount2);
    clientTestDao.get().insertAccount(clientAccount3);
    clientTestDao.get().insertAccount(clientAccount4);
    clientTestDao.get().insertAccount(clientAccount5);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    //
    assertThat(list.get(0).commonMoney).isEqualTo(clientAccount4.money);
    assertThat(list.get(1).commonMoney).isEqualTo(clientAccount5.money);
    assertThat(list.get(2).commonMoney).isEqualTo(clientAccount3.money);
    assertThat(list.get(3).commonMoney).isEqualTo(clientAccount2.money);
    assertThat(list.get(4).commonMoney).isEqualTo(clientAccount1.money);
  }

  @Test
  public void sortByMinMoneyAsc() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client1 = randomEntity.get().client(charm);
    Client client2 = randomEntity.get().client(charm);
    Client client3 = randomEntity.get().client(charm);
    Client client4 = randomEntity.get().client(charm);
    Client client5 = randomEntity.get().client(charm);
    ClientAccount clientAccount1 = randomEntity.get().account(client1, -600.0);
    ClientAccount clientAccount2 = randomEntity.get().account(client2, -500.0);
    ClientAccount clientAccount3 = randomEntity.get().account(client3, -450.0);
    ClientAccount clientAccount4 = randomEntity.get().account(client4, -550.0);
    ClientAccount clientAccount5 = randomEntity.get().account(client5, -650.0);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.sort = "minMoney";
    clientFilter.order = false;
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client1);
    clientTestDao.get().insertClient(client2);
    clientTestDao.get().insertClient(client3);
    clientTestDao.get().insertClient(client4);
    clientTestDao.get().insertClient(client5);
    clientTestDao.get().insertAccount(clientAccount1);
    clientTestDao.get().insertAccount(clientAccount2);
    clientTestDao.get().insertAccount(clientAccount3);
    clientTestDao.get().insertAccount(clientAccount4);
    clientTestDao.get().insertAccount(clientAccount5);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list.get(0).commonMoney).isEqualTo(clientAccount5.money);
    assertThat(list.get(1).commonMoney).isEqualTo(clientAccount1.money);
    assertThat(list.get(2).commonMoney).isEqualTo(clientAccount4.money);
    assertThat(list.get(3).commonMoney).isEqualTo(clientAccount2.money);
    assertThat(list.get(4).commonMoney).isEqualTo(clientAccount3.money);
  }

  @Test
  public void sortByMinMoneyDesc() {
    clientTestDao.get().deactual();

    Charm charm = randomEntity.get().charm();
    Client client1 = randomEntity.get().client(charm);
    Client client2 = randomEntity.get().client(charm);
    Client client3 = randomEntity.get().client(charm);
    Client client4 = randomEntity.get().client(charm);
    Client client5 = randomEntity.get().client(charm);
    ClientAccount clientAccount1 = randomEntity.get().account(client1, 10000000.0);
    ClientAccount clientAccount2 = randomEntity.get().account(client2, 11000000.0);
    ClientAccount clientAccount3 = randomEntity.get().account(client3, 12000000.0);
    ClientAccount clientAccount4 = randomEntity.get().account(client4, 15000000.0);
    ClientAccount clientAccount5 = randomEntity.get().account(client5, 13300000.0);
    ClientFilter clientFilter = new ClientFilter();
    clientFilter.sort = "minMoney";
    clientFilter.order = true;
    //
    //
    clientTestDao.get().insertCharm(charm);
    clientTestDao.get().insertClient(client1);
    clientTestDao.get().insertClient(client2);
    clientTestDao.get().insertClient(client3);
    clientTestDao.get().insertClient(client4);
    clientTestDao.get().insertClient(client5);
    clientTestDao.get().insertAccount(clientAccount1);
    clientTestDao.get().insertAccount(clientAccount2);
    clientTestDao.get().insertAccount(clientAccount3);
    clientTestDao.get().insertAccount(clientAccount4);
    clientTestDao.get().insertAccount(clientAccount5);
    //
    //
    List<ClientRecord> list = clientRegister.get().list(clientFilter);
    //
    //
    assertThat(list.get(0).commonMoney).isEqualTo(clientAccount4.money);
    assertThat(list.get(1).commonMoney).isEqualTo(clientAccount5.money);
    assertThat(list.get(2).commonMoney).isEqualTo(clientAccount3.money);
    assertThat(list.get(3).commonMoney).isEqualTo(clientAccount2.money);
    assertThat(list.get(4).commonMoney).isEqualTo(clientAccount1.money);
  }


}
