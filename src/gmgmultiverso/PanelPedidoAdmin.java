/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.PedidoConNombreDao;
import gmgmultiverso.model.PedidoConNombre;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.RowFilter;
import javax.swing.RowFilter.ComparisonType;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author monic
 */
public class PanelPedidoAdmin extends javax.swing.JPanel {
    private PrincipalAdministrador principalAdmin;    
    //CONEXION
    ManagerConexion con = new ManagerConexion();
    Connection conet;  
    Statement st;
    ResultSet rs;
    
    private DefaultTableModel miModelo;
    PedidoConNombreDao pedidoCompleto = new PedidoConNombreDao(con);
    private TableRowSorter<DefaultTableModel> sorter;
    
    /**
     * Creates new form PanelPedidoAdmin
     */
    public PanelPedidoAdmin(PrincipalAdministrador principalAdmin) {
        initComponents();
        this.principalAdmin = principalAdmin;
        anadirDatosTabla();
        tableTodosPedidos.setRowHeight(50);
        
        sorter = new TableRowSorter<>(miModelo);
        tableTodosPedidos.setRowSorter(sorter);
        cargarEstadosEnComboBox();
    }
    
     /*--------- METODO PARA LOS ICONOS --------------*/
    public ImageIcon crearImageIcon(String path) {
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
    
    
    /*--------------------- METODO PARA AÑADIR LOS DATOS A LA TABLA ------------------*/
    public void anadirDatosTabla(){
        
    
       // Obtener lista de pedidos
        List<PedidoConNombre> pedidos = pedidoCompleto.list();
        miModelo = new DefaultTableModel(new Object[]{
                "Numero de pedido", "Nombre cliente", "Fecha pedido", "Nombre empleado", "Estado del pedido", "Última actualización"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 4) {
                    return ImageIcon.class;
                }
                return Object.class;
            }
        };
        
        // imagenes para los diferentes estados
        ImageIcon estado1Icon = crearImageIcon("/imgEmple/recibidoIcono.png");
        ImageIcon estado2Icon = crearImageIcon("/imgEmple/preparacionIcono.png");
        ImageIcon estado3Icon = crearImageIcon("/imgEmple/enviadoIcono.png");
        
        for (PedidoConNombre pedido : pedidos) {
            ImageIcon estadoIcon;
            switch (pedido.getEstado()) {
                case 1:
                    estadoIcon = estado1Icon;
                    break;
                case 2:
                    estadoIcon = estado2Icon;
                    break;
                case 3:
                    estadoIcon = estado3Icon;
                    break;
                default:
                    estadoIcon = null; 
                    break;
            }
            miModelo.addRow(new Object[]{
                pedido.getId(),
                pedido.getNombreCliente(),
                pedido.getFechaPedido(),
                pedido.getNombreEmpleado(),
                estadoIcon,
                pedido.getUltimaActualizacion(),
            });
        }
        tableTodosPedidos.setModel(miModelo);
        
        // Crear y aplicar el renderizador de celda centrado
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        // Aplicar el renderizador a cada columna excepto la de imagen
        TableColumnModel columnModel = tableTodosPedidos.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            if (i != 4 && i != 6) { 
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }

        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        // cambia el color de fondo de los encabezados
        headerRenderer.setBackground(Color.LIGHT_GRAY); 
        // poner el texto en negrita
        headerRenderer.setFont(headerRenderer.getFont().deriveFont(Font.BOLD)); 
        // Aplicar el renderizador de encabezado a cada columna
        JTableHeader tableHeader = tableTodosPedidos.getTableHeader();
        tableHeader.setDefaultRenderer(headerRenderer);

        
    }
    
