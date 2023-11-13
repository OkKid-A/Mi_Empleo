package org.cunoc.mi_empleo_api.Services;

import com.mysql.cj.util.DnsSrv;
import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.DB.FormateoDeFechas;
import org.cunoc.mi_empleo_api.Email.Notificador;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Usuario.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public String getNombreByID(String codigoUsuario) throws SQLException {
        String selectQ = String.format("SELECT nombre FROM usuario WHERE codigo = %s", codigoUsuario);
        ResultSet resultSet = conector.selectFrom(selectQ);
        String email;
        if (resultSet.next()){
            email  = resultSet.getString("nombre");
            return email;
        }
        return null;
    }

    public List<String> getTelefonosByID(String codigo) throws SQLException {
        String selectQ = String.format("SELECT * FROM telefono WHERE id_usuario = %s",codigo);
        ResultSet resultSet = conector.selectFrom(selectQ);
        List<String> telefonos = new ArrayList<>();
        while (resultSet.next()){
            telefonos.add(resultSet.getString("numero"));
        }
        return telefonos;
    }

    public Usuario crearUsuarioBase(Usuario usuario) throws SQLException, InvalidDataException {
        String token  = UUID.randomUUID().toString();
        String insertQ = "INSERT INTO usuario (nombre,username,email,direccion,cui,tipo,fecha_dob,password) " +
                "VALUES (?,?,?,?,?,?,?,?)";
        conector.updateWithException(insertQ,new String[]{
                usuario.getNombre(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getDireccion(),
                String.valueOf(usuario.getCui()),
                usuario.getTipo(),
                new FormateoDeFechas().formatioDateAString(usuario.getDob()),
                token
        });
        Notificador notificador = new Notificador(new Conector());
        notificador.enviarEmailAUsuario(usuario.getEmail(),"Verificacion de Email.","Has click en este link" +
                " para completar la creacion de tu cuenta:\nhttp://localhost:4200/validate/"+token);
        return usuario;
    }
}
