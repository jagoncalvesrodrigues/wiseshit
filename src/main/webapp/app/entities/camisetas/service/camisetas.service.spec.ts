import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ICamisetas, Camisetas } from '../camisetas.model';

import { CamisetasService } from './camisetas.service';

describe('Service Tests', () => {
  describe('Camisetas Service', () => {
    let service: CamisetasService;
    let httpMock: HttpTestingController;
    let elemDefault: ICamisetas;
    let expectedResult: ICamisetas | ICamisetas[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(CamisetasService);
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

      it('should create a Camisetas', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Camisetas()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Camisetas', () => {
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

      it('should partial update a Camisetas', () => {
        const patchObject = Object.assign(
          {
            stock: 1,
            imagen: 'BBBBBB',
          },
          new Camisetas()
        );

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Camisetas', () => {
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

      it('should delete a Camisetas', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addCamisetasToCollectionIfMissing', () => {
        it('should add a Camisetas to an empty array', () => {
          const camisetas: ICamisetas = { id: 123 };
          expectedResult = service.addCamisetasToCollectionIfMissing([], camisetas);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(camisetas);
        });

        it('should not add a Camisetas to an array that contains it', () => {
          const camisetas: ICamisetas = { id: 123 };
          const camisetasCollection: ICamisetas[] = [
            {
              ...camisetas,
            },
            { id: 456 },
          ];
          expectedResult = service.addCamisetasToCollectionIfMissing(camisetasCollection, camisetas);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Camisetas to an array that doesn't contain it", () => {
          const camisetas: ICamisetas = { id: 123 };
          const camisetasCollection: ICamisetas[] = [{ id: 456 }];
          expectedResult = service.addCamisetasToCollectionIfMissing(camisetasCollection, camisetas);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(camisetas);
        });

        it('should add only unique Camisetas to an array', () => {
          const camisetasArray: ICamisetas[] = [{ id: 123 }, { id: 456 }, { id: 97005 }];
          const camisetasCollection: ICamisetas[] = [{ id: 123 }];
          expectedResult = service.addCamisetasToCollectionIfMissing(camisetasCollection, ...camisetasArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const camisetas: ICamisetas = { id: 123 };
          const camisetas2: ICamisetas = { id: 456 };
          expectedResult = service.addCamisetasToCollectionIfMissing([], camisetas, camisetas2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(camisetas);
          expect(expectedResult).toContain(camisetas2);
        });

        it('should accept null and undefined values', () => {
          const camisetas: ICamisetas = { id: 123 };
          expectedResult = service.addCamisetasToCollectionIfMissing([], null, camisetas, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(camisetas);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
