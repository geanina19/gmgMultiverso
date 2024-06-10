 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.db.dao;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.model.Proveedor;
import java.util.List;
//import java.awt.print.Proveedor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;


/**
 *
 * @author geanina.foanta
 */
public class ProveedorDao {
    
    private ManagerConexion con;
    
    
    public ProveedorDao(ManagerConexion con) {
        this.con = con;
    }
    
    
    public List<Proveedor> list() {
        Connection conect = null;
    
        try {
            conect = con.abrirConexion();

            var ps = conect.prepareStatement("SELECT * FROM proveedor");

            var resultSet = ps.executeQuery();
            List<Proveedor> proveedores = new ArrayList<>();
            while (resultSet.next()) {
                Proveedor proveedor = new Proveedor(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre_empresa"),
                        resultSet.getInt("telefono"),
                        resultSet.getString("email")
                );
                proveedores.add(proveedor);
            }

            return proveedores;
            
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        } 
        finally {
            if (conect != null) {
                try {
                    conect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public boolean tieneProductosAsociados(int codigoProveedor) {
        String sql = "SELECT COUNT(*) FROM producto WHERE id_proveedor = ?";
        try (Connection conect = con.abrirConexion();
             PreparedStatement pst = conect.prepareStatement(sql)) {

            pst.setInt(1, codigoProveedor);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    public boolean correoExiste(String email) {
        String query = "SELECT COUNT(*) FROM proveedor WHERE email = ?";
        try (Connection conect = con.abrirConexion();
             PreparedStatement stmt = conect.prepareStatement(query)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true si hay al menos un proveedor con ese correo electrónico
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
        return false;
    }
    
    public boolean correoExiste(String email, int idProveedorExcluido) {
        String query = "SELECT COUNT(*) FROM proveedor WHERE email = ? AND id != ?";
        try (Connection conn = con.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            stmt.setInt(2, idProveedorExcluido);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true si hay al menos un proveedor con ese correo electrónico excluyendo el actual
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
        return false;
    }

    // Método para verificar si un proveedor ya existe por número de teléfono excluyendo el proveedor actual
    public boolean proveedorExiste(int telefono, int idProveedorExcluido) {
        String query = "SELECT COUNT(*) FROM proveedor WHERE telefono = ? AND id != ?";
        try (Connection conn = con.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, telefono);
            stmt.setInt(2, idProveedorExcluido);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true si hay al menos un proveedor con ese teléfono excluyendo el actual
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
        return false;
    }


    
    // Método para verificar si un proveedor ya existe por número de teléfono
    public boolean proveedorExiste(int telefono) {
        String query = "SELECT COUNT(*) FROM PROVEEDOR WHERE telefono = ?";
        try (Connection conn = con.abrirConexion();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, telefono);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; // Retorna true si hay al menos un proveedor con ese teléfono
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Manejo de errores
        }
        return false;
    }
    
    
    public boolean anadirProveedor(Proveedor proveedor) {
        Connection conect = null;
    
        try {
            conect = con.abrirConexion();

            var sqlDatos = conect.prepareStatement("INSERT INTO proveedor(nombre_empresa, telefono, email) VALUES(?,?,?)");

            sqlDatos.setString(1, proveedor.getNombre_empresa());
            sqlDatos.setInt(2, proveedor.getTelefono());
            sqlDatos.setString(3, proveedor.getEmail());
            int insertedRows = sqlDatos.executeUpdate();
            return insertedRows == 1;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 
        finally {
            if (conect != null) {
                try {
                    conect.close();
                } 
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    //-------------eliminar un proveedor-------------
    
    
    public boolean eliminarProveedor(int idProveedor) {
        Connection conect = null;
    
        String sqlEliminar = "DELETE FROM proveedor WHERE id = ?";

        try{
            conect = con.abrirConexion();

            PreparedStatement statement = conect.prepareStatement(sqlEliminar);
            statement.setInt(1, idProveedor);

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 
        finally {
            if (conect != null) {
                try {
                    conect.close();
                } 
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    //-------------Actualizar proveedor-------------
    
    public boolean actualizarProveedor(Proveedor proveedor) {
        Connection conect = null;
    
        String sqlActualizar = "UPDATE proveedor SET nombre_empresa = ?, telefono = ?, email = ? WHERE id = ?";

        try {
            conect = con.abrirConexion();
            PreparedStatement statement = conect.prepareStatement(sqlActualizar);
            statement.setString(1, proveedor.getNombre_empresa());
            statement.setInt(2, proveedor.getTelefono());
            statement.setString(3, proveedor.getEmail());
            statement.setInt(4, proveedor.getId());

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } 
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        } 
        finally {
            if (conect != null) {
                try {
                    conect.close();
                } 
                catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public List<Proveedor> buscarProveedoresPorTelefono(int telefono) {
        List<Proveedor> proveedores = new ArrayList<>();
        String consultaSQL = "SELECT nombre_empresa, telefono, email FROM proveedor WHERE telefono LIKE ?";

        try (Connection conet = con.abrirConexion();
             PreparedStatement pstmt = conet.prepareStatement(consultaSQL)) {
            
            pstmt.setString(1, "%" + telefono + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Proveedor proveedor = new Proveedor();
                proveedor.setNombre_empresa(rs.getString("nombre_empresa"));
                proveedor.setTelefono(rs.getInt("telefono"));
                proveedor.setEmail(rs.getString("email"));
                proveedores.add(proveedor);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return proveedores;
    }
    
}
