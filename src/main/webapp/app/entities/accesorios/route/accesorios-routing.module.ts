import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AccesoriosComponent } from '../list/accesorios.component';
import { AccesoriosDetailComponent } from '../detail/accesorios-detail.component';
import { AccesoriosUpdateComponent } from '../update/accesorios-update.component';
import { AccesoriosRoutingResolveService } from './accesorios-routing-resolve.service';

const accesoriosRoute: Routes = [
  {
    path: '',
    component: AccesoriosComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AccesoriosDetailComponent,
    resolve: {
      accesorios: AccesoriosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AccesoriosUpdateComponent,
    resolve: {
      accesorios: AccesoriosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AccesoriosUpdateComponent,
    resolve: {
      accesorios: AccesoriosRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(accesoriosRoute)],
  exports: [RouterModule],
})
export class AccesoriosRoutingModule {}
