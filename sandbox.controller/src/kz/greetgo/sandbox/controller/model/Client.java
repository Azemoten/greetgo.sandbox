package kz.greetgo.sandbox.controller.model;

import java.util.Date;

public class Client {
    public Integer id;
    public String name;
    public String surname;
    public String patronymic;
    public gender gender;
    public Date birthDate;
    public Integer charm;

    public enum gender {
        MALE, FEMALE
    }
}
