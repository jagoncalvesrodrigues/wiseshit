jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { SudaderasService } from '../service/sudaderas.service';
import { ISudaderas, Sudaderas } from '../sudaderas.model';

import { SudaderasUpdateComponent } from './sudaderas-update.component';

describe('Component Tests', () => {
  describe('Sudaderas Management Update Component', () => {
    let comp: SudaderasUpdateComponent;
    let fixture: ComponentFixture<SudaderasUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let sudaderasService: SudaderasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [SudaderasUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(SudaderasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SudaderasUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      sudaderasService = TestBed.inject(SudaderasService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const sudaderas: ISudaderas = { id: 456 };

        activatedRoute.data = of({ sudaderas });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(sudaderas));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sudaderas = { id: 123 };
        spyOn(sudaderasService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sudaderas });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sudaderas }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(sudaderasService.update).toHaveBeenCalledWith(sudaderas);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sudaderas = new Sudaderas();
        spyOn(sudaderasService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sudaderas });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: sudaderas }));
        saveSubject.complete();

        // THEN
        expect(sudaderasService.create).toHaveBeenCalledWith(sudaderas);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const sudaderas = { id: 123 };
        spyOn(sudaderasService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ sudaderas });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(sudaderasService.update).toHaveBeenCalledWith(sudaderas);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
