package kz.greetgo.sandbox.controller.model;

import java.time.LocalDate;
import java.util.List;

public  class ClientSave {
    public String surname;
    public String name;
    public String patronymic;
    public Gender gender;
    public LocalDate birthDate;
    public Integer charm;
    public List<ClientAddr> addrs;
    public List<ClientPhone> phones;
}
