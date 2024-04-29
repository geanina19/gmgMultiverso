/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.model;

/**
 *
 * @author geanina.foanta
 */
public class Proveedor 
{
    private String nombre_empresa;
    private int telefono;
    private String email;
    
    //-----------Constructor vacio-----------
    public Proveedor()
    {

    }
    
    //-----------Constructor-----------
    public Proveedor(String nombre_empresa, int telefono, String email) 
    {
        this.nombre_empresa = nombre_empresa;
        this.telefono = telefono;
        this.email = email;
    }
    
    //-----------nombre_empresa-----------
    public String getNombre_empresa() 
    {
        return nombre_empresa;
    }

    public void setNombre_empresa(String nombre_empresa) 
    {
        this.nombre_empresa = nombre_empresa;
    }
    
    //-----------telefono-----------
    public int getTelefono() 
    {
        return telefono;
    }

    public void setTelefono(int telefono) 
    {
        this.telefono = telefono;
    }
    
    //-----------nombre_empresa-----------
    public String getEmail() 
    {
        return email;
    }

    public void setEmail(String email) 
    {
        this.email = email;
    }
    
    @Override
    public String toString() 
    {
        return "Proveedor{" +
                "nombre_empresa='" + nombre_empresa + '\'' +
                ", telefono='" + telefono + '\'' +
                ", email='" + email +
                '}';
    }
}
