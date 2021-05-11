import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { ISudaderas, Sudaderas } from '../sudaderas.model';
import { SudaderasService } from '../service/sudaderas.service';

@Injectable({ providedIn: 'root' })
export class SudaderasRoutingResolveService implements Resolve<ISudaderas> {
  constructor(protected service: SudaderasService, protected router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ISudaderas> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        mergeMap((sudaderas: HttpResponse<Sudaderas>) => {
          if (sudaderas.body) {
            return of(sudaderas.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Sudaderas());
  }
}
