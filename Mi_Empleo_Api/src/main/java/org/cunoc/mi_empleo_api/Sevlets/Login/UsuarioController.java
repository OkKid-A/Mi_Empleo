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
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Services.UsuarioService;
import org.cunoc.mi_empleo_api.Sessions.Iniciador;
import org.cunoc.mi_empleo_api.Usuario.Usuario;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UsuarioController", urlPatterns = {"/usuario"})
public class UsuarioController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Conector conector = new Iniciador().getConector(resp,req);
        UsuarioService usuarioService = new UsuarioService(conector);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Usuario usuario = objectMapper.readValue(req.getInputStream(),Usuario.class);
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        try {
            usuario = usuarioService.crearUsuarioBase(usuario);
            objectMapper.writeValue(resp.getWriter(), usuario);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (InvalidDataException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
