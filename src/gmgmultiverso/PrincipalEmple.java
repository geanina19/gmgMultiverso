/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gmgmultiverso;

import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatArcIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatArcOrangeIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCarbonIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatDarkPurpleIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatHiberbeeDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatHighContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.FlatLightFlatIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatDraculaContrastIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatMaterialLighterIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatSolarizedDarkContrastIJTheme;
import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.PedidoConNombreDao;
import gmgmultiverso.model.PedidoConNombre;
import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.help.JHelp;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableRowSorter;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author monic
 */
public class PrincipalEmple extends javax.swing.JFrame {
    //CONEXION
    ManagerConexion con = new ManagerConexion();
    Connection conet;  
    Statement st;
    ResultSet rs;
    
    private DefaultTableModel miModelo;
    
    int codigoEmpleado;

    PedidoConNombreDao pedidoCompleto = new PedidoConNombreDao(con);
    
    //para informe
    String miwhere = "";
    
    private TableRowSorter<DefaultTableModel> sorter;
    public PrincipalEmple(){
        initComponents();
    }
    
    public PrincipalEmple(java.awt.Frame parent, boolean modal,int codigoEmpleado) {
        initComponents();
        this.codigoEmpleado = codigoEmpleado;
        anadirDatosTabla(codigoEmpleado);
        this.setIconImage(getIconImage());
        this.getContentPane().setBackground(new java.awt.Color(250, 240, 230));
        
                // Obtener y mostrar el nombre del empleado logueado
        String nombreEmpleado = pedidoCompleto.obtenerNombreEmpleado(codigoEmpleado);
        if (nombreEmpleado != null) {
            labelInicio.setText("¡Hola " + nombreEmpleado + "!");
        } else {
            labelInicio.setText("Bienvenido");
        }
        
        sorter = new TableRowSorter<>(miModelo);
        tablePedidos.setRowSorter(sorter);
        tablePedidos.setBackground(new java.awt.Color(255, 218, 185));
        
        //La pantalla se abra en el centro
        this.setLocationRelativeTo(null);
        tablePedidos.setRowHeight(50); // Ajusta la altura de las filas
        tablePedidos.getColumnModel().getColumn(0).setPreferredWidth(25); // Ajusta el ancho de la primera columna
     //   tabla.setEnabled(false);
        tablePedidos.setModel(miModelo);
        // Esto hace que la tabla no sea editable
        tablePedidos.setDefaultEditor(Object.class, null); 
        // Esto permite la selección de una sola fila

        //----------------JAVAHELP----------------------------
    
        String AYUDA_HS = "ayuda/helpset2.hs";
        try 
        {
            ClassLoader cl = getClass().getClassLoader();
            URL ayudaURL = cl.getResource(AYUDA_HS);
            if (ayudaURL != null) 
            {
                HelpSet helpset = new HelpSet(null, ayudaURL);
                HelpBroker hb = helpset.createHelpBroker();
                JHelp jhelp = new JHelp(helpset);
                //jhelp.setCurrentID("inicio");
                hb.enableHelpOnButton(ayuda, "codEmple", helpset);
            } 
            else 
            {
                System.err.println("No se pudo encontrar el archivo de ayuda: " + AYUDA_HS);
            }
        } 
        catch (HelpSetException ex) 
        {
            System.err.println("Error al cargar la ayuda: " + ex);
        }
                  
    }
    
