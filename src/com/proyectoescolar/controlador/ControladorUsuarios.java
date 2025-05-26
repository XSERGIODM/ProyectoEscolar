package com.proyectoescolar.controlador;

import com.proyectoescolar.modelo.Coordinador;
import com.proyectoescolar.modelo.Docente;
import com.proyectoescolar.modelo.Estudiante;
import com.proyectoescolar.modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

/**
 * Controlador para la gestión de usuarios en el sistema.
 */
public class ControladorUsuarios {
    
    private List<Usuario> usuarios;
    private Usuario usuarioActual;
    
    /**
     * Constructor del controlador de usuarios
     */
    public ControladorUsuarios() {
        this.usuarios = new ArrayList<>();
        this.usuarioActual = null;
        cargarDatosPrueba();
    }
    
    /**
     * Método para registrar un nuevo estudiante en el sistema
     * 
     * @param id Identificador único del estudiante
     * @param nombre Nombre del estudiante
     * @param apellido Apellido del estudiante
     * @param correo Correo electrónico del estudiante
     * @param telefono Número de teléfono del estudiante
     * @param nombreUsuario Nombre de usuario para iniciar sesión
     * @param contrasena Contraseña para iniciar sesión
     * @param institucion Institución educativa a la que pertenece
     * @param grado Grado que cursa el estudiante
     * @return Estudiante creado o null si ya existe un usuario con el mismo nombreUsuario
     */
    public Estudiante registrarEstudiante(String id, String nombre, String apellido, 
                                         String correo, String telefono, 
                                         String nombreUsuario, String contrasena,
                                         String institucion, String grado) {
        // Verificamos que no exista un usuario con el mismo nombreUsuario
        if (buscarUsuarioPorNombreUsuario(nombreUsuario) != null) {
            return null;
        }
        
        Estudiante estudiante = new Estudiante(id, nombre, apellido, correo, telefono,
                                             nombreUsuario, contrasena, institucion, grado);
        usuarios.add(estudiante);
        return estudiante;
    }
    
    /**
     * Método para registrar un nuevo docente en el sistema
     * 
     * @param id Identificador único del docente
     * @param nombre Nombre del docente
     * @param apellido Apellido del docente
     * @param correo Correo electrónico del docente
     * @param telefono Número de teléfono del docente
     * @param nombreUsuario Nombre de usuario para iniciar sesión
     * @param contrasena Contraseña para iniciar sesión
     * @param institucion Institución educativa a la que pertenece
     * @param especialidad Área de especialidad del docente
     * @return Docente creado o null si ya existe un usuario con el mismo nombreUsuario
     */
    public Docente registrarDocente(String id, String nombre, String apellido,
                                   String correo, String telefono,
                                   String nombreUsuario, String contrasena,
                                   String institucion, String especialidad) {
        // Verificamos que no exista un usuario con el mismo nombreUsuario
        if (buscarUsuarioPorNombreUsuario(nombreUsuario) != null) {
            return null;
        }
        
        Docente docente = new Docente(id, nombre, apellido, correo, telefono,
                                     nombreUsuario, contrasena, institucion, especialidad);
        usuarios.add(docente);
        return docente;
    }
    
    /**
     * Método para registrar un nuevo coordinador en el sistema
     * 
     * @param id Identificador único del coordinador
     * @param nombre Nombre del coordinador
     * @param apellido Apellido del coordinador
     * @param correo Correo electrónico del coordinador
     * @param telefono Número de teléfono del coordinador
     * @param nombreUsuario Nombre de usuario para iniciar sesión
     * @param contrasena Contraseña para iniciar sesión
     * @param departamento Departamento al que pertenece el coordinador
     * @return Coordinador creado o null si ya existe un usuario con el mismo nombreUsuario
     */
    public Coordinador registrarCoordinador(String id, String nombre, String apellido,
                                           String correo, String telefono,
                                           String nombreUsuario, String contrasena,
                                           String departamento) {
        // Verificamos que no exista un usuario con el mismo nombreUsuario
        if (buscarUsuarioPorNombreUsuario(nombreUsuario) != null) {
            return null;
        }
        
        Coordinador coordinador = new Coordinador(id, nombre, apellido, correo, telefono,
                                                 nombreUsuario, contrasena, departamento);
        usuarios.add(coordinador);
        return coordinador;
    }
    
