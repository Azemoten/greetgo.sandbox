package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.Client;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;


public interface ClientTestDao {
    @Results({
            @Result(property = "cid", column = "id"),
            @Result(property = "cname", column = "name"),
            @Result(property = "csurname", column = "surname"),
            @Result(property = "csex", column = "sex"),
            @Result(property = "cbirthDate", column = "birth_date")
    })

    @Insert("insert into client(id, name, surname, sex, birth_date) " +
            "VALUES(#{cid}, #{cname}, #{csurname}, #{csex}, #{cbirthDate})")
    void insertCharm(Charm charm);


    void insertClient(Client client);
}
