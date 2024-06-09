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
import java.util.Date;
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

    /***************** LISTA TODOS LOS PEDIDOS ******************/
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
                    e.printStackTrace();
                }
            }
        }
    }

    /***************** LISTA TODOS LOS PEDIDOS POR ID EMPLEADO ******************/
    public List<PedidoConNombre> listarPedidosPorIdEmpleado(int idEmpleado) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            String query = "SELECT p.id, c.nombre AS nombre_cliente, p.fecha_pedido, e.nombre AS nombre_empleado, p.estado, p.ultima_actualizacion " +
                           "FROM pedido p " +
                           "JOIN cliente c ON p.id_cliente = c.id " +
                           "LEFT JOIN empleado e ON p.id_empleado = e.id " +
                           "WHERE e.id = ? OR p.id_empleado IS NULL";

            var ps = conect.prepareStatement(query);
            ps.setInt(1, idEmpleado);

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
                    e.printStackTrace();
                }
            }
        }
    }

    /************* OBTENER ID DEL PEDIDO POR NOMBRE Y FECHA ************/
    public int getPedidoIdByNombreYFecha(String nombreCliente, java.sql.Date fechaPedido) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            String query = "SELECT p.id " +
                           "FROM pedido p " +
                           "JOIN cliente c ON p.id_cliente = c.id " +
                           "WHERE c.nombre = ? AND p.fecha_pedido = ?";

            var ps = conect.prepareStatement(query);
            ps.setString(1, nombreCliente);
            ps.setDate(2, fechaPedido);

            var rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("id");
            } else {
                throw new RuntimeException("Pedido no encontrado para el cliente y fecha dados.");
            }
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

    /************* OBTENER NOMBRE DEL CLIENTE POR ID DEL PEDIDO ************/
    public String getNombreClientePorIdPedido(int idPedido) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            String query = "SELECT c.nombre " +
                           "FROM pedido p " +
                           "JOIN cliente c ON p.id_cliente = c.id " +
                           "WHERE p.id = ?";
            var ps = conect.prepareStatement(query);
            ps.setInt(1, idPedido);

            var rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getString("nombre");
            } else {
                return null; // No se encontró el pedido con ese id
            }
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

    /************* LISTAR ESTADOS DE PEDIDOS ************/
    public List<Integer> listarEstadosPedido() {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            String query = "SELECT DISTINCT estado FROM pedido";
            var ps = conect.prepareStatement(query);
            var rs = ps.executeQuery();

            List<Integer> estados = new ArrayList<>();
            while (rs.next()) {
                estados.add(rs.getInt("estado"));
            }

            return estados;
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

    /************* ACTUALIZAR ESTADO DEL PEDIDO ************/
    public void actualizarEstadoPedido(int idPedido, int nuevoEstadoPedido) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            String query = "UPDATE pedido SET estado = ? WHERE id = ?";
            var ps = conect.prepareStatement(query);
            ps.setInt(1, nuevoEstadoPedido);
            ps.setInt(2, idPedido);

            int filasActualizadas = ps.executeUpdate();
            if (filasActualizadas == 0) {
                throw new SQLException("No se pudo actualizar el pedido con ID: " + idPedido);
            }
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

    /************* ACTUALIZAR ESTADO Y EMPLEADO DEL PEDIDO ************/
    public void actualizarEstadoPedidoyEmple(int idPedido, int nuevoEstadoPedido, int codEmple) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            String query = "UPDATE pedido SET estado = ?, id_empleado = ? WHERE id = ?";
            var ps = conect.prepareStatement(query);
            ps.setInt(1, nuevoEstadoPedido);
            ps.setInt(2, codEmple);
            ps.setInt(3, idPedido);

            int filasActualizadas = ps.executeUpdate();
            if (filasActualizadas == 0) {
                throw new SQLException("No se pudo actualizar el pedido con ID: " + idPedido);
            }
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

    /************* OBTENER ESTADO DEL PEDIDO ************/
    public int obtenerEstadoPedido(int idPedido) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            String query = "SELECT estado FROM pedido WHERE id = ?";
            var ps = conect.prepareStatement(query);
            ps.setInt(1, idPedido);
            var rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("estado");
            } else {
                throw new RuntimeException("No se encontró el estado del pedido con ID: " + idPedido);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al obtener el estado del pedido", e);
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

    /************* LISTAR PEDIDOS POR CLIENTE ************/
    public List<PedidoConNombre> listByCliente(String correoCliente) {
        Connection conect = null;
        try {
            conect = con.abrirConexion();

            String query = "SELECT p.id, c.nombre AS nombre_cliente, p.fecha_pedido, e.nombre AS nombre_empleado, p.estado, p.ultima_actualizacion " +
                           "FROM pedido p " +
                           "JOIN cliente c ON p.id_cliente = c.id " +
                           "JOIN empleado e ON p.id_empleado = e.id " +
                           "WHERE c.email = ?";

            var ps = conect.prepareStatement(query);
            ps.setString(1, correoCliente);

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
                    e.printStackTrace();
                }
            }
        }
    }

    /************ NOMBRE EMPLEADO **********/
    public String obtenerNombreEmpleado(int idEmpleado) {
        Connection conect = null;
        String nombreEmpleado = null;
        String sql = "SELECT nombre FROM empleado WHERE id = ?";

        try {
            conect = con.abrirConexion();
            var pst = conect.prepareStatement(sql);
            pst.setInt(1, idEmpleado);

            var rs = pst.executeQuery();
            if (rs.next()) {
                nombreEmpleado = rs.getString("nombre");
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

        return nombreEmpleado;
    }
}