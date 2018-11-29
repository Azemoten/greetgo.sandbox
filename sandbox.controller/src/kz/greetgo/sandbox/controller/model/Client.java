package kz.greetgo.sandbox.controller.model;

import java.time.LocalDate;

public class Client {
    public int id;
    public String surname;
    public String name;
    public String patronymic;
    public gender sex;
    public LocalDate birth_date;
    public int charm_id;

    public enum gender{
        MALE, FEMALE
    }
}
