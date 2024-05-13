/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.db.dao;
import gmgmultiverso.db.ManagerConexion;
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
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = con.abrirConexion();
            String query = "SELECT * FROM cliente WHERE email = ?";
            ps = conn.prepareStatement(query);
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
                if (conn != null) con.cerrarConexion(conn);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return correoEncontrado;
    }


//guardar cliente 
public boolean guardarCliente(String nombre, String apellido, String contrasenia, String direccion, int telefono, String email) {
    boolean exito = false;
    Connection conn = null;
    PreparedStatement ps = null;

    try {
        conn = con.abrirConexion();
        String query = "INSERT INTO cliente (nombre, apellido, contrasenia, direccion, telefono, email) VALUES (?, ?, ?, ?, ?, ?)";
        ps = conn.prepareStatement(query);
        ps.setString(1, nombre);
        ps.setString(2, apellido);
        ps.setString(3, contrasenia);
        ps.setString(4, direccion);
        ps.setInt(5, telefono);
        ps.setString(6, email);

        int resultado = ps.executeUpdate();
        if (resultado > 0) {
            exito = true;
        }
    } catch (SQLException e) {
        e.printStackTrace();
    } finally {
        try {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                con.cerrarConexion(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    return exito;
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

}