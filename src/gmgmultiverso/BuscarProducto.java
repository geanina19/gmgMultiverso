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
import propiedades.EvObjOverComp4;
import propiedades.LisOverComp4;
import java.sql.PreparedStatement;

/*
en mi codigo quiero tengo un jcombobox y quiero que en el se carguen por ejemplo en el item 1 tengo rango de 0 a 10 euros que es el precio incluyendo ambos numeros , 
de 11 a 20 incluyendo ambos numeros, de 21 a 30 ambos numerose incluidos, necesitare algun metodo que me agrupe los precios de los productos para luego cuando pulse alguna opcion del 
*/

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
        
        List<ProductoConProveedor> productos = producdao.list();

            // Filtrar la lista de proveedores según el código de proveedor si es diferente de -1
            for (ProductoConProveedor producto : productos) {
                if (codProveedor != -1) {
                    if (producto.getId() == codProveedor) {
                        //proveedoresFiltrados.add(producto);
                    }
                } else {
                    // Si el código de proveedor es -1, mostrar todos los proveedores
                    //proveedoresFiltrados = proveedores;
                }
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
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        botonReiniciar = new javax.swing.JButton();

        latelTitulo.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        latelTitulo.setText("Buscar producto");

        jScrollPane1.setViewportView(tablaBuscarProducto);

        componenteProveedores.setEtiqueta("Proveedores  :");
        componenteProveedores.setMensaje("\"Elije una empresa\"");
        componenteProveedores.setPrimerElementoEsMensaje(true);

        jLabel2.setText("Producto :");

        jLabel1.setText("Precio :");

        botonReiniciar.setText("Reiniciar");
        botonReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReiniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(474, Short.MAX_VALUE)
                .addComponent(latelTitulo)
                .addGap(453, 453, 453))
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
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(textFieldProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botonReiniciar)
                                .addGap(105, 105, 105)))))
                .addContainerGap())
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(67, 67, 67)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(textFieldProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(99, 99, 99)
                                .addComponent(botonReiniciar)))
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1))))
                .addContainerGap(106, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReiniciarActionPerformed
        // TODO add your handling code here:
        quitarListener();
        codProducto = -1;
        componenteProveedores.eliminarSeleccion();
        tablaBuscarProducto.clearSelection();
        textFieldProducto.setText("");

        actualizarTablaBuscarProducto();
        deseleccionarTodasFilas();

    }//GEN-LAST:event_botonReiniciarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonReiniciar;
    private propiedades.Componente4 componenteProveedores;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel latelTitulo;
    private javax.swing.JTable tablaBuscarProducto;
    private javax.swing.JTextField textFieldProducto;
    // End of variables declaration//GEN-END:variables
}
