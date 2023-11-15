package org.cunoc.mi_empleo_api.Sevlets.Ofertas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.DB.DBOferta;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Empleos.OfertaService;
import org.cunoc.mi_empleo_api.Sessions.Iniciador;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet (name = "OfertasController", urlPatterns = {"/ofertas"})
public class OfertasController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OfertaService ofertaService = new OfertaService(new Conector());
        List<Oferta> ofertas = new ArrayList<>();
            try {
                if (req.getParameter("estado") != null){
                    ofertas = ofertaService.getAllOfertas(req.getParameter("estado"));
                }else {
                   ofertas = ofertaService.getAllOfertas(req.getParameter("searchKey"),
                            Integer.parseInt(req.getParameter("filtro")));
                }
                resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                objectMapper.writeValue(resp.getWriter(), ofertas);
            } catch (SQLException e) {
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                objectMapper.writeValue(resp.getWriter(), "");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println(req);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Oferta oferta = objectMapper.readValue(req.getInputStream(),Oferta.class);
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        DBOferta dbOferta = new DBOferta(new Iniciador().getConector(resp,req));
        try {
            oferta = dbOferta.crearOfertaADB(oferta);
            objectMapper.writeValue(resp.getWriter(),oferta);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (InvalidDataException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OfertaService ofertaService = new OfertaService(new Iniciador().getConector(resp,req));
        String oferta = req.getParameter("codigo");
        String estado = req.getParameter("estado");
        if (oferta!=null&&estado!=null){
            try {
                ofertaService.eliminarOferta(oferta,estado);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
                System.out.println("eliminado");
            } catch (InvalidDataException | SQLException | NoExisteException e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } else {
            try {
                throw new InvalidDataException();
            } catch (InvalidDataException e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Conector conector = new Iniciador().getConector(resp,req);
        OfertaService ofertaService = new OfertaService(conector);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Oferta oferta = objectMapper.readValue(req.getInputStream(),Oferta.class);
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        String codigo = req.getParameter("codigo");
        if (codigo!=null) {
            try {
                oferta = ofertaService.editarOferta(oferta,codigo);
                objectMapper.writeValue(resp.getWriter(), oferta);
                resp.setStatus(HttpServletResponse.SC_CREATED);
            } catch (InvalidDataException e) {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        }
    }
}

