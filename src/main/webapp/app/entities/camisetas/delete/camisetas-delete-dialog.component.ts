import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ICamisetas } from '../camisetas.model';
import { CamisetasService } from '../service/camisetas.service';

@Component({
  templateUrl: './camisetas-delete-dialog.component.html',
})
export class CamisetasDeleteDialogComponent {
  camisetas?: ICamisetas;

  constructor(protected camisetasService: CamisetasService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.camisetasService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
