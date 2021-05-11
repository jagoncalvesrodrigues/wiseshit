import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { IAccesorios } from '../accesorios.model';
import { AccesoriosService } from '../service/accesorios.service';

@Component({
  templateUrl: './accesorios-delete-dialog.component.html',
})
export class AccesoriosDeleteDialogComponent {
  accesorios?: IAccesorios;

  constructor(protected accesoriosService: AccesoriosService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.accesoriosService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
