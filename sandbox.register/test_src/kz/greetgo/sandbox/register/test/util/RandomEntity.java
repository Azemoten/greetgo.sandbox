package kz.greetgo.sandbox.register.test.util;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.util.RND;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;


public class RandomEntity {


    public static Client client(Charm charm) {
        Client client = new Client();
        client.id = RND.plusInt(1000000000);
        client.surname = RND.str(10);
        client.name = RND.str(10);
        client.patronymic = RND.str(10);
        client.gender = Gender.FEMALE;
        client.birthDate = new GregorianCalendar(2014, 4, 12).getTime();
        client.charm = charm.id;
        return client;
    }

    public static Client client(GregorianCalendar date, Charm charm) {
        Client client = new Client();
        client.id = RND.plusInt(1000000000);
        client.surname = RND.str(10);
        client.name = RND.str(10);
        client.patronymic = RND.str(10);
        client.gender = Gender.FEMALE;
        client.birthDate = date.getTime();
        client.charm = charm.id;
        return client;
    }


    public static Client client(String name) {
        Client client = new Client();
        client.id = RND.plusInt(1000000000);
        client.surname = RND.str(10);
        client.name = name;
        client.patronymic = RND.str(10);
        client.gender = Gender.FEMALE;
        client.birthDate = new GregorianCalendar(2014, 4, 12).getTime();
        client.charm = 2;
        return client;
    }

    public static ClientFilter clientSortById() {
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.sort = "id";
        clientFilter.order = true;
        return clientFilter;
    }

    public static ClientFilter clientFilterBy(String filter, Client client) {
        ClientFilter clientFilter = new ClientFilter();
        if (filter.equals("name")) {
            clientFilter.name = client.name;
        } else if (filter.equals("surname")) {
            clientFilter.surname = client.surname;
        } else if (filter.equals("patronymic")) {
            clientFilter.patronymic = client.patronymic;
        }
        return clientFilter;
    }


    public static Charm charm() {
        Charm charm = new Charm();
        charm.id = RND.plusInt(1000000000);
        charm.description = RND.str(11);
        charm.energy = RND.plusDouble(0, 100);
        charm.name = RND.str(11);
        return charm;
    }

    public static int numOfLastPage(int count) {
        while (count > 5) {
            count -= 5;
        }
        return count;
    }

    public static ClientFilter clientSortByName(boolean bool, String sort) {
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.order = bool;
        clientFilter.sort = sort;
        return clientFilter;
    }

    public static ClientAccount account(Client client, Double money) {
        ClientAccount clientAccount = new ClientAccount();
        clientAccount.id = RND.plusInt(1000000000);
        clientAccount.client = client.id;
        clientAccount.money = money;
        clientAccount.number = RND.str(10);
        return clientAccount;
    }

//    public static ClientSave clientSave(Charm charm) {
//        ClientSave clientSave = new ClientSave();
//        Client client = client(charm);
//        clientSave.client = client;
//        List<ClientAddr> addresses = clientAddr(clientSave.client.id);
//        List<ClientPhone> clientPhones = clientPhones(clientSave.client.id);
//        clientSave.phones = clientPhones;
//        clientSave.addrs = addresses;
//        return clientSave;
//    }

    public static List<ClientAddr> clientAddr(Client client) {
        List<ClientAddr> list = new ArrayList<>();
        ClientAddr clientAddr1 = new ClientAddr();
        ClientAddr clientAddr2 = new ClientAddr();
        clientAddr1.type = AddressType.FACT;
        clientAddr1.street = RND.str(10);
        clientAddr1.house = RND.str(4);
        clientAddr1.flat = RND.str(3);
        clientAddr1.client = client.id;
        clientAddr2.type =AddressType.REG;
        clientAddr2.street = RND.str(10);
        clientAddr2.house = RND.str(4);
        clientAddr2.flat = RND.str(3);
        clientAddr2.client = client.id;
        list.add(clientAddr1);
        list.add(clientAddr2);
        return list;
    }

    public static List<ClientPhone> clientPhones(Client client){
        List<ClientPhone> list = new ArrayList<>();
        ClientPhone clientPhone1 = new ClientPhone();
        ClientPhone clientPhone2 = new ClientPhone();
        ClientPhone clientPhone3 = new ClientPhone();
        clientPhone1.client = client.id;
        clientPhone1.number = RND.str(8);
        clientPhone1.type = PhoneType.HOME;
        clientPhone2.client = client.id;
        clientPhone2.number = RND.str(8);
        clientPhone2.type = PhoneType.MOBILE;
        clientPhone3.client = client.id;
        clientPhone3.number = RND.str(8);
        clientPhone3.type = PhoneType.WORK;
        list.add(clientPhone1);
        list.add(clientPhone2);
        list.add(clientPhone3);
        return list;
    }
}
