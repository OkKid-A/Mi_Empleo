import {Component, OnInit} from '@angular/core';
import {LoginService} from "../../../services/Usuarios/login.service";
import {ActivatedRoute, Router} from "@angular/router";

@Component({
  selector: 'app-empleador-header',
  templateUrl: './empleador-header.component.html',
  styleUrls: ['./empleador-header.component.css']
})
export class EmpleadorHeaderComponent implements OnInit{
  auth : boolean;
  routerLogin = "../login";
  routerInicio = "inicio";
  routerPostulaciones = "postulaciones";
  routerEntrevistas = "entrevistas";
  routerReportes = "reportes";

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
