import {Component, Inject, Input, OnInit} from '@angular/core';
import {FormArray, FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ClientService} from "../service/client.service";
import {ClientListComponent} from "../client-list/client-list.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ClientDetails} from "../../model/client-details";
import {Client} from "../../model/client";
import {Address} from "cluster";
import {ClientAddr} from "../../model/client-addr";

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
  clientDetails: ClientDetails;
  client: Client = new Client();
  addr1 : ClientAddr = new ClientAddr();
  addr2 : ClientAddr = new ClientAddr();
  clientId: number;


  ngOnInit() {
    this.clientId = this.data;

    let ad = [];
    ad.push(
        this.formBuilder.group({
          type: [this.client.name, Validators.required],
          street: [this.client.name],
          house: [this.client.name],
          flat: [this.client.name]
        })
    );
    ad.push(
      this.formBuilder.group({
        type: [this.client.name, Validators.required],
        street: [this.client.name],
        house: [this.client.name],
        flat: [this.client.name]
      })
    );

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

      addrs: this.formBuilder.array(ad),
      phones: this.formBuilder.array([this.createPhone("FACT"),this.createPhone("REG"),this.createPhone("REG")])
    });


    if (this.clientId) {
      this.clientService.getClientById(this.clientId).toPromise().then(
         data => {
           this.client = data.body.client
           // this.editForm.get("client").setValue(data.body.client);
           // this.editForm.get("addrs").controls().get("0").setValue()
        });
    }



  }

  onSubmit() {
    if(this.clientId){
    this.clientService.update(this.editForm.value).toPromise().then(res => {
      console.log("response", res)
    }).catch(err => {
      console.error(err);
    });
    } else {
      this.clientService.create(this.editForm.value).toPromise().then(
        res=>{
          console.log("response", res)
        }
      ). catch( err =>{
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

  createPhone(type: string) {
    return this.formBuilder.group({
      number: ['asd', Validators.required],
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

}
