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
    private Date fecha_pedido;
    private int estado;
    private Date ultima_actualizacion;
    
    //detalle pedido 
     private List<DetallePedido> detalles;
    private int idCliente;
    private int idEmpleado;


    public Pedido(int id, Date fecha_pedido, int estado, Date ultima_actualizacion) {
        this.id = id;
        this.fecha_pedido = fecha_pedido;
        this.estado = estado;
        this.ultima_actualizacion = ultima_actualizacion;
    }
    
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
    
    public List<DetallePedido> getDetalles() {
        return detalles;
    }

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
