package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
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

  @Insert("insert into client_phone(client, number, type) values(#{client}, #{number}, #{type}::phone)")
  void insertPhone(ClientPhone clientPhone);

  @Insert("insert into client_addr(client, type, street, house, flat) values(#{client}, #{type}::address, #{street}, #{house}, #{flat})")
  void insertAddress(ClientAddr clientAddr);

  @Select("Select * from client where id=#{id} and actual=false")
  boolean checkDel(int id);

  @Update("update client set actual=false")
  void deactual();
}
