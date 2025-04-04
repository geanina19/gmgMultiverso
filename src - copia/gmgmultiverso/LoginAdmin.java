/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import java.awt.Color;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import gmgmultiverso.db.dao.EmpleadoDao;
import gmgmultiverso.model.Empleado;
import java.awt.Image;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ContainerListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author geanina.foanta
 */


public class LoginAdmin extends javax.swing.JFrame {

    private String nombreUsuario;
    
    //private ManagerConexion con;
    //int xMouse, yMouse;
    /**
     * Creates new form Login
     */
    public LoginAdmin() {
        initComponents();

        this.setSize(860, 500);
        //[860, 500]
        this.setLocationRelativeTo(null);
/*
        this.jPanel1.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                BufferedImage img = null;
                try {
                    img = ImageIO.read(new File("imagenes/foandoLogin.png"));
                    Image dimg = img.getScaledInstance(labelFondoLogin.getWidth(), labelFondoLogin.getHeight(), Image.SCALE_SMOOTH);
                    ImageIcon imageIcon = new ImageIcon(dimg);
                    labelFondoLogin.setIcon(imageIcon);
                    jPanel1.repaint();
                    jPanel1.revalidate();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }

        });
*/
        ingresarContrasenia.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    botonIniciarSesionMouseClicked(null); // Llama al método del botón "Iniciar sesión"
                }
            }
        });
    }
    
    public boolean validarCredenciales(String usuario, String contrasenia) {
        Connection conect;
        ManagerConexion con = new ManagerConexion();

        //Statement st;
        //ResultSet rs;
        try {
            conect = con.abrirConexion();
            String sql = "SELECT * FROM empleado WHERE nombre = ? AND contrasenia = ?";
            try (PreparedStatement statement = conect.prepareStatement(sql)) {
                statement.setString(1, usuario);
                statement.setString(2, contrasenia);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        String nombreEmpleado = resultSet.getString("nombre");
                        if (nombreEmpleado.equals("Admin") ||
                            nombreEmpleado.equals("Gema") ||
                            nombreEmpleado.equals("Monica") ||
                            nombreEmpleado.equals("Geanina")) {
                            // Si el usuario es Admin y la contraseña es correcta, permitir el inicio de sesión
                            
                            return true;
                        }
                    }
                    // Si el usuario no es Admin o la contraseña es incorrecta, denegar el inicio de sesión
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public int obtenerIdUsuario(String usuario, String contrasenia) {
        // Crear una instancia de EmpleadoDao
        EmpleadoDao empleadoDao = new EmpleadoDao(new ManagerConexion());
        // Obtener la lista de empleados con el EmpleadoDao
        List<Empleado> empleados = empleadoDao.list();
        // Iterar sobre la lista de empleados para buscar el usuario y la contraseña
        for (Empleado empleado : empleados) {
            // Verificar si el nombre de usuario y la contraseña coinciden con algún empleado
            if (empleado.getNombre().equals(usuario) && empleado.getContrasenia().equals(contrasenia)) {
                // Si coinciden, devolver el ID del empleado
                return empleado.getId();
            }
        }
        // Si no se encuentra el usuario o la contraseña no coincide, devolver -1
        return -1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel1 = new javax.swing.JPanel();
        labelIniciarSesion = new javax.swing.JLabel();
        labelUsuario = new javax.swing.JLabel();
        labelContrasenia = new javax.swing.JLabel();
        ingresarContrasenia = new javax.swing.JPasswordField();
        botonIniciarSesion = new javax.swing.JButton();
        userIcono = new javax.swing.JLabel();
        llave = new javax.swing.JLabel();
        ingresarUsuario = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        labelFondoLogin = new javax.swing.JLabel();

        jCheckBox1.setText("jCheckBox1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Iniciar sesión Administrador");
        setIconImage(getIconImage());
        setPreferredSize(new java.awt.Dimension(1280, 720));
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        labelIniciarSesion.setFont(new java.awt.Font("Showcard Gothic", 1, 24)); // NOI18N
        labelIniciarSesion.setText("Iniciar sesión");
        jPanel1.add(labelIniciarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 100, 200, 50));

        labelUsuario.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelUsuario.setText("Administrador");
        jPanel1.add(labelUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 160, -1, -1));

        labelContrasenia.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        labelContrasenia.setText("Contraseña");
        jPanel1.add(labelContrasenia, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 250, -1, -1));

        ingresarContrasenia.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ingresarContraseniaMousePressed(evt);
            }
        });
        jPanel1.add(ingresarContrasenia, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 300, 240, 30));

        botonIniciarSesion.setBackground(new java.awt.Color(153, 51, 255));
        botonIniciarSesion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        botonIniciarSesion.setForeground(new java.awt.Color(255, 255, 255));
        botonIniciarSesion.setText("Iniciar sesión");
        botonIniciarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                botonIniciarSesionMouseClicked(evt);
            }
        });
        botonIniciarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonIniciarSesionActionPerformed(evt);
            }
        });
        jPanel1.add(botonIniciarSesion, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 360, -1, 30));

        userIcono.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/user.png"))); // NOI18N
        jPanel1.add(userIcono, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 200, 30, 30));

        llave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/llave.png"))); // NOI18N
        jPanel1.add(llave, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 300, 30, 30));

        ingresarUsuario.setForeground(new java.awt.Color(204, 204, 204));
        ingresarUsuario.setText("Ingrese su nombre de usuario");
        ingresarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                ingresarUsuarioMousePressed(evt);
            }
        });
        jPanel1.add(ingresarUsuario, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 200, 240, 30));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logox200.png"))); // NOI18N
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 310, -1, 150));

        labelFondoLogin.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/fondoLogin.png"))); // NOI18N
        jPanel1.add(labelFondoLogin, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 860, 500));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ingresarContraseniaMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ingresarContraseniaMousePressed
        // TODO add your handling code here:
        if (String.valueOf(ingresarContrasenia.getPassword()).equals("********")) {
            ingresarContrasenia.setText("");
            ingresarContrasenia.setForeground(Color.black);
        }
        if (ingresarUsuario.getText().isEmpty()) {
            ingresarUsuario.setText("Ingrese su nombre de usuario");
            ingresarUsuario.setForeground(Color.gray);
        }
    }//GEN-LAST:event_ingresarContraseniaMousePressed

    private void botonIniciarSesionMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_botonIniciarSesionMouseClicked
        // TODO add your handling code here:
        String usuario = ingresarUsuario.getText();
        String contrasenia = String.valueOf(ingresarContrasenia.getPassword());
        // Validar las credenciales ingresadas
        if (validarCredenciales(usuario, contrasenia)) {
            // Si las credenciales son válidas, obtener el ID del empleado
            int idEmpleado = obtenerIdUsuario(usuario, contrasenia);
            // Verificar si se pudo obtener el ID del empleado
            if (idEmpleado != 0) {
                try {
                    // Crear la ventana principal del administrador con el ID del empleado
                    UIManager.setLookAndFeel(new FlatCyanLightIJTheme());
                    UIManager.put("TextComponent.arc", 100);
                    PrincipalAdministrador p1 = new PrincipalAdministrador(idEmpleado);
                    SwingUtilities.updateComponentTreeUI(p1);
                    p1.setVisible(true);
                    this.dispose(); // Cerrar la ventana de login
                } catch (UnsupportedLookAndFeelException ex) {
                    Logger.getLogger(LoginAdmin.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                // Manejar el caso en que no se pueda obtener el ID del empleado
                javax.swing.JOptionPane.showMessageDialog(this, "No se pudo obtener el ID del empleado.", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
            }
        } else {
            // Manejar el caso en que las credenciales no sean válidas
            javax.swing.JOptionPane.showMessageDialog(this, "Usuario o contraseña incorrectos", "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_botonIniciarSesionMouseClicked

    private void ingresarUsuarioMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ingresarUsuarioMousePressed
        // TODO add your handling code here:
        if (ingresarUsuario.getText().equals("Ingrese su nombre de usuario")) {
            ingresarUsuario.setText("");
            ingresarUsuario.setForeground(Color.black);
        }
        if (String.valueOf(ingresarContrasenia.getPassword()).isEmpty()) {
            ingresarContrasenia.setText("********");
            ingresarContrasenia.setForeground(Color.gray);
        }

    }//GEN-LAST:event_ingresarUsuarioMousePressed

    private void botonIniciarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonIniciarSesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_botonIniciarSesionActionPerformed

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
            java.util.logging.Logger.getLogger(LoginAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(LoginAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(LoginAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(LoginAdmin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new LoginAdmin().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonIniciarSesion;
    private javax.swing.JPasswordField ingresarContrasenia;
    private javax.swing.JTextField ingresarUsuario;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel labelContrasenia;
    private javax.swing.JLabel labelFondoLogin;
    private javax.swing.JLabel labelIniciarSesion;
    private javax.swing.JLabel labelUsuario;
    private javax.swing.JLabel llave;
    private javax.swing.JLabel userIcono;
    // End of variables declaration//GEN-END:variables
}
