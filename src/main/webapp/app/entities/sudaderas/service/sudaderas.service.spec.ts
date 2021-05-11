import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { ISudaderas, Sudaderas } from '../sudaderas.model';

import { SudaderasService } from './sudaderas.service';

describe('Service Tests', () => {
  describe('Sudaderas Service', () => {
    let service: SudaderasService;
    let httpMock: HttpTestingController;
    let elemDefault: ISudaderas;
    let expectedResult: ISudaderas | ISudaderas[] | boolean | null;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      service = TestBed.inject(SudaderasService);
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

      it('should create a Sudaderas', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);

        service.create(new Sudaderas()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Sudaderas', () => {
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

      it('should partial update a Sudaderas', () => {
        const patchObject = Object.assign({}, new Sudaderas());

        const returnedFromService = Object.assign(patchObject, elemDefault);

        const expected = Object.assign({}, returnedFromService);

        service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PATCH' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of Sudaderas', () => {
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

      it('should delete a Sudaderas', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });

      describe('addSudaderasToCollectionIfMissing', () => {
        it('should add a Sudaderas to an empty array', () => {
          const sudaderas: ISudaderas = { id: 123 };
          expectedResult = service.addSudaderasToCollectionIfMissing([], sudaderas);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(sudaderas);
        });

        it('should not add a Sudaderas to an array that contains it', () => {
          const sudaderas: ISudaderas = { id: 123 };
          const sudaderasCollection: ISudaderas[] = [
            {
              ...sudaderas,
            },
            { id: 456 },
          ];
          expectedResult = service.addSudaderasToCollectionIfMissing(sudaderasCollection, sudaderas);
          expect(expectedResult).toHaveLength(2);
        });

        it("should add a Sudaderas to an array that doesn't contain it", () => {
          const sudaderas: ISudaderas = { id: 123 };
          const sudaderasCollection: ISudaderas[] = [{ id: 456 }];
          expectedResult = service.addSudaderasToCollectionIfMissing(sudaderasCollection, sudaderas);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(sudaderas);
        });

        it('should add only unique Sudaderas to an array', () => {
          const sudaderasArray: ISudaderas[] = [{ id: 123 }, { id: 456 }, { id: 15984 }];
          const sudaderasCollection: ISudaderas[] = [{ id: 123 }];
          expectedResult = service.addSudaderasToCollectionIfMissing(sudaderasCollection, ...sudaderasArray);
          expect(expectedResult).toHaveLength(3);
        });

        it('should accept varargs', () => {
          const sudaderas: ISudaderas = { id: 123 };
          const sudaderas2: ISudaderas = { id: 456 };
          expectedResult = service.addSudaderasToCollectionIfMissing([], sudaderas, sudaderas2);
          expect(expectedResult).toHaveLength(2);
          expect(expectedResult).toContain(sudaderas);
          expect(expectedResult).toContain(sudaderas2);
        });

        it('should accept null and undefined values', () => {
          const sudaderas: ISudaderas = { id: 123 };
          expectedResult = service.addSudaderasToCollectionIfMissing([], null, sudaderas, undefined);
          expect(expectedResult).toHaveLength(1);
          expect(expectedResult).toContain(sudaderas);
        });
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
