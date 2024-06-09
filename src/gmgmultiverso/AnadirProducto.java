/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.ProductoConProveedorDao;
import gmgmultiverso.model.ProductoConProveedor;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import propiedades.EvObjOverEtiquetav2;
import propiedades.LisOverEtiquetav2;

/**
 *
 * @author geanina.foanta
 */
public class AnadirProducto extends javax.swing.JPanel {

    private ArrayList<String> listaCamposObligPorCompletar = new ArrayList<>();
    private String camposObligPorCompletar;
    ManagerConexion managerConexion = new ManagerConexion();
    Connection conet;
    Statement st;
    ResultSet rs;
    PrincipalAdministrador principalAdmin;
    
    private File imagenSeleccionada;

    private int codProveedor = -1;
    
    /**
     * Creates new form AnadirProducto
     */
    public AnadirProducto(PrincipalAdministrador principalAdmin) {
        initComponents();
        this.principalAdmin = principalAdmin;
        this.setSize(1091, 642);
        botonAnadir.setEnabled(false);
        visorErrores.setEditable(false);
        cargarProveedores();
        
        // Inicializar la lista con los nombres de los campos obligatorios
        listaCamposObligPorCompletar.add(componenteProveedores.getEtiqueta());
        listaCamposObligPorCompletar.add(componenteNombre.getEtiqueta());
        listaCamposObligPorCompletar.add(componentePrecio.getEtiqueta());
        listaCamposObligPorCompletar.add(componenteUnidadExistente.getEtiqueta());
        //listaCamposObligPorCompletar.add(botonImagen.getText());
        
        actualizarTextAreaVisorErrores();
        
        // Inicializar componentes adicionales
        botonImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
            }
        });

        // Agregar componentes de imagen al layout
        this.add(botonImagen);
        
        //-------------visor errores--------------------------
        //--------componenteNombre--------
        LisOverEtiquetav2 li = new LisOverEtiquetav2() {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) {
                if (error) {
                    visorErrores.append(componenteNombre.getEtiqueta());

                    // Si el campo está vacío, agrega el nombre del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) {
                        listaCamposObligPorCompletar.add(componenteNombre.getEtiqueta());
                    }

                    System.out.println("Nombre sin completar");
                } else {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componenteNombre.getEtiqueta());
