import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from "@angular/forms";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {ActivatedRoute} from "@angular/router";
import {Usuario} from "../../../entities/usuario";
import {UsuarioService} from "../../../services/Usuarios/usuario.service";

@Component({
  selector: 'app-crear-cuenta-form',
  templateUrl: './crear-cuenta-form.component.html',
  styleUrls: ['./crear-cuenta-form.component.css']
})
export class CrearCuentaFormComponent implements OnInit{

  usuarioForm! : FormGroup
  usuario! : Usuario
  creado : boolean = false;
  telefono   = new FormControl([null,[Validators.required, Validators.maxLength(8)]])

  constructor(private fB: FormBuilder,
              private ofertaService: OfertaService,
              private route : ActivatedRoute,
              private usuarioService: UsuarioService) {
  }

  ngOnInit(): void {
    this.usuarioForm = this.fB.group({
      nombre: [null,[Validators.required, Validators.maxLength(45)]],
      username: [null,[Validators.required, Validators.maxLength(20)]],
      direccion: [null,[Validators.required, Validators.maxLength(60)]],
      email: [null,[Validators.required, Validators.email]],
      cui: [null,[Validators.required, Validators.minLength(13),Validators.maxLength(13)]],
      telefono: [null,[Validators.required, Validators.maxLength(8)]],
      dob: [null,[Validators.required]],
      tipo: [null,[Validators.required]]
    })
  }

  submit(){
    this.usuario = this.usuarioForm.value as Usuario
    this.usuarioService.crearUsuario(this.usuario,this.usuarioForm.value.telefono).subscribe({
      next:(usuario)=>{
        this.creado = true;
      }
    })
  }
}
