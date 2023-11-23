package org.cunoc.mi_empleo_api.DB;

import org.cunoc.mi_empleo_api.Archivos.FormateoDeFechas;
import org.cunoc.mi_empleo_api.Empleo.EstadoOferta;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class DBOferta {

    private final String UPDATE_ESTADO = "UPDATE oferta SET estado = ? WHERE codigo = ?";
    private final String UPDATE_GANADOR = "UPDATE oferta SET ganador = ? WHERE codigo = ?";
    private final DBStatements dbStatements;

    public DBOferta() {
        this.dbStatements = new DBStatements();
    }

    public Oferta buscarByCodigo(int codigo) throws NoExisteException {
        String searchQ = "SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa " +
                "                     INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.codigo = ?";
        ResultSet resultSet = dbStatements.select(searchQ, new String[]{
                String.valueOf(codigo)
        });
        Oferta oferta;
        try {
            if (resultSet.next()){
                oferta = crearOferta(resultSet);
                return oferta;
            } else {
                throw new NoExisteException("No existe el usuario "+codigo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NoExisteException(e.getMessage());
        }
    }

    public Oferta crearOferta(ResultSet resultSet) throws SQLException {
        FormateoDeFechas formateoDeFechas = new FormateoDeFechas();
        try {
            return new Oferta(resultSet.getString("u.nombre"),
                    resultSet.getString("c.nombre"),
                    resultSet.getString("ubicacion"),
                    resultSet.getString("estado"),
                    formateoDeFechas.formateDateADate(resultSet.getDate("fecha_publicacion")),
                    formateoDeFechas.formateDateADate(resultSet.getDate("fecha_limite")),
                    resultSet.getString("detalles"),
                    resultSet.getInt("ganador"),
                    resultSet.getString("modalidad"),
                    resultSet.getFloat("salario"),
                    resultSet.getInt("codigo"),
                    resultSet.getString("nombre"),
                    resultSet.getString("descripcion"));
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    public Oferta crearOfertaADB(Oferta oferta) throws InvalidDataException {
        String insertQ = "INSERT INTO oferta (empresa, categoria, ubicacion, estado, fecha_publicacion, fecha_limite, " +
                "detalles, modalidad, salario, nombre, descripcion) VALUES (?,?,?,'ACTIVA',?,?,?,?,?,?,?)";
        FormateoDeFechas formater =  new FormateoDeFechas();
        String fechaPublicacion = formater.formatioDateAString(oferta.getFechaPublicacion());
        String fechaLimite = formater.formatioDateAString(oferta.getFechaLimite());
        dbStatements.update(insertQ, new String[]{
                oferta.getEmpresa(),
                oferta.getCategoria(),
                oferta.getUbicacion(),
                fechaPublicacion,
                fechaLimite,
                oferta.getDetalles(),
                oferta.getModalidad(),
                String.valueOf(oferta.getSalario()),
                oferta.getNombre(),
                oferta.getDescripcion()
        });
        return oferta;
    }

    public void cambiarEstadoDeOferta(Oferta oferta, String nuevoEstado) throws SQLException {
        String codigoOferta = String.valueOf(oferta.getCodigo());
        dbStatements.update(UPDATE_ESTADO, new String[]{
                nuevoEstado,
                codigoOferta
        });
    }

    public void asignarGanadorDeOferta(String ganador, String codigoOferta) throws InvalidDataException {
        String[][] values = new String[2][];
        values[0] = new String[]{ganador, codigoOferta};
        values[1] = new String[]{String.valueOf(EstadoOferta.FINALIZADA), codigoOferta};
        dbStatements.updateMultipleStatements(new String[]{
                UPDATE_GANADOR,
                UPDATE_ESTADO
        },
                values);
    }

    public int getCodigoEmpresaDeOFerta(String codigoOferta) throws NoExisteException {
        String selectQ = "SELECT empresa FROM oferta WHERE codigo = "+codigoOferta;
        try (ResultSet resultSet = dbStatements.select(selectQ)) {
                if (resultSet.next()) {
                    return resultSet.getInt("empresa");
                } else {
                    throw new SQLException();
                }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NoExisteException("No existe la oferta");
        }
    }

}
