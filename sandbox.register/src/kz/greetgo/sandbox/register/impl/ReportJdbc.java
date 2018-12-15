package kz.greetgo.sandbox.register.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.sandbox.controller.report.ReportRow;
import kz.greetgo.sandbox.controller.register.ReportView;

public class ReportJdbc implements ConnectionCallback<Void> {

  private final ReportView view;
  private final String sql = "select concat(c.surname,' ', c.name) fio, ch.name charmName, "
      + "(current_date-c.birth_date)/365 age, sum(c_acc.money) commonMoney,"
      + " min(c_acc.money) minMoney, max(c_acc.money) maxMoney from client"
      + " c inner join charm ch on c.charm = ch.id left join client_account "
      + "c_acc on c_acc.client=c.id where c.actual=true group by c.id, c.surname, c.name, ch.name,"
      + " c.birth_date order by c.id asc";

  public ReportJdbc(ReportView view) {
    this.view = view;
  }

  @Override
  public Void doInConnection(Connection con) throws Exception {
    try (PreparedStatement ps = con.prepareStatement(sql)) {

      try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
          view.addRow(rsToRow(rs));
        }
      }
    }

    return null;
  }

  private ReportRow rsToRow(ResultSet rs) throws SQLException {
    ReportRow row = new ReportRow();
    row.fio = rs.getString("fio");
    row.charmName = rs.getString("charmName");
    row.maxMoney = rs.getDouble("maxMoney");
    row.minMoney = rs.getDouble("minMoney");
    row.commonMoney = rs.getDouble("commonMoney");
    row.age = rs.getInt("age");

    return row;
  }
}
