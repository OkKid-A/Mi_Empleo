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

      const md5 = new Md5();
      let pass = md5.appendStr(this.loginForm.get('password')?.value).end()!.toString();
      this.loginService.buscarUsuario(pass, this.loginForm.get('username')?.value).subscribe({
        next: (creado : Usuario) => {
          console.log("creat" + creado.tipo);
          this.elserv.identificarTipoDeUsuario(creado.tipo,creado.codigo)
          this.elserv.redirectUser()
          this.limpiar();
        },
        error: (error: any) => {
          console.log("error");
        }
      })
    }
  }

  limpiar(): void{
    this.loginForm.reset()
  }
}
