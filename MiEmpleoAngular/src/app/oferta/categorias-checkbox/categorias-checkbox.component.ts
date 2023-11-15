import {Component, EventEmitter, Input, Output} from '@angular/core';
import {Categoria} from "../../../entities/categoria";

@Component({
  selector: 'app-categorias-checkbox',
  templateUrl: './categorias-checkbox.component.html',
  styleUrls: ['./categorias-checkbox.component.css']
})
export class CategoriasCheckboxComponent {

  @Output() seleccionada : EventEmitter<string> = new EventEmitter<string>()
  @Input() categoria! : Categoria;

  constructor() {
  }

  enviarId(){
    this.seleccionada.emit(String(this.categoria.codigo));
  }
}
