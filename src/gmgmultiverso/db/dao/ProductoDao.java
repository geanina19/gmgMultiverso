/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.db.dao;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.model.Producto;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author monic
 */
public class ProductoDao {
    
    private ManagerConexion con;
    
    public ProductoDao(ManagerConexion con) {
        this.con = con;
    }

    public List<Producto> list() {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            var ps = conect.prepareStatement("SELECT * FROM producto");

            var rs = ps.executeQuery();
            List<Producto> productos = new ArrayList<>();
            while (rs.next()) {
                Producto producto = new Producto(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getDouble("precio"),
                        rs.getInt("unidad_existente"),
                        rs.getInt("idProveedor")
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

    public boolean anadirProducto(Producto producto) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            var ps = conect.prepareStatement("INSERT INTO producto(id, nombre, precio, unidad_existente) VALUES(?,?,?,?)");

            ps.setInt(1, producto.getId());
            ps.setString(2, producto.getNombre());
            ps.setDouble(3, producto.getPrecio());
            ps.setInt(4, producto.getUnidad_existente());
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
    
}
