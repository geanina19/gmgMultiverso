/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.db.dao;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.model.Pedido;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author monic
 */
public class PedidoDao {
    private ManagerConexion con;

    public PedidoDao(ManagerConexion con) {
        this.con = con;
    }

    public List<Pedido> list() {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            var ps = conect.prepareStatement("SELECT * FROM pedido");

            var rs = ps.executeQuery();
            List<Pedido> pedidos = new ArrayList<>();
            while (rs.next()) {
                Pedido pedido = new Pedido(
                        rs.getInt("id"),
                        rs.getDate("fecha_pedido"),
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

    public boolean save(Pedido pedido) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            var ps = conect.prepareStatement("INSERT INTO pedido(id, fecha_pedido, estado, ultima_actualizacion) VALUES(?,?,?,?)");

            ps.setInt(1, pedido.getId());
            ps.setDate(2, new java.sql.Date(pedido.getFecha_pedido().getTime()));
            ps.setInt(3, pedido.getEstado());
            ps.setDate(4, new java.sql.Date(pedido.getUltima_actualizacion().getTime()));
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
