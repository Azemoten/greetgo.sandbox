package kz.greetgo.sandbox.register.test.beans.develop;

import kz.greetgo.depinject.core.Bean;
import kz.greetgo.depinject.core.BeanGetter;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.register.ClientRegister;
import kz.greetgo.sandbox.register.beans.all.IdGenerator;
import kz.greetgo.sandbox.register.test.dao.AuthTestDao;
import kz.greetgo.sandbox.register.test.dao.ClientDaoTest;
import kz.greetgo.security.password.PasswordEncoder;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Bean
public class DbLoader {
    final Logger logger = Logger.getLogger(getClass());


    public BeanGetter<AuthTestDao> authTestDao;
    public BeanGetter<IdGenerator> idGenerator;
    public BeanGetter<PasswordEncoder> passwordEncoder;
    public BeanGetter<ClientRegister> clientRegister;
    public BeanGetter<ClientDaoTest> clientDaoTest;

    public void loadTestData() throws Exception {

        loadPersons();

        logger.info("FINISH");
    }

    @SuppressWarnings("SpellCheckingInspection")
    private void loadPersons() throws Exception {
        logger.info("Start loading persons...");
        Charm charm = new Charm();
        charm.name = "QWER";
        charm.id = 100;
        charm.description = "asd";
        charm.energy = 2.0;
        clientDaoTest.get().insertCharm(charm);
        client(100, "APush", 800.0, "AA", "", Gender.FEMALE, new SimpleDateFormat("yyyy-MM-dd").parse("2011-01-01"));
        client(102, "ABPush", 800.0, "BB", "", Gender.FEMALE, new SimpleDateFormat("yyyy-MM-dd").parse("2000-01-01"));
        client(103, "PushPush", 800.0, "CC", "", Gender.FEMALE, new SimpleDateFormat("yyyy-MM-dd").parse("2003-01-01"));
        client(104, "QPushC", 800.0, "DD", "", Gender.MALE, new SimpleDateFormat("yyyy-MM-dd").parse("2002-01-01"));
        client(105, "WWW", 700.0, "WEE", "", Gender.MALE, new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01"));
        client(106, "ASD as", 700.0, "qqq", "", Gender.MALE, new SimpleDateFormat("yyyy-MM-dd").parse("2001-01-01"));
        client(107, "qwer", 950.0, "rrr", "", Gender.MALE, new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01"));
        client(108, "zczx ", 756.0, "hhh", "", Gender.MALE, new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01"));
        client(109, "cxv", 666.2, "mmm", "", Gender.MALE, new SimpleDateFormat("yyyy-MM-dd").parse("1900-01-01"));


        logger.info("Finish loading persons");
    }

    private void client(int id, String surname, Double money, String name, String patronymic, Gender gender, Date birthDate) {
        Client client = new Client();
        ClientDetail clientDetail = new ClientDetail();
        ClientAccount clientAccount = new ClientAccount();
        client.id = id;
        client.surname = surname;
        client.name = name;
        client.patronymic = patronymic;
        client.gender = gender;
        client.birthDate = birthDate;
        client.charm = 100;
        clientDetail.client = client;
        clientAccount.client = id;
        clientAccount.money = money;
        clientAccount.number = "qweas asda asd ";
        clientDaoTest.get().insertClient(client);
//        clientDaoTest.get().insertAccount(clientAccount);
    }

    private void user(String fioStr, String birthDateStr, String accountName) throws Exception {
        String id = idGenerator.get().newId();
        String[] fio = fioStr.split("\\s+");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = sdf.parse(birthDateStr);
        String encryptPassword = passwordEncoder.get().encode("111");

        authTestDao.get().insertPerson(id, accountName, encryptPassword);
        authTestDao.get().updatePersonField(id, "birth_date", new Timestamp(birthDate.getTime()));
        authTestDao.get().updatePersonField(id, "surname", fio[0]);
        authTestDao.get().updatePersonField(id, "name", fio[1]);
        authTestDao.get().updatePersonField(id, "patronymic", fio[2]);
    }

    private void add_can(String username, UserCan... cans) {
        for (UserCan can : cans) {
            authTestDao.get().upsert(can.name());
            authTestDao.get().personCan(username, can.name());
        }
    }
}
