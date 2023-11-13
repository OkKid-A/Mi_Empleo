import {Component, Input, OnInit} from '@angular/core';
import {Oferta} from "../../../entities/oferta";
import {OfertaService} from "../../../services/ofertas/oferta.service";

@Component({
  selector: 'app-oferta-list',
  templateUrl: './oferta-list.component.html',
  styleUrls: ['./oferta-list.component.css']
})
export class OfertaListComponent implements OnInit{
  @Input() ofertas : Oferta[] = []
  @Input() empleador : boolean = false
  @Input() solicitante : boolean = false

  constructor(private ofServ : OfertaService) {
  }

  ngOnInit(): void {
  }
}