    @Override
    public Image getIconImage() {
        URL url = getClass().getResource("/imagenes/planeta.png") ;
        if (url != null){
            return Toolkit.getDefaultToolkit().getImage(url) ;
        } else {
            System.err.println("Resource not found: /imagenes/planeta.png");
            return  null;
        }
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

    public void anadirDatosTabla(int idEmpleado) {
        System.out.println("Añadiendo datos para el empleado con ID: " + idEmpleado); // Mensaje de depuración
        // Obtener lista de pedidos
        List<PedidoConNombre> pedidos = pedidoCompleto.listarPedidosPorIdEmpleado(idEmpleado); // Filtrar por idEmpleado

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
        tablePedidos.setModel(miModelo);
        
        /*****/
        // Crear y aplicar el renderizador de celda centrado
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        // Aplicar el renderizador a cada columna excepto la de imagen
        TableColumnModel columnModel = tablePedidos.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            if (i != 4 && i != 6) { 
                columnModel.getColumn(i).setCellRenderer(centerRenderer);
            }
        }
        // PARA ENCABEZADOS
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer();
        headerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        // cambia el color de fondo de los encabezados
        headerRenderer.setBackground(new java.awt.Color(255, 160, 122)); 
        // poner el texto en negrita
        headerRenderer.setFont(headerRenderer.getFont().deriveFont(Font.BOLD)); 
        // Aplicar el renderizador de encabezado a cada columna
        JTableHeader tableHeader = tablePedidos.getTableHeader();
        tableHeader.setDefaultRenderer(headerRenderer);
       

        // Añadir el MouseListener para el evento de clic
        tablePedidos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int columnaModificar = tablePedidos.getColumnModel().getColumnIndex("Editar");
                int fila = tablePedidos.rowAtPoint(e.getPoint());

                if (fila >= 0 && tablePedidos.columnAtPoint(e.getPoint()) == columnaModificar) {
                    // Obtén el valor de la columna "CódigoEmpleado"
                    int columnaCodigoEmpleado = tablePedidos.getColumnModel().getColumnIndex("Numero de pedido");
                    int codigoEmpleado = (int) tablePedidos.getValueAt(fila, columnaCodigoEmpleado);

                    // Abre la ventana de edición con el código del empleado
                    abrirVentanaPedido(codigoEmpleado, idEmpleado);
                }
            }
        });

    }

    
        /*-------------- MODIFICAR PEDIDO --------------*/

    private void abrirVentanaPedido(int codigoPedido, int codigoEmpleado) {
        
        FrameEditarPedido panelEditar = new FrameEditarPedido(this, true, codigoPedido, codigoEmpleado,this);
        panelEditar.setVisible(true);               
    }
    
    /***********ACTUALIZAR TABLA***********/
    public void actualizarTabla() {
        anadirDatosTabla(codigoEmpleado);
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
        tablePedidos = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        textNombre = new javax.swing.JTextField();
        buscarButton = new javax.swing.JButton();
        dateFiltro = new com.toedter.calendar.JDateChooser();
        buttonReiniciar = new javax.swing.JButton();
        buttonInformePedido = new javax.swing.JButton();
        labelInicio = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuCerrarSesion = new javax.swing.JMenuItem();
        menuCerrar = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        ayuda = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuOscuro = new javax.swing.JMenuItem();
        menuClaro = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        temaOp1 = new javax.swing.JMenuItem();
        temaOp2 = new javax.swing.JMenuItem();
        temaOp3 = new javax.swing.JMenuItem();
        temaOp10 = new javax.swing.JMenuItem();
        temaOp9 = new javax.swing.JMenuItem();
        temaOp8 = new javax.swing.JMenuItem();
        temaOp7 = new javax.swing.JMenuItem();
        temaOp6 = new javax.swing.JMenuItem();
        temaOp5 = new javax.swing.JMenuItem();
        temaOp4 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        menuItemPerfil = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gestión de Pedidos");
        setBackground(new java.awt.Color(255, 204, 153));
        setResizable(false);

        tablePedidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablePedidos);

        jPanel1.setBackground(new java.awt.Color(255, 245, 238));

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

        buttonReiniciar.setText("Reiniciar");
        buttonReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonReiniciarActionPerformed(evt);
            }
        });

        buttonInformePedido.setText("Crear informe");
        buttonInformePedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonInformePedidoActionPerformed(evt);
            }
        });

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(buscarButton)
                        .addGap(65, 65, 65)
                        .addComponent(buttonReiniciar))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(textNombre)
                            .addComponent(dateFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE))
                        .addGap(214, 214, 214)
                        .addComponent(buttonInformePedido)))
                .addContainerGap(440, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(75, 75, 75)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(textNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(72, 72, 72)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel1)
                        .addComponent(dateFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(buttonInformePedido))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 71, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buscarButton)
                    .addComponent(buttonReiniciar))
                .addGap(51, 51, 51))
        );

        labelInicio.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        labelInicio.setForeground(new java.awt.Color(255, 160, 122));

        jMenu1.setText("Archivo");

        menuCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/x(roja).png"))); // NOI18N
        menuCerrarSesion.setText("Cerrar sesión");
        menuCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCerrarSesionActionPerformed(evt);
            }
        });
        jMenu1.add(menuCerrarSesion);

        menuCerrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrarApp.png"))); // NOI18N
        menuCerrar.setText("Cerrar aplicación");
        menuCerrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCerrarActionPerformed(evt);
            }
        });
        jMenu1.add(menuCerrar);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Ayuda");

        ayuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pregunta.png"))); // NOI18N
        ayuda.setText("Ver ayuda");
        ayuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ayudaActionPerformed(evt);
            }
        });
        jMenu2.add(ayuda);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Tema");

        menuOscuro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/luna.png"))); // NOI18N
        menuOscuro.setText("Oscuro");
        menuOscuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuOscuroActionPerformed(evt);
            }
        });
        jMenu3.add(menuOscuro);

        menuClaro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sol.png"))); // NOI18N
        menuClaro.setText("Claro");
        menuClaro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuClaroActionPerformed(evt);
            }
        });
        jMenu3.add(menuClaro);

        jMenu5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/gamaColores.png"))); // NOI18N
        jMenu5.setText("Más ...");

        temaOp1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blancoAzul.png"))); // NOI18N
        temaOp1.setText("Blanco - Azul Oscuro");
        temaOp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp1ActionPerformed(evt);
            }
        });
        jMenu5.add(temaOp1);

        temaOp2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blancoAzulClaro.png"))); // NOI18N
        temaOp2.setText("Oscuro Claro - Azul Claro");
        temaOp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp2ActionPerformed(evt);
            }
        });
        jMenu5.add(temaOp2);

        temaOp3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/oscuroMorado.png"))); // NOI18N
        temaOp3.setText("Oscuro - Morado ");
        temaOp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp3ActionPerformed(evt);
            }
        });
        jMenu5.add(temaOp3);

        temaOp10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/grisAzulClaro.png"))); // NOI18N
        temaOp10.setText("Gris - Azul Claro ");
        temaOp10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp10ActionPerformed(evt);
            }
        });
        jMenu5.add(temaOp10);

        temaOp9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blancoVerde.png"))); // NOI18N
        temaOp9.setText("Blanco - Verde ");
        temaOp9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp9ActionPerformed(evt);
            }
        });
        jMenu5.add(temaOp9);

        temaOp8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/verdeAzulado.png"))); // NOI18N
        temaOp8.setText("Verde Azulado ");
        temaOp8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp8ActionPerformed(evt);
            }
        });
        jMenu5.add(temaOp8);

        temaOp7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blancoNaranja.png"))); // NOI18N
        temaOp7.setText("Blanco - Naranja ");
        temaOp7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp7ActionPerformed(evt);
            }
        });
        jMenu5.add(temaOp7);

        temaOp6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blancoAzulClaro.png"))); // NOI18N
        temaOp6.setText("Blanco - Azul Claro");
        temaOp6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp6ActionPerformed(evt);
            }
        });
        jMenu5.add(temaOp6);

        temaOp5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/contraste.png"))); // NOI18N
        temaOp5.setText("Contraste");
        temaOp5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp5ActionPerformed(evt);
            }
        });
        jMenu5.add(temaOp5);

        temaOp4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/grisNaranja.png"))); // NOI18N
        temaOp4.setText("Gris - Naranja");
        temaOp4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp4ActionPerformed(evt);
            }
        });
        jMenu5.add(temaOp4);

        jMenu3.add(jMenu5);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Perfil");

        menuItemPerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario.png"))); // NOI18N
        menuItemPerfil.setText("Ver perfil");
        menuItemPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemPerfilActionPerformed(evt);
            }
        });
        jMenu4.add(menuItemPerfil);

        jMenuBar1.add(jMenu4);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 419, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(59, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(labelInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuCerrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCerrarActionPerformed
        // TODO add your handling code here:
        int opcion = JOptionPane.showConfirmDialog(null, "¿Seguro que quieres cerrar la ventana?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (opcion == JOptionPane.YES_OPTION) {
           dispose();
        }
    }//GEN-LAST:event_menuCerrarActionPerformed

    private void menuOscuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuOscuroActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatArcDarkIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);

        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }   
        
    }//GEN-LAST:event_menuOscuroActionPerformed

    private void menuClaroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuClaroActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatCyanLightIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_menuClaroActionPerformed

    private void temaOp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp1ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatArcIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp1ActionPerformed

    private void temaOp2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp2ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatCarbonIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp2ActionPerformed

    private void temaOp3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp3ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatDarkPurpleIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp3ActionPerformed

    private void temaOp10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp10ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatDraculaContrastIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
           
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp10ActionPerformed

    private void temaOp9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp9ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp9ActionPerformed

    private void temaOp8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp8ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatSolarizedDarkContrastIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp8ActionPerformed

    private void temaOp7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp7ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp7ActionPerformed

    private void temaOp6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp6ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatLightFlatIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp6ActionPerformed

    private void temaOp5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp5ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatHighContrastIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp5ActionPerformed

    private void temaOp4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp4ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatHiberbeeDarkIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp4ActionPerformed

    private void menuCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCerrarSesionActionPerformed
        // TODO add your handling code here:
        this.dispose();
        LoginEmple log = new LoginEmple();
        log.setVisible(true);
        
        
              
    }//GEN-LAST:event_menuCerrarSesionActionPerformed

    private void buscarButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buscarButtonActionPerformed
        String nombre = textNombre.getText();
        java.util.Date date = dateFiltro.getDate();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = (date != null) ? sdf.format(date) : "";

        if (nombre.isEmpty() && date == null) {
            sorter.setRowFilter(null);
        } else if (nombre.isEmpty()) {
            sorter.setRowFilter(RowFilter.regexFilter(dateStr, 2));
        } else if (date == null) {
            sorter.setRowFilter(RowFilter.regexFilter("(?i)" + nombre, 1));
        } else {
            sorter.setRowFilter(RowFilter.andFilter(
                List.of(RowFilter.regexFilter("(?i)" + nombre, 1),
                        RowFilter.regexFilter(dateStr, 2))
            ));
        }
    }//GEN-LAST:event_buscarButtonActionPerformed

    private void menuItemPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemPerfilActionPerformed
        FramePerfilEmple frPerfil = new FramePerfilEmple(this, true, codigoEmpleado,this);
        frPerfil.setVisible(true);
        this.setEnabled(true);