/*
                    // Verificar si el nombre contiene números
                    if (textoAnadir.matches(".*\\d.*")) {
                        JOptionPane.showMessageDialog(null, "El nombre del producto no puede contener números.", "Error", JOptionPane.ERROR_MESSAGE);
                    }
*/
                    // Verificar si el nombre contiene caracteres no permitidos (excepto el espacio)
                    if (!textoAnadir.matches("[a-zA-Z\\s]+")) {
                        JOptionPane.showMessageDialog(null, "El nombre del producto solo puede contener letras y espacios.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    System.out.println("No se ha producido ningún error, textField nombre con contenido");
                    System.out.println("Nombre introducido : " + textoAnadir);
                }

                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componenteNombre.addLisOverEtiquetav2(li);



        
        //--------componentePrecio--------
        LisOverEtiquetav2 li2 = new LisOverEtiquetav2() {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) {
                if (error) {
                    visorErrores.append(componentePrecio.getEtiqueta());
                    // Si el campo está vacío, agrega el Precio del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) {
                        listaCamposObligPorCompletar.add(componentePrecio.getEtiqueta());
                    }

                    System.out.println("Precio sin completar");
                } else {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componentePrecio.getEtiqueta());

                    // Reemplazar cualquier coma por un punto en el texto del precio
                    textoAnadir = textoAnadir.replace(',', '.');

                    // Verificar si el precio contiene caracteres no permitidos
                    if (!textoAnadir.matches("[0-9.]+")) {
                        JOptionPane.showMessageDialog(null, "El precio solo puede contener números y punto (.)", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Convertir el texto del precio a un número
                        double precio = Double.parseDouble(textoAnadir);
                        // Verificar que el precio no sea mayor a 100
                        if (precio > 100) {
                            JOptionPane.showMessageDialog(null, "El precio no puede ser mayor a 100.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }

                    System.out.println("No se ha producido ningún error, textField Precio con contenido");
                    System.out.println("Precio introducido : " + textoAnadir);
                }

                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componentePrecio.addLisOverEtiquetav2(li2);



        
        //--------componenteUnidadExistente--------
        LisOverEtiquetav2 li3 = new LisOverEtiquetav2() {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) {
                if (error) {
                    visorErrores.append(componenteUnidadExistente.getEtiqueta());
                    // Si el campo está vacío, agrega el UnidadExistente del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) {
                        listaCamposObligPorCompletar.add(componenteUnidadExistente.getEtiqueta());
                    }

                    System.out.println("UnidadExistente sin completar");
                } else {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componenteUnidadExistente.getEtiqueta());

                    // Verificar si la unidad existente contiene letras
                    if (textoAnadir.matches(".*[a-zA-Z].*")) {
                        JOptionPane.showMessageDialog(null, "La cantidad existente no puede contener letras.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    System.out.println("No se ha producido ningún error, textField UnidadExistente con contenido");
                    System.out.println("UnidadExistente introducido : " + textoAnadir);
                }

                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        };
        componenteUnidadExistente.addLisOverEtiquetav2(li3);
        
        
        // Agregar un listener al componente de proveedores
        componenteProveedores.getResultadosList().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                int indiceSeleccionado = componenteProveedores.obtenerIndiceSeleccionado();
                boolean proveedorSeleccionado = indiceSeleccionado != -1;

                if (proveedorSeleccionado) {
                        
                    listaCamposObligPorCompletar.remove(componenteProveedores.getEtiqueta());
                    System.out.println("No se ha producido ningún error, proveedor seleccionado con contenido");
                        
                } else {
                    visorErrores.append(componenteProveedores.getEtiqueta());
                    listaCamposObligPorCompletar.add(componenteProveedores.getEtiqueta());
                    System.out.println("UnidadExistente sin completar");
                }
                    
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        });
        
        /*
        // Añadir un FocusListener al botón de la imagen
        botonImagen.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // Verifica si se ha seleccionado una imagen
                if (imagenSeleccionada != null) {
                    // Si se ha seleccionado una imagen, elimina el nombre del botón de la lista
                    listaCamposObligPorCompletar.remove(botonImagen.getText());
                    System.out.println("No se ha producido ningún error, imagen seleccionada");
                } else {
                    // Si no se ha seleccionado ninguna imagen, agrega el nombre del botón a la lista
                    visorErrores.append(botonImagen.getText());
                    listaCamposObligPorCompletar.add(botonImagen.getText());
                    System.out.println("Imagen sin completar");
                }

                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonAnadir();
            }
        });
        */
    }
    
    //--------------------MÉTODOS-------------------------
//----------------para el textArea vosirErrores----------------
    
    // Método para actualizar el visor de errores
    public void actualizarTextAreaVisorErrores() {
        // Crear una nueva lista para almacenar los campos vacíos no registrados
        ArrayList<String> nuevosCamposVacios = new ArrayList<>();

        // Iterar sobre la lista de campos vacíos
        for (String campo : listaCamposObligPorCompletar) {
            // Verificar si el campo aún no está en la nueva lista
            if (!nuevosCamposVacios.contains(campo)) {
                nuevosCamposVacios.add(campo);
            }
        }

        // Actualizar la lista original con la nueva lista
        listaCamposObligPorCompletar = nuevosCamposVacios;

        // Crear la cadena de campos vacíos para mostrar en el visor de errores
        camposObligPorCompletar = String.join(", \n", listaCamposObligPorCompletar);

        // Verificar si hay campos obligatorios vacíos
        if (camposObligPorCompletar.isEmpty()) {
            
            visorErrores.setText("Todos los campos están completados.");
            
        } else {
            visorErrores.setText("Los siguientes campos obligatorios están vacíos: \n" + camposObligPorCompletar);
        }

    }

    
    // Acceder a listaCamposVacios
    public ArrayList<String> getListaCamposVacios() 
    {
        return listaCamposObligPorCompletar;
    }
    
    //-------------------Habilitar / Deshabilitar botón añadir-------------------------------------------
    public boolean hayCamposObligatoriosVacios() 
    {
        return !listaCamposObligPorCompletar.isEmpty();
    }

    // Método para actualizar el estado del botón añadir
    public void actualizarEstadoBotonAnadir() 
    {
        botonAnadir.setEnabled(!hayCamposObligatoriosVacios());
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
    
     public PrincipalAdministrador getPrincipalAdmin() {
        return this.principalAdmin;
    }
     
    public void seleccionarImagen() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            imagenSeleccionada = fileChooser.getSelectedFile();
            if (imagenSeleccionada != null) {
                botonImagen.setText("");
                botonImagen.setIcon(new ImageIcon(new ImageIcon(imagenSeleccionada.getPath()).getImage().getScaledInstance(100, 100, Image.SCALE_DEFAULT)));
            }
        }
    }

    public void guardarImagen(String nombreProducto) {
        try {
            if (imagenSeleccionada != null) {
                String extension = imagenSeleccionada.getName().substring(imagenSeleccionada.getName().lastIndexOf("."));
                File destino = new File("src/imagenesProductos/" + nombreProducto + extension);

                // Leer la imagen para obtener sus dimensiones
                BufferedImage imagen = ImageIO.read(imagenSeleccionada);
                int ancho = imagen.getWidth();
                int alto = imagen.getHeight();

                Files.copy(imagenSeleccionada.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    // Método para verificar las dimensiones de la imagen
    private boolean verificarDimensionesImagen(File imagenSeleccionada) {
        try {
            // Leer la imagen para obtener sus dimensiones
            BufferedImage imagen = ImageIO.read(imagenSeleccionada);
            int ancho = imagen.getWidth();
            int alto = imagen.getHeight();

            // Verificar si las dimensiones son 150x150
            return (ancho <= 150 && alto <= 150);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
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

        labelTitulo = new javax.swing.JLabel();
        componenteProveedores = new propiedades.Componente4();
        try {
            componenteNombre = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componentePrecio = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componenteUnidadExistente = new propiedades.Componente2Anadir();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        jScrollPane1 = new javax.swing.JScrollPane();
        visorErrores = new javax.swing.JTextArea();
        jPanel1 = new javax.swing.JPanel();
        botonAnadir = new javax.swing.JButton();
        botonReiniciar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        botonAnadirProveedor = new javax.swing.JButton();
        botonImagen = new javax.swing.JButton();

        labelTitulo.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        labelTitulo.setText("Añadir un producto");

        componenteProveedores.setEtiqueta("Proveedor  :");
        componenteProveedores.setMensaje("\"Selecciona un proveedor\"");
        componenteProveedores.setPrimerElementoEsMensaje(true);

        componenteNombre.setEtiqueta("Nombre :");
        componenteNombre.setObligatorio(true);

        componentePrecio.setEtiqueta("Precio :");
        componentePrecio.setObligatorio(true);

        componenteUnidadExistente.setEtiqueta("Stock :");
        componenteUnidadExistente.setObligatorio(true);

        visorErrores.setColumns(20);
        visorErrores.setRows(5);
        jScrollPane1.setViewportView(visorErrores);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        botonAnadir.setText("Añadir");
        botonAnadir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnadirActionPerformed(evt);
            }
        });
        jPanel1.add(botonAnadir);

        botonReiniciar.setText("Reiniciar");
        botonReiniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonReiniciarActionPerformed(evt);
            }
        });
        jPanel1.add(botonReiniciar);

        jLabel1.setText("¿ No está el proveedor ? : ");

        botonAnadirProveedor.setText("Añadir proveedor");
        botonAnadirProveedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAnadirProveedorActionPerformed(evt);
            }
        });

        botonImagen.setText("Elige imagen");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(444, 444, 444)
                        .addComponent(labelTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addComponent(componenteProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(48, 48, 48)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(componenteNombre, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(componentePrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71)
                        .addComponent(componenteUnidadExistente, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(53, 53, 53)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 346, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(131, 131, 131)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(botonAnadirProveedor)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(79, 79, 79)
                        .addComponent(botonImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labelTitulo)
                        .addGap(33, 33, 33)
                        .addComponent(componenteProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(118, 118, 118)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(componenteNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(componenteUnidadExistente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(26, 26, 26)
                        .addComponent(componentePrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(botonAnadirProveedor))
                        .addGap(30, 30, 30)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(153, 153, 153))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(botonImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(91, 91, 91))))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonAnadirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnadirActionPerformed
        // TODO add your handling code here:
        
        // Verificar si todos los campos están seleccionados
        if (componenteProveedores.obtenerIndiceSeleccionado() == -1 ||
            componenteNombre.getEscritura().isEmpty() ||
            componentePrecio.getEscritura().isEmpty() ||
            componenteUnidadExistente.getEscritura().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos para añadir el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si algún campo no está seleccionado
        }
        
        // Verificar si se ha seleccionado una imagen
        if (imagenSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una imagen para añadir el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return; 
        }

        // Verificar las dimensiones de la imagen
        if (!verificarDimensionesImagen(imagenSeleccionada)) {
            JOptionPane.showMessageDialog(this, "La imagen debe tener dimensiones de 150x150.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Variables para almacenar los errores
        StringBuilder errores = new StringBuilder();

        // Lógica para añadir el producto
        int nuevoCodigoProveedor = componenteProveedores.obtenerCodigoSeleccionado(componenteProveedores.obtenerIndiceSeleccionado());
        String nombre = componenteNombre.getEscritura();
        String precioTexto = componentePrecio.getEscritura().replace(',', '.'); // Reemplazar "," por "."
        String unidadExistenteTexto = componenteUnidadExistente.getEscritura();

        // Validar que el nombre solo contenga caracteres
        if (!nombre.matches("[a-zA-Z\\s]+")) {
            errores.append("- El nombre del producto solo puede contener letras.\n");
        }

        // Validar que el precio sea un número válido y no sea negativo
        if (!precioTexto.matches("\\d*\\.?\\d+")) {
            errores.append("- El precio del producto debe ser un número válido.\n");
        } else {
            // Convertir el precio a un número
            double precio = Double.parseDouble(precioTexto);
            // Verificar que el precio no sea negativo
            if (precio < 0) {
                errores.append("- El precio del producto no puede ser negativo.\n");
            }
        }

        // Validar que la cantidad existente sea un número entero y no sea negativa
        if (!unidadExistenteTexto.matches("\\d+")) {
            errores.append("- La cantidad existente debe ser un número entero y no puede ser negativa.\n");
        } else {
            // Convertir la cantidad existente a un número
            int unidadExistente = Integer.parseInt(unidadExistenteTexto);
            // Verificar que la cantidad existente no sea negativa
            if (unidadExistente < 0) {
                errores.append("- La cantidad existente no puede ser negativa.\n");
            }
        }

        // Si hay errores, mostrarlos en un mensaje
        if (errores.length() > 0) {
            JOptionPane.showMessageDialog(this, "Se encontraron los siguientes errores:\n" + errores.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si hay errores
        }

        // Instanciar ProductoConProveedorDao
        ManagerConexion managerConexion = new ManagerConexion();
        ProductoConProveedorDao productoDao = new ProductoConProveedorDao(managerConexion);

        // Verificar si ya existe un producto con el mismo nombre y proveedor
        if (productoDao.existeProductoConNombreYProveedor(nombre, nuevoCodigoProveedor)) {
            JOptionPane.showMessageDialog(this, "Ya existe un producto con el mismo nombre y proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si ya existe un producto con el mismo nombre y proveedor
        }
        
        // Verificar si ya existe un producto con el mismo nombre y proveedor
        if (productoDao.existeProductoConOtroProveedor(nombre, nuevoCodigoProveedor)) {
            JOptionPane.showMessageDialog(this, "Ya existe un producto con el mismo producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si ya existe un producto con el mismo nombre y proveedor
        }
        
        // Crear un objeto ProductoConProveedor con los datos ingresados
        ProductoConProveedor nuevoProducto = new ProductoConProveedor(
            nuevoCodigoProveedor,
            null, // El nombre del proveedor no es necesario aquí
            nombre,
            Double.parseDouble(precioTexto),
            Integer.parseInt(unidadExistenteTexto)
        );

        // Añadir el producto
        boolean resultado = productoDao.añadirProducto(nuevoProducto);

        // Mostrar mensaje de éxito o error
        if (resultado) {
            JOptionPane.showMessageDialog(this, "Producto añadido exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);

            // Guardar la imagen vinculada al ID del pedido
            guardarImagen(nuevoProducto.getNombreProducto());

            // Reiniciar el formulario
            botonReiniciarActionPerformed(evt);
        } else {
            JOptionPane.showMessageDialog(this, "Error al añadir el producto", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_botonAnadirActionPerformed

    private void botonReiniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonReiniciarActionPerformed
        // TODO add your handling code here:
        // Obtener el panel padre del componente actual
        java.awt.Container parent = this.getParent();

        // Verificar si el panel padre es un JPanel
        if (parent != null && parent instanceof javax.swing.JPanel) {
            javax.swing.JPanel panelPrincipal = (javax.swing.JPanel) parent;

            // Crear una nueva instancia de AnadirProveedor
            AnadirProducto nuevoPanel = new AnadirProducto(principalAdmin);

            // Remover el panel actual del panel padre
            panelPrincipal.remove(this);

            // Añadir el nuevo panel AnadirProveedor al panel padre
            panelPrincipal.add(nuevoPanel);

            // Validar y repintar el panel padre para reflejar los cambios
            panelPrincipal.revalidate();
            panelPrincipal.repaint();
        }

    }//GEN-LAST:event_botonReiniciarActionPerformed

    private void botonAnadirProveedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAnadirProveedorActionPerformed
        
        // Crear una instancia de AnadirProductoConAnadirProveedor
        AnadirProductoConAnadirProveedor apcap = new AnadirProductoConAnadirProveedor(principalAdmin);

        // Obtener el panelPrincipal de PrincipalAdministrador y agregar apcap
        principalAdmin.getPanelPrincipal().removeAll();
        principalAdmin.getPanelPrincipal().add(apcap);
        principalAdmin.getPanelPrincipal().revalidate();
        principalAdmin.getPanelPrincipal().repaint();
        
    }//GEN-LAST:event_botonAnadirProveedorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonAnadir;
    private javax.swing.JButton botonAnadirProveedor;
    private javax.swing.JButton botonImagen;
    private javax.swing.JButton botonReiniciar;
    private propiedades.Componente2Anadir componenteNombre;
    private propiedades.Componente2Anadir componentePrecio;
    private propiedades.Componente4 componenteProveedores;
    private propiedades.Componente2Anadir componenteUnidadExistente;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JTextArea visorErrores;
    // End of variables declaration//GEN-END:variables
}
