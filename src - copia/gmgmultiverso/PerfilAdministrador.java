/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import java.awt.Desktop;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;
import javax.swing.JOptionPane;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author geanina.foanta
 */
public class PerfilAdministrador extends javax.swing.JPanel {

    private String nombreEmpresa;
    private String correo;
    private String web;
    private String telefono;
    private String direccion;
    private String instagram;
    
    private boolean cambiosRealizados = false;
    
    private String nombreUsuario;
    
    /**
     * Creates new form PerfilAdministrador
     */
    public PerfilAdministrador(String nombreUsuario) {
        initComponents();
        this.nombreUsuario = nombreUsuario;
        this.setSize(1091, 642);
        cargarDatos();
        
        textFieldWeb.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirPagina("https://gmgmultiverso.wordpress.com/");
            }
        });
        
        textFieldInstagram.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                abrirPagina("https://www.instagram.com/gmg_multiverso?igsh=MWFsZ3ptc2UzYjNuYQ==");
            }
        });
        

    }

    
    
    //--------Para que me lleve a la pagina web---------------------
    
    public void abrirPagina(String url) {
        try {
            Desktop.getDesktop().browse(new URI(url));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    
    //-------getters y setters

    //-----nombre de la empresa
    public String getNombreEmpresa() {
        return textFieldNombreEmpresa.getText();
    }

    public void setNombreEmpresa(String info) {
        textFieldNombreEmpresa.setText(info);
    }

    //-----correo
    public String getCorreo() {
        return textFieldCorreo.getText();
    }

    public void setCorreo(String info) {
        textFieldCorreo.setText(info);
    }

    //-----web
    public String getWeb() {
        return textFieldWeb.getText();
    }

    public void setWeb(String info) {
        textFieldWeb.setText(info);
    }

    //-----telefono
    public String getTelefono() {
        return textFieldTelefono.getText();
    }

    public void setTelefono(String info) {
        textFieldTelefono.setText(info);
    }

    //-----direccion
    public String getDireccion() {
        return textFieldDireccion.getText();
    }

    public void setDireccion(String info) {
        textFieldDireccion.setText(info);
    }

    //-----instagram
    public String getInstagram() {
        return textFieldInstagram.getText();
    }

    public void setInstagram(String info) {
        textFieldInstagram.setText(info);
    }
    
    //----------Para guardar cambios
    
    public void cargarDatos() {
        File archivo = new File("src/archivosGenerados/configPerfilAdmin.txt");
        if (!archivo.exists()) {
            return; // Si el archivo no existe, no hacer nada
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(archivo))) {
            StringBuilder ultimoCambio = new StringBuilder();
            String separador = "------------------";

            String linea;
            while ((linea = reader.readLine()) != null) {
                if (linea.equals(separador)) {
                    procesarCambios(ultimoCambio.toString());
                    ultimoCambio.setLength(0);
                } else {
                    ultimoCambio.append(linea).append("\n");
                }
            }
            if (ultimoCambio.length() > 0) {
                procesarCambios(ultimoCambio.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar los cambios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void procesarCambios(String cambios) {
        String[] lines = cambios.split("\n");
        for (String line : lines) {
            if (line.contains("=")) {
                String[] parts = line.split(" = ", 2);
                switch (parts[0]) {
                    case "nombreEmpresa":
                        setNombreEmpresa(parts[1]);
                        break;
                    case "correo":
                        setCorreo(parts[1]);
                        break;
                    case "web":
                        setWeb(parts[1]);
                        break;
                    case "telefono":
                        setTelefono(parts[1]);
                        break;
                    case "direccion":
                        setDireccion(parts[1]);
                        break;
                    case "instagram":
                        setInstagram(parts[1]);
                        break;
                }
            }
        }
    }

    public void guardarCambios() {
        String camposVacios = camposVacios();
        if (camposVacios.isEmpty()) {
            try {
                File archivo = new File("src/archivosGenerados/configPerfilAdmin.txt");

                if (!archivo.getParentFile().exists()) {
                    archivo.getParentFile().mkdirs();
                }
                if (!archivo.exists()) {
                    archivo.createNewFile();
                }

                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
                Date date = new Date();

                try (FileWriter writer = new FileWriter(archivo, true)) {
                    writer.write("Fecha y hora del cambio: " + dateFormat.format(date) + "\n");
                    writer.write("Usuario que hizo el cambio: " + nombreUsuario + "\n");
                    writer.write("nombreEmpresa = " + getNombreEmpresa() + "\n");
                    writer.write("correo = " + getCorreo() + "\n");
                    writer.write("web = " + getWeb() + "\n");
                    writer.write("telefono = " + getTelefono() + "\n");
                    writer.write("direccion = " + getDireccion() + "\n");
                    writer.write("instagram = " + getInstagram() + "\n");
                    writer.write("\n");
                    writer.write("------------------\n");
                    writer.write("\n");
                }

                JOptionPane.showMessageDialog(this, "Cambios guardados correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
                cambiosRealizados = false;
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al guardar los cambios: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Uno o más campos están vacíos:\n" + camposVacios, "Campos vacíos", JOptionPane.WARNING_MESSAGE);
        }
    }

    public String camposVacios() {
        StringBuilder camposVacios = new StringBuilder();
        if (getNombreEmpresa().isEmpty()) {
            camposVacios.append("- Nombre de la empresa\n");
        }
        if (getCorreo().isEmpty()) {
            camposVacios.append("- Correo\n");
        }
        if (getWeb().isEmpty()) {
            camposVacios.append("- Web\n");
        }
        if (getTelefono().isEmpty()) {
            camposVacios.append("- Teléfono\n");
        }
        if (getDireccion().isEmpty()) {
            camposVacios.append("- Dirección\n");
        }
        if (getInstagram().isEmpty()) {
            camposVacios.append("- Instagram\n");
        }
        return camposVacios.toString();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        textFieldNombreEmpresa = new javax.swing.JTextField();
        textFieldCorreo = new javax.swing.JTextField();
        textFieldWeb = new javax.swing.JTextField();
        textFieldTelefono = new javax.swing.JTextField();
        textFieldDireccion = new javax.swing.JTextField();
        textFieldInstagram = new javax.swing.JTextField();
        botonGuardarCambios = new javax.swing.JButton();

        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel1.setText("Perfil del administrador");

        jLabel2.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel2.setText("Administradoras");
        jLabel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/monica.png"))); // NOI18N
        jLabel3.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/imagenes/planeta.png")))); // NOI18N

        jLabel4.setFont(new java.awt.Font("Times New Roman", 3, 18)); // NOI18N
        jLabel4.setText("Empresa");
        jLabel4.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel5.setFont(new java.awt.Font("Century Schoolbook", 3, 14)); // NOI18N
        jLabel5.setText("Mónica Salinas");

        jLabel6.setFont(new java.awt.Font("Century Schoolbook", 3, 14)); // NOI18N
        jLabel6.setText("Gema Castellano");

        jLabel7.setFont(new java.awt.Font("Century Schoolbook", 3, 14)); // NOI18N
        jLabel7.setText("Geanina Foanta");

        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/gema.png"))); // NOI18N
        jLabel8.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/imagenes/planeta.png")))); // NOI18N

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/geanina.png"))); // NOI18N
        jLabel9.setBorder(new javax.swing.border.MatteBorder(new javax.swing.ImageIcon(getClass().getResource("/imagenes/planeta.png")))); // NOI18N

        jLabel10.setFont(new java.awt.Font("Century Schoolbook", 3, 14)); // NOI18N
        jLabel10.setText("Nombre empresa : ");

        jLabel12.setFont(new java.awt.Font("Century Schoolbook", 3, 14)); // NOI18N
        jLabel12.setText("Correo : ");

        jLabel13.setFont(new java.awt.Font("Century Schoolbook", 3, 14)); // NOI18N
        jLabel13.setText("Web : ");

        jLabel14.setFont(new java.awt.Font("Century Schoolbook", 3, 14)); // NOI18N
        jLabel14.setText("Teléfono : ");

        jLabel15.setFont(new java.awt.Font("Century Schoolbook", 3, 14)); // NOI18N
        jLabel15.setText("Instagram : ");

        jLabel16.setFont(new java.awt.Font("Century Schoolbook", 3, 14)); // NOI18N
        jLabel16.setText("Dirección : ");

        textFieldNombreEmpresa.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        textFieldNombreEmpresa.setText("Gmg Multiverso");
        textFieldNombreEmpresa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldNombreEmpresaActionPerformed(evt);
            }
        });

        textFieldCorreo.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        textFieldCorreo.setText("gmgmultiverso@gmail.com");
        textFieldCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldCorreoActionPerformed(evt);
            }
        });

        textFieldWeb.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        textFieldWeb.setForeground(new java.awt.Color(51, 204, 255));
        textFieldWeb.setText("https://gmgmultiverso.wordpress.com/");
        textFieldWeb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        textFieldTelefono.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        textFieldTelefono.setText("695319038");
        textFieldTelefono.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldTelefonoActionPerformed(evt);
            }
        });

        textFieldDireccion.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        textFieldDireccion.setText("C/ Secoya, 22, Madrid ");
        textFieldDireccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldDireccionActionPerformed(evt);
            }
        });

        textFieldInstagram.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        textFieldInstagram.setForeground(new java.awt.Color(0, 218, 204));
        textFieldInstagram.setText("@gmg_multiverso");
        textFieldInstagram.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        textFieldInstagram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                textFieldInstagramActionPerformed(evt);
            }
        });

        botonGuardarCambios.setText("Guardar cambios");
        botonGuardarCambios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGuardarCambiosActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(425, 425, 425)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(89, 89, 89)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(54, 54, 54)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel13)
                                                .addGap(18, 18, 18)
                                                .addComponent(textFieldWeb))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel12)
                                                .addGap(18, 18, 18)
                                                .addComponent(textFieldCorreo))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel10)
                                                .addGap(18, 18, 18)
                                                .addComponent(textFieldNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(176, 176, 176)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel14)
                                                    .addComponent(jLabel16))
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(textFieldDireccion, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
                                                    .addComponent(textFieldTelefono)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jLabel15)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(textFieldInstagram)))))
                                .addGap(120, 120, 120)
                                .addComponent(botonGuardarCambios))
                            .addComponent(jLabel2))))
                .addGap(108, 108, 108))
            .addGroup(layout.createSequentialGroup()
                .addGap(129, 129, 129)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addComponent(jLabel6))
                    .addComponent(jLabel8))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(143, 143, 143)
                        .addComponent(jLabel5)
                        .addGap(223, 223, 223)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(86, 86, 86)
                        .addComponent(jLabel3)
                        .addGap(93, 93, 93)
                        .addComponent(jLabel9)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel5))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel6)))
                .addGap(27, 27, 27)
                .addComponent(jLabel4)
                .addGap(39, 39, 39)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(jLabel14)
                    .addComponent(textFieldNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel12)
                            .addComponent(jLabel16)
                            .addComponent(textFieldCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textFieldDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(botonGuardarCambios)
                        .addGap(20, 20, 20)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15)
                    .addComponent(textFieldWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldInstagram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(62, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void textFieldNombreEmpresaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldNombreEmpresaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldNombreEmpresaActionPerformed

    private void textFieldCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldCorreoActionPerformed

    private void textFieldTelefonoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldTelefonoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldTelefonoActionPerformed

    private void textFieldDireccionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldDireccionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldDireccionActionPerformed

    private void textFieldInstagramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textFieldInstagramActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_textFieldInstagramActionPerformed

    private void botonGuardarCambiosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGuardarCambiosActionPerformed
        // TODO add your handling code here:
        guardarCambios();
    }//GEN-LAST:event_botonGuardarCambiosActionPerformed

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
    
    }//GEN-LAST:event_formFocusLost


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonGuardarCambios;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField textFieldCorreo;
    private javax.swing.JTextField textFieldDireccion;
    private javax.swing.JTextField textFieldInstagram;
    private javax.swing.JTextField textFieldNombreEmpresa;
    private javax.swing.JTextField textFieldTelefono;
    private javax.swing.JTextField textFieldWeb;
    // End of variables declaration//GEN-END:variables
}
