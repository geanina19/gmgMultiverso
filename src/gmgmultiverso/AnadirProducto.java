/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.ProductoConProveedorDao;
import gmgmultiverso.model.ProductoConProveedor;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import propiedades.EvObjOverEtiquetav2;
import propiedades.LisOverEtiquetav2;

/**
 *
 * @author geanina.foanta
 */
public class AnadirProducto extends javax.swing.JPanel {

    private ArrayList<String> listaCamposObligPorCompletar = new ArrayList<>();
    private String camposObligPorCompletar;
    ManagerConexion managerConexion = new ManagerConexion();
    Connection conet;
    Statement st;
    ResultSet rs;
    PrincipalAdministrador principalAdmin;
    

    private int codProveedor = -1;
    
    /**
     * Creates new form AnadirProducto
     */
    public AnadirProducto(PrincipalAdministrador principalAdmin) {
        initComponents();
        this.principalAdmin = principalAdmin;
        this.setSize(1091, 642);
        botonAnadir.setEnabled(false);
        visorErrores.setEditable(false);
        cargarProveedores();
        
        // Inicializar la lista con los nombres de los campos obligatorios
        listaCamposObligPorCompletar.add(componenteProveedores.getEtiqueta());
        listaCamposObligPorCompletar.add(componenteNombre.getEtiqueta());
        listaCamposObligPorCompletar.add(componentePrecio.getEtiqueta());
        listaCamposObligPorCompletar.add(componenteUnidadExistente.getEtiqueta());
        
        actualizarTextAreaVisorErrores();
        
        //-------------visor errores--------------------------
        //--------componenteNombre--------
        LisOverEtiquetav2 li = new LisOverEtiquetav2() {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) {
                if (error) {
                    visorErrores.append(componenteNombre.getEtiqueta());

                    // Si el campo está vacío, agrega el nombre del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) {
                        listaCamposObligPorCompletar.add(componenteNombre.getEtiqueta());
                    }

                    System.out.println("Nombre sin completar");
                } else {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componenteNombre.getEtiqueta());

                    // Verificar si el nombre contiene números
                    if (textoAnadir.matches(".*\\d.*")) {
                        JOptionPane.showMessageDialog(null, "El nombre del producto no puede contener números.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    System.out.println("No se ha producido ningún error, textField nombre con contenido");
                    System.out.println("Nombre introducido : " + textoAnadir);
                }

                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componenteNombre.addLisOverEtiquetav2(li);

        
        //--------componentePrecio--------
        LisOverEtiquetav2 li2 = new LisOverEtiquetav2() {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) {
                if (error) {
                    visorErrores.append(componentePrecio.getEtiqueta());
                    // Si el campo está vacío, agrega el Precio del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) {
                        listaCamposObligPorCompletar.add(componentePrecio.getEtiqueta());
                    }

                    System.out.println("Precio sin completar");
                } else {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componentePrecio.getEtiqueta());

                    // Verificar si el precio contiene caracteres no permitidos
                    if (!textoAnadir.matches("[0-9.,]+")) {
                        JOptionPane.showMessageDialog(null, "El precio solo puede contener números, punto (.) y coma (,).", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    System.out.println("No se ha producido ningún error, textField Precio con contenido");
                    System.out.println("Precio introducido : " + textoAnadir);
                }

                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componentePrecio.addLisOverEtiquetav2(li2);

        
        //--------componenteUnidadExistente--------
        LisOverEtiquetav2 li3 = new LisOverEtiquetav2() {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) {
                if (error) {
                    visorErrores.append(componenteUnidadExistente.getEtiqueta());
                    // Si el campo está vacío, agrega el UnidadExistente del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) {
                        listaCamposObligPorCompletar.add(componenteUnidadExistente.getEtiqueta());
                    }

                    System.out.println("UnidadExistente sin completar");
                } else {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componenteUnidadExistente.getEtiqueta());

                    // Verificar si la unidad existente contiene letras
                    if (textoAnadir.matches(".*[a-zA-Z].*")) {
                        JOptionPane.showMessageDialog(null, "La cantidad existente no puede contener letras.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    System.out.println("No se ha producido ningún error, textField UnidadExistente con contenido");
                    System.out.println("UnidadExistente introducido : " + textoAnadir);
                }

                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componenteUnidadExistente.addLisOverEtiquetav2(li3);
        
        
        // Agregar un listener al componente de proveedores
        componenteProveedores.getResultadosList().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                int indiceSeleccionado = componenteProveedores.obtenerIndiceSeleccionado();
                boolean proveedorSeleccionado = indiceSeleccionado != -1;

                if (proveedorSeleccionado) {
                        
                    listaCamposObligPorCompletar.remove(componenteProveedores.getEtiqueta());
                    System.out.println("No se ha producido ningún error, proveedor seleccionado con contenido");
                        
                } else {
                    visorErrores.append(componenteProveedores.getEtiqueta());
                    listaCamposObligPorCompletar.add(componenteProveedores.getEtiqueta());
                    System.out.println("UnidadExistente sin completar");
                }
                    
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        });
    }
    
    //-
//----------------para el textArea vosirErrores----------------
    
    // Método para actualizar el visor de errores
    public void actualizarTextAreaVisorErrores() {
        // Crear una nueva lista para almacenar los campos vacíos no registrados
        ArrayList<String> nuevosCamposVacios = new ArrayList<>();

        // Iterar sobre la lista de campos vacíos
        for (String campo : listaCamposObligPorCompletar) {
            // Verificar si el campo aún no está en la nueva lista
            if (!nuevosCamposVacios.contains(campo)) {
                nuevosCamposVacios.add(campo);
            }
        }

        // Verificar si el proveedor está seleccionado
        int indiceSeleccionado = componenteProveedores.obtenerIndiceSeleccionado();
        boolean proveedorSeleccionado = indiceSeleccionado != -1;

        // Actualizar la lista original con la nueva lista
        listaCamposObligPorCompletar = nuevosCamposVacios;

        // Crear la cadena de campos vacíos para mostrar en el visor de errores
        camposObligPorCompletar = String.join(", \n", listaCamposObligPorCompletar);

        // Verificar si hay campos obligatorios vacíos
        if (camposObligPorCompletar.isEmpty()) {
            // Verificar si el proveedor está seleccionado
            if (proveedorSeleccionado) {
                visorErrores.setText("Todos los campos están completados.");
            } else {
                visorErrores.setText("Selecciona un proveedor.");
            }
        } else {
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
    
    //-------------Cargar proveedores------------
    public void cargarProveedores() {
        
        String slqPaises = "select nombre_empresa, id from proveedor";
        try{
            conet = managerConexion.abrirConexion();
            st = conet.createStatement();
            rs = st.executeQuery(slqPaises);
            
            componenteProveedores.eliminarSeleccion();
            
            componenteProveedores.rellenarJlist(rs, "nombre_empresa", "id");
            rs.close();
            st.close();
            conet.close();
        } catch(SQLException e) {
           e.printStackTrace();
        }
    }
    
     public PrincipalAdministrador getPrincipalAdmin() {
        return this.principalAdmin;
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
        componenteProveedores = new propiedades.Componente4();
        try {
            componenteNombre = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componentePrecio = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componenteUnidadExistente = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        jScrollPane1 = new javax.swing.JScrollPane();
        visorErrores = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        botonAnadir = new javax.swing.JButton();
        botonReiniciar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        botonAnadirProveedor = new javax.swing.JButton();

        labelTitulo.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        labelTitulo.setText("Añadir un producto");

        componenteProveedores.setEtiqueta("Proveedor  :");
        componenteProveedores.setMensaje("\"Selecciona un proveedor\"");
        componenteProveedores.setPrimerElementoEsMensaje(true);

        componenteNombre.setEtiqueta("Nombre :");
        componenteNombre.setObligatorio(true);

        componentePrecio.setEtiqueta("Precio :");
        componentePrecio.setObligatorio(true);

        componenteUnidadExistente.setEtiqueta("Stock :");
        componenteUnidadExistente.setObligatorio(true);

        visorErrores.setColumns(20);
        visorErrores.setRows(5);
        jScrollPane1.setViewportView(visorErrores);

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

        jLabel1.setText("¿ No está el proveedor ? : ");

        botonAnadirProveedor.setText("Añadir proveedor");
        botonAnadirProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnadirProveedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(444, 444, 444)
                        .addComponent(labelTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(componenteProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(componenteNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(componentePrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71)
                        .addComponent(componenteUnidadExistente, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonAnadirProveedor))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(232, 232, 232)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelTitulo)
                        .addGap(33, 33, 33)
                        .addComponent(componenteProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(componenteNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(componenteUnidadExistente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(componentePrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(194, 194, 194))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(botonAnadirProveedor))
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnadirActionPerformed
        // TODO add your handling code here:
        
        // Verificar si todos los campos están seleccionados
        if (componenteProveedores.obtenerIndiceSeleccionado() == -1 ||
            componenteNombre.getEscritura().isEmpty() ||
            componentePrecio.getEscritura().isEmpty() ||
            componenteUnidadExistente.getEscritura().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos para añadir el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si algún campo no está seleccionado
        }

        // Variables para almacenar los errores
        StringBuilder errores = new StringBuilder();

        // Lógica para añadir el producto
        int nuevoCodigoProveedor = componenteProveedores.obtenerCodigoSeleccionado(componenteProveedores.obtenerIndiceSeleccionado());
        String nombre = componenteNombre.getEscritura();
        String precioTexto = componentePrecio.getEscritura().replace(',', '.'); // Reemplazar "," por "."
        String unidadExistenteTexto = componenteUnidadExistente.getEscritura();

        // Validar que el nombre solo contenga caracteres
        if (!nombre.matches("[a-zA-Z]+")) {
            errores.append("- El nombre del producto solo puede contener letras.\n");
        }

        // Validar que el precio sea un número válido
        if (!precioTexto.matches("\\d*\\.?\\d+")) {
            errores.append("- El precio del producto debe ser un número válido.\n");
        }

        // Validar que la cantidad existente sea un número entero
        if (!unidadExistenteTexto.matches("\\d+")) {
            errores.append("- La cantidad existente debe ser un número entero.\n");
        }

        // Si hay errores, mostrarlos en un mensaje
        if (errores.length() > 0) {
            JOptionPane.showMessageDialog(this, "Se encontraron los siguientes errores:\n" + errores.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si hay errores
        }

        // Convertir los datos validados
        double precio = Double.parseDouble(precioTexto);
        int unidadExistente = Integer.parseInt(unidadExistenteTexto);

        // Crear un objeto ProductoConProveedor con los datos ingresados
        ProductoConProveedor nuevoProducto = new ProductoConProveedor(
            nuevoCodigoProveedor, 
            null, // El nombre del proveedor no es necesario aquí
            nombre, 
            precio, 
            unidadExistente
        );

        // Instanciar ProductoConProveedorDao
        ManagerConexion managerConexion = new ManagerConexion();
        ProductoConProveedorDao productoDao = new ProductoConProveedorDao(managerConexion);

        // Añadir el producto
        boolean resultado = productoDao.añadirProducto(nuevoProducto);

        // Mostrar mensaje de éxito o error
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Producto añadido exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            // Reiniciar el formulario
            botonReiniciarActionPerformed(evt);
        } else {
            JOptionPane.showMessageDialog(this, "Error al añadir el producto", "Error", JOptionPane.ERROR_MESSAGE);
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
            AnadirProducto nuevoPanel = new AnadirProducto(principalAdmin);

            // Remover el panel actual del panel padre
            panelPrincipal.remove(this);

            // Añadir el nuevo panel AnadirProveedor al panel padre
            panelPrincipal.add(nuevoPanel);

            // Validar y repintar el panel padre para reflejar los cambios
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
        }

    }//GEN-LAST:event_botonReiniciarActionPerformed

    private void botonAnadirProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnadirProveedorActionPerformed
        
        // Crear una instancia de AnadirProductoConAnadirProveedor
        AnadirProductoConAnadirProveedor apcap = new AnadirProductoConAnadirProveedor(principalAdmin);

        // Obtener el panelPrincipal de PrincipalAdministrador y agregar apcap
        principalAdmin.getPanelPrincipal().removeAll();
        principalAdmin.getPanelPrincipal().add(apcap);
        principalAdmin.getPanelPrincipal().revalidate();
        principalAdmin.getPanelPrincipal().repaint();
        
    }//GEN-LAST:event_botonAnadirProveedorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAnadir;
    private javax.swing.JButton botonAnadirProveedor;
    private javax.swing.JButton botonReiniciar;
    private propiedades.Componente2Anadir componenteNombre;
    private propiedades.Componente2Anadir componentePrecio;
    private propiedades.Componente4 componenteProveedores;
    private propiedades.Componente2Anadir componenteUnidadExistente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JTextArea visorErrores;
    // End of variables declaration//GEN-END:variables
}
