import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { SudaderasComponent } from './list/sudaderas.component';
import { SudaderasDetailComponent } from './detail/sudaderas-detail.component';
import { SudaderasUpdateComponent } from './update/sudaderas-update.component';
import { SudaderasDeleteDialogComponent } from './delete/sudaderas-delete-dialog.component';
import { SudaderasRoutingModule } from './route/sudaderas-routing.module';
import { FormsModule } from '@angular/forms';
import { SudaderasFilterPipe } from './list/sudaderas-filter.pipe';

@NgModule({
  imports: [SharedModule, SudaderasRoutingModule, FormsModule],
  declarations: [
    SudaderasComponent,
    SudaderasDetailComponent,
    SudaderasUpdateComponent,
    SudaderasDeleteDialogComponent,
    SudaderasFilterPipe,
  ],
  entryComponents: [SudaderasDeleteDialogComponent],
})
export class SudaderasModule {}
