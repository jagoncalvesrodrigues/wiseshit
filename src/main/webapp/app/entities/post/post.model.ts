import { IUsuario } from 'app/entities/usuario/usuario.model';

export interface IPost {
  id?: number;
  imagen?: string | null;
  like?: number | null;
  usuario?: IUsuario | null;
}

export class Post implements IPost {
  constructor(public id?: number, public imagen?: string | null, public like?: number | null, public usuario?: IUsuario | null) {}
}

export function getPostIdentifier(post: IPost): number | undefined {
  return post.id;
}
