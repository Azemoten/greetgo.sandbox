import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Router} from "@angular/router";
import {ClientService} from "../service/client.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {EditClientComponent} from "../edit-client/edit-client.component";

@Component({
  selector: 'app-add-client',
  templateUrl: './add-client.component.html',
  styleUrls: ['./add-client.component.css']
})
export class AddClientComponent implements OnInit {

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private clientService: ClientService,
              public dialogRef: MatDialogRef<EditClientComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) { }

  addForm: FormGroup;

  ngOnInit() {
    this.addForm = this.formBuilder.group({
      id: [],
      surname: ['', Validators.required],
      name: ['', Validators.required],
      tname: ['', Validators.required],
      charm: [],
      min_money: [],
      max_money: [],
      address: [],
      birth_date: []
    });
  }

  onSubmit(){
    this.clientService.createClient(this.addForm.value)
      .subscribe(data=> {
        this.exit();
      });
  }

  exit() {
    this.dialogRef.close();
  }
}
