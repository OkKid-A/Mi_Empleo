import {Component, Input, OnInit, TemplateRef} from '@angular/core';
import {Entrevista} from "../../../../entities/entrevista";
import {BsModalRef, BsModalService} from "ngx-bootstrap/modal";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {EntrevistaService} from "../../../../services/ofertas/entrevista.service";
import {Router} from "@angular/router";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {HttpErrorResponse} from "@angular/common/http";
import {ComisionService} from "../../../../services/ofertas/comision.service";

@Component({
  selector: 'app-entrevista-card',
  templateUrl: './entrevista-card.component.html',
  styleUrls: ['./entrevista-card.component.css']
})
export class EntrevistaCardComponent implements OnInit{

  @Input() entrevista! : Entrevista
  notasForm! : FormGroup
  modalRef! : BsModalRef

  constructor(private modalService: BsModalService,
              private fB: FormBuilder,
              private entrevistaService: EntrevistaService,
              private router: Router,
              private comisionService: ComisionService) {
  }

  openModal(template : TemplateRef<any>){
    this.modalRef = this.modalService.show(template)
  }

  ngOnInit(): void {
    this.notasForm = this.fB.group({
      notas: [null,[Validators.required, Validators.maxLength(256)]]
    })
  }

  finalizarEntrevista(){
    this.entrevistaService.finalizarEntrevista(this.entrevista,this.notasForm.value.notas).subscribe({
      next:(entrevista) =>{
            alert("Se finalizo la entrevista con exito.");
            this.router.navigate([this.router.url])
    }, error:(error:HttpErrorResponse) =>{
        alert(error.message)
    }
    })
  }
}
