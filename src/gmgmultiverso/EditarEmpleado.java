/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.EmpleadoDao;
import gmgmultiverso.model.Empleado;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import propiedades.EvObjOverEtiquetav2;
import propiedades.LisOverEtiquetav2;

/**
 *
 * @author geany
 */
public class EditarEmpleado extends javax.swing.JPanel {

    
    PrincipalAdministrador principalAdmin;
    BuscarEmpleado buscarEmpledo;
    private int codEmpleado;
    
    private ArrayList<String> listaCamposObligPorCompletar = new ArrayList<>();
    private String camposObligPorCompletar;
    
    /**
     * Creates new form EditarEmpleado
     */
    public EditarEmpleado(int codEmpleado, BuscarEmpleado buscarEmpledo) {
        initComponents();
        this.principalAdmin = buscarEmpledo.getPrincipalAdmin();
        this.codEmpleado = codEmpleado;
        this.setSize(1091, 642);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        //no se vea el label de volver
        labelVolver.setVisible(false);
        
        // Convertir componenteContrasenia a campo de contraseña
        componenteContrasenia.convertirAContrasena();
        
        visorErrores.setEditable(false);
        cargarInformacionEmpleado(codEmpleado);
        actualizarTextAreaVisorErrores();
        
        //-------------visor errores--------------------------
    
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
                    System.out.println("No se ha producido ningun error, textField Nombre con contenido");
                    System.out.println("Nombre introducido : " + textoAnadir);
                    
                }
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonModificar();
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
                    System.out.println("No se ha producido ningun error, textField Apellido con contenido");
                    System.out.println("Apellido introducido : " + textoAnadir);
                    
                }
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonModificar();
            }
        };
        componenteApellido.addLisOverEtiquetav2(li2);
        
        //--------componenteContrasenia--------
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
                    
                    //String llenoNombre = vacioNombre.setText();
                    System.out.println("No se ha producido ningun error, textField Contrasenia con contenido");
                    System.out.println("Contrasenia introducido : " + textoAnadir);
                    
                }
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonModificar ();
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

                    System.out.println("Telefono sin completar");
                }
                else
                {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componenteTelefono.getEtiqueta());
                    
                    System.out.println("No se ha producido ningun error, textField Contacto con contenido");
                    System.out.println("Telefono introducido : " + textoAnadir);
                }
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonModificar();
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
                    
                    System.out.println("No se ha producido ningun error, textField Email con contenido");
                    System.out.println("Email introducido : " + textoAnadir);
                }
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonModificar();
            }
        };
        componenteEmail.addLisOverEtiquetav2(li5);
    }
    
    //------------------------------MÉTODOS-------------------------------
    
