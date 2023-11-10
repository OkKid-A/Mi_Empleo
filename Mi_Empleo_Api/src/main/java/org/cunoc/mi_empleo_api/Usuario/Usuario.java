package org.cunoc.mi_empleo_api.Usuario;


import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class Usuario {

    private String username;
    private String nombre;
    private String codigo;
    private String email;
    private String direccion;
    @JsonFormat(shape =  JsonFormat.Shape.STRING, pattern = "yyyy-Mm-dd")
    private Date dob;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private String tipo;
    private int cui;

    public Usuario() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getCui() {
        return cui;
    }

    public void setCui(int cui) {
        this.cui = cui;
    }
}
