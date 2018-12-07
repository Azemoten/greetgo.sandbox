package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientAccount;
import org.apache.ibatis.annotations.*;


public interface ClientDaoTest {
    @Insert("insert into charm(id, name, description, energy) " +
            "VALUES(#{id}, #{name}, #{description}, #{energy})")
    void insertCharm(Charm charm);


    @Insert("insert into client(id, surname, name, patronymic, gender, birth_date, charm)" +
            " values(#{id}, #{surname}, #{name}, #{patronymic}, #{gender}::gender, " +
            "#{birthDate}, #{charm})")
    void insertClient(Client client);

    @Select("select count(*) from client where actual=true")
    int getNumOfClients();

    @Insert("insert into client_account(id, client, money, number ) values(#{id}, #{client}, #{money}, #{number})")
    void insertAccount(ClientAccount clientAccount);
}
