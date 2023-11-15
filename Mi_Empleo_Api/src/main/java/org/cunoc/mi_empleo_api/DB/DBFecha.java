package org.cunoc.mi_empleo_api.DB;

import org.cunoc.mi_empleo_api.Empleo.EstadoOferta;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Empleos.OfertaService;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DBFecha {

    private Conector conector;
    private FormateoDeFechas formateoDeFechas;
    public DBFecha(Conector conector) {
        this.conector = conector;
        this.formateoDeFechas = new FormateoDeFechas();
    }

    public String getFechaDB() throws NoExisteException {
        String selectQ = "SELECT valor FROM parametros WHERE nombre = 'fecha'";
        ResultSet resultSet = conector.selectFrom(selectQ);
        try {
            if (resultSet.next()) {
                return resultSet.getString("valor");
            } else {
                return "2023-11-12";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NoExisteException(e.getMessage());
        }
    }

    public boolean fechaEsPasado(String fechaNueva) throws NoExisteException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fechaComparar = sdf.parse(fechaNueva);
            Date fechaActual = formateoDeFechas.formateStringToDate(getFechaDB());
            if (fechaActual.after(fechaComparar)){
                return true;
            } else {
                return false;
            }
        } catch (ParseException | NoExisteException e) {
            e.printStackTrace();
            throw new NoExisteException(e.getMessage());
        }
    }

    public int updateFecha(String fecha) throws ParseException, SQLException, NoExisteException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(fecha);
        String fechaString = sdf.format(date);
        String update = "UPDATE parametros SET valor = ? WHERE nombre = 'fecha'";
        conector.updateWithException(update,new String[]{
                fechaString
        });
        this.conector = new Conector();
        return aplicarCambiosFecha();
    }

    public int aplicarCambiosFecha() throws SQLException, NoExisteException {
        String updateOferta = "UPDATE oferta SET estado = ? WHERE codigo = ?";
        List<Oferta> ofertasActivas = new OfertaService(new Conector()).getAllOfertas(EstadoOferta.ACTIVA.toString());
        int count = 0;
        for (Oferta oferta : ofertasActivas){
            String fechaLimite = formateoDeFechas.formatioDateAString(oferta.getFechaLimite());
            if (fechaEsPasado(fechaLimite)){
                conector.updateWithException(updateOferta, new String[]{
                        EstadoOferta.SELECCION.toString(),
                        String.valueOf(oferta.getCodigo())
                });
                count++;
                this.conector = new Conector();
            }
        }
        return count;
    }
}
