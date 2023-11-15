package org.cunoc.mi_empleo_api.Archivos;

import org.cunoc.mi_empleo_api.DB.Conector;
import org.cunoc.mi_empleo_api.Usuario.TipoUsuario;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;

public class CargadorDeArchivos {

    private BufferedReader neededReader;
    private Conector conector;
    private JSONParser jsonParser;


    public CargadorDeArchivos(Conector conector) {
        this.conector = conector;
        this.jsonParser = new JSONParser();
    }

    public void cargarArchivo(BufferedReader reader) throws IOException, ParseException, SQLException {
        Object datos =  jsonParser.parse(reader);
        JSONObject jsonObject = (JSONObject) datos;
        JSONObject adminList = (JSONObject) jsonObject.get("admin");
        JSONArray categoriasList = convertirArray(jsonObject.get("categorias"));
        JSONArray empleadoresList = convertirArray(jsonObject.get("empleadores"));
        JSONArray usuariosList = convertirArray(jsonObject.get("usuarios"));
        JSONArray ofertasList = convertirArray(jsonObject.get("ofertas"));
        ingresarAdmin(adminList);
        ingresarCategorias(categoriasList);
       ingresarSolicitantes(usuariosList);
        ingresarEmpleadores(empleadoresList);
        ingresarOfertas(ofertasList);
    }

    public void ingresarAdmin(JSONObject admin) throws SQLException {
        ingresarUsuario(admin,2);
    }

    public void ingresarCategorias(JSONArray categorias) throws SQLException {
        String insertCategorias = "INSERT  INTO categoria (codigo, nombre, descripcion) VALUES (?,?,?)";
        int size = ((JSONArray)categorias.get(0)).size();
        for (int i = 0; i < size; i++){
            JSONObject categoria = ((JSONObject) (((JSONArray)categorias.get(0)).get(i)));
            conector.updateWithException(insertCategorias, new String[]{
                    String.valueOf((Long)categoria.get("codigo")),
                    (String) categoria.get("nombre"),
                    (String) categoria.get("descripcion")
            });
        }
    }

    public void ingresarSolicitantes(JSONArray usuarios) throws SQLException {
        String insertSolicitante = "INSERT  INTO solicitante (codigo_usuario, cv_path) VALUES (?,?)";
        int size = ((JSONArray) usuarios.get(0)).size();
        for (int i = 0; i < size; i++) {
            JSONObject usuario = ((JSONObject) (((JSONArray)usuarios.get(0)).get(i)));
            ingresarUsuario(usuario,0);
            JSONArray telfonos = convertirArray(usuario.get("telefonos"));
            insertTelefonos(telfonos, String.valueOf((Long)usuario.get("codigo")));
            JSONArray categorias = convertirArray(usuario.get("categorias"));
            insertPreferencias(categorias, String.valueOf((Long)usuario.get("codigo")));
            conector.updateWithException(insertSolicitante,new String[]{
                    String.valueOf((Long)usuario.get("codigo")),
                    (String) usuario.get("curriculum")
            });
        }
    }

    public void ingresarEmpleadores(JSONArray empleadores) throws SQLException {
        String insertEmpleadores = "INSERT INTO empleador (codigo_usuario, vision, mision) VALUES (?,?,?)";
        int size = ((JSONArray) empleadores.get(0)).size();
        for (int i = 0; i < size; i++) {
            JSONObject usuario = ((JSONObject) (((JSONArray)empleadores.get(0)).get(i)));
            ingresarUsuario(usuario,1);
            JSONArray telfonos = convertirArray(usuario.get("telefonos"));
            insertTelefonos(telfonos, String.valueOf((Long)usuario.get("codigo")));
            insertTarjeta((JSONObject) usuario.get("tarjeta"),String.valueOf((Long)usuario.get("codigo")));
            conector.updateWithException(insertEmpleadores, new String[]{
                     String.valueOf((Long)usuario.get("codigo")),
                    (String) usuario.get("vision"),
                    (String) usuario.get("mision")
            });
        }
    }

    public void ingresarOfertas(JSONArray ofertas) throws SQLException {
        String insertOferta = "INSERT INTO oferta (codigo, nombre, descripcion, empresa, categoria, estado, fecha_publicacion," +
                " fecha_limite, salario, modalidad, ubicacion, detalles, ganador) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
        int size = ((JSONArray) ofertas.get(0)).size();
        for (int i = 0; i < size; i++) {
            JSONObject oferta = ((JSONObject) (((JSONArray) ofertas.get(0)).get(i)));
            conector.updateWithException(insertOferta, new String[]{
                    String.valueOf((Long)oferta.get("codigo")),
                    (String) oferta.get("nombre"),
                    (String) oferta.get("descripcion"),
                    String.valueOf((Long)oferta.get("empresa")),
                    String.valueOf((Long)oferta.get("categoria")),
                    (String) oferta.get("estado"),
                    (String) oferta.get("fechaPublicacion"),
                    (String) oferta.get("fechaLimite"),
                    clasificarTipo(oferta.get("salario")),
                    (String) oferta.get("modalidad"),
                    (String) oferta.get("ubicacion"),
                    (String) oferta.get("detalles"),
                    clasificarNulo(oferta.get("usuarioElegido"))
            });
            String codigoOferta = String.valueOf((Long)oferta.get("codigo"));
            JSONArray solicitudes = convertirArray(oferta.get("solicitudes"));
            ingresarSolicitudes(solicitudes,codigoOferta);
            JSONArray entrevistas = convertirArray(oferta.get("entrevistas"));
            ingresarEntrevistas(entrevistas,codigoOferta);
            System.out.println(                    String.valueOf((Long)oferta.get("codigo")));
        }
    }

