package kz.greetgo.sandbox.register.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import kz.greetgo.db.ConnectionCallback;
import kz.greetgo.sandbox.controller.model.ClientFilter;
import kz.greetgo.sandbox.controller.report.ReportRow;
import kz.greetgo.sandbox.controller.register.ReportView;
import kz.greetgo.sandbox.register.util.SqlProvider;

public class ReportJdbc implements ConnectionCallback<Void> {

  private final ReportView view;
  private String sql = null;

  public ReportJdbc(ReportView view, ClientFilter clientFilter) {
    this.view = view;
    this.sql = SqlProvider.getClientForJDBC(clientFilter).toString();
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
    row.name = rs.getString("name");
    row.surname = rs.getString("surname");
    row.patronymic = rs.getString("patronymic");
    row.charmName = rs.getString("charmName");
    row.maxMoney = rs.getDouble("maxMoney");
    row.minMoney = rs.getDouble("minMoney");
    row.commonMoney = rs.getDouble("commonMoney");
    row.age = rs.getInt("age");

    return row;
  }
}
