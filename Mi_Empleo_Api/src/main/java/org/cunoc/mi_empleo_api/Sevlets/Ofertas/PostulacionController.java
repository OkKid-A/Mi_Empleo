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
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Empleos.OfertaService;
import org.cunoc.mi_empleo_api.Services.Empleos.PostulacionService;
import org.cunoc.mi_empleo_api.Usuario.Solicitante.Postulacion;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "PostulacionController", urlPatterns = {"/postulacion"})
public class PostulacionController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String empleador = req.getParameter("empleador");
        String oferta = req.getParameter("oferta");
        PostulacionService postulacionService = new PostulacionService();
        List<Postulacion> postulaciones;
        try {
            if (empleador != null&&oferta!=null) {
                postulaciones = postulacionService.getCandidatosOfertaByOFerta(empleador,oferta);
                resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                objectMapper.writeValue(resp.getWriter(), postulaciones);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            } else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OfertaService ofertaService = new OfertaService();
        String nuevoEstado = req.getParameter("estado");
        try {
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            Oferta oferta = objectMapper.readValue(req.getInputStream(),Oferta.class);
            resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            ofertaService.getDbOferta().cambiarEstadoDeOferta(oferta,nuevoEstado);
            objectMapper.writeValue(resp.getWriter(), oferta);
            resp.setStatus(HttpServletResponse.SC_FOUND);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PostulacionService postulacionService = new PostulacionService();
        String usuario = req.getParameter("usuario");
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String codOferta = objectMapper.readValue(req.getInputStream(), String.class);
        try {
            if (usuario!=null && codOferta!= null){
                resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                Oferta oferta  = postulacionService.asignarGanadorAOferta(usuario,codOferta);
                objectMapper.writeValue(resp.getWriter(), oferta);
                resp.setStatus(HttpServletResponse.SC_CREATED);
            }
        } catch (NoExisteException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (InvalidDataException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
