package org.cunoc.mi_empleo_api.Sevlets.Admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;

import org.cunoc.mi_empleo_api.DB.DBFecha;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Admin.ResumenService;
import org.cunoc.mi_empleo_api.Usuario.Admin.Dashboard;

import java.io.IOException;
import java.text.ParseException;

@WebServlet(name = "ResumenController", urlPatterns = {"/resumen"})
public class ResumenController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResumenService resumenService = new ResumenService();
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String fecha = req.getParameter("condicion");
        if (fecha!=null){
            try {
                String fechaActual = new DBFecha().getFechaDB();
                resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                objectMapper.writeValue(resp.getWriter(), fechaActual);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            } catch (NoExisteException e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        } else {
            try {
                Dashboard dashboard = resumenService.getResumen();
                resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                objectMapper.writeValue(resp.getWriter(), dashboard);
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } catch (NoExisteException e) {
                e.printStackTrace();
                System.out.println(e.getErrorMessage());
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String  fechaNueva = req.getParameter("fecha");
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        DBFecha fecha = new DBFecha();
        try {
            int contador = fecha.updateFecha(fechaNueva);
            objectMapper.writeValue(resp.getWriter(),contador);
        } catch (ParseException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (NoExisteException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (InvalidDataException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
