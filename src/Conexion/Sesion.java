package Conexion;

//Clase que guarda el usuario con la sesión activa
public class Sesion {
    private static String usuarioActual;
    private static String tipoUsuario;
    
    public static void iniciarSesion(String usuario, String tipo){
        usuarioActual = usuario;
        tipoUsuario = tipo;
    }
    
    public static String getUsuario(){return usuarioActual;}
    
    public static String getTipoUsuario(){return tipoUsuario;}
    
    public static void cerrarSesion(){
        usuarioActual = null;
        tipoUsuario = null;
    }
}