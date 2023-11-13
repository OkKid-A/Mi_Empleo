import { Injectable } from '@angular/core';
import {UsuarioAuth} from "../../entities/usuario-auth";
import {UsuarioSession} from "../../entities/usuario-session";
import {Usuario} from "../../entities/usuario";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {ApiUrl} from "../../share/api-url";

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  usuarioSes! : UsuarioSession;

  readonly USER_URL = "usuario"
  constructor(private http:HttpClient) { }

  public crearUsuarioAuth(tipo:string,codigo:string){
    this.usuarioSes.tipo = tipo;
    this.usuarioSes.codigo = codigo;
    return this.usuarioSes;
  }

  public crearUsuario(usuario: Usuario, telefono: string):Observable<Usuario>{
    return this.http.post<Usuario>(ApiUrl.API_URL+this.USER_URL+"?telefono="+telefono,usuario)
  }
}
