package org.cunoc.mi_empleo_api.Archivos;

import jakarta.servlet.http.Part;
import org.cunoc.mi_empleo_api.DB.Conector;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class Lector {

    private Conector conector;

    public Lector(Conector conector) {
        this.conector = conector;
    }

    public BufferedReader getBufferedReaderFromFile(Part part) throws IOException {
        String nombreArchivo = part.getSubmittedFileName();
        System.out.println(part.getContentType());
        System.out.println(part.getSubmittedFileName());
        System.out.println(part.getHeader("Content-disposition"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(part.getInputStream(), StandardCharsets.UTF_8));
        return reader;
    }


}
