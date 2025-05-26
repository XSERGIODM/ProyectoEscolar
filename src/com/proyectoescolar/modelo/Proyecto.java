package com.proyectoescolar.modelo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Clase que representa un proyecto escolar en el sistema.
 */
public class Proyecto {
    
    private int id;
    private String titulo;
    private String area;
    private String objetivos;
    private String cronograma;
    private double presupuesto;
    private Date fechaCreacion;
    private EstadoProyecto estado;
    private Docente docente;
    private List<Estudiante> estudiantes;
    private List<Avance> avances;
    private List<CambioEstado> historialEstados;
    
    /**
     * Constructor de la clase Proyecto
     * @param id Identificador único del proyecto
     * @param titulo Título del proyecto
     * @param area Área del conocimiento a la que pertenece el proyecto
     * @param objetivos Objetivos del proyecto
     * @param cronograma Cronograma del proyecto
     * @param presupuesto Presupuesto del proyecto
     * @param docente Docente responsable del proyecto
     */
    public Proyecto(int id, String titulo, String area, String objetivos, 
                   String cronograma, double presupuesto, Docente docente) {
        this.id = id;
        this.titulo = titulo;
        this.area = area;
        this.objetivos = objetivos;
        this.cronograma = cronograma;
        this.presupuesto = presupuesto;
        this.fechaCreacion = new Date();
        this.estado = EstadoProyecto.FORMULACION; // Estado inicial
        this.docente = docente;
        this.estudiantes = new ArrayList<>();
        this.avances = new ArrayList<>();
        this.historialEstados = new ArrayList<>();
        
        // Agregamos este proyecto a la lista de proyectos del docente
        docente.agregarProyecto(this);
    }
    
    /**
     * Método para agregar un estudiante al proyecto
     * @param estudiante Estudiante a agregar
     * @return true si se pudo agregar el estudiante, false en caso contrario
     */
    public boolean agregarEstudiante(Estudiante estudiante) {
        // Verificamos que no se supere el máximo de 10 estudiantes
        if (estudiantes.size() < 10) {
            estudiantes.add(estudiante);
            return true;
        }
        return false;
    }
    
    /**
     * Método para agregar un avance al proyecto
     * @param avance Avance a agregar
     */
    public void agregarAvance(Avance avance) {
        avances.add(avance);
    }
    
    /**
     * Método para cambiar el estado del proyecto
     * Solo puede ser invocado por un Coordinador
     * @param nuevoEstado Nuevo estado del proyecto
     * @param coordinador Coordinador que autoriza el cambio
     * @return true si se pudo cambiar el estado, false en caso contrario
     */
    public boolean cambiarEstado(EstadoProyecto nuevoEstado, Coordinador coordinador) {
        if (coordinador != null) {
            EstadoProyecto estadoAnterior = this.estado;
            this.estado = nuevoEstado;
            
            // Registramos el cambio de estado en el historial
            CambioEstado cambio = new CambioEstado(
                historialEstados.size() + 1,
                estadoAnterior,
                nuevoEstado,
                new Date(),
                "Cambio de estado autorizado por el coordinador",
                coordinador
            );
            
            historialEstados.add(cambio);
            return true;
        }
        return false;
    }
    
    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getObjetivos() {
        return objetivos;
    }

    public void setObjetivos(String objetivos) {
        this.objetivos = objetivos;
    }

    public String getCronograma() {
        return cronograma;
    }

    public void setCronograma(String cronograma) {
        this.cronograma = cronograma;
    }

    public double getPresupuesto() {
        return presupuesto;
    }

    public void setPresupuesto(double presupuesto) {
        this.presupuesto = presupuesto;
    }

    public Date getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public EstadoProyecto getEstado() {
        return estado;
    }

    public Docente getDocente() {
        return docente;
    }

    public void setDocente(Docente docente) {
        this.docente = docente;
    }

    public List<Estudiante> getEstudiantes() {
        return estudiantes;
    }

    public List<Avance> getAvances() {
        return avances;
    }

    public List<CambioEstado> getHistorialEstados() {
        return historialEstados;
    }
    
    @Override
    public String toString() {
        return "Proyecto [" + id + "] - " + titulo + " - " + area + 
               " - Estado: " + estado + " - Docente: " + docente.getNombreCompleto();
    }
}
