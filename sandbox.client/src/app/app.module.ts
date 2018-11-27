import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppComponent} from './app.component';
import {HttpService} from "./http.service";
import {HttpClientModule} from "@angular/common/http";
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {ClientListComponent} from './client-list/client-list.component';
import {AboutComponent} from './about/about.component';
import {EditClientComponent} from './edit-client/edit-client.component';
import {AddClientComponent} from './add-client/add-client.component';
import {ClientService} from "./service/client.service";
import {routing} from "./app.routing";

import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {MatButtonModule, MatCheckboxModule, MatDialogModule, MatTableModule} from '@angular/material';

@NgModule({
  declarations: [
    AppComponent,
    ClientListComponent,
    AboutComponent,
    EditClientComponent,
    AddClientComponent
  ],
  entryComponents: [
    EditClientComponent,
    AddClientComponent
  ],
  imports: [
    routing,
    BrowserModule,
    HttpClientModule,
    FormsModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    MatButtonModule,
    MatCheckboxModule,
    MatTableModule,
    MatDialogModule

  ],
  providers: [HttpService, ClientService],
  bootstrap: [AppComponent]
})
export class AppModule {
}
