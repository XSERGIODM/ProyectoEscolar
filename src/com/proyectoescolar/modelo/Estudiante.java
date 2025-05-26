package com.proyectoescolar.modelo;

/**
 * Clase que representa un estudiante en el sistema.
 * Hereda de la clase Usuario.
 */
public class Estudiante extends Usuario {
    
    private String institucion;
    private String grado;
    
    /**
     * Constructor de la clase Estudiante
     * @param id Identificador único del estudiante
     * @param nombre Nombre del estudiante
     * @param apellido Apellido del estudiante
     * @param correo Correo electrónico del estudiante
     * @param telefono Número de teléfono del estudiante
     * @param nombreUsuario Nombre de usuario para iniciar sesión
     * @param contrasena Contraseña para iniciar sesión
     * @param institucion Institución educativa a la que pertenece
     * @param grado Grado que cursa el estudiante
     */
    public Estudiante(String id, String nombre, String apellido, String correo, 
                     String telefono, String nombreUsuario, String contrasena,
                     String institucion, String grado) {
        super(id, nombre, apellido, correo, telefono, nombreUsuario, contrasena);
        this.institucion = institucion;
        this.grado = grado;
    }

    @Override
    public String getRol() {
        return "Estudiante";
    }
    
    // Getters y setters específicos
    public String getInstitucion() {
        return institucion;
    }

    public void setInstitucion(String institucion) {
        this.institucion = institucion;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }
    
    @Override
    public String toString() {
        return super.toString() + " - " + institucion + " - Grado: " + grado;
    }
}
