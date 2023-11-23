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
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Empleos.OfertaService;

import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "OfertaDetallesController", urlPatterns = {"/oferta-detalles"})
public class OfertaDetallesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        OfertaService ofertaService = new OfertaService();
        int codigo = Integer.parseInt(req.getParameter("codigo"));
        try {
            Oferta oferta = ofertaService.getOferta(codigo);
            resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
            ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
            objectMapper.writeValue(resp.getWriter(), oferta);
        } catch (NoExisteException e) {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
