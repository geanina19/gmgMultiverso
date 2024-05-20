/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.db.dao;
import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.model.Cliente;
import gmgmultiverso.model.Proveedor;
import java.awt.List;
import java.awt.print.Book;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;

/**
 *
 * @author gema
 */
public class ClienteDao {
    private final ManagerConexion con;
    
   public ClienteDao(ManagerConexion con) {
        this.con = con;
    }

    
    public boolean verificarCorreo(String correoElectronico) {
        boolean correoEncontrado = false;
        Connection conect = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conect = con.abrirConexion();
            String query = "SELECT * FROM cliente WHERE email = ?";
            ps = conect.prepareStatement(query);
            ps.setString(1, correoElectronico);
            rs = ps.executeQuery();

            if (rs.next()) {
                correoEncontrado = true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conect != null) con.cerrarConexion(conect);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return correoEncontrado;
    }


//guardar cliente 
public boolean guardarCliente(Cliente cliente) {
    Connection conect = null;

    try {
        conect = con.abrirConexion();
        var ps = conect.prepareStatement("INSERT INTO cliente (nombre, apellido, contrasenia, direccion, telefono, email) VALUES (?, ?, ?, ?, ?, ?)");
        
        ps.setString(1, cliente.getNombre());
        ps.setString(2, cliente.getApellido());
        ps.setString(3, cliente.getContrasenia());
        ps.setString(4, cliente.getDireccion());
        ps.setInt(5, cliente.getTelefono());
        ps.setString(6, cliente.getEmail());
        
        int insertedRows = ps.executeUpdate();
            return insertedRows == 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conect != null) {
                try {
                    conect.close();
                } catch (SQLException e) {
                }
            }
        }
    }

 // Método para cargar los nombres de los productos
    public String[] cargarNombresProductos() {
        String[] nombresProductos = new String[6]; 
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = con.abrirConexion();
            String query = "SELECT nombre FROM producto";
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();

            int i = 0;
            while (rs.next() && i < 6) {
                nombresProductos[i] = rs.getString("nombre");
                i++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) con.cerrarConexion(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return nombresProductos;
    }


public boolean verificarCredenciales(String correoElectronico, String contrasena) {
    boolean credencialesCorrectas = false;
    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    try {
        conn = con.abrirConexion();
        String query = "SELECT * FROM cliente WHERE email = ? AND contrasenia = ?";
        ps = conn.prepareStatement(query);
        ps.setString(1, correoElectronico);
        ps.setString(2, contrasena);
        rs = ps.executeQuery();

        if (rs.next()) {
            credencialesCorrectas = true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (rs != null) rs.close();
            if (ps != null) ps.close();
            if (conn != null) con.cerrarConexion(conn);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
    }

    return credencialesCorrectas;
}

//perfil cliente
public String[] obtenerDatosCliente(String correoElectronico) {
        String[] datosCliente = new String[4]; // Array para almacenar los datos del cliente

        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = con.abrirConexion();
            String query = "SELECT nombre, apellido, direccion, telefono FROM cliente WHERE email = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, correoElectronico);
            rs = ps.executeQuery();

            if (rs.next()) {
                // Obtener los datos del cliente de la consulta
                datosCliente[0] = rs.getString("nombre");
                datosCliente[1] = rs.getString("apellido");
                datosCliente[2] = rs.getString("direccion");
                datosCliente[3] = rs.getString("telefono");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) con.cerrarConexion(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return datosCliente;
    }
}