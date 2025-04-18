/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.model;

import java.util.Date;

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

    public Pedido(int id, int id_cliente, Date fecha_pedido, int id_empleado, int estado, Date ultima_actualizacion) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.fecha_pedido = fecha_pedido;
        this.id_empleado = id_empleado;
        this.estado = estado;
        this.ultima_actualizacion = ultima_actualizacion;
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
    
    

    @Override
    public String toString() {
        return "Pedido{" + "id=" + id + ", fecha_pedido=" + fecha_pedido + ", estado=" + estado + ", ultima_actualizacion=" + ultima_actualizacion + '}';
    }    
}
