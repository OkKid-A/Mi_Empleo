package org.cunoc.mi_empleo_api.Services.Empleos;

import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.DB.FormateoDeFechas;
import org.cunoc.mi_empleo_api.Empleo.Entrevista;
import org.cunoc.mi_empleo_api.Empleo.EstadoEntrevista;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Exceptions.NoExisteException;
import org.cunoc.mi_empleo_api.Services.Service;
import org.cunoc.mi_empleo_api.Services.UsuarioService;
import org.cunoc.mi_empleo_api.Usuario.Usuario;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class EntrevistaService extends Service {
    public EntrevistaService(Conector conector) {
        super(conector);
    }

    public Entrevista crearEntrevista(Entrevista entrevista) throws InvalidDataException {
        String insertQ = "INSERT INTO entrevista (solicitante, fecha, hora, estado, ubicacion, codigo_oferta)" +
                " VALUES (?,?,?,?,?,?)";
        try {
            getConector().updateWithException(insertQ, new String[]{
                    String.valueOf(entrevista.getSolicitante()),
                    new FormateoDeFechas().formatioDateAString(entrevista.getFecha()),
                    String.valueOf(entrevista.getHora()),
                    entrevista.getEstado(),
                    entrevista.getUbicacion(),
                    String.valueOf(entrevista.getCodigoOferta())
            });
            return entrevista;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataException();
        }
    }

    public boolean tieneEntrevista(String codigoOferta){
        String selectQ = "SELECT * FROM entrevista WHERE codigo_oferta = "+codigoOferta;
        ResultSet resultSet = getConector().selectFrom(selectQ);
        try {
            if (resultSet.next()){
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Entrevista> getAllEntrevistasOferta(String codigoOferta) throws NoExisteException {
        String selectQ = "SELECT * FROM entrevista WHERE codigo_oferta = "+codigoOferta;
        UsuarioService usuarioService = new UsuarioService(getConector());
        ResultSet resultSet = getConector().selectFrom(selectQ);
        List<Entrevista> entrevistas = new ArrayList<>();
        try {
            while (resultSet.next()){
                entrevistas.add(new Entrevista(
                    resultSet.getInt("codigo"),
                        resultSet.getInt("solicitante"),
                        resultSet.getDate("fecha"),
                        resultSet.getTime("hora"),
                        resultSet.getString("estado"),
                        resultSet.getString("ubicacion"),
                        resultSet.getString("notas"),
                        resultSet.getInt("codigo_oferta"),
                        usuarioService.getNombreByID(String.valueOf(resultSet.getInt("solicitante")))
                ));
            }
            return entrevistas;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new NoExisteException("No se encontraron entrevistas");
        }
    }

    public Entrevista finalizarEntrevista(Entrevista entrevista, String notas) throws InvalidDataException {
        String updateQ = "UPDATE entrevista SET notas = ?, estado = ? WHERE codigo = ?";
        try {
            getConector().updateWithException(updateQ, new String[]{
                    notas,
                    String.valueOf(EstadoEntrevista.FINALIZADA),
                    String.valueOf(entrevista.getCodigo())
            });
            entrevista.setNotas(notas);
            entrevista.setEstado(String.valueOf(EstadoEntrevista.FINALIZADA));
            return entrevista;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new InvalidDataException();
        }
    }

    public void borrarEntrevista(String  codigo) throws SQLException {
        String deleteQ = "DELETE FROM entrevista WHERE codigo_oferta = ?";
        getConector().updateWithException(deleteQ, new String[]{
                String.valueOf(codigo)
        });
    }
}
