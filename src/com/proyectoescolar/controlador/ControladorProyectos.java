package com.proyectoescolar.controlador;

import com.proyectoescolar.modelo.Avance;
import com.proyectoescolar.modelo.Coordinador;
import com.proyectoescolar.modelo.Docente;
import com.proyectoescolar.modelo.EstadoProyecto;
import com.proyectoescolar.modelo.Estudiante;
import com.proyectoescolar.modelo.Proyecto;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Controlador para la gestión de proyectos en el sistema.
 */
public class ControladorProyectos {
    
    private List<Proyecto> proyectos;
    private int ultimoIdProyecto;
    private int ultimoIdAvance;
    
    /**
     * Constructor del controlador de proyectos
     */
    public ControladorProyectos() {
        this.proyectos = new ArrayList<>();
        this.ultimoIdProyecto = 0;
        this.ultimoIdAvance = 0;
        cargarDatosPrueba();
    }
    
    /**
     * Método para crear un nuevo proyecto
     * 
     * @param titulo Título del proyecto
     * @param area Área del conocimiento
     * @param objetivos Objetivos del proyecto
     * @param cronograma Cronograma del proyecto
     * @param presupuesto Presupuesto del proyecto
     * @param docente Docente responsable del proyecto
     * @return Proyecto creado
     */
    public Proyecto crearProyecto(String titulo, String area, String objetivos,
                                String cronograma, double presupuesto, Docente docente) {
        ultimoIdProyecto++;
        Proyecto proyecto = new Proyecto(ultimoIdProyecto, titulo, area, objetivos,
                                       cronograma, presupuesto, docente);
        proyectos.add(proyecto);
        return proyecto;
    }
    
    /**
     * Método para agregar un estudiante a un proyecto
     * 
     * @param proyecto Proyecto al que se agregará el estudiante
     * @param estudiante Estudiante a agregar
     * @return true si se pudo agregar el estudiante, false en caso contrario
     */
    public boolean agregarEstudianteAProyecto(Proyecto proyecto, Estudiante estudiante) {
        return proyecto.agregarEstudiante(estudiante);
    }
    
    /**
     * Método para registrar un avance en un proyecto
     * 
     * @param proyecto Proyecto al que se agregará el avance
     * @param descripcion Descripción del avance
     * @param evidencia Evidencia del avance
     * @return Avance creado
     */
    public Avance registrarAvance(Proyecto proyecto, String descripcion, String evidencia) {
        ultimoIdAvance++;
        Avance avance = new Avance(ultimoIdAvance, descripcion, new Date(), evidencia, proyecto);
        proyecto.agregarAvance(avance);
        return avance;
    }
    
    /**
     * Método para cambiar el estado de un proyecto
     * 
     * @param proyecto Proyecto al que se cambiará el estado
     * @param nuevoEstado Nuevo estado del proyecto
     * @param coordinador Coordinador que autoriza el cambio
     * @return true si se pudo cambiar el estado, false en caso contrario
     */
    public boolean cambiarEstadoProyecto(Proyecto proyecto, EstadoProyecto nuevoEstado, Coordinador coordinador) {
        return proyecto.cambiarEstado(nuevoEstado, coordinador);
    }
    
    /**
     * Método para obtener todos los proyectos
     * 
     * @return Lista de proyectos
     */
    public List<Proyecto> obtenerProyectos() {
        return new ArrayList<>(proyectos);
    }
    
    /**
     * Método para buscar un proyecto por su ID
     * 
     * @param id ID del proyecto a buscar
     * @return Proyecto encontrado o null si no existe
     */
    public Proyecto buscarProyectoPorId(int id) {
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getId() == id) {
                return proyecto;
            }
        }
        return null;
    }
    
    /**
     * Método para filtrar proyectos por docente
     * 
     * @param docente Docente por el que se filtrarán los proyectos
     * @return Lista de proyectos del docente
     */
    public List<Proyecto> filtrarProyectosPorDocente(Docente docente) {
        List<Proyecto> resultado = new ArrayList<>();
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getDocente().equals(docente)) {
                resultado.add(proyecto);
            }
        }
        return resultado;
    }
    
    /**
     * Método para filtrar proyectos por institución
     * 
     * @param institucion Institución por la que se filtrarán los proyectos
     * @return Lista de proyectos de la institución
     */
    public List<Proyecto> filtrarProyectosPorInstitucion(String institucion) {
        List<Proyecto> resultado = new ArrayList<>();
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getDocente().getInstitucion().equalsIgnoreCase(institucion)) {
                resultado.add(proyecto);
            }
        }
        return resultado;
    }
    
    /**
     * Método para filtrar proyectos por estado
     * 
     * @param estado Estado por el que se filtrarán los proyectos
     * @return Lista de proyectos con el estado especificado
     */
    public List<Proyecto> filtrarProyectosPorEstado(EstadoProyecto estado) {
        List<Proyecto> resultado = new ArrayList<>();
        for (Proyecto proyecto : proyectos) {
            if (proyecto.getEstado() == estado) {
                resultado.add(proyecto);
            }
        }
        return resultado;
    }
    
    /**
     * Método para ordenar los proyectos por título
     * 
     * @return Lista de proyectos ordenados por título
     */
    public List<Proyecto> ordenarProyectosPorTitulo() {
        List<Proyecto> resultado = new ArrayList<>(proyectos);
        
        // Ordenamiento burbuja por título
        for (int i = 0; i < resultado.size() - 1; i++) {
            for (int j = 0; j < resultado.size() - i - 1; j++) {
                if (resultado.get(j).getTitulo().compareToIgnoreCase(resultado.get(j + 1).getTitulo()) > 0) {
                    // Intercambiamos los proyectos
                    Proyecto temp = resultado.get(j);
                    resultado.set(j, resultado.get(j + 1));
                    resultado.set(j + 1, temp);
                }
            }
        }
        
        return resultado;
    }
    
    /**
     * Método para ordenar los proyectos por fecha de creación
     * 
     * @return Lista de proyectos ordenados por fecha de creación
     */
    public List<Proyecto> ordenarProyectosPorFechaCreacion() {
        List<Proyecto> resultado = new ArrayList<>(proyectos);
        
        // Ordenamiento burbuja por fecha de creación
        for (int i = 0; i < resultado.size() - 1; i++) {
            for (int j = 0; j < resultado.size() - i - 1; j++) {
                if (resultado.get(j).getFechaCreacion().after(resultado.get(j + 1).getFechaCreacion())) {
                    // Intercambiamos los proyectos
                    Proyecto temp = resultado.get(j);
                    resultado.set(j, resultado.get(j + 1));
                    resultado.set(j + 1, temp);
                }
            }
        }
        
        return resultado;
    }
    
    /**
     * Método para cargar datos de prueba
     */
    private void cargarDatosPrueba() {
        // Este método será llamado por el constructor para cargar datos de prueba
        // pero necesitamos acceso al controlador de usuarios para obtener docentes
        // Lo dejaremos implementado en la clase App
    }
}
