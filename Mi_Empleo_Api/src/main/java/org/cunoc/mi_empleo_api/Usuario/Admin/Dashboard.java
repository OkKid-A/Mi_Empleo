package org.cunoc.mi_empleo_api.Usuario.Admin;

public class Dashboard {

    private int solicitantes;
    private int empleadores;
    private float ganancias;
    private int visitas;

    public Dashboard(int solicitantes, int empleadores, float ganancias, int visitas) {
        this.solicitantes = solicitantes;
        this.empleadores = empleadores;
        this.ganancias = ganancias;
        this.visitas = visitas;
    }

    public int getSolicitantes() {
        return solicitantes;
    }

    public void setSolicitantes(int solicitantes) {
        this.solicitantes = solicitantes;
    }

    public int getEmpleadores() {
        return empleadores;
    }

    public void setEmpleadores(int empleadores) {
        this.empleadores = empleadores;
    }

    public float getGanancias() {
        return ganancias;
    }

    public void setGanancias(float ganancias) {
        this.ganancias = ganancias;
    }

    public int getVisitas() {
        return visitas;
    }

    public void setVisitas(int visitas) {
        this.visitas = visitas;
    }
}
