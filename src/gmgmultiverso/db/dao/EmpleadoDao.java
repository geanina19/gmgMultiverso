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
    
    public boolean crearEmpleado(Empleado empleado) {
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
    
    public boolean actualizarEmpleado(Empleado empleado) {
        Connection conect = null;
    
        String sqlActualizar = "UPDATE empleado SET nombre = ?, apellido = ?, contrasenia = ?, telefono = ?, email = ? WHERE id = ?";

        try {
            conect = con.abrirConexion();
            PreparedStatement statement = conect.prepareStatement(sqlActualizar);
            statement.setString(1, empleado.getNombre());
            statement.setString(1, empleado.getApellido());
            statement.setString(1, empleado.getContrasenia());
            statement.setInt(2, empleado.getTelefono());
            statement.setString(3, empleado.getEmail());
            statement.setInt(4, empleado.getId());

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
    
}
