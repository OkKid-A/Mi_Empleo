import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UsuarioService} from "../../../services/Usuarios/usuario.service";
import {LoginService} from "../../../services/Usuarios/login.service";
import {ActivatedRoute} from "@angular/router";
import {ElectorService} from "../../../services/Usuarios/elector.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-validate-form',
  templateUrl: './validate-form.component.html',
  styleUrls: ['./validate-form.component.css']
})
export class ValidateFormComponent implements OnInit{

  passwordForm! : FormGroup
  token! : string

  constructor(private fB: FormBuilder,
              private usuarioService: UsuarioService,
              private loginService: LoginService,
              private route: ActivatedRoute,
              private elecService : ElectorService) {
    this.route.paramMap.subscribe((params) => {
      if (params){
        this.token = params.get('codigo') ?? "";
      }
    })
  }

  ngOnInit(): void {
    this.passwordForm = this.fB.group({
      password: [null,[Validators.required,Validators.maxLength(32)]],
      conPassword: [null,[Validators.required, Validators.maxLength(32)]],
    },{
      validator: this.CompararPassword('password','conPassword')
    })
  }

  CompararPassword(controlName: string, conControlName: string){
    return (formGroup: FormGroup) =>{
      const control = formGroup.controls[controlName];
      const confirmarControl = formGroup.controls[conControlName];
      if (confirmarControl.errors && !confirmarControl.errors['compararPassword']){
        return;
      } if (control.value !== confirmarControl.value){
        confirmarControl.setErrors({compararPassword: true});
      } else {
        confirmarControl.setErrors(null);
      }
    };
  }

  submit(){
    const password = this.loginService.encriptar(this.passwordForm.value.conPassword);
    console.log(password)
    this.usuarioService.crearPassword(password,this.token).subscribe({
      next:(usuario) =>{
        alert("Se ha cambiado la contraseña con exito");
        this.elecService.identificarTipoDeUsuario(usuario.tipo,usuario.codigo);
        this.elecService.redirectUser();
      }, error: (error : HttpErrorResponse) =>{
        if (error.status === 404){
          alert("No se reconocio el token. Intenta de nuevo");
        } else {
          alert("Hubo un error al actualizar la contraseña")
        }
    }
    })
  }
}
