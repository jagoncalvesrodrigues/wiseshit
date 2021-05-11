import { IPost } from 'app/entities/post/post.model';
import { IVenta } from 'app/entities/venta/venta.model';

export interface IUsuario {
  id?: number;
  nombre?: string | null;
  apellido?: string | null;
  correo?: string | null;
  numeroTelefono?: number | null;
  direccion?: string | null;
  codigoPostal?: number | null;
  posts?: IPost[] | null;
  ventas?: IVenta[] | null;
}

export class Usuario implements IUsuario {
  constructor(
    public id?: number,
    public nombre?: string | null,
    public apellido?: string | null,
    public correo?: string | null,
    public numeroTelefono?: number | null,
    public direccion?: string | null,
    public codigoPostal?: number | null,
    public posts?: IPost[] | null,
    public ventas?: IVenta[] | null
  ) {}
}

export function getUsuarioIdentifier(usuario: IUsuario): number | undefined {
  return usuario.id;
}
