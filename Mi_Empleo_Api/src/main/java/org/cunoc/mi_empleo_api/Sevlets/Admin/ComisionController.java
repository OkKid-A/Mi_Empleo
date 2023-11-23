package org.cunoc.mi_empleo_api.Sevlets.Admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.ParseException;
import org.apache.http.entity.ContentType;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Admin.ComisionService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "ComisionController", urlPatterns = {"/comision"})
public class ComisionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ComisionService comisionService = new ComisionService();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Float comision = null;
        try {
            comision = comisionService.getComisionValor();
            resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            objectMapper.writeValue(resp.getWriter(), comision);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (NoExisteException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ComisionService comisionService = new ComisionService();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String codigoOferta = objectMapper.readValue(req.getInputStream(),String.class);
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        if (codigoOferta!=null) {
            try {
                float valor = comisionService.addComision(codigoOferta);
                objectMapper.writeValue(resp.getWriter(), valor);
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } catch (InvalidDataException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            } catch (NoExisteException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ComisionService comisionService = new ComisionService();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        try {
            Float nuevoValor = Float.valueOf(objectMapper.readValue(req.getInputStream(),String.class));
            resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            comisionService.updateComision(nuevoValor);
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } catch (ParseException | SQLException e){
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
