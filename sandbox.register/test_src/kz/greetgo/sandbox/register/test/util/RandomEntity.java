package kz.greetgo.sandbox.register.test.util;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientFilter;
import kz.greetgo.util.RND;


public class RandomEntity {


    public static   Client client(Charm charm) {
        Client client = new Client();
        client.id = RND.plusInt(1000000);
        client.surname = RND.str(10);
        client.name = RND.str(10);
        client.patronymic = RND.str(10);
        client.birthDate = RND.dateDays(1000, 1900);
        client.charm = charm.id;
        return client;
    }

    public static  ClientFilter clientSortById(){
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.sort="id";
        clientFilter.order=true;
        return clientFilter;
    }

    public static ClientFilter clientFilterBy(String filter){
        ClientFilter clientFilter = new ClientFilter();
        if(filter.equals("name")){
            clientFilter.name = RND.str(10);
        }
        else if(filter.equals("surname")){
            clientFilter.surname = RND.str(10);
        }
        else if(filter.equals("patronymic")){
            clientFilter.patronymic = RND.str(10);
        }
        return clientFilter;
    }



    public static  Charm charm() {
        Charm charm = new Charm();
        charm.id = RND.plusInt(1000000);
        charm.description = RND.str(11);
        charm.energy = RND.plusDouble(0, 100);
        charm.name = RND.str(11);
        return charm;
    }

    public static int numOfLastPage(ClientFilter clientFilter, int count){
        while(count>clientFilter.page){
            count-=5;
        }
        return count;
    }

    public static ClientFilter clientSortByName(boolean bool, String filter){
        ClientFilter clientFilter = new ClientFilter();
        clientFilter.order = bool;
        clientFilter.sort = filter;
        return clientFilter;
    }
}
