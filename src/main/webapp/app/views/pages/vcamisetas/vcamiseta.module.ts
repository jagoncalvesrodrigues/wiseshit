import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';

import { VcamisetasComponent } from './vcamisetas.component';
import { RouterModule } from '@angular/router';
import { VCAMISETA_ROUTE } from './vcamisetas.route';

@NgModule({
  declarations: [VcamisetasComponent],
  imports: [SharedModule, RouterModule.forChild([VCAMISETA_ROUTE])],
  exports: [],
})
export class VCamisetasModule {
  // constructor(){
  // }
  // ngOnInit(): void {
  //     throw new Error('Method not implemented.');
  // }
}
