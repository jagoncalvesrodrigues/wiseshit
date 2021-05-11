import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISudaderas, Sudaderas } from '../sudaderas.model';
import { SudaderasService } from '../service/sudaderas.service';

@Component({
  selector: 'jhi-sudaderas-update',
  templateUrl: './sudaderas-update.component.html',
})
export class SudaderasUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    stock: [],
    imagen: [],
    talla: [],
    color: [],
    coleccion: [],
  });

  constructor(protected sudaderasService: SudaderasService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sudaderas }) => {
      this.updateForm(sudaderas);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const sudaderas = this.createFromForm();
    if (sudaderas.id !== undefined) {
      this.subscribeToSaveResponse(this.sudaderasService.update(sudaderas));
    } else {
      this.subscribeToSaveResponse(this.sudaderasService.create(sudaderas));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISudaderas>>): void {
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

  protected updateForm(sudaderas: ISudaderas): void {
    this.editForm.patchValue({
      id: sudaderas.id,
      stock: sudaderas.stock,
      imagen: sudaderas.imagen,
      talla: sudaderas.talla,
      color: sudaderas.color,
      coleccion: sudaderas.coleccion,
    });
  }

  protected createFromForm(): ISudaderas {
    return {
      ...new Sudaderas(),
      id: this.editForm.get(['id'])!.value,
      stock: this.editForm.get(['stock'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      talla: this.editForm.get(['talla'])!.value,
      color: this.editForm.get(['color'])!.value,
      coleccion: this.editForm.get(['coleccion'])!.value,
    };
  }
}
