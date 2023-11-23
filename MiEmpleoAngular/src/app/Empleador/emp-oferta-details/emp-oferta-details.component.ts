import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {EntrevistaService} from "../../../services/ofertas/entrevista.service";
import {Oferta} from "../../../entities/oferta";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {of} from "rxjs";

@Component({
  selector: 'app-emp-oferta-details',
  templateUrl: './emp-oferta-details.component.html',
  styleUrls: ['./emp-oferta-details.component.css']
})
export class EmpOfertaDetailsComponent implements OnInit{

  codigo! : string
  oferta! : Oferta

  constructor(private route: ActivatedRoute,
              private ofertaSerice: OfertaService) {
    this.route.paramMap.subscribe((params) => {
      if (params){
        this.codigo = params.get('codigo') ?? "";
      }
    })
  }

  ngOnInit(): void {
    this.ofertaSerice.getOfertaById(this.codigo).subscribe((oferta: Oferta) => {
      this.oferta = oferta;
    })
  }

  protected readonly of = of;
}
