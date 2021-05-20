import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      // Metemos las rutas a las vistas
      {
        path: 'vcamisetas',
        data: { pageTitle: 'wiseshitApp.Camisetas' },
        loadChildren: () => import('./pages/vcamisetas/vcamiseta.module').then(m => m.VcamisetasModule),
      },
      {
        path: 'vsudaderas',
        data: { pageTitle: 'wiseshitApp.Sudaderas' },
        loadChildren: () => import('./pages/vsudaderas/vsudaderas.module').then(m => m.VsudaderasModule),
      },
    ]),
  ],
})
export class EntityRoutingModule {}
