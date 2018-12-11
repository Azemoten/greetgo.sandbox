import {Injectable} from '@angular/core';
import {HttpService} from "../http.service";
import {ClientFilter} from "../../model/clientFilter";
import {ClientDetails} from "../../model/client-details";
import {t} from "@angular/core/src/render3";

@Injectable({
  providedIn: 'root'
})
export class ClientService {

  //TODO Нужно использовать HtppService!
  //DONE
  constructor(private http: HttpService) {
  }


  //TODO объеденить лист в один метод и передовать обхект toFilter
  //TODO done
  getClients(filter = new ClientFilter()) {
    return this.http.get('/list', {});
  }

  getClientById(id: number) {
    return this.http.get("/clientDetails/" + id);
  }

  create(clientDetails: ClientDetails) {
    console.log("asd")
    return this.http.post("/create", {clientDetail: JSON.stringify(clientDetails)});
  }

  update(clientDetails: ClientDetails) {
    return this.http.post('/update', {clientDetail: JSON.stringify(clientDetails)});
  }

  deleteById(id: number) {
    return this.http.delete('/remove/' + id);
  }

  listCharms() {
    return this.http.get('/list/Charms');
  }
}
