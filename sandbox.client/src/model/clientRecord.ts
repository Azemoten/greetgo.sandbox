export class ClientRecord {
  public id: number;
  public surname: string;
  public name: string;
  public patronymic: string;
  public charmName: string;
  public age: any;
  public commonMoney: any;
  public minMoney: any;
  public maxMoney: string;

  public toJson(a: any) {
    let js = {
      id: a.id, surname: a.surname, name: a.name, patronymic: a.patronymic, charmName: a.charmName,
      minMoney: a.minMoney, maxMoney: a.maxMoney, age: a.age, commonMoney: a.commonMoney
    };
    return js;
  }

}
