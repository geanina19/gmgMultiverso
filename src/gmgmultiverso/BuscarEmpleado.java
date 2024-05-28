/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.EmpleadoDao;
import gmgmultiverso.model.Empleado;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowSorter.SortKey;
import javax.swing.SwingConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author geanina.foanta
 */
public class BuscarEmpleado extends javax.swing.JPanel {

    private PrincipalAdministrador principalAdmin;
    private MouseListener mouseClickListener = null;
    private MouseListener mouseOrdenarTabla = null;

    ManagerConexion managerConexion = new ManagerConexion();
    Connection conet;
    
    Statement st;
    ResultSet rs;
    
    private int codEmpleado = -1;
    
    Object[] cabecera = new Object[]{"Nombre", "Apellido", "Telefono", "Email"};
    DefaultTableModel miModelo = new DefaultTableModel(null, cabecera);
    
    EmpleadoDao emple = new EmpleadoDao(managerConexion);
    
    /**
     * Creates new form BuscarEmpleado
     */
    public BuscarEmpleado(PrincipalAdministrador principalAdmin) {
        
        initComponents();
        this.principalAdmin = principalAdmin;
        this.setSize(1091, 642);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 20, 30));
        inicializarTabla();
        
        tablaBuscarEmpleado.setModel(miModelo);
        //actualizarTablaBuscarEmpleado();
        
        textFieldNombre.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                //buscarEmpleado(codEmpleado);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                //buscarEmpleado(codEmpleado);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //buscarEmpleado(codEmpleado);
            }
        });
    }
    
    //-------------Métodos-----------------
    
    public PrincipalAdministrador getPrincipalAdmin() {
        return this.principalAdmin;
    }
    
    public void quitarListener()
    {
        tablaBuscarEmpleado.removeMouseListener(mouseClickListener);
        mouseClickListener = null;
    }
    
    public void activarOrdenarColumnas(DefaultTableModel model)
    {
        MouseAdapter mouseOrdenarTabla = null;
        tablaBuscarEmpleado.removeMouseListener(mouseOrdenarTabla);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tablaBuscarEmpleado.setRowSorter(sorter);
        mouseOrdenarTabla = new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent evt) 
            {
                int columnaSeleccionada = tablaBuscarEmpleado.columnAtPoint(evt.getPoint());
                sorter.toggleSortOrder(columnaSeleccionada);
            }
        };
        tablaBuscarEmpleado.addMouseListener(mouseOrdenarTabla);
    }
    
    public void mouseListenerAnadirColumnasExtra() {
        quitarListener();

        mouseClickListener = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {

                int editarColumna = tablaBuscarEmpleado.getColumnCount() - 2;
                int eliminarColumna = tablaBuscarEmpleado.getColumnCount() - 1;

                int column = tablaBuscarEmpleado.columnAtPoint(evt.getPoint());
                int row = tablaBuscarEmpleado.rowAtPoint(evt.getPoint());

                if (row >= 0 && row < tablaBuscarEmpleado.getRowCount() && column >= 0 && column < miModelo.getColumnCount()) {
                    String codigoEmpleadoSeleccionado = "SELECT id FROM empleado WHERE nombre = '" +  tablaBuscarEmpleado.getValueAt(row, 0) + "' "
                                + "AND telefono = '" + tablaBuscarEmpleado.getValueAt(row, 1) + "'";
                    int codigoEmplado = 0;

                    try {

                        conet = managerConexion.abrirConexion();
                        st = conet.createStatement();
                        rs = st.executeQuery(codigoEmpleadoSeleccionado);

                        if (rs.next()) {
                            codigoEmplado = rs.getInt("id");
                        }

                        rs.close();
                        st.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    if (column == editarColumna) {
                        abrirEditarEmpleado(codigoEmplado);
                    } else if (column == eliminarColumna) {
                        confirmarEliminarEmpleado(codigoEmplado);
                    }
                }
            }
        };

        tablaBuscarEmpleado.addMouseListener(mouseClickListener);
    }
    
    //------------------------------Abrir Editar Empleado------------------------------
    
    public void abrirEditarEmpleado(int codEmpleado) 
    {
        principalAdmin.mostrarEditarEmpleado(codEmpleado, this);
    }
    
    //------------Crear objetos imagen------------
    
    public ImageIcon crearImageIcon(String path) 
    {
        URL urlImagen = getClass().getResource(path);
        if (urlImagen != null) 
        {
            ImageIcon imagen = new ImageIcon(urlImagen);
            return new ImageIcon(imagen.getImage().getScaledInstance(23, 23, java.awt.Image.SCALE_SMOOTH));
        } 
        else
        {
            System.err.println("No se pudo encontrar el archivo: " + path);
            return null;
        }
    }
    
    //-----------------------------------------
    public void inicializarTabla() {
        miModelo = new DefaultTableModel(null, new Object[]{"Nombre", "Apellido", "Telefono", "Email", "Editar", "Eliminar"}) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Devolver la clase de la columna, ImageIcon para las columnas de imágenes
                return columnIndex >= 4 ? ImageIcon.class : super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer las columnas de no sean editables
                return column >= 0 ? false : super.isCellEditable(row, column);
            }
        };

        tablaBuscarEmpleado.setModel(miModelo);
        tablaBuscarEmpleado.setRowHeight(30);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tablaBuscarEmpleado.getColumnCount() - 2; i++) {
            tablaBuscarEmpleado.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
    }
    
    /*
    //-------------Actualizar tabla------------
    
    public void actualizarTablaBuscarEmpleado() {
        quitarListener(); // Eliminar cualquier listener previo

        // Limpiar el modelo actual de la tabla
        miModelo.setRowCount(0);

        List<Empleado> empleados = emple.list();
        
        // Crear ImageIcons con las imágenes para los botones
        ImageIcon editarIcon = crearImageIcon("/imagenes/editar.png");
        ImageIcon eliminarIcon = crearImageIcon("/imagenes/eliminar.png");
        
        // Llenar el modelo con los datos de los empleados
        for (Empleado empleado : empleados) {
            Object[] rowData = new Object[]{
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getTelefono(),
                empleado.getEmail(),
                editarIcon,
                eliminarIcon
            };
            miModelo.addRow(rowData);
        }

        // Actualizar la tabla con el nuevo modelo
        activarOrdenarColumnas(miModelo);
        mouseListenerAnadirColumnasExtra();
    }
    */
    //-------------Buscar empleados------------
    /*
    public void buscarEmpleado(int codEmpleado) {
        String nombre = textFieldNombre.getText().trim();
        String apellido = textFieldApellido.getText().trim();

        // Verificar si el nombre o el apellido contienen números
        if (nombre.matches(".*\\d.*") || apellido.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(this, "El nombre y el apellido deben contener solo caracteres", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }


            
        
        // Utilizar EmpleadoDao para buscar empleados por nombre y apellido
        List<Empleado> empleados = emple.buscarEmpleadosPorNombre(nombre);

        // Filtrar la lista de proveedores según el código de proveedor si es diferente de -1
            for (Empleado empleado : empleados) {
                if (codEmpleado != -1) {
                    if (empleado.getId() == codEmpleado) {
                        empleados.add(empleado);
                    }
                } else {
                    // Si el código de proveedor es -1, mostrar todos los proveedores
                    empleados = empleados;
                }
            }
        
        // Limpiar el modelo actual de la tabla
        miModelo.setRowCount(0);

        // Crear ImageIcons con las imágenes
        ImageIcon editarIcon = crearImageIcon("/imagenes/editar.png");
        ImageIcon eliminarIcon = crearImageIcon("/imagenes/eliminar.png");
        
        boolean hayResultados = !empleados.isEmpty();

        
        // Llenar el modelo con los resultados de la búsqueda
        for (Empleado empleado : empleados) {
            Object[] rowData = new Object[]{
                empleado.getNombre(),
                empleado.getApellido(),
                empleado.getTelefono(),
                empleado.getEmail(),
                editarIcon,
                eliminarIcon
            };
            miModelo.addRow(rowData);
        }

        // Actualizar la tabla con el nuevo modelo
        tablaBuscarEmpleado.setModel(miModelo);
        mouseListenerAnadirColumnasExtra();

        // Mostrar un mensaje si no se encontraron resultados
        if (empleados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron empleados con los datos proporcionados.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    */
    
    //------------Metodos para eliminar un empleado------------
    
    public void confirmarEliminarEmpleado(int codEmpleado) {
        String nombre = null;

        // Obtener la lista de empleados
        List<Empleado> empleados = emple.list();

        // Buscar el empleado correspondiente al código proporcionado
        for (Empleado empleado : empleados) {
            if (empleado.getId() == codEmpleado) {
                nombre = empleado.getNombre();
                break; // Detener la búsqueda una vez que se encuentre el empleado
            }
        }

        // Guardar el estado actual de la ordenación de la tabla
        List<? extends SortKey> sortKeys = tablaBuscarEmpleado.getRowSorter().getSortKeys();

        // Desactivar la ordenación de la tabla
        tablaBuscarEmpleado.setAutoCreateRowSorter(false);

        // Mostrar el cuadro de diálogo de confirmación incluyendo el nombre de la empresa
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres eliminar el empleado '" + nombre + "'?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            // Si el usuario presiona "OK", procede con la eliminación
            if (eliminarEmpleado(codEmpleado)) {
                JOptionPane.showMessageDialog(this, "Empleado eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                //actualizarTablaBuscarEmpleado(); // Actualiza la tabla después de eliminar el empleado.
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el empleado.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Eliminación cancelada");
        }

        // Reactivar la ordenación de la tabla utilizando el estado guardado
        tablaBuscarEmpleado.setAutoCreateRowSorter(true);
        tablaBuscarEmpleado.getRowSorter().setSortKeys(sortKeys);
    }



    public boolean eliminarEmpleado(int codEmpleado) {
        return emple.eliminarEmpleado(codEmpleado);
    }

    //para asegurarnos de que se deseleccionan todas las filas
    private void deseleccionarTodasFilas() {
        int rowCount = tablaBuscarEmpleado.getRowCount();
        tablaBuscarEmpleado.clearSelection();
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
        tablaBuscarEmpleado = new javax.swing.JTable();
        botonReiniciar = new javax.swing.JButton();
        labelNombre = new javax.swing.JLabel();
        textFieldNombre = new javax.swing.JTextField();
        textFieldApellido = new javax.swing.JTextField();
        labelApellido = new javax.swing.JLabel();

        labelTitulo.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        labelTitulo.setText("Buscar empleado");

        jScrollPane1.setViewportView(tablaBuscarEmpleado);

        botonReiniciar.setText("Reiniciar");
        botonReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReiniciarActionPerformed(evt);
            }
        });

        labelNombre.setText("Nombre : ");

        labelApellido.setText("Apellido : ");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(145, 145, 145)
                        .addComponent(labelNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(65, 65, 65)
                        .addComponent(labelApellido)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(textFieldApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 190, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(133, 133, 133)
                        .addComponent(botonReiniciar))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(447, 447, 447)
                        .addComponent(labelTitulo)))
                .addContainerGap(193, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(labelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(72, 72, 72)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(botonReiniciar)
                    .addComponent(labelNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textFieldApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(201, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReiniciarActionPerformed
        // TODO add your handling code here:
        quitarListener();
        codEmpleado = -1;
        textFieldNombre.setText("");
        textFieldApellido.setText("");
        
        //actualizarTablaBuscarEmpleado();
        deseleccionarTodasFilas();
    }//GEN-LAST:event_botonReiniciarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonReiniciar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelApellido;
    private javax.swing.JLabel labelNombre;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JTable tablaBuscarEmpleado;
    private javax.swing.JTextField textFieldApellido;
    private javax.swing.JTextField textFieldNombre;
    // End of variables declaration//GEN-END:variables
}