//----------------para el textArea vosirErrores----------------
    
    public void actualizarTextAreaVisorErrores() 
    {
        // Sirve para que no se me dupliquen los campos que yo añada al textarea visorErrores
        listaCamposObligPorCompletar = (ArrayList<String>) listaCamposObligPorCompletar.stream().distinct().collect(Collectors.toList());

        camposObligPorCompletar = String.join(", \n", listaCamposObligPorCompletar);

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
    
//-------------------Habilitar / Deshabilitar botón modificar-------------------------------------------
    public boolean hayCamposObligatoriosVacios() 
    {
        return !listaCamposObligPorCompletar.isEmpty();
    }

    // Método para actualizar el estado del botónModificar
    public void actualizarEstadoBotonModificar() 
    {
        botonModificar.setEnabled(!hayCamposObligatoriosVacios());
    }
    
    //------------------carga la info del empleado seleccionado------------------------
    
    public void cargarInformacionEmpleado(int codEmpleado) 
    {
        ManagerConexion managerConexion = new ManagerConexion();
        EmpleadoDao empleadoDao = new EmpleadoDao(managerConexion);
        List<Empleado> empleados = empleadoDao.list();

        // Buscar el empleado con el código proporcionado
        Empleado empleadoSeleccionado = null;
        for (Empleado empleado : empleados) {
            if (empleado.getId() == codEmpleado) {
                empleadoSeleccionado = empleado;
                break;
            }
        }

        if (empleadoSeleccionado != null) {
            componenteNombre.setEscritura(empleadoSeleccionado.getNombre());
            componenteApellido.setEscritura(empleadoSeleccionado.getApellido());
            componenteContrasenia.setEscritura(empleadoSeleccionado.getContrasenia());
            componenteTelefono.setEscritura(String.valueOf(empleadoSeleccionado.getTelefono()));
            componenteEmail.setEscritura(empleadoSeleccionado.getEmail());
        } else {
            JOptionPane.showMessageDialog(this, "El empleado con el ID " + codEmpleado + " no existe.");
        }
    }
    
    public void modificar(){
     
        String nuevoNombre = componenteNombre.getEscritura();
        String nuevoApellido = componenteApellido.getEscritura();
        String nuevaContrasenia = componenteContrasenia.getEscritura();
        String nuevoTelefono = componenteTelefono.getEscritura();
        String nuevoEmail = componenteEmail.getEscritura();

        // Variables para verificar la validez del teléfono y el email
        boolean telefonoValido = true;
        boolean emailValido = true;

        // Verificar si el teléfono es un número válido
        int telefono = 0;
        try {
            telefono = Integer.parseInt(nuevoTelefono);
            if (String.valueOf(telefono).length() != 9) {
                telefonoValido = false;
            }
        } catch (NumberFormatException e) {
            telefonoValido = false;
        }

        // Verificar si el email contiene un '@'
        if (!nuevoEmail.contains("@")) {
            emailValido = false;
        }

        // Si alguna de las validaciones falla, mostrar el mensaje de error correspondiente
        if (nuevoNombre.matches(".*\\d.*") || nuevoApellido.matches(".*\\d.*") || !telefonoValido || !emailValido) {
            String mensajeError = "";
            if (nuevoNombre.matches(".*\\d.*")) {
                mensajeError += "- El nombre no puede contener números.\n";
            }
            if (nuevoApellido.matches(".*\\d.*")) {
                mensajeError += "- El apellido no puede contener números.\n";
            }
            if (!telefonoValido) {
                mensajeError += "- El teléfono debe ser un número válido.\n";
            }
            if (!emailValido) {
                mensajeError += "- El email debe ser válido.\n";
            }
            JOptionPane.showMessageDialog(this, mensajeError, "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Obtener el ID del empleado
        int idEmpleado = codEmpleado;

        // Crear una instancia de EmpleadoDao
        ManagerConexion managerConexion = new ManagerConexion();
        EmpleadoDao empleadoDao = new EmpleadoDao(managerConexion);

        // Verificar si el nuevo número de teléfono ya existe para otro empleado
        if (empleadoDao.telefonoExisteParaOtrosEmpleados(Integer.parseInt(nuevoTelefono), idEmpleado)) {
            JOptionPane.showMessageDialog(this, "No se puede modificar el empleado, el número de teléfono ya está en uso.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear una instancia de Empleado con los nuevos valores
        Empleado empleadoModificado = new Empleado(idEmpleado, nuevoNombre, nuevoApellido, nuevaContrasenia, Integer.parseInt(nuevoTelefono), nuevoEmail);

        // Intentar actualizar el empleado en la base de datos
        boolean exito = empleadoDao.actualizarEmpleado(empleadoModificado);

        // Verificar si la actualización fue exitosa
        if (exito) {
            JOptionPane.showMessageDialog(this, "Empleado modificado correctamente.");
            BuscarEmpleado be = new BuscarEmpleado(principalAdmin);
            principalAdmin.mostrarPanel(be);
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar el empleado.");
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

        labelTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        visorErrores = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        botonModificar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        try {
            componenteNombre = new propiedades.EtiquetaVertHorizv2();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componenteApellido = new propiedades.EtiquetaVertHorizv2();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componenteContrasenia = new propiedades.EtiquetaVertHorizv2();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componenteTelefono = new propiedades.EtiquetaVertHorizv2();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componenteEmail = new propiedades.EtiquetaVertHorizv2();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        labelVolver = new javax.swing.JLabel();

        labelTitulo.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        labelTitulo.setText("Editar un empleado");

        visorErrores.setColumns(20);
        visorErrores.setRows(5);
        jScrollPane1.setViewportView(visorErrores);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        botonModificar.setText("Modificar");
        botonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarActionPerformed(evt);
            }
        });
        jPanel1.add(botonModificar);

        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(botonCancelar);

        componenteNombre.setEtiqueta("Nombre :");
        componenteNombre.setObligatorio(true);

        componenteApellido.setEtiqueta("Apellido :");
        componenteApellido.setObligatorio(true);

        componenteContrasenia.setEtiqueta("Clave :");
        componenteContrasenia.setObligatorio(true);

        componenteTelefono.setEtiqueta("Teléfono :");
        componenteTelefono.setObligatorio(true);

        componenteEmail.setEtiqueta("Email :");
        componenteEmail.setObligatorio(true);

        labelVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/flechaIzquierda.png"))); // NOI18N
        labelVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelVolverMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelVolver)
                .addGap(392, 392, 392)
                .addComponent(labelTitulo)
                .addContainerGap(418, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(componenteNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
                    .addComponent(componenteApellido, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(componenteContrasenia, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(componenteTelefono, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(componenteEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 381, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(31, 31, 31))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelVolver)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelTitulo)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(componenteNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(componenteApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(componenteContrasenia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(componenteTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(componenteEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(76, 76, 76)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(155, 155, 155)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(211, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarActionPerformed
        // TODO add your handling code here:
        modificar();
    }//GEN-LAST:event_botonModificarActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed

        int opcion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea volver a la ventana anterior?, no se realizarán cambios", "Confirmar acción", JOptionPane.YES_NO_OPTION);

        // Verificar la opción seleccionada por el usuario
        if (opcion == JOptionPane.YES_OPTION) {
            BuscarEmpleado be = new BuscarEmpleado(principalAdmin);
            principalAdmin.mostrarPanel(be);
        } else {
            // no hace nada
        }
    }//GEN-LAST:event_botonCancelarActionPerformed

    private void labelVolverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelVolverMouseClicked
        // TODO add your handling code here:
        int opcion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea volver a la ventana anterior?, no se realizarán cambios", "Confirmar acción", JOptionPane.YES_NO_OPTION);

        // Verificar la opción seleccionada por el usuario
        if (opcion == JOptionPane.YES_OPTION) {
            BuscarEmpleado be = new BuscarEmpleado(principalAdmin);
            principalAdmin.mostrarPanel(be);
        } else {
            // no hace nada
        }
    }//GEN-LAST:event_labelVolverMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonModificar;
    private propiedades.EtiquetaVertHorizv2 componenteApellido;
    private propiedades.EtiquetaVertHorizv2 componenteContrasenia;
    private propiedades.EtiquetaVertHorizv2 componenteEmail;
    private propiedades.EtiquetaVertHorizv2 componenteNombre;
    private propiedades.EtiquetaVertHorizv2 componenteTelefono;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JLabel labelVolver;
    private javax.swing.JTextArea visorErrores;
    // End of variables declaration//GEN-END:variables
}
