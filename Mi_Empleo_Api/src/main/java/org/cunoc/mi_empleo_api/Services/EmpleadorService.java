package org.cunoc.mi_empleo_api.Services;

import org.cunoc.mi_empleo_api.DB.Conector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmpleadorService {

    private Conector conector;

    public EmpleadorService(Conector conector) {
        this.conector = conector;
    }

    public String getCodigoEmpleadorByOferta(String oferta) throws SQLException {
        String selectQ = String.format("SELECT empresa FROM oferta WHERE codigo = %s",oferta);
        ResultSet resultSet = conector.selectFrom(selectQ);
        if (resultSet.next()){
            return resultSet.getString("empresa");
        } else {
            return null;
        }
    }

    public String getEmail(String codigo) throws SQLException {
        String selectQ = String.format("SELECT email FROM usuario WHERE codigo = %s",codigo);
        ResultSet resultSet = conector.selectFrom(selectQ);
        if (resultSet.next()){
            return resultSet.getString("email");
        }
        return null;
    }
}
