import {Component, Input, OnInit, TemplateRef} from '@angular/core';
import {Postulacion} from "../../../entities/postulacion";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {EntrevistaService} from "../../../services/ofertas/entrevista.service";
import {Entrevista} from "../../../entities/entrevista";
import {EstadoEntrevista} from "../../../share/estado-entrevista";
import {NotificationService} from "../../../services/notification/notification.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-candidato-card',
  templateUrl: './candidato-card.component.html',
  styleUrls: ['./candidato-card.component.css']
})
export class CandidatoCardComponent implements OnInit{
  @Input() postulacion! : Postulacion
  entrevistaForm! : FormGroup
  modalRef!: BsModalRef
  entrevista! : Entrevista
  creada: boolean
  constructor(private modalService: BsModalService,
              private formBuilder: FormBuilder,
              private entreService: EntrevistaService,
              private notificationService : NotificationService,
              private router: Router) {
    this.creada = false;
  }

  ngOnInit(): void {
    this.entrevistaForm = this.formBuilder.group({
      fecha: [null,[Validators.required]],
      hora: [null,[Validators.required]],
      ubicacion: [null, [Validators.required, Validators.maxLength(60)]],
      solicitante: this.postulacion.codigoUsuario,
      estado : EstadoEntrevista.PEN,
      codigoOferta: this.postulacion.codigoOferta
    })
  }

  submitEntrevista(){
    if (this.entrevistaForm.valid) {
      this.entrevista = this.entrevistaForm.value as Entrevista;
      this.entreService.createEntrevista(this.entrevista).subscribe({
        next: (nuevo: Entrevista) => {
          this.notificationService.enviarEmailAUsuariop(this.postulacion.codigoUsuario,"Has sido seleccionado para una entrevista!",
              "Lugar: "+ this.entrevista.ubicacion+"\n Hora: "+this.entrevista.hora+"\n Fecha: "+this.entrevista.fecha);
          this.entrevistaForm.reset()
          this.creada = true;
          this.router.navigate([this.router.url])
        },
        error: (err:any) => {
          alert("Error"+err.toString())
        }
      })
    } else {
      alert(this.entrevistaForm.errors)
    }
  }

  openModal(template : TemplateRef<any>){
    this.modalRef = this.modalService.show(template)
  }
}
