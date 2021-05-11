jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { CamisetasService } from '../service/camisetas.service';
import { ICamisetas, Camisetas } from '../camisetas.model';

import { CamisetasUpdateComponent } from './camisetas-update.component';

describe('Component Tests', () => {
  describe('Camisetas Management Update Component', () => {
    let comp: CamisetasUpdateComponent;
    let fixture: ComponentFixture<CamisetasUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let camisetasService: CamisetasService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [CamisetasUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(CamisetasUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CamisetasUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      camisetasService = TestBed.inject(CamisetasService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const camisetas: ICamisetas = { id: 456 };

        activatedRoute.data = of({ camisetas });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(camisetas));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const camisetas = { id: 123 };
        spyOn(camisetasService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ camisetas });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: camisetas }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(camisetasService.update).toHaveBeenCalledWith(camisetas);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const camisetas = new Camisetas();
        spyOn(camisetasService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ camisetas });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: camisetas }));
        saveSubject.complete();

        // THEN
        expect(camisetasService.create).toHaveBeenCalledWith(camisetas);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const camisetas = { id: 123 };
        spyOn(camisetasService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ camisetas });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(camisetasService.update).toHaveBeenCalledWith(camisetas);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
