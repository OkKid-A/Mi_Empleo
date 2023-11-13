package org.cunoc.mi_empleo_api.Exceptions;

public class NoExisteException extends Exception implements CustomException{

    public NoExisteException(String message) {
        super(message);
    }


    @Override
    public String getErrorMessage() {
        return "No existe en la base de datos";
    }

    public void printMessage(){
        System.out.println(getMessage());
    }
}
