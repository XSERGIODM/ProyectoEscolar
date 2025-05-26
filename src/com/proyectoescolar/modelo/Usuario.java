package com.proyectoescolar.modelo;

/**
 * Clase abstracta que representa un usuario genérico del sistema.
 * Implementa los atributos y métodos comunes a todos los tipos de usuarios.
 */
public abstract class Usuario {
    // Atributos
    protected String id;
    protected String nombre;
    protected String apellido;
    protected String correo;
    protected String telefono;
    protected String nombreUsuario;
    protected String contrasena;
    
    /**
     * Constructor de la clase Usuario
     * @param id Identificador único del usuario
     * @param nombre Nombre del usuario
     * @param apellido Apellido del usuario
     * @param correo Correo electrónico del usuario
     * @param telefono Número de teléfono del usuario
     * @param nombreUsuario Nombre de usuario para iniciar sesión
     * @param contrasena Contraseña para iniciar sesión
     */
    public Usuario(String id, String nombre, String apellido, String correo, 
                  String telefono, String nombreUsuario, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.telefono = telefono;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }
    
    /**
     * Método abstracto que define el rol del usuario
     * @return String con el nombre del rol
     */
    public abstract String getRol();
    
    /**
     * Método para validar las credenciales del usuario
     * @param usuario Nombre de usuario
     * @param contrasena Contraseña
     * @return true si las credenciales son válidas, false en caso contrario
     */
    public boolean validarCredenciales(String usuario, String contrasena) {
        return this.nombreUsuario.equals(usuario) && this.contrasena.equals(contrasena);
    }
    
    // Getters y setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
    
    /**
     * Método que devuelve el nombre completo del usuario
     * @return Nombre completo (nombre + apellido)
     */
    public String getNombreCompleto() {
        return this.nombre + " " + this.apellido;
    }
    
    @Override
    public String toString() {
        return getNombreCompleto() + " (" + getRol() + ")";
    }
}
