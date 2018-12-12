import {Component, Inject, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ClientService} from "../service/client.service";
import {ClientListComponent} from "../client-list/client-list.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {Client} from "../../model/client";
import {Charm} from "../../model/charm";
import {atLeastOne} from "../service/at-least-one";

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
  clientId: number;
  submitted = false;

  ngOnInit() {
    this.clientId = this.data;
    this.loadCharms();
    this.editForm = this.formBuilder.group({
      client: this.formBuilder.group({
        id: [''],
        name: ['', Validators.required],
        surname: ['', Validators.required],
        patronymic: [''],
        charm: ['', Validators.required],
        gender: ['', Validators.required],
        birthDate: ['', Validators.required],
      },),
      addrs: this.formBuilder.array([this.createAddrFact(), this.createAddrReg()]),
      phones: this.formBuilder.array([this.createPhone('HOME'), this.createPhone('WORK'), this.createPhone('MOBILE')])
    });


    if (this.clientId) {
      this.clientService.getClientById(this.clientId).toPromise().then(
        data => {
          this.editForm.get("client").setValue(data.body.client);
          let date = new Date(data.body.client.birthDate);
          let year = date.getUTCFullYear();
          let month = date.getUTCMonth() + 1;
          let day = date.getUTCDate();
          let stringDate = year + "-" + month + "-" + day;
          this.editForm.get(["client", "birthDate"]).setValue(stringDate);
          for (var i = 0; i < 2; i++) {
            if (data.body.addrs[i] != null) {
              this.editForm.get(['addrs', i, 'street']).setValue(data.body.addrs[i].street);
              this.editForm.get(['addrs', i, 'house']).setValue(data.body.addrs[i].house);
              this.editForm.get(['addrs', i, 'flat']).setValue(data.body.addrs[i].flat);
            }
          }
          for (var i = 0; i < 3; i++) {
            if (data.body.phones[i] != null) {
              this.editForm.get(['phones', i, 'type']).setValue(data.body.phones[i].type);
              this.editForm.get(['phones', i, 'number']).setValue(data.body.phones[i].number);
              console.log(this.editForm)
            }
          }
        });
    }
  }

  isAtLeastOnePhone() {
    let atLeastOnePhone;
    for (var i = 0; i < 3; i++) {
      atLeastOnePhone = atLeastOnePhone || this.editForm.get(['phones', i, 'number']).value != "";
    }
    return atLeastOnePhone;
  }

  onSubmit() {
    this.submitted = true;

    if (this.editForm.invalid || !this.isAtLeastOnePhone()) {
      return;
    }
    if (this.clientId) {
      this.clientService.update(this.editForm.value).toPromise().then().catch(err => {
        console.error(err);
      });
    } else {
      this.clientService.create(this.editForm.value).toPromise().then().catch(err => {
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
      type: [type,],
      number: ['',]
    });
  }

  createAddrReg() {
    return this.formBuilder.group({
      type: ['REG'],
      street: ['',],
      house: [''],
      flat: ['']
    });
  }

  createAddrFact() {
    return this.formBuilder.group({
      type: ['FACT'],
      street: ['', Validators.required],
      house: ['', Validators.required],
      flat: ['', Validators.required]
    });
  }

  check

  get name() {
    return this.editForm.get(["client", "name"]);
  }

  get surname() {
    return this.editForm.get(["client", "surname"]);
  }

  get gender() {
    return this.editForm.get(["client", "gender"]);
  }

  get birthDate() {
    return this.editForm.get(["client", "birthDate"]);
  }

  get charm() {
    return this.editForm.get(["client", "charm"]);
  }

  get addrs() {
    return this.editForm.get(["addrs", "0"]);
  }

}
