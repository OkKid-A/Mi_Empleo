package org.cunoc.mi_empleo_api.DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConectorSingleton {

    private static ConectorSingleton instance = null;
    private final String usuario = "rootdba";
    private final String portUrl = "jdbc:mysql://localhost:3306/Mi_Empleo";
    private final String password = "1234";
    private Connection connection;

    private ConectorSingleton() throws SQLException {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            this.connection = DriverManager.getConnection(portUrl, usuario, password);
        }catch (ClassNotFoundException e) {
            System.out.println("Error en el driver de la jdbc :"+e.getMessage());
        }
    }

    public static ConectorSingleton getInstance() {
        try {
            if (instance==null || instance.getConnection().isClosed()){
                instance = new ConectorSingleton();
            }
        } catch (SQLException e) {
            System.out.println("Error al crear la base de datos: "+e.getMessage());
        }
        return instance;
    }

    public Connection getConnection(){
        return connection;
    }

}


