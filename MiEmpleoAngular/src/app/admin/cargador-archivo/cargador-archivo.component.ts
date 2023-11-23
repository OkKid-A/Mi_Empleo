import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {UploadService} from "../../../services/Usuarios/upload.service";
import {error} from "@angular/compiler-cli/src/transformers/util";
import {HttpErrorResponse} from "@angular/common/http";

@Component({
  selector: 'app-cargador-archivo',
  templateUrl: './cargador-archivo.component.html',
  styleUrls: ['./cargador-archivo.component.css']
})
export class CargadorArchivoComponent implements OnInit{

  archivoEntrada! : File;
  actualizado : boolean = false;
  routerLogin = "../login"

  constructor(private uploadService: UploadService) {
  }

  ngOnInit(): void {
  }

  createFile(event : Event){
    this.actualizado = false;
    let files = (event.target as HTMLInputElement).files;
    if (files!=null){
      this.archivoEntrada = files[0];
    } else {
      alert("No se ha seleccionado un archivo.");
    }
  }

  subirArchivo(){
    this.actualizado = false;
    if (this.archivoEntrada!=null){
      this.uploadService.sendArchivoEntrada(this.archivoEntrada).subscribe({
        next:() => {
          this.actualizado = true;
        }, error:(error:HttpErrorResponse) =>{
          if (error.status==400){
            alert("Hubo un error al subir los datos.")
          } else {
            alert(error.message)
          }
        }
      });
    }
  }

}
