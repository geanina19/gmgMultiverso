/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author geanina.foanta
 */
public class Conexion 
{
    private Connection conexion;
    private String bbdd = "jdbc:hsqldb:hsql://localhost/GMGMULTIVERSO/";
    private String usuario = "SA";
    private String contrasena = "";
    
    /*
    public Conexion(String bbdd, String usuario, String contrasena) throws ClassNotFoundException {
        this.bbdd = bbdd;
        this.usuario = usuario;
        this.contrasena = contrasena;
        Class.forName("com.mysql.jdbc.Driver");
    }
    */
    
    public Connection abrirConexion() throws SQLException
    {
        try
        {
            //cargar driver
            Class.forName("org.hsqldb.jdbc.JDBCDriver");
            
            // Asignar la conexión a la variable de instancia para que esté disponible en toda la clase
            this.conexion = DriverManager.getConnection(bbdd, usuario, contrasena);
            
            if(this.conexion != null)
            {
                System.out.println("Conexion exitosa");
            }
        }
        catch(ClassNotFoundException | SQLException e)
        {
            e.printStackTrace();
        }
        
        return this.conexion;
    }
    
    public void cerrarConexion(Connection con) throws SQLException
    {
        try
        {
            if (con != null) 
            {
                con.close();
                System.out.println("Conexión cerrada");
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
    
}
