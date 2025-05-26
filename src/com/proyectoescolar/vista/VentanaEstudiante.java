package com.proyectoescolar.vista;

import com.proyectoescolar.controlador.ControladorProyectos;
import com.proyectoescolar.controlador.ControladorUsuarios;
import com.proyectoescolar.modelo.Avance;
import com.proyectoescolar.modelo.Estudiante;
import com.proyectoescolar.modelo.Proyecto;
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
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Ventana principal para el rol de Estudiante.
 */
public class VentanaEstudiante extends VentanaBase {
    
    // El controlador de proyectos viene como atributo heredado de VentanaBase
    private JList<Proyecto> listaProyectos;
    private DefaultListModel<Proyecto> modeloListaProyectos;
    private JTextArea txtDetallesProyecto;
    private JList<Avance> listaAvances;
    private DefaultListModel<Avance> modeloListaAvances;
    private JButton btnVerDetalles;
    
    /**
     * Constructor de la ventana para estudiantes
     * @param estudiante Estudiante autenticado
     * @param controladorUsuarios Controlador de usuarios
     * @param controladorProyectos Controlador de proyectos
     */
    public VentanaEstudiante(Estudiante estudiante, ControladorUsuarios controladorUsuarios,
                           ControladorProyectos controladorProyectos) {
        super(estudiante, controladorUsuarios, controladorProyectos, "Sistema de Gestión de Proyectos - Estudiante");
        configurarComponentesEspecificos();
    }
    
    @Override
    protected void configurarComponentesEspecificos() {
        // Panel principal dividido en dos secciones
        JPanel panelPrincipal = new JPanel(new GridLayout(1, 2, 10, 10));
        
        // Panel izquierdo (Lista de proyectos)
        JPanel panelIzquierdo = new JPanel(new BorderLayout(5, 5));
        panelIzquierdo.setBorder(BorderFactory.createTitledBorder("Mis Proyectos"));
        
        modeloListaProyectos = new DefaultListModel<>();
        listaProyectos = new JList<>(modeloListaProyectos);
        listaProyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollProyectos = new JScrollPane(listaProyectos);
        scrollProyectos.setPreferredSize(new Dimension(300, 300));
        
        JPanel panelBotonesProyectos = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnVerDetalles = new JButton("Ver Detalles");
        
        panelBotonesProyectos.add(btnVerDetalles);
        
        panelIzquierdo.add(new JLabel("Seleccione un proyecto:"), BorderLayout.NORTH);
        panelIzquierdo.add(scrollProyectos, BorderLayout.CENTER);
        panelIzquierdo.add(panelBotonesProyectos, BorderLayout.SOUTH);
        
        // Panel derecho (Detalles del proyecto seleccionado y avances)
        JPanel panelDerecho = new JPanel(new BorderLayout(5, 5));
        panelDerecho.setBorder(BorderFactory.createTitledBorder("Detalles del Proyecto"));
        
        txtDetallesProyecto = new JTextArea();
        txtDetallesProyecto.setEditable(false);
        
        // Aumentar tamaño de letra en un 20%
        Font fontDetallesProyecto = txtDetallesProyecto.getFont();
        float newSizeDetallesProyecto = fontDetallesProyecto.getSize() * 1.2f;
        txtDetallesProyecto.setFont(fontDetallesProyecto.deriveFont(newSizeDetallesProyecto));
        
        txtDetallesProyecto.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JScrollPane scrollDetalles = new JScrollPane(txtDetallesProyecto);
        scrollDetalles.setPreferredSize(new Dimension(300, 150));
        
        modeloListaAvances = new DefaultListModel<>();
        listaAvances = new JList<>(modeloListaAvances);
        listaAvances.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollAvances = new JScrollPane(listaAvances);
        scrollAvances.setPreferredSize(new Dimension(300, 150));
        
        JPanel panelAvances = new JPanel(new BorderLayout(5, 5));
        panelAvances.setBorder(BorderFactory.createTitledBorder("Avances del Proyecto"));
        panelAvances.add(new JLabel("Historial de avances:"), BorderLayout.NORTH);
        panelAvances.add(scrollAvances, BorderLayout.CENTER);
        
        panelDerecho.add(scrollDetalles, BorderLayout.CENTER);
        panelDerecho.add(panelAvances, BorderLayout.SOUTH);
        
        // Agregar los paneles al panel principal
        panelPrincipal.add(panelIzquierdo);
        panelPrincipal.add(panelDerecho);
        
        // Agregar el panel principal al panel de contenido
        panelContenido.add(panelPrincipal, BorderLayout.CENTER);
        
        // Eventos
        btnVerDetalles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                verDetallesProyecto();
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
        
        // Cargar proyectos del estudiante
        cargarProyectosDelEstudiante();
    }
    
