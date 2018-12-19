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
  lastPage: number;
  orderName: boolean = false;
  orderAge: boolean = false;
  orderMinMoney: boolean = false;
  orderMaxMoney: boolean = false;
  orderCommonMoney: boolean = false;
  pdfLinkForDownload: string;
  xlsxLinkForDownload: string;

  @HostBinding('class.is-open')
  isOpen = false;

  constructor(public clientService: ClientService,
              public dialog: MatDialog,
              public dialogDelete: MatDialog) {
  }

  ngOnInit() {
    this.loadClients();
    this.getNumberOfPage();
    this.changeLinkAfterFilter();
  }

  loadClients() {
    this.clientService.getClients(this.clientFilter).toPromise().then(
      data => {
        this.clients = data.body;
      });
  }

  editClient(id: number): void {
    const dialogRef = this.dialog.open(EditClientComponent, {
      data: id
    });
    dialogRef.afterClosed().subscribe(result => {
      this.paging(this.currentPage);
    });
  }


  deleteClient(id: number): void {
    const dialogDel = this.dialogDelete.open(ConfirmationComponent, {
      data: id
    });
    dialogDel.afterClosed().subscribe(() => {
      this.paging(this.currentPage);
    })
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

  createList(page: number) {
    let list = [];
    for (var i = 0; i < page + 1; i++) {
      list.push(i)
    }
    this.lastPage = list[list.length - 1];
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
    this.getNumberOfPage();
    this.currentPage = page;
    if (this.page[this.page.length - 1] < this.currentPage) {
      this.currentPage = this.page[this.page.length - 1]
    }
    this.clientFilter.page = this.currentPage;
    this.changeLinkAfterFilter();
    this.loadClients();
  }

  download(type: string) {
    let addToEndFilters = "";
    if (this.clientFilter.name) {
      addToEndFilters += "&name=" + this.clientFilter.name;
    }
    if (this.clientFilter.patronymic) {
      addToEndFilters += "&patronymic=" + this.clientFilter.patronymic;
    }
    if (this.clientFilter.surname) {
      addToEndFilters += "&surname=" + this.clientFilter.surname;
    }
    if (this.clientFilter.sort) {
      addToEndFilters += "&sort=" + this.clientFilter.sort +"&order="+this.clientFilter.order;
    }
    let href = "http://localhost:1313/sandbox/api/client/report/"+type+"?"+addToEndFilters;
    return href;
  }
  changeLinkAfterFilter(){
    this.pdfLinkForDownload = this.download("pdf");
    this.xlsxLinkForDownload = this.download("xlsx");
  }
}

