import { IVenta } from 'app/entities/venta/venta.model';

export interface ICamisetas {
  id?: number;
  stock?: number | null;
  imagen?: string | null;
  talla?: string | null;
  color?: string | null;
  coleccion?: number | null;
  ventas?: IVenta[] | null;
}

export class Camisetas implements ICamisetas {
  constructor(
    public id?: number,
    public stock?: number | null,
    public imagen?: string | null,
    public talla?: string | null,
    public color?: string | null,
    public coleccion?: number | null,
    public ventas?: IVenta[] | null
  ) {}
}

export function getCamisetasIdentifier(camisetas: ICamisetas): number | undefined {
  return camisetas.id;
}
