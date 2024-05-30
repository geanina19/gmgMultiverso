/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.model;

/**
 *
 * @author geanina.foanta
 */
public class ProductoConProveedor {
    
    private int id;
    private int idProveedor;
    private String nombre;
    private double precio;
    private int unidad_existente;
    
    //constructor vacio
    public ProductoConProveedor(){
        
    }
    
    //constructor con id
    public ProductoConProveedor(int id, int idProveedor, String nombre, double precio, int unidad_existente){
        this.id = id;
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.precio = precio;
        this.unidad_existente = unidad_existente;
    }
    
    //constructor sin id
    public ProductoConProveedor(int idProveedor, String nombre, double precio, int unidad_existente){
        this.idProveedor = idProveedor;
        this.nombre = nombre;
        this.precio = precio;
        this.unidad_existente = unidad_existente;
    }

    //-----------Getters y Setters-----------
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public int getUnidad_existente() {
        return unidad_existente;
    }

    public void setUnidad_existente(int unidad_existente) {
        this.unidad_existente = unidad_existente;
    }
    
    
    
}
