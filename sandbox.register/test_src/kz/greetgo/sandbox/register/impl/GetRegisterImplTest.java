package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.ClientsDisplay;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dataForTest.ClientEntity;
import kz.greetgo.sandbox.register.test.dao.ClientTestDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Random;

import static org.fest.assertions.api.Assertions.assertThat;

public class GetRegisterImplTest extends ParentTestNg {

    Random rnd = new Random();
    int min = 100000;
    int max = 100000000;
    public BeanGetter<ClientTestDao> clientTestDao;
    public BeanGetter<ClientRegister> clientRegister;

    public enum Gender {
        MALE, FEMALE
    }

    @Test
    public void getClients() {
        //
        //
        Charm charm = ClientEntity.getCharm();
        Client testClient = ClientEntity.getClient(charm.id);
        //
        //
        //
        //
        clientTestDao.get().insertCharm(charm);
        clientTestDao.get().insertClient(testClient);
        //
        //
        List<ClientsDisplay> client = clientRegister.get().listClients();
        //
        //
        assertThat(client).isNotNull();
    }


}
