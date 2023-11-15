package org.cunoc.mi_empleo_api.Services.Usuarios;

import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Services.Service;

import java.sql.SQLException;

public class SolicitanteService extends Service {
    public SolicitanteService(Conector conector) {
        super(conector);
    }

    public void updateSolicitante(String path, String usuario) throws SQLException {
        String insertQ = "INSERT INTO solicitante (cv_path, codigo_usuario) VALUES (?,?)";
        getConector().updateWithException(insertQ,new String[]{
                path,
                usuario
        });
    }

}
