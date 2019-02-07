package kz.greetgo.sandbox.register.util;


import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientFilter;

import java.util.Objects;

public class SqlProvider {

  public static String getListClient(ClientFilter clientFilter) {
    SQL sql = new SQL();
    sql.select(
        "c.id, c.name, c.surname, c.patronymic, ch.name charmName, coalesce((current_date-c.birth_date)/365, 0) age");
    sql.select(
        "coalesce(sum(c_acc.money), 0.0) commonMoney, coalesce(MAX(c_acc.money), 0.0) maxMoney, coalesce(MIN(c_acc.money), 0.0) minMoney");
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
    endQuery(clientFilter, sql);
    sql.limit("5");
    sql.offset("#{page}*5");
    return sql.toString();
  }

  public static String getClientForJDBC(ClientFilter clientFilter) {
    SQL sql = new SQL();
    sql.select(
        "c.name, c.surname, c.patronymic, coalesce((current_date-c.birth_date)/365, 0) age ");
    sql.select(
        "coalesce(sum(c_acc.money), 0.0) commonMoney, coalesce(MAX(c_acc.money), 0.0) maxMoney, coalesce(MIN(c_acc.money), 0.0) minMoney, ch.name charmName ");
    sql.from("client c");
    sql.innerjoin("charm ch on c.charm = ch.id ");
    sql.leftjoin("client_account c_acc on c.id=c_acc.client ");
    sql.where("c.actual = true ");
    if (Objects.nonNull(clientFilter.name)) {
      sql.where("LOWER(c.name) like '%' || LOWER('" + clientFilter.name + "') || '%'");
    }
    if (Objects.nonNull(clientFilter.surname)) {
      sql.where("LOWER(c.surname) like '%' || LOWER('" + clientFilter.surname + "') || '%'");
    }
    if (Objects.nonNull(clientFilter.patronymic)) {
      sql.where("LOWER(c.patronymic) like '%' || LOWER('" + clientFilter.patronymic + "') || '%'");
    }
    sql.group_by("c.id, ch.name ");
    endQuery(clientFilter, sql);
    return sql.toString();
  }

  public static String numPage(ClientFilter clientFilter) {
    SQL sql = new SQL();
    sql.select("count(*)");
    sql.from("client c");
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
    return sql.toString();
  }

  public static String create(Client client) {
    SQL sql = new SQL();
    sql.insert_into("client");
    sql.values("name, surname, gender, birth_date, charm",
               "#{name}, #{surname}, #{gender}::gender, #{birthDate}, #{charm}");
    if (Objects.nonNull(client.patronymic)) {
      sql.values("patronymic", "#{patronymic}");
    }
    return sql.toString() + "RETURNING id";
  }

  public static void endQuery(ClientFilter clientFilter, SQL sql) {
    if (Objects.equals(clientFilter.sort, "name")) {
      if (!clientFilter.order) {
        sql.order_by("name asc");
      } else {
        sql.order_by("name desc");
      }
    } else if (Objects.equals(clientFilter.sort, "id")) {
      if (!clientFilter.order) {
        sql.order_by("id asc");
      } else {
        sql.order_by("id desc");
      }
    } else if (Objects.equals(clientFilter.sort, "commonMoney")) {
      if (!clientFilter.order) {
        sql.order_by("commonMoney asc");
      } else {
        sql.order_by("commonMoney desc");
      }
    } else if (Objects.equals(clientFilter.sort, "maxMoney")) {
      if (!clientFilter.order) {
        sql.order_by("maxMoney asc");
      } else {
        sql.order_by("maxMoney desc");
      }
    } else if (Objects.equals(clientFilter.sort, "minMoney")) {
      if (!clientFilter.order) {
        sql.order_by("minMoney asc");
      } else {
        sql.order_by("minMoney desc");
      }
    } else if (Objects.equals(clientFilter.sort, "age")) {
      if (!clientFilter.order) {
        sql.order_by("age asc");
      } else {
        sql.order_by("age desc");
      }
    }
  }

  //"insert into client_addr(client, type, street, house, flat) values(#{client}, #{type}::address, #{street}, #{house}, #{flat}) \n"
  //          +
  //          "on conflict(client, type) do update set (client, type, street, house, flat ) = (#{client}, #{type}::address, #{street}, #{house}, #{flat}) "
  public static String sqlInsertClient(String patronymic) {
    String result = "";
    SQL sql = new SQL();
    sql.insert_into("migration_client")
        .values("id, cia_id, name, surname, gender, birth, charm, status", "(select nextval('client_id_seq')),?, ?, ?, ?, ?::date, ?, ?");
    boolean nonNullPatronymic = Objects.nonNull(patronymic);
    if (nonNullPatronymic) {
      sql.values("patronymic", "?");
    }

    return sql.toString();
  }

}
class A{

  public static void main(String[] args) {
    System.out.println(SqlProvider.sqlInsertClient(null));
  }
}