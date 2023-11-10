package org.cunoc.mi_empleo_api.Empleo;

public class Solicitud {

    private int codigo_oferta;
    private int codigo;
    private int usuario;
    private String mensaje;

    public Solicitud() {
    }

    public int getCodigo_oferta() {
        return codigo_oferta;
    }

    public void setCodigo_oferta(int codigo_oferta) {
        this.codigo_oferta = codigo_oferta;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public int getUsuario() {
        return usuario;
    }

    public void setUsuario(int usuario) {
        this.usuario = usuario;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
}
