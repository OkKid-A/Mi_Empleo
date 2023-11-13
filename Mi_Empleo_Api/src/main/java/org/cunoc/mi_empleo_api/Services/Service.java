package org.cunoc.mi_empleo_api.Services;

import org.cunoc.mi_empleo_api.DB.Conector;

public class Service {

    private Conector conector;

    public Conector getConector() {
        return conector;
    }

    public Service(Conector conector) {
        this.conector = conector;
    }
}
