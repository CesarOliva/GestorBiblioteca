package Conexion;

//Importa las librerias necesarias
import java.sql.*;
import java.time.LocalDate;
import biblioteca.App;
import biblioteca.LibroIndividual;

//"Librerias" personalizadas a importar
import elementos.WindowMessage;
import elementos.WindowError;

//Clase que realiza las peticiones a la base de datos
public class Peticiones {
    //Conexion a la base de datos
    private static Conexion conectar = Conexion.getInstance();
    //Fecha actual
    private static LocalDate fecha = LocalDate.now();

    private static int IdAutor, IdGenero;

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
                    PreparedStatement insertar = conexion.prepareStatement("insert into administradores values(?,?,?,?,?,?)"); //Los seis atributos de la tabla

                    //Pasa los valores
                    insertar.setString(1, "0");
                    insertar.setString(2, nombre);
                    insertar.setString(3, admin[1]);
                    insertar.setString(4, contraseña);
                    insertar.setDate(5, java.sql.Date.valueOf(fecha));
                    insertar.setString(6, "C:/xampp/htdocs/Imagenes/Usuario.jpg");

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
                    PreparedStatement insertar = conexion.prepareStatement("insert into socios values(?,?,?,?,?,?)"); //Los seis atributos de la tabla

                    //Pasa los valores
                    insertar.setString(1, "0");
                    insertar.setString(2, nombre);
                    insertar.setString(3, usuario);
                    insertar.setString(4, contraseña);
                    insertar.setDate(5, java.sql.Date.valueOf(fecha));                    
                    insertar.setString(6, "C:/xampp/htdocs/Imagenes/Usuario.jpg");

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

            //Busca si ya existe el autor y el genero
            PreparedStatement buscarAutor = conexion.prepareStatement("select IdAutor FROM autores WHERE Nombre= ?");
            PreparedStatement buscarGenero = conexion.prepareStatement("select IdGenero FROM generos WHERE Genero= ?");
            buscarAutor.setString(1, autor);
            buscarGenero.setString(1, genero);
            
            ResultSet consultaAutor = buscarAutor.executeQuery();
            ResultSet consultaGenero = buscarGenero.executeQuery();
            
            //Busca si existe el autor, sino, lo crea
            if(consultaAutor.next()){ //Si lo encuentra en la tabla de autores
                IdAutor = consultaAutor.getInt("IdAutor");
            }else{ //Si no existe en la tabla
                PreparedStatement insertarAutor = conexion.prepareStatement("insert into autores values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS); //Crea un registro del autor
                
                //Pasa los valores
                insertarAutor.setString(1, "0");
                insertarAutor.setString(2, autor);
                insertarAutor.setString(3, "Sobre el autor");
                insertarAutor.setString(4, "C:/xampp/htdocs/Imagenes/Usuario.jpg");
                
                insertarAutor.executeUpdate();
                
                //Obtiene el id del autor
                ResultSet id = insertarAutor.getGeneratedKeys();
                id.next();
                IdAutor = id.getInt(1);
            }
            
            //Busca si existe el genero, sino, lo crea
            if(consultaGenero.next()){ //Si lo encuentra en la tabla de generos
                IdGenero = consultaGenero.getInt("IdGenero");
            }else{ //Si no existe en la tabla
                PreparedStatement insertarGenero = conexion.prepareStatement("insert into generos values (?,?)", Statement.RETURN_GENERATED_KEYS); //Crea un registro del genero
                
                //Pasa los valores
                insertarGenero.setString(1, "0");
                insertarGenero.setString(2, genero);
                
                insertarGenero.executeUpdate();
                
                //Obtiene el id del genero
                ResultSet id = insertarGenero.getGeneratedKeys();
                id.next();
                IdGenero = id.getInt(1);
            }
            
            //Inserta elementos a la base de datos de libros
            PreparedStatement insertar = conexion.prepareStatement("insert into libros values (?,?,?,?,?,?,?,?,?,?)");
            
            //Pasa los valores
            insertar.setString(1, "0");
            insertar.setString(2, isbn);
            insertar.setString(3, titulo);
            insertar.setInt(4, IdAutor);
            insertar.setString(5, portada);
            insertar.setInt(6, año);
            insertar.setString(7, editorial);
            insertar.setInt(8, IdGenero);
            insertar.setInt(9, paginas);
            insertar.setString(10, descripcion);

            //Ejecuta los cambios
            insertar.executeUpdate();

            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
            
            //Limpia los campos
            biblioteca.CRUD_Libro.getInstancia().limpiarCampos();

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