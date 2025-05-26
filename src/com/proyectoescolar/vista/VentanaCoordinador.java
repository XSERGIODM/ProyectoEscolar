package com.proyectoescolar.vista;

import com.proyectoescolar.controlador.ControladorProyectos;
import com.proyectoescolar.controlador.ControladorUsuarios;
import com.proyectoescolar.modelo.CambioEstado;
import com.proyectoescolar.modelo.Coordinador;
import com.proyectoescolar.modelo.EstadoProyecto;
import com.proyectoescolar.modelo.Estudiante;
import com.proyectoescolar.modelo.Proyecto;
import com.proyectoescolar.utilidades.Utilidades;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
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
    private JComboBox<String> cmbFiltroEstado;
    private JButton btnFiltrar;
    private JTextField txtBusquedaInstitucion;
    private JButton btnBuscar;
    
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
        
        panelBotones.add(btnCambiarEstado);
        panelBotones.add(btnVerDetalles);
        
        panelLista.add(new JLabel("Seleccione un proyecto:"), BorderLayout.NORTH);
        panelLista.add(scrollProyectos, BorderLayout.CENTER);
        panelLista.add(panelBotones, BorderLayout.SOUTH);
        
        // Panel de detalles
        JPanel panelDetalles = new JPanel(new BorderLayout(5, 5));
        panelDetalles.setBorder(BorderFactory.createTitledBorder("Detalles del Proyecto"));
        
        txtDetallesProyecto = new JTextArea();
        txtDetallesProyecto.setEditable(false);
        
        JScrollPane scrollDetalles = new JScrollPane(txtDetallesProyecto);
        scrollDetalles.setPreferredSize(new Dimension(300, 400));
        
        panelDetalles.add(scrollDetalles, BorderLayout.CENTER);
        
        panelListaDetalles.add(panelLista);
        panelListaDetalles.add(panelDetalles);
        
        panelGestionProyectos.add(panelFiltros, BorderLayout.NORTH);
        panelGestionProyectos.add(panelListaDetalles, BorderLayout.CENTER);
        
        // Agregar pestañas
        tabbedPane.addTab("Gestión de Proyectos", panelGestionProyectos);
        
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
}
