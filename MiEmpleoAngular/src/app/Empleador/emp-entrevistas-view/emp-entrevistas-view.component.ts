import { Component } from '@angular/core';
import {Oferta} from "../../../entities/oferta";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {EstadoOferta} from "../../../share/estado-oferta";
import {LocalStorageVariables} from "../../../share/local-storage-variables";

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
    this.ofertaService.getOfertasByEstado(EstadoOferta.ENT,localStorage.getItem(LocalStorageVariables.LOCAL_USER)??"").
    subscribe((ofertas)=>{
      this.ofertas = ofertas;
    })
  }

}
