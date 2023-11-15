import {Component, OnInit} from '@angular/core';
import { FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Tarjeta} from "../../../entities/tarjeta";
import {EmpleadorService} from "../../../services/Usuarios/empleador.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-empleador-form',
  templateUrl: './empleador-form.component.html',
  styleUrls: ['./empleador-form.component.css']
})
export class EmpleadorFormComponent implements OnInit{

  empForm! : FormGroup

  tarjetaForm! : FormGroup
  tarjeta! : Tarjeta;
  mision!:string;
  vision!:string;
  constructor(private fB: FormBuilder,
              private service: EmpleadorService,
              private router: Router) {
  }

  ngOnInit(): void {

    this.tarjetaForm = this.fB.group({
      numero: [null,[Validators.required,Validators.maxLength(18)]],
      cvv: [null,[Validators.required,Validators.maxLength(3)]],
      titular: [null,[Validators.required,Validators.maxLength(45)]]
    });
    this.empForm = this.fB.group({
      mision: [null,[Validators.required,Validators.maxLength(250)]],
      vision: [null, [Validators.required, Validators.maxLength(250)]]
    });
  }

  submit(){
    this.tarjeta = this.tarjetaForm.value as Tarjeta;
    this.mision = this.empForm.value.mision;
    this.vision= this.empForm.value.vision;
    this.service.uploadEmpleador(this.tarjeta,this.mision,this.vision).subscribe({
      next: ()=>{
        alert("Actualizado con exito");
        this.router.navigate([this.router.url]);
      }
    })
  }
}
