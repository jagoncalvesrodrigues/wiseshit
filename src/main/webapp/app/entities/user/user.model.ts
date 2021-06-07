import { IPost } from '../post/post.model';
import { IVenta } from '../venta/venta.model';

export interface IUser {
  id?: number;
  login?: string;
  posts?: IPost[] | null;
  ventas?: IVenta[] | null;
}

export class User implements IUser {
  constructor(public id: number, public login: string) {}
}

export function getUserIdentifier(user: IUser): number | undefined {
  return user.id;
}
