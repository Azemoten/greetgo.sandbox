package kz.greetgo.sandbox.register.dao;

import java.util.List;
import kz.greetgo.sandbox.controller.report.ReportRow;
import org.apache.ibatis.annotations.Select;

public interface ReportDao {
  @Select("select concat(c.surname,' ', c.name) fio, ch.name charmName, "
      + "(current_date-c.birth_date)/365 age, sum(c_acc.money) commonMoney,"
      + " min(c_acc.money) minMoney, max(c_acc.money) maxMoney from client"
      + " c inner join charm ch on c.charm = ch.id left join client_account "
      + "c_acc on c_acc.client=c.id group by c.surname, c.name, ch.name,"
      + " c.birth_date")
  List<ReportRow> list();
}