//        frPerfilsetAlwaysOnTop(true);
//        this.dispose();
        actualizarTabla();
    }//GEN-LAST:event_menuItemPerfilActionPerformed

    private void buttonReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonReiniciarActionPerformed
        textNombre.setText("");
        dateFiltro.setDate(null);
        sorter.setRowFilter(null);
        actualizarTabla();
    }//GEN-LAST:event_buttonReiniciarActionPerformed

    private void buttonInformePedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonInformePedidoActionPerformed
        // Obtener el nombre del cliente ingresado en el textField
        String nombreCliente = textNombre.getText().trim();

        // Obtener la fecha de pedido seleccionada en el datePicker
        Date fechaPedido = dateFiltro.getDate();

        // Verificar si la fecha de pedido no es nula
        if (fechaPedido == null) {
            JOptionPane.showMessageDialog(this, "Por favor, seleccione una fecha de pedido.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Convertir la fecha de pedido a formato de cadena (yyyy-MM-dd)
        String fechaPedidoStr = new SimpleDateFormat("yyyy-MM-dd").format(fechaPedido);

        Connection conexion = null;
        try {
            Class.forName("org.hsqldb.jdbcDriver");
            conexion = DriverManager.getConnection("jdbc:hsqldb:hsql://localhost", "SA", "SA");
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(BuscarProveedor.class.getName()).log(Level.SEVERE, null, ex);
            return;
        }

        InputStream vinculoarchivo = getClass().getResourceAsStream("Pedidos.jrxml");

        if (vinculoarchivo != null) {
            JasperReport jr = null;
            try {
                String continuarConsulta = " ";

                // Construir la cadena de consulta basada en los parámetros proporcionados
                if (!nombreCliente.isEmpty()) {
                    continuarConsulta += " AND nombre_cliente LIKE '%" + nombreCliente + "%'";
                }

                continuarConsulta += " fecha_pedido = '" + fechaPedidoStr + "'";

                Map<String, Object> parametros = new HashMap<>();
                parametros.put("consulta", continuarConsulta);
                parametros.put("imagen", "gmgmultiverso/logo.png");

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
            JOptionPane.showMessageDialog(this, "Error: No se pudo cargar el archivo pedidos.jrxml", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_buttonInformePedidoActionPerformed

    private void ayudaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ayudaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ayudaActionPerformed

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
            java.util.logging.Logger.getLogger(PrincipalEmple.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalEmple.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalEmple.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalEmple.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalEmple().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ayuda;
    private javax.swing.JButton buscarButton;
    private javax.swing.JButton buttonInformePedido;
    private javax.swing.JButton buttonReiniciar;
    private com.toedter.calendar.JDateChooser dateFiltro;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelInicio;
    private javax.swing.JMenuItem menuCerrar;
    private javax.swing.JMenuItem menuCerrarSesion;
    private javax.swing.JMenuItem menuClaro;
    private javax.swing.JMenuItem menuItemPerfil;
    private javax.swing.JMenuItem menuOscuro;
    private javax.swing.JTable tablePedidos;
    private javax.swing.JMenuItem temaOp1;
    private javax.swing.JMenuItem temaOp10;
    private javax.swing.JMenuItem temaOp2;
    private javax.swing.JMenuItem temaOp3;
    private javax.swing.JMenuItem temaOp4;
    private javax.swing.JMenuItem temaOp5;
    private javax.swing.JMenuItem temaOp6;
    private javax.swing.JMenuItem temaOp7;
    private javax.swing.JMenuItem temaOp8;
    private javax.swing.JMenuItem temaOp9;
    private javax.swing.JTextField textNombre;
    // End of variables declaration//GEN-END:variables
}
