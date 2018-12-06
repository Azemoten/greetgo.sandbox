package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.register.util.SqlProvider;
import org.apache.ibatis.annotations.*;

import java.util.List;

public interface ClientDao {
    @SelectProvider(type=SqlProvider.class, method = "myMethod")
    List<ClientRecord> listClients(ClientFilter clientFilter);


    @Select("select * from charm where surname like #{surname} || '%'")
    List<Charm> listCharms();

    @Delete("update client set actual=false where id=#{id}")
    void deleteClient(Integer id);

    //client, client_addr, client_phone


    @Insert("insert into client() values()")
    Client createClient(Client client);

    @Insert("insert into client_addr() values()")
    void createAddr(ClientAddr clientAddr);

    @Insert("insert into client_phone() values()")
    void createClientPhone();


    @Insert("insert into public.client(surname, name, patronymic, gender, birth_date, charm)\n" +
            "    values (#{surname}, #{name}, #{patronymic}, 'MALE', #{birthDate}, 1)")
    void exCreate(Client client);

    @Select("select count(*)/5 - 1 from client")
    Integer numPage();
}
