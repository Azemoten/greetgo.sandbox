import {Gender} from "./Gender";
import {Character} from "./Character";
import {Address} from "./Address";
import {Phone} from "./Phone";
import {ClientDetail} from "./ClientDetail";

export class ClientToSave{
  public clientID: number/*long*/;
  public surname: string;
  public name: string;
  public patronymic:string ;
  public age: number/*int*/;
  public gender: Gender = new Gender();
  public birthDay: Date;
  public character: Character = new Character();
  public actualAddress: Address = new Address();
  public registrationAddress: Address = new Address();
  public phones: Phone[];



  static create(clientDetail: ClientDetail): ClientToSave {
    //debugger;
    const ret = new ClientToSave();
    ret.assign(clientDetail);
    return ret;
  }
  assign(a: ClientDetail) {
    if(a.clientID)
      this.clientID = a.clientId;
    if(a.surname)
      this.surname = a.surname;
    if(a.name)
      this.name = a.name;
    if(a.age)
      this.age = a.age;
    if(a.patronymic)
      this.patronymic = a.patronymic;
    if(a.gender)
      this.gender = a.gender;
    if(a.birthDay)
      this.birthDay = a.birthDay;
    if(a.character)
      this.character = a.character;
    if(a.actualAddress)
      this.actualAddress = a.actualAddress;
    if(a.registrationAddress)
      this.registrationAddress = a.registrationAddress;
    if(a.phones)
      this.phones = a.phones;
  }
}
