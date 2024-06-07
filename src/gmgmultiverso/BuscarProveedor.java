/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.ProveedorDao;
import gmgmultiverso.model.Proveedor;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowSorter.SortKey;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import propiedades.EvObjOverComp4;
import propiedades.LisOverComp4;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author geanina.foanta
 */
public class BuscarProveedor extends javax.swing.JPanel {

    private PrincipalAdministrador principalAdmin;
    private MouseListener mouseClickListener = null;
    private MouseListener mouseOrdenarTabla = null;

    ManagerConexion managerConexion = new ManagerConexion();
    Connection conet;
    
    Statement st;
    ResultSet rs;
    
    private int codProveedor = -1;
    
    private List<Integer> idsProveedoresFiltrados = new ArrayList<>();

    
    Object[] cabecera = new Object[]{"Nombre de la empresa", "Telefono", "Email"};
    DefaultTableModel miModelo = new DefaultTableModel(null, cabecera);
    
    //PARA HACERLO CON EL DAO 
    ProveedorDao prov = new ProveedorDao(managerConexion);
    
    /**
     * Creates new form PrincipalBuscarProveedor
     */
    public BuscarProveedor(PrincipalAdministrador principalAdmin) {
        
        initComponents();
        this.principalAdmin = principalAdmin;
        textFieldTelefono.setSize(64, 22);
        this.setSize(1091, 642);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 20, 30));
        inicializarTabla();
        
      
        tablaBuscarProveedor.setModel(miModelo);
        actualizarTablaBuscarProveedor();
        cargarNombreEmpresa();
        
        //NO TOCAR (FUNCIONA)
        //cuaundo pulse una empresa, en la tabla se mostrará los proveedores
        componenteNombreEmpresas.addLisOverComp4(new LisOverComp4() 
        {
            @Override
            public void accion(EvObjOverComp4 ev) 
            {
                System.out.println(ev.getMensaje());
                codProveedor = ev.getSelectedCode();
                buscarProveedor(codProveedor);
            }
        });
        

        textFieldTelefono.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarProveedor(codProveedor);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarProveedor(codProveedor);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buscarProveedor(codProveedor);
            }
        });

        
    }
    
    //-------------Métodos-----------------
    
    public PrincipalAdministrador getPrincipalAdmin() {
        return this.principalAdmin;
    }
    
    
    public void quitarListener()
    {
        tablaBuscarProveedor.removeMouseListener(mouseClickListener);
        mouseClickListener = null;
    }
    
    public void activarOrdenarColumnas(DefaultTableModel model)
    {
        MouseAdapter mouseOrdenarTabla = null;
        tablaBuscarProveedor.removeMouseListener(mouseOrdenarTabla);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tablaBuscarProveedor.setRowSorter(sorter);
        mouseOrdenarTabla = new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent evt) 
            {
                int columnaSeleccionada = tablaBuscarProveedor.columnAtPoint(evt.getPoint());
                sorter.toggleSortOrder(columnaSeleccionada);
            }
        };
        tablaBuscarProveedor.addMouseListener(mouseOrdenarTabla);
    }
    
    public void mouseListenerAnadirColumnasExtra() {
        quitarListener();

        mouseClickListener = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {

                int editarColumna = tablaBuscarProveedor.getColumnCount() - 2;
                int eliminarColumna = tablaBuscarProveedor.getColumnCount() - 1;

                int column = tablaBuscarProveedor.columnAtPoint(evt.getPoint());
                int row = tablaBuscarProveedor.rowAtPoint(evt.getPoint());

                if (row >= 0 && row < tablaBuscarProveedor.getRowCount() && column >= 0 && column < miModelo.getColumnCount()) {
                    String codigoProveedorSeleccionado = "SELECT id FROM proveedor WHERE nombre_empresa = '" +  tablaBuscarProveedor.getValueAt(row, 0) + "' "
                                + "AND telefono = '" + tablaBuscarProveedor.getValueAt(row, 1) + "'";
                    int codigoProveedor = 0;

                    try {

                        conet = managerConexion.abrirConexion();
                        st = conet.createStatement();
                        rs = st.executeQuery(codigoProveedorSeleccionado);

                        if (rs.next()) {
                            codigoProveedor = rs.getInt("id");
                        }

                        rs.close();
                        st.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    if (column == editarColumna) {
                        abrirEditarProveedor(codigoProveedor);
                    } else if (column == eliminarColumna) {
                        confirmarEliminarProveedor(codigoProveedor);
                    }
                }
            }
        };

        tablaBuscarProveedor.addMouseListener(mouseClickListener);
    }

    
    //------------------------------Abrir Editar Proveedor------------------------------
    
    public void abrirEditarProveedor(int codigoProveedor) 
    {
        principalAdmin.mostrarEditarProveedor(codigoProveedor, this);
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
        miModelo = new DefaultTableModel(null, new Object[]{"Nombre de la empresa", "Telefono", "Email", "Editar", "Eliminar"}) {
            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Devolver la clase de la columna, ImageIcon para las columnas de imágenes
                return columnIndex >= 3 ? ImageIcon.class : super.getColumnClass(columnIndex);
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                // Hacer las columnas de no sean editables
                return column >= 0 ? false : super.isCellEditable(row, column);
            }
        };

        tablaBuscarProveedor.setModel(miModelo);
        tablaBuscarProveedor.setRowHeight(30);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tablaBuscarProveedor.getColumnCount() - 2; i++) {
            tablaBuscarProveedor.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
    }
    
    //-------------Actualizar tabla------------

    public void actualizarTablaBuscarProveedor() {
        quitarListener(); // Eliminar cualquier listener previo

        // Limpiar el modelo antes de añadir filas
        miModelo.setRowCount(0);

        // Obtener la lista de proveedores
        List<Proveedor> proveedores = prov.list();
        
        // Crear ImageIcons con las imágenes para los botones
        ImageIcon editarIcon = crearImageIcon("/imagenes/editar.png");
        ImageIcon eliminarIcon = crearImageIcon("/imagenes/eliminar.png");

        // Añadir las filas al modelo
        for (Proveedor proveedor : proveedores) {
            Object[] rowData = {
                proveedor.getNombre_empresa(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                editarIcon,
                eliminarIcon
            };
            miModelo.addRow(rowData);
        }

        // Reactivar el ordenamiento de columnas y añadir el listener de mouse para los botones
        activarOrdenarColumnas(miModelo);
        mouseListenerAnadirColumnasExtra();
    }

    //-------------Cargar proveedores------------
    public void cargarNombreEmpresa() {
        
        String slqPaises = "select nombre_empresa, id from proveedor";
        try{
            conet = managerConexion.abrirConexion();
            st = conet.createStatement();
            rs = st.executeQuery(slqPaises);
            
            componenteNombreEmpresas.eliminarSeleccion();
            
            componenteNombreEmpresas.rellenarJlist(rs, "nombre_empresa", "id");
            rs.close();
            st.close();
            conet.close();
        } catch(SQLException e) {
           e.printStackTrace();
        }
    }
    
    //-------------Buscar proveedores------------
    
    public void buscarProveedor(int codProveedor) {
        // Obtener el número de teléfono ingresado en el textField
        String telefono = textFieldTelefono.getText().trim();

        // Verificar si el teléfono contiene solo números
        if (!telefono.matches("\\d*")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener solo números", "Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        // Lista para almacenar los proveedores filtrados
        List<Proveedor> proveedoresFiltrados = new ArrayList<>();

        // Si no se ingresa un número de teléfono, simplemente mostrar todos los proveedores con el codProveedor
        if (telefono.isEmpty()) {
            // Obtener la lista completa de proveedores
            List<Proveedor> proveedores = prov.list();

            // Filtrar la lista de proveedores según el código de proveedor si es diferente de -1
            for (Proveedor proveedor : proveedores) {
                if (codProveedor != -1) {
                    if (proveedor.getId() == codProveedor) {
                        proveedoresFiltrados.add(proveedor);
                    }
                } else {
                    // Si el código de proveedor es -1, mostrar todos los proveedores
                    proveedoresFiltrados = proveedores;
                }
            }
        } else {
            // Obtener la lista de proveedores filtrados por teléfono
            List<Proveedor> proveedoresPorTelefono = prov.buscarProveedoresPorTelefono(Integer.parseInt(telefono));

            // Filtrar la lista de proveedores según el código de proveedor si es diferente de -1
            for (Proveedor proveedor : proveedoresPorTelefono) {
                if (codProveedor != -1) {
                    if (proveedor.getId() == codProveedor) {
                        proveedoresFiltrados.add(proveedor);
                    }
                } else {
                    // Si el código de proveedor es -1, mostrar todos los proveedores filtrados por teléfono
                    proveedoresFiltrados = proveedoresPorTelefono;
                }
            }
        }

        // Limpiar el modelo actual de la tabla
        miModelo.setRowCount(0);

        // Crear ImageIcons con las imágenes
        ImageIcon editarIcon = crearImageIcon("/imagenes/editar.png");
        ImageIcon eliminarIcon = crearImageIcon("/imagenes/eliminar.png");

        boolean hayResultados = !proveedoresFiltrados.isEmpty();

        // Llenar el modelo con los resultados de la búsqueda
        for (Proveedor proveedor : proveedoresFiltrados) {
            Object[] rowData = new Object[]{
                proveedor.getNombre_empresa(),
                proveedor.getTelefono(),
                proveedor.getEmail(),
                editarIcon,
                eliminarIcon
            };
            miModelo.addRow(rowData);
        }

        // Actualizar la tabla con el nuevo modelo
        tablaBuscarProveedor.setModel(miModelo);
        mouseListenerAnadirColumnasExtra();

        if (!hayResultados) {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
        }
    }



    
    //------------Metodos para eliminar un proveedor------------
    
    public void confirmarEliminarProveedor(int codigoProveedor) {
        String nombreEmpresa = null;

        // Obtener la lista de proveedores
        List<Proveedor> proveedores = prov.list();

        // Buscar el proveedor correspondiente al código proporcionado
        for (Proveedor proveedor : proveedores) {
            if (proveedor.getId() == codigoProveedor) {
                nombreEmpresa = proveedor.getNombre_empresa();
                break; // Detener la búsqueda una vez que se encuentre el proveedor
            }
        }

        // Guardar el estado actual de la ordenación de la tabla
        List<? extends SortKey> sortKeys = tablaBuscarProveedor.getRowSorter().getSortKeys();

        // Desactivar la ordenación de la tabla
        tablaBuscarProveedor.setAutoCreateRowSorter(false);

        // Mostrar el cuadro de diálogo de confirmación incluyendo el nombre de la empresa
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres eliminar el proveedor '" + nombreEmpresa + "'?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            // Si el usuario presiona "OK", procede con la eliminación
            if (eliminarProveedor(codigoProveedor)) {
                JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTablaBuscarProveedor(); // Actualiza la tabla después de eliminar el proveedor.
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Eliminación cancelada");
        }

        // Reactivar la ordenación de la tabla utilizando el estado guardado
        tablaBuscarProveedor.setAutoCreateRowSorter(true);
        tablaBuscarProveedor.getRowSorter().setSortKeys(sortKeys);
    }



    public boolean eliminarProveedor(int codigoProveedor) {
        return prov.eliminarProveedor(codigoProveedor);
    }

    //para asegurarnos de que se deseleccionan todas las filas
    public void deseleccionarTodasFilas() {
        int rowCount = tablaBuscarProveedor.getRowCount();
        tablaBuscarProveedor.clearSelection();
    }

    public void generarInformePorTelefono(String telefono, String nombreEmpresa) {
        // Obtener el ID del proveedor basado en el número de teléfono
        int idProveedor = prov.obtenerIdProveedorPorTelefono(telefono);


        // Verificar si se encontró un proveedor con el número de teléfono proporcionado
        if (idProveedor != -1) {
            // Generar el informe utilizando el ID del proveedor encontrado
            Connection conexion = null;
            try {
                Class.forName("org.hsqldb.jdbcDriver");
                conexion = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "SA");
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(BuscarProveedor.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Cargar el archivo JRXML
            InputStream vinculoarchivo = getClass().getResourceAsStream("proveedores.jrxml");

            // Verificar si el InputStream no es nulo antes de continuar
            if (vinculoarchivo != null) {
                JasperReport jr = null;
                try {
                    // Construir la cadena de consulta
                    String continuarConsulta = " AND id = " + idProveedor;

                    if (nombreEmpresa != null && !nombreEmpresa.isEmpty()) {
                        continuarConsulta += " AND nombre_empresa = '" + nombreEmpresa + "'";
                    }

                    // Agregar el parámetro "consulta" al informe
                    Map<String, Object> parametros = new HashMap<>();
                    parametros.put("consulta", continuarConsulta);
                    parametros.put("imglogo", "gmgmultiverso/logo.png");

                    jr = JasperCompileManager.compileReport(vinculoarchivo);
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parametros, conexion);

                    JasperViewer visor = new JasperViewer(jasperPrint, false);
                    visor.setVisible(true);
                } catch (JRException ex) {
                    Logger.getLogger(BuscarProveedor.class.getName()).log(Level.SEVERE, null, ex);
                } finally {
                    // Cierre del InputStream
                    try {
                        if (vinculoarchivo != null) {
                            vinculoarchivo.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, "Error: No se pudo cargar el archivo proveedores.jrxml", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "No se encontró ningún proveedor con el número de teléfono proporcionado.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void generarInformePorNombreEmpresa(String nombreEmpresa) {
        // Generar el informe utilizando el nombre de la empresa seleccionado
        Connection conexion = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conexion = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "SA");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(BuscarProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }

        // Cargar el archivo JRXML
        InputStream vinculoarchivo = getClass().getResourceAsStream("proveedores.jrxml");

        // Verificar si el InputStream no es nulo antes de continuar
        if (vinculoarchivo != null) {
            JasperReport jr = null;
            try {
                // Construir la cadena de consulta
                String continuarConsulta = "";
                if (nombreEmpresa != null && !nombreEmpresa.isEmpty()) {
                    continuarConsulta = " AND nombre_empresa = '" + nombreEmpresa + "'";
                }

                // Agregar el parámetro "consulta" al informe
                Map<String, Object> parametros = new HashMap<>();
                parametros.put("consulta", continuarConsulta);
                parametros.put("imglogo", "gmgmultiverso/logo.png");

                jr = JasperCompileManager.compileReport(vinculoarchivo);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parametros, conexion);

                JasperViewer visor = new JasperViewer(jasperPrint, false);
                visor.setVisible(true);
            } catch (JRException ex) {
                Logger.getLogger(BuscarProveedor.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                // Cierre del InputStream
                try {
                    if (vinculoarchivo != null) {
                        vinculoarchivo.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error: No se pudo cargar el archivo proveedores.jrxml", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    // Método para generar el informe con todos los proveedores
    public void generarInformeTodosProveedores() {
        Connection conexion = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conexion = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "SA");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(BuscarProveedor.class.getName()).log(Level.SEVERE, null, ex);
        }

        InputStream vinculoarchivo = getClass().getResourceAsStream("proveedores.jrxml");

        if (vinculoarchivo != null) {
            JasperReport jr = null;
            try {
                String continuarConsulta = " "; // Consulta que siempre es verdadera para obtener todos los registros

                Map<String, Object> parametros = new HashMap<>();
                parametros.put("consulta", continuarConsulta);
                parametros.put("imglogo", "gmgmultiverso/logo.png");

                jr = JasperCompileManager.compileReport(vinculoarchivo);
                JasperPrint jasperPrint = JasperFillManager.fillReport(jr, parametros, conexion);

                JasperViewer visor = new JasperViewer(jasperPrint, false);
                visor.setVisible(true);
            } catch (JRException ex) {
                Logger.getLogger(BuscarProveedor.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    if (vinculoarchivo != null) {
                        vinculoarchivo.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Error: No se pudo cargar el archivo proveedores.jrxml", "Error", JOptionPane.ERROR_MESSAGE);
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

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaBuscarProveedor = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        textFieldTelefono = new javax.swing.JTextField();
        labelTitulo = new javax.swing.JLabel();
        componenteNombreEmpresas = new propiedades.Componente4();
        panelBotones = new javax.swing.JPanel();
        botonInforme = new javax.swing.JButton();
        botonReiniciar = new javax.swing.JButton();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1091, 642));

        jScrollPane1.setViewportView(tablaBuscarProveedor);

        jLabel2.setText("Telefono :");

        labelTitulo.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        labelTitulo.setText("Buscar proveedor");

        componenteNombreEmpresas.setEtiqueta("Nombre empresa  :");
        componenteNombreEmpresas.setMensaje("\"Elije una empresa\"");
        componenteNombreEmpresas.setPrimerElementoEsMensaje(true);

        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 5));

        botonInforme.setText("Generar informe");
        botonInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonInformeActionPerformed(evt);
            }
        });
        panelBotones.add(botonInforme);

        botonReiniciar.setText("Reiniciar");
        botonReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReiniciarActionPerformed(evt);
            }
        });
        panelBotones.add(botonReiniciar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(componenteNombreEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(96, 96, 96)
                                .addComponent(jLabel2)
                                .addGap(18, 18, 18)
                                .addComponent(textFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(443, 443, 443)
                                .addComponent(labelTitulo))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(119, 119, 119)))
                        .addGap(0, 229, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(labelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(textFieldTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(componenteNombreEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(85, 85, 85))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReiniciarActionPerformed
        // TODO add your handling code here:
        quitarListener();
        codProveedor = -1;
        componenteNombreEmpresas.eliminarSeleccion();
        tablaBuscarProveedor.clearSelection();
        textFieldTelefono.setText("");
        
        actualizarTablaBuscarProveedor();
        deseleccionarTodasFilas();
        
    }//GEN-LAST:event_botonReiniciarActionPerformed

    private void botonInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonInformeActionPerformed
        // TODO add your handling code here:
        // Obtener el nombre de la empresa seleccionado en el comboBox
        String nombreEmpresa = componenteNombreEmpresas.obtenerNombreElementoSeleccionado();

        // Obtener el número de teléfono ingresado en el textField
        String telefono = textFieldTelefono.getText().trim();

        // Verificar si el teléfono contiene solo números
        if (!telefono.matches("\\d*")) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener solo números", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        boolean informeGenerado = false;

        // Verificar si se ingresó un número de teléfono
        if (!telefono.isEmpty()) {
            // Generar informe por número de teléfono
            generarInformePorTelefono(telefono, nombreEmpresa);
            informeGenerado = true;
        } else if (nombreEmpresa != null && !nombreEmpresa.isEmpty()) {
            // Generar informe por nombre de empresa
            generarInformePorNombreEmpresa(nombreEmpresa);
            informeGenerado = true;
        }

        // Si no se proporcionó ni teléfono ni nombre de empresa, generar informe con todos los proveedores
        if (!informeGenerado) {
            generarInformeTodosProveedores();
        }
    }//GEN-LAST:event_botonInformeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonInforme;
    private javax.swing.JButton botonReiniciar;
    private propiedades.Componente4 componenteNombreEmpresas;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JTable tablaBuscarProveedor;
    private javax.swing.JTextField textFieldTelefono;
    // End of variables declaration//GEN-END:variables
}
