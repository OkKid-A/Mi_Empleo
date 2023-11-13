import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Entrevista} from "../../../entities/entrevista";
import {EntrevistaService} from "../../../services/ofertas/entrevista.service";
import {PostulacionService} from "../../../services/ofertas/postulacion.service";
import {NotificationService} from "../../../services/notification/notification.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {ComisionService} from "../../../services/ofertas/comision.service";

@Component({
  selector: 'app-entrevistas-table',
  templateUrl: './entrevistas-table.component.html',
  styleUrls: ['./entrevistas-table.component.css']
})
export class EntrevistasTableComponent {

  @Output() closeMod : EventEmitter<boolean> = new EventEmitter<boolean>()
  @Input() entrevistas : Entrevista[] = [];

  constructor(private postulacionService : PostulacionService,
              private notiService : NotificationService,
              private router: Router,
              private comisionService : ComisionService) {
  }

  seleccionarGanador(entrevista : Entrevista){
    this.postulacionService.seleccionarGanadorOferta(String(entrevista.solicitante),String(entrevista.codigoOferta)).subscribe({
      next: (oferta) => {
        this.notiService.enviarEmailAUsuariop(String(entrevista.solicitante),"Has ganado el empleo!",
            "Se te selecciono como el ganador del empleo: "+ oferta.nombre).subscribe({
          next: () => {
            this.comisionService.sumarComision(String(entrevista.codigoOferta)).subscribe({
              next: (valor) =>{
                alert("El solicitante " + entrevista.nombreUsuario + " ha sido notificado del empleo! Se te han cargado $"+valor);
                this.router.navigate([this.router.url]);
                this.closeMod.emit();
              }
            })
          }
        })

      },
      error: (error: HttpErrorResponse) =>{
        alert(error.message)
    }
    })
  }
}
