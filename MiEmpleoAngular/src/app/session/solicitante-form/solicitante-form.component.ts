import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup} from "@angular/forms";
import {UsuarioService} from "../../../services/Usuarios/usuario.service";
import {SolicitanteService} from "../../../services/Usuarios/solicitante.service";
import {Categoria} from "../../../entities/categoria";
import {OfertaService} from "../../../services/ofertas/oferta.service";
import {Router} from "@angular/router";
import {HttpErrorResponse} from "@angular/common/http";
import {LocalStorageVariables} from "../../../share/local-storage-variables";

@Component({
  selector: 'app-solicitante-form',
  templateUrl: './solicitante-form.component.html',
  styleUrls: ['./solicitante-form.component.css']
})
export class SolicitanteFormComponent implements OnInit{

  curriculum! : File;
  actualizado : boolean = false;
  categoriasID: string[] = [];
  categorias : Categoria[] = [];

  constructor(private service: SolicitanteService,
              private ofertaService: OfertaService,
              private router:Router) {
  }

  ngOnInit(): void {
    this.ofertaService.getAllCategorias().subscribe({
        next: (categorias)=>{
          this.categorias = categorias;
        }
    })
  }

  createFile(event : Event){
    this.actualizado = false;
    let files = (event.target as HTMLInputElement).files;
    if (files!=null){
      this.curriculum = files[0];
    } else {
      alert("No se ha seleccionado un archivo.");
    }
  }

  cambiarCateSeleccionadas(seleccionada:string){
    if (this.categoriasID.includes(seleccionada)){
      this.categoriasID = this.categoriasID.filter((seleccionada) => seleccionada !== seleccionada);
    } else {
      this.categoriasID.push(seleccionada);
    }
    console.log(seleccionada)
  }

  subirDatos(){
  this.service.subirPreferencias(this.categoriasID,localStorage.getItem(LocalStorageVariables.LOCAL_USER)??"").subscribe({
    next:() =>{
      this.service.sendInfoInicio(this.curriculum,localStorage.getItem(LocalStorageVariables.LOCAL_USER)??"").subscribe({
        next:() =>{
          alert("Se actualizo con exito");
          this.router.navigate([this.router.url]);
        }
      })
    },
    error:(err:HttpErrorResponse)=>{
      alert(err.message);
    }
  })
    }

    invalidar():boolean{
      if (this.categoriasID.length<1 || this.curriculum===null){
        return true;
      } else {
        return false;
      }
    }
}
