package kz.greetgo.sandbox.register.dao;

import kz.greetgo.sandbox.controller.model.ClientsDisplay;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface ClientDao {
    @Select("select c.id, c.name, c.surname, c.patronymic, ch.name charm_name," +
            " (current_date-c.birth_date)/365 age, sum(c_acc.money) common_money, " +
            "MAX(c_acc.money) max_money, MIN(c_acc.money) min_money \n" +
            "from client c inner join charm ch on c.charm = ch.id" +
            " inner join client_account c_acc on c.id = c_acc.client" +
            " where c.actual=true group by c.id, c.name, c.surname, c.patronymic, ch.name")
    List<ClientsDisplay> listClients();


}
