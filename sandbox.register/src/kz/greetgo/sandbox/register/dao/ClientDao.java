package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.register.util.SqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ClientDao {
    @SelectProvider(type = SqlProvider.class, method = "getListClient")
    List<ClientRecord> listClients(ClientFilter clientFilter);

    @Select("select * from charm")
    List<Charm> listCharms();

    @Delete("update client set actual=false where id=#{id}")
    void deleteClient(Integer id);

    @SelectProvider(type = SqlProvider.class, method = "create")
    int insertClient(Client client);


    @Insert("insert into client_phone(client, number, type) values(#{client}, #{number}, #{type}::phone)")
    void insertPhone(ClientPhone clientPhone);

    @Insert("insert into client_addr(client, type, street, house, flat) values(#{client}, #{type}::address, #{street}, #{house}, #{flat})")
    void insertAddress(ClientAddr clientAddr);

    @SelectProvider(type = SqlProvider.class, method = "numPage")
    Integer numPage(ClientFilter clientFilter);

    @Select("select id, surname, name, patronymic, gender, birth_date birthDate, charm from client where actual=true and id=#{id}")
    Client getClient(int id);

    @Select("select * from client_addr where client=#{id}")
    List<ClientAddr> getAddresses(int id);

    @Select("select * from client_phone where client=#{id}")
    List<ClientPhone> getPhones(int id);

    @Update("UPDATE client\n" +
            "   SET surname=#{surname}, name=#{name}, patronymic=#{patronymic}, gender=#{gender}::gender, birth_date=#{birthDate}, \n" +
            "   charm=#{charm}\n" +
            " WHERE id=#{id};\n")
    void updateClient(Client client);

    @Update("UPDATE client_addr\n" +
            "   SET street=#{street}, house=#{house}, flat=#{flat}\n" +
            " WHERE client=#{client} and type=#{type}::address\n")
    void updateAddr(ClientAddr clientAddr);

    @Update("UPDATE client_phone\n" +
            "   SET \"number\"=#{number}\n" +
            " WHERE client=#{client} and type=#{type}::phone\n")
    void updatePhone(ClientPhone clientPhone);
}

