jest.mock('@angular/router');

import { TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of } from 'rxjs';

import { ICamisetas, Camisetas } from '../camisetas.model';
import { CamisetasService } from '../service/camisetas.service';

import { CamisetasRoutingResolveService } from './camisetas-routing-resolve.service';

describe('Service Tests', () => {
  describe('Camisetas routing resolve service', () => {
    let mockRouter: Router;
    let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
    let routingResolveService: CamisetasRoutingResolveService;
    let service: CamisetasService;
    let resultCamisetas: ICamisetas | undefined;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        providers: [Router, ActivatedRouteSnapshot],
      });
      mockRouter = TestBed.inject(Router);
      mockActivatedRouteSnapshot = TestBed.inject(ActivatedRouteSnapshot);
      routingResolveService = TestBed.inject(CamisetasRoutingResolveService);
      service = TestBed.inject(CamisetasService);
      resultCamisetas = undefined;
    });

    describe('resolve', () => {
      it('should return ICamisetas returned by find', () => {
        // GIVEN
        service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCamisetas = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCamisetas).toEqual({ id: 123 });
      });

      it('should return new ICamisetas if id is not provided', () => {
        // GIVEN
        service.find = jest.fn();
        mockActivatedRouteSnapshot.params = {};

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCamisetas = result;
        });

        // THEN
        expect(service.find).not.toBeCalled();
        expect(resultCamisetas).toEqual(new Camisetas());
      });

      it('should route to 404 page if data not found in server', () => {
        // GIVEN
        spyOn(service, 'find').and.returnValue(of(new HttpResponse({ body: null })));
        mockActivatedRouteSnapshot.params = { id: 123 };

        // WHEN
        routingResolveService.resolve(mockActivatedRouteSnapshot).subscribe(result => {
          resultCamisetas = result;
        });

        // THEN
        expect(service.find).toBeCalledWith(123);
        expect(resultCamisetas).toEqual(undefined);
        expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
      });
    });
  });
});
