package com.proyectoescolar.vista;

import com.proyectoescolar.controlador.ControladorUsuarios;
import com.proyectoescolar.controlador.ControladorProyectos;
import com.proyectoescolar.modelo.Coordinador;
import com.proyectoescolar.modelo.Docente;
import com.proyectoescolar.modelo.Estudiante;
import com.proyectoescolar.modelo.Usuario;
import com.proyectoescolar.utilidades.Utilidades;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Ventana de inicio de sesión para la aplicación.
 */
public class VentanaLogin extends JFrame {
    
    private ControladorUsuarios controladorUsuarios;
    private ControladorProyectos controladorProyectos;
    private JTextField txtUsuario;
    private JPasswordField txtContrasena;
    private JButton btnIniciarSesion;
    
    /**
     * Constructor de la ventana de inicio de sesión
     * @param controladorUsuarios Controlador de usuarios
     * @param controladorProyectos Controlador de proyectos
     */
    public VentanaLogin(ControladorUsuarios controladorUsuarios, ControladorProyectos controladorProyectos) {
        this.controladorUsuarios = controladorUsuarios;
        this.controladorProyectos = controladorProyectos;
        configurarVentana();
        inicializarComponentes();
    }
    
    /**
     * Método para configurar las propiedades de la ventana
     */
    private void configurarVentana() {
        setTitle("Gestión de Proyectos Escolares - Inicio de Sesión");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.err.println("Error al establecer el look and feel: " + ex.getMessage());
        }
    }
    
    /**
     * Método para inicializar los componentes de la ventana
     */
    private void inicializarComponentes() {
        // Panel principal
        JPanel panelPrincipal = new JPanel(new BorderLayout(10, 10));
        panelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Panel para el título
        JPanel panelTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel lblTitulo = new JLabel("Sistema de Gestión de Proyectos");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(0, 102, 204));
        panelTitulo.add(lblTitulo);
        
        // Panel para el formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        JLabel lblUsuario = new JLabel("Usuario:", SwingConstants.RIGHT);
        txtUsuario = new JTextField(15);
        JLabel lblContrasena = new JLabel("Contraseña:", SwingConstants.RIGHT);
        txtContrasena = new JPasswordField(15);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelFormulario.add(lblUsuario, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 0;
        panelFormulario.add(txtUsuario, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panelFormulario.add(lblContrasena, gbc);
        
        gbc.gridx = 1;
        gbc.gridy = 1;
        panelFormulario.add(txtContrasena, gbc);
        
        // Panel para los botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnIniciarSesion = new JButton("Iniciar Sesión");
        btnIniciarSesion.setPreferredSize(new Dimension(120, 30));
        
        btnIniciarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarSesion();
            }
        });
        
        panelBotones.add(btnIniciarSesion);
        
        // Agregar todos los paneles al panel principal
        panelPrincipal.add(panelTitulo, BorderLayout.NORTH);
        panelPrincipal.add(panelFormulario, BorderLayout.CENTER);
        panelPrincipal.add(panelBotones, BorderLayout.SOUTH);
        
        // Agregar el panel principal a la ventana
        add(panelPrincipal);
    }
    
    /**
     * Método para iniciar sesión
     */
    private void iniciarSesion() {
        String usuario = txtUsuario.getText();
        String contrasena = new String(txtContrasena.getPassword());
        
        if (usuario.isEmpty() || contrasena.isEmpty()) {
            Utilidades.mostrarError("Debe ingresar usuario y contraseña", "Error de validación");
            return;
        }
        
        Usuario usuarioAutenticado = controladorUsuarios.iniciarSesion(usuario, contrasena);
        
        if (usuarioAutenticado != null) {
            abrirVentanaPrincipal(usuarioAutenticado);
        } else {
            Utilidades.mostrarError("Usuario o contraseña incorrectos", "Error de autenticación");
        }
    }
    
    /**
     * Método para abrir la ventana principal según el tipo de usuario
     * @param usuario Usuario autenticado
     */
    private void abrirVentanaPrincipal(Usuario usuario) {
        dispose(); // Cerramos la ventana de login
        
        if (usuario instanceof Estudiante) {
            VentanaEstudiante ventana = new VentanaEstudiante((Estudiante) usuario, controladorUsuarios, controladorProyectos);
            ventana.setVisible(true);
        } else if (usuario instanceof Docente) {
            VentanaDocente ventana = new VentanaDocente((Docente) usuario, controladorUsuarios, controladorProyectos);
            ventana.setVisible(true);
        } else if (usuario instanceof Coordinador) {
            VentanaCoordinador ventana = new VentanaCoordinador((Coordinador) usuario, controladorUsuarios, controladorProyectos);
            ventana.setVisible(true);
        }
    }
}
