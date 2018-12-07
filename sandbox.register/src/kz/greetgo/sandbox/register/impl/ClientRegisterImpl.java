package kz.greetgo.sandbox.register.impl;


import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.dao.ClientDao;

import java.util.List;

@Bean
public class ClientRegisterImpl implements ClientRegister {

    public BeanGetter<ClientDao> clientDao;

    @Override
    public List<ClientRecord> getList(ClientFilter clientFilter) {
        return clientDao.get().listClients(clientFilter);
    }

    @Override
    public List<Charm> listCharms() {
        return clientDao.get().listCharms();
    }

    @Override
    public Integer numPage() {
        return clientDao.get().numPage();
    }

    @Override
    public void deleteClient(Integer id) {
        clientDao.get().deleteClient(id);
    }

    @Override
    public void createFullClient(ClientSave clientSave) {
        Client cl = new Client();
        cl.name = clientSave.name;
        cl.surname = clientSave.surname;
        //cl.bDate = clientSave.birthDate;
        cl.charm = clientSave.charm;
        cl.patronymic = clientSave.patronymic;
        cl.gender = Gender.valueOf("MALE");
        Client created = clientDao.get().createClient(cl);


    }

    @Override
    public void exCreate(Client client) {
        clientDao.get().exCreate(client);
    }


}
