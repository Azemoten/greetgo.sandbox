import {RouterModule, Routes} from '@angular/router';
import {ClientListComponent} from "./client-list/client-list.component";

const routes: Routes = [
  {path: 'list-client', component: ClientListComponent},
  {path: '', component: ClientListComponent}
];

export const routing = RouterModule.forRoot(routes);
