import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IAccesorios, Accesorios } from '../accesorios.model';

import { AccesoriosService } from './accesorios.service';

describe('Service Tests', () => {
  describe('Accesorios Service', () => {
    let service: AccesoriosService;
    let httpMock: HttpTestingController;
    let elemDefault: IAccesorios;
    let expectedResult: IAccesorios | IAccesorios[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(AccesoriosService);
      httpMock = TestBed.inject(HttpTestingController);

      elemDefault = {
        id: 0,
        stock: 0,
        imagen: 'AAAAAAA',
        talla: 'AAAAAAA',
        color: 'AAAAAAA',
        coleccion: 0,
      };
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a Accesorios', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Accesorios()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Accesorios', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            stock: 1,
            imagen: 'BBBBBB',
            talla: 'BBBBBB',
            color: 'BBBBBB',
            coleccion: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should partial update a Accesorios', () => {
        const patchObject = Object.assign(
          {
            stock: 1,
            talla: 'BBBBBB',
            color: 'BBBBBB',
            coleccion: 1,
          },
          new Accesorios()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Accesorios', () => {
        const returnedFromService = Object.assign(
          {
            id: 1,
            stock: 1,
            imagen: 'BBBBBB',
            talla: 'BBBBBB',
            color: 'BBBBBB',
            coleccion: 1,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a Accesorios', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addAccesoriosToCollectionIfMissing', () => {
        it('should add a Accesorios to an empty array', () => {
          const accesorios: IAccesorios = { id: 123 };
          expectedResult = service.addAccesoriosToCollectionIfMissing([], accesorios);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(accesorios);
        });

        it('should not add a Accesorios to an array that contains it', () => {
          const accesorios: IAccesorios = { id: 123 };
          const accesoriosCollection: IAccesorios[] = [
            {
              ...accesorios,
            },
            { id: 456 },
          ];
          expectedResult = service.addAccesoriosToCollectionIfMissing(accesoriosCollection, accesorios);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Accesorios to an array that doesn't contain it", () => {
          const accesorios: IAccesorios = { id: 123 };
          const accesoriosCollection: IAccesorios[] = [{ id: 456 }];
          expectedResult = service.addAccesoriosToCollectionIfMissing(accesoriosCollection, accesorios);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(accesorios);
        });

        it('should add only unique Accesorios to an array', () => {
          const accesoriosArray: IAccesorios[] = [{ id: 123 }, { id: 456 }, { id: 57417 }];
          const accesoriosCollection: IAccesorios[] = [{ id: 123 }];
          expectedResult = service.addAccesoriosToCollectionIfMissing(accesoriosCollection, ...accesoriosArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const accesorios: IAccesorios = { id: 123 };
          const accesorios2: IAccesorios = { id: 456 };
          expectedResult = service.addAccesoriosToCollectionIfMissing([], accesorios, accesorios2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(accesorios);
          expect(expectedResult).toContain(accesorios2);
        });

        it('should accept null and undefined values', () => {
          const accesorios: IAccesorios = { id: 123 };
          expectedResult = service.addAccesoriosToCollectionIfMissing([], null, accesorios, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(accesorios);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
