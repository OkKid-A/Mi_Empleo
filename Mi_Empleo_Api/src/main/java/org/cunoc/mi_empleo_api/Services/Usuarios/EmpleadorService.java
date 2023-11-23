package org.cunoc.mi_empleo_api.Services.Usuarios;
import org.cunoc.mi_empleo_api.Empleo.Oferta;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Empleos.OfertaService;
import org.cunoc.mi_empleo_api.Services.Service;
import org.cunoc.mi_empleo_api.Usuario.Empleador.Tarjeta;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class EmpleadorService  extends Service {

    public EmpleadorService() {
    }

    public List<Oferta> getAllOfertasEmpresaByEstado(String empresa, String estado) throws NoExisteException {
            String searchQ = "SELECT o.* ,u.nombre, c.nombre FROM oferta o INNER JOIN usuario u ON u.codigo = o.empresa" +
                    " INNER JOIN categoria c ON c.codigo = o.categoria WHERE o.empresa = ? AND o.estado = ?";
            String[] values =  new String[]{empresa,estado};
        try {
            return new OfertaService().getFromOferta(searchQ, values);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NoExisteException("No se encontraron ofertas");
        }
    }
    public String getCodigoEmpleadorByOferta(String oferta) throws SQLException {
        String selectQ = "SELECT empresa FROM oferta WHERE codigo = ?";
        try (ResultSet resultSet = getDBStatements().select(selectQ, new String[]{oferta})) {
            if (resultSet.next()) {
                return resultSet.getString("empresa");
            } else {
                return null;
            }
        }
    }

    public String getEmail(String codigo) throws SQLException {
        String selectQ = "SELECT email FROM usuario WHERE codigo = ?";
        try (ResultSet resultSet = getDBStatements().select(selectQ)) {
            if (resultSet.next()) {
                return resultSet.getString("email");
            }
        }
        return null;
    }

    public void updateEmpleador(Tarjeta tarjeta, String mision, String vision, String usuario) throws SQLException, InvalidDataException {
        String updateQ = "UPDATE empleador SET vision = ?, mision = ? WHERE codigo_usuario = ?";
        String insertTarjeta = "INSERT INTO tarjeta (titular, numero, cvv, id_empleador) VALUES (?,?,?,?)";
        getDBStatements().updateMultipleStatements(new String[]{updateQ,insertTarjeta},
                new String[][]{new String[]{
                        vision,
                        mision,
                        usuario
                },new String[]{
                        tarjeta.getTitular(),
                        tarjeta.getNumero(),
                        String.valueOf(tarjeta.getCvv()),
                        usuario
                }});
    }
}
