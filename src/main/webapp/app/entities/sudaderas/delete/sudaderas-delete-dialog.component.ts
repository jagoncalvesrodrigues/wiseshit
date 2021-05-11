import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ISudaderas } from '../sudaderas.model';
import { SudaderasService } from '../service/sudaderas.service';

@Component({
  templateUrl: './sudaderas-delete-dialog.component.html',
})
export class SudaderasDeleteDialogComponent {
  sudaderas?: ISudaderas;

  constructor(protected sudaderasService: SudaderasService, public activeModal: NgbActiveModal) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.sudaderasService.delete(id).subscribe(() => {
      this.activeModal.close('deleted');
    });
  }
}
