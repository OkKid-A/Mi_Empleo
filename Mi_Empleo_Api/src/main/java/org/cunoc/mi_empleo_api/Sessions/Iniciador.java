package org.cunoc.mi_empleo_api.Sessions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Exceptions.SessionException;

import java.net.http.HttpResponse;

public class Iniciador {

    public Iniciador() {
    }

    public void crearSesion(HttpServletRequest req, HttpServletResponse resp) throws SessionException{
            if (!revisarSesion(resp,req)){
                Conector conector = new Conector();
                req.getSession(true).setAttribute("conector",conector);
            } else {
                throw new SessionException("Ya existe una sesion");
            }
    }

    private boolean revisarSesion(HttpServletResponse resp, HttpServletRequest req) throws SessionException {
        try {
            if (req.getSession(false).getAttribute("conector") != null) {
                return true;
            } else {
                return false;
            }
        } catch (NullPointerException e){
            return false;
        }
    }

    public Conector getConector(HttpServletResponse resp, HttpServletRequest req){
        try {
            crearSesion(req,resp);
        } catch (SessionException e) {
            e.getErrorMessage();
        }
        return (Conector) req.getSession(true).getAttribute("conector");
    }
}
