import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IAccesorios, Accesorios } from '../accesorios.model';
import { AccesoriosService } from '../service/accesorios.service';

@Injectable({ providedIn: 'root' })
export class AccesoriosRoutingResolveService implements Resolve<IAccesorios> {
  constructor(protected service: AccesoriosService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAccesorios> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((accesorios: HttpResponse<Accesorios>) => {
          if (accesorios.body) {
            return of(accesorios.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Accesorios());
  }
}
