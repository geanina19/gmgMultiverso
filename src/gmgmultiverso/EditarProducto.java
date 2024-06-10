/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package gmgmultiverso;

import gmgmultiverso.db.ManagerConexion;
import gmgmultiverso.db.dao.ProductoConProveedorDao;
import gmgmultiverso.model.ProductoConProveedor;
import gmgmultiverso.model.Proveedor;
import java.awt.Dimension;
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
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import propiedades.EvObjOverEtiquetav2;
import propiedades.LisOverEtiquetav2;

/**
 *
 * @author geanina.foanta
 */
public class EditarProducto extends javax.swing.JPanel {

    PrincipalAdministrador principalAdmin;
    BuscarProducto buscarProducto;
    
    private int codProducto;
    
    ManagerConexion managerConexion = new ManagerConexion();
    Connection conet;
    
    Statement st;
    ResultSet rs;
    
    private File imagenSeleccionada;
    
    private ArrayList<String> listaCamposObligPorCompletar = new ArrayList<>();
    private String camposObligPorCompletar;
    
    /**
     * Creates new form EditarProducto
     */
    public EditarProducto(int codProducto, BuscarProducto buscarProducto) {
        initComponents();
        this.principalAdmin = buscarProducto.getPrincipalAdmin();
        this.codProducto = codProducto;
        
        this.setSize(1091, 642);
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(20, 30, 20, 30));
        
        visorErrores.setEditable(false);
        
        
        cargarProveedores();
        cargarInformacionProducto(codProducto);
        actualizarTextAreaVisorErrores();
        
        
        
        //--------componenteNombre--------
        LisOverEtiquetav2 li = new LisOverEtiquetav2() 
        {
            @Override
            public void accionPerdidaFoco(EvObjOverEtiquetav2 ev, String textoAnadir, boolean error) 
            {
                if(error)
                {
                    visorErrores.append(componenteNombre.getEtiqueta());

                    // Si el campo está vacío, agrega el nombre del campo a listaCamposVacios
                    if (textoAnadir.isEmpty()) 
                    {
                        listaCamposObligPorCompletar.add(componenteNombre.getEtiqueta());
                    }

                    System.out.println("Nombre sin completar");
                }
                else
                {
                    // Si el campo se ha completado, quítalo de la listaCamposVacios
                    listaCamposObligPorCompletar.remove(componenteNombre.getEtiqueta());
                    
                    //String llenoNombre = vacioNombre.setText();
                    System.out.println("No se ha producido ningun error, textField Nombre con contenido");
                    System.out.println("Nombre introducido : " + textoAnadir);
                    
                }
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonModificar();
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

                    // Verificar si el precio contiene caracteres no permitidos
                    if (!textoAnadir.matches("[0-9.,]+")) {
                        JOptionPane.showMessageDialog(null, "El precio solo puede contener números, punto (.) y coma (,).", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        // Convertir el texto del precio a un número
                        double precio = Double.parseDouble(textoAnadir);
                        // Verificar si el precio es mayor a 100
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
                actualizarEstadoBotonModificar();
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
                actualizarEstadoBotonModificar();
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
                actualizarEstadoBotonModificar();
            }
        });
        
        botonImagen.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarImagen();
            }
        });
        
        /*
        // Añadir un FocusListener al botón de la imagen
        botonImagen.addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                // Verifica si se ha seleccionado una imagen
                if (imagenSeleccionada != null) {
                    
                    listaCamposObligPorCompletar.remove(botonImagen.getText());
                    System.out.println("No se ha producido ningún error, imagen seleccionada");
                    
                } else {
                    visorErrores.append(botonImagen.getText());
                    listaCamposObligPorCompletar.add(botonImagen.getText());
                    System.out.println("Imagen sin completar");
                }
                
                // Llama al método para actualizar el visor de errores
                actualizarTextAreaVisorErrores();
                // Llama al método para actualizar el estado del botón
                actualizarEstadoBotonModificar();
            }
        });
        */

        
    }
    
    //------------------------------MÉTODOS-------------------------------
    
