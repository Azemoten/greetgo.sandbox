package kz.greetgo.sandbox.register.migration;

import java.sql.Connection;
import java.sql.DriverManager;
import kz.greetgo.conf.ConfData;

public class ConnectionToDB {
  public Connection getConnection() throws Exception {
    ConfData cd = new ConfData();
    cd.readFromFile(System.getProperty("user.home")+"/sandbox.d/conf/DbConfig.hotconfig");

    Class.forName("org.postgresql.Driver");
    String res = cd.str("url")+" "+ cd.str("username")+" "+ cd.str("password");

    return DriverManager.getConnection(cd.str("url"), cd.str("username"), cd.str("password"));

  }
}
