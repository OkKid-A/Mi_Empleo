package org.cunoc.mi_empleo_api.Services.Usuarios;

import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Usuario.Empleador.Tarjeta;

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

    public void updateEmpleador(Tarjeta tarjeta, String mision, String vision, String usuario) throws SQLException {
        String insertQ = "INSERT INTO empleador (codigo_usuario, vision, mision) VALUES (?,?,?)";
        conector.updateWithException(insertQ, new String[]{
                usuario,
                mision,
                vision
        });
        String insertTarjeta = "INSERT INTO tarjeta (titular, numero, cvv, id_empleador) VALUES (?,?,?,?)";
        conector.updateWithException(insertTarjeta, new String[]{
                tarjeta.getTitular(),
                tarjeta.getNumero(),
                String.valueOf(tarjeta.getCvv()),
                usuario
        });
    }
}
