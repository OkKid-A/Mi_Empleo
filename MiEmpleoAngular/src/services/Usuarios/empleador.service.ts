import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Tarjeta} from "../../entities/tarjeta";
import {LocalStorageVariables} from "../../share/local-storage-variables";
import {ApiUrl} from "../../share/api-url";

@Injectable({
  providedIn: 'root'
})
export class EmpleadorService {

  constructor(private http: HttpClient) { }

  public uploadEmpleador(tarjeta: Tarjeta, mision: string, vision: string):Observable<void>{
    const usuario = localStorage.getItem(LocalStorageVariables.LOCAL_USER);
    return this.http.post<void>(ApiUrl.API_URL+"empleador?usuario="+usuario+"&mision="+mision+"&vision="+vision,tarjeta)
  }

}
