import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../../services/Usuarios/login.service";
import {ActivatedRoute, Router, RouterLink} from "@angular/router";
import {relative} from "@angular/compiler-cli";


@Component({
  selector: 'app-solicitante-header',
  templateUrl: './solicitante-header.component.html',
  styleUrls: ['./solicitante-header.component.css']
})
export class SolicitanteHeaderComponent implements OnInit{
  auth : boolean;


  constructor(private logS : LoginService,
              private router : Router,
              private route : ActivatedRoute) {
    this.auth = false;
  }

  public routerSolicitudes = "solicitudes";
  public routerLogin = "../login";
  public routerOut = "";
  public routerBusqueda = "busqueda";
  public routerEntrevistas = "entrevistas";

  ngOnInit(): void {
    this.auth = this.logS.autenticado()
  }

  logout(){
    this.logS.cerrarSesion();
    this.router.navigate(["../login"], {relativeTo: this.route})
  }
}
