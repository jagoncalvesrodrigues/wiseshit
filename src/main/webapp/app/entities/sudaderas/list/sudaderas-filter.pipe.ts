import { PipeTransform, Pipe } from '@angular/core';
import { Sudaderas } from '../sudaderas.model';

@Pipe({
  name: 'sudaderasFilter',
})
export class SudaderasFilterPipe implements PipeTransform {
  transform(sudadera: Sudaderas[], searchTerm: string): Sudaderas[] {
    return sudadera.filter(sudaderas => sudaderas.coleccion?.toString().indexOf(searchTerm.toLowerCase()) !== -1);
  }
}
