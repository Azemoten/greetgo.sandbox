package kz.greetgo.sandbox.controller.model;

import java.sql.Timestamp;
import java.util.Date;

public class ClientAccountTransaction{
  public Integer id;
  public Integer account;
  public Double money;
  public Date finishedAt;
  public Integer type;
  public String accountNumber;
}
