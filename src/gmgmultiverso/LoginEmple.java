/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.EmpleadoDao;
import gmgmultiverso.model.Empleado;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author monic
 */
public class LoginEmple extends javax.swing.JFrame {

    private EmpleadoDao empleadoDao;

    
    public LoginEmple() {
        initComponents();
        //La pantalla se abra en el centro
        this.setLocationRelativeTo(null);
        
        //para poner el logo del planeta en el frame
        this.setIconImage(getIconImage());
        
        empleadoDao = new EmpleadoDao(new ManagerConexion());
        /*-----------------------COLOR JPANEL-------------------------------------------*/
        panelPrincipal.setBackground(new java.awt.Color(230, 95, 76)); 
        panelLogin.setBackground(new java.awt.Color(230, 163, 141));
        
        /*-----------------------PLACEHOLDER TEXTFIELD-------------------------------------------*/
        textFieldUser.setText("Ingrese su usuario");
        textFieldUser.setForeground(Color.BLACK);
        textFieldUser.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldUser.getText().equals("Ingrese su usuario")) {
                    textFieldUser.setText("");
                    textFieldUser.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldUser.getText().isEmpty()) {
                    textFieldUser.setText("Ingrese su usuario");
                    textFieldUser.setForeground(Color.GRAY);
                }
            }
        });
        
        labelContra.setText("Contraseña:");

        textFieldPassword.setText("********");
        textFieldPassword.setForeground(Color.GRAY);
        textFieldPassword.setEchoChar((char) 0); // Mostrar texto como un campo de texto regular
        textFieldPassword.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (String.valueOf(textFieldPassword.getPassword()).equals("********")) {
                    textFieldPassword.setText("");
                    textFieldPassword.setForeground(Color.BLACK);
                    textFieldPassword.setEchoChar('•'); // Establecer el carácter de eco para contraseñas
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (String.valueOf(textFieldPassword.getPassword()).isEmpty()) {
                    textFieldPassword.setText("********");
                    textFieldPassword.setForeground(Color.GRAY);
                    textFieldPassword.setEchoChar((char) 0); 
                }
            }
        });
        // Añadir KeyListener para detectar Caps Lock y Enter
        textFieldPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_CAPS_LOCK) {
//                    boolean isCapsOn = Toolkit.getDefaultToolkit().getLockingKeyState(KeyEvent.VK_CAPS_LOCK);
//                    if (isCapsOn) {
//                        JOptionPane.showMessageDialog(null, "¡Mayúsculas activadas!", "Advertencia", JOptionPane.WARNING_MESSAGE);
//                    }
//                }
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    iniciarSesion();
                }
            }
        });

        textFieldUser.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    iniciarSesion();
                }
            }
        });                   
    }
    
    //para poner el logo del planeta en el frame
    @Override
    public Image getIconImage() {
        URL url = getClass().getResource("/imagenes/planeta.png");
        if (url != null) {
            return Toolkit.getDefaultToolkit().getImage(url);
        } else {
            System.err.println("No se encuentra la imagen : /imagenes/planeta.png");
            return null;
        }
    }

    private void iniciarSesion() {
        String usuario = textFieldUser.getText();
        String contrasenia = new String(textFieldPassword.getPassword());

        if (empleadoDao.verificarCredenciales(usuario, contrasenia)) {
            int idEmpleado = empleadoDao.obtenerIdUsuario(usuario, contrasenia);
            PrincipalEmple pEmple = new PrincipalEmple(this, true, idEmpleado);
            System.out.println("num emple" + idEmpleado);
            pEmple.setVisible(true);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }    
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panelPrincipal = new javax.swing.JPanel();
        panelLogin = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        labelInicia = new javax.swing.JLabel();
        labelName = new javax.swing.JLabel();
        labelContra = new javax.swing.JLabel();
        textFieldPassword = new javax.swing.JPasswordField();
        textFieldUser = new javax.swing.JTextField();
        buttonEntrar = new javax.swing.JButton();
        buttonCancel = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Login Empleado");
        setBackground(new java.awt.Color(153, 204, 255));

        panelPrincipal.setBackground(new java.awt.Color(204, 204, 204));
        panelPrincipal.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logox200.png"))); // NOI18N

        labelInicia.setFont(new java.awt.Font("Verdana", 1, 18)); // NOI18N
        labelInicia.setText("Bienvenido, inicia sesión");

        labelName.setText("Usuario:");

        labelContra.setText("Contraseña: ");

        textFieldPassword.setText("jPasswordField1");

        buttonEntrar.setText("Entrar");
        buttonEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonEntrarActionPerformed(evt);
            }
        });

        buttonCancel.setText("Cancelar");

        javax.swing.GroupLayout panelLoginLayout = new javax.swing.GroupLayout(panelLogin);
        panelLogin.setLayout(panelLoginLayout);
        panelLoginLayout.setHorizontalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGap(142, 142, 142)
                .addComponent(buttonEntrar)
                .addGap(129, 129, 129)
                .addComponent(buttonCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelLoginLayout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelContra, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textFieldPassword, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
                            .addComponent(textFieldUser))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(labelInicia, javax.swing.GroupLayout.DEFAULT_SIZE, 405, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addContainerGap())
        );
        panelLoginLayout.setVerticalGroup(
            panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginLayout.createSequentialGroup()
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(panelLoginLayout.createSequentialGroup()
                        .addGap(45, 45, 45)
                        .addComponent(labelInicia)
                        .addGap(45, 45, 45)
                        .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(textFieldUser, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))))
                .addGap(35, 35, 35)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(labelContra, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(67, 67, 67)
                .addGroup(panelLoginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonEntrar)
                    .addComponent(buttonCancel))
                .addContainerGap(222, Short.MAX_VALUE))
        );

        panelPrincipal.add(panelLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(240, 90, 710, 520));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 1160, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 710, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void buttonEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonEntrarActionPerformed
        iniciarSesion();
    }//GEN-LAST:event_buttonEntrarActionPerformed

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
            java.util.logging.Logger.getLogger(LoginEmple.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginEmple.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginEmple.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginEmple.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginEmple().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buttonCancel;
    private javax.swing.JButton buttonEntrar;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel labelContra;
    private javax.swing.JLabel labelInicia;
    private javax.swing.JLabel labelName;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelPrincipal;
    private javax.swing.JPasswordField textFieldPassword;
    private javax.swing.JTextField textFieldUser;
    // End of variables declaration//GEN-END:variables
}
