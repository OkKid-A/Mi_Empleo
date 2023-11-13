package org.cunoc.mi_empleo_api.Empleo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Time;
import java.util.Date;

public class Entrevista {

    private int codigo;
    private int solicitante;
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private Date fecha;
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "hh:mm:ss a")
    private Time hora;
    private String estado;
    private String ubicacion;
    private String notas;
    private int codigoOferta;
    private String nombreUsuario;

    public Entrevista() {
    }

    public Entrevista(int codigo, int solicitante, Date fecha, Time hora, String estado, String ubicacion, String notas, int codigoOferta, String nombreUsuario) {
        this.codigo = codigo;
        this.solicitante = solicitante;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.ubicacion = ubicacion;
        this.notas = notas;
        this.codigoOferta = codigoOferta;
        this.nombreUsuario = nombreUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
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
