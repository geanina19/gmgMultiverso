/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author monic
 */
public class Pedido {
    private int id;
    private int id_cliente;
    private Date fecha_pedido;
    private int id_empleado;
    private int estado;
    private Date ultima_actualizacion;
<<<<<<< HEAD
=======

>>>>>>> 3abc3326ddf69cd17a5ab97de23e6023c030acf2
    
    //detalle pedido 
     private List<DetallePedido> detalles;
    private int idCliente;
    private int idEmpleado;

<<<<<<< HEAD
=======

>>>>>>> 3abc3326ddf69cd17a5ab97de23e6023c030acf2

    public Pedido(int id, int id_cliente, Date fecha_pedido, int id_empleado, int estado, Date ultima_actualizacion) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.fecha_pedido = fecha_pedido;
        this.id_empleado = id_empleado;
        this.estado = estado;
        this.ultima_actualizacion = ultima_actualizacion;
    }
    
<<<<<<< HEAD
=======

>>>>>>> 3abc3326ddf69cd17a5ab97de23e6023c030acf2
    // Nuevo constructor solo para detalles
  public Pedido(int id, Date fecha_pedido, int estado, Date ultima_actualizacion, List<DetallePedido> detalles) {
    this.id = id;
    this.fecha_pedido = fecha_pedido;
    this.estado = estado;
    this.ultima_actualizacion = ultima_actualizacion;
    this.detalles = detalles;
}


    
    public Pedido() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getFecha_pedido() {
        return fecha_pedido;
    }

    public void setFecha_pedido(Date fecha_pedido) {
        this.fecha_pedido = fecha_pedido;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getUltima_actualizacion() {
        return ultima_actualizacion;
    }

    public void setUltima_actualizacion(Date ultima_actualizacion) {
        this.ultima_actualizacion = ultima_actualizacion;
    }
<<<<<<< HEAD
=======

>>>>>>> 3abc3326ddf69cd17a5ab97de23e6023c030acf2
    
    public List<DetallePedido> getDetalles() {
        return detalles;
    }
<<<<<<< HEAD
=======

>>>>>>> 3abc3326ddf69cd17a5ab97de23e6023c030acf2
    //añadido id cliente y empleado

    public int getId_cliente() {
        return id_cliente;
    }

    public void setId_cliente(int id_cliente) {
        this.id_cliente = id_cliente;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }
<<<<<<< HEAD
    
    

=======
   
>>>>>>> 3abc3326ddf69cd17a5ab97de23e6023c030acf2
    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
    }

   //
      public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    
      public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }
    
    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", fecha_pedido=" + fecha_pedido + ", estado=" + estado + ", ultima_actualizacion=" + ultima_actualizacion + ", detalles=" + detalles + '}';
    }    
}
