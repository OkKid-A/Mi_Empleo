package org.cunoc.mi_empleo_api.DB;

import org.cunoc.mi_empleo_api.Archivos.FormateoDeFechas;
import org.cunoc.mi_empleo_api.Empleo.EstadoOferta;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Empleos.OfertaService;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DBFecha {

    private DBStatements dbStatements;
    private FormateoDeFechas formateoDeFechas;
    public DBFecha() {
        this.dbStatements = new DBStatements();
        this.formateoDeFechas = new FormateoDeFechas();
    }

    public String getFechaDB() throws NoExisteException {
        String selectQ = "SELECT valor FROM parametros WHERE nombre = 'fecha'";
        try (ResultSet resultSet = dbStatements.select(selectQ)) {
                if (resultSet.next()) {
                    return resultSet.getString("valor");
                } else {
                    return "2023-11-12";
                }
            } catch (SQLException e) {
                e.printStackTrace();
                throw new NoExisteException(e.getMessage());
            }
    }

    public boolean fechaEsPasado(String fechaNueva) throws NoExisteException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fechaComparar = sdf.parse(fechaNueva);
            Date fechaActual = formateoDeFechas.formateStringToDate(getFechaDB());
            if (fechaActual.after(fechaComparar)){
                return true;
            } else {
                return false;
            }
        } catch (ParseException | NoExisteException e) {
            e.printStackTrace();
            throw new NoExisteException(e.getMessage());
        }
    }

    public boolean fechaEsPasado(String fechaInicial, String fechaFinal) throws NoExisteException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date fechaComparar = sdf.parse(fechaInicial);
            Date fechaActual = sdf.parse(fechaFinal);
            if (fechaActual.after(fechaComparar)){
                return true;
            } else {
                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
            throw new NoExisteException(e.getMessage());
        }
    }

    public int updateFecha(String fecha) throws ParseException, NoExisteException, InvalidDataException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(fecha);
        String fechaString = sdf.format(date);
        String update = "UPDATE parametros SET valor = ? WHERE nombre = 'fecha'";
        List<Oferta> ofertasActivas = new OfertaService().getAllOfertas(EstadoOferta.ACTIVA.toString());
        Connection connection = ConectorSingleton.getInstance().getConnection();
        this.dbStatements = new DBStatements(connection);
        int contador = 0;
        try {
            dbStatements.updateWithException(update,new String[]{
                    fechaString
            });
            connection.setAutoCommit(false);
            contador = aplicarCambiosFecha(connection,ofertasActivas);
            return contador;
        } catch (SQLException e) {
            System.out.println("Hubo un error al cambiar fechas en la oferta: "+contador+"\n"+e.getMessage());
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("fallo el rollback, se llenaron los cambios hasta la oferta"+ contador);
            }
            throw new InvalidDataException();
        } finally {
            if (connection!= null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("No se pudo cerrar la conexion para la query:" + e.getMessage());
                }
            }
        }
    }

    public int aplicarCambiosFecha(Connection conn, List<Oferta> ofertasActivas) throws SQLException, NoExisteException {
        String updateOferta = "UPDATE oferta SET estado = ? WHERE codigo = ?";
        int count = 0;
        for (Oferta oferta : ofertasActivas){
            String fechaLimite = formateoDeFechas.formatioDateAString(oferta.getFechaLimite());
            if (fechaEsPasado(fechaLimite)){
                dbStatements.updateWithException(updateOferta, new String[]{
                        EstadoOferta.SELECCION.toString(),
                        String.valueOf(oferta.getCodigo())
                });
                count++;
            }
        }
        return count;
    }
}
