jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { VentaService } from '../service/venta.service';
import { IVenta, Venta } from '../venta.model';
import { ICamisetas } from 'app/entities/camisetas/camisetas.model';
import { CamisetasService } from 'app/entities/camisetas/service/camisetas.service';
import { ISudaderas } from 'app/entities/sudaderas/sudaderas.model';
import { SudaderasService } from 'app/entities/sudaderas/service/sudaderas.service';
import { IAccesorios } from 'app/entities/accesorios/accesorios.model';
import { AccesoriosService } from 'app/entities/accesorios/service/accesorios.service';
import { IUsuario } from 'app/entities/usuario/usuario.model';
import { UsuarioService } from 'app/entities/usuario/service/usuario.service';

import { VentaUpdateComponent } from './venta-update.component';

describe('Component Tests', () => {
  describe('Venta Management Update Component', () => {
    let comp: VentaUpdateComponent;
    let fixture: ComponentFixture<VentaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let ventaService: VentaService;
    let camisetasService: CamisetasService;
    let sudaderasService: SudaderasService;
    let accesoriosService: AccesoriosService;
    let usuarioService: UsuarioService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [VentaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(VentaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(VentaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      ventaService = TestBed.inject(VentaService);
      camisetasService = TestBed.inject(CamisetasService);
      sudaderasService = TestBed.inject(SudaderasService);
      accesoriosService = TestBed.inject(AccesoriosService);
      usuarioService = TestBed.inject(UsuarioService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call Camisetas query and add missing value', () => {
        const venta: IVenta = { id: 456 };
        const camisetas: ICamisetas[] = [{ id: 15368 }];
        venta.camisetas = camisetas;

        const camisetasCollection: ICamisetas[] = [{ id: 50969 }];
        spyOn(camisetasService, 'query').and.returnValue(of(new HttpResponse({ body: camisetasCollection })));
        const additionalCamisetas = [...camisetas];
        const expectedCollection: ICamisetas[] = [...additionalCamisetas, ...camisetasCollection];
        spyOn(camisetasService, 'addCamisetasToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ venta });
        comp.ngOnInit();

        expect(camisetasService.query).toHaveBeenCalled();
        expect(camisetasService.addCamisetasToCollectionIfMissing).toHaveBeenCalledWith(camisetasCollection, ...additionalCamisetas);
        expect(comp.camisetasSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Sudaderas query and add missing value', () => {
        const venta: IVenta = { id: 456 };
        const sudaderas: ISudaderas[] = [{ id: 24262 }];
        venta.sudaderas = sudaderas;

        const sudaderasCollection: ISudaderas[] = [{ id: 42874 }];
        spyOn(sudaderasService, 'query').and.returnValue(of(new HttpResponse({ body: sudaderasCollection })));
        const additionalSudaderas = [...sudaderas];
        const expectedCollection: ISudaderas[] = [...additionalSudaderas, ...sudaderasCollection];
        spyOn(sudaderasService, 'addSudaderasToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ venta });
        comp.ngOnInit();

        expect(sudaderasService.query).toHaveBeenCalled();
        expect(sudaderasService.addSudaderasToCollectionIfMissing).toHaveBeenCalledWith(sudaderasCollection, ...additionalSudaderas);
        expect(comp.sudaderasSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Accesorios query and add missing value', () => {
        const venta: IVenta = { id: 456 };
        const accesorios: IAccesorios[] = [{ id: 46109 }];
        venta.accesorios = accesorios;

        const accesoriosCollection: IAccesorios[] = [{ id: 57313 }];
        spyOn(accesoriosService, 'query').and.returnValue(of(new HttpResponse({ body: accesoriosCollection })));
        const additionalAccesorios = [...accesorios];
        const expectedCollection: IAccesorios[] = [...additionalAccesorios, ...accesoriosCollection];
        spyOn(accesoriosService, 'addAccesoriosToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ venta });
        comp.ngOnInit();

        expect(accesoriosService.query).toHaveBeenCalled();
        expect(accesoriosService.addAccesoriosToCollectionIfMissing).toHaveBeenCalledWith(accesoriosCollection, ...additionalAccesorios);
        expect(comp.accesoriosSharedCollection).toEqual(expectedCollection);
      });

      it('Should call Usuario query and add missing value', () => {
        const venta: IVenta = { id: 456 };
        const usuario: IUsuario = { id: 9403 };
        venta.usuario = usuario;

        const usuarioCollection: IUsuario[] = [{ id: 26601 }];
        spyOn(usuarioService, 'query').and.returnValue(of(new HttpResponse({ body: usuarioCollection })));
        const additionalUsuarios = [usuario];
        const expectedCollection: IUsuario[] = [...additionalUsuarios, ...usuarioCollection];
        spyOn(usuarioService, 'addUsuarioToCollectionIfMissing').and.returnValue(expectedCollection);

        activatedRoute.data = of({ venta });
        comp.ngOnInit();

        expect(usuarioService.query).toHaveBeenCalled();
        expect(usuarioService.addUsuarioToCollectionIfMissing).toHaveBeenCalledWith(usuarioCollection, ...additionalUsuarios);
        expect(comp.usuariosSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const venta: IVenta = { id: 456 };
        const camisetas: ICamisetas = { id: 19197 };
        venta.camisetas = [camisetas];
        const sudaderas: ISudaderas = { id: 86998 };
        venta.sudaderas = [sudaderas];
        const accesorios: IAccesorios = { id: 53405 };
        venta.accesorios = [accesorios];
        const usuario: IUsuario = { id: 64792 };
        venta.usuario = usuario;

        activatedRoute.data = of({ venta });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(venta));
        expect(comp.camisetasSharedCollection).toContain(camisetas);
        expect(comp.sudaderasSharedCollection).toContain(sudaderas);
        expect(comp.accesoriosSharedCollection).toContain(accesorios);
        expect(comp.usuariosSharedCollection).toContain(usuario);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const venta = { id: 123 };
        spyOn(ventaService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ venta });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: venta }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(ventaService.update).toHaveBeenCalledWith(venta);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject();
        const venta = new Venta();
        spyOn(ventaService, 'create').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ venta });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: venta }));
        saveSubject.complete();

        // THEN
        expect(ventaService.create).toHaveBeenCalledWith(venta);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject();
        const venta = { id: 123 };
        spyOn(ventaService, 'update').and.returnValue(saveSubject);
        spyOn(comp, 'previousState');
        activatedRoute.data = of({ venta });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(ventaService.update).toHaveBeenCalledWith(venta);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackCamisetasById', () => {
        it('Should return tracked Camisetas primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackCamisetasById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackSudaderasById', () => {
        it('Should return tracked Sudaderas primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackSudaderasById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackAccesoriosById', () => {
        it('Should return tracked Accesorios primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackAccesoriosById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackUsuarioById', () => {
        it('Should return tracked Usuario primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackUsuarioById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });

    describe('Getting selected relationships', () => {
      describe('getSelectedCamisetas', () => {
        it('Should return option if no Camisetas is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedCamisetas(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Camisetas for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedCamisetas(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Camisetas is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedCamisetas(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });

      describe('getSelectedSudaderas', () => {
        it('Should return option if no Sudaderas is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedSudaderas(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Sudaderas for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedSudaderas(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Sudaderas is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedSudaderas(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });

      describe('getSelectedAccesorios', () => {
        it('Should return option if no Accesorios is selected', () => {
          const option = { id: 123 };
          const result = comp.getSelectedAccesorios(option);
          expect(result === option).toEqual(true);
        });

        it('Should return selected Accesorios for according option', () => {
          const option = { id: 123 };
          const selected = { id: 123 };
          const selected2 = { id: 456 };
          const result = comp.getSelectedAccesorios(option, [selected2, selected]);
          expect(result === selected).toEqual(true);
          expect(result === selected2).toEqual(false);
          expect(result === option).toEqual(false);
        });

        it('Should return option if this Accesorios is not selected', () => {
          const option = { id: 123 };
          const selected = { id: 456 };
          const result = comp.getSelectedAccesorios(option, [selected]);
          expect(result === option).toEqual(true);
          expect(result === selected).toEqual(false);
        });
      });
    });
  });
});
