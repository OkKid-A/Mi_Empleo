package org.cunoc.mi_empleo_api.Services;

import org.cunoc.mi_empleo_api.Services.Email.Notificador;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Services.Usuarios.UsuarioService;

import java.sql.SQLException;
import java.util.List;

public class NotificacionService extends Service{

    private Notificador notificador;
    public NotificacionService() {

        this.notificador = new Notificador();
    }

    public void notificarMultiplesUsuarios(List<Integer> usuarios, String subject, String message) throws SQLException, InvalidDataException {
        UsuarioService usuarioService = new UsuarioService();
        for (int codigo : usuarios){
            String email =  usuarioService.getEmailByID(String.valueOf(codigo));
            notificador.enviarEmailAUsuario(email,subject,message);
        }
    }
}
