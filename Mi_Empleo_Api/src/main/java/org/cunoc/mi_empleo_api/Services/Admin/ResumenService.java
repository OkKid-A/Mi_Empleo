package org.cunoc.mi_empleo_api.Services.Admin;


import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Service;
import org.cunoc.mi_empleo_api.Usuario.Admin.Dashboard;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResumenService extends Service {


    public ResumenService() {

    }

    public Dashboard getResumen() throws NoExisteException {
        return new Dashboard(
                countUsuariosByTipo("SOLICITANTE"),
                countUsuariosByTipo("EMPLEADOR"),
                getGananciasTotales(),
                0
        );
    }

    public int countUsuariosByTipo(String tipo) throws NoExisteException {
        String countQ = "SELECT COUNT(codigo) AS contar FROM usuario WHERE tipo = ?";
        try (ResultSet resultSet = getDBStatements().select(countQ, new String[]{tipo})) {
                if (resultSet.next()) {

                    return resultSet.getInt("contar");
                } else {
                    return 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new NoExisteException("Error en count");
            }
    }

    public float getGananciasTotales() throws NoExisteException {
        String countQ = "SELECT sum(valor) AS total FROM comision";
        try (ResultSet resultSet = getDBStatements().select(countQ)) {

                if (resultSet.next()) {
                    return resultSet.getFloat("total");
                } else {
                    return 0;
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new NoExisteException("No existen comisiones");
        }
    }

}
