import { PipeTransform, Pipe } from '@angular/core';
import { Camisetas } from '../camisetas.model';

@Pipe({
  name: 'camisetasFilter',
})
export class CamisetasFilterPipe implements PipeTransform {
  transform(camiseta: Camisetas[], searchTerm: string): Camisetas[] {
    return camiseta.filter(camisetas => camisetas.coleccion?.toString().indexOf(searchTerm.toLowerCase()) !== -1);
  }
}
