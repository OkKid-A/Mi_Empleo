package org.cunoc.mi_empleo_api.Services.Admin;

import org.cunoc.mi_empleo_api.DB.DBFecha;
import org.cunoc.mi_empleo_api.DB.DBOferta;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ComisionService extends Service {


    public ComisionService() {
    }

    public float getComisionValor() throws NoExisteException {
        String selectQ = "SELECT valor FROM parametros WHERE nombre = 'comision'";
        try (ResultSet resultSet = getDBStatements().select(selectQ)) {
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
        return new DBFecha().getFechaDB();
    }

    public float addComision(String codigoOferta) throws NoExisteException, InvalidDataException {
        String insertQ = "INSERT INTO comision (valor,codigo_empleador,fecha,codigo) VALUES (?,?,?,?)";
        float valor = getComisionValor();
        try {
            int codigoEmpleador = new DBOferta().getCodigoEmpresaDeOFerta(codigoOferta);
            String fecha = getFechaDB();
            getDBStatements().updateWithException(insertQ,new String[]{
                    String.valueOf(valor),
                    String.valueOf(codigoEmpleador),
                    fecha,
                    codigoOferta
            });
            return valor;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataException();
        }
    }

    public void updateComision(Float valor) throws SQLException {
        String updateQ = "UPDATE parametros SET valor = ? WHERE nombre = ?";
        getDBStatements().updateWithException(updateQ,new String[]{
                String.valueOf(valor),
                "comision"
        });
    }

    public Double getGastosTotales(String codigoEmpleador) throws NoExisteException {
        String selectQ = "SELECT SUM(c.valor) AS total FROM comision c INNER JOIN oferta o ON c.codigo = o.codigo " +
                "WHERE c.codigo_empleador = ?";
        try (ResultSet resultSet = getDBStatements().select(selectQ, new String[]{codigoEmpleador})) {
            if (resultSet.next()){
                return (double) resultSet.getFloat("total");
            } else {
                return (double) 0;
            }
        } catch (SQLException e) {
            System.out.println("No se encontro el total de las comisiones");
            e.printStackTrace();
            throw new NoExisteException(e.getMessage());
        }

    }
}
