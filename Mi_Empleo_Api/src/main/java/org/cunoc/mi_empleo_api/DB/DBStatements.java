package org.cunoc.mi_empleo_api.DB;

import org.cunoc.mi_empleo_api.Exceptions.InvalidDataException;

import java.sql.*;

public class DBStatements {

    private Connection connection;

    public DBStatements() {
        this.connection = ConectorSingleton.getInstance().getConnection();
    }

    public DBStatements(Connection connection) {
        this.connection = connection;
    }

    public ResultSet select(String query, String[] values) {
        try {
            PreparedStatement select = connection.prepareStatement(query);
            for (int k = 0; k < values.length; k++) {
                select.setString(k + 1, values[k]);
            }
            return select.executeQuery();
        } catch (SQLException throwables) {
            System.out.println("No se pudo recibir informacion de la DB: "+ throwables.getMessage());
            return null;
        }
    }

    public ResultSet select(String query){
        try {
            PreparedStatement select = connection.prepareStatement(query);
            return select.executeQuery();
        } catch (SQLException throwables) {
            System.out.println("No se pudo recibir informacion de la DB: "+ throwables.getMessage());
            throwables.printStackTrace();
            return null;
        }
    }

    public int update(String query, String[] values){
        try {
            PreparedStatement update = connection.prepareStatement(query);
            for (int k = 0; k < values.length; k++) {
                update.setString(k + 1, values[k]);
            }
            return update.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Se ingresaron datos incorrectos en la query: "+query+" y hubo un fallo en : "+e.getMessage());
            return 0;
        }finally {
            if (connection!=null){
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("No se pudo cerrar la conexion para la query: "+ query +"\n"+  e.getMessage());
                }
            }
        }
    }

    public int updateWithException(String query, String[] values) throws SQLException {
            PreparedStatement update = connection.prepareStatement(query);
            for (int k = 0; k < values.length; k++) {
                update.setString(k + 1, values[k]);
            }
            return update.executeUpdate();
    }

    public void updateMultipleStatements(String[] queries, String[][] values) throws InvalidDataException {
        int i = 0;
        int k = 0;
        try{
            connection.setAutoCommit(false);
            for (i = 0; i < values.length; i++){
                try (PreparedStatement update = connection.prepareStatement(queries[i])) {
                    for (k = 0; k < values[i].length; k++) {
                        update.setString(k + 1, values[i][k]);
                    }
                    update.executeUpdate();
                }
            }
            connection.commit();
        } catch (SQLException e) {
            System.out.println("Se ingresaron datos incorrectos en la query: "+queries[i]+" y hubo un fallo en : "+
                    values[i][k-1]+e.getMessage());
            e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException ex) {
                System.out.println("fallo el rollback, se llenaron los cambios hasta la query "+ queries[i]);
                ex.printStackTrace();
            }
            throw new InvalidDataException();
        } finally {
            if (connection!= null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("No se pudo cerrar la conexion para la query: " + queries[i] + "\n" + e.getMessage());
                }
            }
        }
    }

    public void setAutoComitFalse() throws SQLException {
        connection.setAutoCommit(false);
    }

    public void commit() throws SQLException {
        connection.commit();
    }

    public Connection getConnection() {
        return connection;
    }
}
