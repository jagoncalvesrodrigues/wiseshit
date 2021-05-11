import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICamisetas } from '../camisetas.model';

@Component({
  selector: 'jhi-camisetas-detail',
  templateUrl: './camisetas-detail.component.html',
})
export class CamisetasDetailComponent implements OnInit {
  camisetas: ICamisetas | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ camisetas }) => {
      this.camisetas = camisetas;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
