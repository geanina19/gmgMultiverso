/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.ClienteDao;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import java.awt.GridLayout;

/**
 *
 * @author gema
 */
public class PantallaCliente extends javax.swing.JFrame {

    private final ClienteDao clienteDao;
    private int contadorCarrito = 0;
    private String nombreProductoSeleccionado;
    private ImageIcon imagenProductoSeleccionado;
    private int cantidadProductoSeleccionado;
    private double precioProductoSeleccionado;
     private CarritoCliente carritoCliente;

         private String correoElectronico;

 
    private final String[] nombresProductos = {"Hamburguesa", "Hamburguesa Vegana", "Pizza", "Pasta", "Arroz", "Tostadas"};
    private final ImageIcon[] imagenes = {
        new ImageIcon(new ImageIcon(getClass().getResource("/img/burger.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)),
        new ImageIcon(new ImageIcon(getClass().getResource("/img/burgervegan.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)),
        new ImageIcon(new ImageIcon(getClass().getResource("/img/pizza.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)),
        new ImageIcon(new ImageIcon(getClass().getResource("/img/pasta.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)),
        new ImageIcon(new ImageIcon(getClass().getResource("/img/arroz.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)),
        new ImageIcon(new ImageIcon(getClass().getResource("/img/tostadas.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH))
    };
    
    // arrays de JLabels para las imágenes y los nombres de los productos
    private JLabel[] imgLabels = new JLabel[6];
    private JLabel[] nombreLabels = new JLabel[6];

    public PantallaCliente() {
        initComponents();
    ManagerConexion con = new ManagerConexion(); 
    clienteDao = new ClienteDao(con); 
   
    // Hacer que el botón de búsqueda no tenga color de fondo
        jButton1.setOpaque(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setBorderPainted(false);
    
    ImageIcon iconoCarrito = new ImageIcon(getClass().getResource("/img/carrito.png"));
    Image imagenEscaladaCarrito = iconoCarrito.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
    ImageIcon imagenEscaladaCarritoIcon = new ImageIcon(imagenEscaladaCarrito);

    JLabel imagenCarrito = new JLabel(imagenEscaladaCarritoIcon);
    imagenCarrito.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            abrirCarritoCliente(evt);
        }
    });

    jMenuBar1.add(Box.createHorizontalGlue());
    jMenuBar1.add(imagenCarrito);

    this.carritoCliente = new CarritoCliente();

    mostrarProductos();
    
    // Agregar DocumentListener al campo de búsqueda
    buscador.getDocument().addDocumentListener(new DocumentListener() {
        @Override
        public void insertUpdate(DocumentEvent e) {
            buscarProducto();
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            buscarProducto();
        }

        @Override
        public void changedUpdate(DocumentEvent e) {
            buscarProducto();
        }
    });
    
  
    }


private void mostrarProductos() {
    JLabel[] imagenesLabels = {imgburger, imgburgervegan, imgpizza, imgpasta, imgarroz, imgtostadas};
    JLabel[] nombresLabels = {hamburgesa, hamburguesavegan, pizza, pasta, arroz, tostadas};

    for (int i = 0; i < nombresProductos.length; i++) {
        imgLabels[i] = imagenesLabels[i]; // Agregar a imgLabels
        nombreLabels[i] = nombresLabels[i]; // Agregar a nombreLabels

        imagenesLabels[i].setIcon(imagenes[i]);
        final int index = i;
        imagenesLabels[i].addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                imgMouseClicked(evt, nombresProductos[index]);
            }
        });
        nombresLabels[i].setText(nombresProductos[i]);
    }
}

    // Método para buscar y mostrar el producto correspondiente
private void buscarProducto() {
    String nombreBuscado = buscador.getText().trim().toLowerCase();

    // Mostrar todos los productos si no hay texto en el campo de búsqueda
    if (nombreBuscado.isEmpty()) {
        for (int i = 0; i < nombresProductos.length; i++) {
            imgLabels[i].setLocation(40 + i * 330, 10); // Ajustar la posición de la imagen
            nombreLabels[i].setLocation(110 + i * 330, 210); // Ajustar la posición del nombre
            imgLabels[i].setVisible(true);
            nombreLabels[i].setVisible(true);
        }
    } else {
        // Calcular el número de productos encontrados y el ancho del espacio vacío
        int numProductosEncontrados = 0;
        int anchoEspacioVacio = 0;
        for (int i = 0; i < nombresProductos.length; i++) {
            if (nombresProductos[i].toLowerCase().contains(nombreBuscado)) {
                numProductosEncontrados++;
            }
        }
        if (numProductosEncontrados > 1) {
            anchoEspacioVacio = (numProductosEncontrados - 1) * 330;
        }

        // Mostrar solo los productos que coinciden con la búsqueda y centrarlos
        int x = (panelProductos.getWidth() - anchoEspacioVacio - 203) / 2; // Calcular la posición x inicial
        for (int i = 0; i < nombresProductos.length; i++) {
            if (nombresProductos[i].toLowerCase().contains(nombreBuscado)) {
                imgLabels[i].setLocation(x, 10); // Ajustar la posición de la imagen
                nombreLabels[i].setLocation(x + 70, 210); // Ajustar la posición del nombre
                x += 330; // Aumentar la posición x para el próximo producto
                imgLabels[i].setVisible(true);
                nombreLabels[i].setVisible(true);
            } else {
                imgLabels[i].setVisible(false);
                nombreLabels[i].setVisible(false);
            }
        }
    }
}


    private void abrirCarritoCliente(java.awt.event.MouseEvent evt) {
    CarritoCliente carritoCliente = new CarritoCliente();
    carritoCliente.mostrarProducto(nombreProductoSeleccionado, imagenProductoSeleccionado, cantidadProductoSeleccionado, precioProductoSeleccionado);
    carritoCliente.setVisible(true);
}


 private void imgMouseClicked(java.awt.event.MouseEvent evt, String nombreProducto) {
    // Crear una instancia de la ventana infoProducto
    infoProducto dialog = new infoProducto(this, true, contadorCarrito);
    // Obtener el precio del producto seleccionado
    double precioProducto = getPrecioFromNombre(nombreProducto);
    // Establecer el nombre y precio del producto en la ventana infoProducto
    dialog.setNombreProducto(nombreProducto, precioProducto);
    // Mostrar la ventana infoProducto
    dialog.setVisible(true);
    
    nombreProductoSeleccionado = nombreProducto;
    imagenProductoSeleccionado = imagenes[getIndexFromNombre(nombreProducto)];
    cantidadProductoSeleccionado = 1; 
    precioProductoSeleccionado = getPrecioFromNombre(nombreProducto); 
    
    // Agregar el producto al carrito
    carritoCliente.agregarProductoAlCarrito(nombreProductoSeleccionado, imagenProductoSeleccionado);

    abrirCarritoCliente(evt);
}


private int getIndexFromNombre(String nombreProducto) {
    for (int i = 0; i < nombresProductos.length; i++) {
        if (nombresProductos[i].equalsIgnoreCase(nombreProducto)) {
            return i;
        }
    }
    return -1; // Si no se encuentra el nombre, retorna -1
}

private double getPrecioFromNombre(String nombreProducto) {
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

 // Método para establecer el correo electrónico del cliente
    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }


// Método para obtener el correo electrónico del cliente
private String getCorreoElectronico() {
    return correoElectronico;
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        panel = new javax.swing.JPanel();
        buscador = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        panelProductos = new javax.swing.JPanel();
        imgburger = new javax.swing.JLabel();
        hamburgesa = new javax.swing.JLabel();
        imgburgervegan = new javax.swing.JLabel();
        imgpizza = new javax.swing.JLabel();
        hamburguesavegan = new javax.swing.JLabel();
        pizza = new javax.swing.JLabel();
        imgpasta = new javax.swing.JLabel();
        pasta = new javax.swing.JLabel();
        imgarroz = new javax.swing.JLabel();
        arroz = new javax.swing.JLabel();
        imgtostadas = new javax.swing.JLabel();
        tostadas = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel.setBackground(new java.awt.Color(255, 221, 129));
        panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        panel.add(buscador, new org.netbeans.lib.awtextra.AbsoluteConstraints(307, 58, 428, 46));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N
        jButton1.setToolTipText("");
        jButton1.setBorder(null);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        panel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(750, 30, -1, 100));

        panelProductos.setBackground(new java.awt.Color(255, 221, 129));

        hamburgesa.setText("jLabel1");

        hamburguesavegan.setText("jLabel1");

        pizza.setText("jLabel1");

        pasta.setText("jLabel1");

        arroz.setText("jLabel1");

        tostadas.setText("jLabel1");

        javax.swing.GroupLayout panelProductosLayout = new javax.swing.GroupLayout(panelProductos);
        panelProductos.setLayout(panelProductosLayout);
        panelProductosLayout.setHorizontalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(imgburger, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(127, 127, 127)
                .addComponent(imgburgervegan, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137)
                .addComponent(imgpizza, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGap(110, 110, 110)
                .addComponent(hamburgesa, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(218, 218, 218)
                .addComponent(hamburguesavegan, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151)
                .addComponent(pizza, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(imgpasta, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(117, 117, 117)
                .addComponent(imgarroz, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(137, 137, 137)
                .addComponent(imgtostadas, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGap(100, 100, 100)
                .addComponent(pasta, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(224, 224, 224)
                .addComponent(arroz, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(227, 227, 227)
                .addComponent(tostadas, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        panelProductosLayout.setVerticalGroup(
            panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelProductosLayout.createSequentialGroup()
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelProductosLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(imgburger, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(imgburgervegan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgpizza, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hamburgesa)
                    .addComponent(hamburguesavegan)
                    .addComponent(pizza))
                .addGap(14, 14, 14)
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgpasta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgarroz, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgtostadas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(panelProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pasta)
                    .addComponent(arroz)
                    .addComponent(tostadas)))
        );

        panel.add(panelProductos, new org.netbeans.lib.awtextra.AbsoluteConstraints(74, 165, -1, -1));

        jMenu1.setText("Productos");

        jMenuItem3.setText("Ver productos");
        jMenu1.add(jMenuItem3);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        jMenuItem2.setText("Contáctanos");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem2);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Perfil");

        jMenuItem1.setText("Ver perfil");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(222, 222, 222)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(panel, javax.swing.GroupLayout.DEFAULT_SIZE, 1069, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel, javax.swing.GroupLayout.PREFERRED_SIZE, 792, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        String nombreBuscado = buscador.getText().trim(); // Obtener el texto ingresado en el campo de búsqueda

        // Buscar el índice del nombre en el arreglo de nombresProductos
        int indice = -1;
        for (int i = 0; i < nombresProductos.length; i++) {
            if (nombresProductos[i].equalsIgnoreCase(nombreBuscado)) {
                indice = i;
                break;
            }
        }

        // Mostrar el producto encontrado y ocultar los demás
        if (indice != -1) {
            imgburger.setVisible(indice == 0);
            hamburgesa.setVisible(indice == 0);
            imgburgervegan.setVisible(indice == 1);
            hamburguesavegan.setVisible(indice == 1);
            imgpizza.setVisible(indice == 2);
            pizza.setVisible(indice == 2);
            imgpasta.setVisible(indice == 3);
            pasta.setVisible(indice == 3);
            imgarroz.setVisible(indice == 4);
            arroz.setVisible(indice == 4);
            imgtostadas.setVisible(indice == 5);
            tostadas.setVisible(indice == 5);

            // Ocultar todos los componentes si no se encuentra ningún producto
            if (indice == -1) {
                imgburger.setVisible(false);
                hamburgesa.setVisible(false);
                imgburgervegan.setVisible(false);
                hamburguesavegan.setVisible(false);
                imgpizza.setVisible(false);
                pizza.setVisible(false);
                imgpasta.setVisible(false);
                pasta.setVisible(false);
                imgarroz.setVisible(false);
                arroz.setVisible(false);
                imgtostadas.setVisible(false);
                tostadas.setVisible(false);

                // Mostrar un mensaje de error si el producto no se encuentra
                JOptionPane.showMessageDialog(null, "Producto no encontrado");
            }
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
      // Obtener el correo electrónico del cliente
    String correoElectronico = getCorreoElectronico();

    // Abrir el perfil del cliente y pasar el correo electrónico
    PerfilCliente pc = new PerfilCliente(correoElectronico);
    pc.setVisible(true);
    this.dispose(); 
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
       AyudaCliente ac = new AyudaCliente();
       ac.setVisible(true);
       this.dispose(); 
    }//GEN-LAST:event_jMenuItem2ActionPerformed

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
            java.util.logging.Logger.getLogger(PantallaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PantallaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PantallaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PantallaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PantallaCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel arroz;
    private javax.swing.JTextField buscador;
    private javax.swing.JLabel hamburgesa;
    private javax.swing.JLabel hamburguesavegan;
    private javax.swing.JLabel imgarroz;
    private javax.swing.JLabel imgburger;
    private javax.swing.JLabel imgburgervegan;
    private javax.swing.JLabel imgpasta;
    private javax.swing.JLabel imgpizza;
    private javax.swing.JLabel imgtostadas;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JPanel panel;
    private javax.swing.JPanel panelProductos;
    private javax.swing.JLabel pasta;
    private javax.swing.JLabel pizza;
    private javax.swing.JLabel tostadas;
    // End of variables declaration//GEN-END:variables
}
