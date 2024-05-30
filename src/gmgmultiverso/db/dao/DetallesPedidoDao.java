/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.db.dao;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.model.DetallePedido;
import gmgmultiverso.model.Pedido;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.sql.ResultSet;

/**
 *
 * @author gema
 */
public class DetallesPedidoDao {
    private ManagerConexion con;

    // Constructor con argumentos
    public DetallesPedidoDao(ManagerConexion con) {
        this.con = con; 
    }

    // Constructor sin argumentos
    public DetallesPedidoDao() {
        
    }

public boolean insertarPedido(Pedido pedido) {
    Connection conect = null;
    try {
        conect = con.abrirConexion();

        String insertPedidoSQL = "INSERT INTO pedido (id_cliente, fecha_pedido, id_empleado, estado, ultima_actualizacion) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement psPedido = conect.prepareStatement(insertPedidoSQL, Statement.RETURN_GENERATED_KEYS);
        psPedido.setInt(1, pedido.getIdCliente());
        psPedido.setDate(2, new java.sql.Date(pedido.getFecha_pedido().getTime()));
        psPedido.setInt(3, pedido.getIdEmpleado());
        psPedido.setInt(4, pedido.getEstado());
        psPedido.setDate(5, new java.sql.Date(pedido.getUltima_actualizacion().getTime()));

        int insertedRows = psPedido.executeUpdate();

        if (insertedRows == 1) {
            ResultSet generatedKeys = psPedido.getGeneratedKeys();
            if (generatedKeys.next()) {
                int pedidoId = generatedKeys.getInt(1);
                pedido.setId(pedidoId);

                for (DetallePedido detalle : pedido.getDetalles()) {
                    if (detalle.getProducto().getId() == 0) {
                        System.out.println("Error: Producto con ID 0 encontrado.");
                        return false;  // Detener la ejecución si se encuentra un producto con ID 0
                    }

                    System.out.println("Insertando detalle para pedido ID: " + pedidoId + " con producto ID: " + detalle.getProducto().getId());
                    insertarDetallePedido(conect, detalle, pedidoId);
                }
                return true;
            }
        }
        return false;
    } catch (SQLException e) {
        e.printStackTrace();
        return false;
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

private void insertarDetallePedido(Connection conect, DetallePedido detalle, int pedidoId) throws SQLException {
    String insertDetalleSQL = "INSERT INTO detalle_pedido (id_pedido, id_producto, cantidad) VALUES (?, ?, ?)";
    PreparedStatement ps = conect.prepareStatement(insertDetalleSQL);
    ps.setInt(1, pedidoId);
    ps.setInt(2, detalle.getProducto().getId());
    ps.setInt(3, detalle.getCantidad());

    System.out.println("SQL: " + ps);
    System.out.println("Parameters: id_pedido=" + pedidoId + ", id_producto=" + detalle.getProducto().getId() + ", cantidad=" + detalle.getCantidad());

    ps.executeUpdate();
}

}