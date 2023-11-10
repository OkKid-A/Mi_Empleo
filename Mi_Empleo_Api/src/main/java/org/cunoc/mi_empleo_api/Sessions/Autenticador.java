package org.cunoc.mi_empleo_api.Sessions;

import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Usuario.TipoUsuario;
import org.cunoc.mi_empleo_api.Usuario.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Autenticador {

    private Conector conector;

    public Autenticador(Conector conector) {
        this.conector = conector;
    }

    public Usuario buscarUsuario(String username, String password) throws NoExisteException {
        String searchQ = String.format("SELECT tipo, codigo FROM usuario WHERE username = %s AND password = %s",
                conector.encomillar(username),
                conector.encomillar(password));
        try {
            ResultSet resultSet = conector.selectFrom(searchQ);
            if (resultSet.next()){
                Usuario usuario = new Usuario();
                usuario.setCodigo(resultSet.getString("codigo"));
                usuario.setTipo(resultSet.getString("tipo"));
                return usuario;
            } else {
                throw new NoExisteException("No existe el usuario");
            }
        } catch (SQLException e) {
            throw new NoExisteException("Error en la base de datos");
        } catch (NullPointerException e){
            throw  new NoExisteException("No se encontro el usuario");
        }
    }

}
