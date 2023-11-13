import {Component, Input, Output} from '@angular/core';
import {Postulacion} from "../../../entities/postulacion";
import {SolicitudService} from "../../../services/ofertas/solicitud.service";
import {Oferta} from "../../../entities/oferta";
import {EstadoOferta} from "../../../share/estado-oferta";
import {HttpErrorResponse} from "@angular/common/http";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {ActivatedRoute, Router} from "@angular/router";
import {NotificationService} from "../../../services/notification/notification.service";

@Component({
  selector: 'app-postulacion-card',
  templateUrl: './postulacion-card.component.html',
  styleUrls: ['./postulacion-card.component.css']
})
export class PostulacionCardComponent {
  @Input()
  postulacion! : Oferta
  @Output()
  success! : string

  constructor(private solicitudService : SolicitudService,
              private router: Router,
              private route: ActivatedRoute,
              private notificacionService : NotificationService) {
  }

  retirarSolicitud(){

    this.solicitudService.borrar(this.postulacion).subscribe((oferta) =>
    {
      this.notificacionService.enviarEmailConOferta(this.postulacion.codigo,"Cancelacion de solicitud",
          "El usuario " + localStorage.getItem("usuarioActual")+ " cancelo la solicitud a tu oferta de empleo.").subscribe((oferta)=>
      {
      });
      alert("Se borro la solicitud a la oferta "+ this.postulacion.codigo);
      this.router.navigate([this.router.url])
    },
      (error:HttpErrorResponse)=>{
      alert(error.message)
    });
  }

  validarBoton(estado: string):boolean{
    return estado === EstadoOferta.ACT;
  }
}
