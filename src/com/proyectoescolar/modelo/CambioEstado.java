package com.proyectoescolar.modelo;

import java.util.Date;

/**
 * Clase que representa un cambio de estado en un proyecto.
 * Se utiliza para mantener un historial de cambios de estado.
 */
public class CambioEstado {
    
    private int id;
    private EstadoProyecto estadoAnterior;
    private EstadoProyecto estadoNuevo;
    private Date fecha;
    private String comentario;
    private Coordinador coordinador;
    
    /**
     * Constructor de la clase CambioEstado
     * @param id Identificador único del cambio
     * @param estadoAnterior Estado anterior del proyecto
     * @param estadoNuevo Nuevo estado del proyecto
     * @param fecha Fecha en que se realizó el cambio
     * @param comentario Comentario o justificación del cambio
     * @param coordinador Coordinador que autorizó el cambio
     */
    public CambioEstado(int id, EstadoProyecto estadoAnterior, EstadoProyecto estadoNuevo, 
                        Date fecha, String comentario, Coordinador coordinador) {
        this.id = id;
        this.estadoAnterior = estadoAnterior;
        this.estadoNuevo = estadoNuevo;
        this.fecha = fecha;
        this.comentario = comentario;
        this.coordinador = coordinador;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EstadoProyecto getEstadoAnterior() {
        return estadoAnterior;
    }

    public void setEstadoAnterior(EstadoProyecto estadoAnterior) {
        this.estadoAnterior = estadoAnterior;
    }

    public EstadoProyecto getEstadoNuevo() {
        return estadoNuevo;
    }

    public void setEstadoNuevo(EstadoProyecto estadoNuevo) {
        this.estadoNuevo = estadoNuevo;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public Coordinador getCoordinador() {
        return coordinador;
    }

    public void setCoordinador(Coordinador coordinador) {
        this.coordinador = coordinador;
    }
    
    @Override
    public String toString() {
        return "Cambio de Estado: " + estadoAnterior + " → " + estadoNuevo + 
               " - Fecha: " + fecha + " - Autorizado por: " + coordinador.getNombreCompleto();
    }
}
