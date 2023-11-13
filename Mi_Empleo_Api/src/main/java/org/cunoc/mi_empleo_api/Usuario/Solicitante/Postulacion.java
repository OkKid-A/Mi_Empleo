package org.cunoc.mi_empleo_api.Usuario.Solicitante;

public class Postulacion {

    private String nombreUsuario;
    private String direccion;
    private String[] telefono;
    private String correo;
    private int codigoOferta;
    private int codigoUsuario;
    private String mensaje;
    private String oferta;

    public Postulacion() {
    }

    public Postulacion(String nombreUsuario, String direccion, String[] telefono, String correo, int codigoOferta, int codigoUsuario, String mensaje, String oferta) {
        this.nombreUsuario = nombreUsuario;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.codigoOferta = codigoOferta;
        this.codigoUsuario = codigoUsuario;
        this.mensaje = mensaje;
        this.oferta = oferta;
    }

    public String getOferta() {
        return oferta;
    }

    public void setOferta(String oferta) {
        this.oferta = oferta;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String[] getTelefono() {
        return telefono;
    }

    public void setTelefono(String[] telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public int getCodigoOferta() {
        return codigoOferta;
    }

    public void setCodigoOferta(int codigoOferta) {
        this.codigoOferta = codigoOferta;
    }

    public int getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(int codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
