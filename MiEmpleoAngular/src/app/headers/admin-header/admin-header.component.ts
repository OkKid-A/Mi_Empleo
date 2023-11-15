import { Component } from '@angular/core';
import {LoginService} from "../../../services/Usuarios/login.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-admin-header',
  templateUrl: './admin-header.component.html',
  styleUrls: ['./admin-header.component.css']
})
export class AdminHeaderComponent {

  auth : boolean;
  routerInicio = "inicio";
  routerLogin = "login";
  routerCategorias = "categorias";
  routerComision = "comision";

  constructor(private logS : LoginService,
              private router : Router,
              private route : ActivatedRoute) {
    this.auth = false;
  }

  ngOnInit(): void {
    this.auth = this.logS.autenticado()
  }

  logout(){
    this.logS.cerrarSesion();
    this.router.navigate(["../login"], {relativeTo: this.route})
  }
}
