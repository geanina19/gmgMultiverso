/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.ProveedorDao;
import gmgmultiverso.model.Proveedor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import propiedades.EvObjOverEtiquetav2;
import propiedades.LisOverEtiquetav2;

/**
 *
 * @author geanina.foanta
 */
public class EditarProveedor extends javax.swing.JPanel {

    PrincipalAdministrador principalAdmin;
    BuscarProveedor buscarproveedor;
    private int codProveedor;
    
    private ArrayList<String> listaCamposObligPorCompletar = new ArrayList<>();
    private String camposObligPorCompletar;
    
    /**
     * Creates new form EditarProveedor
     */
    public EditarProveedor(int codProveedor, BuscarProveedor buscarproveedor) {
        initComponents();
        this.principalAdmin = buscarproveedor.getPrincipalAdmin();
        this.codProveedor = codProveedor;
        this.setSize(1091, 642);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        visorErrores.setEditable(false);
        cargarInformacionProveedor(codProveedor);
        actualizarTextAreaVisorErrores();
        
        //-------------visor errores--------------------------
    
        //--------componenteNombreEmpresa--------
        LisOverEtiquetav2 li = new LisOverEtiquetav2() 
        {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) 
            {
                if(error)
                {
                    visorErrores.append(componenteNombreEmpresa.getEtiqueta());

                    // Si el campo está vacío, agrega el nombre del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) 
                    {
                        listaCamposObligPorCompletar.add(componenteNombreEmpresa.getEtiqueta());
                    }

                    System.out.println("Nombre sin completar");
                }
                else
                {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componenteNombreEmpresa.getEtiqueta());
                    
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
        componenteNombreEmpresa.addLisOverEtiquetav2(li);
        
        //--------componenteContacto--------
        LisOverEtiquetav2 li2 = new LisOverEtiquetav2() 
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
        componenteTelefono.addLisOverEtiquetav2(li2);
        
        //--------componenteEmail--------
        LisOverEtiquetav2 li3 = new LisOverEtiquetav2() 
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
        componenteEmail.addLisOverEtiquetav2(li3);
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
    
//------------------carga la info del proveedor seleccionado------------------------
    
    public void cargarInformacionProveedor(int codProveedor) 
    {
        ManagerConexion managerConexion = new ManagerConexion();
        ProveedorDao proveedorDao = new ProveedorDao(managerConexion);
        List<Proveedor> proveedores = proveedorDao.list();

        // Buscar el proveedor con el código proporcionado
        Proveedor proveedorSeleccionado = null;
        for (Proveedor proveedor : proveedores) {
            if (proveedor.getId() == codProveedor) {
                proveedorSeleccionado = proveedor;
                break;
            }
        }

        if (proveedorSeleccionado != null) {
            componenteNombreEmpresa.setEscritura(proveedorSeleccionado.getNombre_empresa());
            componenteTelefono.setEscritura(String.valueOf(proveedorSeleccionado.getTelefono()));
            componenteEmail.setEscritura(proveedorSeleccionado.getEmail());
        } else {
            JOptionPane.showMessageDialog(this, "El proveedor con el ID " + codProveedor + " no existe.");
        }
    }
    
    
    public void modificar(){
         
        String nuevoNombreEmpresa = componenteNombreEmpresa.getEscritura();
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
        if (!telefonoValido || !emailValido) {
            String mensajeError = "Error de entrada:\n";
            if (!telefonoValido) {
                mensajeError += "- El teléfono debe ser un número válido.\n";
            }
            if (!emailValido) {
                mensajeError += "- El email debe ser válido.\n";
            }
            JOptionPane.showMessageDialog(this, mensajeError, "Error de entrada", JOptionPane.ERROR_MESSAGE);
            return;
        }


        // Obtener el ID del proveedor
        int idProveedor = codProveedor;

        // Crear una instancia de Proveedor con los nuevos valores
        Proveedor proveedorModificado = new Proveedor(idProveedor, nuevoNombreEmpresa, Integer.parseInt(nuevoTelefono), nuevoEmail);

        // Crear una instancia de ProveedorDao
        ManagerConexion managerConexion = new ManagerConexion();
        ProveedorDao proveedorDao = new ProveedorDao(managerConexion);

        // Intentar actualizar el proveedor en la base de datos
        boolean exito = proveedorDao.actualizarProveedor(proveedorModificado);

        // Verificar si la actualización fue exitosa
        if (exito) {
            JOptionPane.showMessageDialog(this, "Proveedor modificado correctamente.");
            BuscarProveedor pbp = new BuscarProveedor(principalAdmin);
            principalAdmin.mostrarPanel(pbp);
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar el proveedor.");
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

        jLabel1 = new javax.swing.JLabel();
        labelVolver = new javax.swing.JLabel();
        try {
            componenteNombreEmpresa = new propiedades.EtiquetaVertHorizv2();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        visorErrores = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        botonModificar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel1.setText("Editar un proveedor");

        labelVolver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/flechaIzquierda.png"))); // NOI18N
        labelVolver.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelVolverMouseClicked(evt);
            }
        });

        componenteNombreEmpresa.setEtiqueta("Empresa :");
        componenteNombreEmpresa.setObligatorio(true);

        componenteTelefono.setEtiqueta("Teléfono :");
        componenteTelefono.setObligatorio(true);

        componenteEmail.setEtiqueta("Email :");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelVolver)
                .addGap(394, 394, 394)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(89, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(componenteTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(componenteNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(componenteEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(87, 87, 87)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 362, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(69, 69, 69)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelVolver))
                    .addComponent(jLabel1))
                .addGap(55, 55, 55)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(componenteNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(componenteTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(componenteEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(307, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void labelVolverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelVolverMouseClicked
        // TODO add your handling code here:
        int opcion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea volver a la ventana anterior?, no se realizarán cambios", "Confirmar acción", JOptionPane.YES_NO_OPTION);

        // Verificar la opción seleccionada por el usuario
        if (opcion == JOptionPane.YES_OPTION) {
            BuscarProveedor pbp = new BuscarProveedor(principalAdmin);
            principalAdmin.mostrarPanel(pbp);
        } else {
            // no hace nada
        }
    }//GEN-LAST:event_labelVolverMouseClicked

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
    
        int opcion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea volver a la ventana anterior?, no se realizarán cambios", "Confirmar acción", JOptionPane.YES_NO_OPTION);

        // Verificar la opción seleccionada por el usuario
        if (opcion == JOptionPane.YES_OPTION) {
            BuscarProveedor pbp = new BuscarProveedor(principalAdmin);
            principalAdmin.mostrarPanel(pbp);
        } else {
            // no hace nada
        }
    }//GEN-LAST:event_botonCancelarActionPerformed

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarActionPerformed
        // TODO add your handling code here:
       modificar();
    }//GEN-LAST:event_botonModificarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonModificar;
    private propiedades.EtiquetaVertHorizv2 componenteEmail;
    private propiedades.EtiquetaVertHorizv2 componenteNombreEmpresa;
    private propiedades.EtiquetaVertHorizv2 componenteTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelVolver;
    private javax.swing.JTextArea visorErrores;
    // End of variables declaration//GEN-END:variables
}
