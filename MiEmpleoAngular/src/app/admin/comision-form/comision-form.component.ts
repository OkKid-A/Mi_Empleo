import {Component, OnInit, TemplateRef} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {ComisionService} from "../../../services/ofertas/comision.service";
import {HttpErrorResponse} from "@angular/common/http";
import {DashboardService} from "../../../services/reportes/dashboard.service";

@Component({
  selector: 'app-comision-form',
  templateUrl: './comision-form.component.html',
  styleUrls: ['./comision-form.component.css']
})
export class ComisionFormComponent implements OnInit{

  comisionActual!: number;
  comisionForm! : FormGroup;
  fechaForm! : FormGroup;
  modalRef! : BsModalRef;
  comision! : string;
  fechaActual! : string;
  fechaNueva! : string;

  constructor(private fb: FormBuilder,
              private modalService : BsModalService,
              private comisionService : ComisionService,
              private fechaService : DashboardService) {

  }

  ngOnInit(): void {
    this.fechaService.getFechaDB().subscribe({
      next:(fecha)=> {
        this.fechaActual = fecha;
    }
    })
    this.comisionService.getComision().subscribe({
      next:(comision)=>{
        this.comisionActual = comision;
      }, error:(err:HttpErrorResponse)=>{
        alert("No se pudo cargar el precio actual");
      }
    })
    this.comisionForm = this.fb.group({
      comision: [null,[Validators.required, Validators.pattern('^[0-9]*(\\.[0-9]{0,2})?$'),
        Validators.maxLength(30)]]
    })
    this.fechaForm = this.fb.group({
      fecha: [null,[Validators.required]]
    })
  }

  submit(){
    this.comision = this.comisionForm.value.comision;
    this.comisionService.cambiarComision(this.comision).subscribe({
      next:()=>{
        alert("Comision cambiada con exito");
        this.comisionActual = Number(this.comision);
      }, error:(err:HttpErrorResponse)=>{
        alert(err.message);
      }
    })
  }

  submitFecha(){
    this.fechaNueva = this.fechaForm.value.fecha;
    this.fechaService.cambiarFecha(this.fechaNueva).subscribe({
      next:(count) =>{
        alert("Se actualizo la fecha y se actualizaron "+count+" ofertas.");
        this.fechaActual = this.fechaNueva;
      }, error:(err:HttpErrorResponse) =>{
        alert(err.message)
      }
    })
  }

  openModal(template : TemplateRef<any>){
    this.modalRef = this.modalService.show(template)
  }

}
