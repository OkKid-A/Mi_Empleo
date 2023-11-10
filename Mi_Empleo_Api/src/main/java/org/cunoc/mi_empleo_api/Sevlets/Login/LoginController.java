package org.cunoc.mi_empleo_api.Sevlets.Login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Exceptions.SessionException;
import org.cunoc.mi_empleo_api.Sessions.Autenticador;
import org.cunoc.mi_empleo_api.Sessions.Iniciador;
import org.cunoc.mi_empleo_api.Usuario.Usuario;

import java.io.IOException;

@WebServlet (name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        if (username != null && password != null){
            Iniciador iniciador = new Iniciador();
            Autenticador autenticador =  new Autenticador(iniciador.getConector(resp,req));

            resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            try {
                Usuario usuario = autenticador.buscarUsuario(username,password);
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                objectMapper.writeValue(resp.getWriter(),usuario);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            } catch (NoExisteException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
