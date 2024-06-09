/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.EmpleadoDao;
import gmgmultiverso.model.Empleado;
import java.awt.Dimension;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import propiedades.EvObjOverEtiquetav2;
import propiedades.LisOverEtiquetav2;

/**
 *
 * @author geanina.foanta
 */
public class AnadirEmpleado extends javax.swing.JPanel {

    private ArrayList<String> listaCamposObligPorCompletar = new ArrayList<>();
    private String camposObligPorCompletar;
    
    /**
     * Creates new form AnadirEmpleado
     */
    public AnadirEmpleado() {
        initComponents();
        this.setSize(1091, 642);
        botonAnadir.setEnabled(false);
        visorErrores.setEditable(false);
        
        // Convertir componenteContrasenia a campo de contraseña
        componenteContrasenia.convertirAContrasena();

        
        // Inicializar la lista con los nombres de los campos obligatorios
        listaCamposObligPorCompletar.add(componenteNombre.getEtiqueta());
        listaCamposObligPorCompletar.add(componenteApellido.getEtiqueta());
        listaCamposObligPorCompletar.add(componenteContrasenia.getEtiqueta());
        listaCamposObligPorCompletar.add(componenteTelefono.getEtiqueta());
        listaCamposObligPorCompletar.add(componenteEmail.getEtiqueta());
        
        actualizarTextAreaVisorErrores();
        
        //--------componenteNombre--------
        LisOverEtiquetav2 li = new LisOverEtiquetav2() 
        {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) 
            {
                if(error)
                {
                    visorErrores.append(componenteNombre.getEtiqueta());

                    // Si el campo está vacío, agrega el nombre del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) 
                    {
                        listaCamposObligPorCompletar.add(componenteNombre.getEtiqueta());
                    }

                    System.out.println("Nombre sin completar");
                }
                else
                {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componenteNombre.getEtiqueta());
                    
                    //String llenoNombre = vacioNombre.setText();
                    System.out.println("No se ha producido ningun error, textField nombre con contenido");
                    System.out.println("Nombre introducido : " + textoAnadir);
                    
                }
                
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componenteNombre.addLisOverEtiquetav2(li);
        
        //--------componenteApellido--------
        LisOverEtiquetav2 li2 = new LisOverEtiquetav2() 
        {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) 
            {
                if(error)
                {
                    visorErrores.append(componenteApellido.getEtiqueta());

                    // Si el campo está vacío, agrega el nombre del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) 
                    {
                        listaCamposObligPorCompletar.add(componenteApellido.getEtiqueta());
                    }

                    System.out.println("Apellido sin completar");
                }
                else
                {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componenteApellido.getEtiqueta());
                    
                    //String llenoNombre = vacioNombre.setText();
                    System.out.println("No se ha producido ningun error, textField apellido con contenido");
                    System.out.println("Apellido introducido : " + textoAnadir);
                    
                }
                
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componenteApellido.addLisOverEtiquetav2(li2);
        
        //--------componenteContraseña--------
        LisOverEtiquetav2 li3 = new LisOverEtiquetav2() 
        {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) 
            {
                if(error)
                {
                    visorErrores.append(componenteContrasenia.getEtiqueta());
                    // Si el campo está vacío, agrega el nombre del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) 
                    {
                        listaCamposObligPorCompletar.add(componenteContrasenia.getEtiqueta());
                    }

                    System.out.println("Contrasenia sin completar");
                }
                else
                {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componenteContrasenia.getEtiqueta());
                    
                    System.out.println("No se ha producido ningun error, textField contrasenia con contenido");
                    System.out.println("Contrasenia introducido : " + textoAnadir);
                }
                
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componenteContrasenia.addLisOverEtiquetav2(li3);
        
        //--------componenteTelefono--------
        LisOverEtiquetav2 li4 = new LisOverEtiquetav2() 
        {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) 
            {
                if(error)
                {
                    visorErrores.append(componenteTelefono.getEtiqueta());
                    // Si el campo está vacío, agrega el nombre del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) 
                    {
                        listaCamposObligPorCompletar.add(componenteTelefono.getEtiqueta());
                    }

                    System.out.println("Contacto sin completar");
                }
                else
                {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componenteTelefono.getEtiqueta());
                    
                    System.out.println("No se ha producido ningun error, textField contacto con contenido");
                    System.out.println("Contacto introducido : " + textoAnadir);
                }
                
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componenteTelefono.addLisOverEtiquetav2(li4);
        
        //--------componenteEmail--------
        LisOverEtiquetav2 li5 = new LisOverEtiquetav2() 
        {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) 
            {
                if(error)
                {
                    visorErrores.append(componenteEmail.getEtiqueta());
                    // Si el campo está vacío, agrega el nombre del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) 
                    {
                        listaCamposObligPorCompletar.add(componenteEmail.getEtiqueta());
                    }
                    
                    System.out.println("Email sin completar");
                }
                else
                {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componenteEmail.getEtiqueta());
                    
                    System.out.println("No se ha producido ningun error, textField email con contenido");
                    System.out.println("Email introducido : " + textoAnadir);
                }
                
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componenteEmail.addLisOverEtiquetav2(li5);
    }

