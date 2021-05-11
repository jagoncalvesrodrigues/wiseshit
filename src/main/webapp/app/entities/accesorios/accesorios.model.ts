import { IVenta } from 'app/entities/venta/venta.model';

export interface IAccesorios {
  id?: number;
  stock?: number | null;
  imagen?: string | null;
  talla?: string | null;
  color?: string | null;
  coleccion?: number | null;
  ventas?: IVenta[] | null;
}

export class Accesorios implements IAccesorios {
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

export function getAccesoriosIdentifier(accesorios: IAccesorios): number | undefined {
  return accesorios.id;
}
