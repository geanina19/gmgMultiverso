/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gmgmultiverso;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author geanina.foanta
 */
public class PrincipalGmgMultiverso extends javax.swing.JFrame 
{

    /**
     * Creates new form PrincipalGmgMultiverso
     */
    public PrincipalGmgMultiverso() 
    {
        initComponents();
        this.setSize(1326, 670);
        this.setLocationRelativeTo(null);
    }
    
    //------Cambiar Logo dependiendo del tema
    public void updateLogo(String theme) {
        String logoPath = theme.equals("oscuro") ? "/imagenes/logoBlanco.png" : "/imagenes/logoGrande.png";
        labelLogo.setIcon(new ImageIcon(getClass().getResource(logoPath)));
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        labelLogo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        menugestion = new javax.swing.JMenu();
        proveedores = new javax.swing.JMenu();
        itemBuscarProveedor = new javax.swing.JMenuItem();
        itemAnadirProveedor = new javax.swing.JMenuItem();
        empleados = new javax.swing.JMenu();
        itemBuscarEmpleado = new javax.swing.JMenuItem();
        itemAnadirEmpleado = new javax.swing.JMenuItem();
        productos = new javax.swing.JMenu();
        itemBuscarProducto = new javax.swing.JMenuItem();
        itemAnadirProducto = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        menuTema = new javax.swing.JMenu();
        itemOscuro = new javax.swing.JMenuItem();
        itemClaro = new javax.swing.JMenuItem();
        jMenu1 = new javax.swing.JMenu();
        temaOp1 = new javax.swing.JMenuItem();
        temaOp2 = new javax.swing.JMenuItem();
        temaOp3 = new javax.swing.JMenuItem();
        temaOp4 = new javax.swing.JMenuItem();
        temaOp5 = new javax.swing.JMenuItem();
        temaOp6 = new javax.swing.JMenuItem();
        temaOp7 = new javax.swing.JMenuItem();
        temaOp8 = new javax.swing.JMenuItem();
        temaOp9 = new javax.swing.JMenuItem();
        temaOp10 = new javax.swing.JMenuItem();
        menuPerfil = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Principal GmgMultiverso");
        setBackground(new java.awt.Color(255, 204, 204));

        panelPrincipal.setOpaque(false);
        panelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logoGrande.png"))); // NOI18N
        panelPrincipal.add(labelLogo, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 120, -1, -1));

        menuArchivo.setText("Archivo");
        jMenuBar1.add(menuArchivo);

        menugestion.setText("Gestión");

        proveedores.setText("Proveedores");

        itemBuscarProveedor.setText("Buscar");
        proveedores.add(itemBuscarProveedor);

        itemAnadirProveedor.setText("Añadir");
        proveedores.add(itemAnadirProveedor);

        menugestion.add(proveedores);

        empleados.setText("Empleados");

        itemBuscarEmpleado.setText("Buscar");
        empleados.add(itemBuscarEmpleado);

        itemAnadirEmpleado.setText("Añadir");
        empleados.add(itemAnadirEmpleado);

        menugestion.add(empleados);

        productos.setText("Productos");

        itemBuscarProducto.setText("Buscar");
        productos.add(itemBuscarProducto);

        itemAnadirProducto.setText("Añadir");
        productos.add(itemAnadirProducto);

        menugestion.add(productos);

        jMenuBar1.add(menugestion);

        menuAyuda.setText("Ayuda");
        jMenuBar1.add(menuAyuda);

        menuTema.setText("Tema");

