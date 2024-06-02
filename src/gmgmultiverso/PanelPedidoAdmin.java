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
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;

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
    
    /**
     * Creates new form PanelPedidoAdmin
     */
    public PanelPedidoAdmin(PrincipalAdministrador principalAdmin) {
        initComponents();
        this.principalAdmin = principalAdmin;
        anadirDatosTabla();
        tableTodosPedidos.setRowHeight(50);
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
                "Numero de pedido", "Nombre cliente", "Fecha pedido", "Nombre empleado", "Estado del pedido", "Última actualización", "Editar"}, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 4 || column == 6) {
                    return ImageIcon.class;
                }
                return Object.class;
            }
        };
        // imágenes para los botones
        ImageIcon editarIcon = crearImageIcon("/imagenes/editar.png");
        
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
                editarIcon
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

        // Añadir el MouseListener para el evento de clic
        tableTodosPedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columnaModificar = tableTodosPedidos.getColumnModel().getColumnIndex("Editar");
                int fila = tableTodosPedidos.rowAtPoint(e.getPoint());
                if (fila >= 0 && tableTodosPedidos.columnAtPoint(e.getPoint()) == columnaModificar) {
                    // Código para abrir el panel de edición
                    //abrirVentanaPedido(fila);
                }
            }
        });
        
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

        tableTodosPedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tableTodosPedidos);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 920, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(267, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tableTodosPedidos;
    // End of variables declaration//GEN-END:variables
}
