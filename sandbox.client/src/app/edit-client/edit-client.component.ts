import {Component, Inject, Input, OnInit} from '@angular/core';
import {ClientRecord} from "../../model/clientRecord";
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ClientService} from "../service/client.service";
import {first} from "rxjs/operators";
import {ClientListComponent} from "../client-list/client-list.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ClientSave} from "../../model/client-save";
import {Client} from "../../model/client";
import {Charm} from "../../model/charm";
import {ClientAddr} from "../../model/client-addr";
import {ClientPhone} from "../../model/client-phone";
import {Gender} from "../../model/gender.enum";
import {PhoneType} from "../../model/phone.enum";
import {AddressType} from "../../model/address.enum";

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

  editForm: FormGroup;
  clientSave: ClientSave;
  clientToSave: ClientSave;
  client: Client;
  clientPhone: ClientPhone[];
  clientAddr: ClientAddr[];

  ngOnInit() {
    let clientId = this.data;

    if (clientId) {
      this.clientService.getClientById(clientId).subscribe(
        data => {
          this.clientSave = data.body;
        }
      );
    }
    this.editForm = this.formBuilder.group({
      client: this.formBuilder.group({
        id: [''],
        name: ['', Validators.required],
        surname: [''],
        patronymic: [''],
        charm: [''],
        gender: [''],
        birthDate: ['']
      }),
      addrs: this.formBuilder.array([this.createAddr('REG'),
        this.createAddr('FACT')]),
      phones: this.formBuilder.array([this.createPhone('HOME'),
        this.createPhone('MOBILE'), this.createPhone('WORK')])
    });
    this.clientService.getClientById(clientId).toPromise().then(
      data=>{
        this.editForm.setValue(data)
      }
    );
    // this.clientService.getClientById(clientId)
    //   .subscribe(data => {
    //     this.editForm.setValue(data);
    //
    //   });

  }

  onSubmit() {
    this.clientToSave.client=this.editForm.value.client;
    this.clientToSave.phones=this.editForm.value.phones;
    this.clientToSave.addrs=this.editForm.value.addrs;

    this.clientService.updateClient(this.clientToSave)
    .pipe(first())
    .subscribe(
      data => {
        this.router.navigate(['list-client']);
      },
      error => {
        alert(error);
      }
    );


    this.close();
  }

  //TODO rename
  //TODO fixed
  close() {
    this.dialogRef.close();
  }

  createPhone(type: string) {
    return this.formBuilder.group({
      number: ['', Validators.required],
      type: [type, Validators.required]
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

  get addrs(): FormArray{
    return <FormArray>this.editForm.get('addrs');
  }

  get phones(): FormArray{
    return <FormArray>this.editForm.get('phones');
  }

}
