import {Time} from "@angular/common";

export interface Entrevista {
    codigo: number,
    solicitante: number,
    fecha: string,
    hora: Time,
    estado: string,
    ubicacion: string,
    notas: string,
    codigoOferta: number,
    nombreUsuario: string
}
