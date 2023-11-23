package org.cunoc.mi_empleo_api.Services.Usuarios;

import org.cunoc.mi_empleo_api.Archivos.FormateoDeFechas;
import org.cunoc.mi_empleo_api.DB.ConectorSingleton;
import org.cunoc.mi_empleo_api.DB.DBStatements;
import org.cunoc.mi_empleo_api.Services.Email.Notificador;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Service;
import org.cunoc.mi_empleo_api.Sessions.DBUsuario;
import org.cunoc.mi_empleo_api.Usuario.Usuario;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UsuarioService extends Service {

    private final String INSERT_PHONES = "INSERT INTO telefono (numero, id_usuario) VALUES (?,?)";
    private final String INSERT_USERBASE = "INSERT INTO usuario (nombre,username,email,direccion,cui,tipo,fecha_dob,password) " +
            "VALUES (?,?,?,?,?,?,?,?)";

    public UsuarioService() {

    }

    public String getEmailByID(String usuarioCodigo) throws SQLException {
        String selectQ = "SELECT email FROM usuario WHERE codigo = ?";
        try (ResultSet resultSet = getDBStatements().select(selectQ, new String[]{usuarioCodigo})) {
            String email;
            if (resultSet.next()) {
                email = resultSet.getString("email");
                return email;
            }
        }
        return null;
    }

    public String getNombreByID(String codigoUsuario) throws SQLException {
        String selectQ = "SELECT nombre FROM usuario WHERE codigo = ?";
        try (ResultSet resultSet = getDBStatements().select(selectQ, new String[]{codigoUsuario})) {
            String email;
            if (resultSet.next()) {
                email = resultSet.getString("nombre");
                return email;
            }
        }
        return null;
    }

    public List<String> getTelefonosByID(String codigo) throws SQLException {
        String selectQ = "SELECT * FROM telefono WHERE id_usuario = ?";
        List<String> telefonos;
        try (ResultSet resultSet = getDBStatements().select(selectQ, new String[]{codigo})) {
            telefonos = new ArrayList<>();
            while (resultSet.next()) {
                telefonos.add(resultSet.getString("numero"));
            }
        }
        return telefonos;
    }

    public String getIdByUsername(String username) throws SQLException {
        String selectQ = "SELECT codigo FROM usuario WHERE username = ?";
        try (ResultSet resultSet = getDBStatements().select(selectQ, new String[]{username})) {
            String codigo;
            if (resultSet.next()) {
                codigo = resultSet.getString("codigo");
                return codigo;
            }
        }
        return null;
    }

    public Usuario crearUsuarioBase(Usuario usuario) throws InvalidDataException, SQLException {
        Connection connection = ConectorSingleton.getInstance().getConnection();
        String token  = UUID.randomUUID().toString();
        try {
            connection.setAutoCommit(false);
            String insertTipo = crearTipoBase(usuario.getTipo());
            String[][] multipleValues = new String[][]{
                    new String[]{
                            usuario.getNombre(),
                            usuario.getUsername(),
                            usuario.getEmail(),
                            usuario.getDireccion(),
                            String.valueOf(usuario.getCui()),
                            usuario.getTipo(),
                            new FormateoDeFechas().formatioDateAString(usuario.getDob()),
                            token
                    }};
            getDBStatements().updateWithException(INSERT_USERBASE,multipleValues[0]);
            getDBStatements().updateWithException(insertTipo,new String[]{getIdByUsername(usuario.getUsername())});
            getDBStatements().updateWithException(INSERT_PHONES,new String[]{usuario.getTelefono(), getIdByUsername(usuario.getUsername())});
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Hubo un error al completar la transaccion en: "+"\n"+e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("fallo el rollback");
            }
            throw new InvalidDataException();
        } finally {
            if (connection!= null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("No se pudo cerrar la conexion para la query:" + e.getMessage());
                }
            }
        }
        Notificador notificador = new Notificador();
        notificador.enviarEmailAUsuario(usuario.getEmail(),"Verificacion de Email.","Has click en este link" +
                " para completar la creacion de tu cuenta:\nhttp://localhost:4200/validate/"+token);
        return usuario;
    }

    public void insertPhone(String id, Usuario usuario) throws SQLException {
        String insertPhone = INSERT_PHONES;
        getDBStatements().update(insertPhone,new String[]{
                usuario.getTelefono(),
                id
        });
    }

    public Usuario cambiarPassword1st(String password, String token) throws SQLException, NoExisteException {
        Usuario usuario = new DBUsuario().buscarUsuarioByToken(token);
        if (usuario!=null) {
            String updateQ = "UPDATE usuario SET password = ? WHERE password = ?";
            getDBStatements().update(updateQ, new String[]{
                    password,
                    token
            });
            return usuario;
        } else {
            throw new NoExisteException("No existe el token en el sistema");
        }
    }

    public String crearTipoBase(String tipo) throws SQLException {
        String insertQ = null;
        switch (tipo){
            case "EMPLEADOR":
                insertQ = "INSERT INTO empleador (codigo_usuario) VALUES (?)";
                break;
            case "SOLICITANTE":
                insertQ = "INSERT INTO solicitante (codigo_usuario) VALUES (?)";
                break;
        }
        return insertQ;
    }

    public String getLastIndexUser() throws SQLException {
        String selectQ = "SELECT MAX(codigo) AS 'id' FROM usuario";
        try (ResultSet resultSet = getDBStatements().select(selectQ)) {
            String codigo;
            if (resultSet.next()) {
                int cod = resultSet.getInt("id");
                return String.valueOf(cod+1);
            }
        }
        return null;
    }
}
