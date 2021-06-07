import * as dayjs from 'dayjs';
import { ICamisetas } from 'app/entities/camisetas/camisetas.model';
import { ISudaderas } from 'app/entities/sudaderas/sudaderas.model';
import { IAccesorios } from 'app/entities/accesorios/accesorios.model';
import { IUser } from 'app/entities/user/user.model';

export interface IVenta {
  id?: number;
  importe?: number | null;
  fecha?: dayjs.Dayjs | null;
  camisetas?: ICamisetas[] | null;
  sudaderas?: ISudaderas[] | null;
  accesorios?: IAccesorios[] | null;
  user?: IUser | null;
}

export class Venta implements IVenta {
  constructor(
    public id?: number,
    public importe?: number | null,
    public fecha?: dayjs.Dayjs | null,
    public camisetas?: ICamisetas[] | null,
    public sudaderas?: ISudaderas[] | null,
    public accesorios?: IAccesorios[] | null,
    public user?: IUser | null
  ) {}
}

export function getVentaIdentifier(venta: IVenta): number | undefined {
  return venta.id;
}
