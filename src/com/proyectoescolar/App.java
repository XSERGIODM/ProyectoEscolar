package com.proyectoescolar;

import com.proyectoescolar.controlador.ControladorProyectos;
import com.proyectoescolar.controlador.ControladorUsuarios;
import com.proyectoescolar.modelo.Coordinador;
import com.proyectoescolar.modelo.Docente;
import com.proyectoescolar.modelo.EstadoProyecto;
import com.proyectoescolar.modelo.Estudiante;
import com.proyectoescolar.modelo.Proyecto;
import com.proyectoescolar.vista.VentanaLogin;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Clase principal que inicia la aplicación de gestión de proyectos escolares.
 * 
 * @author XSERGIODM - DMind
 */
public class App {

    /**
     * Método principal que inicia la aplicación.
     * 
     * @param args Argumentos de línea de comandos (no utilizados).
     */
    public static void main(String[] args) {
        // Configurar el look and feel para que se vea mejor
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | 
                IllegalAccessException | UnsupportedLookAndFeelException ex) {
            System.err.println("Error al establecer el look and feel: " + ex.getMessage());
        }
        
        // Iniciar la aplicación en el hilo de eventos de Swing
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                iniciarAplicacion();
            }
        });
    }
    
    /**
     * Método para iniciar la aplicación y cargar datos de prueba.
     */
    private static void iniciarAplicacion() {
        // Crear controladores
        ControladorUsuarios controladorUsuarios = new ControladorUsuarios();
        ControladorProyectos controladorProyectos = new ControladorProyectos();
        
        // Cargar datos de prueba para proyectos
        cargarDatosPruebaProyectos(controladorUsuarios, controladorProyectos);
        
        // Crear y mostrar la ventana de login
        VentanaLogin ventanaLogin = new VentanaLogin(controladorUsuarios, controladorProyectos);
        ventanaLogin.setVisible(true);
    }
    
    /**
     * Método para cargar datos de prueba para proyectos.
     * 
     * @param controladorUsuarios Controlador de usuarios.
     * @param controladorProyectos Controlador de proyectos.
     */
    private static void cargarDatosPruebaProyectos(ControladorUsuarios controladorUsuarios, 
                                                 ControladorProyectos controladorProyectos) {
        // Obtener docentes, estudiantes y coordinadores para los datos de prueba
        Docente docente1 = (Docente) controladorUsuarios.buscarUsuarioPorNombreUsuario("pedro");
        Docente docente2 = (Docente) controladorUsuarios.buscarUsuarioPorNombreUsuario("ana");
        
        Estudiante estudiante1 = (Estudiante) controladorUsuarios.buscarUsuarioPorNombreUsuario("carlos");
        Estudiante estudiante2 = (Estudiante) controladorUsuarios.buscarUsuarioPorNombreUsuario("maria");
        Estudiante estudiante3 = (Estudiante) controladorUsuarios.buscarUsuarioPorNombreUsuario("juan");
        
        Coordinador coordinador = (Coordinador) controladorUsuarios.buscarUsuarioPorNombreUsuario("coord");
        
        // Crear proyectos de prueba
        Proyecto proyecto1 = controladorProyectos.crearProyecto(
                "Estudio del impacto ambiental en la comunidad local",
                "Ciencias Ambientales",
                "1. Analizar el impacto ambiental de residuos en la comunidad.\n" +
                "2. Proponer soluciones para mitigar el impacto.\n" +
                "3. Implementar un plan piloto en la institución educativa.",
                "Fase 1: Investigación (4 semanas)\n" +
                "Fase 2: Análisis de datos (3 semanas)\n" +
                "Fase 3: Implementación (6 semanas)",
                1500000.0,
                docente1
        );
        
        Proyecto proyecto2 = controladorProyectos.crearProyecto(
                "Aplicación de energías renovables en entornos escolares",
                "Física Aplicada",
                "1. Investigar las energías renovables aplicables a entornos escolares.\n" +
                "2. Diseñar un prototipo funcional para la generación de energía.\n" +
                "3. Evaluar la eficiencia y viabilidad del prototipo.",
                "Fase 1: Investigación teórica (5 semanas)\n" +
                "Fase 2: Diseño del prototipo (4 semanas)\n" +
                "Fase 3: Construcción y pruebas (8 semanas)",
                2300000.0,
                docente2
        );
        
        // Agregar estudiantes a los proyectos
        controladorProyectos.agregarEstudianteAProyecto(proyecto1, estudiante1);
        controladorProyectos.agregarEstudianteAProyecto(proyecto1, estudiante2);
        
        controladorProyectos.agregarEstudianteAProyecto(proyecto2, estudiante3);
        controladorProyectos.agregarEstudianteAProyecto(proyecto2, estudiante1);
        
        // Registrar avances
        controladorProyectos.registrarAvance(
                proyecto1, 
                "Se realizó la investigación preliminar sobre los residuos en la comunidad.", 
                "Documento de investigación preliminar.pdf"
        );
        
        controladorProyectos.registrarAvance(
                proyecto1, 
                "Se completó la encuesta a 100 miembros de la comunidad sobre hábitos de reciclaje.", 
                "Resultados de encuestas.xlsx"
        );
        
        controladorProyectos.registrarAvance(
                proyecto2, 
                "Se investigaron diferentes tipos de energías renovables aplicables al contexto escolar.", 
                "Informe de investigación.docx"
        );
        
        // Cambiar estados
        controladorProyectos.cambiarEstadoProyecto(proyecto1, EstadoProyecto.EVALUACION, coordinador);
        controladorProyectos.cambiarEstadoProyecto(proyecto1, EstadoProyecto.ACTIVO, coordinador);
        
        controladorProyectos.cambiarEstadoProyecto(proyecto2, EstadoProyecto.EVALUACION, coordinador);
    }
}
