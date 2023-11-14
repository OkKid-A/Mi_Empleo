import { Injectable } from '@angular/core';
import {Roles} from "../../share/roles";
import {Router} from "@angular/router";
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {Observable} from "rxjs";
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
      this.router.navigate(['empleador/inicio'])
    } else if (rol === Roles.ADMIN){
      this.router.navigate(['admin/dashboard'])
    }
  }

  public identificarTipoDeUsuario(rol : string, codigo : string){
    localStorage.setItem("usuarioActual",codigo);
    localStorage.setItem("permisoActual",rol);
  }

  public verificarCompletado(): boolean{
    const rol = localStorage.getItem("permisoActual")??"";
    const usuario = localStorage.getItem("usuarioActual")??"";
      this.revisarUsuario(rol,usuario).subscribe({
        next: (existe) => {
          return existe;
        },error(error:HttpErrorResponse){
          return false
        }
      })
    return false;
  }

  public revisarUsuario(rol:string, usuario:string):Observable<boolean>{
    return this.http.get<boolean>(ApiUrl.API_URL+"usuario?rol="+rol+"&codigo="+usuario);
  }

}
