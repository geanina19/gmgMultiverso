/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.ProveedorDao;
import gmgmultiverso.model.Proveedor;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import propiedades.EvObjOverComp4;
import propiedades.LisOverComp4;

/**
 *
 * @author geanina.foanta
 */
public class PrincipalBuscarProveedor extends javax.swing.JPanel {

    
    private MouseListener mouseClickListener = null;
    private MouseListener mouseOrdenarTabla = null;

    ManagerConexion con = new ManagerConexion();
    Connection conet;
    
    Statement st;
    ResultSet rs;
    
    private int codProveedor = -1;
    
    Object[] cabecera = new Object[]{"Nombre de la empresa", "Telefono", "Email"};
    DefaultTableModel miModelo = new DefaultTableModel(null, cabecera);
    
    //PARA HACERLO CON EL DAO 
    ProveedorDao prov = new ProveedorDao(con);
    
    /**
     * Creates new form PrincipalBuscarProveedor
     */
    public PrincipalBuscarProveedor() {
        
        initComponents();
        textFieldGmail.setSize(64, 22);
        this.setSize(1091, 642);
        
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
    }
    
    //-------------Métodos-----------------

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
    
    public void mouseListenerAnadirColumnasExtra() 
    {
        quitarListener();
        
        mouseClickListener = new MouseAdapter() {
            
        @Override
        public void mouseClicked(MouseEvent evt){
            
            int editarColumna = tablaBuscarProveedor.getColumnCount() - 2;
            int eliminarColumna = tablaBuscarProveedor.getColumnCount() - 1;

            int column = tablaBuscarProveedor.columnAtPoint(evt.getPoint());
            int row = tablaBuscarProveedor.rowAtPoint(evt.getPoint());

            if (row >= 0 && row < tablaBuscarProveedor.getRowCount()) {

                String codigoProveedorSeleccionado = "SELECT id FROM proveedor WHERE nombre_empresa = '" +  tablaBuscarProveedor.getValueAt(row, 0) + "' "
                            + "AND telefono = '" + tablaBuscarProveedor.getValueAt(row, 1) + "'";
                int codigoProveedor = 0;

                try {
                    
                    conet=con.abrirConexion();
                    st=conet.createStatement();
                    rs=st.executeQuery(codigoProveedorSeleccionado);
                    
                    if (rs.next()) {
                        codigoProveedor = rs.getInt("id");
                    }

                    rs.close();
                    st.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

                if (column == editarColumna) {
                    //abrirFrameEditar(codigoProveedor);
                } 
                else if (column == eliminarColumna) {
                    confirmarEliminarProveedor(codigoProveedor);
                }
            }
        }
    };

    tablaBuscarProveedor.addMouseListener(mouseClickListener);
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
    
    //-------------Actualizar tabla------------

    public void actualizarTablaBuscarProveedor() {
        quitarListener(); // Eliminar cualquier listener previo

        // Configurar el modelo de la tabla con las nuevas columnas
        miModelo = new DefaultTableModel(null, new Object[]{"Nombre de la empresa", "Telefono", "Email", "Editar", "Eliminar"}) {
            @Override
                public Class<?> getColumnClass(int columnIndex) 
                {
                    // Devolver la clase de la columna, ImageIcon para las columnas de imágenes
                    return columnIndex >= 3 ? ImageIcon.class : super.getColumnClass(columnIndex);
                }

                @Override
                public boolean isCellEditable(int row, int column) 
                {
                    // Hacer las columnas de no sean editables
                    return column >= 0 ? false : super.isCellEditable(row, column);
                }
        };

        tablaBuscarProveedor.setRowHeight(26); // Establecer la altura de las filas
        tablaBuscarProveedor.setModel(miModelo); // Asignar el modelo a la tabla

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
            conet = con.abrirConexion();
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
        
        String sqlDatos = "SELECT nombre_empresa AS Nombre_de_la_empresa, "
                + "telefono AS Telefono, "
                + "email AS Email, "
                + "id "
                + "FROM PROVEEDOR "
                + "WHERE 1=1";

        // Agregar condiciones a la consulta basadas en los códigos proporcionados
        if (codProveedor != -1) {
            sqlDatos += " AND id = " + codProveedor;
        }

        // Ejecutar la consulta y actualizar la tabla
        try {
            conet = con.abrirConexion();
            st = conet.createStatement();
            rs = st.executeQuery(sqlDatos);

            // Limpiar modelo actual de la tabla
            miModelo.setRowCount(0);

            boolean hayResultados = false;
            
            // Llenar el modelo con los resultados de la consulta
            while (rs.next()) {
                hayResultados = true;
                
                Object[] proveedor = new Object[]{
                    rs.getString("Nombre_de_la_empresa"),
                    rs.getString("Telefono"),
                    rs.getString("Email"),
                };
                miModelo.addRow(proveedor);
            }

            // Actualizar la tabla con el nuevo modelo
            tablaBuscarProveedor.setModel(miModelo);
            
            if (!hayResultados) {
                JOptionPane.showMessageDialog(this, "No se encontraron resultados.", "Sin Resultados", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } 
        finally {
            try {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conet != null) conet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
    
    //Metodos para eliminar un proveedor
    
    public void confirmarEliminarProveedor(int codigoProveedor) 
    {
            // Mostrar el cuadro de diálogo de confirmación solo si no hay productos asociados
            int opcion = JOptionPane.showConfirmDialog(this,
                    "¿Estás seguro de que quieres eliminar este proveedor?", "Confirmar eliminación",
                    JOptionPane.YES_NO_OPTION);
            
            if (opcion == JOptionPane.YES_OPTION) {
                // Si el usuario presiona "OK", procede con la eliminación
                eliminarProveedor(codigoProveedor);
            } 
            else {
                System.out.println("Eliminación cancelada");
            }
        /*    
        }
        */
    }
    
    private void eliminarProveedor(int codigoProveedor) {
    if (prov.eliminarProveedor(codigoProveedor)) {
        JOptionPane.showMessageDialog(this, "Proveedor eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        actualizarTablaBuscarProveedor(); // Actualiza la tabla después de eliminar el proveedor.
    } else {
        JOptionPane.showMessageDialog(this, "No se pudo eliminar el proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
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
        componenteNombreEmpresas = new propiedades.Componente4();
        textFieldGmail = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();

        setOpaque(false);
        setPreferredSize(new java.awt.Dimension(1091, 642));

        jScrollPane1.setViewportView(tablaBuscarProveedor);

        componenteNombreEmpresas.setEtiqueta("Nombre Empresas  :");
        componenteNombreEmpresas.setMensaje("\"Elije un nombre de empresa\"");
        componenteNombreEmpresas.setPrimerElementoEsMensaje(true);

        jLabel1.setText("Gmail :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(componenteNombreEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(95, 95, 95)
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(textFieldGmail, javax.swing.GroupLayout.PREFERRED_SIZE, 282, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 247, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(componenteNombreEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(292, 292, 292)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(textFieldGmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(215, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private propiedades.Componente4 componenteNombreEmpresas;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaBuscarProveedor;
    private javax.swing.JTextField textFieldGmail;
    // End of variables declaration//GEN-END:variables
}
