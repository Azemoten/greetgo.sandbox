package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.test.util.RandomEntity;
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
    public RandomEntity randomEntity;


    @Test
    public void numberOfPageAfterSorting() {
        Charm charm = randomEntity.charm();
        Client client = randomEntity.client(charm);
        ClientFilter clientFilter = randomEntity.clientFilterBy("name", client);
        ClientFilter clientFilter1 = new ClientFilter();
        //
        //
        clientTestDao.get().insertCharm(charm);
        clientTestDao.get().insertClient(client);
        //
        //
        int page1 = clientRegister.get().numPage(clientFilter);
        int page2 = clientRegister.get().numPage(clientFilter1);
        //
        //
        assertThat(page1).isNotEqualTo(page2);
        //
        //
        clientRegister.get().remove(client.id);
    }

    @Test
    public void nextPageAfterFirst() {

        ClientFilter clientFilter = randomEntity.clientSortById();
        clientFilter.page = 1;
        int numberOfClients = clientTestDao.get().getNumOfClients();
        int numberOfLastPage = randomEntity.numOfLastPage(numberOfClients);
        //
        //
        List<ClientRecord> list = clientRegister.get().list(clientFilter);
        //
        //
        if (numberOfClients >= 10) {
            assertThat(list.size()).isEqualTo(5);
        } else {
            assertThat(list.size()).isEqualTo(numberOfLastPage);
        }

    }


    @Test
    public void lastPage_size() {
        ClientFilter clientFilter = randomEntity.clientSortById();
        clientFilter.page = clientRegister.get().numPage(clientFilter);
        int numberOfClients = clientTestDao.get().getNumOfClients();
        int numberOfLastPage = randomEntity.numOfLastPage(numberOfClients);
        //
        //
        List<ClientRecord> list = clientRegister.get().list(clientFilter);
        //
        //
        assertThat(list.size()).isEqualTo(numberOfLastPage);
    }

    @Test
    public void nextAfterLastPage() {
        ClientFilter clientFilter = randomEntity.clientSortById();
        clientFilter.page = clientRegister.get().numPage(clientFilter) + 1;
        //
        //
        //
        List<ClientRecord> list = clientRegister.get().list(clientFilter);
        //
        //
        //
        assertThat(list).isEqualTo(Arrays.asList());
    }

    @Test
    public void update_insert_Client() {
        Charm charm = randomEntity.charm();
        Client client = randomEntity.client(charm);
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
        clientAddr.flat="Changed";
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

        clientRegister.get().remove(client.id);

    }

    @Test
    public void deleteClient() {
        Charm charm = randomEntity.charm();
        Client client = randomEntity.client(charm);
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

        ///
        //
        //

        assertThat(deleted).isEqualTo(true);

    }

    @Test
    public void list_size_filterByName() {
        Charm charm = randomEntity.charm();
        Client client = randomEntity.client(charm);

        ClientFilter clientFilter = randomEntity.clientFilterBy("name", client);
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
    public void filterBySurname() {
        Charm charm = randomEntity.charm();
        Client client = randomEntity.client(charm);
        ClientFilter clientFilter = randomEntity.clientFilterBy("surname", client);
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
        Charm charm = randomEntity.charm();
        Client client = randomEntity.client(charm);
        ClientFilter clientFilter = randomEntity.clientFilterBy("patronymic", client);
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
    public void sortByNameAsc() {
        Client client1 = randomEntity.client("AAAAAA");
        Client client2 = randomEntity.client("AAAAAD");
        Client client3 = randomEntity.client("AAAAAC");
        Client client4 = randomEntity.client("AAAAAB");
        Client client5 = randomEntity.client("AAAAAE");
        ClientFilter clientFilter = randomEntity.clientSortByName(false, "name");
        //
        //
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
        //
        //
        clientRegister.get().remove(client1.id);
        clientRegister.get().remove(client2.id);
        clientRegister.get().remove(client3.id);
        clientRegister.get().remove(client4.id);
        clientRegister.get().remove(client5.id);

    }

    @Test
    public void sortByNameDesc() {
        Client client1 = randomEntity.client("ZZZZZZ");
        Client client2 = randomEntity.client("ZZZZZW");
        Client client3 = randomEntity.client("ZZZZZX");
        Client client4 = randomEntity.client("ZZZZZY");
        Client client5 = randomEntity.client("ZZZZZV");
        ClientFilter clientFilter = randomEntity.clientSortByName(true, "name");
        //
        //
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
        //
        //
        clientRegister.get().remove(client1.id);
        clientRegister.get().remove(client2.id);
        clientRegister.get().remove(client3.id);
        clientRegister.get().remove(client4.id);
        clientRegister.get().remove(client5.id);
    }

    @Test
    public void sortByCommonMoneyDesc() {
        Charm charm = randomEntity.charm();
        Client client1 = randomEntity.client(charm);
        Client client2 = randomEntity.client(charm);
        Client client3 = randomEntity.client(charm);
        Client client4 = randomEntity.client(charm);
        Client client5 = randomEntity.client(charm);
        ClientAccount clientAccount1 = randomEntity.account(client1, 18000000.0);
        ClientAccount clientAccount2 = randomEntity.account(client2, 15000000.0);
        ClientAccount clientAccount3 = randomEntity.account(client3, 17000000.0);
        ClientAccount clientAccount4 = randomEntity.account(client4, 10000000.0);
        ClientAccount clientAccount5 = randomEntity.account(client5, 20000000.0);
        ClientFilter clientFilter = randomEntity.clientSortByName(true, "commonMoney");
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
        //5,1,3,2.4
        //
        //
        assertThat(list.get(0).commonMoney).isEqualTo(clientAccount5.money);
        assertThat(list.get(1).commonMoney).isEqualTo(clientAccount1.money);
        assertThat(list.get(2).commonMoney).isEqualTo(clientAccount3.money);
        assertThat(list.get(3).commonMoney).isEqualTo(clientAccount2.money);
        assertThat(list.get(4).commonMoney).isEqualTo(clientAccount4.money);
        //
        //
        clientRegister.get().remove(client1.id);
        clientRegister.get().remove(client2.id);
        clientRegister.get().remove(client3.id);
        clientRegister.get().remove(client4.id);
        clientRegister.get().remove(client5.id);
    }

    @Test
    public void sortByCommonMoneyAsc() {
        Charm charm = randomEntity.charm();
        Client client1 = randomEntity.client(charm);
        Client client2 = randomEntity.client(charm);
        Client client3 = randomEntity.client(charm);
        Client client4 = randomEntity.client(charm);
        Client client5 = randomEntity.client(charm);
        ClientAccount clientAccount1 = randomEntity.account(client1, -400.0);
        ClientAccount clientAccount2 = randomEntity.account(client2, -450.0);
        ClientAccount clientAccount3 = randomEntity.account(client3, -600.0);
        ClientAccount clientAccount4 = randomEntity.account(client4, -500.0);
        ClientAccount clientAccount5 = randomEntity.account(client5, -550.0);
        ClientFilter clientFilter = randomEntity.clientSortByName(false, "commonMoney");
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
        //5,1,3,2.4
        //
        //
        assertThat(list.get(0).commonMoney).isEqualTo(clientAccount3.money);
        assertThat(list.get(1).commonMoney).isEqualTo(clientAccount5.money);
        assertThat(list.get(2).commonMoney).isEqualTo(clientAccount4.money);
        assertThat(list.get(3).commonMoney).isEqualTo(clientAccount2.money);
        assertThat(list.get(4).commonMoney).isEqualTo(clientAccount1.money);
        //
        //
        clientRegister.get().remove(client1.id);
        clientRegister.get().remove(client2.id);
        clientRegister.get().remove(client3.id);
        clientRegister.get().remove(client4.id);
        clientRegister.get().remove(client5.id);

    }

    @Test
    public void sortByAgeAsc() {
        Charm charm = randomEntity.charm();
        GregorianCalendar d1 = new GregorianCalendar(2350, 12, 12);
        GregorianCalendar d2 = new GregorianCalendar(2400, 12, 12);
        GregorianCalendar d3 = new GregorianCalendar(2500, 12, 12);
        GregorianCalendar d4 = new GregorianCalendar(2450, 12, 12);
        GregorianCalendar d5 = new GregorianCalendar(2300, 12, 12);
        Client client1 = randomEntity.client(d1, charm);
        Client client2 = randomEntity.client(d2, charm);
        Client client3 = randomEntity.client(d3, charm);
        Client client4 = randomEntity.client(d4, charm);
        Client client5 = randomEntity.client(d5, charm);
        //
        //
        ClientFilter clientFilter = randomEntity.clientSortByName(false, "age");
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
        //5,1,3,2.4
        //2350
        //2400
        //2500
        //2450
        //2300
        assertThat(list.get(0).age).isEqualTo(2018 - 2500);
        assertThat(list.get(1).age).isEqualTo(2018 - 2450);
        assertThat(list.get(2).age).isEqualTo(2018 - 2400);
        assertThat(list.get(3).age).isEqualTo(2018 - 2350);
        assertThat(list.get(4).age).isEqualTo(2018 - 2300);
        //
        //
        clientRegister.get().remove(client1.id);
        clientRegister.get().remove(client2.id);
        clientRegister.get().remove(client3.id);
        clientRegister.get().remove(client4.id);
        clientRegister.get().remove(client5.id);

    }

    @Test
    public void sortByAgeDesc() {
        Charm charm = randomEntity.charm();
        GregorianCalendar d1 = new GregorianCalendar(1000, 12, 12);
        GregorianCalendar d2 = new GregorianCalendar(1100, 12, 12);
        GregorianCalendar d3 = new GregorianCalendar(1050, 12, 12);
        GregorianCalendar d4 = new GregorianCalendar(1150, 12, 12);
        GregorianCalendar d5 = new GregorianCalendar(1200, 12, 12);
        Client client1 = randomEntity.client(d1, charm);
        Client client2 = randomEntity.client(d2, charm);
        Client client3 = randomEntity.client(d3, charm);
        Client client4 = randomEntity.client(d4, charm);
        Client client5 = randomEntity.client(d5, charm);
        //
        //
        ClientFilter clientFilter = randomEntity.clientSortByName(true, "age");
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
        //5,1,3,2.4
        //2350
        //2400
        //2500
        //2450
        //2300
        assertThat(list.get(0).age).isEqualTo(2018 - 1000);
        assertThat(list.get(1).age).isEqualTo(2018 - 1050);
        assertThat(list.get(2).age).isEqualTo(2018 - 1100);
        assertThat(list.get(3).age).isEqualTo(2018 - 1150);
        assertThat(list.get(4).age).isEqualTo(2018 - 1200);
        //
        //
        clientRegister.get().remove(client1.id);
        clientRegister.get().remove(client2.id);
        clientRegister.get().remove(client3.id);
        clientRegister.get().remove(client4.id);
        clientRegister.get().remove(client5.id);

    }


    @Test
    public void sortByMaxMoneyAsc() {
        Charm charm = randomEntity.charm();
        Client client1 = randomEntity.client(charm);
        Client client2 = randomEntity.client(charm);
        Client client3 = randomEntity.client(charm);
        Client client4 = randomEntity.client(charm);
        Client client5 = randomEntity.client(charm);
        ClientAccount clientAccount1 = randomEntity.account(client1, -400.0);
        ClientAccount clientAccount2 = randomEntity.account(client2, -450.0);
        ClientAccount clientAccount3 = randomEntity.account(client3, -600.0);
        ClientAccount clientAccount4 = randomEntity.account(client4, -500.0);
        ClientAccount clientAccount5 = randomEntity.account(client5, -550.0);
        ClientFilter clientFilter = randomEntity.clientSortByName(false, "maxMoney");
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
        //5,1,3,2.4
        //
        //
        assertThat(list.get(0).commonMoney).isEqualTo(clientAccount3.money);
        assertThat(list.get(1).commonMoney).isEqualTo(clientAccount5.money);
        assertThat(list.get(2).commonMoney).isEqualTo(clientAccount4.money);
        assertThat(list.get(3).commonMoney).isEqualTo(clientAccount2.money);
        assertThat(list.get(4).commonMoney).isEqualTo(clientAccount1.money);
        //
        //
        clientRegister.get().remove(client1.id);
        clientRegister.get().remove(client2.id);
        clientRegister.get().remove(client3.id);
        clientRegister.get().remove(client4.id);
        clientRegister.get().remove(client5.id);
    }

    @Test
    public void sortByMaxMoneyDesc() {
        Charm charm = randomEntity.charm();
        Client client1 = randomEntity.client(charm);
        Client client2 = randomEntity.client(charm);
        Client client3 = randomEntity.client(charm);
        Client client4 = randomEntity.client(charm);
        Client client5 = randomEntity.client(charm);
        ClientAccount clientAccount1 = randomEntity.account(client1, 10000000.0);
        ClientAccount clientAccount2 = randomEntity.account(client2, 11000000.0);
        ClientAccount clientAccount3 = randomEntity.account(client3, 12000000.0);
        ClientAccount clientAccount4 = randomEntity.account(client4, 15000000.0);
        ClientAccount clientAccount5 = randomEntity.account(client5, 13300000.0);
        ClientFilter clientFilter = randomEntity.clientSortByName(true, "maxMoney");
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
        //5,1,3,2.4
        //
        //
        assertThat(list.get(0).commonMoney).isEqualTo(clientAccount4.money);
        assertThat(list.get(1).commonMoney).isEqualTo(clientAccount5.money);
        assertThat(list.get(2).commonMoney).isEqualTo(clientAccount3.money);
        assertThat(list.get(3).commonMoney).isEqualTo(clientAccount2.money);
        assertThat(list.get(4).commonMoney).isEqualTo(clientAccount1.money);
        //
        //
        clientRegister.get().remove(client1.id);
        clientRegister.get().remove(client2.id);
        clientRegister.get().remove(client3.id);
        clientRegister.get().remove(client4.id);
        clientRegister.get().remove(client5.id);
    }

    @Test
    public void sortByMinMoneyAsc() {
        Charm charm = randomEntity.charm();
        Client client1 = randomEntity.client(charm);
        Client client2 = randomEntity.client(charm);
        Client client3 = randomEntity.client(charm);
        Client client4 = randomEntity.client(charm);
        Client client5 = randomEntity.client(charm);
        ClientAccount clientAccount1 = randomEntity.account(client1, -600.0);
        ClientAccount clientAccount2 = randomEntity.account(client2, -500.0);
        ClientAccount clientAccount3 = randomEntity.account(client3, -450.0);
        ClientAccount clientAccount4 = randomEntity.account(client4, -550.0);
        ClientAccount clientAccount5 = randomEntity.account(client5, -650.0);
        ClientFilter clientFilter = randomEntity.clientSortByName(false, "minMoney");
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
        //5,1,3,2.4
        //
        //
        assertThat(list.get(0).commonMoney).isEqualTo(clientAccount5.money);
        assertThat(list.get(1).commonMoney).isEqualTo(clientAccount1.money);
        assertThat(list.get(2).commonMoney).isEqualTo(clientAccount4.money);
        assertThat(list.get(3).commonMoney).isEqualTo(clientAccount2.money);
        assertThat(list.get(4).commonMoney).isEqualTo(clientAccount3.money);
        //
        //
        clientRegister.get().remove(client1.id);
        clientRegister.get().remove(client2.id);
        clientRegister.get().remove(client3.id);
        clientRegister.get().remove(client4.id);
        clientRegister.get().remove(client5.id);
    }

    @Test
    public void sortByMinMoneyDesc() {
        Charm charm = randomEntity.charm();
        Client client1 = randomEntity.client(charm);
        Client client2 = randomEntity.client(charm);
        Client client3 = randomEntity.client(charm);
        Client client4 = randomEntity.client(charm);
        Client client5 = randomEntity.client(charm);
        ClientAccount clientAccount1 = randomEntity.account(client1, 10000000.0);
        ClientAccount clientAccount2 = randomEntity.account(client2, 11000000.0);
        ClientAccount clientAccount3 = randomEntity.account(client3, 12000000.0);
        ClientAccount clientAccount4 = randomEntity.account(client4, 15000000.0);
        ClientAccount clientAccount5 = randomEntity.account(client5, 13300000.0);
        ClientFilter clientFilter = randomEntity.clientSortByName(true, "minMoney");
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
        //5,1,3,2.4
        //
        //
        assertThat(list.get(0).commonMoney).isEqualTo(clientAccount4.money);
        assertThat(list.get(1).commonMoney).isEqualTo(clientAccount5.money);
        assertThat(list.get(2).commonMoney).isEqualTo(clientAccount3.money);
        assertThat(list.get(3).commonMoney).isEqualTo(clientAccount2.money);
        assertThat(list.get(4).commonMoney).isEqualTo(clientAccount1.money);
        //
        //
        clientRegister.get().remove(client1.id);
        clientRegister.get().remove(client2.id);
        clientRegister.get().remove(client3.id);
        clientRegister.get().remove(client4.id);
        clientRegister.get().remove(client5.id);
    }


}
