package Conexion;

//Importa las librerias necesarias
import java.sql.*;

//Clase que realiza la conexión de la base de datos
public class Conexion {
    private static Connection conexion;
    private static Conexion instancia;
    
    //Credenciales de la base de datos
    private static final String URL = "jdbc:mysql://localhost/bd_gestorbiblioteca";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    
    //Constructor privado para no modificarlo
    private Conexion(){
        
    }
    
    //Metodo para conectarnos a la base de datos
    public Connection conectar(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            conexion = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Conexión exitosa");
            return conexion;
        }catch(Exception e){
            System.out.println(e);
            return conexion;
        }
    }
    
    //Metodo para cerrar la conexión
    public void cerrarConexion() throws SQLException{
        try{
            conexion.close();
            System.out.println("Desconectado");
        }catch(SQLException e){
            System.out.println("Error: "+e);
            conexion.close();
        }finally{
            conexion.close();
        }
    }
    
    //Crea la instancia
    public static Conexion getInstance(){
        if(instancia == null){
            instancia = new Conexion();
        }
        return instancia;
    }
}