        //------------------------------MÉTODOS-------------------------------
    
//----------------para el textArea vosirErrores----------------
    
    public void actualizarTextAreaVisorErrores()
    {
        // Crear una nueva lista para almacenar los campos vacíos no registrados
        ArrayList<String> nuevosCamposVacios = new ArrayList<>();

        // Iterar sobre la lista de campos vacíos
        for (String campo : listaCamposObligPorCompletar) 
        {
            // Verificar si el campo aún no está en la nueva lista
            if (!nuevosCamposVacios.contains(campo)) 
            {
                nuevosCamposVacios.add(campo);
            }
        }

        // Actualizar la lista original con la nueva lista
        listaCamposObligPorCompletar = nuevosCamposVacios;

        // Crear la cadena de campos vacíos para mostrar en el visorErrores
        camposObligPorCompletar = String.join(", \n", listaCamposObligPorCompletar);

        // Actualizar el visorErrores
        if (camposObligPorCompletar.isEmpty()) 
        {
            visorErrores.setText("Todos los campos están completados.");
        } 
        else 
        {
            visorErrores.setText("Los siguientes campos obligatorios están vacíos: \n" + camposObligPorCompletar);
        }
    }
    
    // Acceder a listaCamposVacios
    public ArrayList<String> getListaCamposVacios() 
    {
        return listaCamposObligPorCompletar;
    }
    
    //-------------------Habilitar / Deshabilitar botón añadir-------------------------------------------
    public boolean hayCamposObligatoriosVacios() 
    {
        return !listaCamposObligPorCompletar.isEmpty();
    }

    // Método para actualizar el estado del botón añadir
    public void actualizarEstadoBotonAnadir() 
    {
        botonAnadir.setEnabled(!hayCamposObligatoriosVacios());
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        visorErrores = new javax.swing.JTextArea();
        try {
            componenteNombre = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componenteApellido = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componenteTelefono = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        jPanel1 = new javax.swing.JPanel();
        botonAnadir = new javax.swing.JButton();
        botonReiniciar = new javax.swing.JButton();
        try {
            componenteEmail = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componenteContrasenia = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        labelTitulo.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        labelTitulo.setText("Añadir un empleado");

        visorErrores.setColumns(20);
        visorErrores.setRows(5);
        jScrollPane1.setViewportView(visorErrores);

        componenteNombre.setEtiqueta("Nombre :");
        componenteNombre.setObligatorio(true);

        componenteApellido.setEtiqueta("Apellido :");
        componenteApellido.setObligatorio(true);

        componenteTelefono.setEtiqueta("Teléfono :");
        componenteTelefono.setObligatorio(true);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        botonAnadir.setText("Añadir");
        botonAnadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnadirActionPerformed(evt);
            }
        });
        jPanel1.add(botonAnadir);

