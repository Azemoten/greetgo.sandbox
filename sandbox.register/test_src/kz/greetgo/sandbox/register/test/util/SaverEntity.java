package kz.greetgo.sandbox.register.test.util;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.register.test.dao.ClientDaoTest;

public class SaverEntity {
    public BeanGetter<ClientDaoTest> clientDaoTest;


    public void save(Client client, Charm charm) {
        clientDaoTest.get().insertClient(client, charm);
    }

    public void save(Charm charm) {
        clientDaoTest.get().insertCharm(charm);
    }

}
