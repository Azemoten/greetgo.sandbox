import {Injectable} from '@angular/core';
import {HttpService} from "../http.service";
import {ClientFilter} from "../../model/clientFilter";
import {ClientSave} from "../../model/client-save";
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
    return this.http.get('/getList', filter.toJson(filter));
  }

  getClientById(id: number) {
    return this.http.get("/getClientForEdit/" + id);
  }

  createClient(clientSave: ClientSave) {
    return this.http.post("/createClient", {
      client: JSON.stringify(clientSave.client),
      addrs: JSON.stringify(clientSave.addrs),
      phones: JSON.stringify(clientSave.phones)
    });
  }

  updateClient(clientSave: ClientSave) {
    return this.http.post('/updateClient', {
      client: JSON.stringify(clientSave.client),
      addrs: JSON.stringify(clientSave.addrs),
      phones: JSON.stringify(clientSave.phones)
    });
  }

  deleteById(id: number) {
    return this.http.delete('/deleteClient/' + id);
  }

  listCharms() {
    return this.http.get('/listCharms');
  }
}
