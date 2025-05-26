package com.proyectoescolar.modelo;

import java.util.Date;

/**
 * Clase que representa un avance o hito de un proyecto.
 */
public class Avance {
    
    private int id;
    private String descripcion;
    private Date fecha;
    private String evidencia;
    private Proyecto proyecto;
    
    /**
     * Constructor de la clase Avance
     * @param id Identificador único del avance
     * @param descripcion Descripción del avance
     * @param fecha Fecha en que se registra el avance
     * @param evidencia Evidencia del avance (puede ser una URL, ruta de archivo, etc.)
     * @param proyecto Proyecto al que pertenece el avance
     */
    public Avance(int id, String descripcion, Date fecha, String evidencia, Proyecto proyecto) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
        this.evidencia = evidencia;
        this.proyecto = proyecto;
    }

    // Getters y setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(String evidencia) {
        this.evidencia = evidencia;
    }

    public Proyecto getProyecto() {
        return proyecto;
    }

    public void setProyecto(Proyecto proyecto) {
        this.proyecto = proyecto;
    }
    
    @Override
    public String toString() {
        return "Avance [" + id + "] - " + fecha + ": " + descripcion;
    }
}
