package com.proyectoescolar.vista;

import com.proyectoescolar.controlador.ControladorProyectos;
import com.proyectoescolar.controlador.ControladorUsuarios;
import com.proyectoescolar.modelo.Docente;
import com.proyectoescolar.modelo.Estudiante;
import com.proyectoescolar.modelo.Proyecto;
import com.proyectoescolar.utilidades.Utilidades;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * Ventana principal para el rol de Docente.
 */
public class VentanaDocente extends VentanaBase {
    
    // El controlador de proyectos viene como atributo heredado de VentanaBase
    private JList<Proyecto> listaProyectos;
    private DefaultListModel<Proyecto> modeloListaProyectos;
    private JTextArea txtDetallesProyecto;
    private JButton btnCrearProyecto;
    private JButton btnAgregarEstudiante;
    private JButton btnRegistrarAvance;
    private JButton btnVerDetalles;
    
    /**
     * Constructor de la ventana para docentes
     * @param docente Docente autenticado
     * @param controladorUsuarios Controlador de usuarios
     * @param controladorProyectos Controlador de proyectos
     */
    public VentanaDocente(Docente docente, ControladorUsuarios controladorUsuarios,
                        ControladorProyectos controladorProyectos) {
        super(docente, controladorUsuarios, controladorProyectos, "Sistema de Gestión de Proyectos - Docente");
        configurarComponentesEspecificos();
    }
    
