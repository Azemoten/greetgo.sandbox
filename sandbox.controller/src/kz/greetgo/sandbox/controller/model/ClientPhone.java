package kz.greetgo.sandbox.controller.model;

public class ClientPhone {
    public Integer client;
    public String number;
    public phoneType type;

    public enum phoneType{
        HOME, WORK, MOBILE
    }
}
