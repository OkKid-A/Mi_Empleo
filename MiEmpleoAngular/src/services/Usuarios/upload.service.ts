import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {ApiUrl} from "../../share/api-url";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UploadService {


  constructor(private http: HttpClient) { }

  public sendArchivoEntrada(archivo: File): Observable<void>{
    let formData: FormData = new FormData();
    formData.append("archivoEntrada",archivo, archivo.name)
    return this.http.post<void>(ApiUrl.API_URL+"upload",formData)
  }
}