    @Override
    protected void configurarComponentesEspecificos() {
        // Panel de pestañas
        JTabbedPane tabbedPane = new JTabbedPane();
        
        // Pestaña de gestión de proyectos
        JPanel panelGestionProyectos = new JPanel(new BorderLayout(10, 10));
        
        // Panel de lista de proyectos
        JPanel panelLista = new JPanel(new BorderLayout(5, 5));
        panelLista.setBorder(BorderFactory.createTitledBorder("Mis Proyectos"));
        
        modeloListaProyectos = new DefaultListModel<>();
        listaProyectos = new JList<>(modeloListaProyectos);
        listaProyectos.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        JScrollPane scrollProyectos = new JScrollPane(listaProyectos);
        scrollProyectos.setPreferredSize(new Dimension(250, 400));
        
        JPanel panelBotones = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        btnCrearProyecto = new JButton("Crear Proyecto");
        btnAgregarEstudiante = new JButton("Agregar Estudiante");
        btnRegistrarAvance = new JButton("Registrar Avance");
        btnVerDetalles = new JButton("Ver Detalles");
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelBotones.add(btnCrearProyecto, gbc);
        
        gbc.gridy = 1;
        panelBotones.add(btnAgregarEstudiante, gbc);
        
        gbc.gridy = 2;
        panelBotones.add(btnRegistrarAvance, gbc);
        
        gbc.gridy = 3;
        panelBotones.add(btnVerDetalles, gbc);
        
        panelLista.add(new JLabel("Seleccione un proyecto:"), BorderLayout.NORTH);
        panelLista.add(scrollProyectos, BorderLayout.CENTER);
        panelLista.add(panelBotones, BorderLayout.EAST);
        
        // Panel de detalles
        JPanel panelDetalles = new JPanel(new BorderLayout(5, 5));
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Proyecto"));
        
        txtDetallesProyecto = new JTextArea();
        txtDetallesProyecto.setEditable(false);
        
        JScrollPane scrollDetalles = new JScrollPane(txtDetallesProyecto);
        scrollDetalles.setPreferredSize(new Dimension(400, 400));
        
        panelDetalles.add(scrollDetalles, BorderLayout.CENTER);
        
        panelGestionProyectos.add(panelLista, BorderLayout.WEST);
        panelGestionProyectos.add(panelDetalles, BorderLayout.CENTER);
        
        // Agregar pestañas
        tabbedPane.addTab("Gestión de Proyectos", panelGestionProyectos);
        
        // Agregar el panel de pestañas al panel de contenido
        panelContenido.add(tabbedPane, BorderLayout.CENTER);
        
        // Configurar eventos
        btnCrearProyecto.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioCrearProyecto();
            }
        });
        
        btnAgregarEstudiante.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioAgregarEstudiante();
            }
        });
        
        btnRegistrarAvance.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mostrarFormularioRegistrarAvance();
            }
        });
        
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
        
        // Cargar proyectos del docente
        cargarProyectosDelDocente();
    }
    
    /**
     * Método para cargar los proyectos del docente
     */
    private void cargarProyectosDelDocente() {
        modeloListaProyectos.clear();
        
        Docente docente = (Docente) usuario;
        List<Proyecto> proyectosDocente = controladorProyectos.filtrarProyectosPorDocente(docente);
        
        for (Proyecto proyecto : proyectosDocente) {
            modeloListaProyectos.addElement(proyecto);
        }
        
        if (modeloListaProyectos.isEmpty()) {
            Utilidades.mostrarMensaje("No tiene proyectos registrados. Puede crear uno nuevo con el botón 'Crear Proyecto'.", "Información");
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
            sb.append("Estado: ").append(proyectoSeleccionado.getEstado()).append("\n");
            sb.append("Fecha Creación: ").append(proyectoSeleccionado.getFechaCreacion()).append("\n\n");
            
            sb.append("Objetivos:\n").append(proyectoSeleccionado.getObjetivos()).append("\n\n");
            sb.append("Cronograma:\n").append(proyectoSeleccionado.getCronograma()).append("\n\n");
            sb.append("Presupuesto: $").append(proyectoSeleccionado.getPresupuesto()).append("\n\n");
            
            sb.append("Integrantes:\n");
            for (Estudiante estudiante : proyectoSeleccionado.getEstudiantes()) {
                sb.append("- ").append(estudiante.getNombreCompleto())
                  .append(" (").append(estudiante.getInstitucion()).append(")").append("\n");
            }
            
            sb.append("\nAvances Registrados:\n");
            if (proyectoSeleccionado.getAvances().isEmpty()) {
                sb.append("No hay avances registrados.\n");
            } else {
                proyectoSeleccionado.getAvances().forEach(avance -> {
                    sb.append("- ").append(avance.getFecha()).append(": ")
                      .append(avance.getDescripcion()).append("\n");
                });
            }
            
            txtDetallesProyecto.setText(sb.toString());
        } else {
            txtDetallesProyecto.setText("");
        }
    }
    
    /**
     * Método para mostrar el formulario de creación de proyectos
     */
    private void mostrarFormularioCrearProyecto() {
        JDialog dialog = new JDialog(this, "Crear Nuevo Proyecto", true);
        dialog.setSize(500, 500);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Campos del formulario
        JLabel lblTitulo = new JLabel("Título:");
        JTextField txtTitulo = new JTextField(20);
        
        JLabel lblArea = new JLabel("Área:");
        JTextField txtArea = new JTextField(20);
        
        JLabel lblObjetivos = new JLabel("Objetivos:");
        JTextArea txtObjetivos = new JTextArea(5, 20);
        JScrollPane scrollObjetivos = new JScrollPane(txtObjetivos);
        
        JLabel lblCronograma = new JLabel("Cronograma:");
        JTextArea txtCronograma = new JTextArea(5, 20);
        JScrollPane scrollCronograma = new JScrollPane(txtCronograma);
        
        JLabel lblPresupuesto = new JLabel("Presupuesto ($):");
        JTextField txtPresupuesto = new JTextField(20);
        
        // Botones
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        // Agregar componentes al panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(lblTitulo, gbc);
        
        gbc.gridx = 1;
        panel.add(txtTitulo, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(lblArea, gbc);
        
        gbc.gridx = 1;
        panel.add(txtArea, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(lblObjetivos, gbc);
        
        gbc.gridx = 1;
        panel.add(scrollObjetivos, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        panel.add(lblCronograma, gbc);
        
        gbc.gridx = 1;
        panel.add(scrollCronograma, gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 4;
        panel.add(lblPresupuesto, gbc);
        
        gbc.gridx = 1;
        panel.add(txtPresupuesto, gbc);
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        panel.add(panelBotones, gbc);
        
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
                    
                    // Crear el proyecto
                    Proyecto nuevoProyecto = controladorProyectos.crearProyecto(
                            titulo, area, objetivos, cronograma, presupuesto, (Docente) usuario);
                    
                    if (nuevoProyecto != null) {
                        Utilidades.mostrarMensaje("Proyecto creado exitosamente", "Éxito");
                        modeloListaProyectos.addElement(nuevoProyecto);
                        dialog.dispose();
                    } else {
                        Utilidades.mostrarError("Error al crear el proyecto", "Error");
                    }
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
    
    /**
     * Método para mostrar el formulario para agregar estudiantes a un proyecto
     */
    private void mostrarFormularioAgregarEstudiante() {
        Proyecto proyectoSeleccionado = listaProyectos.getSelectedValue();
        
        if (proyectoSeleccionado == null) {
            Utilidades.mostrarError("Debe seleccionar un proyecto", "Error");
            return;
        }
        
        if (proyectoSeleccionado.getEstudiantes().size() >= 10) {
            Utilidades.mostrarError("El proyecto ya tiene el máximo de 10 estudiantes", "Error");
            return;
        }
        
        List<Estudiante> estudiantesDisponibles = controladorUsuarios.obtenerEstudiantes();
        
        // Filtrar estudiantes que ya están en el proyecto
        for (int i = estudiantesDisponibles.size() - 1; i >= 0; i--) {
            for (Estudiante estudianteProyecto : proyectoSeleccionado.getEstudiantes()) {
                if (estudiantesDisponibles.get(i).getId().equals(estudianteProyecto.getId())) {
                    estudiantesDisponibles.remove(i);
                    break;
                }
            }
        }
        
        if (estudiantesDisponibles.isEmpty()) {
            Utilidades.mostrarError("No hay estudiantes disponibles para agregar", "Error");
            return;
        }
        
        JDialog dialog = new JDialog(this, "Agregar Estudiante al Proyecto", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblTitulo = new JLabel("Seleccione un estudiante para agregar al proyecto:");
        
        JComboBox<Estudiante> comboEstudiantes = new JComboBox<>();
        for (Estudiante estudiante : estudiantesDisponibles) {
            comboEstudiantes.addItem(estudiante);
        }
        
        JButton btnAgregar = new JButton("Agregar");
        JButton btnCancelar = new JButton("Cancelar");
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnCancelar);
        
        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(comboEstudiantes, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        btnAgregar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Estudiante estudianteSeleccionado = (Estudiante) comboEstudiantes.getSelectedItem();
                
                if (estudianteSeleccionado != null) {
                    boolean agregado = controladorProyectos.agregarEstudianteAProyecto(
                            proyectoSeleccionado, estudianteSeleccionado);
                    
                    if (agregado) {
                        Utilidades.mostrarMensaje("Estudiante agregado exitosamente", "Éxito");
                        actualizarDetallesProyecto();
                        dialog.dispose();
                    } else {
                        Utilidades.mostrarError("Error al agregar el estudiante", "Error");
                    }
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
     * Método para mostrar el formulario de registro de avances
     */
    private void mostrarFormularioRegistrarAvance() {
        Proyecto proyectoSeleccionado = listaProyectos.getSelectedValue();
        
        if (proyectoSeleccionado == null) {
            Utilidades.mostrarError("Debe seleccionar un proyecto", "Error");
            return;
        }
        
        JDialog dialog = new JDialog(this, "Registrar Avance", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JLabel lblDescripcion = new JLabel("Descripción del Avance:");
        JTextArea txtDescripcion = new JTextArea(5, 20);
        JScrollPane scrollDescripcion = new JScrollPane(txtDescripcion);
        
        JLabel lblEvidencia = new JLabel("Evidencia (URL o descripción):");
        JTextField txtEvidencia = new JTextField(20);
        
        JPanel panelCampos = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        
        gbc.gridx = 0;
        gbc.gridy = 0;
        panelCampos.add(lblDescripcion, gbc);
        
        gbc.gridy = 1;
        panelCampos.add(scrollDescripcion, gbc);
        
        gbc.gridy = 2;
        panelCampos.add(lblEvidencia, gbc);
        
        gbc.gridy = 3;
        panelCampos.add(txtEvidencia, gbc);
        
        JButton btnGuardar = new JButton("Guardar");
        JButton btnCancelar = new JButton("Cancelar");
        
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnCancelar);
        
        panel.add(panelCampos, BorderLayout.CENTER);
        panel.add(panelBotones, BorderLayout.SOUTH);
        
        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String descripcion = txtDescripcion.getText().trim();
                String evidencia = txtEvidencia.getText().trim();
                
                if (descripcion.isEmpty()) {
                    Utilidades.mostrarError("La descripción es obligatoria", "Error de validación");
                    return;
                }
                
                controladorProyectos.registrarAvance(proyectoSeleccionado, descripcion, evidencia);
                Utilidades.mostrarMensaje("Avance registrado exitosamente", "Éxito");
                actualizarDetallesProyecto();
                dialog.dispose();
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
        frameDetalles.setSize(600, 400);
        frameDetalles.setLocationRelativeTo(this);
        
        JPanel panelDetalles = new JPanel(new BorderLayout(10, 10));
        panelDetalles.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JTextArea txtDetallesCompletos = new JTextArea();
        txtDetallesCompletos.setEditable(false);
        
        StringBuilder sb = new StringBuilder();
        sb.append("DETALLES DEL PROYECTO\n");
        sb.append("====================\n\n");
        sb.append("ID: ").append(proyectoSeleccionado.getId()).append("\n");
        sb.append("Título: ").append(proyectoSeleccionado.getTitulo()).append("\n");
        sb.append("Área: ").append(proyectoSeleccionado.getArea()).append("\n");
        sb.append("Estado: ").append(proyectoSeleccionado.getEstado()).append("\n");
        sb.append("Fecha Creación: ").append(proyectoSeleccionado.getFechaCreacion()).append("\n\n");
        
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
        if (proyectoSeleccionado.getAvances().isEmpty()) {
            sb.append("No hay avances registrados.\n");
        } else {
            proyectoSeleccionado.getAvances().forEach(avance -> {
                sb.append("- ").append(avance.getFecha()).append(": ")
                  .append(avance.getDescripcion()).append("\n");
                if (!avance.getEvidencia().isEmpty()) {
                    sb.append("  Evidencia: ").append(avance.getEvidencia()).append("\n");
                }
            });
        }
        
        sb.append("\nHistorial de Cambios de Estado:\n");
        if (proyectoSeleccionado.getHistorialEstados().isEmpty()) {
            sb.append("No hay cambios de estado registrados.\n");
        } else {
            proyectoSeleccionado.getHistorialEstados().forEach(cambio -> {
                sb.append("- ").append(cambio.getFecha()).append(": ")
                  .append(cambio.getEstadoAnterior()).append(" → ")
                  .append(cambio.getEstadoNuevo())
                  .append(" (Autorizado por: ").append(cambio.getCoordinador().getNombreCompleto())
                  .append(")\n");
            });
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
    }
}
