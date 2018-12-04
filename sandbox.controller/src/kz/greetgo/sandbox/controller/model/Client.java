package kz.greetgo.sandbox.controller.model;

import java.time.LocalDate;
import java.time.Month;

public class Client {
public Integer id;
public String name;
public String surname;
public String patronymic;
public gender gender;
public LocalDate  birthDate;
public Integer charm;

public enum gender{
    MALE, FEMALE
}

    public static void main(String[] args) {
        LocalDate s = LocalDate.of(2012,2,02);
        System.out.println(s);
}
}
