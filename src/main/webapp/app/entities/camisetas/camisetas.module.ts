import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';

import { SharedModule } from 'app/shared/shared.module';
import { CamisetasComponent } from './list/camisetas.component';
import { CamisetasDetailComponent } from './detail/camisetas-detail.component';
import { CamisetasUpdateComponent } from './update/camisetas-update.component';
import { CamisetasDeleteDialogComponent } from './delete/camisetas-delete-dialog.component';
import { CamisetasRoutingModule } from './route/camisetas-routing.module';
import { FormsModule } from '@angular/forms';
import { CamisetasFilterPipe } from './list/camisetas-fiter.pipe';

@NgModule({
  imports: [SharedModule, CamisetasRoutingModule, FormsModule, HttpClientModule],
  declarations: [
    CamisetasComponent,
    CamisetasDetailComponent,
    CamisetasUpdateComponent,
    CamisetasDeleteDialogComponent,
    CamisetasFilterPipe,
  ],
  entryComponents: [CamisetasDeleteDialogComponent],
})
export class CamisetasModule {}
