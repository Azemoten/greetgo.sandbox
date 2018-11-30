package kz.greetgo.sandbox.controller.model;

import java.time.LocalDate;

public class Client {
public Integer id;
public String name;
public String surname;
public String patronymic;
public static gender gender;
public LocalDate  birthDate;
public Integer charm;

public enum gender{
    MALE, FEMALE
}
}
