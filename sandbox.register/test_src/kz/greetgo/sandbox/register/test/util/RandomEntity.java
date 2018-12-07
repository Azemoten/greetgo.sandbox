package kz.greetgo.sandbox.register.test.util;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.util.RND;

import java.util.GregorianCalendar;


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
    public  static Client client(GregorianCalendar date, Charm charm){
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

    public static  ClientFilter clientSortById(){
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.sort="id";
        clientFilter.order=true;
        return clientFilter;
    }

    public static ClientFilter clientFilterBy(String filter, Client client){
        ClientFilter clientFilter = new ClientFilter();
        if(filter.equals("name")){
            clientFilter.name = client.name;
        }
        else if(filter.equals("surname")){
            clientFilter.surname = client.surname;
        }
        else if(filter.equals("patronymic")){
            clientFilter.patronymic = client.patronymic;
        }
        return clientFilter;
    }



    public static  Charm charm() {
        Charm charm = new Charm();
        charm.id = RND.plusInt(1000000000);
        charm.description = RND.str(11);
        charm.energy = RND.plusDouble(0, 100);
        charm.name = RND.str(11);
        return charm;
    }

    public static int numOfLastPage(int count){
        while(count>5){
            count-=5;
        }
        return count;
    }

    public static ClientFilter clientSortByName(boolean bool, String sort){
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.order = bool;
        clientFilter.sort=sort;
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
}
