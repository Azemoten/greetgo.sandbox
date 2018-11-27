
import { RouterModule, Routes } from '@angular/router';
import {AddClientComponent} from "./add-client/add-client.component";
import {ClientListComponent} from "./client-list/client-list.component";
import {EditClientComponent} from "./edit-client/edit-client.component";

const routes: Routes = [
  { path: 'list-client', component: ClientListComponent},
  { path: '', component: ClientListComponent}
];

export const routing = RouterModule.forRoot(routes);
