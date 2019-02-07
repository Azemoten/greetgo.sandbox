package kz.greetgo.sandbox.register.migration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class InMigrationWorker {

  private Connection con;
  String charmSql = "insert into charm (id, name) \n"
      + "select id, 'name'\n"
      + "from migration_charm where status=2";
  String clientSql =
      "insert into client (surname, name, patronymic, gender, birth_date, charm, cia_id) \n"
          + "select surname, name, patronymic, gender::gender, birth, charm, cia_id\n"
          + "from migration_client where status=2";
  String phoneSql = "insert into client_phone (client, number, type) \n"
      + "select c.id, mp.number, mp.type::phone from migration_phone mp \n"
      + "join client c on c.cia_id = mp.cia_id where mp.status = 2 and mp.actual = 1";
  String addressSql = "insert into client_addr (client, type, street, house, flat)\n"
      + "select c.id, ma.type::address, ma.street, ma.house, ma.flat\n"
      + "from migration_address ma join client c on c.cia_id=ma.cia_id where status=2";
  String accountSql = "insert into client_account (client, money, number, registered_at)\n"
      + "select c.id, 0.0, ma.account_number, ma.registered_at::timestamp\n"
      + "from client c join migration_account ma on ma.cia_id=c.cia_id\n"
      + "where ma.status=2";
  String transactionTypesSql = "insert into transaction_type (id, name)\n"
      + "select real_id, name from migration_transaction_type";
  String transactionSql = "";

  public InMigrationWorker(Connection con) {
    this.con = con;
  }


  public boolean migrate() throws SQLException {

    //
    //Preparation migration
    {
      boolean hasAnyDataToMigrate = checkAnyDataToMigrateAndPrepareMigration();
      if (!hasAnyDataToMigrate) {
        return false;
      }
    }

    //
    //Migration clients
    //
    {//CiaIntegration
      migrCharms();
      migrClientsWithBody();
    }
    {//FrsIntegration
      migrAccounts();
      migrTransactionType();
      migrTransactions();
    }
    {
      updateMigratedStatus();
    }

    return true;


  }

  private void updateMigratedStatus() {
//    exec();
//    exec();
//    exec();
//    exec();
    // exec();

  }

  private void migrClientsWithBody() throws SQLException {
    exec(clientSql);
    exec(phoneSql);
    exec(addressSql);
  }

  private void migrCharms() throws SQLException {
    exec(charmSql);
  }

  private void migrTransactionType() throws SQLException {
    exec(transactionTypesSql);
  }

  private void migrTransactions() throws SQLException {
    exec(transactionSql);
  }

  private void migrAccounts() throws SQLException {
    exec(accountSql);
  }

  private boolean checkAnyDataToMigrateAndPrepareMigration() throws SQLException {
    Timestamp now = getTimestamp();
    {
      int countСia = exec("update migration_client set status = 2 " +
                              " where status=0 and inserted_at <= ?", now);
      int countFrs = exec("update migration_account set status = 2 " +
                              " where status=0 and inserted_at <=?", now);
      int countFrsTransactions = exec("update migration_transaction set status=2 "
                                          + "where status=0 and inserted_at<=?", now);
      int countCharms = exec("update migration_charm set status = 2"
                                 + " where status=0 and inserted_at<= ?", now);
      int countPhones = exec("update migration_phone set status =2"
                                 + " where status = 0 and inserted_at<= ?", now);
      int countAddrs = exec("update migration_address set status = 2 "
                                + "where status = 0 and inserted_at<= ?", now);
      if (countFrs + countСia + countFrsTransactions + countCharms + countPhones +
          countAddrs == 0) {
        return false;
      }
    }

    {// clients for update
      exec("update migration_client as mc "
               + "set status = case"
               + " when inserted_at=(select max(inserted_at) from migration_client) then"
               + " 3 else -1"
               + " end"
               + " from client as c "
               + "where c.cia_id = mc.cia_id and  mc.status = 2 ");
    }
    {//Account whithout client
      exec("update migration_account as mac set status = case \n"
               + "when exists (select 1 from client c where c.cia_id=mac.cia_id) or exists (select 1 from migration_client mc where mc.cia_id=mac.cia_id)\n"
               + "then 2 else 4 end \n"
               + "where mac.status=2 or mac.status=4");
    }
    {//Transaction whithout account
      exec("update migration_transaction as mt set status = case\n"
               + " when exists (select 1 from client_account as ca where\n"
               + "  ca.number = mt.account_number) or exists \n"
               + "  (select 1 from migration_account as ma where ma.account_number=mt.account_number)\n"
               + "   then 2 else 4 end where status=2 or status=4");
    }

    return true;
  }

  private int exec(String s, Date now) throws SQLException {
    PreparedStatement ps = con.prepareStatement(s);
    ps.setTimestamp(1, (Timestamp) now);
    return ps.executeUpdate();
  }

  private void exec(String s) throws SQLException {
    PreparedStatement ps = con.prepareStatement(s);
    ps.executeUpdate();
  }

  private Timestamp getTimestamp() throws SQLException {
    PreparedStatement ps = con.prepareStatement("select moment()");
    ResultSet rs = ps.executeQuery();
    if (rs.next()) {
      return rs.getTimestamp(1);
    }

    return null;
  }


}
