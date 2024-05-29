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

            /*
            SELECT producto.id, proveedor.nombre AS nombre_proveedor, producto.nombre, producto.precio, producto.unidad_existente 
FROM producto 
JOIN proveedor ON producto.id_proveedor = proveedor.id;

            */
            
            String query = "SELECT p.id, c.nombre AS nombre_cliente, p.fecha_pedido, e.nombre AS nombre_empleado, p.estado, p.ultima_actualizacion " +
                           "FROM pedido p " +
                           "JOIN cliente c ON p.id_cliente = c.id " +
                           "JOIN empleado e ON p.id_empleado = e.id";

            var ps = conect.prepareStatement(query);

            var rs = ps.executeQuery();
            List<ProductoConProveedor> productos = new ArrayList<>();
            while (rs.next()) {
                ProductoConProveedor producto = new ProductoConProveedor(
                        rs.getInt("id"),
                        rs.getString("nombre_proveedor"),
                        rs.getString("nombre"),
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
