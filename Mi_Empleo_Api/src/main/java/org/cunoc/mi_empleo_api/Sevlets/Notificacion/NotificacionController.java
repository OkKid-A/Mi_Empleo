package org.cunoc.mi_empleo_api.Sevlets.Notificacion;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Services.EmpleadorService;
import org.cunoc.mi_empleo_api.Services.NotificacionService;
import org.cunoc.mi_empleo_api.Services.UsuarioService;
import org.cunoc.mi_empleo_api.Sessions.Iniciador;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet (name = "NotificacionController", urlPatterns = {"/notificacion"})
public class NotificacionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Conector conector = new Iniciador().getConector(resp,req);
        NotificacionService notificacionService = new NotificacionService(conector);
        UsuarioService usuarioService = new UsuarioService(conector);
        EmpleadorService empleadorService = new EmpleadorService(conector);
        String usuario = req.getParameter("usuario");
        String subject = req.getParameter("subject");
        String msg = req.getParameter("mensaje");
        String oferta = req.getParameter("oferta");
        String email = "";
        try {
            if (subject!=null&&msg!=null){
                if (usuario!=null){
                    email = usuarioService.getEmailByID(usuario);
                } else if (oferta!=null){
                    email = empleadorService.getEmail(empleadorService.getCodigoEmpleadorByOferta(oferta));
                } else {
                    resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                }
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                throw new InvalidDataException();
            }
            notificacionService.enviarEmailAUsuario(email,subject,msg);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (InvalidDataException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }



}