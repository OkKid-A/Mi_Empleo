package org.cunoc.mi_empleo_api.Sevlets.Admin;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Admin.ComisionService;
import org.cunoc.mi_empleo_api.Services.Admin.ResumenService;
import org.cunoc.mi_empleo_api.Sessions.Iniciador;
import org.cunoc.mi_empleo_api.Usuario.Admin.Dashboard;

import java.io.IOException;

@WebServlet(name = "ResumenController", urlPatterns = {"/resumen"})
public class ResumenController extends HttpServlet {


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ResumenService resumenService = new ResumenService(new Iniciador().getConector(resp,req));
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
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
