package org.cunoc.mi_empleo_api.Sevlets.Ofertas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.cunoc.mi_empleo_api.DB.DBOferta;
import org.cunoc.mi_empleo_api.Empleo.Entrevista;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Empleos.EntrevistaService;
import org.cunoc.mi_empleo_api.Sessions.Iniciador;

import java.io.IOException;
import java.util.List;

@WebServlet(name = "EntrevistaController", urlPatterns = {"/entrevista"})
public class EntrevistaController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntrevistaService entrevistaService = new EntrevistaService(new Iniciador().getConector(resp,req));
        String oferta = req.getParameter("oferta");
        String ofertas = req.getParameter("ofertas");
        if (oferta!=null){
                boolean tiene = entrevistaService.tieneEntrevista(oferta);
                resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                objectMapper.writeValue(resp.getWriter(), tiene);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
        } else if (ofertas!=null){
            try {
                List<Entrevista> entrevistas = entrevistaService.getAllEntrevistasOferta(ofertas);
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                objectMapper.writeValue(resp.getWriter(), entrevistas);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            } catch (NoExisteException e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Entrevista entrevista = objectMapper.readValue(req.getInputStream(), Entrevista.class);
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        EntrevistaService entrevistaService = new EntrevistaService(new Iniciador().getConector(resp,req));
        try {
            entrevista = entrevistaService.crearEntrevista(entrevista);
            objectMapper.writeValue(resp.getWriter(),entrevista);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (InvalidDataException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        EntrevistaService entrevistaService = new EntrevistaService(new Iniciador().getConector(resp,req));
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Entrevista entrevista = objectMapper.readValue(req.getInputStream(),Entrevista.class);
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        String notas = req.getParameter("notas");
        if (notas!=null) {
            try {
                entrevista = entrevistaService.finalizarEntrevista(entrevista,notas);
                objectMapper.writeValue(resp.getWriter(), entrevista);
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } catch (InvalidDataException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}
