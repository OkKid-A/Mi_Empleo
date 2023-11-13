import {Component, Input, OnInit} from '@angular/core';
import {Postulacion} from "../../../entities/postulacion";
import {SolicitudService} from "../../../services/ofertas/solicitud.service";
import {Oferta} from "../../../entities/oferta";

@Component({
  selector: 'app-postulaciones-list',
  templateUrl: './postulaciones-list.component.html',
  styleUrls: ['./postulaciones-list.component.css']
})
export class PostulacionesListComponent implements OnInit {
  @Input()  postulaciones : Oferta[] = []

  constructor(private solicitudService : SolicitudService,
              ) {
  }

  ngOnInit(): void {
  }


}
