import { Component } from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {Oferta} from "../../../entities/oferta";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {LocalStorageVariables} from "../../../share/local-storage-variables";

@Component({
  selector: 'app-editar-oferta-form',
  templateUrl: './editar-oferta-form.component.html',
  styleUrls: ['./editar-oferta-form.component.css']
})
export class EditarOfertaFormComponent {
  ofertaForm!: FormGroup;
  oferta!: Oferta
  modificado : boolean;

  constructor(private fB: FormBuilder,
              private ofertaService: OfertaService) {
    this.modificado = false;
  }


}

