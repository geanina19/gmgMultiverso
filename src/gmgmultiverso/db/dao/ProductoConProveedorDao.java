/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.db.dao;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.model.ProductoConProveedor;
import java.sql.Connection;
import java.util.List;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;

/**
 *
 * @author geanina.foanta
 */
public class ProductoConProveedorDao {
    
    private ManagerConexion con;
    
    public ProductoConProveedorDao(ManagerConexion con) {
        this.con = con;
    }
    
    public List<ProductoConProveedor> list() {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            String query = "SELECT p.id AS id_producto, p.id_proveedor, pr.nombre_empresa AS nombre_proveedor, p.nombre AS nombre_producto, p.precio, p.unidad_existente "
                    + "FROM producto p "
                    + "JOIN proveedor pr ON p.id_proveedor = pr.id";

            var ps = conect.prepareStatement(query);

            var rs = ps.executeQuery();
            List<ProductoConProveedor> productos = new ArrayList<>();
            while (rs.next()) {
                ProductoConProveedor producto = new ProductoConProveedor(
                        rs.getInt("id_producto"),
                        rs.getInt("id_proveedor"),
                        rs.getString("nombre_proveedor"),
                        rs.getString("nombre_producto"),
                        rs.getDouble("precio"),
                        rs.getInt("unidad_existente")
                );
                productos.add(producto);
            }

            return productos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conect != null) {
                try {
                    conect.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    // Método para añadir un nuevo producto
    public boolean añadirProducto(ProductoConProveedor producto) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();
            String query = "INSERT INTO producto (nombre, precio, unidad_existente, id_proveedor) VALUES (?, ?, ?, ?)";
            var ps = conect.prepareStatement(query);
            ps.setString(1, producto.getNombreProducto());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getUnidad_existente());
            ps.setInt(4, producto.getIdProveedor());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conect != null) {
                try {
                    conect.close();
                } catch (SQLException e) {
                }
            }
        }
    }
    
    // Método para eliminar un producto por su ID
    public boolean eliminarProducto(int idProducto) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();
            
            String sqlEliminar = "DELETE FROM producto WHERE id = ?";
            PreparedStatement statement = conect.prepareStatement(sqlEliminar);
            statement.setInt(1, idProducto);
            
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (conect != null) {
                try {
                    conect.close();
                } 
                catch (SQLException e) {
                    
                }
            }
        }
    }
    
    
    // Método para actualizar un producto
    public boolean actualizarProducto(ProductoConProveedor producto) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();
            String query = "UPDATE producto SET nombre = ?, precio = ?, unidad_existente = ?, id_proveedor = ? WHERE id = ?";
            var ps = conect.prepareStatement(query);
            ps.setString(1, producto.getNombreProducto());
            ps.setDouble(2, producto.getPrecio());
            ps.setInt(3, producto.getUnidad_existente());
            ps.setInt(4, producto.getIdProveedor()); // Ajuste para almacenar el id_proveedor
            ps.setInt(5, producto.getId());
            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conect != null) {
                try {
                    conect.close();
                } catch (SQLException e) {
                }
            }
        }
    }

    
}
