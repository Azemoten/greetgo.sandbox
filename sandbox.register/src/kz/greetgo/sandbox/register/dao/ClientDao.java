package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.*;
import org.apache.ibatis.annotations.*;
import org.apache.ibatis.jdbc.SQL;

import java.util.List;

public interface ClientDao {
    @Select("select c.id, c.name, c.surname, c.patronymic, ch.name charm_name, (current_date-c.birth_date)/365 age, sum(c_acc.money) common_money, MAX(c_acc.money) max_money, MIN(c_acc.money) min_money \n" +
            "from client c inner join charm ch on c.charm = ch.id left join client_account c_acc on c.id = c_acc.client \n" +
            "where c.actual=true and (LOWER(c.name) like LOWER(#{name}) || '%' \n" +
            "and LOWER(c.surname) like LOWER(#{surname}) || '%' \n" +
            "and LOWER(c.patronymic) like LOWER(#{patronymic}) || '%') group by c.id, ch.name order by ${sort} ${order}")
    List<ClientsDisplay> listClients(Filter filter);


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


    @Insert("insert into public.client(surname, name, patronymic, gender, birth_date, actual, charm)\n" +
            "    values (#{surname}, #{name}, #{patronymic}, 'MALE', '1997-07-01', true, 1)")
    void exCreate(Client client);

    @Select("select count(*) from client")
    Integer getClientListCount();
}

//class ClientSqlBuilder{
//    public String buildGetClientsByFilter(final String name, final String surname, final String patronymic){
//        String where ="";
//        return new SQL(){{
//            SELECT("*");
//            FROM("client");
//            if(name!=null){
//                where+="name like #{name} || '%'";
//            }
//        }}.toString();
//    }
//}
class Check{
    public static void main(String[] args) {
        String c = "select c.id, c.name, c.surname, c.patronymic, ch.name charm_name, (current_date-c.birth_date)/365 age, sum(c_acc.money) common_money, MAX(c_acc.money) max_money, MIN(c_acc.money) min_money \n" +
                "from client c inner join charm ch on c.charm = ch.id left join client_account c_acc on c.id = c_acc.client \n" +
                "where c.actual=true and (LOWER(c.name) like LOWER(#{name}) || '%' \n" +
                "and LOWER(c.surname) like LOWER(#{surname}) || '%' \n" +
                "and LOWER(c.patronymic) like LOWER(#{patronymic}) || '%') group by c.id, ch.name order by #{sort} ${order}";
        System.out.println(c);
    }
}