    /**
     * Método para cargar los proyectos en los que participa el estudiante
     */
    private void cargarProyectosDelEstudiante() {
        modeloListaProyectos.clear();
        
        // Obtener la lista de todos los proyectos
        List<Proyecto> todosLosProyectos = controladorProyectos.obtenerProyectos();
        
        // Filtrar los proyectos en los que participa el estudiante
        for (Proyecto proyecto : todosLosProyectos) {
            for (Estudiante estudiante : proyecto.getEstudiantes()) {
                if (estudiante.getId().equals(((Estudiante) usuario).getId())) {
                    modeloListaProyectos.addElement(proyecto);
                    break;
                }
            }
        }
        
        // Mostrar mensaje si no hay proyectos
        if (modeloListaProyectos.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                    "No participas en ningún proyecto actualmente.", 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Método para actualizar los detalles del proyecto seleccionado
     */
    private void actualizarDetallesProyecto() {
        Proyecto proyectoSeleccionado = listaProyectos.getSelectedValue();
        
        if (proyectoSeleccionado != null) {
            // Mostrar detalles del proyecto
            StringBuilder sb = new StringBuilder();
            sb.append("Título: ").append(proyectoSeleccionado.getTitulo()).append("\n");
            sb.append("Área: ").append(proyectoSeleccionado.getArea()).append("\n");
            sb.append("Estado: ").append(proyectoSeleccionado.getEstado()).append("\n");
            sb.append("Docente: ").append(proyectoSeleccionado.getDocente().getNombreCompleto()).append("\n");
            sb.append("Fecha Creación: ").append(proyectoSeleccionado.getFechaCreacion()).append("\n");
            sb.append("Objetivos: ").append(proyectoSeleccionado.getObjetivos()).append("\n");
            
            txtDetallesProyecto.setText(sb.toString());
            
            // Cargar avances del proyecto
            cargarAvancesProyecto(proyectoSeleccionado);
        } else {
            txtDetallesProyecto.setText("");
            modeloListaAvances.clear();
        }
    }
    
    /**
     * Método para cargar los avances de un proyecto
     * @param proyecto Proyecto del que se cargarán los avances
     */
    private void cargarAvancesProyecto(Proyecto proyecto) {
        modeloListaAvances.clear();
        
        List<Avance> avances = proyecto.getAvances();
        for (Avance avance : avances) {
            modeloListaAvances.addElement(avance);
        }
    }
    
    /**
     * Método para ver los detalles completos del proyecto seleccionado
     */
    private void verDetallesProyecto() {
        Proyecto proyectoSeleccionado = listaProyectos.getSelectedValue();
        
        if (proyectoSeleccionado != null) {
            // Crear una ventana con los detalles completos del proyecto
            javax.swing.JFrame frameDetalles = new javax.swing.JFrame("Detalles Completos del Proyecto");
            frameDetalles.setSize(600, 400);
            frameDetalles.setLocationRelativeTo(this);
            
            JPanel panelDetalles = new JPanel(new BorderLayout(10, 10));
            panelDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            
            JTextArea txtDetallesCompletos = new JTextArea();
            txtDetallesCompletos.setEditable(false);
            
            // Aumentar tamaño de letra en un 20%
            Font fontDetallesCompletos = txtDetallesCompletos.getFont();
            float newSizeDetallesCompletos = fontDetallesCompletos.getSize() * 1.2f;
            txtDetallesCompletos.setFont(fontDetallesCompletos.deriveFont(newSizeDetallesCompletos));
            
            StringBuilder sb = new StringBuilder();
            sb.append("DETALLES DEL PROYECTO\n");
            sb.append("====================\n\n");
            sb.append("ID: ").append(proyectoSeleccionado.getId()).append("\n");
            sb.append("Título: ").append(proyectoSeleccionado.getTitulo()).append("\n");
            sb.append("Área: ").append(proyectoSeleccionado.getArea()).append("\n");
            sb.append("Estado: ").append(proyectoSeleccionado.getEstado()).append("\n");
            sb.append("Fecha Creación: ").append(proyectoSeleccionado.getFechaCreacion()).append("\n");
            sb.append("Docente: ").append(proyectoSeleccionado.getDocente().getNombreCompleto()).append("\n\n");
            
            sb.append("Objetivos:\n").append(proyectoSeleccionado.getObjetivos()).append("\n\n");
            sb.append("Cronograma:\n").append(proyectoSeleccionado.getCronograma()).append("\n\n");
            sb.append("Presupuesto: $").append(proyectoSeleccionado.getPresupuesto()).append("\n\n");
            
            sb.append("Integrantes:\n");
            sb.append("- Docente: ").append(proyectoSeleccionado.getDocente().getNombreCompleto()).append("\n");
            
            for (Estudiante estudiante : proyectoSeleccionado.getEstudiantes()) {
                sb.append("- Estudiante: ").append(estudiante.getNombreCompleto())
                  .append(" (").append(estudiante.getInstitucion()).append(")").append("\n");
            }
            
            sb.append("\nAvances del Proyecto:\n");
            List<Avance> avances = proyectoSeleccionado.getAvances();
            if (avances.isEmpty()) {
                sb.append("No hay avances registrados.\n");
            } else {
                for (Avance avance : avances) {
                    sb.append("- ").append(avance.getFecha()).append(": ")
                      .append(avance.getDescripcion()).append("\n");
                }
            }
            
            txtDetallesCompletos.setText(sb.toString());
            
            JScrollPane scrollDetalles = new JScrollPane(txtDetallesCompletos);
            
            JButton btnCerrar = new JButton("Cerrar");
            btnCerrar.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    frameDetalles.dispose();
                }
            });
            
            JPanel panelBoton = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            panelBoton.add(btnCerrar);
            
            panelDetalles.add(scrollDetalles, BorderLayout.CENTER);
            panelDetalles.add(panelBoton, BorderLayout.SOUTH);
            
            frameDetalles.add(panelDetalles);
            frameDetalles.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, 
                    "Debe seleccionar un proyecto para ver sus detalles.", 
                    "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
