package org.cunoc.mi_empleo_api.Services.Empleos;

import org.cunoc.mi_empleo_api.DB.ConectorSingleton;
import org.cunoc.mi_empleo_api.DB.DBOferta;
import org.cunoc.mi_empleo_api.Archivos.FormateoDeFechas;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OfertaService extends Service {
    
    private final DBOferta dbOferta;
    
    public OfertaService() {
        this.dbOferta = new DBOferta();
    }

    public Oferta getOferta(int codigo) throws NoExisteException, SQLException {
        return dbOferta.buscarByCodigo(codigo);
    }

    public List<Oferta> getAllOfertas(String searchKey, int filtro) throws SQLException {
        String searchQ = selectQuery(searchKey, filtro);
        if (searchKey!=null&& !searchKey.isEmpty()) {
            return getFromOferta(searchQ, new String[]{'%'+searchKey+'%'});
        } else {
            return getFromOferta(searchQ);
        }
    }

    public List<Oferta> getAllOfertas(String estado) throws NoExisteException {
        String searchQ = "SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.estado = ?";
        try {
            return getFromOferta(searchQ, new String[]{estado});
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NoExisteException("No se encontraron ofertas");
        }
    }

    public List<Oferta> getAllOfertasEmpresa(String empresa, String searchKey) throws NoExisteException {
        String searchQ;
        String[] values;
        if (searchKey != null) {
            searchQ = "SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                    " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.empresa = ? AND o.nombre LIKE ?";
            values =  new String[]{empresa,'%'+searchKey+'%'};
        } else {
            searchQ = "SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                    " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.empresa = ?";
            values =  new String[]{empresa};
        }
        try {
            return getFromOferta(searchQ, values);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NoExisteException("No se encontraron ofertas");
        }
    }

    public List<Oferta> getFromOferta(String searchQ, String[] values) throws SQLException {
        ResultSet resultSet = getDBStatements().select(searchQ,values);
        List<Oferta> ofertas = new ArrayList<>();
        while (resultSet.next()){
            ofertas.add(dbOferta.crearOferta(resultSet));
        }
        return ofertas;
    }

    public List<Oferta> getFromOferta(String searchQ) throws SQLException {
        ResultSet resultSet = getDBStatements().select(searchQ);
        List<Oferta> ofertas = new ArrayList<>();
        while (resultSet.next()){
            ofertas.add(dbOferta.crearOferta(resultSet));
        }
        return ofertas;
    }

    public Oferta editarOferta(Oferta oferta, String codigo) throws InvalidDataException {
        CategoriasService categoriasService = new CategoriasService();
        FormateoDeFechas formater = new FormateoDeFechas();
        String fechaPublicacion = formater.formatioDateAString(oferta.getFechaPublicacion());
        String fechaLimite = formater.formatioDateAString(oferta.getFechaLimite());
        try {
            String editQ = "UPDATE oferta SET nombre = ?, salario = ?, descripcion = ?, fecha_publicacion = ?, fecha_limite = ?," +
                    " ubicacion = ?, modalidad = ?, categoria = ?, detalles = ? WHERE codigo = ?";
            getDBStatements().update(editQ, new String[]{
                    oferta.getNombre(),
                    String.valueOf(oferta.getSalario()),
                    oferta.getDescripcion(),
                    fechaPublicacion,
                    fechaLimite,
                    oferta.getUbicacion(),
                    oferta.getModalidad(),
                    categoriasService.getCodigoByNombre(oferta.getCategoria()),
                    oferta.getDetalles(),
                    codigo
            });
            return oferta;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataException();
        }
    }

    public void eliminarOferta(String codigOferta, String estado) throws InvalidDataException, SQLException, NoExisteException {
        SolicitudService solicitudService = new SolicitudService();
        List<Integer> codigosSolicitudes = solicitudService.getAllSolicitudesDeOferta(codigOferta);
        EntrevistaService entrevistaService = new EntrevistaService();
        Connection connection = ConectorSingleton.getInstance().getConnection();
        connection.setAutoCommit(false);
        try {
            switch (estado) {
                case "ACTIVA":
                    for (int usuario : codigosSolicitudes) {
                        solicitudService.deleteSolicitud(String.valueOf(usuario), codigOferta);
                    }
                    break;
                case "ENTREVISTA":
                case "SELECCION":
                    for (int usuario : codigosSolicitudes) {
                        solicitudService.deleteSolicitud(String.valueOf(usuario), codigOferta);
                    }
                    entrevistaService.borrarEntrevista(codigOferta);
                    break;
                default:
                    throw new InvalidDataException();
            }
            String deleteQ = "DELETE FROM oferta WHERE codigo = ?";
            getDBStatements().update(deleteQ, new String[]{String.valueOf(codigOferta)});
            System.out.println("se borro" + codigOferta);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String selectQuery(String searchKey, int filtro){
        if (searchKey.isEmpty()){
            return "SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                    " INNER JOIN categoria c ON c.codigo = o.categoria WHERE estado = 'ACTIVA'";
        } else {
            switch (filtro){
                case 0:
                    return "SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                            " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.nombre LIKE ? AND estado = 'ACTIVA'";
                case 1:
                    return "SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                            " INNER JOIN categoria c ON c.codigo = o.categoria WHERE c.nombre LIKE ? AND estado = 'ACTIVA'";
                case 2:
                    return "SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                            " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.modalidad LIKE ? AND estado = 'ACTIVA'";
                case 3:
                    return "SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                            " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.ubicacion LIKE ? AND estado = 'ACTIVA'";
                case 4:
                    return "SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                            " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.salario LIKE ? AND estado = 'ACTIVA'";
            }
        }
        return null;
    }

    public DBOferta getDbOferta() {
        return dbOferta;
    }

}
