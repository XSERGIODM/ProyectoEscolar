package com.proyectoescolar.modelo;

/**
 * Enumeración que representa los posibles estados de un proyecto.
 */
public enum EstadoProyecto {
    FORMULACION("Formulación"),
    EVALUACION("Evaluación"),
    ACTIVO("Activo"),
    FINALIZADO("Finalizado"),
    INACTIVO("Inactivo");
    
    private final String nombre;
    
    /**
     * Constructor de la enumeración
     * @param nombre Nombre del estado
     */
    private EstadoProyecto(String nombre) {
        this.nombre = nombre;
    }
    
    /**
     * Método para obtener el nombre del estado
     * @return Nombre del estado
     */
    public String getNombre() {
        return nombre;
    }
    
    @Override
    public String toString() {
        return nombre;
    }
}
