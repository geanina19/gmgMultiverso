/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gmgmultiverso.model;

/**
 *
 * @author david
 */

public class DetallePedido {
    private int id;
    private Pedido pedido;
    private Producto producto;
    private int cantidad;
    private double precio;

   public DetallePedido(int id, Pedido pedido, Producto producto, int cantidad, double precio) {
    this.id = id;
    this.pedido = pedido;
    this.producto = producto;
    this.cantidad = cantidad;
    this.precio = precio;
}


    public DetallePedido() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "DetallePedido{" + "id=" + id + ", pedido=" + pedido + ", producto=" + producto + ", cantidad=" + cantidad + ", precio=" + precio + '}';
    }
}
