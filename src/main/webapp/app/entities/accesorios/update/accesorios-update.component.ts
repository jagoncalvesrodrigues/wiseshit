import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAccesorios, Accesorios } from '../accesorios.model';
import { AccesoriosService } from '../service/accesorios.service';

@Component({
  selector: 'jhi-accesorios-update',
  templateUrl: './accesorios-update.component.html',
})
export class AccesoriosUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    stock: [],
    imagen: [],
    talla: [],
    color: [],
    coleccion: [],
  });

  constructor(protected accesoriosService: AccesoriosService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accesorios }) => {
      this.updateForm(accesorios);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const accesorios = this.createFromForm();
    if (accesorios.id !== undefined) {
      this.subscribeToSaveResponse(this.accesoriosService.update(accesorios));
    } else {
      this.subscribeToSaveResponse(this.accesoriosService.create(accesorios));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAccesorios>>): void {
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

  protected updateForm(accesorios: IAccesorios): void {
    this.editForm.patchValue({
      id: accesorios.id,
      stock: accesorios.stock,
      imagen: accesorios.imagen,
      talla: accesorios.talla,
      color: accesorios.color,
      coleccion: accesorios.coleccion,
    });
  }

  protected createFromForm(): IAccesorios {
    return {
      ...new Accesorios(),
      id: this.editForm.get(['id'])!.value,
      stock: this.editForm.get(['stock'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      talla: this.editForm.get(['talla'])!.value,
      color: this.editForm.get(['color'])!.value,
      coleccion: this.editForm.get(['coleccion'])!.value,
    };
  }
}
