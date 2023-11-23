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
import org.cunoc.mi_empleo_api.Services.Usuarios.EmpleadorService;
import org.cunoc.mi_empleo_api.Usuario.Empleador.Tarjeta;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(urlPatterns = {"/empleador"})
public class EmpleadorController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EmpleadorService empleadorService = new EmpleadorService();
        String mision = req.getParameter("mision");
        String vision = req.getParameter("vision");
        String usuario = req.getParameter("usuario");
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Tarjeta tarjeta = objectMapper.readValue(req.getInputStream(),Tarjeta.class);
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        try {
            empleadorService.updateEmpleador(tarjeta,mision,vision,usuario);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
           e.printStackTrace();
           resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (InvalidDataException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            System.out.println("Error en la informacion ingresada: "+e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

    }
}
