import { NgModule } from '@angular/core';

import { SharedModule } from 'app/shared/shared.module';
import { AccesoriosComponent } from './list/accesorios.component';
import { AccesoriosDetailComponent } from './detail/accesorios-detail.component';
import { AccesoriosUpdateComponent } from './update/accesorios-update.component';
import { AccesoriosDeleteDialogComponent } from './delete/accesorios-delete-dialog.component';
import { AccesoriosRoutingModule } from './route/accesorios-routing.module';

@NgModule({
  imports: [SharedModule, AccesoriosRoutingModule],
  declarations: [AccesoriosComponent, AccesoriosDetailComponent, AccesoriosUpdateComponent, AccesoriosDeleteDialogComponent],
  entryComponents: [AccesoriosDeleteDialogComponent],
})
export class AccesoriosModule {}
