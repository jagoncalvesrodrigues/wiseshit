import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { SudaderasComponent } from '../list/sudaderas.component';
import { SudaderasDetailComponent } from '../detail/sudaderas-detail.component';
import { SudaderasUpdateComponent } from '../update/sudaderas-update.component';
import { SudaderasRoutingResolveService } from './sudaderas-routing-resolve.service';

const sudaderasRoute: Routes = [
  {
    path: '',
    component: SudaderasComponent,
    data: {
      defaultSort: 'id,asc',
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: SudaderasDetailComponent,
    resolve: {
      sudaderas: SudaderasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: SudaderasUpdateComponent,
    resolve: {
      sudaderas: SudaderasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: SudaderasUpdateComponent,
    resolve: {
      sudaderas: SudaderasRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(sudaderasRoute)],
  exports: [RouterModule],
})
export class SudaderasRoutingModule {}
