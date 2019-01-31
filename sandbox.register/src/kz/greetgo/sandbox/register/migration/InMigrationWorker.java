package kz.greetgo.sandbox.register.migration;

import java.sql.Connection;
import java.util.Date;

public class InMigrationWorker {
  private Connection con;
  public InMigrationWorker(Connection con) {
    this.con = con;
  }


  public boolean migrate() {

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
    {
      migr();
    }

    return true;


  }

  private boolean checkAnyDataToMigrateAndPrepareMigration() {
    Date now = getTimestamp("select moment()");

    return false;
  }

  private Date getTimestamp(String s) {
  return new Date();
  }

  private void migr() {
  }
}
