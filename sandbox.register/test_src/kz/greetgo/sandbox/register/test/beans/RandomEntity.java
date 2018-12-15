package kz.greetgo.sandbox.register.test.beans;

import java.util.Calendar;
import java.util.Date;
import kz.greetgo.depinject.core.Bean;
import kz.greetgo.sandbox.controller.model.*;
import kz.greetgo.sandbox.controller.report.ReportRow;
import kz.greetgo.util.RND;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

@Bean
public class RandomEntity {


  public Client client(Charm charm) {
    Client client = new Client();
    client.id = RND.plusInt(1000000000);
    client.surname = RND.str(10);
    client.name = RND.str(10);
    client.patronymic = RND.str(10);
    client.gender = Gender.FEMALE;
    client.birthDate = new GregorianCalendar(2014, 4, 12).getTime();
    client.charm = charm.id;
    return client;
  }

  public Client client(GregorianCalendar date, Charm charm) {
    Client client = new Client();
    client.id = RND.plusInt(1000000000);
    client.surname = RND.str(10);
    client.name = RND.str(10);
    client.patronymic = RND.str(10);
    client.gender = Gender.FEMALE;
    client.birthDate = date.getTime();
    client.charm = charm.id;
    return client;
  }


  public Client client(String name, Charm charm) {
    Client client = new Client();
    client.id = RND.plusInt(1000000000);
    client.surname = RND.str(10);
    client.name = name;
    client.patronymic = RND.str(10);
    client.gender = Gender.FEMALE;
    client.birthDate = new GregorianCalendar(2014, 4, 12).getTime();
    client.charm = charm.id;
    return client;
  }


  public Charm charm() {
    Charm charm = new Charm();
    charm.id = RND.plusInt(1000000000);
    charm.description = RND.str(11);
    charm.energy = RND.plusDouble(0, 100);
    charm.name = RND.str(11);
    return charm;
  }

  public int numOfLastPage(int count) {
    while (count > 5) {
      count -= 5;
    }
    return count;
  }



  public ClientAccount account(Client client, Double money) {
    ClientAccount clientAccount = new ClientAccount();
    clientAccount.id = RND.plusInt(1000000000);
    clientAccount.client = client.id;
    clientAccount.money = money;
    clientAccount.number = RND.str(10);
    return clientAccount;
  }

  public List<ClientAddr> clientAddr(Client client) {
    List<ClientAddr> list = new ArrayList<>();
    ClientAddr clientAddr1 = new ClientAddr();
    ClientAddr clientAddr2 = new ClientAddr();
    clientAddr1.type = AddressType.FACT;
    clientAddr1.street = RND.str(10);
    clientAddr1.house = RND.str(4);
    clientAddr1.flat = RND.str(3);
    clientAddr1.client = client.id;
    clientAddr2.type = AddressType.REG;
    clientAddr2.street = RND.str(10);
    clientAddr2.house = RND.str(4);
    clientAddr2.flat = RND.str(3);
    clientAddr2.client = client.id;
    list.add(clientAddr1);
    list.add(clientAddr2);
    return list;
  }

  public List<ClientPhone> clientPhones(Client client) {
    List<ClientPhone> list = new ArrayList<>();
    ClientPhone clientPhone1 = new ClientPhone();
    ClientPhone clientPhone2 = new ClientPhone();
    ClientPhone clientPhone3 = new ClientPhone();
    clientPhone1.client = client.id;
    clientPhone1.number = RND.str(8);
    clientPhone1.type = PhoneType.HOME;
    clientPhone2.client = client.id;
    clientPhone2.number = RND.str(8);
    clientPhone2.type = PhoneType.MOBILE;
    clientPhone3.client = client.id;
    clientPhone3.number = RND.str(8);
    clientPhone3.type = PhoneType.WORK;
    list.add(clientPhone1);
    list.add(clientPhone2);
    list.add(clientPhone3);
    return list;
  }

  public int CurrYear(){
    Date date = new Date();
    Calendar cal = Calendar.getInstance();
    cal.setTime(date);
    return cal.get(Calendar.YEAR);
  }

  public List<ReportRow> reportRows() {
    List<ReportRow> reportRows = new ArrayList<>();
    ReportRow row1 = new ReportRow();
    row1.age=RND.plusInt(1000);
    ReportRow row2 = new ReportRow();
    ReportRow row3 = new ReportRow();
    ReportRow row4 = new ReportRow();
    ReportRow row5 = new ReportRow();
    return reportRows;
  }

  public Client client(Charm charm, int id) {
    Client client = new Client();
    client.surname = RND.str(10);
    client.name = RND.str(10);
    client.patronymic = RND.str(10);
    client.gender = Gender.FEMALE;
    client.birthDate = new GregorianCalendar(2014, 4, 12).getTime();
    client.charm = charm.id;
    return client;
  }

}
