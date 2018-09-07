package kz.greetgo.sandbox.controller.model;


/**
 * Created by msultanova on 9/4/18.
 */
public class ClientRecord {
    public String fio;
    public Character character;
    public int age;
    public int totalBalance;
    public int minBalance;
    public int maxBalance;
    public long clientId;

    public ClientRecord() {
    }

    public ClientRecord(String fio, Character character, int age, int totalBalance, int minBalance, int maxBalance, long clientId) {
        this.fio = fio;
        this.character = character;
        this.age = age;
        this.totalBalance = totalBalance;
        this.minBalance = minBalance;
        this.maxBalance = maxBalance;
        this.clientId = clientId;
    }
}
