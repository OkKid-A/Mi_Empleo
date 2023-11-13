import { Component } from '@angular/core';
import {Oferta} from "../../../entities/oferta";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {EstadoOferta} from "../../../share/estado-oferta";

@Component({
  selector: 'app-emp-entrevistas-view',
  templateUrl: './emp-entrevistas-view.component.html',
  styleUrls: ['./emp-entrevistas-view.component.css']
})
export class EmpEntrevistasViewComponent {

  ofertas : Oferta[] = []
  estado: boolean = true

  constructor(private ofertaService : OfertaService) {
  }

  ngOnInit(): void {
    this.ofertaService.getOfertasByEstado(EstadoOferta.ENT).subscribe((ofertas)=>{
      this.ofertas = ofertas;
    })
  }

}