    /**
     * Método para iniciar sesión en el sistema
     * 
     * @param nombreUsuario Nombre de usuario
     * @param contrasena Contraseña
     * @return Usuario autenticado o null si las credenciales son inválidas
     */
    public Usuario iniciarSesion(String nombreUsuario, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.validarCredenciales(nombreUsuario, contrasena)) {
                this.usuarioActual = usuario;
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Método para cerrar la sesión actual
     */
    public void cerrarSesion() {
        this.usuarioActual = null;
    }
    
    /**
     * Método para buscar un usuario por su nombre de usuario
     * 
     * @param nombreUsuario Nombre de usuario a buscar
     * @return Usuario encontrado o null si no existe
     */
    public Usuario buscarUsuarioPorNombreUsuario(String nombreUsuario) {
        for (Usuario usuario : usuarios) {
            if (usuario.getNombreUsuario().equals(nombreUsuario)) {
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Método para buscar un usuario por su ID
     * 
     * @param id ID del usuario a buscar
     * @return Usuario encontrado o null si no existe
     */
    public Usuario buscarUsuarioPorId(String id) {
        for (Usuario usuario : usuarios) {
            if (usuario.getId().equals(id)) {
                return usuario;
            }
        }
        return null;
    }
    
    /**
     * Método para obtener todos los estudiantes registrados
     * 
     * @return Lista de estudiantes
     */
    public List<Estudiante> obtenerEstudiantes() {
        List<Estudiante> estudiantes = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Estudiante) {
                estudiantes.add((Estudiante) usuario);
            }
        }
        return estudiantes;
    }
    
    /**
     * Método para obtener todos los docentes registrados
     * 
     * @return Lista de docentes
     */
    public List<Docente> obtenerDocentes() {
        List<Docente> docentes = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Docente) {
                docentes.add((Docente) usuario);
            }
        }
        return docentes;
    }
    
    /**
     * Método para obtener todos los coordinadores registrados
     * 
     * @return Lista de coordinadores
     */
    public List<Coordinador> obtenerCoordinadores() {
        List<Coordinador> coordinadores = new ArrayList<>();
        for (Usuario usuario : usuarios) {
            if (usuario instanceof Coordinador) {
                coordinadores.add((Coordinador) usuario);
            }
        }
        return coordinadores;
    }
    
    /**
     * Método para obtener el usuario actualmente autenticado
     * 
     * @return Usuario actual
     */
    public Usuario getUsuarioActual() {
        return usuarioActual;
    }
    
    /**
     * Método para cargar datos de prueba
     */
    private void cargarDatosPrueba() {
        // Registramos algunos estudiantes de prueba
        registrarEstudiante("E001", "Carlos", "Gómez", "carlos@gmail.com", "3101234567", 
                           "carlos", "123", "Colegio San José", "11");
        registrarEstudiante("E002", "María", "López", "maria@gmail.com", "3157654321", 
                           "maria", "123", "Colegio Santa María", "10");
        registrarEstudiante("E003", "Juan", "Pérez", "juan@gmail.com", "3209876543", 
                           "juan", "123", "Colegio San José", "11");
        
        // Registramos algunos docentes de prueba
        registrarDocente("D001", "Pedro", "Rodríguez", "pedro@gmail.com", "3112345678", 
                        "pedro", "123", "Colegio San José", "Física");
        registrarDocente("D002", "Ana", "Martínez", "ana@gmail.com", "3167654321", 
                        "ana", "123", "Colegio Santa María", "Biología");
        
        // Registramos un coordinador de prueba
        registrarCoordinador("C001", "Luis", "Sánchez", "luis@gmail.com", "3123456789", 
                            "coord", "123", "Investigación y Desarrollo");
    }
}
