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
    public List<ClientRecord> list(ClientFilter clientFilter) {
        return clientDao.get().listClients(clientFilter);
    }

    @Override
    public List<Charm> listCharms() {
        return clientDao.get().listCharms();
    }

    @Override
    public Integer numPage(ClientFilter clientFilter) {
        return clientDao.get().numPage(clientFilter);
    }

    @Override
    public void remove(Integer id) {
        clientDao.get().deleteClient(id);
    }

    @Override
    public void create(ClientDetail clientDetail) {
        int clientId = clientDao.get().insertClient(clientDetail.client);
        for (int i = 0; i < clientDetail.addrs.size(); i++) {
            ClientAddr addr = clientDetail.addrs.get(i);
            addr.client = clientId;
            clientDao.get().insertAddress(addr);
        }
        for (int i = 0; i < clientDetail.phones.size(); i++) {
            ClientPhone clientPhone = clientDetail.phones.get(i);
            clientPhone.client = clientId;
            clientDao.get().insertPhone(clientPhone);
        }
    }

    @Override
    public ClientDetail clientDetails(int id) {
        ClientDetail clientDetail = new ClientDetail();
        clientDetail.client = clientDao.get().getClient(id);
        clientDetail.addrs = clientDao.get().getAddresses(id);
        clientDetail.phones = clientDao.get().getPhones(id);
        return clientDetail;
    }

    @Override
    public void update(ClientDetail clientDetail) {
        Client client = clientDetail.client;
        clientDao.get().updateClient(client);
        for (int i = 0; i< clientDetail.addrs.size(); i++) {
            ClientAddr clientAddr = clientDetail.addrs.get(0);
            clientAddr.client=client.id;
            clientDao.get().updateAddr(clientAddr);
        }
        for (int i = 0; i< clientDetail.phones.size(); i++) {
            ClientPhone clientPhone = clientDetail.phones.get(0);
            clientPhone.client=client.id;
            clientDao.get().updatePhone(clientPhone);
        }

    }


}
