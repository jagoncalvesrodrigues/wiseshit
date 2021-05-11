import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ICamisetas, Camisetas } from '../camisetas.model';
import { CamisetasService } from '../service/camisetas.service';

@Injectable({ providedIn: 'root' })
export class CamisetasRoutingResolveService implements Resolve<ICamisetas> {
  constructor(protected service: CamisetasService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICamisetas> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((camisetas: HttpResponse<Camisetas>) => {
          if (camisetas.body) {
            return of(camisetas.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Camisetas());
  }
}
