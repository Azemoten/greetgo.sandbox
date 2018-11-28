import {Component, HostBinding, OnInit} from '@angular/core';
import {ClientService} from "../service/client.service";
import {Client} from "../../model/client";
import {Router} from "@angular/router";
import {MatDialog} from "@angular/material";
import {EditClientComponent} from "../edit-client/edit-client.component";
import {AddClientComponent} from "../add-client/add-client.component";
import {Filter} from "../../model/filter";
import {ConfirmationComponent} from "../confirmation/confirmation.component";

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {

  clients: Client[] = [];
  clicked = false;
  orders = [{'name': ['name', 'Имя']}, {'name': ['surname', 'Фамилия']}, {'name': ['tname', 'Отчество']}];
  selectedOrder = this.orders[0].name;

  @HostBinding('class.is-open')
  isOpen = false;

  constructor(public clientService: ClientService,
              public dialog: MatDialog,
              public dialogDelete: MatDialog) {
  }

  ngOnInit() {
    this.loadClients()
  }

  loadClients() {
    this.clientService.getClients(new Filter()).toPromise().then(
      data => {
        this.clients = data.body;
      });
  }

  sortBy(str: string) {
    if (this.clicked == false) {
      this.loadSorted(str, 'desc');
      this.clicked = true;
    } else {
      this.loadSorted(str, 'asc');
      this.clicked = false;
    }
  }

  loadSorted(sort: string, ord: string): void {
    this.clientService.getClients(new Filter(sort, ord)).toPromise().then(
      data => {
        this.clients = data.body;
      });
  }

  deleteClient(id: number): void {
    const dialogDel = this.dialogDelete.open(ConfirmationComponent, {
      data:id
    });
    dialogDel.afterClosed().subscribe( () =>{
      this.loadClients();
    })
    // const dialogRefer = this.dialogDelete.open(ConfirmationComponent, {
    //   data: id
    // });
    // dialogRefer.afterClosed().subscribe(() => {
    //   this.loadClients();
    // })

  }


  //TODO перименить по человечкски
  //TODO done
  filterBy(like: string) {
    this.clientService.getClients(new Filter("", "", this.selectedOrder[0], like)).toPromise().then(
      data => {
        this.clients = data.body;
      });
    if (!like) {
      this.loadClients();
    }
  }


  //TODO Убраьть локалСторедж!
  //TODO done
  editClient(id: number): void {
    const dialogRef = this.dialog.open(EditClientComponent, {
      data: id
    });
    dialogRef.afterClosed().subscribe(result => {
      this.loadClients();
    });
  }

  addClient() {
    const dialogRef = this.dialog.open(AddClientComponent, {});
    dialogRef.afterClosed().subscribe(result => {
      this.loadClients();
    });
  }


}

