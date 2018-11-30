package kz.greetgo.sandbox.register.impl;


import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.ClientsDisplay;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao.ClientDao;

import java.util.List;

@Bean
public class ClientRegisterImpl implements ClientRegister {

    public BeanGetter<ClientDao> clientDao;

    @Override
    public List<ClientsDisplay> listClients() {
        return clientDao.get().listClients();
    }
}
