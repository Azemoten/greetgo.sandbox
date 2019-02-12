package kz.greetgo.sandbox.register.migration;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

public class InMigrationWorker extends SqlWorker {

  private Connection con;

  //

  public InMigrationWorker(Connection con) {
    super(con);
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
    {
      migrClientsWithBody();
    }
    {
      migrAccounts();
      migrTransactions();
    }
    {
      finishMigration();
    }

    return true;


  }

  private void finishMigration() throws SQLException {
    markClients();
    markBodyOfClients();
    markAccounts();
    markTransactions();

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
      int countPhones = exec("update migration_phone set status =2"
                                 + " where status = 0 and inserted_at<= ?", now);
      int countAddrs = exec("update migration_address set status = 2 "
                                + "where status = 0 and inserted_at<= ?", now);
      if (countFrs + countСia + countFrsTransactions + countPhones +
          countAddrs == 0) {
        return false;
      }
    }
    {//fix dublicates and old datas
      exec("update migration_client mc\n"
               + "set status = case when mc.id in (select max(id) as maxId\n"
               + "                                 from migration_client\n"
               + "                                 group by cia_id)\n"
               + "  then 2\n"
               + "             else -1 end"
               + " where mc.status=2");
    }
    {//charm updating for migration_client
      exec("with row1 as (select DISTINCT(charm_text) from migration_client),\n"
               + "               row2 as (insert into charm(name) select row1.charm_text \n"
               + "               from row1 on conflict(name) do \n"
               + "               update SET name=EXCLUDED.name returning id, name)\n"
               + "               update migration_client mc set charm=row2.id \n"
               + "               from row2 where mc.status=2 and mc.charm_text=row2.name");
    }

    {// clients for update
      exec("update migration_client mc\n"
               + "set status = case when mc.id in (select max(id) as maxId\n"
               + "                                 from migration_client\n"
               + "                                 group by cia_id)\n"
               + "  then 3\n"
               + "             else -1 end\n"
               + "from client c where mc.cia_id = c.cia_id and status=2");
    }
    {//Account whithout client
      exec("update migration_account as mac set status = case \n"
               + "when exists (select 1 from client c where c.cia_id=mac.cia_id) or exists (select 1 from migration_client mc where mc.cia_id=mac.cia_id)\n"
               + "then 2 else 4 end \n"
               + "where mac.status=2 or mac.status=4");
    }
    {//transaction without account
      exec("update migration_transaction as mt set status = ma.status from migration_account ma"
               + " where ma.account_number=mt.account_number");
    }
    {
      exec("with mtt as (select distinct(transaction_type) from migration_transaction),\n"
               + "tt as (insert into transaction_type(name) select mtt.transaction_type from mtt\n"
               + "                         on conflict(name) do\n"
               + "update SET name=EXCLUDED.name returning id, name)\n"
               + "update migration_transaction mt set transaction_id = tt.id "
               + "from tt where tt.name=mt.transaction_type and mt.status=2");
    }


    return true;
  }

  private void markBodyOfClients() throws SQLException {
    exec("update migration_phone set status = status+10 where status=2");
    exec("update migration_address set status = status+10 where status=2");
  }

  private void markTransactions() throws SQLException {
    exec("update migration_transaction set status = status+10 where status=2");
  }

  private void markAccounts() throws SQLException {
    exec("update migration_account set status = status+10 where status=2 ");
  }


  private void markClients() throws SQLException {
    exec("update migration_client set status = status+10 where status=2 or status=3");
  }


}