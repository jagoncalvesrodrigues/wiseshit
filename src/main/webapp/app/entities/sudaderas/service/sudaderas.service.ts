import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISudaderas, getSudaderasIdentifier } from '../sudaderas.model';

export type EntityResponseType = HttpResponse<ISudaderas>;
export type EntityArrayResponseType = HttpResponse<ISudaderas[]>;

@Injectable({ providedIn: 'root' })
export class SudaderasService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/sudaderas');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(sudaderas: ISudaderas): Observable<EntityResponseType> {
    return this.http.post<ISudaderas>(this.resourceUrl, sudaderas, { observe: 'response' });
  }

  update(sudaderas: ISudaderas): Observable<EntityResponseType> {
    return this.http.put<ISudaderas>(`${this.resourceUrl}/${getSudaderasIdentifier(sudaderas) as number}`, sudaderas, {
      observe: 'response',
    });
  }

  partialUpdate(sudaderas: ISudaderas): Observable<EntityResponseType> {
    return this.http.patch<ISudaderas>(`${this.resourceUrl}/${getSudaderasIdentifier(sudaderas) as number}`, sudaderas, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISudaderas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISudaderas[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addSudaderasToCollectionIfMissing(
    sudaderasCollection: ISudaderas[],
    // Rompemos la referencia con el private personajes para que no puedan entrar en el mediate el getter
    ...sudaderasToCheck: (ISudaderas | null | undefined)[]
  ): ISudaderas[] {
    const sudaderas: ISudaderas[] = sudaderasToCheck.filter(isPresent);
    if (sudaderas.length > 0) {
      const sudaderasCollectionIdentifiers = sudaderasCollection.map(sudaderasItem => getSudaderasIdentifier(sudaderasItem)!);
      const sudaderasToAdd = sudaderas.filter(sudaderasItem => {
        const sudaderasIdentifier = getSudaderasIdentifier(sudaderasItem);
        if (sudaderasIdentifier == null || sudaderasCollectionIdentifiers.includes(sudaderasIdentifier)) {
          return false;
        }
        sudaderasCollectionIdentifiers.push(sudaderasIdentifier);
        return true;
      });
      return [...sudaderasToAdd, ...sudaderasCollection];
    }
    return sudaderasCollection;
  }
  // sudaderasNotAvailable(req?: any): Observable<EntityArrayResponseType> {
  //   const options = createRequestOption(req);
  //   return this.http.get<ISudaderas[]>(`${this.resourceUrl}/notsoldSudadera`, { params: options, observe: 'response' });
  // }
  // sudaderasAvailable(req?: any): Observable<EntityArrayResponseType> {
  //   const options = createRequestOption(req);
  //   return this.http.get<ISudaderas[]>(`${this.resourceUrl}/soldSudadera`, { params: options, observe: 'response' });
  // }
}
