/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.db.dao;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.model.Proveedor;
import java.util.List;
//import java.awt.print.Proveedor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.sql.PreparedStatement;


/**
 *
 * @author geanina.foanta
 */
public class ProveedorDao 
{
    
    private ManagerConexion con;
    
    
    public ProveedorDao(ManagerConexion con)
    {
        this.con = con;
    }
    
    /*
    public List<Proveedor> list() 
    {
        String sqlDatos = "SELECT * FROM proveedor";
        
        try 
        {
            conect = con.abrirConexion();
            st = conect.createStatement();
            rs = st.executeQuery(sqlDatos);

            
            List<Proveedor> proveedores = new ArrayList<>();
    
            while (rs.next()) 
            {
                Proveedor proveedor = new Proveedor(
                        rs.getString("nombre_empresa"),
                        rs.getInt("telefono"),
                        rs.getString("email")
                );
                proveedores.add(proveedor);
            }
   
            /*
            Object [] proveedores = new Object[9];
            
            while (rs.next()) 
            {
                proveedores[0] = rs.getString("nombre_empresa");
                proveedores[1] = rs.getInt("telefono");
                proveedores[2] = rs.getString("email");
                proveedores.add(proveedor);
            }
            

            return proveedores;
        }
        catch (SQLException e) 
        {
            throw new RuntimeException(e);
        } 
        finally 
        {
            if (conect != null) 
            {
                try 
                {
                    conect.close();
                } 
                catch (SQLException e) 
                {
                    
                }
            }
        }
    }
    */
    
    public List<Proveedor> list() 
    {
        Connection conect = null;
    
        Statement st;
        ResultSet rs;
    
        try 
        {
            conect = con.abrirConexion();

            var ps = conect.prepareStatement("SELECT * FROM proveedor");

            var resultSet = ps.executeQuery();
            List<Proveedor> proveedores = new ArrayList<>();
            while (resultSet.next()) {
                Proveedor proveedor = new Proveedor(
                        resultSet.getString("nombre_empresa"),
                        resultSet.getInt("telefono"),
                        resultSet.getString("email")
                );
                proveedores.add(proveedor);
            }

            return proveedores;
            
        } 
        catch (SQLException e) 
        {
            throw new RuntimeException(e);
        } 
        finally 
        {
            if (conect != null) 
            {
                try 
                {
                    conect.close();
                } 
                catch (SQLException e) 
                {
                    
                }
            }
        }
    }
    
            
    public boolean crearProveedor(Proveedor proveedor) 
    {
        
        Connection conect = null;
    
        Statement st;
        ResultSet rs;
        
        try 
        {
            conect = con.abrirConexion();

            var sqlDatos = conect.prepareStatement("INSERT INTO proveedor(nombre_empresa, telefono, email) VALUES(?,?,?)");

            sqlDatos.setString(1, proveedor.getNombre_empresa());
            sqlDatos.setInt(2, proveedor.getTelefono());
            sqlDatos.setString(3, proveedor.getEmail());
            int insertedRows = sqlDatos.executeUpdate();
            return insertedRows == 1;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        } 
        finally 
        {
            if (conect != null) 
            {
                try 
                {
                    conect.close();
                } 
                catch (SQLException e) 
                {
                    
                }
            }
        }
    }
    
    /*
    //-------------eliminar un proveedor-------------
    
    //-------------con hibernate-------------
    public void eliminarProveedor(Proveedor proveedor) 
    {
        proveedores.remove(proveedor);
    }
    */
    
    public boolean eliminarProveedor(int idProveedor) 
    {
        Connection conect = null;
    
        Statement st;
        ResultSet rs;
        
        String sqlEliminar = "DELETE FROM proveedor WHERE id = ?";

        try 
        {
            conect = con.abrirConexion();

            PreparedStatement statement = conect.prepareStatement(sqlEliminar);
            statement.setInt(1, idProveedor);

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        } 
        finally 
        {
            if (conect != null) 
            {
                try 
                {
                    conect.close();
                } 
                catch (SQLException e) 
                {
                    
                }
            }
        }
    }
    
    public boolean actualizarProveedor(int idProveedor, Proveedor proveedor) 
    {
        Connection conect = null;
    
        Statement st;
        ResultSet rs;
        
        String sqlActualizar = "UPDATE proveedor SET nombre_empresa = ?, telefono = ?, email = ? WHERE id = ?";

        try {
            conect = con.abrirConexion();
            PreparedStatement statement = conect.prepareStatement(sqlActualizar);
            statement.setString(1, proveedor.getNombre_empresa());
            statement.setInt(2, proveedor.getTelefono());
            statement.setString(3, proveedor.getEmail());
            statement.setInt(4, idProveedor);

            int filasAfectadas = statement.executeUpdate();
            return filasAfectadas > 0;
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
            return false;
        } 
        finally 
        {
            if (conect != null) 
            {
                try 
                {
                    conect.close();
                } 
                catch (SQLException e) 
                {
                    
                }
            }
        }
    }



}
