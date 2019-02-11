package kz.greetgo.sandbox.register.migration;


import java.sql.Connection;


public class InMigration {
  public boolean execute() throws Exception {
    Connection con = new ConnectionToDB().getConnection();
    try{
      InMigrationWorker w = new InMigrationWorker(con);
      return w.migrate();
    } finally{
      con.close();
    }
  }

  public static void main(String[] args) throws Exception {
    new InMigration().execute();
  }


}
