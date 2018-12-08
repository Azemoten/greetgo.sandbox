package kz.greetgo.sandbox.controller.register;

import kz.greetgo.sandbox.controller.model.*;

import java.util.List;

public interface ClientRegister {

    List<ClientRecord> getList(ClientFilter clientFilter);

    List<Charm> listCharms();

    Integer numPage(ClientFilter clientFilter);

    void deleteClient(Integer id);

    void createClient(ClientSave clientSave);

    ClientSave getClientForEdit(int id);

    void updateClient(ClientSave clientSave);
}
