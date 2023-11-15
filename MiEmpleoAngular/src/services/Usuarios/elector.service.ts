import { Injectable } from '@angular/core';
import {Roles} from "../../share/roles";
import {Router} from "@angular/router";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable, of} from "rxjs";
import {ApiUrl} from "../../share/api-url";

@Injectable({
  providedIn: 'root'
})
export class ElectorService {


  constructor(private router:Router,
              private http: HttpClient) { }

  public redirectPorTipo(){

  }
  public redirectUser() {
    let rol = localStorage.getItem("permisoActual");
    console.log(rol)

    if (rol === Roles.SOLICITANTE){
      this.router.navigate(['solicitante'])
      console.log(rol)
    } else if (rol === Roles.EMPLEADOR){
      this.router.navigate(['empleador'])
    } else if (rol === Roles.ADMIN){
      this.router.navigate(['admin/dashboard'])
    }
  }

  public identificarTipoDeUsuario(rol : string, codigo : string){
    localStorage.setItem("usuarioActual",codigo);
    localStorage.setItem("permisoActual",rol);
  }



  public revisarUsuario():Observable<boolean>{
    const rol = localStorage.getItem("permisoActual")??"";
    const usuario = localStorage.getItem("usuarioActual")??"";
    return this.http.get<boolean>(ApiUrl.API_URL+"usuario?rol="+rol+"&codigo="+usuario);
  }

}
