export class Client {
  public id: number;
  public surname: string;
  public name: string;
  public tname: string;
  public charm: string;
  public min_money: any;
  public max_money: any;
  public birth_date: any;
  public address: string;

  public toJson(a: any) {
    let js = {
      id: a.id, surname: a.surname, name: a.name, tname: a.tname, charm: a.charm,
      min_money: a.min_money, max_money: a.max_money, birth_date: a.birth_date
    };
    return js;
  }

}
