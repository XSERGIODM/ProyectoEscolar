package com.proyectoescolar.modelo;

import java.util.ArrayList;
import java.util.List;

/**
 * Clase que representa un docente en el sistema.
 * Hereda de la clase Usuario.
 */
public class Docente extends Usuario {
    
    private String institucion;
    private String especialidad;
    private List<Proyecto> proyectos;
    
    /**
     * Constructor de la clase Docente
     * @param id Identificador único del docente
     * @param nombre Nombre del docente
     * @param apellido Apellido del docente
     * @param correo Correo electrónico del docente
     * @param telefono Número de teléfono del docente
     * @param nombreUsuario Nombre de usuario para iniciar sesión
     * @param contrasena Contraseña para iniciar sesión
     * @param institucion Institución educativa a la que pertenece
     * @param especialidad Área de especialidad del docente
     */
    public Docente(String id, String nombre, String apellido, String correo, 
                  String telefono, String nombreUsuario, String contrasena,
                  String institucion, String especialidad) {
        super(id, nombre, apellido, correo, telefono, nombreUsuario, contrasena);
        this.institucion = institucion;
        this.especialidad = especialidad;
        this.proyectos = new ArrayList<>();
    }

    @Override
    public String getRol() {
        return "Docente";
    }
    
    /**
     * Método para agregar un proyecto a la lista de proyectos del docente
     * @param proyecto Proyecto a agregar
     */
    public void agregarProyecto(Proyecto proyecto) {
        this.proyectos.add(proyecto);
    }
    
    /**
     * Método para obtener la lista de proyectos del docente
     * @return Lista de proyectos
     */
    public List<Proyecto> getProyectos() {
        return proyectos;
    }
    
    // Getters y setters específicos
    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }
    
    @Override
    public String toString() {
        return super.toString() + " - " + institucion + " - Especialidad: " + especialidad;
    }
}
