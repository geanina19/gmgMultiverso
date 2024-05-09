/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.ClienteDao;
import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

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

 
    private final String[] nombresProductos = {"Hamburguesa", "Hamburguesa Vegana", "Pizza", "Pasta", "Arroz", "Tostadas"};
    private final ImageIcon[] imagenes = {
        new ImageIcon(new ImageIcon(getClass().getResource("/img/burger.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)),
        new ImageIcon(new ImageIcon(getClass().getResource("/img/burgervegan.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)),
        new ImageIcon(new ImageIcon(getClass().getResource("/img/pizza.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)),
        new ImageIcon(new ImageIcon(getClass().getResource("/img/pasta.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)),
        new ImageIcon(new ImageIcon(getClass().getResource("/img/arroz.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH)),
        new ImageIcon(new ImageIcon(getClass().getResource("/img/tostadas.png")).getImage().getScaledInstance(200, 200, java.awt.Image.SCALE_SMOOTH))
    };

    public PantallaCliente() {
        initComponents();
    ManagerConexion con = new ManagerConexion(); 
    clienteDao = new ClienteDao(con); 
    
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
    }

    private void mostrarProductos() {
        JLabel[] imagenesLabels = {imgburger, imgburgervegan, imgpizza, imgpasta, imgarroz, imgtostadas};
        JLabel[] nombresLabels = {hamburgesa, hamburguesavegan, pizza, pasta, arroz, tostadas};

        for (int i = 0; i < nombresProductos.length; i++) {
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

    private void abrirCarritoCliente(java.awt.event.MouseEvent evt) {
    CarritoCliente carritoCliente = new CarritoCliente();
    carritoCliente.mostrarProducto(nombreProductoSeleccionado, imagenProductoSeleccionado, cantidadProductoSeleccionado, precioProductoSeleccionado);
    carritoCliente.setVisible(true);
}


 private void imgMouseClicked(java.awt.event.MouseEvent evt, String nombreProducto) {
        infoProducto dialog = new infoProducto(this, true, contadorCarrito);
    dialog.setNombreProducto(nombreProducto);
    dialog.setVisible(true);
    
    nombreProductoSeleccionado = nombreProducto;
    imagenProductoSeleccionado = imagenes[getIndexFromNombre(nombreProducto)];
    cantidadProductoSeleccionado = 1; 
    precioProductoSeleccionado = getPrecioFromNombre(nombreProducto); 
    
    // Agregar el producto al carrito
    carritoCliente.agregarProductoAlCarrito(nombreProductoSeleccionado, imagenProductoSeleccionado);

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


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buscador = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        imgpizza = new javax.swing.JLabel();
        imgburger = new javax.swing.JLabel();
        imgpasta = new javax.swing.JLabel();
        imgarroz = new javax.swing.JLabel();
        imgburgervegan = new javax.swing.JLabel();
        imgtostadas = new javax.swing.JLabel();
        hamburgesa = new javax.swing.JLabel();
        hamburguesavegan = new javax.swing.JLabel();
        pizza = new javax.swing.JLabel();
        pasta = new javax.swing.JLabel();
        arroz = new javax.swing.JLabel();
        tostadas = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        jMenu3 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Buscar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        hamburgesa.setText("jLabel1");

        hamburguesavegan.setText("jLabel1");

        pizza.setText("jLabel1");

        pasta.setText("jLabel1");

        arroz.setText("jLabel1");

        tostadas.setText("jLabel1");

        jMenu1.setText("Productos");
        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");
        jMenuBar1.add(jMenu2);

        jMenu3.setText("Perfil");
        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addComponent(hamburgesa, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(174, 174, 174)
                .addComponent(hamburguesavegan, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pizza, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addComponent(pasta, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(186, 186, 186)
                        .addComponent(arroz, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(tostadas, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addComponent(buscador, javax.swing.GroupLayout.PREFERRED_SIZE, 428, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(imgpasta, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(imgburger, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(imgarroz, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(imgtostadas, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(212, 212, 212)
                                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(imgburgervegan, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                                .addComponent(imgpizza, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addGap(67, 67, 67)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgburger, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgburgervegan, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgpizza, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(hamburgesa)
                    .addComponent(hamburguesavegan)
                    .addComponent(pizza))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgpasta, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgarroz, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(imgtostadas, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(pasta)
                    .addComponent(arroz)
                    .addComponent(tostadas))
                .addContainerGap())
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
    private javax.swing.JLabel pasta;
    private javax.swing.JLabel pizza;
    private javax.swing.JLabel tostadas;
    // End of variables declaration//GEN-END:variables
}
