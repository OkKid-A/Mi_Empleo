import {Component, OnInit} from '@angular/core';
import {Postulacion} from "../../../entities/postulacion";
import {SolicitudService} from "../../../services/ofertas/solicitud.service";
import {EstadoOferta} from "../../../share/estado-oferta";
import {Oferta} from "../../../entities/oferta";

@Component({
  selector: 'app-postulaciones-view',
  templateUrl: './postulaciones-view.component.html',
  styleUrls: ['./postulaciones-view.component.css']
})
export class PostulacionesViewComponent implements OnInit{
  postulacionesSeleccionadas: Oferta[] = []
  error! : string

  constructor(private solicitudService : SolicitudService) {
  }

  ngOnInit(): void {
    this.selectEstado(EstadoOferta.ACT)
  }

  selectEstado(estado: string) {
    this.solicitudService.getSolicitudesByEstado(estado).subscribe((ofertas) =>{
      this.postulacionesSeleccionadas = ofertas;
    })
  }


  protected readonly EstadoOferta = EstadoOferta;
}
