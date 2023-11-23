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
import org.cunoc.mi_empleo_api.Empleo.EstadoEntrevista;
import org.cunoc.mi_empleo_api.Empleo.EstadoOferta;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Admin.ComisionService;
import org.cunoc.mi_empleo_api.Services.Usuarios.UsuarioService;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import static net.sf.jasperreports.engine.JasperPrintManager.printReport;

@WebServlet({"/emp-reportes"})
public class EmpReportesController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String empleador = req.getParameter("emp");
        String estado = req.getParameter("estado");
        int tipo = Integer.parseInt(req.getParameter("tipo"));
        String fechaInicial = (req.getParameter("fechaInicial"));
        String fechaFinal = req.getParameter("fechaFinal");
        String resPath = req.getServletContext().getRealPath("/resources/");
        FormateoDeFechas formateoDeFechas = new FormateoDeFechas();
        Map<String, Object> parametros = new HashMap<>();
        String reportName = null;
        if (empleador != null) {
            switch (tipo) {
                case 0:
                case 1:
                    try {
                        if (fechaInicial!=null&&fechaFinal!=null&&new DBFecha().fechaEsPasado(fechaInicial,fechaFinal)){
                            parametros.put("dateInicial",formateoDeFechas.formateStringToDate(fechaInicial));
                            parametros.put("dateFinal",formateoDeFechas.formateStringToDate(fechaFinal));
                            parametros.put("estado",estado);
                            if (tipo==0){
                                reportName = "OfertasByEstado.jasper";
                            } else if (tipo==1){
                                if (estado.equals(EstadoEntrevista.PENDIENTE.toString())) {
                                    reportName = "EntrevistasPendientes.jasper";
                                } else {
                                    reportName = "EntrevistasFinalizadas.jasper";
                                }
                            }
                        }
                    } catch (NoExisteException | ParseException e) {
                        e.printStackTrace();
                        resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                    }
                    break;
                case 2:
                    try {
                        parametros.put("total",new ComisionService().getGastosTotales(empleador));
                        reportName = "GastosTotales.jasper";
                    } catch (NoExisteException e) {
                        e.printStackTrace();
                        resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    }
            }
            printReport(req,resp,empleador,resPath+reportName,parametros);
        }
    }

    private void printReport(HttpServletRequest req, HttpServletResponse resp, String empleador, String reportPath,
                             Map<String, Object> parametros) throws IOException {
        printPDF(resp, empleador, reportPath, parametros);
    }

    public static void printPDF(HttpServletResponse resp, String empleador, String reportPath, Map<String, Object> parametros) throws IOException {
        try (InputStream inputStream = new FileInputStream(reportPath)) {
            String empName = new UsuarioService().getNombreByID(empleador);
            resp.setContentType("application/pdf");
            resp.addHeader("Content-disposition", "attachment; filename=reporte.pdf");
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(inputStream);
            parametros.put("empresaCode",empName);
            parametros.put("empresaName", empleador);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport,
                    parametros,
                    ConectorSingleton.getInstance().getConnection());
            OutputStream outputStream = resp.getOutputStream();
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            outputStream.flush();
            outputStream.close();
        } catch (JRException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            throw new RuntimeException(e);
        } catch (SQLException e) {
            e.printStackTrace();
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
            throw new RuntimeException(e);
        }
    }
}
