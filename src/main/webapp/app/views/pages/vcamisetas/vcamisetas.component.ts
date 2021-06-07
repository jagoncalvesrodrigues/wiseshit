import { Component, Input } from '@angular/core';
import { ITEMS_PER_PAGE } from 'app/config/pagination.constants';
import { ICamisetas } from 'app/entities/camisetas/camisetas.model';

@Component({
  selector: 'jhi-camisetas',
  templateUrl: './vcamisetas.component.html',
  styleUrls: ['./vcamisetas.component.scss'],
})
export class VcamisetasComponent {
  @Input() camisetas?: ICamisetas[];
  @Input() searchTerm!: string;
  @Input() isLoading = false;
  @Input() totalItems = 0;
  @Input() itemsPerPage = ITEMS_PER_PAGE;
  @Input() page?: number;
  @Input() predicate!: string;
  @Input() ascending!: boolean;
  @Input() ngbPaginationPage = 1;

  trackId(index: number, item: ICamisetas): number {
    return item.id!;
  }
}
