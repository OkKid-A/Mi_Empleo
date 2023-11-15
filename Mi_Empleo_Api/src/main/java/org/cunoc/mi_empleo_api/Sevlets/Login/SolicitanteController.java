package org.cunoc.mi_empleo_api.Sevlets.Login;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.entity.ContentType;
import org.cunoc.mi_empleo_api.Archivos.Lector;
import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Services.Empleos.CategoriasService;
import org.cunoc.mi_empleo_api.Services.Usuarios.SolicitanteService;
import org.cunoc.mi_empleo_api.Sessions.Iniciador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import static org.cunoc.mi_empleo_api.Sevlets.Admin.UploadController.PATHDOC;

@WebServlet(urlPatterns = {"/solicitante"})
@MultipartConfig(location = "/tmp")
public class SolicitanteController extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart("curriculum");
        String solicitante = req.getParameter("solicitante");
        Conector conector = new Conector();
        Lector lector = new Lector(conector);
        InputStream inputStream = part.getInputStream();
        File file = new File(PATHDOC);
        file.mkdirs();
        String fullPath = PATHDOC+ File.separatorChar+"curriculum="+solicitante+"."+ FilenameUtils.getExtension(part.getSubmittedFileName());
        File file1 = new File(fullPath);
        file1.createNewFile();
        part.write(fullPath);
        SolicitanteService solicitanteService = new SolicitanteService(conector);
        try {
            solicitanteService.updateSolicitante(fullPath,solicitante);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Conector conector = new Iniciador().getConector(resp,req);
        CategoriasService categoriasService =new CategoriasService(conector);
        ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
        String[] categorias = objectMapper.readValue(req.getInputStream(),String[].class);
        resp.setContentType(ContentType.APPLICATION_JSON.getMimeType());
        String usuario = req.getParameter("solicitante");
        try {
            categoriasService.updatePreferences(categorias,usuario);
            resp.setStatus(HttpServletResponse.SC_CREATED);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}
