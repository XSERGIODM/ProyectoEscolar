package com.proyectoescolar.vista;

import com.proyectoescolar.controlador.ControladorUsuarios;
import com.proyectoescolar.controlador.ControladorProyectos;
import com.proyectoescolar.modelo.Usuario;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

/**
 * Ventana base que contiene elementos comunes para todas las ventanas principales.
 */
public abstract class VentanaBase extends JFrame {
    
    protected Usuario usuario;
    protected ControladorUsuarios controladorUsuarios;
    protected ControladorProyectos controladorProyectos;
    protected JPanel panelContenido;
    
    /**
     * Constructor de la ventana base
     * @param usuario Usuario autenticado
     * @param controladorUsuarios Controlador de usuarios
     * @param controladorProyectos Controlador de proyectos
     * @param titulo Título de la ventana
     */
    public VentanaBase(Usuario usuario, ControladorUsuarios controladorUsuarios, 
                      ControladorProyectos controladorProyectos, String titulo) {
        this.usuario = usuario;
        this.controladorUsuarios = controladorUsuarios;
        this.controladorProyectos = controladorProyectos;
        
        setTitle(titulo);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        configurarComponentesBase();
    }
    
    /**
     * Método para configurar los componentes básicos de la ventana
     */
    private void configurarComponentesBase() {
        setLayout(new BorderLayout());
        
        // Panel superior con información del usuario
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelSuperior.setBackground(new Color(230, 230, 250));
        
        JLabel lblBienvenida = new JLabel("Bienvenido, " + usuario.getNombreCompleto());
        lblBienvenida.setFont(new Font("Arial", Font.BOLD, 14));
        
        JLabel lblRol = new JLabel("Rol: " + usuario.getRol());
        
        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelInfo.setOpaque(false);
        panelInfo.add(lblBienvenida);
        
        JPanel panelRol = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelRol.setOpaque(false);
        panelRol.add(lblRol);
        
        panelSuperior.add(panelInfo, BorderLayout.WEST);
        panelSuperior.add(panelRol, BorderLayout.EAST);
        
        // Menú superior
        JMenuBar menuBar = new JMenuBar();
        
        JMenu menuArchivo = new JMenu("Archivo");
        JMenuItem menuCerrarSesion = new JMenuItem("Cerrar Sesión");
        JMenuItem menuSalir = new JMenuItem("Salir");
        
        menuArchivo.add(menuCerrarSesion);
        menuArchivo.addSeparator();
        menuArchivo.add(menuSalir);
        
        JMenu menuAyuda = new JMenu("Ayuda");
        JMenuItem menuAcercaDe = new JMenuItem("Acerca de");
        
        menuAyuda.add(menuAcercaDe);
        
        menuBar.add(menuArchivo);
        menuBar.add(menuAyuda);
        
        setJMenuBar(menuBar);
        
        // Panel de contenido (será diferente en cada ventana específica)
        panelContenido = new JPanel(new BorderLayout());
        panelContenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Panel inferior con botones
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelInferior.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        panelInferior.setBackground(new Color(240, 240, 240));
        
        JButton btnCerrarSesion = new JButton("Cerrar Sesión");
        btnCerrarSesion.setPreferredSize(new Dimension(120, 30));
        
        panelInferior.add(btnCerrarSesion);
        
        // Eventos
        btnCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
        
        menuCerrarSesion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cerrarSesion();
            }
        });
        
        menuSalir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        
        menuAcercaDe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarAcercaDe();
            }
        });
        
        // Añadir a la ventana
        add(panelSuperior, BorderLayout.NORTH);
        add(panelContenido, BorderLayout.CENTER);
        add(panelInferior, BorderLayout.SOUTH);
    }
    
    /**
     * Método para cerrar sesión y volver a la ventana de login
     */
    private void cerrarSesion() {
        controladorUsuarios.cerrarSesion();
        dispose();
        VentanaLogin ventanaLogin = new VentanaLogin(controladorUsuarios, controladorProyectos);
        ventanaLogin.setVisible(true);
    }
    
    /**
     * Método para mostrar información acerca de la aplicación
     */
    private void mostrarAcercaDe() {
        JFrame frame = new JFrame("Acerca de");
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(this);
        frame.setResizable(false);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JLabel lblTitulo = new JLabel("Sistema de Gestión de Proyectos Escolares", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 16));
        
        JLabel lblDescripcion = new JLabel("<html><center>Aplicación desarrollada para el registro y seguimiento de proyectos escolares.<br><br>Versión 1.0<br><br>© 2025</center></html>", SwingConstants.CENTER);
        
        JButton btnCerrar = new JButton("Cerrar");
        btnCerrar.setPreferredSize(new Dimension(100, 30));
        
        JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBoton.add(btnCerrar);
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(lblDescripcion, BorderLayout.CENTER);
        panel.add(panelBoton, BorderLayout.SOUTH);
        
        frame.add(panel);
        
        btnCerrar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
            }
        });
        
        frame.setVisible(true);
    }
    
    /**
     * Método abstracto que debe ser implementado por las clases hijas
     * para configurar sus componentes específicos
     */
    protected abstract void configurarComponentesEspecificos();
}
