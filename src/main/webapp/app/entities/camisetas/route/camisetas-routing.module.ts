import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { CamisetasComponent } from '../list/camisetas.component';
import { CamisetasDetailComponent } from '../detail/camisetas-detail.component';
import { CamisetasUpdateComponent } from '../update/camisetas-update.component';
import { CamisetasRoutingResolveService } from './camisetas-routing-resolve.service';

const camisetasRoute: Routes = [
  {
    path: '',
    component: CamisetasComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: CamisetasDetailComponent,
    resolve: {
      camisetas: CamisetasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: CamisetasUpdateComponent,
    resolve: {
      camisetas: CamisetasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: CamisetasUpdateComponent,
    resolve: {
      camisetas: CamisetasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(camisetasRoute)],
  exports: [RouterModule],
})
export class CamisetasRoutingModule {}
