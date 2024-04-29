/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.db.dao;

import gmgmultiverso.db.Conexion;
import gmgmultiverso.model.Proveedor;
import java.awt.List;
import java.awt.print.Book;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author geanina.foanta
 */
public class ProveedorDao 
{
    
    private Conexion con;
    Connection conect;
    
    Statement st;
    ResultSet rs;
    
    //private List<Proveedor> proveedores;
    
    public ProveedorDao(Conexion con)
    {
        this.con = con;
    }
    
    
    /*
    public List<Proveedor> list() 
    {
        String sqlDatos = "SELECT * FROM proveedor";
        
        try 
        {
            conect=con.abrirConexion();
            st=conect.createStatement();
            rs=st.executeQuery(sqlDatos);

            
            List<Proveedor> proveedores = new ArrayList<>();
            /*
            while (rs.next()) 
            {
                Proveedor proveedor = new Proveedor(
                        rs.getString("isbn"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getInt("year")
                );
                proveedores.add(proveedor);
            }
            
            Object [] proveedores = new Object[9];
            
            while (rs.next()) 
            {
                proveedores[0] = rs.getString("NombreProveedor");
                proveedores[1] = rs.getString("NombreContacto");
                proveedores[2] = rs.getString("TELEFONO");
                proveedores[3] = rs.getString("EMAILCONTACTO");
                proveedores[4] = rs.getString("NombrePais");
                proveedores[5] = rs.getString("NombreMunicipio");
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
            if (con != null) {
                try {
                    con.cerrarConexion((Connection) con);
                } 
                catch (SQLException e) 
                {
                    
                }
            }
        }
    }
    */
    
    public boolean save(Proveedor proveedor) 
    {
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
            if (con != null) 
            {
                try 
                {
                    con.cerrarConexion(conect);
                } 
                catch (SQLException e) 
                {
                    System.out.println("Error cerrar ProveedorDao");
                }
            }
        }
    }
    
    /*
    //-------------eliminar un proveedor-------------
    public void eliminarProveedor(Proveedor proveedor) 
    {
        proveedores.remove(proveedor);
    }
    */
}
