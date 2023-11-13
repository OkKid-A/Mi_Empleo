package org.cunoc.mi_empleo_api.DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCategoria {

    private Conector conector;

    public DBCategoria(Conector conector) {
        this.conector = conector;
    }

    public String  getCategoriaCodigoByNombre(String nombre) throws SQLException {
        String selectQ = String.format("SELECT codigo FROM categoria WHERE nombre = %s", conector.encomillar(nombre));
        ResultSet resultSet =          conector.selectFrom(selectQ);
        if (resultSet.next()){
            return resultSet.getString("codigo");
        }
        else return null;
    }
}

