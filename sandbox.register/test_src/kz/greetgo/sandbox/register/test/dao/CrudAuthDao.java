package kz.greetgo.sandbox.register.test.dao;

import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.register.impl.GetRegisterImplTest;

import java.time.LocalDate;

public interface CrudAuthDao {
    void insertCharm(Integer charm_id, String name, String description, float energy);

    Client getClientById(Integer id);

    void insertClient(Integer id, String surname, String name, String patronymic, Client.gender sex, LocalDate birth_date, Integer charm_id);
}
