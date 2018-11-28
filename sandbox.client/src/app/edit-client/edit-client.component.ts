import {Component, Inject, Input, OnInit} from '@angular/core';
import {Client} from "../../model/client";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ClientService} from "../service/client.service";
import {first} from "rxjs/operators";
import {ClientListComponent} from "../client-list/client-list.component";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";

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

  client: Client;
  editForm: FormGroup;

  //Убрать локалстрораге
  //+
  ngOnInit() {
    let clientId = this.data;
    // if (!clientId) {
    //   alert("Invalid action.")
    //   this.router.navigate(['list-client']);
    //   return;
    // }
    this.clientService.getClientById(clientId).subscribe( data=>{
      this.client = data.body[0]
    });
    this.editForm = this.formBuilder.group({
      id: [],
      name: ['', Validators.required],
      surname: [],
      tname: [],
      charm: [],
      min_money: [],
      max_money: [],
      birth_date: []
    });
    this.clientService.getClientById(clientId)
      .subscribe(data => {
        this.editForm.setValue(data[0]);
      });
  }

  onSubmit() {
    this.clientService.updateClient(this.editForm.value)
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

}
