import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISudaderas } from '../sudaderas.model';

@Component({
  selector: 'jhi-sudaderas-detail',
  templateUrl: './sudaderas-detail.component.html',
})
export class SudaderasDetailComponent implements OnInit {
  sudaderas: ISudaderas | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ sudaderas }) => {
      this.sudaderas = sudaderas;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