//----------------para el textArea vosirErrores----------------
    
    public void actualizarTextAreaVisorErrores() 
    {
        // Sirve para que no se me dupliquen los campos que yo añada al textarea visorErrores
        listaCamposObligPorCompletar = (ArrayList<String>) listaCamposObligPorCompletar.stream().distinct().collect(Collectors.toList());

        camposObligPorCompletar = String.join(", \n", listaCamposObligPorCompletar);

        if (camposObligPorCompletar.isEmpty()) 
        {
            visorErrores.setText("Todos los campos están completados.");
        } 
        else 
        {
            visorErrores.setText("Los siguientes campos obligatorios están vacíos: \n" + camposObligPorCompletar);
        }
    }

    // Acceder a listaCamposVacios
    public ArrayList<String> getListaCamposVacios() 
    {
        return listaCamposObligPorCompletar;
    }
    
//-------------------Habilitar / Deshabilitar botón modificar-------------------------------------------
    public boolean hayCamposObligatoriosVacios() 
    {
        return !listaCamposObligPorCompletar.isEmpty();
    }

    // Método para actualizar el estado del botónModificar
    public void actualizarEstadoBotonModificar() 
    {
        botonModificar.setEnabled(!hayCamposObligatoriosVacios());
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
    
    //------------------carga la info del producto seleccionado------------------------
    
    public void cargarInformacionProducto(int codProducto) 
    {
        ManagerConexion managerConexion = new ManagerConexion();
        ProductoConProveedorDao productoDao = new ProductoConProveedorDao(managerConexion);

        // Obtener el producto por su ID
        ProductoConProveedor productoSeleccionado = null;
        for (ProductoConProveedor producto : productoDao.list()) {
            if (producto.getId() == codProducto) {
                productoSeleccionado = producto;
                break;
            }
        }

        if (productoSeleccionado != null) {
            // Establecer los valores de los componentes con la información del producto seleccionado
            componenteNombre.setEscritura(productoSeleccionado.getNombreProducto());
            componentePrecio.setEscritura(String.valueOf(productoSeleccionado.getPrecio()));
            componenteUnidadExistente.setEscritura(String.valueOf(productoSeleccionado.getUnidad_existente()));

            
            // Asegúrate de que el nombre del proveedor está formateado correctamente
            String nombreProveedor = productoSeleccionado.getNombreProveedor();
            componenteProveedores.setSeleccion(nombreProveedor);
            System.out.println("este es el proveedor " + nombreProveedor);

            // Obtener el índice seleccionado en el JList
            int selectedIndexProveedores = componenteProveedores.obtenerIndiceSeleccionado();
            if (selectedIndexProveedores != -1) {
                componenteProveedores.ensureIndexIsVisible(selectedIndexProveedores);
            }
            
            // Cargar la imagen del producto si existe
            String nombreImagen = productoSeleccionado.getNombreProducto() + ".png";
            File imagenFile = new File("src/imagenesProductos/" + nombreImagen);
            if (imagenFile.exists()) {
                try {
                    Image imagen = ImageIO.read(imagenFile);
                    // Escalar la imagen
                    ImageIcon icono = new ImageIcon(imagen.getScaledInstance(200, 200, Image.SCALE_SMOOTH));
                    // Establecer el ícono en el botón
                    botonImagen.setIcon(icono);
                    // Establecer el tamaño preferido del botón
                    botonImagen.setPreferredSize(new Dimension(200, 200));
                    // Asignar la imagen cargada a la variable imagenSeleccionada
                    imagenSeleccionada = imagenFile;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        } else {
            JOptionPane.showMessageDialog(this, "El producto con el ID " + codProducto + " no existe.");
        }
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

                // Verificar si las dimensiones son 150x150
                if (ancho <= 150 && alto <= 150) {
                    // Si las dimensiones son correctas, guardar la imagen
                    Files.copy(imagenSeleccionada.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                } else {
                    // Si las dimensiones no son correctas, mostrar un mensaje de error
                    JOptionPane.showMessageDialog(this, "La imagen debe tener dimensiones de 150x150.", "Error", JOptionPane.ERROR_MESSAGE);
                    return; // Salir del método si las dimensiones no son correctas
                }
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
    
    public void modificar() {
        // Obtener los valores ingresados por el usuario
        String nuevoNombre = componenteNombre.getEscritura();
        String nuevoPrecio = componentePrecio.getEscritura();
        String nuevaUnidadExistente = componenteUnidadExistente.getEscritura();
        int nuevoCodigoProveedor = componenteProveedores.obtenerCodigoSeleccionado(componenteProveedores.obtenerIndiceSeleccionado());

        // Verificar si se ha seleccionado una imagen
        if (imagenSeleccionada == null) {
            JOptionPane.showMessageDialog(this, "Por favor, selecciona una imagen para añadir el producto.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si no hay imagen seleccionada
        }

        // Verificar las dimensiones de la imagen
        if (!verificarDimensionesImagen(imagenSeleccionada)) {
            JOptionPane.showMessageDialog(this, "La imagen debe tener dimensiones de 150x150.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Variables para almacenar los errores
        StringBuilder errores = new StringBuilder();

        // Validar que el nombre solo contenga caracteres
        if (!nuevoNombre.matches("[a-zA-Z\\s]+")) {
            errores.append("- El nombre del producto solo puede contener letras.\n");
        }

        // Validar que el precio sea un número válido
        if (!nuevoPrecio.matches("\\d*\\.?\\d+")) {
            errores.append("- El precio del producto debe ser un número válido.\n");
        } else {
            // Convertir el precio a un número
            double precio = Double.parseDouble(nuevoPrecio);
            // Validar que el precio no sea mayor a 100
            if (precio > 100) {
                errores.append("- El precio del producto no puede ser mayor a 100.\n");
            }
        }

        // Validar que la cantidad existente sea un número entero
        if (!nuevaUnidadExistente.matches("\\d+")) {
            errores.append("- La cantidad existente debe ser un número entero.\n");
        }

        // Si hay errores, mostrarlos en un mensaje
        if (errores.length() > 0) {
            JOptionPane.showMessageDialog(this, "Se encontraron los siguientes errores:\n" + errores.toString(), "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si hay errores
        }

        // Convertir los datos validados
        double precio = Double.parseDouble(nuevoPrecio);
        int unidadExistente = Integer.parseInt(nuevaUnidadExistente);

        // Obtener el ID del producto
        int idProducto = codProducto;

        // Crear una instancia de producto con los nuevos valores
        ProductoConProveedor productoModificado = new ProductoConProveedor(idProducto, nuevoCodigoProveedor, null, nuevoNombre, precio, unidadExistente);

        // Crear una instancia de ProductoConProveedorDao
        ManagerConexion managerConexion = new ManagerConexion();
        ProductoConProveedorDao productoDao = new ProductoConProveedorDao(managerConexion);

        // Verificar si ya existe un producto con el mismo nombre y proveedor
        if (productoDao.existeProductoConNombreYProveedor(nuevoNombre, nuevoCodigoProveedor, idProducto)) {
            JOptionPane.showMessageDialog(this, "Ya existe un producto con el mismo nombre y proveedor.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si ya existe un producto con el mismo nombre y proveedor
        }
        
        // Verificar si ya existe un producto con el mismo nombre y proveedor
        if (productoDao.existeProductoConOtroProveedor(nuevoNombre, nuevoCodigoProveedor)) {
            JOptionPane.showMessageDialog(this, "Ya existe un producto con el mismo nombre.", "Error", JOptionPane.ERROR_MESSAGE);
            return; // Salir del método si ya existe un producto con el mismo nombre y proveedor
        }
        
        // Intentar actualizar el producto en la base de datos
        boolean exito = productoDao.actualizarProducto(productoModificado);

        // Verificar si la actualización fue exitosa
        if (exito) {
            guardarImagen(productoModificado.getNombreProducto()); // Aquí se guarda la imagen

            JOptionPane.showMessageDialog(this, "Producto modificado correctamente.");

            BuscarProducto bp = new BuscarProducto(principalAdmin);
            principalAdmin.mostrarPanel(bp);
        } else {
            JOptionPane.showMessageDialog(this, "Error al modificar el producto.");
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
        jScrollPane1 = new javax.swing.JScrollPane();
        visorErrores = new javax.swing.JTextArea();
        componenteProveedores = new propiedades.Componente4();
        try {
            componenteNombre = new propiedades.EtiquetaVertHorizv2();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componentePrecio = new propiedades.EtiquetaVertHorizv2();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        try {
            componenteUnidadExistente = new propiedades.EtiquetaVertHorizv2();
        } catch (java.beans.PropertyVetoException e1) {
            e1.printStackTrace();
        }
        jPanel1 = new javax.swing.JPanel();
        botonModificar = new javax.swing.JButton();
        botonCancelar = new javax.swing.JButton();
        botonImagen = new javax.swing.JButton();

        labelTitulo.setFont(new java.awt.Font("Times New Roman", 3, 24)); // NOI18N
        labelTitulo.setText("Editar un producto");

        visorErrores.setColumns(20);
        visorErrores.setRows(5);
        jScrollPane1.setViewportView(visorErrores);

        componenteProveedores.setEtiqueta("Proveedores  :");
        componenteProveedores.setMensaje("\"Selecciona un proveedor\"");
        componenteProveedores.setPrimerElementoEsMensaje(true);

        componenteNombre.setEtiqueta("Nombre :");
        componenteNombre.setObligatorio(true);

        componentePrecio.setEtiqueta("Precio :");
        componentePrecio.setObligatorio(true);

        componenteUnidadExistente.setEtiqueta("Stock :");
        componenteUnidadExistente.setObligatorio(true);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 5));

        botonModificar.setText("Modificar");
        botonModificar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonModificarActionPerformed(evt);
            }
        });
        jPanel1.add(botonModificar);

        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });
        jPanel1.add(botonCancelar);

        botonImagen.setText("Elige imagen");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(427, 427, 427)
                        .addComponent(labelTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(27, 27, 27)
                                .addComponent(componenteProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(46, 46, 46)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(42, 42, 42)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(componenteNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 251, Short.MAX_VALUE)
                                    .addComponent(componentePrecio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(45, 45, 45)
                                .addComponent(componenteUnidadExistente, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(botonImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 163, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(72, 72, 72)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addComponent(componenteProveedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(29, 29, 29)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(66, 66, 66)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(labelTitulo)
                        .addGap(63, 63, 63)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(componenteNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(componenteUnidadExistente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(36, 36, 36)
                        .addComponent(componentePrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(botonImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(107, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void botonModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonModificarActionPerformed
        // TODO add your handling code here:
        
        modificar();
    }//GEN-LAST:event_botonModificarActionPerformed

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed

        int opcion = JOptionPane.showConfirmDialog(this, "¿Está seguro de que desea volver a la ventana anterior?, no se realizarán cambios", "Confirmar acción", JOptionPane.YES_NO_OPTION);

        // Verificar la opción seleccionada por el usuario
        if (opcion == JOptionPane.YES_OPTION) {
            BuscarProducto bp = new BuscarProducto(principalAdmin);
            principalAdmin.mostrarPanel(bp);
        } else {
            // no hace nada
        }
    }//GEN-LAST:event_botonCancelarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton botonCancelar;
    private javax.swing.JButton botonImagen;
    private javax.swing.JButton botonModificar;
    private propiedades.EtiquetaVertHorizv2 componenteNombre;
    private propiedades.EtiquetaVertHorizv2 componentePrecio;
    private propiedades.Componente4 componenteProveedores;
    private propiedades.EtiquetaVertHorizv2 componenteUnidadExistente;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel labelTitulo;
    private javax.swing.JTextArea visorErrores;
    // End of variables declaration//GEN-END:variables
}
