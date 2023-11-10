package org.cunoc.mi_empleo_api.Usuario;

public enum TipoUsuario {

    ADMIN(4),
    EMPLEADOR(3),
    SOLICITANTE(2),
    INVITADO(1),
    NO_USER(0);

    private final int nivel;

    TipoUsuario(int nivel) {
        this.nivel = nivel;
    }

    public static TipoUsuario clasificarAcceso(int nivel) {
        switch (nivel) {
            case 0:
                return NO_USER;
            case 1:
                return INVITADO;
            case 2:
                return SOLICITANTE;
            case 3:
                return EMPLEADOR;
            case 4:
                return ADMIN;
            default:
                return null;
        }
    }

    public int getNivel() {
        return nivel;
    }
}


