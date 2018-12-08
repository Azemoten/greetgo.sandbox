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
    public Integer numPage(ClientFilter clientFilter) {
        return clientDao.get().numPage(clientFilter);
    }

    @Override
    public void deleteClient(Integer id) {
        clientDao.get().deleteClient(id);
    }

    @Override
    public void createClient(ClientSave clientSave) {
        int clientId = clientDao.get().insertClient(clientSave.client);
        for (int i = 0; i < clientSave.addrs.size(); i++) {
            ClientAddr addr = clientSave.addrs.get(i);
            addr.client = clientId;
            clientDao.get().insertAddress(addr);
        }
        for (int i = 0; i < clientSave.phones.size(); i++) {
            ClientPhone clientPhone = clientSave.phones.get(i);
            clientPhone.client = clientId;
            clientDao.get().insertPhone(clientPhone);
        }
    }

    @Override
    public ClientSave getClientForEdit(int id) {
        ClientSave clientSave = new ClientSave();
        clientSave.client = clientDao.get().getClient(id);
        clientSave.addrs = clientDao.get().getAddresses(id);
        clientSave.phones = clientDao.get().getPhones(id);
        return clientSave;
    }

    @Override
    public void updateClient(ClientSave clientSave) {
        Client client = clientSave.client;
        clientDao.get().updateClient(client);
        for (int i=0;i<clientSave.addrs.size();i++) {
            ClientAddr clientAddr = clientSave.addrs.get(0);
            clientAddr.client=client.id;
            clientDao.get().updateAddr(clientAddr);
        }
        for (int i=0; i<clientSave.phones.size(); i++) {
            ClientPhone clientPhone = clientSave.phones.get(0);
            clientPhone.client=client.id;
            clientDao.get().updatePhone(clientPhone);
        }

    }


}
