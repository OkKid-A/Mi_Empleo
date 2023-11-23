package org.cunoc.mi_empleo_api.Sevlets.Reportes;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.util.JRLoader;
import org.cunoc.mi_empleo_api.Archivos.FormateoDeFechas;
import org.cunoc.mi_empleo_api.DB.ConectorSingleton;
import org.cunoc.mi_empleo_api.DB.DBFecha;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Usuarios.UsuarioService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "Admin Reportes Controller", urlPatterns = {"/admin-reportes"})
public class AdminReportesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String admin = req.getParameter("admin");
        int tipo = Integer.parseInt(req.getParameter("tipo"));
        String fechaInicial = (req.getParameter("fechaInicial"));
        String fechaFinal = req.getParameter("fechaFinal");
        String resPath = req.getServletContext().getRealPath("/resources/");
        FormateoDeFechas formateoDeFechas = new FormateoDeFechas();
        System.out.println(resPath);
        Map<String, Object> parametros = new HashMap<>();
        String reportName = null;
        if (admin != null) {
            switch (tipo) {
                case 0:
                    reportName = "TopEmpOfertas.jasper";
                    break;
                case 1:
                case 2:
                    try {
                        if (fechaInicial!=null&&fechaFinal!=null&&new DBFecha().fechaEsPasado(fechaInicial,fechaFinal)){
                            parametros.put("fechaInicial",formateoDeFechas.formateStringToDate(fechaInicial));
                            parametros.put("fechaFinal",formateoDeFechas.formateStringToDate(fechaFinal));
                            parametros.put("dateInicial",formateoDeFechas.formateStringToDate(fechaInicial));
                                parametros.put("dateFinal",formateoDeFechas.formateStringToDate(fechaFinal));
                                if (tipo==1){
                                    reportName = "Top5Ganancias.jasper";
                                } else {
                                    reportName = "GananciasPorCategoria.jasper";
                                }
                        }
                    } catch (NoExisteException | ParseException e) {
                        e.printStackTrace();
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }
            }
            printReport(req,resp,admin,resPath+reportName,parametros);
        }
    }

    public void printReport(HttpServletRequest req, HttpServletResponse resp, String admin, String reportPath,
                             Map<String,Object> parametros) throws IOException {
        EmpReportesController.printPDF(resp, admin, reportPath, parametros);
    }
}