    /*****combo box***********/
    public void cargarEstadosEnComboBox() {
        comboBoxEstado.removeAllItems();

        // Mapa para los valores numéricos de los estados y sus representaciones de cadena
        Map<Integer, String> estadoStrings = new HashMap<>();
        estadoStrings.put(1, "Aceptado");
        estadoStrings.put(2, "En preparación");
        estadoStrings.put(3, "Enviado");

        // Obtener los estados desde la base de datos
        List<Integer> estados = pedidoCompleto.listarEstadosPedido();

        // Agrega la opción "Seleccionar estado" como opción por defecto
        comboBoxEstado.addItem("Seleccionar estado");

        // Agrega los estados al combo box
        for (Integer estado : estados) {
            String estadoNombre = estadoStrings.get(estado);
            if (estadoNombre != null) {
                comboBoxEstado.addItem(estadoNombre);
            }
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
        tableTodosPedidos = new javax.swing.JTable();
        labelTitulo = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        textNombre = new javax.swing.JTextField();
        buscarButton = new javax.swing.JButton();
        dateFiltro = new com.toedter.calendar.JDateChooser();
        buttonReiniciar = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        comboBoxEstado = new javax.swing.JComboBox<>();

        tableTodosPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableTodosPedidos);

        labelTitulo.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        labelTitulo.setText("Ver pedidos");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel2.setText("Nombre cliente");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel1.setText("Fecha del pedido");

        buscarButton.setText("Buscar");
        buscarButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buscarButtonActionPerformed(evt);
            }
        });

        dateFiltro.setBackground(new java.awt.Color(255, 245, 238));
        dateFiltro.setOpaque(false);

        buttonReiniciar.setText("Reiniciar");
        buttonReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReiniciarActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel3.setText("Estado");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 109, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(textNombre)
                    .addComponent(dateFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                .addGap(95, 95, 95)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(comboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buscarButton)
                        .addGap(65, 65, 65)
                        .addComponent(buttonReiniciar)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(comboBoxEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(dateFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(buscarButton)
                        .addComponent(buttonReiniciar)))
                .addContainerGap(110, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1050, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(434, 434, 434)
                .addComponent(labelTitulo)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(labelTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(140, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void buscarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarButtonActionPerformed
        String nombre = textNombre.getText();
        java.util.Date date = dateFiltro.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = (date != null) ? sdf.format(date) : "";

        String estadoSeleccionado = (String) comboBoxEstado.getSelectedItem();
        Integer estadoNumero = null;

        // Mapa para los valores numéricos de los estados
        Map<String, Integer> estadoMap = new HashMap<>();
        estadoMap.put("Aceptado", 1);
        estadoMap.put("En preparación", 2);
        estadoMap.put("Enviado", 3);

        // Convertir la selección de estado a su valor numérico
        if (estadoMap.containsKey(estadoSeleccionado)) {
            estadoNumero = estadoMap.get(estadoSeleccionado);
        }

        // Crear una lista de filtros
        List<RowFilter<DefaultTableModel, Object>> filters = new ArrayList<>();

        if (!nombre.isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + nombre, 1)); // Filtro por nombre de cliente
        }
        if (date != null) {
            filters.add(RowFilter.regexFilter(dateStr, 2)); // Filtro por fecha de pedido
        }
        if (estadoNumero != null) {
            filters.add(RowFilter.regexFilter(estadoNumero.toString(), 4)); // Filtro por estado
        }

        // Aplicar el filtro 
        if (filters.isEmpty()) {
            sorter.setRowFilter(null);
        } else {
            sorter.setRowFilter(RowFilter.andFilter(filters));
        }

    }//GEN-LAST:event_buscarButtonActionPerformed

    private void buttonReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReiniciarActionPerformed
        textNombre.setText("");
        dateFiltro.setDate(null);
        sorter.setRowFilter(null);
//        actualizarTabla();
    }//GEN-LAST:event_buttonReiniciarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton buscarButton;
    private javax.swing.JButton buttonReiniciar;
    private javax.swing.JComboBox<String> comboBoxEstado;
    private com.toedter.calendar.JDateChooser dateFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JTable tableTodosPedidos;
    private javax.swing.JTextField textNombre;
    // End of variables declaration//GEN-END:variables
}
