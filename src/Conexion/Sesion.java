package Conexion;

//Se importan las librerias necesarias.
import java.sql.*;

//Clase que guarda el usuario con la sesión activa
public class Sesion {
    //Conexion a la base de datos
    private static Conexion conectar = Conexion.getInstance();
    
    private static String usuarioActual;
    
    public static void iniciarSesion(String usuario){
        usuarioActual = usuario;
    }
    
    public static String getUsuario(){return usuarioActual;}
    
    public static int getIdUsuario(){
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            PreparedStatement busqueda = conexion.prepareStatement("select IdUsuario from usuarios where Usuario='"+usuarioActual+"'");
            
            ResultSet resultado = busqueda.executeQuery();
            
            //Obtiene el id
            if (resultado.next()) {
                return resultado.getInt("IdUsuario");
            } else {
                return 0;
            }
            
        }catch(Exception error){
            System.out.println("Error: "+error);
            return 0;
        }
    }
    
    public static void cerrarSesion(){
        usuarioActual = null;
    }
}