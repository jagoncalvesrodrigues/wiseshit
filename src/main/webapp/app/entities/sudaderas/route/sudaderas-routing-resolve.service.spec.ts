jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ISudaderas, Sudaderas } from '../sudaderas.model';
import { SudaderasService } from '../service/sudaderas.service';

import { SudaderasRoutingResolveService } from './sudaderas-routing-resolve.service';

describe('Service Tests', () => {
  describe('Sudaderas routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: SudaderasRoutingResolveService;
    let service: SudaderasService;
    let resultSudaderas: ISudaderas | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(SudaderasRoutingResolveService);
      service = TestBed.inject(SudaderasService);
      resultSudaderas = undefined;
    });

    describe('resolve', () => {
      it('should return ISudaderas returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSudaderas = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSudaderas).toEqual({ id: 123 });
      });

      it('should return new ISudaderas if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSudaderas = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultSudaderas).toEqual(new Sudaderas());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultSudaderas = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultSudaderas).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
