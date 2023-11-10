package org.cunoc.mi_empleo_api.Usuario.Solicitante;

import org.cunoc.mi_empleo_api.Usuario.Usuario;

public class Solicitante extends Usuario {

    private String cv_path;

    public Solicitante() {
    }

    public String getCv_path() {
        return cv_path;
    }

    public void setCv_path(String cv_path) {
        this.cv_path = cv_path;
    }
}