        botonReiniciar.setText("Reiniciar");
        botonReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReiniciarActionPerformed(evt);
            }
        });
        jPanel1.add(botonReiniciar);

        componenteEmail.setEtiqueta("Email :");
        componenteEmail.setObligatorio(true);

        componenteContrasenia.setEtiqueta("Clave :");
        componenteContrasenia.setObligatorio(true);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(componenteApellido, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addComponent(componenteNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addComponent(componenteTelefono, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE)
                    .addComponent(componenteEmail, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(componenteContrasenia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(126, 126, 126)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(53, 53, 53))
            .addGroup(layout.createSequentialGroup()
                .addGap(456, 456, 456)
                .addComponent(labelTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitulo)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(componenteNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(componenteApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(componenteContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(componenteTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(componenteEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(220, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnadirActionPerformed
        // TODO add your handling code here:

        // Lógica para añadir el proveedor
        String nombre = componenteNombre.getEscritura();
        String apellido = componenteApellido.getEscritura();
        String contrasenia = componenteContrasenia.getEscritura();
        String telefonoTexto = componenteTelefono.getEscritura();
        String email = componenteEmail.getEscritura();

        // Verificar si los campos contienen caracteres no deseados o están en el formato incorrecto
        if (nombre.matches(".*\\d.*") || apellido.matches(".*\\d.*") || !telefonoTexto.matches("\\d{9}") || !email.matches("[^@]+@[^@]+\\.[^.]+")) {
            String mensajeError = "";
            if (nombre.matches(".*\\d.*")) {
                mensajeError += "- El nombre no puede contener números.\n";
            }
            if (apellido.matches(".*\\d.*")) {
                mensajeError += "- El apellido no puede contener números.\n";
            }
            if (!telefonoTexto.matches("\\d{9}")) {
                mensajeError += "- El teléfono debe contener 9 dígitos numéricos.\n";
            }
            if (!email.matches("[^@]+@[^@]+\\.[^.]+")) {
                mensajeError += "- El email debe ser válido.\n";
            }

            JOptionPane.showMessageDialog(this, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear un objeto Empleado con los datos ingresados
        Empleado nuevoEmpleado = new Empleado(nombre, apellido, contrasenia, Integer.parseInt(telefonoTexto), email);

        // Instanciar EmpleadoDao
        ManagerConexion managerConexion = new ManagerConexion();
        EmpleadoDao empleadoDao = new EmpleadoDao(managerConexion);

        
        String mensaje = "";

        if (empleadoDao.empleadoExiste(Integer.parseInt(telefonoTexto))) {
            mensaje += "El número de teléfono.\n";
        }

        if (empleadoDao.emailExiste(email)) {
            mensaje += "El email.\n";
        }

        if (!mensaje.isEmpty()) {
            mensaje = "No se puede añadir el empleado. Los siguientes campos ya están en uso:\n\n" + mensaje;
            JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        

        // Añadir el empleado
        boolean resultado = empleadoDao.anadirEmpleado(nuevoEmpleado);

        // Mostrar mensaje de éxito o error
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Empleado añadido exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Reiniciar el formulario
            botonReiniciarActionPerformed(evt);
        } else {
            JOptionPane.showMessageDialog(this, "Error al añadir el empleado", "Error", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_botonAnadirActionPerformed

    private void botonReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReiniciarActionPerformed
        // TODO add your handling code here:
        // Obtener el panel padre del componente actual
        java.awt.Container parent = this.getParent();

        // Verificar si el panel padre es un JPanel
        if (parent != null && parent instanceof javax.swing.JPanel) {
            javax.swing.JPanel panelPrincipal = (javax.swing.JPanel) parent;

            // Crear una nueva instancia de AnadirProveedor
            AnadirEmpleado nuevoPanel = new AnadirEmpleado();

            // Remover el panel actual del panel padre
            panelPrincipal.remove(this);

            // Añadir el nuevo panel AnadirProveedor al panel padre
            panelPrincipal.add(nuevoPanel);

            // Validar y repintar el panel padre para reflejar los cambios
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
        }

    }//GEN-LAST:event_botonReiniciarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAnadir;
    private javax.swing.JButton botonReiniciar;
    private propiedades.Componente2Anadir componenteApellido;
    private propiedades.Componente2Anadir componenteContrasenia;
    private propiedades.Componente2Anadir componenteEmail;
    private propiedades.Componente2Anadir componenteNombre;
    private propiedades.Componente2Anadir componenteTelefono;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JTextArea visorErrores;
    // End of variables declaration//GEN-END:variables
}
