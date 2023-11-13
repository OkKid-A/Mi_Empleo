import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import {Entrevista} from "../../../entities/entrevista";
import {EntrevistaService} from "../../../services/ofertas/entrevista.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-entrevista-list',
  templateUrl: './entrevista-list.component.html',
  styleUrls: ['./entrevista-list.component.css']
})
export class EntrevistaListComponent implements OnInit{

  entrevistas : Entrevista[] = []
  codigo! : string;
  constructor(private route: ActivatedRoute,
              private entrevistaService: EntrevistaService) {
    this.route.paramMap.subscribe((params) => {
      if (params){
        this.codigo = params.get('codigo') ?? "";
      }
    })
  }

  ngOnInit(): void {
    this.entrevistaService.getEntrevistasOferta(this.codigo).subscribe({
      next:(entrevistas) =>{
        this.entrevistas = entrevistas;
      },
      error: (error: HttpErrorResponse) =>{
        alert(error.message)
    }
    })
  }


}
