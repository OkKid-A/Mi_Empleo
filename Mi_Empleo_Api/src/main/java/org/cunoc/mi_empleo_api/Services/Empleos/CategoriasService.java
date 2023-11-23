package org.cunoc.mi_empleo_api.Services.Empleos;


import org.cunoc.mi_empleo_api.Empleo.Categoria;
import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;
import org.cunoc.mi_empleo_api.Services.Service;

import java.awt.geom.Area;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriasService extends Service {

    public CategoriasService() {
    }

    public List<Categoria> getAllCategorias() throws SQLException {
        String selectQ = "SELECT * FROM categoria";
        List<Categoria> categorias;
        try (ResultSet resultSet = getDBStatements().select(selectQ)) {
            categorias = new ArrayList<>();
            while (resultSet.next()) {
                categorias.add(new Categoria(resultSet.getInt("codigo"),
                        resultSet.getString("nombre"),
                        resultSet.getString("descripcion")));
            }
        }
        return categorias;
    }

    public String getCodigoByNombre(String nombre) throws SQLException {
        String selectQ = "SELECT codigo FROM categoria WHERE nombre = ?";
        try (ResultSet resultSet = getDBStatements().select(selectQ, new String[]{nombre})) {
            if (resultSet.next()) {
                return String.valueOf(resultSet.getInt("codigo"));
            } else {
                return null;
            }
        }
    }

    public void updatePreferences(String[] categorias, String usuario) throws SQLException, InvalidDataException {
        String insertQ = "INSERT IGNORE INTO preferencia (id_categoria, id_usuario) VALUES (?,?)";
        ArrayList<String> insertQueries = new ArrayList<>();
        ArrayList<String[]> values = new ArrayList<>();
        for (String categoria:categorias){
            insertQueries.add(insertQ);
            values.add(new String[]{
                    categoria,
                    usuario
            });
        }
        String[] insertQs = insertQueries.toArray(new String[insertQueries.size()]);
        String[][] valores = values.toArray(new String[values.size()][]);
        getDBStatements().updateMultipleStatements(insertQs,valores);
    }

    public void editarCategoria(Categoria categoria) throws SQLException {
        String updateQ = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE codigo = ?";
        getDBStatements().update(updateQ, new String[]{
                categoria.getNombre(),
                categoria.getDescripcion(),
                String.valueOf(categoria.getCodigo())
        });
    }

    public void addCategoria(Categoria categoria) throws SQLException {
        String insertQ = "INSERT INTO categoria (nombre, descripcion) VALUES (?,?)";
        getDBStatements().update(insertQ, new String[]{
                categoria.getNombre(),
                categoria.getDescripcion()
        });
    }

}
