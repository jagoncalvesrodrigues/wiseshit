import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAccesorios } from '../accesorios.model';

@Component({
  selector: 'jhi-accesorios-detail',
  templateUrl: './accesorios-detail.component.html',
})
export class AccesoriosDetailComponent implements OnInit {
  accesorios: IAccesorios | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ accesorios }) => {
      this.accesorios = accesorios;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
