package kz.greetgo.sandbox.controller.model;

public class ClientAddr {
    public Integer client;
    public addrType type;
    public String street;
    public String house;
    public String flat;


    public enum addrType{
        FACT, REG
    }
}
