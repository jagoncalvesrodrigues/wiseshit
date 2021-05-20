import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'usuario',
        data: { pageTitle: 'wiseshitApp.usuario.home.title' },
        loadChildren: () => import('./usuario/usuario.module').then(m => m.UsuarioModule),
      },
      {
        path: 'camisetas',
        data: { pageTitle: 'wiseshitApp.camisetas.home.title' },
        loadChildren: () => import('./camisetas/camisetas.module').then(m => m.CamisetasModule),
      },
      {
        path: 'sudaderas',
        data: { pageTitle: 'wiseshitApp.sudaderas.home.title' },
        loadChildren: () => import('./sudaderas/sudaderas.module').then(m => m.SudaderasModule),
      },
      {
        path: 'accesorios',
        data: { pageTitle: 'wiseshitApp.accesorios.home.title' },
        loadChildren: () => import('./accesorios/accesorios.module').then(m => m.AccesoriosModule),
      },
      {
        path: 'venta',
        data: { pageTitle: 'wiseshitApp.venta.home.title' },
        loadChildren: () => import('./venta/venta.module').then(m => m.VentaModule),
      },
      {
        path: 'post',
        data: { pageTitle: 'wiseshitApp.post.home.title' },
        loadChildren: () => import('./post/post.module').then(m => m.PostModule),
      },

      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
