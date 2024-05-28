/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.dao.ProveedorDao;
import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.model.Proveedor;
import javax.swing.JOptionPane;
import java.util.ArrayList;
import propiedades.EvObjOverEtiquetav2;
import propiedades.LisOverEtiquetav2;

/**
 *
 * @author geanina.foanta
 */
public class AnadirProveedor extends javax.swing.JPanel {

    private ArrayList<String> listaCamposObligPorCompletar = new ArrayList<>();
    private String camposObligPorCompletar;
    
    /**
     * Creates new form AnadirProveedor
     */
    public AnadirProveedor() {
        initComponents();
        this.setSize(1091, 642);
        botonAnadir.setEnabled(false);
        visorErrores.setEditable(false);
        
        // Inicializar la lista con los nombres de los campos obligatorios
        listaCamposObligPorCompletar.add(componenteNombreEmpresa.getEtiqueta());
        listaCamposObligPorCompletar.add(componenteTelefono.getEtiqueta());
        
        
        actualizarTextAreaVisorErrores();
        
        //--------componenteNombre--------
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
                    System.out.println("No se ha producido ningun error, textField nombre con contenido");
                    System.out.println("Nombre introducido : " + textoAnadir);
                    
                }
                
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componenteNombreEmpresa.addLisOverEtiquetav2(li);
        
        //--------componenteTelefono--------
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
                    
                    System.out.println("No se ha producido ningun error, textField email con contenido");
                    System.out.println("Email introducido : " + textoAnadir);
                }
                
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componenteEmail.addLisOverEtiquetav2(li3);
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

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        botonAnadir = new javax.swing.JButton();
        botonReiniciar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        visorErrores = new javax.swing.JTextArea();
        try {
            componenteNombreEmpresa = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componenteTelefono = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componenteEmail = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }

        jLabel1.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        jLabel1.setText("Añadir un proveedor");

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

        visorErrores.setColumns(20);
        visorErrores.setRows(5);
        jScrollPane1.setViewportView(visorErrores);

        componenteNombreEmpresa.setEtiqueta("Empresa :");
        componenteNombreEmpresa.setObligatorio(true);

        componenteTelefono.setEtiqueta("Teléfono :");
        componenteTelefono.setObligatorio(true);

        componenteEmail.setEtiqueta("Email :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(496, 496, 496)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(componenteNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(componenteTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(componenteEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(113, 113, 113)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(73, 73, 73)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(99, 99, 99)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(componenteNombreEmpresa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(componenteTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(componenteEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(274, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReiniciarActionPerformed
        // TODO add your handling code here:
        // Obtener el panel padre del componente actual
        java.awt.Container parent = this.getParent();

        // Verificar si el panel padre es un JPanel
        if (parent != null && parent instanceof javax.swing.JPanel) {
            javax.swing.JPanel panelPrincipal = (javax.swing.JPanel) parent;

            // Crear una nueva instancia de AnadirProveedor
            AnadirProveedor nuevoPanel = new AnadirProveedor();

            // Remover el panel actual del panel padre
            panelPrincipal.remove(this);

            // Añadir el nuevo panel AnadirProveedor al panel padre
            panelPrincipal.add(nuevoPanel);

            // Validar y repintar el panel padre para reflejar los cambios
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
        }
        
        /*
        limpiarCampos();
        
        listaCamposObligPorCompletar.clear();
        actualizarTextAreaVisorErrores();
        actualizarEstadoBotonAnadir();
        */
    }//GEN-LAST:event_botonReiniciarActionPerformed

    private void botonAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnadirActionPerformed
        // TODO add your handling code here:
        
        // Lógica para añadir el proveedor
        String nombre = componenteNombreEmpresa.getEscritura();
        String telefonoTexto = componenteTelefono.getEscritura();
        String email = componenteEmail.getEscritura();

        
        
        if (!telefonoTexto.matches("\\d{9}") || !email.contains("@")) {
            String mensajeError = "";
            if (!telefonoTexto.matches("\\d{9}")) {
                mensajeError += "El teléfono debe contener 9 dígitos numéricos.\n";
            }
            if (!email.contains("@")) {
                mensajeError += "El email debe ser válido.\n";
            }
            JOptionPane.showMessageDialog(this, mensajeError, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Crear un objeto Proveedor con los datos ingresados
        Proveedor nuevoProveedor = new Proveedor(nombre, Integer.parseInt(telefonoTexto), email);

        // Instanciar ProveedorDao
        ManagerConexion managerConexion = new ManagerConexion();
        ProveedorDao proveedorDao = new ProveedorDao(managerConexion);

        // Añadir el proveedor
        boolean resultado = proveedorDao.anadirProveedor(nuevoProveedor);

        // Mostrar mensaje de éxito o error
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Proveedor añadido exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Reiniciar el formulario
            botonReiniciarActionPerformed(evt);
        } else {
            JOptionPane.showMessageDialog(this, "Error al añadir el proveedor", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_botonAnadirActionPerformed

    public void limpiarCampos(){
        componenteNombreEmpresa.setEscritura("");
        componenteTelefono.setEscritura("");
        componenteEmail.setEscritura("");
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAnadir;
    private javax.swing.JButton botonReiniciar;
    private propiedades.Componente2Anadir componenteEmail;
    private propiedades.Componente2Anadir componenteNombreEmpresa;
    private propiedades.Componente2Anadir componenteTelefono;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea visorErrores;
    // End of variables declaration//GEN-END:variables
}
