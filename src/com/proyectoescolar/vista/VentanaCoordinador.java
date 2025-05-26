package com.proyectoescolar.vista;

import com.proyectoescolar.controlador.ControladorProyectos;
import com.proyectoescolar.controlador.ControladorUsuarios;
import com.proyectoescolar.modelo.CambioEstado;
import com.proyectoescolar.modelo.Coordinador;
import com.proyectoescolar.modelo.Docente;
import com.proyectoescolar.modelo.EstadoProyecto;
import com.proyectoescolar.modelo.Estudiante;
import com.proyectoescolar.modelo.Proyecto;
import com.proyectoescolar.modelo.Usuario;
import com.proyectoescolar.utilidades.Utilidades;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Ventana principal para el rol de Coordinador.
 */
public class VentanaCoordinador extends VentanaBase {
    
    // El controlador de proyectos viene como atributo heredado de VentanaBase
    private JList<Proyecto> listaProyectos;
    private DefaultListModel<Proyecto> modeloListaProyectos;
    private JTextArea txtDetallesProyecto;
    private JButton btnCambiarEstado;
    private JButton btnVerDetalles;
    private JButton btnActualizarProyecto;
    private JButton btnEliminarProyecto;
    private JComboBox<String> cmbFiltroEstado;
    private JButton btnFiltrar;
    private JTextField txtBusquedaInstitucion;
    private JButton btnBuscar;
    
    // Componentes para la gestión de usuarios
    private JList<Usuario> listaUsuarios;
    private DefaultListModel<Usuario> modeloListaUsuarios;
    private JTextArea txtDetallesUsuario;
    private JComboBox<String> cmbFiltroTipoUsuario;
    private JButton btnFiltrarUsuarios;
    private JTextField txtBusquedaUsuario;
    private JButton btnBuscarUsuario;
    private JButton btnRegistrarUsuario;
    private JButton btnEditarUsuario;
    private JButton btnEliminarUsuario;
    
    /**
     * Constructor de la ventana para coordinadores
     * @param coordinador Coordinador autenticado
     * @param controladorUsuarios Controlador de usuarios
     * @param controladorProyectos Controlador de proyectos
     */
    public VentanaCoordinador(Coordinador coordinador, ControladorUsuarios controladorUsuarios,
                            ControladorProyectos controladorProyectos) {
        super(coordinador, controladorUsuarios, controladorProyectos, "Sistema de Gestión de Proyectos - Coordinador");
        configurarComponentesEspecificos();
    }
    
