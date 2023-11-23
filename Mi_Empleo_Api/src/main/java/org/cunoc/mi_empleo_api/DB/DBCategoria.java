package org.cunoc.mi_empleo_api.DB;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DBCategoria {


    public DBCategoria() {

    }

    public String  getCategoriaCodigoByNombre(String nombre) throws SQLException {
        DBStatements dbStatements = new DBStatements();
        String selectQ = "SELECT codigo FROM categoria WHERE nombre = ?";
        ResultSet resultSet = dbStatements.select(selectQ, new String[]{
                nombre
        });
        if (resultSet.next()){
            return resultSet.getString("codigo");
        }
        else return null;
    }
}

