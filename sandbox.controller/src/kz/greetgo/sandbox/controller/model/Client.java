package kz.greetgo.sandbox.controller.model;

import java.time.LocalDate;
import java.time.Month;

public class Client {
public Integer id;
public String name;
public String surname;
public String patronymic;
public gender gender;
public LocalDate bDate;
public Integer charm;

public enum gender{
    MALE, FEMALE
}
}
