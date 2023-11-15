package org.cunoc.mi_empleo_api.Sevlets.Admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.DB.DBFecha;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Admin.ComisionService;
import org.cunoc.mi_empleo_api.Services.Admin.ResumenService;
import org.cunoc.mi_empleo_api.Sessions.Iniciador;
import org.cunoc.mi_empleo_api.Usuario.Admin.Dashboard;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;

@WebServlet(name = "ResumenController", urlPatterns = {"/resumen"})
public class ResumenController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResumenService resumenService = new ResumenService(new Iniciador().getConector(resp,req));
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String fecha = req.getParameter("condicion");
        if (fecha!=null){
            try {
                String fechaActual = new DBFecha(new Conector()).getFechaDB();
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
        DBFecha fecha = new DBFecha(new Conector());
        try {
            int contador = fecha.updateFecha(fechaNueva);
            objectMapper.writeValue(resp.getWriter(),contador);
        } catch (ParseException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        } catch (SQLException | NoExisteException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
