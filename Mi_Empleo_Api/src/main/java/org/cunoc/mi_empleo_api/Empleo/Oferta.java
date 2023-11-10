package org.cunoc.mi_empleo_api.Empleo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Oferta {

    private String empresa;
    private String categoria;
    private String ubicacion;
    private String estado;
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd")

    private Date fechaPublicacion;
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-mm-dd")

    private Date fechaLimite;
    private String detalles;
    private int ganador;
    private String modalidad;
    private float salario;
    private int codigo;
    private String nombre;
    private String descripcion;

    public Oferta() {
    }

    public Oferta(String empresa, String categoria, String ubicacion, String estado, Date fechaPublicacion,
                  Date fechaLimite, String detalles, int ganador, String modalidad, float salario, int codigo,
                  String nombre, String descripcion) {
        this.empresa = empresa;
        this.categoria = categoria;
        this.ubicacion = ubicacion;
        this.estado = estado;
        this.fechaPublicacion = fechaPublicacion;
        this.fechaLimite = fechaLimite;
        this.detalles = detalles;
        this.ganador = ganador;
        this.modalidad = modalidad;
        this.salario = salario;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(Date fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Date getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public String getDetalles() {
        return detalles;
    }

    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }

    public int getGanador() {
        return ganador;
    }

    public void setGanador(int ganador) {
        this.ganador = ganador;
    }

    public String  getModalidad() {
        return modalidad;
    }

    public void setModalidad(String modalidad) {
        this.modalidad = modalidad;
    }

    public float getSalario() {
        return salario;
    }

    public void setSalario(float salario) {
        this.salario = salario;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
