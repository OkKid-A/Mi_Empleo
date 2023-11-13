import { Component } from '@angular/core';
import {Oferta} from "../../../entities/oferta";
import {FormBuilder} from "@angular/forms";
import {OfertaService} from "../../../services/ofertas/oferta.service";

@Component({
  selector: 'app-empleador-ofertas-view',
  templateUrl: './empleador-ofertas-view.component.html',
  styleUrls: ['./empleador-ofertas-view.component.css']
})
export class EmpleadorOfertasViewComponent {
  searchKey = "";
  ofertas : Oferta[] = []
  ofertaForm = this.fB.nonNullable.group({
    searchKey :"",
  });


  constructor(private fB: FormBuilder,
              private ofertaService : OfertaService) {
  }

  ngOnInit(): void {
    this.buscar();
  }

  buscar(){
    this.ofertaService.getOfertas(this.searchKey,0).subscribe((ofertas) => {
      this.ofertas = ofertas;
    })
  }

  onBuscarSubmit():void{
    this.searchKey = this.ofertaForm.value.searchKey ?? '';
    this.buscar();
  }

}
