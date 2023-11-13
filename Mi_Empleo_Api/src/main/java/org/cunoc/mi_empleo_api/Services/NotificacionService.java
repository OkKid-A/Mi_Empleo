package org.cunoc.mi_empleo_api.Services;

import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Email.Notificador;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;

import java.sql.SQLException;
import java.util.List;

public class NotificacionService extends Service{

    private Notificador notificador;
    public NotificacionService(Conector conector) {
        super(conector);
        this.notificador = new Notificador(conector);
    }

    public void notificarMultiplesUsuarios(List<Integer> usuarios, String subject, String message) throws SQLException, InvalidDataException {
        UsuarioService usuarioService = new UsuarioService(getConector());
        for (int codigo : usuarios){
            String email =  usuarioService.getEmailByID(String.valueOf(codigo));
            notificador.enviarEmailAUsuario(email,subject,message);
        }
    }
}
