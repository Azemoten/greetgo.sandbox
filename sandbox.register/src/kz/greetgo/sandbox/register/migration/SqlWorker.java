package kz.greetgo.sandbox.register.migration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class SqlWorker {

  private Connection con;

  public SqlWorker(Connection con) {
    this.con = con;
  }

  String insertClient = "INSERT INTO public.client(surname, name, patronymic, gender, "
      + "birth_date, charm, cia_id) \n"
      + "select surname, name, patronymic, gender::gender, birth::date, charm, cia_id\n"
      + "from migration_client where status = 2;";

  String updateClient = "UPDATE client c\n"
      + "   SET surname=mc.surname, name=mc.name, patronymic=mc.patronymic, gender=mc.gender::gender, birth_date=mc.birth::date, \n"
      + "       charm=mc.charm\n"
      + "   from migration_client mc\n"
      + " WHERE mc.status=3 and c.cia_id=mc.cia_id;\n";

  String phoneSql = "insert into client_phone (client, number, type) \n"
      + "select c.id, mp.number, mp.type::phone from migration_phone mp \n"
      + "join client c on c.cia_id = mp.cia_id where mp.status = 2 and mp.actual = 1";

  String addressSql = "insert into client_addr (client, type, street, house, flat)\n"
      + "select c.id, ma.type::address, ma.street, ma.house, ma.flat\n"
      + "from migration_address ma join client c on c.cia_id=ma.cia_id where status=2 and ma.actual=1";

  String accountSql = "insert into client_account (client, money, number, registered_at, cia_id)\n"
      + "select c.id, 0.0, ma.account_number, ma.registered_at::timestamp, ma.cia_id\n"
      + "from client c join migration_account ma on ma.cia_id=c.cia_id\n"
      + "where ma.status=2";



  String transactionSql = "insert into client_account_transaction (account, money, finished_at, type)\n"
      + "       select ca.id, mt.money, mt.finished_at::timestamp, mt.transaction_id\n"
      + "         from migration_transaction mt join client_account ca on ca.number=mt.account_number where mt.status=2";

  public void migrClientsWithBody() throws SQLException {
    exec(insertClient);
    exec(phoneSql);
    exec(addressSql);
    exec(updateClient);
  }


  public void migrTransactions() throws SQLException {
    exec(transactionSql);
  }

  public void migrAccounts() throws SQLException {
    exec(accountSql);
  }

  public int exec(String s, Date now) throws SQLException {
    PreparedStatement ps = con.prepareStatement(s);
    ps.setTimestamp(1, (Timestamp) now);
    return ps.executeUpdate();
  }

  public void exec(String s) throws SQLException {
    PreparedStatement ps = con.prepareStatement(s);
    ps.executeUpdate();
  }

  public Timestamp getTimestamp() throws SQLException {
    PreparedStatement ps = con.prepareStatement("select moment()");
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      return rs.getTimestamp(1);
    }

    return null;
  }
}
