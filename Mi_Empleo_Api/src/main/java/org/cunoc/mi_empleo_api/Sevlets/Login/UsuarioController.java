package org.cunoc.mi_empleo_api.Sevlets.Login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Usuarios.UsuarioService;
import org.cunoc.mi_empleo_api.Sessions.DBUsuario;
import org.cunoc.mi_empleo_api.Usuario.Usuario;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UsuarioController", urlPatterns = {"/usuario"})
public class UsuarioController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsuarioService usuarioService = new UsuarioService();
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String token = req.getParameter("token");
        String password = req.getParameter("password");
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        UsuarioService usuarioService = new UsuarioService();
        try {
            Usuario usuario = usuarioService.cambiarPassword1st(password,token);
            objectMapper.writeValue(resp.getWriter(), usuario);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (NoExisteException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String rol = req.getParameter("rol");
        String codigoUsuario = req.getParameter("codigo");
        DBUsuario DBUsuario = new DBUsuario();
        try {
        if (rol!=null&&codigoUsuario!=null){
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            boolean existe = DBUsuario.verificarCompletado(rol,codigoUsuario);
            System.out.println(existe);
            objectMapper.writeValue(resp.getWriter(), existe);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        }} catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
