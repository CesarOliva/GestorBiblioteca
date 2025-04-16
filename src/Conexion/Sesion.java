package Conexion;

//Se importan las librerias necesarias.
import java.sql.*;
import elementos.WindowError;

//Clase que guarda el usuario con la sesión activa
public class Sesion {
    //Conexion a la base de datos
    private static Conexion conectar = Conexion.getInstance();
    
    private static int usuarioActual;
    
    public static void iniciarSesion(int IdUsuario){
        usuarioActual = IdUsuario;
    }
    
    //Obtener los datos del usuario
    public static int getIdUsuario(){ 
        return usuarioActual;
    }
    
    public static String getNombreUsuario(){
        try{
            //Inicializa la conexión
            Connection conexion = conectar.conectar();

            //Busca el Nombre del usuario
            PreparedStatement busquedaNombre = conexion.prepareStatement("select Nombre from usuarios where IdUsuario='"+usuarioActual+"'");
            ResultSet consultaNombre = busquedaNombre.executeQuery();
            
            //Si encuentra el id del usuario
            if(consultaNombre.next()){
                return consultaNombre.getString("Nombre");
            }

            //Cerrar la conexión
            conectar.cerrarConexion();
            
            return "";
        }catch(Exception e){
            new WindowError("Ha ocurrido un error. Intente nuevamente");
            System.out.println("Error: "+e);
            return "";
        }
    }

    public static String getUsuario(){
        try{
            //Inicializa la conexión
            Connection conexion = conectar.conectar();

            //Busca el Usuario
            PreparedStatement busquedaUser = conexion.prepareStatement("select Usuario from usuarios where IdUsuario='"+usuarioActual+"'");
            ResultSet consultaUser = busquedaUser.executeQuery();
            
            //Si encuentra el id del usuario
            if(consultaUser.next()){
                return consultaUser.getString("Usuario");
            }

            //Cerrar la conexión
            conectar.cerrarConexion();
            
            return "";
        }catch(Exception e){
            new WindowError("Ha ocurrido un error. Intente nuevamente");
            System.out.println("Error: "+e);
            return "";
        }
    }
    
    public static void cerrarSesion(){
        usuarioActual = 0;
    }
}