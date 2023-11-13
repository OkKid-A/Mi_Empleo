package org.cunoc.mi_empleo_api.Services.Empleos;

import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.DB.DBOferta;
import org.cunoc.mi_empleo_api.Empleo.EstadoOferta;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Service;
import org.cunoc.mi_empleo_api.Services.UsuarioService;
import org.cunoc.mi_empleo_api.Usuario.Solicitante.Postulacion;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PostulacionService extends Service {

    private DBOferta dbOferta;
    private UsuarioService usuarioService;
    public PostulacionService(Conector conector) {
        super(conector);
        this.dbOferta = new DBOferta(getConector());
        this.usuarioService  = new UsuarioService(getConector());
    }

    public Oferta asignarGanadorAOferta(String ganador, String codigoOferta) throws NoExisteException {
        String updateQ = "UPDATE oferta SET ganador = ? WHERE codigo = ?";
        try {
            getConector().updateWithException(updateQ, new String[]{
                    ganador,
                    codigoOferta
            });
            dbOferta = new DBOferta(new Conector());
            Oferta oferta = dbOferta.buscarByCodigo(Integer.parseInt(codigoOferta));
            dbOferta.cambiarEstadoDeOferta(oferta, String.valueOf(EstadoOferta.FINALIZADA));
            return oferta;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NoExisteException("No existe la oferta "+codigoOferta);
        }
    }

    public List<Postulacion> getCandidatosOfertaByOFerta(String empleador, String oferta) throws SQLException {
        List<Postulacion> postulaciones = new ArrayList<>();
        String selectQ = String.format("SELECT o.* ,u.*, s.* FROM solicitud s INNER JOIN usuario u ON u.codigo = s.usuario" +
                " INNER JOIN oferta o ON s.codigo_oferta = o.codigo WHERE s.codigo_oferta = %s AND" +
                        " s.usuario NOT IN (SELECT solicitante FROM entrevista WHERE codigo_oferta = %s)",
                oferta,oferta);
        ResultSet resultSet = getConector().selectFrom(selectQ);
        while (resultSet.next()){
            postulaciones.add(crearPostulacionFromDB(resultSet));
        }
        return postulaciones;
    }

    public Postulacion crearPostulacionFromDB(ResultSet resultSet) throws SQLException {
        List<String > telefonos = usuarioService.getTelefonosByID(resultSet.getString("u.codigo"));
        String [] telefonoSArray = telefonos.toArray(new String[telefonos.size()]);
        return new Postulacion(
            resultSet.getString("u.nombre"),
                resultSet.getString("u.direccion"),
                telefonoSArray,
                resultSet.getString("u.email"),
                resultSet.getInt("s.codigo_oferta"),
                resultSet.getInt("u.codigo"),
                resultSet.getString("s.mensaje"),
                resultSet.getString("o.nombre")
        );
    }


}
