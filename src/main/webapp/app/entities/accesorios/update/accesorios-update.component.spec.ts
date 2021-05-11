jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AccesoriosService } from '../service/accesorios.service';
import { IAccesorios, Accesorios } from '../accesorios.model';

import { AccesoriosUpdateComponent } from './accesorios-update.component';

describe('Component Tests', () => {
  describe('Accesorios Management Update Component', () => {
    let comp: AccesoriosUpdateComponent;
    let fixture: ComponentFixture<AccesoriosUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let accesoriosService: AccesoriosService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AccesoriosUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AccesoriosUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AccesoriosUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      accesoriosService = TestBed.inject(AccesoriosService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const accesorios: IAccesorios = { id: 456 };

        activatedRoute.data = of({ accesorios });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(accesorios));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accesorios = { id: 123 };
        spyOn(accesoriosService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accesorios });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: accesorios }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(accesoriosService.update).toHaveBeenCalledWith(accesorios);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accesorios = new Accesorios();
        spyOn(accesoriosService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accesorios });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: accesorios }));
        saveSubject.complete();

        // THEN
        expect(accesoriosService.create).toHaveBeenCalledWith(accesorios);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const accesorios = { id: 123 };
        spyOn(accesoriosService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ accesorios });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(accesoriosService.update).toHaveBeenCalledWith(accesorios);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
