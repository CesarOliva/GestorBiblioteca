package Conexion;

//Importa las librerias necesarias
import java.sql.*;
import biblioteca.App;
import biblioteca.LibroIndividual;

//"Librerias" personalizadas a importar
import elementos.WindowMessage;
import elementos.WindowError;

//Clase que realiza las peticiones a la base de datos
public class Peticiones {
    //Conexion a la base de datos
    private static Conexion conectar = Conexion.getInstance();
    
    //Agregar usuario
    public static boolean agregarUsuario(String nombre, String usuario, String contraseña){
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            
            //Busca si existe ya el usuario
            PreparedStatement busquedaSocios = conexion.prepareStatement("select * from socios where Usuario='"+usuario+"'");
            PreparedStatement busquedaAdmins = conexion.prepareStatement("select * from administradores where Usuario='"+usuario+"'");
            ResultSet consultaSocios = busquedaSocios.executeQuery();
            ResultSet consultaAdmins = busquedaAdmins.executeQuery();
            
            //Si encuentra usuario
            if(consultaSocios.next() || consultaAdmins.next()){
                new WindowError("Usuario ya registrado. Intente con otro");
                return false;
            }else{
                //si es usuario admin
                if(usuario.contains("admin:")){
                    //quitar el "admin:" del usuario
                    String[] admin = usuario.split("admin:");

                    //Insertar elementos a la base de datos de socios}
                    PreparedStatement insertar = conexion.prepareStatement("insert into administradores values(?,?,?,?)"); //Los cuatro atributos de la tabla

                    //Pasa los valores
                    insertar.setString(1, "0");
                    insertar.setString(2, nombre);
                    insertar.setString(3, admin[1]);
                    insertar.setString(4, contraseña);

                    //Ejecuta los cambios
                    insertar.executeUpdate();

                    //Cierra la conexión a la base de datos
                    conectar.cerrarConexion();

                    //Ventana de confirmación
                    new WindowMessage("Administrador Agregado Exitosamente");

                    //Limpia los campos del formulario            
                    biblioteca.LogIn.Formulario.getInstancia().limpiarCampos();

                    //Regresa si fue posible crear usuario o no
                    return true;
                }else{ //Si es socio                     
                    //Insertar elementos a la base de datos de socios
                    PreparedStatement insertar = conexion.prepareStatement("insert into socios values(?,?,?,?)"); //Los cuatro atributos de la tabla

                    //Pasa los valores
                    insertar.setString(1, "0");
                    insertar.setString(2, nombre);
                    insertar.setString(3, usuario);
                    insertar.setString(4, contraseña);

                    //Ejecuta los cambios
                    insertar.executeUpdate();

                    //Cierra la conexión a la base de datos
                    conectar.cerrarConexion();

                    //Ventana de confirmación
                    new WindowMessage("Socio Agregado Exitosamente");

                    //Limpia los campos del formulario            
                    biblioteca.LogIn.Formulario.getInstancia().limpiarCampos();

                    //Regresa si fue posible crear usuario o no
                    return true;
                }
            }
        }catch(Exception ex){
            System.out.println("Error: "+ex);
            //Regresa si fue posible crear usuario o no
            return false;
        }
    }
    
    public static void validarUsuario(String usuario, String contraseña){
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
        
            //Busca si ya existe el usuario
            PreparedStatement busquedaSocios = conexion.prepareStatement("select * from socios where Usuario='"+usuario+"' and Contraseña='"+contraseña+"'");
            PreparedStatement busquedaAdmins = conexion.prepareStatement("select * from administradores where Usuario='"+usuario+"' and Contraseña='"+contraseña+"'");
            ResultSet consultaSocios = busquedaSocios.executeQuery();
            ResultSet consultaAdmins = busquedaAdmins.executeQuery();
            
            //Si existe
            if(consultaSocios.next()){ //Si lo encuentra en la base de datos de socios abre la app siendo socio
                //Guarda los datos de sesión
                Sesion.iniciarSesion(usuario, "socio");

                biblioteca.LogIn.Formulario.getInstancia().limpiarCampos();

                new App("socio");
                biblioteca.LogIn.getInstancia().cerrar();
            }else if(consultaAdmins.next()){ //Si lo encuentra en la base de datos de administradores abre la app siendo admin
                //Guarda los datos de sesión
                Sesion.iniciarSesion(usuario, "admin");
                
                biblioteca.LogIn.Formulario.getInstancia().limpiarCampos();
                
                new App("admin");
                biblioteca.LogIn.getInstancia().cerrar();                
            }else{ //Si no encuentra el usuario o no coincide la contraseña
                new WindowError("Usuario o contraseña incorrectos");
            }
            
            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
        }catch(Exception error){
            System.out.println("Error: "+error);
        }
    }
    
    //Agrega los libros a la base de datos
    public static void agregarLibro(String isbn, String titulo, String autor, String portada, int año, 
        String editorial, String genero, int paginas, String descripcion){
        
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();

            //Inserta elementos a la base de datos de libros
            PreparedStatement insertar = conexion.prepareStatement("insert into libros values (?,?,?,?,?,?,?,?,?,?)");
            
            //Pasa los valores
            insertar.setString(1, "0");
            insertar.setString(2, isbn);
            insertar.setString(3, titulo);
            insertar.setString(4, autor);
            insertar.setString(5, portada);
            insertar.setInt(6, año);
            insertar.setString(7, editorial);
            insertar.setString(8, genero);
            insertar.setInt(9, paginas);
            insertar.setString(10, descripcion);

            //Ejecuta los cambios
            insertar.executeUpdate();

            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();

            new WindowMessage("Libro Agregado Exitosamente");
        }catch(Exception error){
            System.out.println("Error: "+error);
        }
    }
    
    //Mostrar el libro seleccionado
    public void mostrarLibro(String id){
        try{
            //Inicia la conexión
            Connection conexion = conectar.conectar();

            //Busca el libro en la base de datos
            PreparedStatement busquedaLibro = conexion.prepareStatement("select * from libros where IdLibro='"+id+"'");
            
            //new LibroIndividual(isbn, titulo, autor, portada, año, editorial, genero, paginas, descripcion);
        }catch(Exception error){
            System.out.println("Error: "+error);
        }
    }
}