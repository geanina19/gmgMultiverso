/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.db.dao;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.model.PedidoConNombre;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author monic
 */
public class PedidoConNombreDao {
    private ManagerConexion con;

    public PedidoConNombreDao(ManagerConexion con) {
        this.con = con;
    }

    public List<PedidoConNombre> list() {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            String query = "SELECT p.id, c.nombre AS nombre_cliente, p.fecha_pedido, e.nombre AS nombre_empleado, p.estado, p.ultima_actualizacion " +
                           "FROM pedido p " +
                           "JOIN cliente c ON p.id_cliente = c.id " +
                           "JOIN empleado e ON p.id_empleado = e.id";

            var ps = conect.prepareStatement(query);

            var rs = ps.executeQuery();
            List<PedidoConNombre> pedidos = new ArrayList<>();
            while (rs.next()) {
                PedidoConNombre pedido = new PedidoConNombre(
                        rs.getInt("id"),
                        rs.getString("nombre_cliente"),
                        rs.getDate("fecha_pedido"),
                        rs.getString("nombre_empleado"),
                        rs.getInt("estado"),
                        rs.getDate("ultima_actualizacion")
                );
                pedidos.add(pedido);
            }

            return pedidos;
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
