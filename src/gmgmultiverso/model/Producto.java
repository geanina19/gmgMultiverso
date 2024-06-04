/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.model;

/**
 *
 * @author monic
 */
public class Producto {
    private int id;
    private String nombre;
    private double precio;
    private int unidad_existente;
    private int idProveedor;
    
    //-----------Constructor vacio-----------
    public Producto(){
       
    }
    //-----------Constructor-----------
    public Producto(int id, String nombre, double precio, int unidad_existente, int idProveedor) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.unidad_existente = unidad_existente;
        this.idProveedor = idProveedor;
    }
    
    //-----------Constructor sin el parámetro de ID-----------
    public Producto(String nombre, double precio, int unidad_existente, int idProveedor) {
        this.nombre = nombre;
        this.precio = precio;
        this.unidad_existente = unidad_existente;
        this.idProveedor = idProveedor;
    }
    
    // Constructor que acepta un int para el ID y un String para el nombre
public Producto(int id, String nombre) {
    this.id = id;
    this.nombre = nombre;
    // Asigna valores predeterminados para los otros campos o realiza cualquier otra lógica necesaria
    this.precio = 0.0;  // Precio predeterminado
    this.unidad_existente = 0;  // Unidades predeterminadas
}

    
    
    //-----------Getters y Setters-----------

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getIdProveedor() {
        return idProveedor;
    }

    public void setIdProveedor(int idProveedor) {
        this.idProveedor = idProveedor;
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", unidad_existente=" + unidad_existente + '}';
    }    
     // Método para obtener el precio de un producto dado su nombre
    public static double getPrecioFromNombre(String nombreProducto) {
        switch(nombreProducto) {
            case "Hamburguesa":
            case "Hamburguesa Vegana":
                return 5.99;
            case "Pizza":
                return 7.99;
            case "Pasta":
                return 6.99;
            case "Arroz":
                return 4.99;
            case "Tostadas":
                return 3.99;
            default:
                return 0.0; 
        }
    }
}
