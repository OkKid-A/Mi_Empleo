import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {UsuarioSession} from "../../entities/usuario-session";
import {ApiUrl} from "../../share/api-url";
import {Usuario} from "../../entities/usuario";
import {Roles} from "../../share/roles";
import {UsuarioService} from "./usuario.service";
import {Router, RouterModule} from "@angular/router";
import {LocalStorageVariables} from "../../share/local-storage-variables";
@Injectable({
  providedIn: 'root'
})
export class LoginService {
  constructor(private http : HttpClient) {
  }

  public buscarUsuario(password : string, username : string): Observable<Usuario>{
      return this.http.get<Usuario>(ApiUrl.API_URL + "login?username="+username+"&password=" + password);
  }


  public autenticado() : boolean {
    return localStorage.getItem("permisoActual") != undefined;
  }

  public cerrarSesion(){
    localStorage.removeItem(LocalStorageVariables.LOCAL_USER);
    localStorage.removeItem(LocalStorageVariables.PERSMISO_LOCAL);

  }
}
