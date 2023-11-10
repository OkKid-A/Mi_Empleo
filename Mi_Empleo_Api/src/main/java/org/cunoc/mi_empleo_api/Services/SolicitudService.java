package org.cunoc.mi_empleo_api.Services;

import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Empleo.Solicitud;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;

import javax.management.InvalidAttributeValueException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class SolicitudService {

    private Conector conector;

    public SolicitudService(Conector conector) {
        this.conector = conector;
    }

    public List<Oferta> getOfertasConSolicitud(String usuario, String estado) throws SQLException {
        OfertaService ofertaService = new OfertaService(conector);
        String searchQ = String.format("SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                " INNER JOIN categoria c ON c.codigo = o.categoria INNER JOIN solicitud s ON o.codigo = s.codigo_oferta" +
                " WHERE s.usuario = %s AND o.estado = %s",
                usuario,
                conector.encomillar(estado));
        return ofertaService.getFromOferta(searchQ);

    }

    public void crearSolicitud(Solicitud solicitud) throws InvalidDataException, NoExisteException {
        String insertSolicitud = "INSERT INTO solicitud (codigo_oferta,usuario,mensaje) VALUES (?,?,?)";
        try {
            if (validateSolicitud(solicitud)) {
                conector.updateWithException(insertSolicitud, new String[]{
                        String.valueOf(solicitud.getCodigo_oferta()),
                        String.valueOf(solicitud.getUsuario()),
                        solicitud.getMensaje()
                });
                System.out.println("se creo"+solicitud.getMensaje());
            } else {
                throw new InvalidDataException();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataException(e.getMessage());
        }
    }

    public void deleteSolicitud(String usuario, String oferta) throws NoExisteException {
        String deleteQ = "DELETE FROM solicitud WHERE codigo_oferta = ? AND usuario = ?";
        try {
            conector.updateWithException(deleteQ,new String[]{
                    oferta,
                    usuario
            });
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NoExisteException(e.getMessage());
        }
        System.out.println("se borro"+usuario);
    }

    public boolean validateSolicitud(Solicitud solicitud) throws NoExisteException, InvalidDataException {
        String selectQ = String.format("SELECT * FROM solicitud WHERE codigo_oferta = %s AND usuario = %s",
                solicitud.getCodigo_oferta(),
                solicitud.getUsuario());
        ResultSet resultSet = conector.selectFrom(selectQ);
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
