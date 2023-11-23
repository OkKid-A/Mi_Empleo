import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {LocalStorageVariables} from "../../share/local-storage-variables";
import {ApiUrl} from "../../share/api-url";

@Injectable({
  providedIn: 'root'
})
export class EmpReportesService {

  readonly EMPREP_URL = "emp-reportes"

  constructor(private http : HttpClient) { }

  public getOfertasReport(tipo: number, estado: string, fechaInicial:string, fechaFinal : string){
    const empleador = localStorage.getItem(LocalStorageVariables.LOCAL_USER);
    return this.http.get(ApiUrl.API_URL+this.EMPREP_URL+"?tipo="+tipo+"&emp="+empleador+"&fechaInicial="+fechaInicial+
      "&fechaFinal="+fechaFinal+"&estado="+estado
      ,{responseType: 'blob'});
  }


}
