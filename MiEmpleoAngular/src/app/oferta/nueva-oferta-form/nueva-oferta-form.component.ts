import {Component, Input, OnInit} from '@angular/core';
import {AbstractControl, FormBuilder, FormGroup, ValidationErrors, ValidatorFn, Validators} from "@angular/forms";
import {Oferta} from "../../../entities/oferta";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {LocalStorageVariables} from "../../../share/local-storage-variables";
import {map, of} from "rxjs";
import {ActivatedRoute} from "@angular/router";
import {formatDate} from "@angular/common";

@Component({
  selector: 'app-nueva-oferta-form',
  templateUrl: './nueva-oferta-form.component.html',
  styleUrls: ['./nueva-oferta-form.component.css']
})
export class NuevaOfertaFormComponent implements OnInit {
  ofertaForm!: FormGroup;
  ofertaCodigo! : string;
 @Input()  oferta!: Oferta;
  creado : boolean;
  empCode! : string;
  categoriaCode! : string;
  editando: boolean = false;
  editado: boolean;
  modOpciones = [
      {id: "PRESENCIAL", name:'Presencial'},
          {id: "REMOTO", name:'Remoto'},
          {id: "HIBRIDO", name:'Hibrido'}

      ]

  constructor(private fB: FormBuilder,
              private ofertaService: OfertaService,
              private route : ActivatedRoute) {
    this.creado = false;
    this.editado = false;
    this.route.paramMap.subscribe((params) => {
      if (params){
        console.log(params.get('codigo'))
        this.ofertaCodigo = params.get('codigo') ?? "";
        this.editando = true;
      }
    })
  }

  ngOnInit(): void {
    this.editar()
    this.empCode = localStorage.getItem(LocalStorageVariables.LOCAL_USER) ?? "";
    this.ofertaForm = this.fB.group({
        nombre: [null,[Validators.required, Validators.maxLength(60)]],
        salario: [null,[Validators.required, Validators.pattern('^[0-9]*(\\.[0-9]{0,2})?$')]],
        descripcion: [null,[Validators.required, Validators.maxLength(200)]],
        fechaPublicacion: [null,[Validators.required]],
        fechaLimite: [null,[Validators.required]],
        categoria: this.categoriaCode,
        modalidad: [null,Validators.required],
        ubicacion: [null,[Validators.required, Validators.maxLength(60)]],
        detalles: [null,[Validators.required, Validators.maxLength(1000)]],
      empresa: this.empCode
      }
    )
  }

  editar(){
    if (this.ofertaCodigo){
      this.ofertaService.getOfertaById(this.ofertaCodigo).subscribe((oferta : Oferta) => {
        this.oferta = oferta;
        this.ofertaForm.controls['nombre'].setValue(oferta.nombre)
        this.ofertaForm.controls['salario'].setValue(oferta.salario)
        this.ofertaForm.controls['descripcion'].setValue(oferta.descripcion)
          this.ofertaForm.controls['fechaPublicacion'].setValue(oferta.fechaPublicacion)
          this.ofertaForm.controls['fechaLimite'].setValue(oferta.fechaLimite)
        this.ofertaForm.controls['ubicacion'].setValue(oferta.ubicacion)
        this.ofertaForm.controls['detalles'].setValue(oferta.detalles)
          this.ofertaForm.controls['modalidad'].setValue(oferta.modalidad)
          this.ofertaForm.controls['categoria'].setValue(oferta.categoria)
          this.categoriaCode = oferta.categoria;
      })
    }
  }


  submit() {
    if (this.ofertaForm.valid){
      this.oferta = this.ofertaForm.value;
      this.oferta.categoria = this.categoriaCode;
      if (this.editando){
          this.ofertaService.editarOferta(this.oferta, this.ofertaCodigo).subscribe({
              next: (nuevo: Oferta)  =>{
                  console.log(this.ofertaCodigo)
                  this.ofertaForm.reset();
                  this.editado = true;
              }
          })
      } else {
          this.ofertaService.crearOferta(this.oferta).subscribe({
              next: (nuevo: Oferta) => {
                  this.ofertaForm.reset();
                  this.creado = true;
              },
              error: (error: any) => {
                  alert(error)
              }
          })
      }
    } else {
      alert(this.ofertaForm.errors)
    }
      }


  updateCategoria(input: string) {
    this.categoriaCode = input;
  }


}
