import { Injectable } from '@angular/core';
import {Roles} from "../../share/roles";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class ElectorService {

  constructor(private router:Router) { }

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
}
