package org.cunoc.mi_empleo_api.Services.Empleos;

import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Empleo.Solicitud;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class SolicitudService extends Service {

    public SolicitudService() {
    }

    public List<Integer> getAllSolicitudesDeOferta (String oferta) throws InvalidDataException {
        String searchQ = "SELECT usuario FROM solicitud WHERE codigo_oferta = ?";
        List<Integer> codigosUsuario = new ArrayList<>();
        try (ResultSet resultSet = getDBStatements().select(searchQ, new String[]{oferta})) {
            while (resultSet.next()) {
                codigosUsuario.add(resultSet.getInt("usuario"));
            }
        } catch (SQLException e) {
            System.out.println("No se reconocen solicitudes para la oferta"+ e.getMessage());
            throw new InvalidDataException();
        }
        return codigosUsuario;
    }

    public List<Oferta> getOfertasConSolicitud(String usuario, String estado) throws SQLException, ParseException {
        OfertaService ofertaService = new OfertaService();
        String searchQ = "SELECT o.* ,u.nombre, u.codigo, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                " INNER JOIN categoria c ON c.codigo = o.categoria INNER JOIN solicitud s ON o.codigo = s.codigo_oferta" +
                " WHERE s.usuario = ? AND o.estado = ?";
        return ofertaService.getFromOferta(searchQ, new String[]{usuario,estado});

    }

    public void crearSolicitud(Solicitud solicitud) throws InvalidDataException, NoExisteException {
        String insertSolicitud = "INSERT INTO solicitud (codigo_oferta,usuario,mensaje) VALUES (?,?,?)";
        if (validateSolicitud(solicitud)) {
            getDBStatements().update(insertSolicitud, new String[]{
                    String.valueOf(solicitud.getCodigo_oferta()),
                    String.valueOf(solicitud.getUsuario()),
                    solicitud.getMensaje()
            });
            System.out.println("se creo"+solicitud.getMensaje());
        } else {
            throw new InvalidDataException();
        }
    }

    public void deleteSolicitud(String usuario, String oferta) throws SQLException {
        String deleteQ = "DELETE FROM solicitud WHERE codigo_oferta = ? AND usuario = ?";
            getDBStatements().update(deleteQ,new String[]{
                    oferta,
                    usuario
            });
        System.out.println("se borro"+usuario);
    }

    public boolean validateSolicitud(Solicitud solicitud) throws NoExisteException, InvalidDataException {
        String selectQ = "SELECT * FROM solicitud WHERE codigo_oferta = ? AND usuario = ?";
        ResultSet resultSet = getDBStatements().select(selectQ, new String[]{String.valueOf(solicitud.getCodigo_oferta()),
                String.valueOf(solicitud.getUsuario())});
        try {
            if (resultSet.next()){
                throw new NoExisteException("Ya existe una aplicacion");
            } else if (solicitud.getMensaje().equals("")||solicitud.getCodigo_oferta() == 0||solicitud.getUsuario()==0){
                throw new InvalidDataException();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
