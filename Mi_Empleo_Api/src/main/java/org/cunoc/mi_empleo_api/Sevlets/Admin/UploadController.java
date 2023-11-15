package org.cunoc.mi_empleo_api.Sevlets.Admin;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.cunoc.mi_empleo_api.Archivos.Lector;
import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Archivos.CargadorDeArchivos;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet(name = "UploadServlet", urlPatterns = {"/upload"})
@MultipartConfig(location = "/tmp")
public class UploadController extends HttpServlet {

    public static final String PATHDOC = System.getProperty("user.dir")+ File.separatorChar +"documents";
    private static final String PART_NAME = "archivoEntrada";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part part = req.getPart(PART_NAME);
        Conector conector = new Conector();
        CargadorDeArchivos cargadorArchivo = new CargadorDeArchivos(conector);
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        try {
            Lector lector = new Lector(conector);
            if (part!=null) {
                cargadorArchivo.cargarArchivo(lector.getBufferedReaderFromFile(part));
            } else {
                throw new InvalidDataException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        } catch (ParseException | InvalidDataException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }
}

