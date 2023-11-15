package org.cunoc.mi_empleo_api.Services.Empleos;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Empleo.Categoria;
import org.cunoc.mi_empleo_api.Services.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriasService extends Service {

    public CategoriasService(Conector conector) {
        super(conector);
    }

    public List<Categoria> getAllCategorias() throws SQLException {
        String selectQ = "SELECT * FROM categoria";
        ResultSet resultSet = getConector().selectFrom(selectQ);
        List<Categoria> categorias = new ArrayList<>();
        while (resultSet.next()){
            categorias.add(new Categoria(resultSet.getInt("codigo"),
                    resultSet.getString("nombre"),
                    resultSet.getString("descripcion")));
        }
        return categorias;
    }

    public String getCodigoByNombre(String nombre) throws SQLException {
        String selectQ = String.format("SELECT codigo FROM categoria WHERE nombre = %s",getConector().encomillar(nombre));
        ResultSet resultSet = getConector().selectFrom(selectQ);
        if (resultSet.next()){
            return String.valueOf(resultSet.getInt("codigo"));
        } else {
            return null;
        }
    }

    public void updatePreferences(String[] categorias, String usuario) throws SQLException {
        String insertQ = String.format("INSERT IGNORE INTO preferencia (id_categoria, id_usuario) VALUES (?,?)");
        for (String categoria:categorias){
            getConector().updateWithException(insertQ,new String[]{
                    categoria,
                    usuario
            });
        }
    }

    public void editarCategoria(Categoria categoria) throws SQLException {
        String updateQ = "UPDATE categoria SET nombre = ?, descripcion = ? WHERE codigo = ?";
        getConector().updateWithException(updateQ, new String[]{
                categoria.getNombre(),
                categoria.getDescripcion(),
                String.valueOf(categoria.getCodigo())
        });
    }

    public void addCategoria(Categoria categoria) throws SQLException {
        String insertQ = "INSERT INTO categoria (nombre, descripcion) VALUES (?,?)";
        getConector().updateWithException(insertQ, new String[]{
                categoria.getNombre(),
                categoria.getDescripcion()
        });
    }

}
