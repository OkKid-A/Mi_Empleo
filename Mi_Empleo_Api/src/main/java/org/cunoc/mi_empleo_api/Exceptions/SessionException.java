package org.cunoc.mi_empleo_api.Exceptions;

public class SessionException extends Exception implements CustomException{

    public SessionException(String message) {
        super(message);
    }

    public String getErrorMessage(){
        return "Hubo un error al crear la sesion";
    }
}
