import {Component, HostBinding, OnInit} from '@angular/core';
import {ClientService} from "../service/client.service";
import {ClientRecord} from "../../model/clientRecord";
import {MatDialog} from "@angular/material";
import {EditClientComponent} from "../edit-client/edit-client.component";
import {ClientFilter} from "../../model/clientFilter";
import {ConfirmationComponent} from "../confirmation/confirmation.component";

@Component({
  selector: 'app-client-list',
  templateUrl: './client-list.component.html',
  styleUrls: ['./client-list.component.css']
})
export class ClientListComponent implements OnInit {

  clients: ClientRecord[] = [];
  clicked = false;
  clientFilter: ClientFilter = new ClientFilter();
  page: number[];
  currentPage: number = 0;
  orderName: boolean = false;
  orderAge: boolean = false;
  orderMinMoney: boolean = false;
  orderMaxMoney: boolean = false;
  orderCommonMoney: boolean = false;

  @HostBinding('class.is-open')
  isOpen = false;

  constructor(public clientService: ClientService,
              public dialog: MatDialog,
              public dialogDelete: MatDialog) {
  }

  ngOnInit() {
    this.loadClients();
    this.getNumberOfPage()
  }

  loadClients() {
    this.clientService.getClients(this.clientFilter).toPromise().then(
      data => {
        this.clients = data.body;
      });
  }

  sortByName() {
    this.clientFilter.sort = "name";
    if (this.orderName) {
      this.orderName = false;
    } else {
      this.orderName = true;
    }
    this.clientFilter.order = this.orderName;
    this.getNumberOfPage();
    this.paging(this.currentPage)
  }

  sortByAge() {
    this.clientFilter.sort = "age";
    if (this.orderAge) {
      this.orderAge = false;
    } else {
      this.orderAge = true;
    }
    this.clientFilter.order = this.orderAge;
    this.getNumberOfPage();
    this.paging(this.currentPage)
  }

  sortByMinMoney() {
    this.clientFilter.sort = "minMoney";
    if (this.orderMinMoney) {
      this.orderMinMoney = false;
    } else {
      this.orderMinMoney = true;
    }
    this.clientFilter.order = this.orderMinMoney;
    this.getNumberOfPage();
    this.paging(this.currentPage)
  }

  sortByMaxMoney() {
    this.clientFilter.sort = "maxMoney";
    if (this.orderMaxMoney) {
      this.orderMaxMoney = false;
    } else {
      this.orderMaxMoney = true;
    }
    this.clientFilter.order = this.orderMaxMoney;
    this.getNumberOfPage();
    this.paging(this.currentPage)
  }

  sortByCommonMoney() {
    this.clientFilter.sort = "commonMoney";
    if (this.orderCommonMoney) {
      this.orderCommonMoney = false;
    } else {
      this.orderCommonMoney = true;
    }
    this.clientFilter.order = this.orderCommonMoney;
    this.getNumberOfPage();
    this.paging(this.currentPage)
  }

  deleteClient(id: number): void {
    const dialogDel = this.dialogDelete.open(ConfirmationComponent, {
      data: id
    });
    dialogDel.afterClosed().subscribe(() => {
      this.loadClients();
    })

  }


  //TODO перименить по человечкски
  //TODO done
  filterByName(like: any) {
    this.clientFilter.name = like.target.value;
    this.getNumberOfPage();
    this.paging(this.currentPage)
  }

  filterBySurname(like: any) {
    this.clientFilter.surname = like.target.value;
    this.getNumberOfPage();
    this.paging(this.currentPage)
  }

  filterByPatronymic(like: any) {
    this.clientFilter.patronymic = like.target.value;
    this.getNumberOfPage();
    this.paging(this.currentPage)
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

  createList(page: number) {
    let list = [];
    for (var i = 0; i < page + 1; i++) {
      list.push(i)
    }
    return list;
  }

  getNumberOfPage() {
    this.clientService.numberOfPage(this.clientFilter).toPromise().then(
      data => {
        this.page = this.createList(data.body);
      }
    )
  }


  paging(page: number) {
    this.currentPage = page;
    this.clientFilter.page = this.currentPage;
    this.loadClients();
  }

}

