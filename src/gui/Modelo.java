package gui;

import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import java.util.Properties;

public class Modelo {
    private String ip;
    private String user;
    private String password;
    private String adminPassword;

    public Modelo() {
        getPropValues();
    }

    public String getIp() {
        return ip;
    }
    public String getUser() {
        return user;
    }
    public String getPassword() {
        return password;
    }
    public String getAdminPassword() {
        return adminPassword;
    }

    private Connection conexion;

    void conectar() {

        try {
            conexion = DriverManager.getConnection(
                    "jdbc:mysql://"+ip+":3306/mibasegonzalo",user, password);
        } catch (SQLException sqle) {
            try {
                conexion = DriverManager.getConnection(
                        "jdbc:mysql://"+ip+":3306/",user, password);

                PreparedStatement statement = null;

                String code = leerFichero();
                String[] query = code.split("--");
                for (String aQuery : query) {
                    statement = conexion.prepareStatement(aQuery);
                    statement.executeUpdate();
                }
                assert statement != null;
                statement.close();

            } catch (SQLException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String leerFichero() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader("basedatos_java.sql")) ;
            String linea;
            StringBuilder stringBuilder = new StringBuilder();
            while ((linea = reader.readLine()) != null) {
                stringBuilder.append(linea);
                stringBuilder.append(" ");
            }

            return stringBuilder.toString();
    }

    public void desconectar() {
        if (conexion != null) {
            try {
                conexion.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            conexion = null; // para no volver a cerrar
        }
    }

    void insertarMarca(String nombreMarca, String nombreLegalEmpresa, LocalDate fechaFundacion, String paisFundacion) {
        String sentenciaSql = "INSERT INTO marcas (nombremarca, nombrelegalempresa, fechafundacion, paisfundacion) VALUES (?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombreMarca);
            sentencia.setString(2, nombreLegalEmpresa);
            sentencia.setDate(3, Date.valueOf(fechaFundacion));
            sentencia.setString(4, paisFundacion);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    void insertarTienda(String nombreTienda, String email, String telefono, String tipoTienda, String web) {
        String sentenciaSql = "INSERT INTO tiendas (nombretienda, email, telefono, tipotienda, web) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombreTienda);
            sentencia.setString(2, email);
            sentencia.setString(3, telefono);
            sentencia.setString(4, tipoTienda);
            sentencia.setString(5, web);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    void insertarCalzado(String modelo, String codigoSKU, String idTienda, String tipoCalzado, String marca,
                       float precio, LocalDate fechaDeLanzamiento) {
        String sentenciaSql = "INSERT INTO calzados (modelo, codigosku, idtienda, tipocalzado, idmarca, precio, fechadelanzamiento) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement sentencia = null;

        int idtienda = Integer.valueOf(idTienda.split(" ")[0]);
        int idmarca = Integer.valueOf(marca.split(" ")[0]);

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, modelo);
            sentencia.setString(2, codigoSKU);
            sentencia.setInt(3, idtienda);
            sentencia.setString(4, tipoCalzado);
            sentencia.setInt(5, idmarca);
            sentencia.setFloat(6, precio);
            sentencia.setDate(7, Date.valueOf(fechaDeLanzamiento));
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    void insertarPedido(String codigoSeguimiento,String idCalzado, String idTienda, String idMarca, int cantidad,
                        String nombreDestinatario, String tipoEnvio, String direccion) {
        String sentenciaSql = "INSERT INTO pedidos (codigoseguimiento, idcalzado, idtienda, idmarca, cantidad, " +
                "nombredestinatario, tipoenvio, direccion) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?,?)";
        PreparedStatement sentencia = null;

        int idcalzado = Integer.valueOf(idCalzado.split(" ")[0]);
        int idtienda = Integer.valueOf(idTienda.split(" ")[0]);
        int idmarca = Integer.valueOf(idMarca.split(" ")[0]);

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, codigoSeguimiento);
            sentencia.setInt(2, idcalzado);
            sentencia.setInt(3, idtienda);
            sentencia.setInt(4, idmarca);
            sentencia.setInt(5, cantidad);
            sentencia.setString(6, nombreDestinatario);
            sentencia.setString(7, tipoEnvio);
            sentencia.setString(8, direccion);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    void modificarTienda(String nombreTienda, String email, String telefono, String tipoTienda, String web, int idTienda){

        String sentenciaSql = "UPDATE tiendas SET nombretienda = ?, email = ?, telefono = ?, tipotienda = ?, web = ?" +
                "WHERE idtienda = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombreTienda);
            sentencia.setString(2, email);
            sentencia.setString(3, telefono);
            sentencia.setString(4, tipoTienda);
            sentencia.setString(5, web);
            sentencia.setInt(6, idTienda);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    void modificarMarca(String nombreMarca, String nombreLegalEmpresa, LocalDate fechaFundacion, String paisFundacion, int idMarca){

        String sentenciaSql = "UPDATE marcas SET nombremarca = ?, nombrelegalempresa = ?, fechafundacion = ?, paisfundacion = ?" +
                "WHERE idmarca = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, nombreMarca);
            sentencia.setString(2, nombreLegalEmpresa);
            sentencia.setDate(3, Date.valueOf(fechaFundacion));
            sentencia.setString(4, paisFundacion);
            sentencia.setInt(5, idMarca);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void modificarCalzado(String modelo, String codigoSKU, String idTienda, String tipoCalzado, String marca,
                        float precio, LocalDate fechaDeLanzamiento, int idCalzado) {

        String sentenciaSql = "UPDATE calzados SET modelo = ?, codigosku = ?, idtienda = ?, tipocalzado = ?, " +
                "idmarca = ?, precio = ?, fechadelanzamiento = ? WHERE idcalzado = ?";
        PreparedStatement sentencia = null;

        int idtienda = Integer.valueOf(idTienda.split(" ")[0]);
        int idmarca = Integer.valueOf(marca.split(" ")[0]);

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, modelo);
            sentencia.setString(2, codigoSKU);
            sentencia.setInt(3, idtienda);
            sentencia.setString(4, tipoCalzado);
            sentencia.setInt(5, idmarca);
            sentencia.setFloat(6, precio);
            sentencia.setDate(7, Date.valueOf(fechaDeLanzamiento));
            sentencia.setInt(8, idCalzado);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    void modificarPedido(String codigoSeguimiento,String idCalzado, String idTienda, String idMarca, int cantidad,
                         String nombreDestinatario, String tipoEnvio, String direccion, int idPedido) {

        String sentenciaSql = "UPDATE pedidos SET codigoseguimiento = ?, idcalzado = ?, idtienda = ?, idmarca = ?, " +
                "cantidad = ?, nombredestinatario = ?, tipoenvio = ?, direccion = ? WHERE idpedido = ?";
        PreparedStatement sentencia = null;

        int idcalzado = Integer.valueOf(idCalzado.split(" ")[0]);
        int idtienda = Integer.valueOf(idTienda.split(" ")[0]);
        int idmarca = Integer.valueOf(idMarca.split(" ")[0]);

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setString(1, codigoSeguimiento);
            sentencia.setInt(2, idcalzado);
            sentencia.setInt(3, idtienda);
            sentencia.setInt(4, idmarca);
            sentencia.setInt(5, cantidad);
            sentencia.setString(6, nombreDestinatario);
            sentencia.setString(7, tipoEnvio);
            sentencia.setString(8, direccion);
            sentencia.setInt(9, idPedido);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    void eliminarTienda(int idTienda) {
        String sentenciaSql = "DELETE FROM tiendas WHERE idtienda = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idTienda);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void eliminarMarca(int idmarca) {
        String sentenciaSql = "DELETE FROM marcas WHERE idmarca = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idmarca);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }

    void eliminarCalzado(int idcalzado) {
        String sentenciaSql = "DELETE FROM calzados WHERE idcalzado = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idcalzado);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    void eliminarPedido(int idpedido) {
        String sentenciaSql = "DELETE FROM pedidos WHERE idpedido = ?";
        PreparedStatement sentencia = null;

        try {
            sentencia = conexion.prepareStatement(sentenciaSql);
            sentencia.setInt(1, idpedido);
            sentencia.executeUpdate();
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        } finally {
            if (sentencia != null)
                try {
                    sentencia.close();
                } catch (SQLException sqle) {
                    sqle.printStackTrace();
                }
        }
    }
    ResultSet consultarTienda() throws SQLException {
        String sentenciaSql = "SELECT idtienda as 'ID', " +
                "nombretienda as 'Nombre tienda', " +
                "email as 'Email', " +
                "telefono as 'Teléfono', " +
                "tipotienda as 'Tipo', " +
                "web as 'Web' " +
                "FROM tiendas";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = conexion.prepareStatement(sentenciaSql);
        resultado = sentencia.executeQuery();
        return resultado;
    }
    ResultSet consultarMarca() throws SQLException {
        String sentenciaSql = "SELECT idmarca as 'ID', " +
                "nombremarca as 'Nombre marca', " +
                "nombrelegalempresa as 'Nombre legal empresa', " +
                "fechafundacion as 'Fecha de fundación'," +
                "paisfundacion as 'País de fundación' " +
                "FROM marcas";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = conexion.prepareStatement(sentenciaSql);
        resultado = sentencia.executeQuery();
        return resultado;
    }
    ResultSet consultarCalzados() throws SQLException {
        String sentenciaSql = "SELECT c.idcalzado as 'ID', " +
                "c.modelo as 'Modelo', " +
                "c.codigosku as 'Código SKU', " +
                "concat(t.idtienda, ' - ', t.nombretienda) as 'Tienda', " +
                "c.tipocalzado as 'Tipo calzado', " +
                "concat(m.idmarca, ' - ', m.nombrelegalempresa, ', ', m.nombremarca) as 'Marca', " +
                "c.precio as 'Precio', " +
                "c.fechadelanzamiento as 'Fecha de lanzamiento' " +
                "FROM calzados as c " +
                "inner join tiendas as t " +
                "on t.idtienda = c.idtienda " +
                "inner join marcas as m " +
                "on m.idmarca = c.idmarca";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = conexion.prepareStatement(sentenciaSql);
        resultado = sentencia.executeQuery();
        return resultado;
    }
    ResultSet consultarPedidos() throws SQLException {
        String sentenciaSql = "SELECT p.idpedido as 'ID', " +
                "p.codigoseguimiento as 'Código de seguimiento', " +
                "concat(c.idcalzado, ' - ', c.modelo) as 'Calzado', " +
                "concat(t.idtienda, ' - ', t.nombretienda) as 'Tienda', " +
                "concat(m.idmarca, ' - ', m.nombrelegalempresa, ', ', m.nombremarca) as 'Marca', " +
                "p.cantidad as 'Cantidad', " +
                "p.nombredestinatario as 'Nombre del destinatario', " +
                "p.tipoenvio as 'Tipo de envío', " +
                "p.direccion as 'Dirección' " +
                "FROM pedidos as p " +
                "inner join calzados as c " +
                "on c.idcalzado = p.idcalzado " +
                "inner join tiendas as t " +
                "on t.idtienda = p.idtienda " +
                "inner join marcas as m " +
                "on m.idmarca = p.idmarca";
        PreparedStatement sentencia = null;
        ResultSet resultado = null;
        sentencia = conexion.prepareStatement(sentenciaSql);
        resultado = sentencia.executeQuery();
        return resultado;
    }
    private void getPropValues() {
        InputStream inputStream = null;
        try {
            Properties prop = new Properties();
            String propFileName = "config.properties";

            inputStream = new FileInputStream(propFileName);

            prop.load(inputStream);
            ip = prop.getProperty("ip");
            user = prop.getProperty("user");
            password = prop.getProperty("pass");
            adminPassword = prop.getProperty("admin");

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            try {
                if (inputStream != null) inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    void setPropValues(String ip, String user, String pass, String adminPass) {
        try {
            Properties prop = new Properties();
            prop.setProperty("ip", ip);
            prop.setProperty("user", user);
            prop.setProperty("pass", pass);
            prop.setProperty("admin", adminPass);
            OutputStream out = new FileOutputStream("config.properties");
            prop.store(out, null);

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        this.ip = ip;
        this.user = user;
        this.password = pass;
        this.adminPassword = adminPass;
    }

    public boolean calzadoCodigoSKUYaExiste(String codigoSKU) {
        String salesConsult = "SELECT existeCodigoSKU(?)";
        PreparedStatement function;
        boolean codigoSKUExists = false;
        try {
            function = conexion.prepareStatement(salesConsult);
            function.setString(1, codigoSKU);
            ResultSet rs = function.executeQuery();
            rs.next();

            codigoSKUExists = rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codigoSKUExists;
    }

    public boolean tiendaNombreYaExiste(String nombre) {
        String tiendaNameConsult = "SELECT existeNombreTienda(?)";
        PreparedStatement function;
        boolean nameExists = false;
        try {
            function = conexion.prepareStatement(tiendaNameConsult);
            function.setString(1, nombre);
            ResultSet rs = function.executeQuery();
            rs.next();

            nameExists = rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nameExists;
    }

    public boolean marcaNombreYaExiste(String nombreMarca, String nombreLegalEmpresa) {
        String completeName = nombreLegalEmpresa + ", " + nombreMarca;
        String marcaNameConsult = "SELECT existeNombreMarca(?)";
        PreparedStatement function;
        boolean nameExists = false;
        try {
            function = conexion.prepareStatement(marcaNameConsult);
            function.setString(1, completeName);
            ResultSet rs = function.executeQuery();
            rs.next();

            nameExists = rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nameExists;
    }

    public boolean pedidoCodigoSeguimientoYaExiste(String codigoSeguimiento) {
        String salesConsult = "SELECT existeCodigoSeguimiento(?)";
        PreparedStatement function;
        boolean codigoSeguimientoExists = false;
        try {
            function = conexion.prepareStatement(salesConsult);
            function.setString(1, codigoSeguimiento);
            ResultSet rs = function.executeQuery();
            rs.next();

            codigoSeguimientoExists = rs.getBoolean(1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return codigoSeguimientoExists;
    }
}
