<<<<<<< HEAD
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.model;

/**
 *
 * @author geanina.foanta
 */
public class Empleado {
    
    private int id;
    private String nombre;
    private String apellido;
    private String contrasenia;
    private int telefono;
    private String email;
    
    //-----------Constructor vacio-----------
    public Empleado() {
        
    }
    
    //-----------Constructor con parametros-----------
    public Empleado(int id, String nombre, String apellido, String contrasenia, int telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
        this.email = email;
    }
    
    // Getters y setters
    
    //-----------id-----------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    //-----------nombre-----------
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //-----------apellido-----------
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    //-----------contrasenia-----------
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    //-----------telefono-----------
    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    //-----------email-----------
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString(){
        return "Proveedor{" + "id" + id + "nombre='" + nombre + '\'' + 
                ", apellido='" + apellido + '\'' + ", contrasenia='" + contrasenia + '\'' + 
                ", telefono='" + telefono + '\'' + ", email='" + email + '}';
    }
}
=======
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.model;

/**
 *
 * @author geanina.foanta
 */
public class Empleado {
    
    private int id;
    private String nombre;
    private String apellido;
    private String contrasenia;
    private int telefono;
    private String email;
    
    //-----------Constructor vacio-----------
    public Empleado() {
        
    }
    
    //-----------Constructor con parametros-----------
    public Empleado(int id, String nombre, String apellido, String contrasenia, int telefono, String email) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.contrasenia = contrasenia;
        this.telefono = telefono;
        this.email = email;
    }
    
    // Getters y setters
    
    //-----------id-----------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    //-----------nombre-----------
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    //-----------apellido-----------
    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    //-----------contrasenia-----------
    public String getContrasenia() {
        return contrasenia;
    }

    public void setContrasenia(String contrasenia) {
        this.contrasenia = contrasenia;
    }
    
    //-----------telefono-----------
    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    //-----------email-----------
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    @Override
    public String toString(){
        return "Proveedor{" + "id" + id + "nombre='" + nombre + '\'' + 
                ", apellido='" + apellido + '\'' + ", contrasenia='" + contrasenia + '\'' + 
                ", telefono='" + telefono + '\'' + ", email='" + email + '}';
    }
}
>>>>>>> 7a97e1064f71025cf8b98b0456bb877c1ab95761
