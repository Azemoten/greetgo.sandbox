package kz.greetgo.sandbox.register.util;


import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientFilter;

import java.util.Objects;

public class SqlProvider {
    public static String myMethod(ClientFilter clientFilter) {
        String query ="SELECT c.id, c.name, c.surname, c.patronymic, ch.name charmName, (current_date-c.birth_date)/365 age";
        SQL sql = new SQL();
        sql.select("c.id, c.name, c.surname, c.patronymic, ch.name charmName, (current_date-c.birth_date)/365 age");
        sql.select("sum(c_acc.money) commonMoney, MAX(c_acc.money) maxMoney, MIN(c_acc.money) minMoney");
        sql.from("client c");
        sql.innerjoin("charm ch on c.charm = ch.id");
        sql.leftjoin("client_account c_acc on c.id=c_acc.client");
        sql.where("c.actual = true");
        if (Objects.nonNull(clientFilter.name)) {
            sql.where("LOWER(c.name) like '%' || LOWER(#{name}) || '%'");
        }
        if (Objects.nonNull(clientFilter.surname)) {
            sql.where("LOWER(c.surname) like '%' || LOWER(#{surname}) || '%'");
        }
        if (Objects.nonNull(clientFilter.patronymic)) {
            sql.where("LOWER(c.patronymic) like '%' || LOWER(#{patronymic}) || '%'");
        }
        sql.group_by("c.id, ch.name");
        if (Objects.equals(clientFilter.sort, "name") && Objects.nonNull(clientFilter.sort)) {
            if(clientFilter.order==false){
                sql.order_by("name asc");
            }
            else{
                sql.order_by("name desc");
            }
        }
        else if(Objects.equals(clientFilter.sort, "surname") && Objects.nonNull(clientFilter.sort)){
            if(clientFilter.order==false){
                sql.order_by("surname asc");
            }
            else{
                sql.order_by("surname desc");
            }
        }
        else if(Objects.equals(clientFilter.sort, "patronymic") && Objects.nonNull(clientFilter.sort)){
            if(clientFilter.order==false){
                sql.order_by("patronymic asc");
            }
            else{
                sql.order_by("patronymic desc");
            }
        }
        else if(Objects.equals(clientFilter.sort, "id") && Objects.nonNull(clientFilter.sort)){
            if(clientFilter.order==false){
                sql.order_by("id asc");
            }
            else{
                sql.order_by("id desc");
            }
        }
        sql.limit("5");
        sql.offset("#{page}*5");
        return sql.toString();
    }
}