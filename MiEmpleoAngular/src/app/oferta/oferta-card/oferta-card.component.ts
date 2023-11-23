import {Component, Input, OnInit, TemplateRef} from '@angular/core';
import {Oferta} from "../../../entities/oferta";
import {EstadoOferta} from "../../../share/estado-oferta";
import {HttpErrorResponse} from "@angular/common/http";
import {NotificationService} from "../../../services/notification/notification.service";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {ActivatedRoute, Router} from "@angular/router";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {PostulacionService} from "../../../services/ofertas/postulacion.service";
import {LocalStorageVariables} from "../../../share/local-storage-variables";
import {EntrevistaService} from "../../../services/ofertas/entrevista.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {Entrevista} from "../../../entities/entrevista";

@Component({
  selector: 'app-oferta-card',
  templateUrl: './oferta-card.component.html',
  styleUrls: ['./oferta-card.component.css']
})
export class OfertaCardComponent implements OnInit{
  @Input()  oferta! : Oferta;
  @Input() solicitante : boolean = false;
  @Input() empleador: boolean = false;
  @Input() postulaciones: boolean = false;
  @Input() conEntrevista: boolean = false;
  @Input() entrevista: boolean = false;
  @Input() ganador: boolean = false;
  entrevistas: Entrevista[] = [];
  borrarForm! : FormGroup
  modalRef!: BsModalRef;

  constructor(private notificationService: NotificationService,
              private ofertaService: OfertaService,
              private router: Router,
              private form: FormBuilder,
              private modalService: BsModalService,
              private postulacionService : PostulacionService,
              private entrevistaService : EntrevistaService) {
  }

  ngOnInit(){
    this.entrevistaService.tieneEntrevista(String(this.oferta.codigo)).subscribe({
      next: (tiene) => {
        this.conEntrevista = tiene;
    }
    })
    this.borrarForm = this.form.group({
      mensaje : [null,[Validators.required, Validators.maxLength(256)]]
    });
  }

  borrar(){
    this.ofertaService.borrarOferta(this.oferta.codigo,this.oferta.estado).subscribe((oferta) =>
        {
          this.notificationService.enviarEmailAllOferta(this.oferta.codigo,"Cancelacion de solicitud",
              "El usuario " + localStorage.getItem("usuarioActual")+
              " cancelo la solicitud a tu oferta de empleo porque " + this.borrarForm.value.mensaje,this.oferta.estado).
          subscribe((oferta)=>
          {
          });
          alert("Se borro la oferta "+ this.oferta.codigo + ". Se le notificara a los solicitantes que tenian una solicitud o entrevista.");
          this.router.navigate([this.router.url]);
        },
        (error:HttpErrorResponse)=>{
          alert(error.message);
        });
  }

  finalizar(template : TemplateRef<any>) {
    if (this.entrevista) {
      this.entrevistaService.getEntrevistasOferta(String(this.oferta.codigo)).subscribe({
        next: (entrevistas) => {
          this.entrevistas =entrevistas;
          this.openModalFinalizar(template)
        }
      })
    } else {
      this.postulacionService.cambiarEstadoOferta(this.oferta, EstadoOferta.ENT).subscribe({
        next: (oferta) => {
          alert("Se ha finalizado el proceso de seleccion para la oferta: " + this.oferta.codigo);
          this.router.navigate([this.router.url]);
        },
        error: (error) => {
          alert(error.message);
        }
      })
    }
  }

  irADetalles(): string{
    return "../oferta/" + this.oferta.codigo;
  }

  validarActiva(estado : string):boolean{
    return (estado===EstadoOferta.ACT);
  }

  validarFinalizado(estado : string):boolean{
    return (estado===EstadoOferta.FIN);
  }

  irAEditar():string{
    return "../nueva-oferta/" + this.oferta.codigo;
  }


  openModal(template : TemplateRef<any>){
    this.modalRef = this.modalService.show(template)
  }

  openModalFinalizar(template : TemplateRef<any>){
    this.modalRef = this.modalService.show(template)
  }

  irAListado() {
    if (this.entrevista) {
      return "../entrevista/" + this.oferta.codigo;
    } else if (this.solicitante){
      return this.irADetalles();
    }else if (this.postulaciones){
      return "../postulacion/" + this.oferta.codigo;
    } else {
      return "../inicio";
    }
  }
}
