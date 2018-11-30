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
    public List<ClientsDisplay> listClients() {
        return clientDao.get().listClients();
    }

    @Override
    public List<Charm> listCharms() {
        return clientDao.get().listCharms();
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
        cl.birthDate = clientSave.birthDate;
        cl.charm = clientSave.charm;
        cl.patronymic = clientSave.patronymic;
        cl.gender = clientSave.gender;
        Client created = clientDao.get().createClient(cl);

        ClientAddr clientAddr1 = new ClientAddr();
        clientAddr1.client = created.id;
        clientAddr1.flat = clientSave.factFlat;
        clientAddr1.house = clientSave.factHouse;
        clientAddr1.street = clientSave.factStreet;
        clientAddr1.type = ClientAddr.addrType.FACT;
        clientDao.get().createAddr(clientAddr1);

        ClientPhone clientPhone1 = new Clien
        clientDao.get().createClientPhone();
    }
}
