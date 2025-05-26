package com.proyectoescolar.utilidades;

import javax.swing.JOptionPane;

/**
 * Clase utilitaria con métodos útiles para la aplicación.
 */
public class Utilidades {
    
    /**
     * Método para mostrar un mensaje informativo
     * 
     * @param mensaje Mensaje a mostrar
     * @param titulo Título de la ventana
     */
    public static void mostrarMensaje(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Método para mostrar un mensaje de error
     * 
     * @param mensaje Mensaje a mostrar
     * @param titulo Título de la ventana
     */
    public static void mostrarError(String mensaje, String titulo) {
        JOptionPane.showMessageDialog(null, mensaje, titulo, JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Método para mostrar una ventana de confirmación
     * 
     * @param mensaje Mensaje a mostrar
     * @param titulo Título de la ventana
     * @return true si el usuario confirma, false en caso contrario
     */
    public static boolean confirmar(String mensaje, String titulo) {
        int respuesta = JOptionPane.showConfirmDialog(null, mensaje, titulo, 
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return respuesta == JOptionPane.YES_OPTION;
    }
    
    /**
     * Método para solicitar información al usuario
     * 
     * @param mensaje Mensaje a mostrar
     * @param titulo Título de la ventana
     * @return Texto ingresado por el usuario o null si cancela
     */
    public static String solicitarTexto(String mensaje, String titulo) {
        return JOptionPane.showInputDialog(null, mensaje, titulo, JOptionPane.QUESTION_MESSAGE);
    }
}
