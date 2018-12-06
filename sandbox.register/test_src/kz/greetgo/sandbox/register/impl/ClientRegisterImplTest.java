package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientFilter;
import kz.greetgo.sandbox.controller.model.ClientRecord;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.test.util.RandomEntity;
import kz.greetgo.sandbox.register.test.dao.ClientDaoTest;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.sandbox.register.test.util.SaverEntity;
import org.testng.annotations.Test;


import java.util.Arrays;
import java.util.List;

import static org.fest.assertions.api.Assertions.assertThat;

public class ClientRegisterImplTest extends ParentTestNg {

    public BeanGetter<ClientDaoTest> clientTestDao;
    public BeanGetter<ClientRegister> clientRegister;
    public RandomEntity randomEntity;
    public SaverEntity saver = new SaverEntity();

    final String baseUrl = "http://localhost:1313/sandbox/api/person/listClient";


    @Test
    public void list_assign_column() {
        Charm charm = randomEntity.charm();
        saver.save(charm);

        Client client = randomEntity.client(charm);
        saver.save(client, charm);
        int count = clientTestDao.get().getNumOfClients()%clientRegister.get().numPage();


        ClientFilter clientFilter = randomEntity.clientSortById();
        //
        //
        List<ClientRecord> list = clientRegister.get().getList(clientFilter);
        //
        //
        assertThat(list.size()).isEqualTo(4);
    }

    @Test
    public void nextPageAfterFirst() {

        ClientFilter clientFilter = randomEntity.clientSortById();
        clientFilter.page = 1;
        //
        //
        List<ClientRecord> list = clientRegister.get().getList(clientFilter);
        //
        //
        assertThat(list.size()).isEqualTo(5);
    }

    @Test
    public void lastPage_size(){
        ClientFilter clientFilter = randomEntity.clientSortById();
        clientFilter.page = clientRegister.get().numPage();
        int numberOfClients = clientTestDao.get().getNumOfClients();
        int numberOfLastPage = randomEntity.numOfLastPage(clientFilter, numberOfClients);
        //
        //
        List<ClientRecord> list = clientRegister.get().getList(clientFilter);
        //
        //
        assertThat(list.size()).isEqualTo(numberOfLastPage);
    }

    @Test
    public void nextAfterLastPage(){
        ClientFilter clientFilter = randomEntity.clientSortById();
        clientFilter.page = clientRegister.get().numPage()+1;
        //
        //
        //
        List<ClientRecord> list = clientRegister.get().getList(clientFilter);
        //
        //
        //
        assertThat(list).isEqualTo(Arrays.asList());
    }
    @Test
    public void updateClient(){}

    @Test
    public void insertClient(){}

    @Test
    public void deleteClient(){}

    @Test
    public void list_size_filterByName(){
        Charm charm = randomEntity.charm();
        Client client = randomEntity.client(charm);

        ClientFilter clientFilter = randomEntity.clientFilterBy("name");
        //
        //
        clientTestDao.get().insertCharm(charm);
        clientTestDao.get().insertClient(client, charm);
        //
        //
        List<ClientRecord> list = clientRegister.get().getList(clientFilter);
        //
        //
        assertThat(list).isNotNull();
        assertThat(list).hasSize(1);
        assertThat(list.get(0).id).isEqualTo(client.id);

    }

    @Test
    public void sortByNameAsc(){}

    @Test
    public void sortByNameDesc(){}

    @Test
    public void sortByCommonMoneyAsc(){}

    @Test
    public void sortByCommonMoneyDesc(){}

    @Test
    public void sortByAgeAsc(){}

    @Test
    public void sortByAgeDesc(){}

    @Test
    public void sortByMaxMoneyAsc(){}

    @Test
    public void sortByMaxMoneyDesc(){}

    @Test
    public void sortByMinMoneyAsc(){}

    @Test
    public void sortByMinMoneyDesc(){}

    @Test
    public void filterBySurname(){}

    @Test
    public void filterByPatronymic(){}


}
