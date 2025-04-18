/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.db.dao;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.model.Empleado;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;


/**
 *
 * @author geanina.foanta
 */
public class EmpleadoDao {
    
    
    private ManagerConexion con;
    
    public EmpleadoDao(ManagerConexion con) {
        this.con = con;
    }
    
    public List<Empleado> list() {
        Connection conect = null;
    
        try {
            conect = con.abrirConexion();

            var ps = conect.prepareStatement("SELECT * FROM empleado");

            var resultSet = ps.executeQuery();
            List<Empleado> empleados = new ArrayList<>();
            while (resultSet.next()) {
                Empleado empleado = new Empleado(
                        resultSet.getInt("id"),
                        resultSet.getString("nombre"),
                        resultSet.getString("apellido"),
                        resultSet.getString("contrasenia"),
                        resultSet.getInt("telefono"),
                        resultSet.getString("email")
                );
                empleados.add(empleado);
            }

            return empleados;
            
        } 
        catch (SQLException e) {
            throw new RuntimeException(e);
        } 
        finally {
            if (conect != null) {
                try {
                    conect.close();
                } catch (SQLException e) {
                    
                }
            }
        }
    }
    
    public boolean anadirEmpleado(Empleado empleado) {
        Connection conect = null;
    
        try {
            conect = con.abrirConexion();

            var sqlDatos = conect.prepareStatement("INSERT INTO empleado(nombre, apellido, contrasenia, telefono, email) VALUES(?,?,?,?,?)");

            sqlDatos.setString(1, empleado.getNombre());
            sqlDatos.setString(2, empleado.getApellido());
            sqlDatos.setString(3, empleado.getContrasenia());
            sqlDatos.setInt(4, empleado.getTelefono());
            sqlDatos.setString(5, empleado.getEmail());
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
                    
                }
            }
        }
    }
    
    public boolean eliminarEmpleado(int idEmpleado) {
        Connection conect = null;
    
        String sqlEliminar = "DELETE FROM empleado WHERE id = ?";

        try {
            conect = con.abrirConexion();

            PreparedStatement statement = conect.prepareStatement(sqlEliminar);
            statement.setInt(1, idEmpleado);

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
                    
                }
            }
        }
    }
    
    //-------------Actualizar empleado-------------
    
    public boolean actualizarEmpleado(Empleado empleado) {
        Connection conect = null;
    
        String sqlActualizar = "UPDATE empleado SET nombre = ?, apellido = ?, contrasenia = ?, telefono = ?, email = ? WHERE id = ?";

        try {
            conect = con.abrirConexion();
            PreparedStatement statement = conect.prepareStatement(sqlActualizar);
            statement.setString(1, empleado.getNombre());
            statement.setString(2, empleado.getApellido());
            statement.setString(3, empleado.getContrasenia());
            statement.setInt(4, empleado.getTelefono());
            statement.setString(5, empleado.getEmail());
            statement.setInt(6, empleado.getId());

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
                    
                }
            }
        }
    }
    
    public boolean actualizarTelefonoEmpleado(int idEmpleado, int nuevoTelefono) {
        Connection conect = null;
        String sqlActualizarTelefono = "UPDATE empleado SET telefono = ? WHERE id = ?";

        try {
            conect = con.abrirConexion();
            PreparedStatement statement = conect.prepareStatement(sqlActualizarTelefono);
            statement.setInt(1, nuevoTelefono);
            statement.setInt(2, idEmpleado);

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
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

    
    /* ------------Metodo para validar credenciales ----------------------*/
    public boolean verificarCredenciales(String usuario, String contrasenia) {
        Connection conect = null;
        String query = "SELECT * FROM empleado WHERE email = ? AND contrasenia = ?";
        
        try {
            conect = con.abrirConexion();
                      
            PreparedStatement stmt = conect.prepareStatement(query);
            stmt.setString(1, usuario);
            stmt.setString(2, contrasenia);
            var rs = stmt.executeQuery();

            return rs.next();
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
    
    public String obtenerNombreEmpleado(int idEmpleado) {
        Connection conect = null;
        String nombreEmpleado = null;

        try {
            conect = con.abrirConexion();
            String sql = "SELECT nombre FROM empleado WHERE id = ?";
            PreparedStatement statement = conect.prepareStatement(sql);
            statement.setInt(1, idEmpleado);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                nombreEmpleado = resultSet.getString("nombre");
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
    
    public String obtenerTelefonoEmpleado(int idEmpleado) {
        Connection conect = null;

        try {
            conect = con.abrirConexion();

            String sql = "SELECT telefono FROM empleado WHERE id = ?";
            PreparedStatement ps = conect.prepareStatement(sql);
            ps.setInt(1, idEmpleado);

            ResultSet resultSet = ps.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString("telefono");
            } else {
                // Si no se encuentra el empleado con el ID dado, se puede manejar como desees
                return null;
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
    
    public List<Empleado> buscarEmpleadosPorNombre(String nombre) {
        List<Empleado> empleados = new ArrayList<>();
        String consultaSQL = "SELECT * FROM empleado WHERE nombre LIKE ?";

        try (Connection conet = con.abrirConexion();
             PreparedStatement pstmt = conet.prepareStatement(consultaSQL)) {

            pstmt.setString(1, "%" + nombre + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setContrasenia(rs.getString("contrasenia"));
                empleado.setTelefono(rs.getInt("telefono"));
                empleado.setEmail(rs.getString("email"));
                empleados.add(empleado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleados;
    }
    
    public List<Empleado> buscarEmpleadosPorApellido(String apellido) {
        List<Empleado> empleados = new ArrayList<>();
        String consultaSQL = "SELECT * FROM empleado WHERE apellido LIKE ?";

        try (Connection conet = con.abrirConexion();
             PreparedStatement pstmt = conet.prepareStatement(consultaSQL)) {

            pstmt.setString(1, "%" + apellido + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Empleado empleado = new Empleado();
                empleado.setNombre(rs.getString("nombre"));
                empleado.setApellido(rs.getString("apellido"));
                empleado.setContrasenia(rs.getString("contrasenia"));
                empleado.setTelefono(rs.getInt("telefono"));
                empleado.setEmail(rs.getString("email"));
                empleados.add(empleado);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return empleados;
    }


}
