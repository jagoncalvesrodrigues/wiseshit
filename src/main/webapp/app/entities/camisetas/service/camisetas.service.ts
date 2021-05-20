import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICamisetas, getCamisetasIdentifier } from '../camisetas.model';

export type EntityResponseType = HttpResponse<ICamisetas>;
export type EntityArrayResponseType = HttpResponse<ICamisetas[]>;

@Injectable({ providedIn: 'root' })
export class CamisetasService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/camisetas');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(camisetas: ICamisetas): Observable<EntityResponseType> {
    return this.http.post<ICamisetas>(this.resourceUrl, camisetas, { observe: 'response' });
  }

  update(camisetas: ICamisetas): Observable<EntityResponseType> {
    return this.http.put<ICamisetas>(`${this.resourceUrl}/${getCamisetasIdentifier(camisetas) as number}`, camisetas, {
      observe: 'response',
    });
  }

  partialUpdate(camisetas: ICamisetas): Observable<EntityResponseType> {
    return this.http.patch<ICamisetas>(`${this.resourceUrl}/${getCamisetasIdentifier(camisetas) as number}`, camisetas, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICamisetas>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICamisetas[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addCamisetasToCollectionIfMissing(
    camisetasCollection: ICamisetas[],
    ...camisetasToCheck: (ICamisetas | null | undefined)[]
  ): ICamisetas[] {
    const camisetas: ICamisetas[] = camisetasToCheck.filter(isPresent);
    if (camisetas.length > 0) {
      const camisetasCollectionIdentifiers = camisetasCollection.map(camisetasItem => getCamisetasIdentifier(camisetasItem)!);
      const camisetasToAdd = camisetas.filter(camisetasItem => {
        const camisetasIdentifier = getCamisetasIdentifier(camisetasItem);
        if (camisetasIdentifier == null || camisetasCollectionIdentifiers.includes(camisetasIdentifier)) {
          return false;
        }
        camisetasCollectionIdentifiers.push(camisetasIdentifier);
        return true;
      });
      return [...camisetasToAdd, ...camisetasCollection];
    }
    return camisetasCollection;
  }

  // camisetasNotAvailable(req?: any): Observable<EntityArrayResponseType> {
  //   const options = createRequestOption(req);
  //   return this.http.get<ICamisetas[]>(`${this.resourceUrl}/notsoldCamiseta`, { params: options, observe: 'response' });
  // }
  // camisetasAvailable(req?: any): Observable<EntityArrayResponseType> {
  //   const options = createRequestOption(req);
  //   return this.http.get<ICamisetas[]>(`${this.resourceUrl}/soldCamiseta`, { params: options, observe: 'response' });
  // }
}
