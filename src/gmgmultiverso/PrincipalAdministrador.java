/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package gmgmultiverso;

import com.formdev.flatlaf.FlatLaf;
import com.formdev.flatlaf.intellijthemes.FlatCyanLightIJTheme;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.*;
import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.EmpleadoDao;
import gmgmultiverso.model.Empleado;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.URL;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.help.HelpBroker;
import javax.help.HelpSet;
import javax.help.HelpSetException;
import javax.help.JHelp;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author geanina.foanta
 */
public class PrincipalAdministrador extends javax.swing.JFrame 
{
    
    BuscarProveedor buscarproveedor;
    private int idEmpleado;
    private EmpleadoDao empleadoDao;

    /**
   
    /**
     * Creates new form PrincipalGmgMultiverso
     */
    
    public PrincipalAdministrador() 
    {
        
    }
    
    public PrincipalAdministrador(int idEmpleado) 
    {
        initComponents();
        this.idEmpleado = idEmpleado;
        this.setSize(1326, 670);
        
        this.empleadoDao = new EmpleadoDao(new ManagerConexion());
        
        //para poner el logo del planeta en el frame
        this.setIconImage(getIconImage());
        
        //La pantalla se abra en el centro
        this.setLocationRelativeTo(null);
        //Para que el logo esté centrado en la pantalla
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.CENTER;
        panelPrincipal.add(labelLogo, gbc);
        
        // Obtener el nombre del empleado usando el EmpleadoDao
        String nombreEmpleado = empleadoDao.obtenerNombreEmpleado(idEmpleado);
        itemCerrarSesion.setText("Cerrar sesión de " + nombreEmpleado);
        
        //----------------JAVAHELP----------------------------
    
        String AYUDA_HS = "ayuda/helpset.hs";
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
                hb.enableHelpOnButton(ayuda, "codProveedor", helpset);
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
    
    //----------------MÉTODOS---------------------------
    
    //para poner el logo del planeta en el frame
    @Override
    public Image getIconImage() {
        URL url = getClass().getResource("/imagenes/planeta.png");
        if (url != null) {
            return Toolkit.getDefaultToolkit().getImage(url);
        } else {
            System.err.println("No se encuentra la imagen : /imagenes/planeta.png");
            return null;
        }
    }
    
    //------Cambiar Logo dependiendo del tema
    public void updateLogoGrande(String theme) {
        String logoPath = theme.equals("oscuro") ? "/imagenes/logoBlanco.png" : "/imagenes/logoGrande.png";
        labelLogo.setIcon(new ImageIcon(getClass().getResource(logoPath)));
        
    }
    
    public void mostrarPanel(JPanel panel) {
        panel.setSize(panelPrincipal.getSize());
        panelPrincipal.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        panelPrincipal.add(panel, gbc);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    public void mostrarEditarProveedor(int codigoProveedor, BuscarProveedor buscarProveedor) {
        EditarProveedor ep = new EditarProveedor(codigoProveedor, buscarProveedor);
        ep.setSize(panelPrincipal.getSize());
        
        panelPrincipal.removeAll();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        
        panelPrincipal.add(ep, gbc);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
        
    }
    
    public void mostrarEditarEmpleado(int codigoEmpleado, BuscarEmpleado buscarEmpleado) {
        EditarEmpleado ee = new EditarEmpleado(codigoEmpleado, buscarEmpleado);
        ee.setSize(panelPrincipal.getSize());
        
        panelPrincipal.removeAll();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        
        panelPrincipal.add(ee, gbc);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    public void mostrarEditarProducto(int codigoProducto, BuscarProducto buscarproducto) {
        EditarProducto ep = new EditarProducto(codigoProducto, buscarproducto);
        ep.setSize(panelPrincipal.getSize());
        
        panelPrincipal.removeAll();
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;
        
        panelPrincipal.add(ep, gbc);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
    }
    
    
    public JPanel getPanelPrincipal() {
        return panelPrincipal;
    }
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        panelPrincipal = new javax.swing.JPanel();
        labelLogo = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        menuArchivo = new javax.swing.JMenu();
        itemCerrarSesion = new javax.swing.JMenuItem();
        itemCerrarAplicacion = new javax.swing.JMenuItem();
        menuGestion = new javax.swing.JMenu();
        menuProveedores = new javax.swing.JMenu();
        itemBuscarProveedor = new javax.swing.JMenuItem();
        itemAnadirProveedor = new javax.swing.JMenuItem();
        menuEmpleados = new javax.swing.JMenu();
        itemBuscarEmpleado = new javax.swing.JMenuItem();
        itemAnadirEmpleado = new javax.swing.JMenuItem();
        menuProductos = new javax.swing.JMenu();
        itemBuscarProducto = new javax.swing.JMenuItem();
        itemAnadirProducto = new javax.swing.JMenuItem();
        menuPedidos = new javax.swing.JMenu();
        itemVerPedido = new javax.swing.JMenuItem();
        menuAyuda = new javax.swing.JMenu();
        ayuda = new javax.swing.JMenuItem();
        menuTema = new javax.swing.JMenu();
        itemOscuro = new javax.swing.JMenuItem();
        itemClaro = new javax.swing.JMenuItem();
        itemMas = new javax.swing.JMenu();
        temaOp1 = new javax.swing.JMenuItem();
        temaOp2 = new javax.swing.JMenuItem();
        temaOp3 = new javax.swing.JMenuItem();
        temaOp4 = new javax.swing.JMenuItem();
        temaOp5 = new javax.swing.JMenuItem();
        temaOp6 = new javax.swing.JMenuItem();
        temaOp7 = new javax.swing.JMenuItem();
        temaOp8 = new javax.swing.JMenuItem();
        temaOp9 = new javax.swing.JMenuItem();
        temaOp10 = new javax.swing.JMenuItem();
        menuPerfil = new javax.swing.JMenu();
        itemPerfil = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Principal GmgMultiverso");
        setBackground(new java.awt.Color(255, 255, 255));

        panelPrincipal.setBackground(new java.awt.Color(255, 255, 255));
        panelPrincipal.setOpaque(false);
        panelPrincipal.setLayout(new java.awt.GridBagLayout());

        labelLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/logoGrande.png"))); // NOI18N
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(90, 390, 52, 201);
        panelPrincipal.add(labelLogo, gridBagConstraints);

        menuArchivo.setText("Archivo");

        itemCerrarSesion.setForeground(new java.awt.Color(255, 0, 0));
        itemCerrarSesion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/x(roja).png"))); // NOI18N
        itemCerrarSesion.setText("Cerrar sesión");
        itemCerrarSesion.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                itemCerrarSesionMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                itemCerrarSesionMouseExited(evt);
            }
        });
        itemCerrarSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCerrarSesionActionPerformed(evt);
            }
        });
        menuArchivo.add(itemCerrarSesion);

        itemCerrarAplicacion.setForeground(new java.awt.Color(255, 0, 0));
        itemCerrarAplicacion.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/cerrarApp.png"))); // NOI18N
        itemCerrarAplicacion.setText("Cerrar aplicación");
        itemCerrarAplicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCerrarAplicacionActionPerformed(evt);
            }
        });
        menuArchivo.add(itemCerrarAplicacion);

        jMenuBar1.add(menuArchivo);

        menuGestion.setText("Gestión");

        menuProveedores.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/proveedores.png"))); // NOI18N
        menuProveedores.setText("Proveedores");

        itemBuscarProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.png"))); // NOI18N
        itemBuscarProveedor.setText("Buscar");
        itemBuscarProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBuscarProveedorActionPerformed(evt);
            }
        });
        menuProveedores.add(itemBuscarProveedor);

        itemAnadirProveedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/signoMas.png"))); // NOI18N
        itemAnadirProveedor.setText("Añadir");
        itemAnadirProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAnadirProveedorActionPerformed(evt);
            }
        });
        menuProveedores.add(itemAnadirProveedor);

        menuGestion.add(menuProveedores);

        menuEmpleados.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/empleados.png"))); // NOI18N
        menuEmpleados.setText("Empleados");

        itemBuscarEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.png"))); // NOI18N
        itemBuscarEmpleado.setText("Buscar");
        itemBuscarEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBuscarEmpleadoActionPerformed(evt);
            }
        });
        menuEmpleados.add(itemBuscarEmpleado);

        itemAnadirEmpleado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/signoMas.png"))); // NOI18N
        itemAnadirEmpleado.setText("Añadir");
        itemAnadirEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAnadirEmpleadoActionPerformed(evt);
            }
        });
        menuEmpleados.add(itemAnadirEmpleado);

        menuGestion.add(menuEmpleados);

        menuProductos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/carritoAColor.png"))); // NOI18N
        menuProductos.setText("Productos");

        itemBuscarProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/lupa.png"))); // NOI18N
        itemBuscarProducto.setText("Buscar");
        itemBuscarProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemBuscarProductoActionPerformed(evt);
            }
        });
        menuProductos.add(itemBuscarProducto);

        itemAnadirProducto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/signoMas.png"))); // NOI18N
        itemAnadirProducto.setText("Añadir");
        itemAnadirProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAnadirProductoActionPerformed(evt);
            }
        });
        menuProductos.add(itemAnadirProducto);

        menuGestion.add(menuProductos);

        menuPedidos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgEmple/pedido.png"))); // NOI18N
        menuPedidos.setText("Pedidos");

        itemVerPedido.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/comida.png"))); // NOI18N
        itemVerPedido.setText("Ver Pedidos");
        itemVerPedido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemVerPedidoActionPerformed(evt);
            }
        });
        menuPedidos.add(itemVerPedido);

        menuGestion.add(menuPedidos);

        jMenuBar1.add(menuGestion);

        menuAyuda.setText("Ayuda");

        ayuda.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        ayuda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pregunta.png"))); // NOI18N
        ayuda.setText("Ver ayuda");
        ayuda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ayudaActionPerformed(evt);
            }
        });
        menuAyuda.add(ayuda);

        jMenuBar1.add(menuAyuda);

        menuTema.setText("Tema");

        itemOscuro.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemOscuro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/luna.png"))); // NOI18N
        itemOscuro.setText("Oscuro");
        itemOscuro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemOscuroActionPerformed(evt);
            }
        });
        menuTema.add(itemOscuro);

        itemClaro.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_C, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemClaro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/sol.png"))); // NOI18N
        itemClaro.setText("Claro");
        itemClaro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemClaroActionPerformed(evt);
            }
        });
        menuTema.add(itemClaro);

        itemMas.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/gamaColores.png"))); // NOI18N
        itemMas.setText("Más ...");

        temaOp1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blancoAzul.png"))); // NOI18N
        temaOp1.setText("Blanco - Azul Oscuro");
        temaOp1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp1ActionPerformed(evt);
            }
        });
        itemMas.add(temaOp1);

        temaOp2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/oscuroAzulClaro.png"))); // NOI18N
        temaOp2.setText("Oscuro - Azul Claro");
        temaOp2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp2ActionPerformed(evt);
            }
        });
        itemMas.add(temaOp2);

        temaOp3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/oscuroMorado.png"))); // NOI18N
        temaOp3.setText("Oscuro - Morado ");
        temaOp3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp3ActionPerformed(evt);
            }
        });
        itemMas.add(temaOp3);

        temaOp4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/grisNaranja.png"))); // NOI18N
        temaOp4.setText("Gris - Naranja");
        temaOp4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp4ActionPerformed(evt);
            }
        });
        itemMas.add(temaOp4);

        temaOp5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/contraste.png"))); // NOI18N
        temaOp5.setText("Contraste");
        temaOp5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp5ActionPerformed(evt);
            }
        });
        itemMas.add(temaOp5);

        temaOp6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blancoAzulClaro.png"))); // NOI18N
        temaOp6.setText("Blanco - Azul Claro");
        temaOp6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp6ActionPerformed(evt);
            }
        });
        itemMas.add(temaOp6);

        temaOp7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blancoNaranja.png"))); // NOI18N
        temaOp7.setText("Blanco - Naranja ");
        temaOp7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp7ActionPerformed(evt);
            }
        });
        itemMas.add(temaOp7);

        temaOp8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/verdeAzulado.png"))); // NOI18N
        temaOp8.setText("Verde Azulado ");
        temaOp8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp8ActionPerformed(evt);
            }
        });
        itemMas.add(temaOp8);

        temaOp9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/blancoVerde.png"))); // NOI18N
        temaOp9.setText("Blanco - Verde ");
        temaOp9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp9ActionPerformed(evt);
            }
        });
        itemMas.add(temaOp9);

        temaOp10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/grisAzulClaro.png"))); // NOI18N
        temaOp10.setText("Gris - Azul Claro ");
        temaOp10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                temaOp10ActionPerformed(evt);
            }
        });
        itemMas.add(temaOp10);

        menuTema.add(itemMas);

        jMenuBar1.add(menuTema);

        menuPerfil.setText("Perfil");
        menuPerfil.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                menuPerfilMouseClicked(evt);
            }
        });

        itemPerfil.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_P, java.awt.event.InputEvent.CTRL_DOWN_MASK));
        itemPerfil.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagenes/usuario.png"))); // NOI18N
        itemPerfil.setText("Ver perfil");
        itemPerfil.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemPerfilActionPerformed(evt);
            }
        });
        menuPerfil.add(itemPerfil);

        jMenuBar1.add(menuPerfil);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelPrincipal, javax.swing.GroupLayout.DEFAULT_SIZE, 673, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void itemClaroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemClaroActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatCyanLightIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogoGrande("blanco");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_itemClaroActionPerformed

    private void itemOscuroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemOscuroActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatArcDarkIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogoGrande("oscuro");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_itemOscuroActionPerformed

    private void temaOp1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp1ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatArcIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogoGrande("blanco"); // Cambia el logo al negro
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
            updateLogoGrande("oscuro"); // Cambia el logo al blanco
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
            updateLogoGrande("oscuro"); // Cambia el logo al blanco
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp3ActionPerformed

    private void temaOp4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp4ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatHiberbeeDarkIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogoGrande("oscuro"); // Cambia el logo al blanco
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp4ActionPerformed

    private void temaOp5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp5ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatHighContrastIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogoGrande("oscuro"); // Cambia el logo al blanco
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp5ActionPerformed

    private void temaOp6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp6ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatLightFlatIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogoGrande("blanco"); // Cambia el logo al oscuro
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp6ActionPerformed

    private void temaOp7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp7ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatArcOrangeIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogoGrande("blanco"); // Cambia el logo al oscuro
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp7ActionPerformed

    private void temaOp8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp8ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatSolarizedDarkContrastIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogoGrande("oscuro"); // Cambia el logo al blanco
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp8ActionPerformed

    private void temaOp9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp9ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatMaterialLighterIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogoGrande("blanco"); // Cambia el logo al oscuro
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp9ActionPerformed

    private void temaOp10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_temaOp10ActionPerformed
        // TODO add your handling code here:
        try {
            UIManager.setLookAndFeel(new FlatDraculaContrastIJTheme());
            UIManager.put("TextComponent.arc", 100);
            SwingUtilities.updateComponentTreeUI(this);
            updateLogoGrande("oscuro"); // Cambia el logo al blanco
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }
    }//GEN-LAST:event_temaOp10ActionPerformed

    private void itemBuscarProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBuscarProveedorActionPerformed
        // TODO add your handling code here:
        /*
        BuscarProveedor pbp = new BuscarProveedor();
        pbp.setSize(1091,642);
        pbp.setLocation(0,0);
        
        getContentPane().removeAll();
        getContentPane().add(pbp,BorderLayout.CENTER);
        revalidate();
        repaint();
        
        
        
        panelPrincipal.removeAll();
        panelPrincipal.setLayout(new BorderLayout()); // Usar BorderLayout para centrar el panel
        BuscarProveedor principalBuscarProveedor = new BuscarProveedor();
        panelPrincipal.add(principalBuscarProveedor, BorderLayout.CENTER); // Añadir el panel al centro
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
        */

        BuscarProveedor pbp = new BuscarProveedor(this);
        pbp.setSize(panelPrincipal.getSize());

        // Remover todos los componentes y añadir pbp ocupando todo el espacio disponible horizontalmente
        panelPrincipal.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Establecer el peso en x para ocupar todo el espacio disponible horizontalmente
        gbc.weighty = 1.0; // Dejar el peso en y como 0 para que no ocupe espacio vertical adicional
        gbc.fill = GridBagConstraints.HORIZONTAL; // Permitir que el componente ocupe todo el ancho disponible pero no el alto
        gbc.anchor = GridBagConstraints.CENTER;

        panelPrincipal.add(pbp, gbc);
        revalidate();
        repaint();

    }//GEN-LAST:event_itemBuscarProveedorActionPerformed

    private void itemCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCerrarSesionActionPerformed
        // TODO add your handling code here:
        
        this.dispose();
        
        LoginAdmin loginAdmin = new LoginAdmin();
        loginAdmin.setVisible(true);
    }//GEN-LAST:event_itemCerrarSesionActionPerformed

    private void itemCerrarSesionMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemCerrarSesionMouseEntered
        
    }//GEN-LAST:event_itemCerrarSesionMouseEntered

    private void itemCerrarSesionMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_itemCerrarSesionMouseExited
        
    }//GEN-LAST:event_itemCerrarSesionMouseExited

    private void menuPerfilMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuPerfilMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_menuPerfilMouseClicked

    private void itemAnadirProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAnadirProveedorActionPerformed
        // TODO add your handling code here:
        
        AnadirProveedor ap = new AnadirProveedor();
        ap.setSize(panelPrincipal.getSize());

        // Remover todos los componentes y añadir ap ocupando todo el espacio disponible horizontalmente y verticalmente
        panelPrincipal.removeAll();
        panelPrincipal.setLayout(new GridBagLayout());

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new GridBagLayout());
        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.anchor = GridBagConstraints.CENTER;
        wrapperPanel.add(ap, innerGbc);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        panelPrincipal.add(wrapperPanel, gbc);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();

    
    }//GEN-LAST:event_itemAnadirProveedorActionPerformed

    private void itemBuscarEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBuscarEmpleadoActionPerformed
        // TODO add your handling code here:
        BuscarEmpleado be = new BuscarEmpleado(this);
        be.setSize(panelPrincipal.getSize());

        // Remover todos los componentes y añadir pbp ocupando todo el espacio disponible horizontalmente
        panelPrincipal.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Establecer el peso en x para ocupar todo el espacio disponible horizontalmente
        gbc.weighty = 1.0; // Dejar el peso en y como 0 para que no ocupe espacio vertical adicional
        gbc.fill = GridBagConstraints.HORIZONTAL; // Permitir que el componente ocupe todo el ancho disponible pero no el alto
        gbc.anchor = GridBagConstraints.CENTER;

        panelPrincipal.add(be, gbc);
        revalidate();
        repaint();
        
    }//GEN-LAST:event_itemBuscarEmpleadoActionPerformed

    private void itemAnadirEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAnadirEmpleadoActionPerformed
        // TODO add your handling code here:
        AnadirEmpleado ae = new AnadirEmpleado();
        ae.setSize(panelPrincipal.getSize());

        // Remover todos los componentes y añadir ap ocupando todo el espacio disponible horizontalmente y verticalmente
        panelPrincipal.removeAll();
        panelPrincipal.setLayout(new GridBagLayout());

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new GridBagLayout());
        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.anchor = GridBagConstraints.CENTER;
        wrapperPanel.add(ae, innerGbc);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        panelPrincipal.add(wrapperPanel, gbc);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();

    }//GEN-LAST:event_itemAnadirEmpleadoActionPerformed

    private void itemBuscarProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemBuscarProductoActionPerformed
        // TODO add your handling code here:
        BuscarProducto bp = new BuscarProducto(this);
        bp.setSize(panelPrincipal.getSize());

        // Remover todos los componentes y añadir pbp ocupando todo el espacio disponible horizontalmente
        panelPrincipal.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Establecer el peso en x para ocupar todo el espacio disponible horizontalmente
        gbc.weighty = 1.0; // Dejar el peso en y como 0 para que no ocupe espacio vertical adicional
        gbc.fill = GridBagConstraints.HORIZONTAL; // Permitir que el componente ocupe todo el ancho disponible pero no el alto
        gbc.anchor = GridBagConstraints.CENTER;

        panelPrincipal.add(bp, gbc);
        revalidate();
        repaint();
    }//GEN-LAST:event_itemBuscarProductoActionPerformed

    private void itemAnadirProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAnadirProductoActionPerformed
        // TODO add your handling code here:
        AnadirProducto anadirProducto = new AnadirProducto(this);
        anadirProducto.setSize(panelPrincipal.getSize());

        // Remover todos los componentes y añadir ap ocupando todo el espacio disponible horizontalmente y verticalmente
        panelPrincipal.removeAll();
        panelPrincipal.setLayout(new GridBagLayout());

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new GridBagLayout());
        GridBagConstraints innerGbc = new GridBagConstraints();
        innerGbc.gridx = 0;
        innerGbc.gridy = 0;
        innerGbc.anchor = GridBagConstraints.CENTER;
        wrapperPanel.add(anadirProducto, innerGbc);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.anchor = GridBagConstraints.CENTER;

        panelPrincipal.add(wrapperPanel, gbc);
        panelPrincipal.revalidate();
        panelPrincipal.repaint();
        
    }//GEN-LAST:event_itemAnadirProductoActionPerformed

    private void itemVerPedidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemVerPedidoActionPerformed
        // TODO add your handling code here:
        PanelPedidoAdmin p1Admin = new PanelPedidoAdmin(this);
        p1Admin.setSize(panelPrincipal.getSize());

        // Remover todos los componentes y añadir pbp ocupando todo el espacio disponible horizontalmente
        panelPrincipal.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Establecer el peso en x para ocupar todo el espacio disponible horizontalmente
        gbc.weighty = 1.0; // Dejar el peso en y como 0 para que no ocupe espacio vertical adicional
        gbc.fill = GridBagConstraints.HORIZONTAL; // Permitir que el componente ocupe todo el ancho disponible pero no el alto
        gbc.anchor = GridBagConstraints.CENTER;

        panelPrincipal.add(p1Admin, gbc);
        revalidate();
        repaint();
    }//GEN-LAST:event_itemVerPedidoActionPerformed

    private void itemPerfilActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemPerfilActionPerformed
        // TODO add your handling code here:
        PerfilAdministrador pa = new PerfilAdministrador(idEmpleado);
        pa.setSize(panelPrincipal.getSize());

        // Remover todos los componentes y añadir pbp ocupando todo el espacio disponible horizontalmente
        panelPrincipal.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0; // Establecer el peso en x para ocupar todo el espacio disponible horizontalmente
        gbc.weighty = 1.0; // Dejar el peso en y como 0 para que no ocupe espacio vertical adicional
        gbc.fill = GridBagConstraints.HORIZONTAL; // Permitir que el componente ocupe todo el ancho disponible pero no el alto
        gbc.anchor = GridBagConstraints.CENTER;

        panelPrincipal.add(pa, gbc);
        revalidate();
        repaint();
    }//GEN-LAST:event_itemPerfilActionPerformed

    private void itemCerrarAplicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCerrarAplicacionActionPerformed
        // TODO add your handling code here:
        System.exit(0);
    }//GEN-LAST:event_itemCerrarAplicacionActionPerformed

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
            java.util.logging.Logger.getLogger(PrincipalAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(PrincipalAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(PrincipalAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(PrincipalAdministrador.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new PrincipalAdministrador().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ayuda;
    private javax.swing.JMenuItem itemAnadirEmpleado;
    private javax.swing.JMenuItem itemAnadirProducto;
    private javax.swing.JMenuItem itemAnadirProveedor;
    private javax.swing.JMenuItem itemBuscarEmpleado;
    private javax.swing.JMenuItem itemBuscarProducto;
    private javax.swing.JMenuItem itemBuscarProveedor;
    private javax.swing.JMenuItem itemCerrarAplicacion;
    private javax.swing.JMenuItem itemCerrarSesion;
    private javax.swing.JMenuItem itemClaro;
    private javax.swing.JMenu itemMas;
    private javax.swing.JMenuItem itemOscuro;
    private javax.swing.JMenuItem itemPerfil;
    private javax.swing.JMenuItem itemVerPedido;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JLabel labelLogo;
    private javax.swing.JMenu menuArchivo;
    private javax.swing.JMenu menuAyuda;
    private javax.swing.JMenu menuEmpleados;
    private javax.swing.JMenu menuGestion;
    private javax.swing.JMenu menuPedidos;
    private javax.swing.JMenu menuPerfil;
    private javax.swing.JMenu menuProductos;
    private javax.swing.JMenu menuProveedores;
    private javax.swing.JMenu menuTema;
    private javax.swing.JPanel panelPrincipal;
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
    // End of variables declaration//GEN-END:variables
}
