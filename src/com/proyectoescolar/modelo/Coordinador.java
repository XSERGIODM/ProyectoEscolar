package com.proyectoescolar.modelo;



/**
 * Clase que representa un coordinador en el sistema.
 * Hereda de la clase Usuario.
 */
public class Coordinador extends Usuario {
    
    private String departamento;
    
    /**
     * Constructor de la clase Coordinador
     * @param id Identificador único del coordinador
     * @param nombre Nombre del coordinador
     * @param apellido Apellido del coordinador
     * @param correo Correo electrónico del coordinador
     * @param telefono Número de teléfono del coordinador
     * @param nombreUsuario Nombre de usuario para iniciar sesión
     * @param contrasena Contraseña para iniciar sesión
     * @param departamento Departamento al que pertenece el coordinador
     */
    public Coordinador(String id, String nombre, String apellido, String correo, 
                      String telefono, String nombreUsuario, String contrasena,
                      String departamento) {
        super(id, nombre, apellido, correo, telefono, nombreUsuario, contrasena);
        this.departamento = departamento;
    }

    @Override
    public String getRol() {
        return "Coordinador";
    }
    
    /**
     * Método para cambiar el estado de un proyecto
     * @param proyecto Proyecto al que se le cambiará el estado
     * @param nuevoEstado Nuevo estado del proyecto
     * @return true si se pudo cambiar el estado, false en caso contrario
     */
    public boolean cambiarEstadoProyecto(Proyecto proyecto, EstadoProyecto nuevoEstado) {
        return proyecto.cambiarEstado(nuevoEstado, this);
    }
    
    // Getters y setters específicos
    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }
    
    @Override
    public String toString() {
        return super.toString() + " - Departamento: " + departamento;
    }
}
