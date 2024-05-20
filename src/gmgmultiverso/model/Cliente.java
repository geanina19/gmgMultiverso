/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.model;

/**
 *
 * @author gema
 */
public class Cliente {
    // Atributos
    private int id;
    private String nombre;
    private String apellido;
    private String contrasenia;
    private String direccion;
    private int telefono;
    private String email;
    
    // Constructor vacío
    public Cliente() {
        // Aquí se podría inicializar los atributos si es necesario
    }
    
    // Constructor con todos los parámetros
    public Cliente(String nombre, String apellido, String contrasenia, String direccion, int telefono, String email) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasenia = contrasenia;
        this.direccion = direccion;
        this.telefono = telefono;
        this.email = email;
    }
    
    // Getters y setters
    
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

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString(){
        return "Proveedor{" + "id" + id + "nombre='" + nombre + '\'' + ", apellido='" + apellido + '\'' + 
                ", contrasenia='" + contrasenia + '\'' + ", direccion='" + direccion + '\'' + 
                ", telefono='" + telefono + '\'' + ", email='" + email + '}';
    }
}