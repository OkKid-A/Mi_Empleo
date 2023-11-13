import {UsuarioSession} from "./usuario-session";
import {UsuarioAuth} from "./usuario-auth";

export interface Usuario extends UsuarioSession, UsuarioAuth{
  nombre : string;
  email : string;
  direccion: string;
  dob: string;
  cui: string;
}
