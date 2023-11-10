package org.cunoc.mi_empleo_api.Services;

import com.mysql.cj.util.DnsSrv;
import org.cunoc.mi_empleo_api.DB.Conector;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioService {

    private Conector conector;

    public UsuarioService(Conector conector) {
        this.conector = conector;
    }

    public String getEmailByID(String usuarioCodigo) throws SQLException {
        String selectQ = String.format("SELECT email FROM usuario WHERE codigo = %s", usuarioCodigo);
        ResultSet resultSet = conector.selectFrom(selectQ);
        String email;
        if (resultSet.next()){
            email  = resultSet.getString("email");
            return email;
        }
        return null;
    }
}
