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
public class PedidoConNombre {
    private int id;
    private String nombreCliente;
    private Date fechaPedido;
    private String nombreEmpleado;
    private int estado;
    private Date ultimaActualizacion;

    public PedidoConNombre(int id, String nombreCliente, Date fechaPedido, String nombreEmpleado, int estado, Date ultimaActualizacion) {
        this.id = id;
        this.nombreCliente = nombreCliente;
        this.fechaPedido = fechaPedido;
        this.nombreEmpleado = nombreEmpleado;
        this.estado = estado;
        this.ultimaActualizacion = ultimaActualizacion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public String getNombreEmpleado() {
        return nombreEmpleado;
    }

    public void setNombreEmpleado(String nombreEmpleado) {
        this.nombreEmpleado = nombreEmpleado;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Date getUltimaActualizacion() {
        return ultimaActualizacion;
    }

    public void setUltimaActualizacion(Date ultimaActualizacion) {
        this.ultimaActualizacion = ultimaActualizacion;
    }
    
    
    
}
