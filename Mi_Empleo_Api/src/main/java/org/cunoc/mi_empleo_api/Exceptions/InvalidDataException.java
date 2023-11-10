package org.cunoc.mi_empleo_api.Exceptions;

public class InvalidDataException extends Exception implements CustomException{

    public InvalidDataException() {
    }

    public InvalidDataException(String message) {
        super(message);
    }

    @Override
    public String getErrorMessage() {
        return "Se encontro la informacion equivocada.";
    }
}
