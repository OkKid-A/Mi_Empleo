import {Component, OnInit} from '@angular/core';
import {FormBuilder} from "@angular/forms";
import {Oferta} from "../../../entities/oferta";
import {OfertaService} from "../../../services/ofertas/oferta.service";

@Component({
  selector: 'app-solicitante-buscador',
  templateUrl: './solicitante-buscador.component.html',
  styleUrls: ['./solicitante-buscador.component.css']
})
export class SolicitanteBuscadorComponent implements OnInit{
  searchKey = "";
  filtro = 0;
  ofertas : Oferta[] = []
  ofertaForm = this.fB.nonNullable.group({
    searchKey :"",
    filtro : 0
  });


  constructor(private fB: FormBuilder,
              private ofertaService : OfertaService) {
  }

  ngOnInit(): void {
    this.buscar();
  }

  buscar(){
    this.ofertaService.getOfertas(this.searchKey,this.filtro).subscribe((ofertas) => {
      this.ofertas = ofertas;
    })
  }

  onBuscarSubmit():void{
    this.searchKey = this.ofertaForm.value.searchKey ?? '';
    this.buscar();
  }

  changeFiltro():void{
    this.filtro = this.ofertaForm.value.filtro ?? 0
  }
}
