import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {OfertaService} from "../../../../services/ofertas/oferta.service";
import {Categoria} from "../../../../entities/categoria";
import {FormBuilder, FormGroup} from "@angular/forms";

@Component({
  selector: 'app-categoria-selector',
  templateUrl: './categoria-selector.component.html',
  styleUrls: ['./categoria-selector.component.css']
})
export class CategoriaSelectorComponent implements OnInit{
  @Input() selected: number | undefined;
  @Output() categoriaSeleccionada = new EventEmitter<string>()
  categorias : Categoria[] = [];
  cateForm : FormGroup;

  constructor(private ofertaService: OfertaService,
              private fB: FormBuilder) {
    this.cateForm = this.fB.group({
      categoria : ['']
    })
  }

  ngOnInit(): void {
    this.ofertaService.getAllCategorias().subscribe((categorias : Categoria[]) => {
      this.categorias = categorias;
    })

    if (this.selected){
      this.cateForm.setValue({categoria:this.selected});
    }
  }


  changeCategoria() {
    this.categoriaSeleccionada.emit(this.cateForm.value.categoria);
  }

}
