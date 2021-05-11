import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IAccesorios, getAccesoriosIdentifier } from '../accesorios.model';

export type EntityResponseType = HttpResponse<IAccesorios>;
export type EntityArrayResponseType = HttpResponse<IAccesorios[]>;

@Injectable({ providedIn: 'root' })
export class AccesoriosService {
  public resourceUrl = this.applicationConfigService.getEndpointFor('api/accesorios');

  constructor(protected http: HttpClient, private applicationConfigService: ApplicationConfigService) {}

  create(accesorios: IAccesorios): Observable<EntityResponseType> {
    return this.http.post<IAccesorios>(this.resourceUrl, accesorios, { observe: 'response' });
  }

  update(accesorios: IAccesorios): Observable<EntityResponseType> {
    return this.http.put<IAccesorios>(`${this.resourceUrl}/${getAccesoriosIdentifier(accesorios) as number}`, accesorios, {
      observe: 'response',
    });
  }

  partialUpdate(accesorios: IAccesorios): Observable<EntityResponseType> {
    return this.http.patch<IAccesorios>(`${this.resourceUrl}/${getAccesoriosIdentifier(accesorios) as number}`, accesorios, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAccesorios>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAccesorios[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  addAccesoriosToCollectionIfMissing(
    accesoriosCollection: IAccesorios[],
    ...accesoriosToCheck: (IAccesorios | null | undefined)[]
  ): IAccesorios[] {
    const accesorios: IAccesorios[] = accesoriosToCheck.filter(isPresent);
    if (accesorios.length > 0) {
      const accesoriosCollectionIdentifiers = accesoriosCollection.map(accesoriosItem => getAccesoriosIdentifier(accesoriosItem)!);
      const accesoriosToAdd = accesorios.filter(accesoriosItem => {
        const accesoriosIdentifier = getAccesoriosIdentifier(accesoriosItem);
        if (accesoriosIdentifier == null || accesoriosCollectionIdentifiers.includes(accesoriosIdentifier)) {
          return false;
        }
        accesoriosCollectionIdentifiers.push(accesoriosIdentifier);
        return true;
      });
      return [...accesoriosToAdd, ...accesoriosCollection];
    }
    return accesoriosCollection;
  }
}
