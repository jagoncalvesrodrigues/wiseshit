import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { SharedModule } from 'app/shared/shared.module';

import { VsudaderasComponent } from './vsudaderas.component';
import { VSUDADERA_ROUTE } from './vsudaderas.route';

@NgModule({
  declarations: [VsudaderasComponent],
  imports: [SharedModule, RouterModule.forChild([VSUDADERA_ROUTE])],
  exports: [],
})
export class VsudaderasModule {
  // constructor(){
  // }
  // ngOnInit(): void {
  //     throw new Error('Method not implemented.');
  // }
}
