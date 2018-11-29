package kz.greetgo.sandbox.register.impl;

import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.register.dataForTest.ClientData;
import kz.greetgo.sandbox.register.test.dao.CrudAuthDao;
import kz.greetgo.sandbox.register.test.util.ParentTestNg;
import kz.greetgo.util.RND;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.util.Random;

import static org.fest.assertions.api.Assertions.assertThat;

public class GetRegisterImplTest extends ParentTestNg {

    Random rnd = new Random();
    int min = 100000;
    int max = 100000000;
    public BeanGetter<CrudAuthDao> crudAuthDao;

    public enum Gender {
        MALE, FEMALE
    }

    @Test
    public void getClient() {
        //Data
        //Client
        Integer id = min + rnd.nextInt(max - min + 1);
        String surname = RND.str(10);
        String name = RND.str(10);
        String patronymic = RND.str(10);
        Client.gender sex = Client.gender.FEMALE;
        LocalDate birth_date = ClientData.createRandomDate(1800, 1900);
        //
        //creating character
        //
        Integer charm_id = min + rnd.nextInt(max - min + 1);
        String description = RND.str(10);
        float energy = ClientData.createFloatNumber(1, 10);
        //
        //charm insert
        //
        crudAuthDao.get().insertCharm(charm_id, name, description, energy);
        crudAuthDao.get().insertClient(id, surname, name, patronymic, sex, birth_date, charm_id);
        //insert client
        //
        Client client = crudAuthDao.get().getClientById(id);
        //
        //
        assertThat(client).isNotNull();
        assertThat(client.name).isEqualTo(name);
        assertThat(client.charm_id).isEqualTo(charm_id);
        assertThat(client.birth_date).isEqualTo(birth_date);
        assertThat(client.sex).isEqualTo(sex);
    }
}
