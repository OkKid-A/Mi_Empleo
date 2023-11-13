package org.cunoc.mi_empleo_api.Services.Admin;

import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.DB.DBOferta;
import org.cunoc.mi_empleo_api.DB.FormateoDeFechas;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Empleos.OfertaService;
import org.cunoc.mi_empleo_api.Services.Service;

import javax.ws.rs.NotFoundException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Date;

public class ComisionService extends Service {


    public ComisionService(Conector conector) {
        super(conector);
    }

    public float getComisionValor() throws NoExisteException {
        String selectQ = "SELECT valor FROM parametros WHERE nombre = 'comsision'";
        ResultSet resultSet = getConector().selectFrom(selectQ);
        try {
            if (resultSet.next()) {
                return resultSet.getFloat("valor");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NoExisteException(e.getMessage());
        }
    }

    public String getFechaDB() throws NoExisteException {
        String selectQ = "SELECT valor FROM parametros WHERE nombre = 'fecha'";
        ResultSet resultSet = getConector().selectFrom(selectQ);
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

    public float addComision(String codigoOferta) throws NoExisteException, InvalidDataException {
        String insertQ = "INSERT INTO comision (valor,codigo_empleador,fecha) VALUES (?,?,?)";
        float valor = getComisionValor();
        try {
            int codigoEmpleador = new DBOferta(getConector()).getCodigoEmpresaDeOFerta(codigoOferta);
            String fecha = getFechaDB();
            getConector().updateWithException(insertQ,new String[]{
                    String.valueOf(valor),
                    String.valueOf(codigoEmpleador),
                    fecha
            });
            return valor;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataException();
        }
    }
}
