package org.cunoc.mi_empleo_api.Sevlets.Ofertas;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.http.entity.ContentType;
import org.cunoc.mi_empleo_api.Empleo.Categoria;
import org.cunoc.mi_empleo_api.Services.Empleos.CategoriasService;
import org.cunoc.mi_empleo_api.Sessions.Iniciador;
import org.cunoc.mi_empleo_api.Usuario.Empleador.Tarjeta;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "CategoriasController", urlPatterns = {"/categorias"})
public class CategoriasController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoriasService categoriasService = new CategoriasService(new Iniciador().getConector(resp,req));
        String nombre = req.getParameter("nombre");
        if (nombre!=null){
            try {
                String codigoCategoria = categoriasService.getCodigoByNombre(nombre);
                resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                objectMapper.writeValue(resp.getWriter(), codigoCategoria);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            } catch (SQLException e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);            }

        } else {
            try {
                List<Categoria> categorias = categoriasService.getAllCategorias();
                resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
                ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
                objectMapper.writeValue(resp.getWriter(), categorias);
                resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            } catch (SQLException e) {
                e.printStackTrace();
                resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            }
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CategoriasService categoriasService = new CategoriasService(new Iniciador().getConector(resp,req));
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        Categoria categoria = objectMapper.readValue(req.getInputStream(),Categoria.class);
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        try {
            if (categoria.getCodigo()==0){
                categoriasService.addCategoria(categoria);
            } else {
                categoriasService.editarCategoria(categoria);
            }
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
