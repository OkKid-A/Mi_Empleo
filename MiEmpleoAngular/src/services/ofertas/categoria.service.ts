import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Categoria} from "../../entities/categoria";
import {Observable} from "rxjs";
import {ApiUrl} from "../../share/api-url";

@Injectable({
  providedIn: 'root'
})
export class CategoriaService {

  constructor(private http: HttpClient) { }

  public editarCategoria(categoria:Categoria):Observable<void>{
    return this.http.put<void>(ApiUrl.API_URL+"categorias",categoria);
  }
}