        itemOscuro.setText("Oscuro");
        itemOscuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemOscuroActionPerformed(evt);
            }
        });
        menuTema.add(itemOscuro);

        itemClaro.setText("Claro");
        itemClaro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemClaroActionPerformed(evt);
            }
        });
        menuTema.add(itemClaro);

        jMenu1.setText("Más ...");

        temaOp1.setText("Blanco - Azul Oscuro");
        temaOp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp1ActionPerformed(evt);
            }
        });
        jMenu1.add(temaOp1);

        temaOp2.setText("Oscuro Claro - Azul Claro");
        temaOp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp2ActionPerformed(evt);
            }
        });
        jMenu1.add(temaOp2);

        temaOp3.setText("Oscuro - Morado ");
        temaOp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp3ActionPerformed(evt);
            }
        });
        jMenu1.add(temaOp3);

        temaOp4.setText("Gris - Naranja");
        temaOp4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp4ActionPerformed(evt);
            }
        });
        jMenu1.add(temaOp4);

        temaOp5.setText("Contraste");
        temaOp5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp5ActionPerformed(evt);
            }
        });
        jMenu1.add(temaOp5);

        temaOp6.setText("Blanco - Azul Claro");
        temaOp6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp6ActionPerformed(evt);
            }
        });
        jMenu1.add(temaOp6);

        temaOp7.setText("Blanco - Naranja ");
        temaOp7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp7ActionPerformed(evt);
            }
        });
        jMenu1.add(temaOp7);

        temaOp8.setText("Verde Azulado ");
        temaOp8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp8ActionPerformed(evt);
            }
        });
        jMenu1.add(temaOp8);

        temaOp9.setText("Blanco - Verde ");
        temaOp9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp9ActionPerformed(evt);
            }
        });
        jMenu1.add(temaOp9);

        temaOp10.setText("Gris - Azul Claro ");
        temaOp10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp10ActionPerformed(evt);
            }
        });
        jMenu1.add(temaOp10);

        menuTema.add(jMenu1);

        jMenuBar1.add(menuTema);

        menuPerfil.setText("Perfil");
        jMenuBar1.add(menuPerfil);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemClaroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemClaroActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatCyanLightIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogo("blanco");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_itemClaroActionPerformed

    private void itemOscuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemOscuroActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatArcDarkIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogo("oscuro");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_itemOscuroActionPerformed

    private void temaOp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp1ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatArcIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogo("blanco"); // Cambia el logo al negro
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp1ActionPerformed

    private void temaOp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp2ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatCarbonIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogo("oscuro"); // Cambia el logo al blanco
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp2ActionPerformed

    private void temaOp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp3ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogo("oscuro"); // Cambia el logo al blanco
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp3ActionPerformed

    private void temaOp4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp4ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatHiberbeeDarkIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogo("oscuro"); // Cambia el logo al blanco
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp4ActionPerformed

    private void temaOp5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp5ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatHighContrastIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogo("oscuro"); // Cambia el logo al blanco
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp5ActionPerformed

    private void temaOp6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp6ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatLightFlatIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogo("blanco"); // Cambia el logo al oscuro
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp6ActionPerformed

    private void temaOp7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp7ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogo("blanco"); // Cambia el logo al oscuro
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp7ActionPerformed

    private void temaOp8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp8ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatSolarizedDarkContrastIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogo("oscuro"); // Cambia el logo al blanco
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp8ActionPerformed

    private void temaOp9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp9ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogo("blanco"); // Cambia el logo al oscuro
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp9ActionPerformed

    private void temaOp10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp10ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatDraculaContrastIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogo("oscuro"); // Cambia el logo al blanco
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp10ActionPerformed

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
            java.util.logging.Logger.getLogger(PrincipalGmgMultiverso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalGmgMultiverso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalGmgMultiverso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalGmgMultiverso.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalGmgMultiverso().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu empleados;
    private javax.swing.JMenuItem itemAnadirEmpleado;
    private javax.swing.JMenuItem itemAnadirProducto;
    private javax.swing.JMenuItem itemAnadirProveedor;
    private javax.swing.JMenuItem itemBuscarEmpleado;
    private javax.swing.JMenuItem itemBuscarProducto;
    private javax.swing.JMenuItem itemBuscarProveedor;
    private javax.swing.JMenuItem itemClaro;
    private javax.swing.JMenuItem itemOscuro;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel labelLogo;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuPerfil;
    private javax.swing.JMenu menuTema;
    private javax.swing.JMenu menugestion;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JMenu productos;
    private javax.swing.JMenu proveedores;
    private javax.swing.JMenuItem temaOp1;
    private javax.swing.JMenuItem temaOp10;
    private javax.swing.JMenuItem temaOp2;
    private javax.swing.JMenuItem temaOp3;
    private javax.swing.JMenuItem temaOp4;
    private javax.swing.JMenuItem temaOp5;
    private javax.swing.JMenuItem temaOp6;
    private javax.swing.JMenuItem temaOp7;
    private javax.swing.JMenuItem temaOp8;
    private javax.swing.JMenuItem temaOp9;
    // End of variables declaration//GEN-END:variables
}
