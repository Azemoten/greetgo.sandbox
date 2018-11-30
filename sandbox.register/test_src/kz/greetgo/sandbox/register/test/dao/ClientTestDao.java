package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.controller.model.Charm;
import kz.greetgo.sandbox.controller.model.ClientDisplay;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface CrudDao {
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

    @Select("select * from client")
    ClientDisplay getClientById(Integer id);



    void insertClient(ClientDisplay client);
}
