import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Usuario} from "../../../entities/usuario";
import {LoginService} from "../../../services/Usuarios/login.service";
import {UsuarioAuth} from "../../../entities/usuario-auth";
import {Md5} from "ts-md5";
import {UsuarioSession} from "../../../entities/usuario-session";
import {Router} from "@angular/router";
import {ElectorService} from "../../../services/Usuarios/elector.service";

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit{
  loginForm! : FormGroup;
  usuario! : Usuario;
  existente : boolean;
  crearRoute = "../crear-cuenta"
  uploadRoute = "../upload"

  constructor(private fb: FormBuilder, private loginService : LoginService, router:Router,private elserv:ElectorService) {
    this.existente = false;
  }

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      username : [null,[Validators.required, Validators.maxLength(20)]],
      password: [null,[Validators.required, Validators.maxLength(32)]]
    })
  }

  submit(): void {
    if (this.loginForm.valid) {
      console.log("creat" + "creado");
      let pass = this.loginService.encriptar(this.loginForm.get('password')?.value);
      this.loginService.buscarUsuario(pass, this.loginForm.get('username')?.value).subscribe({
        next: (creado : Usuario) => {
          console.log("creat" + creado.tipo);
          this.elserv.identificarTipoDeUsuario(creado.tipo,creado.codigo)
          this.elserv.redirectUser()
          this.limpiar();
        },
        error: (error: any) => {
          alert("El usuario no fue encontrado.")
          console.log("error");
        }
      })
    }
  }

  limpiar(): void{
    this.loginForm.reset()
  }
}
