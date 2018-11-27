import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Client} from "../../model/client";
import {HttpService} from "../http.service";
import {Filter} from "../../model/filter";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  //TODO Нужно использовать HtppService!
  //DONE
  constructor(private http: HttpService,
              private httpP: HttpClient) { }


  //TODO объеденить лист в один метод и передовать обхект toFilter
  //TODO done
  getClients(filter = new Filter()){
    return this.http.get('users', filter.toJson(filter), 'json');
  }

  getClientById(id: number){
    return this.http.get("users/", {id:id}, 'json');
  }

  createClient(client: Client){
    return this.http.post("users", client.toJson(client), 'json');
  }

  updateClient(client: Client){
    return this.httpP.put('http://localhost:3000/users/'+client.id, client)
  }

  deleteById(id: number){
    return this.http.delete('users/'+id);
  }
}
