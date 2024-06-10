/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.DetallesPedidoDao;
import gmgmultiverso.model.DetallePedido;
import gmgmultiverso.model.Pedido;
import static gmgmultiverso.model.Producto.getPrecioFromNombre;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import gmgmultiverso.model.Producto;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.JButton;
import javax.swing.JScrollPane;


/**
 *
 * @author gema
 */
public class CarritoCliente extends javax.swing.JFrame {

    private List<String> nombresProductosEnCarrito; // almacenar los nombres de los productos en el carrito
    private List<ImageIcon> imagenesProductosEnCarrito; // almacenar las imágenes de los productos en el carrito
    private int clienteId; 
    private int empleadoId;  
    
    public CarritoCliente() {
        initComponents();
        nombresProductosEnCarrito = new ArrayList<>();
        imagenesProductosEnCarrito = new ArrayList<>();
        this.setLocationRelativeTo(null);
        
         //para poner el logo del planeta en el frame
        this.setIconImage(getIconImage());
    }

    //para poner el logo del planeta en el frame
    @Override
    public Image getIconImage() {
        URL url = getClass().getResource("/imagenes/planeta.png");
        if (url != null) {
            return Toolkit.getDefaultToolkit().getImage(url);
        } else {
            System.err.println("Resource not found: /imagenes/planeta.png");
            return null;
        }
    }
    
    public void agregarProductoAlCarrito(String nombreProducto, ImageIcon imagenProducto) {
        nombresProductosEnCarrito.add(nombreProducto);
        imagenesProductosEnCarrito.add(imagenProducto);
        actualizarInterfazDeUsuario(); //Metodo que actualiza la interfaz
    }

