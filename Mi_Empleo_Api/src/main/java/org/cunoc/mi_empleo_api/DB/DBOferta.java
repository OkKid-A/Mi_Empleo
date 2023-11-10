package org.cunoc.mi_empleo_api.DB;

import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;

import java.sql.ResultSet;
import java.sql.SQLException;

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
        return new Oferta(resultSet.getString("u.nombre"),
                resultSet.getString("c.nombre"),
                resultSet.getString("ubicacion"),
                resultSet.getString("estado"),
                resultSet.getDate("fecha_publicacion"),
                resultSet.getDate("fecha_limite"),
                resultSet.getString("detalles"),
                resultSet.getInt("ganador"),
                resultSet.getString("modalidad"),
                resultSet.getFloat("salario"),
                resultSet.getInt("codigo"),
                resultSet.getString("nombre"),
                resultSet.getString("descripcion"));
    }
}
