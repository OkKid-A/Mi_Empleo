package org.cunoc.mi_empleo_api.Services;

import org.cunoc.mi_empleo_api.DB.DBStatements;

import java.sql.SQLException;

public class Service {

    private DBStatements conector;

    public DBStatements getDBStatements() {
        return conector;
    }

    public Service() {
        this.conector = new DBStatements();
    }
}