    private void actualizarInterfazDeUsuario() {
    // primero limpio el panel antes de agregar los productos
    panelProductos.removeAll();

    // gridBagLayout para organizar los elementos en filas y columnas
    panelProductos.setLayout(new GridBagLayout());
    GridBagConstraints gbc = new GridBagConstraints();
    gbc.gridx = 0;
    gbc.gridy = GridBagConstraints.RELATIVE;
    gbc.anchor = GridBagConstraints.WEST;
    gbc.insets = new Insets(10, 10, 10, 10); // Espacio entre los elementos

    for (int i = 0; i < nombresProductosEnCarrito.size(); i++) {
        String nombreProducto = nombresProductosEnCarrito.get(i);
        ImageIcon imagenProducto = imagenesProductosEnCarrito.get(i);
        double precioProducto = getPrecioFromNombre(nombreProducto);

        // Mostrar la imagen del producto en un JLabel
        JLabel imagenLabel = new JLabel();
        ImageIcon imagenEscalada = new ImageIcon(imagenProducto.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH));
        imagenLabel.setIcon(imagenEscalada);
        gbc.gridx = 0;
        panelProductos.add(imagenLabel, gbc);

        // Mostrar el nombre del producto en un JLabel
        JLabel nombreLabel = new JLabel(nombreProducto);
        gbc.gridx = 1;
        gbc.insets = new Insets(10, 30, 5, 10); // Espacio entre el nombre y la imagen
        panelProductos.add(nombreLabel, gbc);

        // Mostrar la cantidad del producto en un JComboBox
        JComboBox<String> cantidadComboBox = new JComboBox<>(new String[]{"1", "2", "3", "4", "5"});
        gbc.gridx = 2;
        gbc.insets = new Insets(10, 10, 5, 10); // Espacio entre la cantidad y el nombre
        panelProductos.add(cantidadComboBox, gbc);

        // Mostrar el precio del producto en un JLabel
        JLabel precioLabel = new JLabel(String.valueOf(precioProducto));
        gbc.gridx = 3;
        gbc.insets = new Insets(10, 10, 10, 10); 
        panelProductos.add(precioLabel, gbc);

        // Crear el botón de eliminación
        JButton eliminarButton = new JButton("Eliminar");
        gbc.gridx = 4;
        panelProductos.add(eliminarButton, gbc);

        // Añadir ActionListener al JComboBox para actualizar el precio
        cantidadComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int cantidad = Integer.parseInt((String) cantidadComboBox.getSelectedItem());
                double nuevoPrecio = precioProducto * cantidad;
                precioLabel.setText(String.valueOf(nuevoPrecio));
            }
        });

        // Añadir ActionListener al botón de eliminación
        eliminarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Eliminar el producto de las listas
                int index = nombresProductosEnCarrito.indexOf(nombreProducto);
                if (index != -1) {
                    nombresProductosEnCarrito.remove(index);
                    imagenesProductosEnCarrito.remove(index);
                    // Actualizar la interfaz
                    actualizarInterfazDeUsuario();
                }
            }
        });
    }

    // Actualizar la interfaz
    revalidate();
    repaint();
}


    public void mostrarProducto(String nombreProducto, ImageIcon imagen, int cantidadProducto, double precioProducto) {
        // Verificar si la imagen es null
        if (imagen == null) {
            return;
        }

        // Mostrar el nombre del producto
        name.setText(nombreProducto);

        // Mostrar la imagen del producto
        ImageIcon imagenEscalada = new ImageIcon(imagen.getImage().getScaledInstance(190, 190, Image.SCALE_SMOOTH));
        jLabel2.setIcon(imagenEscalada);

        cantidad.removeAllItems();

        // opciones del 1 al 5 al JComboBox
        for (int i = 1; i <= 5; i++) {
            cantidad.addItem(String.valueOf(i));
        }

        if (cantidadProducto > 5) {
            cantidad.setEditable(true);
            cantidad.setSelectedItem(String.valueOf(cantidadProducto)); // Seleccionar la cantidad actual por defecto
        } else {
            cantidad.setEditable(false);
            cantidad.setSelectedItem("1");
        }

        precio.setText(String.valueOf(precioProducto));

    }
    
    private void configurarScrollPane() {
    JScrollPane scrollPane = new JScrollPane(panelProductos); // Envuelve panelProductos en un JScrollPane
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Establece la política de desplazamiento vertical
    getContentPane().add(scrollPane, BorderLayout.CENTER); // Añade el JScrollPane al contenedor principal
}
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelProductos = new javax.swing.JPanel();
        cantidad = new javax.swing.JComboBox<>();
        jLabel3 = new javax.swing.JLabel();
        precio = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        name = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Carrito Cliente");
        setResizable(false);

        panelProductos.setOpaque(false);

        jLabel3.setText("Cantidad");

        precio.setText("jLabel5");

        jLabel4.setText("Precio");

        name.setText("jLabel5");

        jLabel1.setText("Nombre");

        javax.swing.GroupLayout panelProductosLayout = new javax.swing.GroupLayout(panelProductos);
        panelProductos.setLayout(panelProductosLayout);
        panelProductosLayout.setHorizontalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGap(957, 957, 957)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(name, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(precio, javax.swing.GroupLayout.PREFERRED_SIZE, 54, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        panelProductosLayout.setVerticalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGap(106, 106, 106)
                        .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(panelProductosLayout.createSequentialGroup()
                                .addGap(15, 15, 15)
                                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(28, 28, 28)
                                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(name)
                                    .addComponent(cantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(panelProductosLayout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(jLabel4))))
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(precio)))
                .addContainerGap(243, Short.MAX_VALUE))
        );

        jButton1.setText("continuar con el pago");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 928, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panelProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addGap(18, 18, 18))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //boton de confirmar
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
     // Crear el objeto Pedido con los detalles 
    Pedido pedido = new Pedido();
    pedido.setIdCliente(clienteId);  
    pedido.setIdEmpleado(empleadoId);  
    pedido.setFecha_pedido(new Date());
    pedido.setEstado(1);  // Estado inicial del pedido
    pedido.setUltima_actualizacion(new Date());

    // Crear la lista de DetallePedido a partir de los productos en el carrito
    List<DetallePedido> detallesPedido = new ArrayList<>();
    
    // Obtener todos los componentes del panelProductos para obtener las cantidades
    for (int i = 0; i < nombresProductosEnCarrito.size(); i++) {
        // Obtener el componente en la posición correspondiente
        Component component = panelProductos.getComponent(4 * i + 2); 
        
        if (component instanceof JComboBox) {
            @SuppressWarnings("unchecked")
            JComboBox<String> cantidadComboBox = (JComboBox<String>) component;
            DetallePedido detalle = new DetallePedido();
            Producto producto = new Producto();
            producto.setNombre(nombresProductosEnCarrito.get(i));
            // Obtener el ID del producto desde la base de datos 
            int idProducto = getIdProductoFromNombre(nombresProductosEnCarrito.get(i));
            producto.setId(idProducto);
            detalle.setProducto(producto);
            detalle.setCantidad(Integer.parseInt((String) cantidadComboBox.getSelectedItem()));
            detallesPedido.add(detalle);
        } else {
            System.err.println("Error: Componente inesperado encontrado en el panelProductos.");
        }
    }
    
    pedido.setDetalles(detallesPedido);  // Lista de DetallePedido seleccionados por el cliente

    ManagerConexion managerConexion = new ManagerConexion();
    DetallesPedidoDao detallesPedidoDao = new DetallesPedidoDao(managerConexion);

    if (detallesPedidoDao.insertarPedido(pedido, clienteId)) {

        JOptionPane.showMessageDialog(this, "Pago confirmado y pedido guardado.");
    } else {
        JOptionPane.showMessageDialog(this, "Error al guardar el pedido.");
    }
    }//GEN-LAST:event_jButton1ActionPerformed

    // Método para obtener el ID del producto a partir del nombre
private int getIdProductoFromNombre(String nombreProducto) {
    List<Producto> listaProductos = obtenerListaProductos(); // Debes implementar este método
    
    // Iterar sobre la lista de productos para encontrar el ID del producto con el nombre dado
    for (Producto producto : listaProductos) {
        if (producto.getNombre().equalsIgnoreCase(nombreProducto)) {
            return producto.getId(); // Devolver el ID del producto encontrado
        }
    }
    
    // Si no se encuentra el producto
    return -1;
}

// Método para la obtención de la lista de productos
private List<Producto> obtenerListaProductos() {
    List<Producto> listaProductos = new ArrayList<>();
    
    listaProductos.add(new Producto(1, "Hamburguesa"));
    listaProductos.add(new Producto(2, "Hamburguesa Vegana"));
    listaProductos.add(new Producto(3, "Pizza"));
    listaProductos.add(new Producto(4, "Pasta"));
    listaProductos.add(new Producto(5, "Arroz"));
    listaProductos.add(new Producto(6, "Tostadas"));
    
    return listaProductos;
}

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(CarritoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CarritoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CarritoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CarritoCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CarritoCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cantidad;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel name;
    private javax.swing.JPanel panelProductos;
    private javax.swing.JLabel precio;
    // End of variables declaration//GEN-END:variables
}
