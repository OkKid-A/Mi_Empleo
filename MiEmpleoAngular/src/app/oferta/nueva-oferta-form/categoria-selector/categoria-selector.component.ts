import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {OfertaService} from "../../../../services/ofertas/oferta.service";
import {Categoria} from "../../../../entities/categoria";
import {FormBuilder} from "@angular/forms";

@Component({
  selector: 'app-categoria-selector',
  templateUrl: './categoria-selector.component.html',
  styleUrls: ['./categoria-selector.component.css']
})
export class CategoriaSelectorComponent implements OnInit{
  @Input() selected: any;
  @Output() categoriaSeleccionada : EventEmitter<string> = new EventEmitter<string>()
  categorias : Categoria[] = [];
  cateForm = this.fB.nonNullable.group({
    categoria : ''
  })

  constructor(private ofertaService: OfertaService,
              private fB: FormBuilder) {
  }

  ngOnInit(): void {
    this.categoriaSeleccionada = this.selected;
    this.ofertaService.getAllCategorias().subscribe((categorias : Categoria[]) => {
      this.categorias = categorias;
    })
  }


  changeCategoria() {
    this.categoriaSeleccionada.emit(this.cateForm.value.categoria)
  }
}
