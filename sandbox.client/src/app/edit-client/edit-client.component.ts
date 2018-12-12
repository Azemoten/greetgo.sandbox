import {Component, Inject, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ClientService} from "../service/client.service";
import {ClientListComponent} from "../client-list/client-list.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Client} from "../../model/client";
import {Charm} from "../../model/charm";

@Component({
  selector: 'app-edit-client',
  templateUrl: './edit-client.component.html',
  styleUrls: ['./edit-client.component.css']
})
export class EditClientComponent implements OnInit {

  @Input() clientList: ClientListComponent;

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private clientService: ClientService,
              public dialogRef: MatDialogRef<EditClientComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  charms: Charm[];
  editForm: FormGroup;
  client: Client = new Client();
  clientId: number;


  ngOnInit() {
    this.clientId = this.data;
    this.loadCharms();
    this.editForm = this.formBuilder.group({
      client: this.formBuilder.group({
        id: [this.client.id],
        name: [this.client.name, Validators.required],
        surname: [this.client.surname],
        patronymic: [this.client.patronymic],
        charm: [this.client.charm],
        gender: [this.client.gender],
        birthDate: [this.client.birthDate],
      }),
      addrs: this.formBuilder.array([this.createAddr('FACT'), this.createAddr('REG')]),
      phones: this.formBuilder.array([this.createPhone('HOME'), this.createPhone('WORK'), this.createPhone('MOBILE')])
    });


    if (this.clientId) {
      this.clientService.getClientById(this.clientId).toPromise().then(
        data => {
          this.editForm.get("client").setValue(data.body.client);
          for (var i = 0; i < 2; i++) {
            if(data.body.addrs[i]!=null) {
              this.editForm.get(['addrs', i, 'street']).setValue(data.body.addrs[i].street)
              this.editForm.get(['addrs', i, 'house']).setValue(data.body.addrs[i].house)
              this.editForm.get(['addrs', i, 'flat']).setValue(data.body.addrs[i].flat)
            }
            }
          for (var i = 0; i < 3; i++) {
            if(data.body.phones[i]!=null) {
              this.editForm.get(['phones', i, 'type']).setValue(data.body.phones[i].type)
              this.editForm.get(['phones', i, 'number']).setValue(data.body.phones[i].number)
              console.log(this.editForm)
            }
          }
        });
    }
  }

  onSubmit() {
    if(this.editForm.invalid){
      return;
    }
    if (this.clientId) {
      this.clientService.update(this.editForm.value).toPromise().then(
        () => {}).catch(err => {
        console.error(err);
      });
    } else {
      this.clientService.create(this.editForm.value).toPromise().then(
        () => {}
      ).catch(err => {
        console.error(err);
      })
    }

    this.close();
  }

  //TODO rename
  //TODO fixed
  close() {
    this.dialogRef.close();
  }

  loadCharms() {
    this.clientService.listCharms().toPromise().then(
      data => {
        this.charms = data.body;
        console.log(data.body);
      }
    )
  }

  createPhone(type: string) {
    return this.formBuilder.group({
      type: [type, Validators.required],
      number: ['', Validators.required]
    });
  }

  createAddr(type: string) {
    return this.formBuilder.group({
      type: [type, Validators.required],
      street: [''],
      house: [''],
      flat: ['']
    });
  }
  get errors(){
    return this.editForm.controls;
  }
}
