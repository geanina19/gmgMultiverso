/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.ProductoConProveedorDao;
import gmgmultiverso.db.dao.ProveedorDao;
import gmgmultiverso.model.Producto;
import gmgmultiverso.model.ProductoConProveedor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
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
import propiedades.EvObjOverComp4;
import propiedades.LisOverComp4;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.TableColumn;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.view.JasperViewer;



/**
 *
 * @author geanina.foanta
 */
public class BuscarProducto extends javax.swing.JPanel {

    private PrincipalAdministrador principalAdmin;
    private MouseListener mouseClickListener = null;
    private MouseListener mouseOrdenarTabla = null;
    
    ManagerConexion managerConexion = new ManagerConexion();
    Connection conet;
    
    Statement st;
    ResultSet rs;
    
    private int codProveedor = -1;
    private int codProducto = -1;
    
    Object[] cabecera = new Object[]{"Proveedor", "Producto", "Precio", "Unidad existente"};
    DefaultTableModel miModelo = new DefaultTableModel(null, cabecera);
    
    //PARA HACERLO CON EL DAO 
    //ProveedorDao prov = new ProveedorDao(managerConexion);
    
    ProductoConProveedorDao producdao = new ProductoConProveedorDao(managerConexion);
    
    /**
     * Creates new form BuscarProducto
     */
    public BuscarProducto(PrincipalAdministrador principalAdmin) {
        initComponents();
        this.principalAdmin = principalAdmin;
        this.setSize(1091, 642);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 20, 30));
        inicializarTabla();
        
        tablaBuscarProducto.setModel(miModelo);
        actualizarTablaBuscarProducto();
        cargarProveedores();
        
        // Agregar ítems al JComboBox comboBoxPrecios
        DefaultComboBoxModel<String> modeloCombo = new DefaultComboBoxModel<>();
        modeloCombo.addElement("Elegir rango");
        modeloCombo.addElement("0 - 5 €");
        modeloCombo.addElement("5 - 10 €");
        modeloCombo.addElement("10 - 15 €");
        modeloCombo.addElement("15 - 100 €");
        comboBoxPrecio.setModel(modeloCombo);
        
        //NO TOCAR (FUNCIONA)
        //cuaundo pulse una empresa, en la tabla se mostrará los proveedores
        componenteProveedores.addLisOverComp4(new LisOverComp4() 
        {
            @Override
            public void accion(EvObjOverComp4 ev) 
            {
                System.out.println(ev.getMensaje());
                codProveedor = ev.getSelectedCode();
                buscarProducto(codProducto);
            }
        });
        
        textFieldProducto.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscarProducto(codProducto);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                buscarProducto(codProducto);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                buscarProducto(codProducto);
            }
        });
        
        // Agregar ActionListener al JComboBox comboBoxPrecio
        comboBoxPrecio.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProducto(codProducto);
            }
        });
    }
    
    //-------------Métodos-----------------
    
    public PrincipalAdministrador getPrincipalAdmin() {
        return this.principalAdmin;
    }
    
    
    public void quitarListener()
    {
        tablaBuscarProducto.removeMouseListener(mouseClickListener);
        mouseClickListener = null;
    }
    
    public void activarOrdenarColumnas(DefaultTableModel model)
    {
        MouseAdapter mouseOrdenarTabla = null;
        tablaBuscarProducto.removeMouseListener(mouseOrdenarTabla);
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(model);
        tablaBuscarProducto.setRowSorter(sorter);
        mouseOrdenarTabla = new MouseAdapter() 
        {
            @Override
            public void mouseClicked(MouseEvent evt) 
            {
                int columnaSeleccionada = tablaBuscarProducto.columnAtPoint(evt.getPoint());
                sorter.toggleSortOrder(columnaSeleccionada);
            }
        };
        tablaBuscarProducto.addMouseListener(mouseOrdenarTabla);
    }
    
    
    public void mouseListenerAnadirColumnasExtra() {
        quitarListener();

        mouseClickListener = new MouseAdapter() {

            @Override
            public void mouseClicked(MouseEvent evt) {
                int editarColumna = tablaBuscarProducto.getColumnCount() - 2;
                int eliminarColumna = tablaBuscarProducto.getColumnCount() - 1;

                int column = tablaBuscarProducto.columnAtPoint(evt.getPoint());
                int row = tablaBuscarProducto.rowAtPoint(evt.getPoint());

                if (row >= 0 && row < tablaBuscarProducto.getRowCount() && column >= 0 && column < miModelo.getColumnCount()) {
                    String nombre = (String) tablaBuscarProducto.getValueAt(row, 1); // Índice 1 para el nombre del producto

                    String precioStr = tablaBuscarProducto.getValueAt(row, 2).toString(); // Índice 2 para el precio
                    double precio;

                    try {
                        precio = Double.parseDouble(precioStr);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;  // Si el valor no es un número válido, sal del método.
                    }

                    String unidadExistenteStr = tablaBuscarProducto.getValueAt(row, 3).toString(); // Índice 3 para unidad_existente
                    int unidad_existente;

                    try {
                        unidad_existente = Integer.parseInt(unidadExistenteStr);
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        return;  // Si el valor no es un número válido, sal del método.
                    }

                    int codigoProducto = 0;

                    try {
                        conet = managerConexion.abrirConexion();
                        String query = "SELECT id FROM producto WHERE nombre = ? AND precio = ? AND unidad_existente = ?";
                        var ps = conet.prepareStatement(query);
                        ps.setString(1, nombre);
                        ps.setDouble(2, precio);
                        ps.setInt(3, unidad_existente);
                        rs = ps.executeQuery();

                        if (rs.next()) {
                            codigoProducto = rs.getInt("id");
                        }

                        rs.close();
                        ps.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }

                    if (column == editarColumna) {
                        abrirEditarProducto(codigoProducto);
                    } else if (column == eliminarColumna) {
                        confirmarEliminarProducto(codigoProducto);
                    }
                }
            }

        };

        tablaBuscarProducto.addMouseListener(mouseClickListener);
    }

    
    //------------------------------Abrir Editar Proveedor------------------------------
    
    public void abrirEditarProducto(int codProducto) 
    {
        principalAdmin.mostrarEditarProducto(codProducto, this);
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
        miModelo = new DefaultTableModel(null, new Object[]{"Proveedor", "Producto", "Precio", "Unidad existente", "Editar", "Eliminar"}) {
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

        tablaBuscarProducto.setModel(miModelo);
        tablaBuscarProducto.setRowHeight(30);
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);

        for (int i = 0; i < tablaBuscarProducto.getColumnCount() - 2; i++) {
            tablaBuscarProducto.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        
    }

    
    //-------------Actualizar tabla------------

    public void actualizarTablaBuscarProducto() {
        quitarListener(); // Eliminar cualquier listener previo

        // Limpiar el modelo antes de añadir filas
        miModelo.setRowCount(0);

        // Obtener la lista de proveedores
        List<ProductoConProveedor> productos = producdao.list();
        
        // Crear ImageIcons con las imágenes para los botones
        ImageIcon editarIcon = crearImageIcon("/imagenes/editar.png");
        ImageIcon eliminarIcon = crearImageIcon("/imagenes/eliminar.png");

        // Añadir las filas al modelo
        for (ProductoConProveedor producto : productos) {
            Object[] rowData = {
                producto.getNombreProveedor(),
                producto.getNombreProducto(),
                producto.getPrecio(),
                producto.getUnidad_existente(),
                editarIcon,
                eliminarIcon
            };
            miModelo.addRow(rowData);
        }

        // Reactivar el ordenamiento de columnas y añadir el listener de mouse para los botones
        activarOrdenarColumnas(miModelo);
        mouseListenerAnadirColumnasExtra();
    }
    
    
    
    //-----------------------------
    /*
    public void filtrarProductosRango0a5() {
        quitarListener(); // Eliminar cualquier listener previo

        double precioMinimo = 0.0;
        double precioMaximo = 5.0;

        // Filtrar los productos según el rango de precios seleccionado
        List<ProductoConProveedor> productosFiltrados = producdao.buscarProductoPorRangoPrecio(precioMinimo, precioMaximo);

        // Actualizar la tabla con los productos filtrados
        actualizarTablaBuscarProductoRango(productosFiltrados);
    }
    */
    /*
    public void actualizarTablaBuscarProductoRango(List<ProductoConProveedor> productosFiltrados) {
        quitarListener(); // Eliminar cualquier listener previo

        // Limpiar el modelo antes de añadir filas
        miModelo.setRowCount(0);

        // Crear ImageIcons con las imágenes para los botones
        ImageIcon editarIcon = crearImageIcon("/imagenes/editar.png");
        ImageIcon eliminarIcon = crearImageIcon("/imagenes/eliminar.png");

        // Añadir las filas al modelo
        for (ProductoConProveedor producto : productosFiltrados) {
            Object[] rowData = {
                producto.getNombreProveedor(),
                producto.getNombreProducto(),
                producto.getPrecio(),
                producto.getUnidad_existente(),
                editarIcon,
                eliminarIcon
            };
            miModelo.addRow(rowData);
        }

        // Reactivar el ordenamiento de columnas y añadir el listener de mouse para los botones
        activarOrdenarColumnas(miModelo);
        mouseListenerAnadirColumnasExtra();
    }
*/

    
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
    
    //-------------Buscar producto------------
    
    public void buscarProducto(int codProducto) {
        quitarListener(); // Eliminar cualquier listener previo

        String nombreProducto = textFieldProducto.getText().trim();

        if (nombreProducto.matches(".*\\d.*")) {
            JOptionPane.showMessageDialog(null, "El nombre del producto no puede contener números.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        double precioMinimo = 0.0;
        double precioMaximo = 100.0;
        List<ProductoConProveedor> productosFiltrados = new ArrayList<>();

        String rangoPrecioSeleccionado = (String) comboBoxPrecio.getSelectedItem();

        if (rangoPrecioSeleccionado != null && !"Elegir rango".equals(rangoPrecioSeleccionado)) {
            String[] partesRango = rangoPrecioSeleccionado.split(" - ");
            double valorInicial = Double.parseDouble(partesRango[0].replace(" €", ""));
            double valorFinal = Double.parseDouble(partesRango[1].replace(" €", ""));

            if (valorInicial > 0 || valorFinal < 100) {
                precioMinimo = valorInicial;
                precioMaximo = valorFinal;
            }
        }

        final double precioMinimoFinal = precioMinimo;
        final double precioMaximoFinal = precioMaximo;

        // Filtrar por proveedor y producto juntos
        if (codProveedor != -1 && !nombreProducto.isEmpty() && (precioMinimo <= 0 || precioMaximo >= 100)) {
            productosFiltrados = producdao.buscarProductoPorNombreProveedorYPrecio(nombreProducto, codProveedor, precioMinimo, precioMaximo);
        }
        // Filtrar por proveedor solamente
        else if (codProveedor != -1 && (precioMinimo <= 0 || precioMaximo >= 100)) {
            productosFiltrados = producdao.listBuscarPorProveedor(codProveedor);
        }
        // Filtrar por rango de precios solamente
        else if ((codProveedor == -1) && (precioMinimo > 0 || precioMaximo < 100)) {
            productosFiltrados = producdao.buscarProductoPorRangoPrecio(precioMinimo, precioMaximo);
        }
        // Filtrar por proveedor y rango de precios juntos
        else if (codProveedor != -1 && (precioMinimo > 0 || precioMaximo < 100)) {
            productosFiltrados = producdao.buscarProductoPorProveedorYPrecio(codProveedor, precioMinimo, precioMaximo);
        }
        // No se seleccionó ni proveedor ni rango de precios
        else {
            if (!nombreProducto.isEmpty()) {
                productosFiltrados = producdao.buscarProductoPorNombre(nombreProducto);
            } else {
                productosFiltrados = producdao.list();
            }
        }
/*
        if (productosFiltrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
            actualizarTablaBuscarProducto();
        }
*/
        miModelo.setRowCount(0);

        ImageIcon editarIcon = crearImageIcon("/imagenes/editar.png");
        ImageIcon eliminarIcon = crearImageIcon("/imagenes/eliminar.png");

        boolean hayResultados = !productosFiltrados.isEmpty();

        for (ProductoConProveedor producto : productosFiltrados) {
            // Verificación del precio
            if (producto.getPrecio() >= precioMinimoFinal && producto.getPrecio() <= precioMaximoFinal) {
                Object[] rowData = {
                    producto.getNombreProveedor(),
                    producto.getNombreProducto(),
                    producto.getPrecio(),
                    producto.getUnidad_existente(),
                    editarIcon,
                    eliminarIcon
                };
                miModelo.addRow(rowData);
            }
        }

        activarOrdenarColumnas(miModelo);
        tablaBuscarProducto.setModel(miModelo);
        mouseListenerAnadirColumnasExtra();

        if (!hayResultados) {
            JOptionPane.showMessageDialog(this, "No se encontraron resultados.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
            actualizarTablaBuscarProducto();
        }
    }

    
    //------------Metodos para eliminar un proveedor------------
    
    public void confirmarEliminarProducto(int codProducto) {
        String nombreProducto = null;

        // Obtener la lista de proveedores
        List<ProductoConProveedor> productos = producdao.list();

        // Buscar el proveedor correspondiente al código proporcionado
        for (ProductoConProveedor productoConProveedor : productos) {
            if (productoConProveedor.getId() == codProducto) {
                nombreProducto = productoConProveedor.getNombreProducto();
                break; // Detener la búsqueda una vez que se encuentre el producto
            }
        }

        // Guardar el estado actual de la ordenación de la tabla
        List<? extends SortKey> sortKeys = tablaBuscarProducto.getRowSorter().getSortKeys();

        // Desactivar la ordenación de la tabla
        tablaBuscarProducto.setAutoCreateRowSorter(false);

        // Mostrar el cuadro de diálogo de confirmación incluyendo el nombre de la empresa
        int opcion = JOptionPane.showConfirmDialog(this,
                "¿Estás seguro de que quieres eliminar el producto '" + nombreProducto + "'?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);

        if (opcion == JOptionPane.YES_OPTION) {
            // Si el usuario presiona "OK", procede con la eliminación
            if (eliminarProducto(codProducto)) {
                JOptionPane.showMessageDialog(this, "Producto eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                actualizarTablaBuscarProducto(); 
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo eliminar el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            System.out.println("Eliminación cancelada");
        }

        // Reactivar la ordenación de la tabla utilizando el estado guardado
        tablaBuscarProducto.setAutoCreateRowSorter(true);
        tablaBuscarProducto.getRowSorter().setSortKeys(sortKeys);
    }
    
    
    public boolean eliminarProducto(int codProducto) {
        return producdao.eliminarProducto(codProducto);
    }

    //para asegurarnos de que se deseleccionan todas las filas
    private void deseleccionarTodasFilas() {
        int rowCount = tablaBuscarProducto.getRowCount();
        tablaBuscarProducto.clearSelection();
    }
    
    public void limpiarTodo() {
        quitarListener();
        codProducto = -1;
        codProveedor = -1;
        componenteProveedores.eliminarSeleccion();
        tablaBuscarProducto.clearSelection();
        textFieldProducto.setText("");
        
        comboBoxPrecio.setSelectedIndex(0);
        
        actualizarTablaBuscarProducto();
        deseleccionarTodasFilas();
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        latelTitulo = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaBuscarProducto = new javax.swing.JTable();
        componenteProveedores = new propiedades.Componente4();
        jLabel2 = new javax.swing.JLabel();
        textFieldProducto = new javax.swing.JTextField();
        comboBoxPrecio = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        panelBotones = new javax.swing.JPanel();
        botonReiniciar = new javax.swing.JButton();
        botonGenerarInforme = new javax.swing.JButton();

        latelTitulo.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        latelTitulo.setText("Buscar producto");

        jScrollPane1.setViewportView(tablaBuscarProducto);

        componenteProveedores.setEtiqueta("Proveedores  :");
        componenteProveedores.setMensaje("\"Selecciona un proveedor\"");
        componenteProveedores.setPrimerElementoEsMensaje(true);

        jLabel2.setText("Producto :");

        jLabel1.setText("Precio :");

        panelBotones.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 30, 5));

        botonReiniciar.setText("Reiniciar");
        botonReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReiniciarActionPerformed(evt);
            }
        });
        panelBotones.add(botonReiniciar);

        botonGenerarInforme.setText("Generar informe");
        botonGenerarInforme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonGenerarInformeActionPerformed(evt);
            }
        });
        panelBotones.add(botonGenerarInforme);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(componenteProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel2))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addComponent(jLabel1)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textFieldProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(105, 310, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(comboBoxPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)))))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(426, 426, 426)
                .addComponent(latelTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(latelTitulo, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 286, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(componenteProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(panelBotones, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(textFieldProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(30, 30, 30)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(comboBoxPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1))))))
                .addContainerGap(106, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReiniciarActionPerformed
        // TODO add your handling code here:
        limpiarTodo();

    }//GEN-LAST:event_botonReiniciarActionPerformed

    private void botonGenerarInformeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonGenerarInformeActionPerformed
        // TODO add your handling code here:
        String proveedores = componenteProveedores.obtenerNombreElementoSeleccionado();
        String nombreProducto = textFieldProducto.getText().trim();
        double precioMinimo = 0;
        double precioMaximo = Double.MAX_VALUE; // Valor máximo por defecto

        // Obtener el rango seleccionado en el JComboBox comboBoxPrecio
        String rangoSeleccionado = comboBoxPrecio.getSelectedItem().toString();

        // Determinar los valores de precioMinimo y precioMaximo según el rango seleccionado
        switch (rangoSeleccionado) {
            case "0 - 5 €":
                precioMinimo = 0;
                precioMaximo = 5;
                break;
            case "5 - 10 €":
                precioMinimo = 5;
                precioMaximo = 10;
                break;
            case "10 - 15 €":
                precioMinimo = 10;
                precioMaximo = 15;
                break;
            case "15 - 100 €":
                precioMinimo = 15;
                precioMaximo = 100;
                break;
            default:
                break;
        }
        
        Connection conexion = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conexion = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "SA");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(BuscarProveedor.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        InputStream vinculoarchivo = getClass().getResourceAsStream("productos.jrxml");

        if (vinculoarchivo != null) {
            JasperReport jr = null;
            try {
                String continuarConsulta = " ";

                if (!nombreProducto.isEmpty()) {
                    // Modificar la consulta para que busque todos los productos cuyos nombres comienzan con el texto ingresado
                    continuarConsulta += " AND nombre LIKE '%" + nombreProducto + "%'";
                }
                
                if (proveedores != null && !proveedores.isEmpty()) {
                    continuarConsulta += continuarConsulta + " AND nombre_empresa = '" + proveedores + "'";
                }
                
                // Agregar la condición del rango de precios solo si se selecciona un rango específico
                if (!rangoSeleccionado.equals("Todos")) {
                    continuarConsulta += " AND precio BETWEEN " + precioMinimo + " AND " + precioMaximo;
                }

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
            JOptionPane.showMessageDialog(this, "Error: No se pudo cargar el archivo productos.jrxml", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_botonGenerarInformeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonGenerarInforme;
    private javax.swing.JButton botonReiniciar;
    private javax.swing.JComboBox<String> comboBoxPrecio;
    private propiedades.Componente4 componenteProveedores;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel latelTitulo;
    private javax.swing.JPanel panelBotones;
    private javax.swing.JTable tablaBuscarProducto;
    private javax.swing.JTextField textFieldProducto;
    // End of variables declaration//GEN-END:variables
}
