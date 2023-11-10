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
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Services.OfertaService;
import org.cunoc.mi_empleo_api.Sessions.Iniciador;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet (name = "OfertasController", urlPatterns = {"/ofertas"})
public class OfertasController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OfertaService ofertaService = new OfertaService(new Iniciador().getConector(resp,req));
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
}

