package kz.greetgo.sandbox.controller.model;

import java.sql.Timestamp;

public class ClientAccountTransaction {
    public Integer id;
    public Integer account;
    public Float money;
    public Timestamp finished_at;
    public Integer type;
}