    public void ingresarEntrevistas(JSONArray entrevistas, String oferta) throws SQLException {
        String insertEntrevistas = "INSERT INTO entrevista (codigo_oferta, solicitante, fecha, hora, ubicacion, estado, " +
                "notas) VALUES (?,?,?,?,?,?,?)";
        int size = ((JSONArray) entrevistas.get(0)).size();
        for (int i = 0; i < size; i++) {
            JSONObject entrevista = ((JSONObject) (((JSONArray) entrevistas.get(0)).get(i)));
            conector.updateWithException(insertEntrevistas, new String[]{
                    oferta,
                    String.valueOf((Long)entrevista.get("usuario")),
                    (String) entrevista.get("fecha"),
                    (String) entrevista.get("hora"),
                    (String) entrevista.get("ubicacion"),
                    (String) entrevista.get("estado"),
                    (String) entrevista.get("notas")
            });

        }
    }

    public void ingresarSolicitudes(JSONArray solicitudes, String codigoOferta) throws SQLException {
        String insertSolicitud = "INSERT  INTO solicitud (codigo_oferta, usuario, mensaje) VALUES (?,?,?)";
        int size = ((JSONArray) solicitudes.get(0)).size();
        for (int i = 0; i < size; i++) {
            JSONObject solicitud = ((JSONObject) (((JSONArray) solicitudes.get(0)).get(i)));
            conector.updateWithException(insertSolicitud, new String[]{
                    codigoOferta,
                    String.valueOf((Long)solicitud.get("usuario")),
                    (String) solicitud.get("mensaje")
            });
        }
    }

    public void ingresarUsuario(JSONObject usuario, int tipo) throws SQLException {
        String insertUsuario = "INSERT  INTO usuario (codigo, nombre, direccion, username, password, email, cui, fecha_dob,tipo)" +
                " VALUES (?,?,?,?,MD5(?),?,?,?,?)";
        String fecha;
        String rol;
        if (tipo==1){
            fecha = (String) usuario.get("fechaFundacion");
            rol = TipoUsuario.EMPLEADOR.name();
        } else if (tipo==2){
            fecha = (String) usuario.get("fechaNacimiento");
            rol = TipoUsuario.ADMIN.name();
        } else {
            fecha = (String) usuario.get("fechaNacimiento");
            rol = TipoUsuario.SOLICITANTE.name();
        }
        conector.updateWithException(insertUsuario, new String[]{
                String.valueOf((Long)usuario.get("codigo")),
                (String) usuario.get("nombre"),
                (String) usuario.get("direccion"),
                (String) usuario.get("username"),
                (String) usuario.get("password"),
                (String) usuario.get("email"),
                (String) usuario.get("CUI"),
                fecha,
                rol});
    }

    public void insertTelefonos(JSONArray telefonos, String usuario) throws SQLException {
        String insertTelefonos = "INSERT  INTO telefono (numero, id_usuario) VALUES (?,?)";
        int size = ((JSONArray) telefonos.get(0)).size();
        for (int i = 0; i < size; i++){
            String telefono = ((String) (((JSONArray) telefonos.get(0)).get(i)));
            conector.updateWithException(insertTelefonos, new String[]{
                    telefono,
                    usuario
            });
        }
    }

    public void insertPreferencias(JSONArray categorias, String usuario) throws SQLException {
        String insertPref = "INSERT  INTO preferencia (id_categoria, id_usuario) VALUES (?,?)";
        int size =  ((JSONArray) categorias.get(0)).size();
        for (int i = 0; i < size; i++){
            String categoria = String.valueOf((Long) (((JSONArray) categorias.get(0)).get(i)));
            conector.updateWithException(insertPref, new String[]{
                    categoria,
                    usuario
            });
        }
    }

    public void insertTarjeta(JSONObject tarjeta, String usuario) throws SQLException {
        String insertTarjeta = "INSERT  INTO tarjeta (titular, numero, cvv, id_empleador) VALUES (?,?,?,?)";
        conector.updateWithException(insertTarjeta, new String[]{
                (String) tarjeta.get("Titular"),
                String.valueOf((Long)tarjeta.get("numero")),
                String.valueOf((Long)tarjeta.get("codigoSeguridad")),
                usuario
        });
    }

    private JSONArray convertirArray(Object jsonObject){
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(jsonObject);
        return jsonArray;
    }

    public String clasificarTipo(Object dato){
        String valid = null;
        try {
            return String.valueOf((Double) dato);
        } catch (ClassCastException e){
            return String.valueOf((Long) dato);
        }
    }

    public String clasificarNulo(Object dato){
        if (((Long) dato) == null){
            return null;
        } else {
            return String.valueOf((Long) dato);
        }
    }
}
