<<<<<<< HEAD
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

            String query = "SELECT p.id AS id_producto, pr.nombre_empresa AS nombre_proveedor, p.nombre AS nombre_producto, p.precio, p.unidad_existente "
                    + "FROM producto p "
                    + "JOIN proveedor pr ON p.id_proveedor = pr.id";

            var ps = conect.prepareStatement(query);

            var rs = ps.executeQuery();
            List<ProductoConProveedor> productos = new ArrayList<>();
            while (rs.next()) {
                ProductoConProveedor producto = new ProductoConProveedor(
                        rs.getInt("id_producto"),
                        rs.getString("nombre_empresa"),
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
    
}
=======
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

            String query = "SELECT p.id AS id_producto, pr.nombre_empresa AS nombre_proveedor, p.nombre AS nombre_producto, p.precio, p.unidad_existente "
                    + "FROM producto p "
                    + "JOIN proveedor pr ON p.id_proveedor = pr.id";

            var ps = conect.prepareStatement(query);

            var rs = ps.executeQuery();
            List<ProductoConProveedor> productos = new ArrayList<>();
            while (rs.next()) {
                ProductoConProveedor producto = new ProductoConProveedor(
                        rs.getInt("id_producto"),
                        rs.getString("nombre_empresa"),
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
    
}
>>>>>>> 7a97e1064f71025cf8b98b0456bb877c1ab95761
