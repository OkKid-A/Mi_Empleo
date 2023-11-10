package org.cunoc.mi_empleo_api.Empleo;

import java.sql.Time;
import java.util.Date;

public class Entrevista {

    private int codigo;
    private int solicitante;
    private Date fecha;
    private Time hora;
    private String estado;
    private String ubicacion;
    private String notas;
    private int codigoOferta;

    public Entrevista() {
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(int solicitante) {
        this.solicitante = solicitante;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public int getCodigoOferta() {
        return codigoOferta;
    }

    public void setCodigoOferta(int codigoOferta) {
        this.codigoOferta = codigoOferta;
    }
}
