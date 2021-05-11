import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IVenta, Venta } from '../venta.model';
import { VentaService } from '../service/venta.service';
import { ICamisetas } from 'app/entities/camisetas/camisetas.model';
import { CamisetasService } from 'app/entities/camisetas/service/camisetas.service';
import { ISudaderas } from 'app/entities/sudaderas/sudaderas.model';
import { SudaderasService } from 'app/entities/sudaderas/service/sudaderas.service';
import { IAccesorios } from 'app/entities/accesorios/accesorios.model';
import { AccesoriosService } from 'app/entities/accesorios/service/accesorios.service';
import { IUsuario } from 'app/entities/usuario/usuario.model';
import { UsuarioService } from 'app/entities/usuario/service/usuario.service';

@Component({
  selector: 'jhi-venta-update',
  templateUrl: './venta-update.component.html',
})
export class VentaUpdateComponent implements OnInit {
  isSaving = false;

  camisetasSharedCollection: ICamisetas[] = [];
  sudaderasSharedCollection: ISudaderas[] = [];
  accesoriosSharedCollection: IAccesorios[] = [];
  usuariosSharedCollection: IUsuario[] = [];

  editForm = this.fb.group({
    id: [],
    importe: [],
    fecha: [],
    camisetas: [],
    sudaderas: [],
    accesorios: [],
    usuario: [],
  });

  constructor(
    protected ventaService: VentaService,
    protected camisetasService: CamisetasService,
    protected sudaderasService: SudaderasService,
    protected accesoriosService: AccesoriosService,
    protected usuarioService: UsuarioService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ venta }) => {
      if (venta.id === undefined) {
        const today = dayjs().startOf('day');
        venta.fecha = today;
      }

      this.updateForm(venta);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const venta = this.createFromForm();
    if (venta.id !== undefined) {
      this.subscribeToSaveResponse(this.ventaService.update(venta));
    } else {
      this.subscribeToSaveResponse(this.ventaService.create(venta));
    }
  }

  trackCamisetasById(index: number, item: ICamisetas): number {
    return item.id!;
  }

  trackSudaderasById(index: number, item: ISudaderas): number {
    return item.id!;
  }

  trackAccesoriosById(index: number, item: IAccesorios): number {
    return item.id!;
  }

  trackUsuarioById(index: number, item: IUsuario): number {
    return item.id!;
  }

  getSelectedCamisetas(option: ICamisetas, selectedVals?: ICamisetas[]): ICamisetas {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  getSelectedSudaderas(option: ISudaderas, selectedVals?: ISudaderas[]): ISudaderas {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  getSelectedAccesorios(option: IAccesorios, selectedVals?: IAccesorios[]): IAccesorios {
    if (selectedVals) {
      for (const selectedVal of selectedVals) {
        if (option.id === selectedVal.id) {
          return selectedVal;
        }
      }
    }
    return option;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVenta>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(venta: IVenta): void {
    this.editForm.patchValue({
      id: venta.id,
      importe: venta.importe,
      fecha: venta.fecha ? venta.fecha.format(DATE_TIME_FORMAT) : null,
      camisetas: venta.camisetas,
      sudaderas: venta.sudaderas,
      accesorios: venta.accesorios,
      usuario: venta.usuario,
    });

    this.camisetasSharedCollection = this.camisetasService.addCamisetasToCollectionIfMissing(
      this.camisetasSharedCollection,
      ...(venta.camisetas ?? [])
    );
    this.sudaderasSharedCollection = this.sudaderasService.addSudaderasToCollectionIfMissing(
      this.sudaderasSharedCollection,
      ...(venta.sudaderas ?? [])
    );
    this.accesoriosSharedCollection = this.accesoriosService.addAccesoriosToCollectionIfMissing(
      this.accesoriosSharedCollection,
      ...(venta.accesorios ?? [])
    );
    this.usuariosSharedCollection = this.usuarioService.addUsuarioToCollectionIfMissing(this.usuariosSharedCollection, venta.usuario);
  }

  protected loadRelationshipsOptions(): void {
    this.camisetasService
      .query()
      .pipe(map((res: HttpResponse<ICamisetas[]>) => res.body ?? []))
      .pipe(
        map((camisetas: ICamisetas[]) =>
          this.camisetasService.addCamisetasToCollectionIfMissing(camisetas, ...(this.editForm.get('camisetas')!.value ?? []))
        )
      )
      .subscribe((camisetas: ICamisetas[]) => (this.camisetasSharedCollection = camisetas));

    this.sudaderasService
      .query()
      .pipe(map((res: HttpResponse<ISudaderas[]>) => res.body ?? []))
      .pipe(
        map((sudaderas: ISudaderas[]) =>
          this.sudaderasService.addSudaderasToCollectionIfMissing(sudaderas, ...(this.editForm.get('sudaderas')!.value ?? []))
        )
      )
      .subscribe((sudaderas: ISudaderas[]) => (this.sudaderasSharedCollection = sudaderas));

    this.accesoriosService
      .query()
      .pipe(map((res: HttpResponse<IAccesorios[]>) => res.body ?? []))
      .pipe(
        map((accesorios: IAccesorios[]) =>
          this.accesoriosService.addAccesoriosToCollectionIfMissing(accesorios, ...(this.editForm.get('accesorios')!.value ?? []))
        )
      )
      .subscribe((accesorios: IAccesorios[]) => (this.accesoriosSharedCollection = accesorios));

    this.usuarioService
      .query()
      .pipe(map((res: HttpResponse<IUsuario[]>) => res.body ?? []))
      .pipe(
        map((usuarios: IUsuario[]) => this.usuarioService.addUsuarioToCollectionIfMissing(usuarios, this.editForm.get('usuario')!.value))
      )
      .subscribe((usuarios: IUsuario[]) => (this.usuariosSharedCollection = usuarios));
  }

  protected createFromForm(): IVenta {
    return {
      ...new Venta(),
      id: this.editForm.get(['id'])!.value,
      importe: this.editForm.get(['importe'])!.value,
      fecha: this.editForm.get(['fecha'])!.value ? dayjs(this.editForm.get(['fecha'])!.value, DATE_TIME_FORMAT) : undefined,
      camisetas: this.editForm.get(['camisetas'])!.value,
      sudaderas: this.editForm.get(['sudaderas'])!.value,
      accesorios: this.editForm.get(['accesorios'])!.value,
      usuario: this.editForm.get(['usuario'])!.value,
    };
  }
}
