package kz.greetgo.sandbox.controller.model;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;

public class ClientRecord {
  public String fio;
  public Character character;
  public int age;
  public float totalBalance;
  public float minBalance;
  public float maxBalance;
  public int clientId;

  public ClientRecord() {
  }

  public ClientRecord convertToSaveToClientRecord(ClientToSave toSave) {
    this.fio = toSave.surname + " " + toSave.name;
    if (toSave.patronymic != null)
      this.fio += " " + toSave.patronymic;
    LocalDate currentDate = LocalDate.now();
    LocalDate birthDate = toSave.birthDay.toInstant()
      .atZone(ZoneId.systemDefault())
      .toLocalDate();
    if ((birthDate != null) && (currentDate != null)) {
      this.age = Period.between(birthDate, currentDate).getYears();
    } else {
      this.age = 0;
    }

    this.character = toSave.character;
    this.clientId = toSave.clientID;
    return this;

  }
}
