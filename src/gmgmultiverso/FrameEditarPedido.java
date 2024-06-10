/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.PedidoConNombreDao;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author monic
 */
public class FrameEditarPedido extends javax.swing.JFrame {
        //CONEXION
    ManagerConexion con = new ManagerConexion();
    Connection conet;  
    Statement st;
    ResultSet rs;
    

    PrincipalEmple principal;
    int codigoPedido;
    PedidoConNombreDao pedidoCompleto = new PedidoConNombreDao(con);
    
    int codEmple;
    
    public FrameEditarPedido() {
        initComponents();
    }
    public FrameEditarPedido(java.awt.Frame parent, boolean modal,int codigoPedido, int codEmple, PrincipalEmple principal) {
        initComponents();
        this.principal = principal;
        this.codigoPedido = codigoPedido;
        this.codEmple = codEmple;
        // Depuración
        System.out.println("Codigo del Pedido en el constructor::ç " + codigoPedido + "codigo emple: " +codEmple); 
        this.setLocationRelativeTo(null);
        this.getContentPane().setBackground(new java.awt.Color(250, 240, 230));
        
        cargarDatosCliente(codigoPedido);
//        String nombreCliente = pedidoCompleto.getNombreClientePorIdPedido(codigoPedido);
//        textNombreCliente.setText(nombreCliente);
        
        cargarEstadosEnComboBox();
        this.setIconImage(getIconImage());
        textNombreCliente.setEditable(false);
        
        textNombreCliente.addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            JOptionPane.showMessageDialog(null, "No tiene los suficientes privilegios para modificar este dato", "Error de privilegios", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        
    }  
    @Override
    public Image getIconImage() {
        URL url = getClass().getResource("/imagenes/planeta.png") ;
        if (url != null){
            return Toolkit.getDefaultToolkit().getImage(url) ;
        } else {
            System.err.println("Resource not found: /imagenes/planeta.png");
            return  null;
        }
    }
    private void cargarDatosCliente(int idPedido) {
        String nombreCliente = pedidoCompleto.getNombreClientePorIdPedido(idPedido);
        textNombreCliente.setText(nombreCliente);
    }
    
        // Este método carga la lista de estados en un JComboBox
    public void cargarEstadosEnComboBox() {
        comboBoxEstado.removeAllItems();

        List<Integer> estados = pedidoCompleto.listarEstadosPedido();
        // Mapea los valores numéricos de los estados a sus representaciones de cadena
        Map<Integer, String> estadoStrings = new HashMap<>();
        estadoStrings.put(1, "Aceptado");
        estadoStrings.put(2, "En preparación");
        estadoStrings.put(3, "Enviado");

        // Obtener el estado del pedido actual desde la base de datos
        int estadoPedidoActual = pedidoCompleto.obtenerEstadoPedido(codigoPedido);

        // Agrega la opción "Seleccionar estado" si no hay estado asignado
        if (estadoPedidoActual == 0 || !estadoStrings.containsKey(estadoPedidoActual)) {
            comboBoxEstado.addItem("Seleccionar estado");
        }

        // Agrega los estados existentes al combo box
        for (Integer estado : estados) {
            if (estadoStrings.containsKey(estado)) {
                comboBoxEstado.addItem(estadoStrings.get(estado));
            }
        }

        // Seleccionar el estado actual si está en la lista, de lo contrario seleccionar "Seleccionar estado"
        String estadoActualTexto = estadoStrings.get(estadoPedidoActual);
        if (estadoActualTexto != null) {
            comboBoxEstado.setSelectedItem(estadoActualTexto);
        } else {
            comboBoxEstado.setSelectedItem("Seleccionar estado");
        }
    }

    
    /**************** ACTUALIZAR TABLA *****************/
    private void actualizarTablaPedidos() {  
        principal.actualizarTabla();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        textNombreCliente = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        comboBoxEstado = new javax.swing.JComboBox<>();
        editButton = new javax.swing.JButton();
        cancelButtom = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Editar Pedido");

        jLabel1.setText("Nombre del cliente");

        jLabel2.setText("Estado del pedido ");

        comboBoxEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxEstadoActionPerformed(evt);
            }
        });

        editButton.setText("Editar");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        cancelButtom.setText("Cancelar");
        cancelButtom.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtomActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(51, 51, 51)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textNombreCliente)
                            .addComponent(comboBoxEstado, 0, 232, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(152, 152, 152)
                        .addComponent(editButton)
                        .addGap(51, 51, 51)
                        .addComponent(cancelButtom)))
                .addContainerGap(105, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(58, 58, 58)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textNombreCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(81, 81, 81)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton)
                    .addComponent(cancelButtom))
                .addContainerGap(60, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtomActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtomActionPerformed
        this.dispose();
    }//GEN-LAST:event_cancelButtomActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        // Se suma 1 porque los índices en el combo box comienzan desde 0
        int nuevoEstado = comboBoxEstado.getSelectedIndex() + 1; 

        pedidoCompleto.actualizarEstadoPedidoyEmple(codigoPedido, nuevoEstado, codEmple);
        JOptionPane.showMessageDialog(null, "Se ha modificado el estado del pedido correctamente.");
        System.out.println("Estado del pedido actualizado correctamente. ID del Pedido: " + codigoPedido);
        actualizarTablaPedidos();
        dispose();
    }//GEN-LAST:event_editButtonActionPerformed

    private void comboBoxEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_comboBoxEstadoActionPerformed

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
            java.util.logging.Logger.getLogger(FrameEditarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrameEditarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrameEditarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrameEditarPedido.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrameEditarPedido().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButtom;
    private javax.swing.JComboBox<String> comboBoxEstado;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JTextField textNombreCliente;
    // End of variables declaration//GEN-END:variables
}
