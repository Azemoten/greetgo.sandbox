import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material";
import {ClientService} from "../service/client.service";

@Component({
  selector: 'app-confirmation',
  templateUrl: './confirmation.component.html',
  styleUrls: ['./confirmation.component.css']
})
export class ConfirmationComponent implements OnInit {

  constructor(private clientService: ClientService,
              public dialogDel: MatDialogRef<ConfirmationComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any) {
  }

  ngOnInit() {
  }

  deleteClient() {
    this.clientService.deleteById(this.data).subscribe(
      () => {
        this.close();
      }
    )

  }

  close() {
    this.dialogDel.close();
  }

}
