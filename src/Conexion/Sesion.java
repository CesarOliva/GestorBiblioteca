package Conexion;

//Se importan las librerias necesarias
import java.sql.*;
import elementos.WindowError;

public class Sesion {
    private static Conexion conectar = Conexion.getInstance();
    
    private static int idUsuario;
    private static String nombre;
    private static String usuario;
    private static String Contraseña;
    private static String fechaCreacion;
    private static String foto;

    public static void iniciarSesion(int IdUsuario){
        idUsuario = IdUsuario;
        try {
            //Inicia la conexión
            Connection conexion = conectar.conectar();
            
            PreparedStatement busquedaUsuario = conexion.prepareStatement("select Nombre, Usuario, Contraseña, FechaCreacion, Foto from usuarios where IdUsuario='"+idUsuario+"'");
            ResultSet consultaUsuario = busquedaUsuario.executeQuery();

            if (consultaUsuario.next()) {
                nombre = consultaUsuario.getString("Nombre");
                usuario = consultaUsuario.getString("Usuario");
                Contraseña = consultaUsuario.getString("Contraseña");
                fechaCreacion = consultaUsuario.getString("FechaCreacion");
                foto = consultaUsuario.getString("Foto");
            }

            conectar.cerrarConexion();
        } catch (Exception e) {
            new WindowError("Ha ocurrido un error. Intente nuevamente");
            System.out.println("Error al iniciar sesión: " + e);
        }
    }

    public static int getIdUsuario() { return idUsuario; }
    
    public static String getNombre() { return nombre; }
    
    public static String getUsuario() { return usuario; }
    
    public static String getContraseña() { return Contraseña; }
    
    public static String getFechaCreacion() { return fechaCreacion; }
    
    public static String getFoto() { return foto; }

    public static void cerrarSesion(){
        idUsuario = 0;
        nombre = null;
        usuario = null;
        fechaCreacion = null;
        foto = null;
    }
}
