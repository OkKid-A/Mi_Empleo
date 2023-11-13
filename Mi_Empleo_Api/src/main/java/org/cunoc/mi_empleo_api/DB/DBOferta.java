package org.cunoc.mi_empleo_api.DB;

import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;

public class DBOferta {

    private Conector conector;

    public DBOferta(Conector conector) {
        this.conector = conector;
    }

    public Oferta buscarByCodigo(int codigo) throws SQLException, NoExisteException {
        String searchQ = String.format("SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa " +
                "                     INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.codigo = %s", codigo);
        ResultSet resultSet = conector.selectFrom(searchQ);
        Oferta oferta = new Oferta();
        if (resultSet.next()){
            oferta = crearOferta(resultSet);
            return oferta;
        } else {
            throw new NoExisteException("No existe el usuario "+codigo);
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
        try {
            conector.updateWithException(insertQ, new String[]{
                    oferta.getEmpresa(),
                    new DBCategoria(conector).getCategoriaCodigoByNombre(oferta.getEmpresa()),
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
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataException();
        }
    }

    public void cambiarEstadoDeOferta(Oferta oferta, String nuevoEstado) throws SQLException {
        String codigoOferta = String.valueOf(oferta.getCodigo());
        String updateQ = "UPDATE oferta SET estado = ? WHERE codigo = ?";
        conector.updateWithException(updateQ, new String[]{
                nuevoEstado,
                codigoOferta
        });
    }

    public int getCodigoEmpresaDeOFerta(String codigoOferta) throws NoExisteException {
        String selectQ = "SELECT empresa FROM oferta WHERE codigo = "+codigoOferta;
        ResultSet resultSet = conector.selectFrom(selectQ);
            try {
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
