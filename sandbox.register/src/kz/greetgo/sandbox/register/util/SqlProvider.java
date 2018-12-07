package kz.greetgo.sandbox.register.util;


import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientFilter;

import java.util.GregorianCalendar;
import java.util.Objects;

public class SqlProvider {
    public static String myMethod(ClientFilter clientFilter) {
        SQL sql = new SQL();
        sql.select("c.id, c.name, c.surname, c.patronymic, ch.name charmName, coalesce((current_date-c.birth_date)/365, 0) age");
        sql.select("coalesce(sum(c_acc.money), 0.0) commonMoney, coalesce(MAX(c_acc.money), 0.0) maxMoney, coalesce(MIN(c_acc.money), 0.0) minMoney");
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
        if (Objects.equals(clientFilter.sort, "name")) {
            if(clientFilter.order==false){
                sql.order_by("name asc");
            }
            else{
                sql.order_by("name desc");
            }
        }
        else if(Objects.equals(clientFilter.sort, "id")){
            if(clientFilter.order==false){
                sql.order_by("id asc");
            }
            else{
                sql.order_by("id desc");
            }
        }else if(Objects.equals(clientFilter.sort, "commonMoney")){
            if(clientFilter.order==false){
                sql.order_by("commonMoney asc");
            }
            else{
                sql.order_by("commonMoney desc");
            }
        }else if(Objects.equals(clientFilter.sort, "maxMoney")){
            if(clientFilter.order==false){
                sql.order_by("maxMoney asc");
            }
            else{
                sql.order_by("maxMoney desc");
            }
        }else if(Objects.equals(clientFilter.sort, "minMoney")){
            if(clientFilter.order==false){
                sql.order_by("minMoney asc");
            }
            else{
                sql.order_by("minMoney desc");
            }
        }else if(Objects.equals(clientFilter.sort, "age")){
            if(clientFilter.order==false){
                sql.order_by("age asc");
            }
            else{
                sql.order_by("age desc");
            }
        }
        sql.limit("5");
        sql.offset("#{page}*5");
        return sql.toString();
    }
}
class O {
    public static void main(String[] args) {
        ClientFilter f = new ClientFilter();
        f.sort="CommonMoney";
        String s = SqlProvider.myMethod(f);
        System.out.println(s);
        GregorianCalendar d1 = new GregorianCalendar(2350, 05, 01);
        System.out.println(d1.getTime());
    }
}