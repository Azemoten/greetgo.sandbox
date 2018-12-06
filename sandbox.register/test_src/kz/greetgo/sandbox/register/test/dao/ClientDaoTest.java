package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import org.apache.ibatis.annotations.*;

@Bean
public interface ClientDaoTest {
    @Insert("insert into charm(name, description, energy) " +
            "VALUES(#{name}, #{description}, #{energy})")
    void insertCharm(Charm charm);


    @Insert("insert into client(surname, name, patronymic, gender, birth_date, charm)" +
            " values(#{client.surname}, #{client.name}, #{client.patronymic}, #{client.gender}, " +
            "#{client.birthDate}, #{charm.id})")
    void insertClient(@Param("client")Client client, @Param("charm") Charm charm);

    @Select("select count(*) from client")
    int getNumOfClients();
}
