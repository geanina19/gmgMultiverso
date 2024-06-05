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
import java.sql.ResultSet;

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
                    e.printStackTrace();
                    
                }
            }
        }
    }
    
    public boolean existeProductoConNombreYProveedor(String nombreProducto, int codigoProveedor) {
        Connection conect = null;

        try {
            conect = con.abrirConexion();
            String sql = "SELECT COUNT(*) FROM producto WHERE nombre = ? AND id_proveedor = ?";
            var ps = conect.prepareStatement(sql);
            ps.setString(1, nombreProducto);
            ps.setInt(2, codigoProveedor);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0; 
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conect != null) {
                try {
                    conect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
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
                    e.printStackTrace();
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
                    e.printStackTrace();
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
                    e.printStackTrace();
                }
            }
        }
    }
    
    
    public List<ProductoConProveedor> listBuscarPorProveedor(int codProveedor) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            String query = "SELECT p.id AS id_producto, p.id_proveedor, pr.nombre_empresa AS nombre_proveedor, p.nombre AS nombre_producto, p.precio, p.unidad_existente " +
                           "FROM producto p " +
                           "JOIN proveedor pr ON p.id_proveedor = pr.id " +
                           "WHERE p.id_proveedor = ?";

            PreparedStatement ps = conect.prepareStatement(query);
            ps.setInt(1, codProveedor);

            ResultSet rs = ps.executeQuery();
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

            rs.close();
            ps.close();
            return productos;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            if (conect != null) {
                try {
                    conect.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    
    public List<ProductoConProveedor> buscarProductoPorNombre(String nombre) {
        List<ProductoConProveedor> productos = new ArrayList<>();
        String consultaSQL = "SELECT p.id AS id_producto, p.id_proveedor, pr.nombre_empresa AS nombre_proveedor, p.nombre AS nombre_producto, p.precio, p.unidad_existente " +
                            "FROM producto p " +
                            "JOIN proveedor pr ON p.id_proveedor = pr.id " +
                            "WHERE p.nombre LIKE ?";

        try (Connection conet = con.abrirConexion();
             PreparedStatement pstmt = conet.prepareStatement(consultaSQL)) {

            pstmt.setString(1, "%" + nombre + "%");
            ResultSet rs = pstmt.executeQuery();

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    
    public List<ProductoConProveedor> buscarProductoPorNombreYProveedor(String nombreProducto, int idProveedor) {
        List<ProductoConProveedor> productos = new ArrayList<>();
        String consultaSQL = "SELECT p.id AS id_producto, p.id_proveedor, pr.nombre_empresa AS nombre_proveedor, p.nombre AS nombre_producto, p.precio, p.unidad_existente "
                + "FROM producto p "
                + "JOIN proveedor pr ON p.id_proveedor = pr.id "
                + "WHERE p.nombre LIKE ? AND p.id_proveedor = ?";

        try (Connection conet = con.abrirConexion();
             PreparedStatement pstmt = conet.prepareStatement(consultaSQL)) {

            pstmt.setString(1, "%" + nombreProducto + "%");
            pstmt.setInt(2, idProveedor);
            ResultSet rs = pstmt.executeQuery();

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

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public List<ProductoConProveedor> buscarProductoPorRangoPrecio(double precioMinimo, double precioMaximo) {
        List<ProductoConProveedor> productos = new ArrayList<>();
        String consultaSQL = "SELECT p.*, pr.nombre_empresa AS nombre_proveedor " +
                         "FROM producto p " +
                         "JOIN proveedor pr ON p.id_proveedor = pr.id " +
                         "WHERE p.precio BETWEEN ? AND ?";

        try (Connection conet = con.abrirConexion();
             PreparedStatement pstmt = conet.prepareStatement(consultaSQL)) {

            pstmt.setDouble(1, precioMinimo);
            pstmt.setDouble(2, precioMaximo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductoConProveedor producto = new ProductoConProveedor();
                producto.setId(rs.getInt("id"));
                producto.setNombreProveedor(rs.getString("nombre_proveedor"));
                producto.setNombreProducto(rs.getString("nombre"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setUnidad_existente(rs.getInt("unidad_existente"));
                // Aquí puedes establecer otros atributos si es necesario
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }


    public List<ProductoConProveedor> buscarProductoPorNombreYPrecio(String nombreProducto, double precioMinimo, double precioMaximo) {
        List<ProductoConProveedor> productos = new ArrayList<>();

        String query = "SELECT p.id AS id_producto, p.id_proveedor, pr.nombre_empresa AS nombre_proveedor, p.nombre AS nombre_producto, p.precio, p.unidad_existente "
                + "FROM producto p "
                + "JOIN proveedor pr ON p.id_proveedor = pr.id "
                + "WHERE p.nombre LIKE ? AND p.precio BETWEEN ? AND ?";


        try (Connection conect = con.abrirConexion();
             PreparedStatement ps = conect.prepareStatement(query)) {

            ps.setString(1, "%" + nombreProducto + "%");
            ps.setDouble(2, precioMinimo);
            ps.setDouble(3, precioMaximo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Obtener los datos de cada producto
                    int id = rs.getInt("id");
                    String nombreProveedor = rs.getString("nombre_proveedor");
                    String nombreProductoDB = rs.getString("nombre_producto");
                    double precio = rs.getDouble("precio");
                    int unidadExistente = rs.getInt("unidad_existente");

                    // Crear un objeto ProductoConProveedor y agregarlo a la lista
                    ProductoConProveedor producto = new ProductoConProveedor(id, nombreProveedor, nombreProductoDB, precio, unidadExistente);
                    productos.add(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }


    public List<ProductoConProveedor> buscarProductoPorNombreProveedorYPrecio(String nombreProducto, int codProveedor, double precioMinimo, double precioMaximo) {
        List<ProductoConProveedor> productos = new ArrayList<>();
        
        String consultaSQL = "SELECT p.id AS id_producto, p.id_proveedor, pr.nombre_empresa AS nombre_proveedor, p.nombre AS nombre_producto, p.precio, p.unidad_existente "
                + "FROM producto p "
                + "JOIN proveedor pr ON p.id_proveedor = pr.id "
                + "WHERE p.nombre LIKE ? AND p.id_proveedor = ? AND p.precio BETWEEN ? AND ?";


        try (Connection conexion = con.abrirConexion();
             PreparedStatement pstmt = conexion.prepareStatement(consultaSQL)) {

            pstmt.setString(1, "%" + nombreProducto + "%");
            pstmt.setInt(2, codProveedor);
            pstmt.setDouble(3, precioMinimo);
            pstmt.setDouble(4, precioMaximo);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ProductoConProveedor producto = new ProductoConProveedor();
                producto.setId(rs.getInt("id"));
                producto.setNombreProveedor(rs.getString("nombre_proveedor"));
                producto.setNombreProducto(rs.getString("nombre_producto"));
                producto.setPrecio(rs.getDouble("precio"));
                producto.setUnidad_existente(rs.getInt("unidad_existente"));
                productos.add(producto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    public List<ProductoConProveedor> buscarProductoPorProveedorYPrecio(int codProveedor, double precioMinimo, double precioMaximo) {
        List<ProductoConProveedor> productos = new ArrayList<>();

        String query = "SELECT p.id AS id_producto, p.id_proveedor, pr.nombre_empresa AS nombre_proveedor, p.nombre AS nombre_producto, p.precio, p.unidad_existente "
                + "FROM producto p "
                + "JOIN proveedor pr ON p.id_proveedor = pr.id "
                + "WHERE p.id_proveedor = ? AND p.precio BETWEEN ? AND ?";


        try (Connection conect = con.abrirConexion();
             PreparedStatement ps = conect.prepareStatement(query)) {

            ps.setInt(1, codProveedor);
            ps.setDouble(2, precioMinimo);
            ps.setDouble(3, precioMaximo);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // Obtener los datos de cada producto
                    int id = rs.getInt("id");
                    String nombreProveedor = rs.getString("nombre_proveedor");
                    String nombreProducto = rs.getString("nombre_producto");
                    double precio = rs.getDouble("precio");
                    int unidadExistente = rs.getInt("unidad_existente");

                    // Crear un objeto ProductoConProveedor y agregarlo a la lista
                    ProductoConProveedor producto = new ProductoConProveedor(id, nombreProveedor, nombreProducto, precio, unidadExistente);
                    productos.add(producto);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productos;
    }

    
}
