import { Injectable } from '@angular/core';
import {HttpClient, HttpErrorResponse} from "@angular/common/http";
import {catchError, Observable, of, throwError} from "rxjs";
import {Oferta} from "../../entities/oferta";
import {ApiUrl} from "../../share/api-url";
import {Solicitud} from "../../entities/solicitud";
import {Postulacion} from "../../entities/postulacion";
import {Categoria} from "../../entities/categoria";
import {LocalStorageVariables} from "../../share/local-storage-variables";

@Injectable({
  providedIn: 'root'
})
export class OfertaService {

  constructor(private http:HttpClient,
              ) { }


  public editarOferta(oferta: Oferta, codigo: string):Observable<Oferta>{
    return this.http.put<Oferta>(ApiUrl.API_URL+"ofertas?codigo="+codigo,oferta)
  }
  public borrarOferta(codigo: number, estado:string){
    return this.http.delete(ApiUrl.API_URL+"ofertas?codigo="+codigo+"&estado="+estado)
  }
  public crearOferta(oferta: Oferta):Observable<Oferta>{
    console.log(oferta.categoria);
    return this.http.post<Oferta>(ApiUrl.API_URL + "ofertas",oferta)
  }

  public getOfertas(searchKey: string,filtro : number): Observable<Oferta[]> {
    return this.http.get<Oferta[]>(ApiUrl.API_URL + "ofertas?searchKey="+searchKey+"&filtro="+filtro)
  }

  public getOfertaById(id : string): Observable<Oferta> {
    return this.http.get<Oferta>(ApiUrl.API_URL+"oferta-detalles?codigo="+id)
  }


  public getOfertasByEstado(estado: string, empresa : string) : Observable<Oferta[]> {
    return this.http.get<Oferta[]>(ApiUrl.API_URL + "ofertas?estado=" + estado+ "&empresa="+empresa).pipe(
    )
  }

  public getAllCategorias(): Observable<Categoria[]>{
    return this.http.get<Categoria[]>(ApiUrl.API_URL + "categorias")
  }

  getOfertasByEmpresa(searchKey: string) : Observable<Oferta[]>{
    const empresa = localStorage.getItem(LocalStorageVariables.LOCAL_USER);
    return this.http.get<Oferta[]>(ApiUrl.API_URL+"ofertas?empresa="+empresa+"&searchKey="+searchKey)
  }
  public manejarError(err:HttpErrorResponse){
    throwError(err)
  }


}
