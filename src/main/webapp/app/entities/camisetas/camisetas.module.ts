import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { CamisetasComponent } from './list/camisetas.component';
import { CamisetasDetailComponent } from './detail/camisetas-detail.component';
import { CamisetasUpdateComponent } from './update/camisetas-update.component';
import { CamisetasDeleteDialogComponent } from './delete/camisetas-delete-dialog.component';
import { CamisetasRoutingModule } from './route/camisetas-routing.module';

@NgModule({
  imports: [SharedModule, CamisetasRoutingModule],
  declarations: [CamisetasComponent, CamisetasDetailComponent, CamisetasUpdateComponent, CamisetasDeleteDialogComponent],
  entryComponents: [CamisetasDeleteDialogComponent],
})
export class CamisetasModule {}
