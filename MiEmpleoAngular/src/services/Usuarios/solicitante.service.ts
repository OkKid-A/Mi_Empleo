import { Injectable } from '@angular/core';
import {Observable} from "rxjs";
import {ApiUrl} from "../../share/api-url";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SolicitanteService {

  readonly SOLIC_URL = "solicitante";

  constructor(private http: HttpClient) { }

  public sendInfoInicio(archivo: File, soli:string): Observable<void>{
    let formData: FormData = new FormData();
    formData.append("curriculum",archivo, archivo.name)
    return this.http.post<void>(ApiUrl.API_URL+this.SOLIC_URL+"?solicitante="+soli,formData)
  }

  public subirPreferencias(categorias: string[], soli:string): Observable<void>{
    return this.http.put<void>(ApiUrl.API_URL+this.SOLIC_URL+"?solicitante="+soli,categorias)
  }
}
