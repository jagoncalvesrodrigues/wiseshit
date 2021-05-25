import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ICamisetas, Camisetas } from '../camisetas.model';
import { CamisetasService } from '../service/camisetas.service';

@Component({
  selector: 'jhi-camisetas-update',
  templateUrl: './camisetas-update.component.html',
  styleUrls: ['./camisetas-update.component.scss'],
})
export class CamisetasUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    stock: [],
    imagen: [],
    talla: [],
    color: [],
    coleccion: [],
  });

  constructor(protected camisetasService: CamisetasService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ camisetas }) => {
      this.updateForm(camisetas);
    });
  }

  // onUpload():void{
  //   console.log('hey');
  // }

  // onFileChange(e:any):void{
  //   console.log('klk',e);
  // }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const camisetas = this.createFromForm();
    if (camisetas.id !== undefined) {
      this.subscribeToSaveResponse(this.camisetasService.update(camisetas));
    } else {
      this.subscribeToSaveResponse(this.camisetasService.create(camisetas));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICamisetas>>): void {
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

  protected updateForm(camisetas: ICamisetas): void {
    this.editForm.patchValue({
      id: camisetas.id,
      stock: camisetas.stock,
      imagen: camisetas.imagen,
      talla: camisetas.talla,
      color: camisetas.color,
      coleccion: camisetas.coleccion,
    });
  }

  protected createFromForm(): ICamisetas {
    return {
      ...new Camisetas(),
      id: this.editForm.get(['id'])!.value,
      stock: this.editForm.get(['stock'])!.value,
      imagen: this.editForm.get(['imagen'])!.value,
      talla: this.editForm.get(['talla'])!.value,
      color: this.editForm.get(['color'])!.value,
      coleccion: this.editForm.get(['coleccion'])!.value,
    };
  }
}
