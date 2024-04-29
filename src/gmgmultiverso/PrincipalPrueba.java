package gmgmultiverso;

import gmgmultiverso.db.Conexion;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.ImageIcon;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import propiedades.EvObjOverComp4;
import propiedades.LisOverComp4;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */

/**
 *
 * @author geanina.foanta
 */
public class PrincipalPrueba extends javax.swing.JFrame 
{

    Conexion con = new Conexion();
    Connection conet;
    
    Statement st;
    ResultSet rs;
    
    private int codigoNomEmpresa = -1;
    
    Object[] cabecera = new Object[]{"Nombre de la empresa", "Telefono", "Email"};
    DefaultTableModel miModelo = new DefaultTableModel(null, cabecera);
    
    /**
     * Creates new form PrincipalPrueba
     */
    public PrincipalPrueba() 
    {
        initComponents();
        
        tablaBuscarProveedor.setModel(miModelo);
        actualizarTablaBuscarProveedor();
        //cargarProveedores();
        
        //NO TOCAR (FUNCIONA)
        //cuaundo pulse un pais, en la tabla se mostrará los proveedores con ese pais seleccinado
        componenteNombreEmpresas.addLisOverComp4(new LisOverComp4() 
        {
            @Override
            public void accion(EvObjOverComp4 ev) 
            {
                System.out.println(ev.getMensaje());
                codigoNomEmpresa = ev.getSelectedCode();
                //buscarProveedor(codigoNomEmpresa);
            }
        });
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
    
    public void actualizarTablaBuscarProveedor()
    {
        
        
        //creamos la tabla con el sql
        String sqlDatos = "SELECT P.NOMBRE AS NombreProveedor, "
                + "P.NOMCONTACTO AS NombreContacto, "
                + "P.TELEFONO, "
                + "P.EMAILCONTACTO, "
                + "PA.NOMBRE AS NombrePais, "
                + "M.NOMBRE AS NombreMunicipio "
                + "FROM PROVEEDORES P, MUNICIPIOS M, PAISES PA "
                + "WHERE P.CODIGOMUNICIPIO = M.CODIGO AND P.CODIGOPAIS = PA.CODIGO";

        try
        {
            conet=con.abrirConexion();
            st=conet.createStatement();
            rs=st.executeQuery(sqlDatos);
            
            
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            
            miModelo = new DefaultTableModel(null, new Object[]{"Proveedor", "Nombre de contacto", "Telefono", "Correo", "Pais", "Municipio"})
            {
                @Override
                public Class<?> getColumnClass(int columnIndex) 
                {
                    // Devolver la clase de la columna, ImageIcon para las columnas de imágenes
                    return columnIndex >= 6 ? ImageIcon.class : super.getColumnClass(columnIndex);
                }

                @Override
                public boolean isCellEditable(int row, int column) 
                {
                    // Hacer las columnas de no sean editables
                    return column >= 0 ? false : super.isCellEditable(row, column);
                }
            };
            tablaBuscarProveedor.setRowHeight(26);
            tablaBuscarProveedor.setModel(miModelo);
            miModelo.setRowCount(0);
            
            
            Object [] proveedores = new Object[9];
            miModelo = (DefaultTableModel) tablaBuscarProveedor.getModel();
            
            
            // Configurar la tabla con el nuevo modelo
            tablaBuscarProveedor.setRowHeight(26);
            tablaBuscarProveedor.setModel(miModelo);
            miModelo.setRowCount(0);
            
            
            while (rs.next()) 
            {
                proveedores[0] = rs.getString("NombreProveedor");
                proveedores[1] = rs.getString("NombreContacto");
                proveedores[2] = rs.getString("TELEFONO");
                proveedores[3] = rs.getString("EMAILCONTACTO");
                proveedores[4] = rs.getString("NombrePais");
                proveedores[5] = rs.getString("NombreMunicipio");
                miModelo.addRow(proveedores);
            }
            
            tablaBuscarProveedor.setModel(miModelo);
        } 
        catch (SQLException e) 
        {
            e.printStackTrace();
        } 
        finally 
        {
            try 
            {
                if (rs != null) rs.close();
                if (st != null) st.close();
                if (conet != null) conet.close();
            } 
            catch (SQLException ex) 
            {
                ex.printStackTrace();
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
        tablaBuscarProveedor = new javax.swing.JTable();
        componenteNombreEmpresas = new propiedades.Componente4();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("PruebaPrincipal");

        jScrollPane1.setViewportView(tablaBuscarProveedor);

        componenteNombreEmpresas.setEtiqueta("Nombre Empresas  :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 926, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(componenteNombreEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(componenteNombreEmpresas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(PrincipalPrueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalPrueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalPrueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalPrueba.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalPrueba().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private propiedades.Componente4 componenteNombreEmpresas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaBuscarProveedor;
    // End of variables declaration//GEN-END:variables
}
