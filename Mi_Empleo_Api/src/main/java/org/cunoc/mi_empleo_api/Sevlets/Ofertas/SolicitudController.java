package org.cunoc.mi_empleo_api.Sevlets.Ofertas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Empleo.Solicitud;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Empleos.SolicitudService;
import org.cunoc.mi_empleo_api.Sessions.Iniciador;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "SolicitudController", urlPatterns = {"/solicitud"})
public class SolicitudController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SolicitudService solicitudService = new SolicitudService(new Iniciador().getConector(resp,req));
        String usuario = req.getParameter("usuario");
        String estado = req.getParameter("estado");
        List<Oferta> ofertas = new ArrayList<>();
        try {
            if (estado != null&&usuario !=null ){
                ofertas = solicitudService.getOfertasConSolicitud(usuario,estado);
            }
            resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            objectMapper.writeValue(resp.getWriter(), ofertas);
        } catch (SQLException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SolicitudService solicitudService = new SolicitudService(new Iniciador().getConector(resp,req));
        ObjectMapper objectMapper= new ObjectMapper().registerModule(new JavaTimeModule());
        Solicitud solicitud = objectMapper.readValue(req.getInputStream(),Solicitud.class);
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        try {
            solicitudService.crearSolicitud(solicitud);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (InvalidDataException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        } catch (NoExisteException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        SolicitudService solicitudService = new SolicitudService(new Iniciador().getConector(resp,req));
        String usuario = req.getParameter("usuario");
        String oferta = req.getParameter("oferta");
        if (usuario !=null && oferta != null){
            try {
                solicitudService.deleteSolicitud(usuario,oferta);
            } catch (NoExisteException e) {
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else {
            resp.setStatus(HttpServletResponse.SC_CONFLICT);
        }
    }
}
