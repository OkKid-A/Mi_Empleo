import {Component, OnInit} from '@angular/core';
import {EstadoOferta} from "../../../share/estado-oferta";
import {Oferta} from "../../../entities/oferta";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {of} from "rxjs";

@Component({
  selector: 'app-empleador-postulaciones-view',
  templateUrl: './empleador-postulaciones-view.component.html',
  styleUrls: ['./empleador-postulaciones-view.component.css']
})
export class EmpleadorPostulacionesViewComponent implements OnInit{

    ofertas : Oferta[] = []
    estado: boolean = true

    constructor(private ofertaService : OfertaService) {
    }

    ngOnInit(): void {
        this.ofertaService.getOfertasByEstado(EstadoOferta.PEND).subscribe((ofertas)=>{
            this.ofertas = ofertas;
        })
    }
}
