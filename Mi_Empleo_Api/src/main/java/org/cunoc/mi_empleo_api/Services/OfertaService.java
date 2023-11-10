package org.cunoc.mi_empleo_api.Services;

import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.DB.DBOferta;
import org.cunoc.mi_empleo_api.Empleo.EstadoOferta;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfertaService {

    private Conector conector;
    private DBOferta dbOferta;

    public OfertaService(Conector conector) {
        this.conector = conector;
        this.dbOferta = new DBOferta(conector);
    }

    public Oferta getOferta(int codigo) throws NoExisteException, SQLException {
        Oferta oferta = dbOferta.buscarByCodigo(codigo);
        return oferta;
    }

    public List<Oferta> getAllOfertas(String searchKey, int filtro) throws SQLException {
        String searchQ = selectQuery(searchKey, filtro);
        return getFromOferta(searchQ);
    }

    public List<Oferta> getAllOfertas(String estado) throws SQLException {
        String searchQ = String.format("SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.estado = %s", conector.encomillar(estado));
        return getFromOferta(searchQ);
    }

    public List<Oferta> getFromOferta(String searchQ) throws SQLException {
        ResultSet resultSet = conector.selectFrom(searchQ);
        List<Oferta> ofertas = new ArrayList<>();
        while (resultSet.next()){
            ofertas.add(dbOferta.crearOferta(resultSet));
        }
        return ofertas;
    }

    private String selectQuery(String searchKey, int filtro){
        if (searchKey==null){
            return "SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                    " INNER JOIN categoria c ON c.codigo = o.categoria";
        } else {
            switch (filtro){
                case 0:
                    return String.format("SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                            " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.nombre LIKE %s",
                            conector.encomillar(conector.enPorcentaje(searchKey)));
                case 1:
                    return String.format("SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                            " INNER JOIN categoria c ON c.codigo = o.categoria WHERE c.nombre LIKE %s",
                            conector.encomillar(conector.enPorcentaje(searchKey)));
                case 2:
                    return String.format("SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                            " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.modalidad LIKE %s",
                            conector.encomillar(conector.enPorcentaje(searchKey.toUpperCase())));
                case 3:
                    return String.format("SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                            " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.ubicacion LIKE %s",
                            conector.encomillar(conector.enPorcentaje(searchKey)));
                case 4:
                    return String.format("SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                            " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.salario LIKE %s",
                            conector.encomillar(conector.enPorcentaje(searchKey)));
            }
        }
        return null;
    }
}
