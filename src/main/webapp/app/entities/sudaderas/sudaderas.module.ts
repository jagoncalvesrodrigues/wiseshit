import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { SudaderasComponent } from './list/sudaderas.component';
import { SudaderasDetailComponent } from './detail/sudaderas-detail.component';
import { SudaderasUpdateComponent } from './update/sudaderas-update.component';
import { SudaderasDeleteDialogComponent } from './delete/sudaderas-delete-dialog.component';
import { SudaderasRoutingModule } from './route/sudaderas-routing.module';

@NgModule({
  imports: [SharedModule, SudaderasRoutingModule],
  declarations: [SudaderasComponent, SudaderasDetailComponent, SudaderasUpdateComponent, SudaderasDeleteDialogComponent],
  entryComponents: [SudaderasDeleteDialogComponent],
})
export class SudaderasModule {}
