package kz.greetgo.sandbox.register.test.beans;

import java.sql.Timestamp;
import java.util.GregorianCalendar;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.AddressType;
import kz.greetgo.sandbox.controller.model.Client;
import kz.greetgo.sandbox.controller.model.ClientAccount;
import kz.greetgo.sandbox.controller.model.ClientAccountTransaction;
import kz.greetgo.sandbox.controller.model.ClientAddr;
import kz.greetgo.sandbox.controller.model.ClientPhone;
import kz.greetgo.sandbox.controller.model.Gender;
import kz.greetgo.sandbox.controller.model.PhoneType;
import kz.greetgo.util.RND;

@Bean
public class RandomMigrationEntity {

  public Client createClient() {
    Client client = new Client();
    client.ciaId = RND.str(10);
    client.name = RND.str(10);
    client.patronymic = RND.str(10);
    client.birthDate = new GregorianCalendar(2014, 4, 12).getTime();
    client.gender = Gender.MALE;
    client.surname = RND.str(10);
    client.charm = 1;

    return client;
  }

  public ClientAddr createAddr() {
    ClientAddr clientAddr = new ClientAddr();
    clientAddr.ciaId = RND.str(10);
    clientAddr.type = AddressType.FACT;
    clientAddr.street = RND.str(5);
    clientAddr.house = RND.str(5);
    clientAddr.flat = RND.str(5);
    return clientAddr;
  }

  public ClientPhone createPhone() {
    ClientPhone clientPhone = new ClientPhone();
    clientPhone.type = PhoneType.HOME;
    clientPhone.ciaId = RND.str(10);
    clientPhone.number = RND.str(12);
    return clientPhone;
  }

  public ClientAccount createAccount() {
    ClientAccount account = new ClientAccount();
    account.ciaId = RND.str(10);
    account.money = RND.plusDouble(1000.0, 10);
    account.registered_at = new Timestamp(RND.plusInt(500000000));
    account.number = RND.str(10);

    return account;

  }

  public ClientAccountTransaction createTransaction() {
    ClientAccountTransaction transaction = new ClientAccountTransaction();
    transaction.accountNumber = RND.str(10);
    transaction.finishedAt = new GregorianCalendar(2014, 4, 12).getTime();
    transaction.money = RND.plusDouble(1000, 100);
    transaction.type = 1;

    return transaction;
  }
}
