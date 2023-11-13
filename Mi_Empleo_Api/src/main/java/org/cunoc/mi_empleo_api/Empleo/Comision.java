package org.cunoc.mi_empleo_api.Empleo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;

import java.util.Date;

public class Comision {

    private int codigoEmpleador;
    private float valor;
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", lenient = OptBoolean.FALSE)
    private Date fecha;

    public Comision(int codigoEmpleador, float valor, Date fecha) {
        this.codigoEmpleador = codigoEmpleador;
        this.valor = valor;
        this.fecha = fecha;
    }

    public int getCodigoEmpleador() {
        return codigoEmpleador;
    }

    public void setCodigoEmpleador(int codigoEmpleador) {
        this.codigoEmpleador = codigoEmpleador;
    }

    public float getValor() {
        return valor;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
}
