package org.cunoc.mi_empleo_api.Usuario.Solicitante;

public class Postulacion {

    private String estado;
    private String nombreOferta;
    private String empresa;
    private String fechaLimite;

    public Postulacion(String estado, String nombreOferta, String empresa, String fechaLimite) {
        this.estado = estado;
        this.nombreOferta = nombreOferta;
        this.empresa = empresa;
        this.fechaLimite = fechaLimite;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombreOferta() {
        return nombreOferta;
    }

    public void setNombreOferta(String nombreOferta) {
        this.nombreOferta = nombreOferta;
    }

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }
}
