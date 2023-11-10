package org.cunoc.mi_empleo_api.Usuario.Empleador;

import org.cunoc.mi_empleo_api.Usuario.Usuario;

public class Empleador extends Usuario {

    private int tarjetaID;
    private String vision;
    private String mision;

    public Empleador() {
    }

    public int getTarjetaID() {
        return tarjetaID;
    }

    public void setTarjetaID(int tarjetaID) {
        this.tarjetaID = tarjetaID;
    }

    public String getVision() {
        return vision;
    }

    public void setVision(String vision) {
        this.vision = vision;
    }

    public String getMision() {
        return mision;
    }

    public void setMision(String mision) {
        this.mision = mision;
    }
}
