package org.cunoc.mi_empleo_api.Services;

import com.mysql.cj.jdbc.result.ResultSetImpl;
import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Empleo.Categoria;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriasService extends Service{

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
}