    @Override
    protected void configurarComponentesEspecificos() {
        // Panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Pestaña de gestión de proyectos
        JPanel panelGestionProyectos = new JPanel(new BorderLayout(10, 10));
        
        // Panel de filtros
        JPanel panelFiltros = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltros.setBorder(BorderFactory.createTitledBorder("Filtros"));
        
        JLabel lblFiltroEstado = new JLabel("Filtrar por Estado:");
        cmbFiltroEstado = new JComboBox<>();
        cmbFiltroEstado.addItem("Todos");
        for (EstadoProyecto estado : EstadoProyecto.values()) {
            cmbFiltroEstado.addItem(estado.getNombre());
        }
        
        btnFiltrar = new JButton("Filtrar");
        
        JLabel lblBusquedaInstitucion = new JLabel("Buscar por Institución:");
        txtBusquedaInstitucion = new JTextField(15);
        btnBuscar = new JButton("Buscar");
        
        panelFiltros.add(lblFiltroEstado);
        panelFiltros.add(cmbFiltroEstado);
        panelFiltros.add(btnFiltrar);
        panelFiltros.add(lblBusquedaInstitucion);
        panelFiltros.add(txtBusquedaInstitucion);
        panelFiltros.add(btnBuscar);
        
        // Panel de lista y detalles
        JPanel panelListaDetalles = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // Panel de lista de proyectos
        JPanel panelLista = new JPanel(new BorderLayout(5, 5));
        panelLista.setBorder(BorderFactory.createTitledBorder("Proyectos"));
        
        modeloListaProyectos = new DefaultListModel<>();
        listaProyectos = new JList<>(modeloListaProyectos);
        listaProyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollProyectos = new JScrollPane(listaProyectos);
        scrollProyectos.setPreferredSize(new Dimension(300, 400));
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnCambiarEstado = new JButton("Cambiar Estado");
        btnVerDetalles = new JButton("Ver Detalles");
        btnActualizarProyecto = new JButton("Actualizar");
        btnEliminarProyecto = new JButton("Eliminar");
        
        panelBotones.add(btnCambiarEstado);
        panelBotones.add(btnVerDetalles);
        panelBotones.add(btnActualizarProyecto);
        panelBotones.add(btnEliminarProyecto);
        
        panelLista.add(new JLabel("Seleccione un proyecto:"), BorderLayout.NORTH);
        panelLista.add(scrollProyectos, BorderLayout.CENTER);
        panelLista.add(panelBotones, BorderLayout.SOUTH);
        
        // Panel de detalles
        JPanel panelDetalles = new JPanel(new BorderLayout(5, 5));
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Proyecto"));
        
        txtDetallesProyecto = new JTextArea();
        txtDetallesProyecto.setEditable(false);
        
        // Aumentar tamaño de letra en un 50%
        Font fontDetallesProyecto = txtDetallesProyecto.getFont();
        float newSizeDetallesProyecto = fontDetallesProyecto.getSize() * 1.2f;
        txtDetallesProyecto.setFont(fontDetallesProyecto.deriveFont(newSizeDetallesProyecto));
        
        JScrollPane scrollDetalles = new JScrollPane(txtDetallesProyecto);
        scrollDetalles.setPreferredSize(new Dimension(300, 400));
        
        panelDetalles.add(scrollDetalles, BorderLayout.CENTER);
        
        panelListaDetalles.add(panelLista);
        panelListaDetalles.add(panelDetalles);
        
        panelGestionProyectos.add(panelFiltros, BorderLayout.NORTH);
        panelGestionProyectos.add(panelListaDetalles, BorderLayout.CENTER);
        
        // Crear pestaña de gestión de usuarios
        JPanel panelGestionUsuarios = crearPanelGestionUsuarios();
        
        // Agregar pestañas
        tabbedPane.addTab("Gestión de Proyectos", panelGestionProyectos);
        tabbedPane.addTab("Gestión de Usuarios", panelGestionUsuarios);
        
        // Agregar el panel de pestañas al panel de contenido
        panelContenido.add(tabbedPane, BorderLayout.CENTER);
        
        // Configurar eventos
        btnCambiarEstado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioCambiarEstado();
            }
        });
        
        btnVerDetalles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verDetallesProyecto();
            }
        });
        
        btnActualizarProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                actualizarProyectoSeleccionado();
            }
        });
        
        btnEliminarProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarProyectoSeleccionado();
            }
        });
        
        btnFiltrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrarProyectosPorEstado();
            }
        });
        
        btnBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarProyectosPorInstitucion();
            }
        });
        
        listaProyectos.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    actualizarDetallesProyecto();
                }
            }
        });
        
        // Cargar todos los proyectos
        cargarTodosLosProyectos();
    }
    
    /**
     * Método para crear el panel de gestión de usuarios
     * @return Panel configurado
     */
    private JPanel crearPanelGestionUsuarios() {
        JPanel panelGestionUsuarios = new JPanel(new BorderLayout(10, 10));
        
        // Panel de filtros para usuarios
        JPanel panelFiltrosUsuarios = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelFiltrosUsuarios.setBorder(BorderFactory.createTitledBorder("Filtros"));
        
        JLabel lblFiltroTipoUsuario = new JLabel("Filtrar por Tipo:");
        cmbFiltroTipoUsuario = new JComboBox<>();
        cmbFiltroTipoUsuario.addItem("Todos");
        cmbFiltroTipoUsuario.addItem("Estudiantes");
        cmbFiltroTipoUsuario.addItem("Docentes");
        
        btnFiltrarUsuarios = new JButton("Filtrar");
        
        JLabel lblBusquedaUsuario = new JLabel("Buscar por Nombre:");
        txtBusquedaUsuario = new JTextField(15);
        btnBuscarUsuario = new JButton("Buscar");
        
        panelFiltrosUsuarios.add(lblFiltroTipoUsuario);
        panelFiltrosUsuarios.add(cmbFiltroTipoUsuario);
        panelFiltrosUsuarios.add(btnFiltrarUsuarios);
        panelFiltrosUsuarios.add(lblBusquedaUsuario);
        panelFiltrosUsuarios.add(txtBusquedaUsuario);
        panelFiltrosUsuarios.add(btnBuscarUsuario);
        
        // Panel de lista y detalles de usuarios
        JPanel panelListaDetallesUsuarios = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // Panel de lista de usuarios
        JPanel panelListaUsuarios = new JPanel(new BorderLayout(5, 5));
        panelListaUsuarios.setBorder(BorderFactory.createTitledBorder("Usuarios"));
        
        modeloListaUsuarios = new DefaultListModel<>();
        listaUsuarios = new JList<>(modeloListaUsuarios);
        listaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollUsuarios = new JScrollPane(listaUsuarios);
        scrollUsuarios.setPreferredSize(new Dimension(300, 400));
        
        JPanel panelBotonesUsuarios = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnRegistrarUsuario = new JButton("Registrar");
        btnEditarUsuario = new JButton("Editar");
        btnEliminarUsuario = new JButton("Eliminar");
        
        panelBotonesUsuarios.add(btnRegistrarUsuario);
        panelBotonesUsuarios.add(btnEditarUsuario);
        panelBotonesUsuarios.add(btnEliminarUsuario);
        
        panelListaUsuarios.add(new JLabel("Seleccione un usuario:"), BorderLayout.NORTH);
        panelListaUsuarios.add(scrollUsuarios, BorderLayout.CENTER);
        panelListaUsuarios.add(panelBotonesUsuarios, BorderLayout.SOUTH);
        
        // Panel de detalles de usuario
        JPanel panelDetallesUsuario = new JPanel(new BorderLayout(5, 5));
        panelDetallesUsuario.setBorder(BorderFactory.createTitledBorder("Detalles del Usuario"));
        
        txtDetallesUsuario = new JTextArea();
        txtDetallesUsuario.setEditable(false);
        
        // Aumentar tamaño de letra en un 50%
        Font fontDetallesUsuario = txtDetallesUsuario.getFont();
        float newSizeDetallesUsuario = fontDetallesUsuario.getSize() * 1.5f;
        txtDetallesUsuario.setFont(fontDetallesUsuario.deriveFont(newSizeDetallesUsuario));
        
        JScrollPane scrollDetallesUsuario = new JScrollPane(txtDetallesUsuario);
        scrollDetallesUsuario.setPreferredSize(new Dimension(300, 400));
        
        panelDetallesUsuario.add(scrollDetallesUsuario, BorderLayout.CENTER);
        
        panelListaDetallesUsuarios.add(panelListaUsuarios);
        panelListaDetallesUsuarios.add(panelDetallesUsuario);
        
        panelGestionUsuarios.add(panelFiltrosUsuarios, BorderLayout.NORTH);
        panelGestionUsuarios.add(panelListaDetallesUsuarios, BorderLayout.CENTER);
        
        // Configurar eventos de usuario
        btnFiltrarUsuarios.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filtrarUsuariosPorTipo();
            }
        });
        
        btnBuscarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                buscarUsuariosPorNombre();
            }
        });
        
        btnRegistrarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioRegistrarUsuario();
            }
        });
        
        btnEditarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editarUsuarioSeleccionado();
            }
        });
        
        btnEliminarUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eliminarUsuarioSeleccionado();
            }
        });
        
        listaUsuarios.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    actualizarDetallesUsuario();
                }
            }
        });
        
        // Cargar todos los usuarios
        cargarTodosLosUsuarios();
        
        return panelGestionUsuarios;
    }
    
    /**
     * Método para cargar todos los usuarios
     */
    private void cargarTodosLosUsuarios() {
        modeloListaUsuarios.clear();
        
        // Cargar estudiantes
        List<Estudiante> estudiantes = controladorUsuarios.obtenerEstudiantes();
        for (Estudiante estudiante : estudiantes) {
            modeloListaUsuarios.addElement(estudiante);
        }
        
        // Cargar docentes
        List<Docente> docentes = controladorUsuarios.obtenerDocentes();
        for (Docente docente : docentes) {
            modeloListaUsuarios.addElement(docente);
        }
        
        if (modeloListaUsuarios.isEmpty()) {
            Utilidades.mostrarMensaje("No hay usuarios registrados en el sistema.", "Información");
        }
    }
    
    /**
     * Método para filtrar usuarios por tipo
     */
    private void filtrarUsuariosPorTipo() {
        String filtro = (String) cmbFiltroTipoUsuario.getSelectedItem();
        
        modeloListaUsuarios.clear();
        
        if ("Todos".equals(filtro) || filtro == null) {
            cargarTodosLosUsuarios();
        } else if ("Estudiantes".equals(filtro)) {
            List<Estudiante> estudiantes = controladorUsuarios.obtenerEstudiantes();
            for (Estudiante estudiante : estudiantes) {
                modeloListaUsuarios.addElement(estudiante);
            }
        } else if ("Docentes".equals(filtro)) {
            List<Docente> docentes = controladorUsuarios.obtenerDocentes();
            for (Docente docente : docentes) {
                modeloListaUsuarios.addElement(docente);
            }
        }
        
        if (modeloListaUsuarios.isEmpty()) {
            Utilidades.mostrarMensaje("No se encontraron usuarios con el filtro seleccionado.", "Información");
        }
    }
    
    /**
     * Método para buscar usuarios por nombre
     */
    private void buscarUsuariosPorNombre() {
        String busqueda = txtBusquedaUsuario.getText().trim().toLowerCase();
        
        if (busqueda.isEmpty()) {
            cargarTodosLosUsuarios();
            return;
        }
        
        modeloListaUsuarios.clear();
        
        // Filtrar por nombre o apellido
        List<Estudiante> estudiantes = controladorUsuarios.obtenerEstudiantes();
        for (Estudiante estudiante : estudiantes) {
            if (estudiante.getNombre().toLowerCase().contains(busqueda) || 
                estudiante.getApellido().toLowerCase().contains(busqueda)) {
                modeloListaUsuarios.addElement(estudiante);
            }
        }
        
        List<Docente> docentes = controladorUsuarios.obtenerDocentes();
        for (Docente docente : docentes) {
            if (docente.getNombre().toLowerCase().contains(busqueda) || 
                docente.getApellido().toLowerCase().contains(busqueda)) {
                modeloListaUsuarios.addElement(docente);
            }
        }
        
        if (modeloListaUsuarios.isEmpty()) {
            Utilidades.mostrarMensaje("No se encontraron usuarios que coincidan con la búsqueda.", "Información");
        }
    }
    
    /**
     * Método para actualizar los detalles del usuario seleccionado
     */
    private void actualizarDetallesUsuario() {
        Usuario usuarioSeleccionado = listaUsuarios.getSelectedValue();
        
        if (usuarioSeleccionado != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("DETALLES DEL USUARIO\n");
            sb.append("====================\n\n");
            sb.append("ID: ").append(usuarioSeleccionado.getId()).append("\n");
            sb.append("Nombre: ").append(usuarioSeleccionado.getNombre()).append("\n");
            sb.append("Apellido: ").append(usuarioSeleccionado.getApellido()).append("\n");
            sb.append("Rol: ").append(usuarioSeleccionado.getRol()).append("\n");
            sb.append("Correo: ").append(usuarioSeleccionado.getCorreo()).append("\n");
            sb.append("Teléfono: ").append(usuarioSeleccionado.getTelefono()).append("\n");
            sb.append("Nombre de Usuario: ").append(usuarioSeleccionado.getNombreUsuario()).append("\n\n");
            
            if (usuarioSeleccionado instanceof Estudiante) {
                Estudiante estudiante = (Estudiante) usuarioSeleccionado;
                sb.append("Institución: ").append(estudiante.getInstitucion()).append("\n");
                sb.append("Grado: ").append(estudiante.getGrado()).append("\n");
            } else if (usuarioSeleccionado instanceof Docente) {
                Docente docente = (Docente) usuarioSeleccionado;
                sb.append("Institución: ").append(docente.getInstitucion()).append("\n");
                sb.append("Especialidad: ").append(docente.getEspecialidad()).append("\n");
            }
            
            txtDetallesUsuario.setText(sb.toString());
        } else {
            txtDetallesUsuario.setText("");
        }
    }
    
    /**
     * Método para mostrar el formulario de registro de usuarios
     */
    private void mostrarFormularioRegistrarUsuario() {
        // Crear un diálogo para el formulario
        JDialog dialog = new JDialog(this, "Registrar Usuario", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel panelFormulario = new JPanel(new GridLayout(10, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Componentes del formulario
        JLabel lblTipoUsuario = new JLabel("Tipo de Usuario:");
        JComboBox<String> cmbTipoUsuario = new JComboBox<>(new String[]{"","Estudiante", "Docente"});
        
        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField(20);
        
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField(20);
        
        JLabel lblApellido = new JLabel("Apellido:");
        JTextField txtApellido = new JTextField(20);
        
        JLabel lblCorreo = new JLabel("Correo:");
        JTextField txtCorreo = new JTextField(20);
        
        JLabel lblTelefono = new JLabel("Teléfono:");
        JTextField txtTelefono = new JTextField(20);
        
        JLabel lblNombreUsuario = new JLabel("Nombre de Usuario:");
        JTextField txtNombreUsuario = new JTextField(20);
        
        JLabel lblContrasena = new JLabel("Contraseña:");
        JTextField txtContrasena = new JTextField(20);
        
        JLabel lblInstitucion = new JLabel("Institución:");
        JTextField txtInstitucion = new JTextField(20);
        
        JLabel lblEspecifico = new JLabel();
        JTextField txtEspecifico = new JTextField(20);
        
        // Configurar cambio dinámico de la etiqueta específica
        cmbTipoUsuario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if ("Estudiante".equals(cmbTipoUsuario.getSelectedItem())) {
                    lblEspecifico.setText("Grado:");
                } else {
                    lblEspecifico.setText("Especialidad:");
                }
            }
        });
        
        // Agregar componentes al panel
        panelFormulario.add(lblTipoUsuario);
        panelFormulario.add(cmbTipoUsuario);
        panelFormulario.add(lblId);
        panelFormulario.add(txtId);
        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lblApellido);
        panelFormulario.add(txtApellido);
        panelFormulario.add(lblCorreo);
        panelFormulario.add(txtCorreo);
        panelFormulario.add(lblTelefono);
        panelFormulario.add(txtTelefono);
        panelFormulario.add(lblNombreUsuario);
        panelFormulario.add(txtNombreUsuario);
        panelFormulario.add(lblContrasena);
        panelFormulario.add(txtContrasena);
        panelFormulario.add(lblInstitucion);
        panelFormulario.add(txtInstitucion);
        panelFormulario.add(lblEspecifico);
        panelFormulario.add(txtEspecifico);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        // Agregar paneles al diálogo
        dialog.add(panelFormulario, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        
        // Configurar eventos
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validar campos
                if (txtId.getText().trim().isEmpty() || txtNombre.getText().trim().isEmpty() ||
                    txtApellido.getText().trim().isEmpty() || txtCorreo.getText().trim().isEmpty() ||
                    txtTelefono.getText().trim().isEmpty() || txtNombreUsuario.getText().trim().isEmpty() ||
                    txtContrasena.getText().trim().isEmpty() || txtInstitucion.getText().trim().isEmpty() ||
                    txtEspecifico.getText().trim().isEmpty() || cmbTipoUsuario.getSelectedItem() == "") {
                    Utilidades.mostrarMensaje("Todos los campos son obligatorios.", "Error");
                    return;
                }
                
                // Registrar usuario
                boolean registroExitoso = false;
                
                if ("Estudiante".equals(cmbTipoUsuario.getSelectedItem())) {
                    Estudiante estudiante = controladorUsuarios.registrarEstudiante(
                        txtId.getText().trim(),
                        txtNombre.getText().trim(),
                        txtApellido.getText().trim(),
                        txtCorreo.getText().trim(),
                        txtTelefono.getText().trim(),
                        txtNombreUsuario.getText().trim(),
                        txtContrasena.getText().trim(),
                        txtInstitucion.getText().trim(),
                        txtEspecifico.getText().trim()
                    );
                    
                    registroExitoso = (estudiante != null);
                } else {
                    Docente docente = controladorUsuarios.registrarDocente(
                        txtId.getText().trim(),
                        txtNombre.getText().trim(),
                        txtApellido.getText().trim(),
                        txtCorreo.getText().trim(),
                        txtTelefono.getText().trim(),
                        txtNombreUsuario.getText().trim(),
                        txtContrasena.getText().trim(),
                        txtInstitucion.getText().trim(),
                        txtEspecifico.getText().trim()
                    );
                    
                    registroExitoso = (docente != null);
                }
                
                if (registroExitoso) {
                    Utilidades.mostrarMensaje("Usuario registrado exitosamente.", "Información");
                    cargarTodosLosUsuarios();
                    dialog.dispose();
                } else {
                    Utilidades.mostrarMensaje("No se pudo registrar el usuario. El nombre de usuario ya existe.", "Error");
                }
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        dialog.setVisible(true);
    }
    
    /**
     * Método para editar el usuario seleccionado
     */
    private void editarUsuarioSeleccionado() {
        Usuario usuarioSeleccionado = listaUsuarios.getSelectedValue();
        
        if (usuarioSeleccionado == null) {
            Utilidades.mostrarMensaje("Por favor, seleccione un usuario para editar.", "Información");
            return;
        }
        
        // Crear un diálogo para el formulario de edición
        JDialog dialog = new JDialog(this, "Editar Usuario", true);
        dialog.setSize(500, 400);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));
        
        JPanel panelFormulario = new JPanel(new GridLayout(9, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Componentes del formulario
        JLabel lblId = new JLabel("ID:");
        JTextField txtId = new JTextField(usuarioSeleccionado.getId(), 20);
        txtId.setEditable(false); // No permitir editar el ID
        
        JLabel lblNombre = new JLabel("Nombre:");
        JTextField txtNombre = new JTextField(usuarioSeleccionado.getNombre(), 20);
        
        JLabel lblApellido = new JLabel("Apellido:");
        JTextField txtApellido = new JTextField(usuarioSeleccionado.getApellido(), 20);
        
        JLabel lblCorreo = new JLabel("Correo:");
        JTextField txtCorreo = new JTextField(usuarioSeleccionado.getCorreo(), 20);
        
        JLabel lblTelefono = new JLabel("Teléfono:");
        JTextField txtTelefono = new JTextField(usuarioSeleccionado.getTelefono(), 20);
        
        JLabel lblNombreUsuario = new JLabel("Nombre de Usuario:");
        JTextField txtNombreUsuario = new JTextField(usuarioSeleccionado.getNombreUsuario(), 20);
        txtNombreUsuario.setEditable(false); // No permitir editar el nombre de usuario
        
        JLabel lblContrasena = new JLabel("Contraseña:");
        JTextField txtContrasena = new JTextField(usuarioSeleccionado.getContrasena(), 20);
        
        JLabel lblInstitucion = new JLabel("Institución:");
        JTextField txtInstitucion = new JTextField(20);
        
        JLabel lblEspecifico = new JLabel();
        JTextField txtEspecifico = new JTextField(20);
        
        // Configurar campos específicos según el tipo de usuario
        if (usuarioSeleccionado instanceof Estudiante) {
            Estudiante estudiante = (Estudiante) usuarioSeleccionado;
            lblEspecifico.setText("Grado:");
            txtInstitucion.setText(estudiante.getInstitucion());
            txtEspecifico.setText(estudiante.getGrado());
        } else if (usuarioSeleccionado instanceof Docente) {
            Docente docente = (Docente) usuarioSeleccionado;
            lblEspecifico.setText("Especialidad:");
            txtInstitucion.setText(docente.getInstitucion());
            txtEspecifico.setText(docente.getEspecialidad());
        }
        
        // Agregar componentes al panel
        panelFormulario.add(lblId);
        panelFormulario.add(txtId);
        panelFormulario.add(lblNombre);
        panelFormulario.add(txtNombre);
        panelFormulario.add(lblApellido);
        panelFormulario.add(txtApellido);
        panelFormulario.add(lblCorreo);
        panelFormulario.add(txtCorreo);
        panelFormulario.add(lblTelefono);
        panelFormulario.add(txtTelefono);
        panelFormulario.add(lblNombreUsuario);
        panelFormulario.add(txtNombreUsuario);
        panelFormulario.add(lblContrasena);
        panelFormulario.add(txtContrasena);
        panelFormulario.add(lblInstitucion);
        panelFormulario.add(txtInstitucion);
        panelFormulario.add(lblEspecifico);
        panelFormulario.add(txtEspecifico);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        // Agregar paneles al diálogo
        dialog.add(panelFormulario, BorderLayout.CENTER);
        dialog.add(panelBotones, BorderLayout.SOUTH);
        
        // Configurar eventos
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validar campos
                if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty() ||
                    txtCorreo.getText().trim().isEmpty() || txtTelefono.getText().trim().isEmpty() ||
                    txtContrasena.getText().trim().isEmpty() || txtInstitucion.getText().trim().isEmpty() ||
                    txtEspecifico.getText().trim().isEmpty()) {
                    Utilidades.mostrarMensaje("Todos los campos son obligatorios.", "Error");
                    return;
                }
                
                // Actualizar usuario
                usuarioSeleccionado.setNombre(txtNombre.getText().trim());
                usuarioSeleccionado.setApellido(txtApellido.getText().trim());
                usuarioSeleccionado.setCorreo(txtCorreo.getText().trim());
                usuarioSeleccionado.setTelefono(txtTelefono.getText().trim());
                usuarioSeleccionado.setContrasena(txtContrasena.getText().trim());
                
                if (usuarioSeleccionado instanceof Estudiante) {
                    Estudiante estudiante = (Estudiante) usuarioSeleccionado;
                    estudiante.setInstitucion(txtInstitucion.getText().trim());
                    estudiante.setGrado(txtEspecifico.getText().trim());
                } else if (usuarioSeleccionado instanceof Docente) {
                    Docente docente = (Docente) usuarioSeleccionado;
                    docente.setInstitucion(txtInstitucion.getText().trim());
                    docente.setEspecialidad(txtEspecifico.getText().trim());
                }
                
                Utilidades.mostrarMensaje("Usuario actualizado exitosamente.", "Información");
                actualizarDetallesUsuario();
                dialog.dispose();
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        dialog.setVisible(true);
    }
    
    /**
     * Método para eliminar el usuario seleccionado
     */
    private void eliminarUsuarioSeleccionado() {
        Usuario usuarioSeleccionado = listaUsuarios.getSelectedValue();
        
        if (usuarioSeleccionado == null) {
            Utilidades.mostrarMensaje("Por favor, seleccione un usuario para eliminar.", "Información");
            return;
        }
        
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar al usuario " + usuarioSeleccionado.getNombreCompleto() + "?",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (respuesta == JOptionPane.YES_OPTION) {
            // Verificar si es un docente con proyectos
            if (usuarioSeleccionado instanceof Docente) {
                Docente docente = (Docente) usuarioSeleccionado;
                if (!docente.getProyectos().isEmpty()) {
                    Utilidades.mostrarMensaje(
                        "No se puede eliminar al docente porque tiene proyectos asignados. " +
                        "Por favor, reasigne o elimine los proyectos primero.",
                        "Error"
                    );
                    return;
                }
            }
            
            // Eliminar el usuario
            boolean eliminado = false;
            for (int i = 0; i < modeloListaUsuarios.size(); i++) {
                Usuario usuario = modeloListaUsuarios.getElementAt(i);
                if (usuario.getId().equals(usuarioSeleccionado.getId())) {
                    modeloListaUsuarios.remove(i);
                    eliminado = true;
                    break;
                }
            }
            
            if (eliminado) {
                Utilidades.mostrarMensaje("Usuario eliminado exitosamente.", "Información");
                txtDetallesUsuario.setText("");
            } else {
                Utilidades.mostrarMensaje("No se pudo eliminar el usuario.", "Error");
            }
        }
    }
    
    /**
     * Método para cargar todos los proyectos
     */
    private void cargarTodosLosProyectos() {
        modeloListaProyectos.clear();
        
        List<Proyecto> proyectos = controladorProyectos.obtenerProyectos();
        for (Proyecto proyecto : proyectos) {
            modeloListaProyectos.addElement(proyecto);
        }
        
        if (modeloListaProyectos.isEmpty()) {
            Utilidades.mostrarMensaje("No hay proyectos registrados en el sistema.", "Información");
        }
    }
    
    /**
     * Método para actualizar los detalles del proyecto seleccionado
     */
    private void actualizarDetallesProyecto() {
        Proyecto proyectoSeleccionado = listaProyectos.getSelectedValue();
        
        if (proyectoSeleccionado != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("DETALLES DEL PROYECTO\n");
            sb.append("====================\n\n");
            sb.append("ID: ").append(proyectoSeleccionado.getId()).append("\n");
            sb.append("Título: ").append(proyectoSeleccionado.getTitulo()).append("\n");
            sb.append("Área: ").append(proyectoSeleccionado.getArea()).append("\n");
            sb.append("Estado Actual: ").append(proyectoSeleccionado.getEstado()).append("\n");
            sb.append("Fecha Creación: ").append(proyectoSeleccionado.getFechaCreacion()).append("\n");
            sb.append("Docente: ").append(proyectoSeleccionado.getDocente().getNombreCompleto()).append("\n");
            sb.append("Institución: ").append(proyectoSeleccionado.getDocente().getInstitucion()).append("\n\n");
            
            sb.append("Objetivos:\n").append(proyectoSeleccionado.getObjetivos()).append("\n\n");
            sb.append("Presupuesto: $").append(proyectoSeleccionado.getPresupuesto()).append("\n\n");
            
            sb.append("Integrantes:\n");
            for (Estudiante estudiante : proyectoSeleccionado.getEstudiantes()) {
                sb.append("- ").append(estudiante.getNombreCompleto())
                  .append(" (").append(estudiante.getInstitucion()).append(")").append("\n");
            }
            
            sb.append("\nHistorial de Estados:\n");
            if (proyectoSeleccionado.getHistorialEstados().isEmpty()) {
                sb.append("No hay cambios de estado registrados.\n");
            } else {
                for (CambioEstado cambio : proyectoSeleccionado.getHistorialEstados()) {
                    sb.append("- ").append(cambio.getFecha()).append(": ")
                      .append(cambio.getEstadoAnterior()).append(" → ")
                      .append(cambio.getEstadoNuevo())
                      .append(" (Autorizado por: ").append(cambio.getCoordinador().getNombreCompleto())
                      .append(")\n");
                }
            }
            
            txtDetallesProyecto.setText(sb.toString());
        } else {
            txtDetallesProyecto.setText("");
        }
    }
    
    /**
     * Método para mostrar el formulario para cambiar el estado de un proyecto
     */
    private void mostrarFormularioCambiarEstado() {
        Proyecto proyectoSeleccionado = listaProyectos.getSelectedValue();
        
        if (proyectoSeleccionado == null) {
            Utilidades.mostrarError("Debe seleccionar un proyecto", "Error");
            return;
        }
        
        JDialog dialog = new JDialog(this, "Cambiar Estado del Proyecto", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblEstadoActual = new JLabel("Estado Actual: " + proyectoSeleccionado.getEstado());
        JLabel lblNuevoEstado = new JLabel("Nuevo Estado:");
        
        JComboBox<EstadoProyecto> cmbNuevoEstado = new JComboBox<>(EstadoProyecto.values());
        
        // Seleccionar el estado actual por defecto
        cmbNuevoEstado.setSelectedItem(proyectoSeleccionado.getEstado());
        
        JLabel lblComentario = new JLabel("Comentario:");
        JTextArea txtComentario = new JTextArea(3, 20);
        
        // Aumentar tamaño de letra en un 50%
        Font fontComentario = txtComentario.getFont();
        float newSizeComentario = fontComentario.getSize() * 1.5f;
        txtComentario.setFont(fontComentario.deriveFont(newSizeComentario));
        
        JScrollPane scrollComentario = new JScrollPane(txtComentario);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        JPanel panelCampos = new JPanel(new GridLayout(3, 2, 5, 5));
        panelCampos.add(lblEstadoActual);
        panelCampos.add(new JLabel()); // Celda vacía
        panelCampos.add(lblNuevoEstado);
        panelCampos.add(cmbNuevoEstado);
        panelCampos.add(lblComentario);
        panelCampos.add(scrollComentario);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        panel.add(panelCampos, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                EstadoProyecto nuevoEstado = (EstadoProyecto) cmbNuevoEstado.getSelectedItem();
                
                // Verificar si se está intentando cambiar al mismo estado
                if (nuevoEstado == proyectoSeleccionado.getEstado()) {
                    Utilidades.mostrarError("El nuevo estado debe ser diferente al estado actual", "Error");
                    return;
                }
                
                // Cambiar el estado del proyecto
                boolean cambioExitoso = controladorProyectos.cambiarEstadoProyecto(
                        proyectoSeleccionado, nuevoEstado, (Coordinador) usuario);
                
                if (cambioExitoso) {
                    Utilidades.mostrarMensaje("Estado del proyecto cambiado exitosamente", "Éxito");
                    actualizarDetallesProyecto();
                    dialog.dispose();
                } else {
                    Utilidades.mostrarError("Error al cambiar el estado del proyecto", "Error");
                }
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
    
    /**
     * Método para filtrar proyectos por estado
     */
    private void filtrarProyectosPorEstado() {
        String estadoSeleccionado = (String) cmbFiltroEstado.getSelectedItem();
        
        if (estadoSeleccionado.equals("Todos")) {
            cargarTodosLosProyectos();
            return;
        }
        
        // Convertir el nombre del estado a la enumeración
        EstadoProyecto estado = null;
        for (EstadoProyecto e : EstadoProyecto.values()) {
            if (e.getNombre().equals(estadoSeleccionado)) {
                estado = e;
                break;
            }
        }
        
        if (estado != null) {
            modeloListaProyectos.clear();
            List<Proyecto> proyectosFiltrados = controladorProyectos.filtrarProyectosPorEstado(estado);
            
            for (Proyecto proyecto : proyectosFiltrados) {
                modeloListaProyectos.addElement(proyecto);
            }
            
            if (modeloListaProyectos.isEmpty()) {
                Utilidades.mostrarMensaje("No hay proyectos con el estado seleccionado.", "Información");
            }
        }
    }
    
    /**
     * Método para buscar proyectos por institución
     */
    private void buscarProyectosPorInstitucion() {
        String institucion = txtBusquedaInstitucion.getText().trim();
        
        if (institucion.isEmpty()) {
            Utilidades.mostrarError("Debe ingresar el nombre de la institución", "Error");
            return;
        }
        
        modeloListaProyectos.clear();
        List<Proyecto> proyectosFiltrados = controladorProyectos.filtrarProyectosPorInstitucion(institucion);
        
        for (Proyecto proyecto : proyectosFiltrados) {
            modeloListaProyectos.addElement(proyecto);
        }
        
        if (modeloListaProyectos.isEmpty()) {
            Utilidades.mostrarMensaje("No hay proyectos asociados a la institución: " + institucion, "Información");
        }
    }
    
    /**
     * Método para ver los detalles completos del proyecto seleccionado
     */
    private void verDetallesProyecto() {
        Proyecto proyectoSeleccionado = listaProyectos.getSelectedValue();
        
        if (proyectoSeleccionado == null) {
            Utilidades.mostrarError("Debe seleccionar un proyecto", "Error");
            return;
        }
        
        // Crear una ventana con los detalles completos del proyecto
        JFrame frameDetalles = new JFrame("Detalles Completos del Proyecto");
        frameDetalles.setSize(700, 500);
        frameDetalles.setLocationRelativeTo(this);
        
        JPanel panelDetalles = new JPanel(new BorderLayout(10, 10));
        panelDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea txtDetallesCompletos = new JTextArea();
        txtDetallesCompletos.setEditable(false);
        
        // Aumentar tamaño de letra en un 50%
        Font fontDetallesCompletos = txtDetallesCompletos.getFont();
        float newSizeDetallesCompletos = fontDetallesCompletos.getSize() * 1.5f;
        txtDetallesCompletos.setFont(fontDetallesCompletos.deriveFont(newSizeDetallesCompletos));
        
        StringBuilder sb = new StringBuilder();
        sb.append("INFORME COMPLETO DEL PROYECTO\n");
        sb.append("=============================\n\n");
        sb.append("ID: ").append(proyectoSeleccionado.getId()).append("\n");
        sb.append("Título: ").append(proyectoSeleccionado.getTitulo()).append("\n");
        sb.append("Área: ").append(proyectoSeleccionado.getArea()).append("\n");
        sb.append("Estado Actual: ").append(proyectoSeleccionado.getEstado()).append("\n");
        sb.append("Fecha Creación: ").append(proyectoSeleccionado.getFechaCreacion()).append("\n\n");
        
        sb.append("INFORMACIÓN DEL DOCENTE\n");
        sb.append("----------------------\n");
        sb.append("Nombre: ").append(proyectoSeleccionado.getDocente().getNombreCompleto()).append("\n");
        sb.append("Institución: ").append(proyectoSeleccionado.getDocente().getInstitucion()).append("\n");
        sb.append("Especialidad: ").append(proyectoSeleccionado.getDocente().getEspecialidad()).append("\n");
        sb.append("Correo: ").append(proyectoSeleccionado.getDocente().getCorreo()).append("\n");
        sb.append("Teléfono: ").append(proyectoSeleccionado.getDocente().getTelefono()).append("\n\n");
        
        sb.append("DETALLES DEL PROYECTO\n");
        sb.append("--------------------\n");
        sb.append("Objetivos:\n").append(proyectoSeleccionado.getObjetivos()).append("\n\n");
        sb.append("Cronograma:\n").append(proyectoSeleccionado.getCronograma()).append("\n\n");
        sb.append("Presupuesto: $").append(proyectoSeleccionado.getPresupuesto()).append("\n\n");
        
        sb.append("INTEGRANTES DEL PROYECTO\n");
        sb.append("-----------------------\n");
        sb.append("Docente: ").append(proyectoSeleccionado.getDocente().getNombreCompleto()).append("\n");
        
        for (Estudiante estudiante : proyectoSeleccionado.getEstudiantes()) {
            sb.append("Estudiante: ").append(estudiante.getNombreCompleto())
              .append(" (").append(estudiante.getInstitucion()).append(")")
              .append(" - Grado: ").append(estudiante.getGrado()).append("\n");
        }
        sb.append("\n");
        
        sb.append("AVANCES DEL PROYECTO\n");
        sb.append("-------------------\n");
        if (proyectoSeleccionado.getAvances().isEmpty()) {
            sb.append("No hay avances registrados.\n");
        } else {
            proyectoSeleccionado.getAvances().forEach(avance -> {
                sb.append("Fecha: ").append(avance.getFecha()).append("\n");
                sb.append("Descripción: ").append(avance.getDescripcion()).append("\n");
                if (!avance.getEvidencia().isEmpty()) {
                    sb.append("Evidencia: ").append(avance.getEvidencia()).append("\n");
                }
                sb.append("\n");
            });
        }
        
        sb.append("HISTORIAL DE ESTADOS\n");
        sb.append("-------------------\n");
        if (proyectoSeleccionado.getHistorialEstados().isEmpty()) {
            sb.append("No hay cambios de estado registrados.\n");
        } else {
            proyectoSeleccionado.getHistorialEstados().forEach(cambio -> {
                sb.append("Fecha: ").append(cambio.getFecha()).append("\n");
                sb.append("Cambio: ").append(cambio.getEstadoAnterior()).append(" → ")
                  .append(cambio.getEstadoNuevo()).append("\n");
                sb.append("Autorizado por: ").append(cambio.getCoordinador().getNombreCompleto()).append("\n");
                if (!cambio.getComentario().isEmpty()) {
                    sb.append("Comentario: ").append(cambio.getComentario()).append("\n");
                }
                sb.append("\n");
            });
        }
        
        txtDetallesCompletos.setText(sb.toString());
        
        JScrollPane scrollDetalles = new JScrollPane(txtDetallesCompletos);
        
        JButton btnImprimir = new JButton("Imprimir");
        JButton btnCerrar = new JButton("Cerrar");
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnImprimir);
        panelBotones.add(btnCerrar);
        
        panelDetalles.add(scrollDetalles, BorderLayout.CENTER);
        panelDetalles.add(panelBotones, BorderLayout.SOUTH);
        
        btnImprimir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utilidades.mostrarMensaje("Funcionalidad de impresión no implementada", "Información");
            }
        });
        
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frameDetalles.dispose();
            }
        });
        
        frameDetalles.add(panelDetalles);
        frameDetalles.setVisible(true);
    }
    
    /**
     * Método para eliminar el proyecto seleccionado
     */
    private void eliminarProyectoSeleccionado() {
        Proyecto proyectoSeleccionado = listaProyectos.getSelectedValue();
        
        if (proyectoSeleccionado == null) {
            Utilidades.mostrarError("Debe seleccionar un proyecto para eliminar", "Error");
            return;
        }
        
        int respuesta = JOptionPane.showConfirmDialog(
            this,
            "¿Está seguro de eliminar el proyecto '" + proyectoSeleccionado.getTitulo() + "'?\n" +
            "Esta acción no se puede deshacer.",
            "Confirmar Eliminación",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );
        
        if (respuesta == JOptionPane.YES_OPTION) {
            // Eliminar el proyecto del modelo
            modeloListaProyectos.removeElement(proyectoSeleccionado);
            
            // Limpiar el área de detalles
            txtDetallesProyecto.setText("");
            
            // Mostrar mensaje de éxito
            Utilidades.mostrarMensaje("Proyecto eliminado exitosamente", "Éxito");
        }
    }
    
    /**
     * Método para actualizar el proyecto seleccionado
     */
    private void actualizarProyectoSeleccionado() {
        Proyecto proyectoSeleccionado = listaProyectos.getSelectedValue();
        
        if (proyectoSeleccionado == null) {
            Utilidades.mostrarError("Debe seleccionar un proyecto para actualizar", "Error");
            return;
        }
        
        // Crear un diálogo para editar el proyecto
        JDialog dialog = new JDialog(this, "Actualizar Proyecto", true);
        dialog.setSize(600, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel para los campos del formulario
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));
        
        JLabel lblTitulo = new JLabel("Título:");
        JTextField txtTitulo = new JTextField(proyectoSeleccionado.getTitulo());
        
        JLabel lblArea = new JLabel("Área:");
        JTextField txtArea = new JTextField(proyectoSeleccionado.getArea());
        
        JLabel lblObjetivos = new JLabel("Objetivos:");
        JTextArea txtObjetivos = new JTextArea(proyectoSeleccionado.getObjetivos(), 5, 20);
        
        // Aumentar tamaño de letra en un 20%
        Font fontObjetivos = txtObjetivos.getFont();
        float newSizeObjetivos = fontObjetivos.getSize() * 1.2f;
        txtObjetivos.setFont(fontObjetivos.deriveFont(newSizeObjetivos));
        
        JScrollPane scrollObjetivos = new JScrollPane(txtObjetivos);
        
        JLabel lblCronograma = new JLabel("Cronograma:");
        JTextArea txtCronograma = new JTextArea(proyectoSeleccionado.getCronograma(), 5, 20);
        
        // Aumentar tamaño de letra en un 20%
        Font fontCronograma = txtCronograma.getFont();
        float newSizeCronograma = fontCronograma.getSize() * 1.2f;
        txtCronograma.setFont(fontCronograma.deriveFont(newSizeCronograma));
        
        JScrollPane scrollCronograma = new JScrollPane(txtCronograma);
        
        JLabel lblPresupuesto = new JLabel("Presupuesto ($):");
        JTextField txtPresupuesto = new JTextField(String.valueOf(proyectoSeleccionado.getPresupuesto()));
        
        JLabel lblDocente = new JLabel("Docente:");
        JComboBox<Docente> cmbDocente = new JComboBox<>();
        
        // Cargar todos los docentes disponibles
        List<Docente> docentes = controladorUsuarios.obtenerDocentes();
        for (Docente docente : docentes) {
            cmbDocente.addItem(docente);
            // Seleccionar el docente actual del proyecto
            if (docente.getId().equals(proyectoSeleccionado.getDocente().getId())) {
                cmbDocente.setSelectedItem(docente);
            }
        }
        
        // Agregar componentes al panel del formulario
        panelFormulario.add(lblTitulo);
        panelFormulario.add(txtTitulo);
        panelFormulario.add(lblArea);
        panelFormulario.add(txtArea);
        panelFormulario.add(lblObjetivos);
        panelFormulario.add(scrollObjetivos);
        panelFormulario.add(lblCronograma);
        panelFormulario.add(scrollCronograma);
        panelFormulario.add(lblPresupuesto);
        panelFormulario.add(txtPresupuesto);
        panelFormulario.add(lblDocente);
        panelFormulario.add(cmbDocente);
        
        // Panel de botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        // Agregar paneles al diálogo
        panel.add(new JLabel("Actualizar información del proyecto:"), BorderLayout.NORTH);
        panel.add(panelFormulario, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        // Eventos
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Validar campos
                    String titulo = txtTitulo.getText().trim();
                    String area = txtArea.getText().trim();
                    String objetivos = txtObjetivos.getText().trim();
                    String cronograma = txtCronograma.getText().trim();
                    String presupuestoStr = txtPresupuesto.getText().trim();
                    
                    if (titulo.isEmpty() || area.isEmpty() || objetivos.isEmpty() || 
                        cronograma.isEmpty() || presupuestoStr.isEmpty()) {
                        Utilidades.mostrarError("Todos los campos son obligatorios", "Error de validación");
                        return;
                    }
                    
                    double presupuesto = Double.parseDouble(presupuestoStr);
                    Docente docenteSeleccionado = (Docente) cmbDocente.getSelectedItem();
                    
                    // Actualizar el proyecto
                    proyectoSeleccionado.setTitulo(titulo);
                    proyectoSeleccionado.setArea(area);
                    proyectoSeleccionado.setObjetivos(objetivos);
                    proyectoSeleccionado.setCronograma(cronograma);
                    proyectoSeleccionado.setPresupuesto(presupuesto);
                    
                    // Si el docente cambió, actualizar la relación
                    if (!proyectoSeleccionado.getDocente().equals(docenteSeleccionado)) {
                        // Remover el proyecto del docente anterior
                        Docente docenteAnterior = proyectoSeleccionado.getDocente();
                        docenteAnterior.getProyectos().remove(proyectoSeleccionado);
                        
                        // Asignar el nuevo docente
                        proyectoSeleccionado.setDocente(docenteSeleccionado);
                        
                        // Agregar el proyecto a la lista del nuevo docente
                        docenteSeleccionado.agregarProyecto(proyectoSeleccionado);
                    }
                    
                    Utilidades.mostrarMensaje("Proyecto actualizado exitosamente", "Éxito");
                    actualizarDetallesProyecto();
                    dialog.dispose();
                    
                } catch (NumberFormatException ex) {
                    Utilidades.mostrarError("El presupuesto debe ser un número válido", "Error de validación");
                }
            }
        });
        
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dialog.dispose();
            }
        });
        
        dialog.add(panel);
        dialog.setVisible(true);
    }
}
