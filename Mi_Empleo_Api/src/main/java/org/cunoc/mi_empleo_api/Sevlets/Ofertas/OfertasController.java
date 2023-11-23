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
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Empleos.EntrevistaService;
import org.cunoc.mi_empleo_api.Services.Empleos.OfertaService;
import org.cunoc.mi_empleo_api.Services.Usuarios.EmpleadorService;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet (name = "OfertasController", urlPatterns = {"/ofertas"})
public class OfertasController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OfertaService ofertaService = new OfertaService();
        List<Oferta> ofertas = new ArrayList<>();
            try {
                if (req.getParameter("estado") != null && req.getParameter("empresa") != null) {
                    ofertas = new EmpleadorService().getAllOfertasEmpresaByEstado(req.getParameter("empresa"),
                            req.getParameter("estado"));
                } else if (req.getParameter("empresa") != null) {
                    ofertas = ofertaService.getAllOfertasEmpresa(req.getParameter("empresa"),
                            req.getParameter("searchKey"));
                } else if (req.getParameter("estado")!=null){

            }else{
                   ofertas = ofertaService.getAllOfertas(req.getParameter("searchKey"),
                            Integer.parseInt(req.getParameter("filtro")));
                }
                resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                objectMapper.writeValue(resp.getWriter(), ofertas);
            } catch (SQLException | NoExisteException e) {
                e.printStackTrace();
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                objectMapper.writeValue(resp.getWriter(), "");
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Oferta oferta = objectMapper.readValue(req.getInputStream(),Oferta.class);
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        DBOferta dbOferta = new DBOferta();
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
        OfertaService ofertaService = new OfertaService();
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
        OfertaService ofertaService = new OfertaService();
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

