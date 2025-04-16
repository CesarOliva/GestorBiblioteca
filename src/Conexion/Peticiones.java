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

    private static int IdAutor, IdGenero, IdEditorial;

    
    
    //Agregar usuario
    public static boolean agregarUsuario(String nombre, String usuario, String contraseña){
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            
            //Busca si existe ya el usuario
            PreparedStatement busquedaUsers = conexion.prepareStatement("select * from usuarios where Usuario='"+usuario+"'");
            ResultSet consultaUsers = busquedaUsers.executeQuery();
            
            //Si encuentra usuario
            if(consultaUsers.next()){
                new WindowError("Usuario ya registrado. Intente con otro");
                return false;
            }else{
                //Insertar elementos a la base de datos de socios}
                PreparedStatement insertar = conexion.prepareStatement("insert into usuarios (Nombre, Usuario, Contraseña, FechaCreacion, Foto, TipoUsuario) values(?,?,?,?,?,?)");

                //Pasa los valores
                insertar.setString(1, nombre);

                //si es usuario admin
                if(usuario.contains("admin:")){
                    //quitar el "admin:" del usuario
                    String[] admin = usuario.split("admin:");
                    insertar.setString(2, admin[1]);
                    insertar.setString(6, "administrador");
                }else{
                    insertar.setString(2, usuario);
                    insertar.setString(6, "socio");
                }
                
                insertar.setString(3, contraseña);
                insertar.setDate(4, java.sql.Date.valueOf(fecha));
                insertar.setString(5, "C:/xampp/htdocs/Imagenes/Usuario.jpg");

                //Ejecuta los cambios
                insertar.executeUpdate();

                //Cierra la conexión a la base de datos
                conectar.cerrarConexion();

                //Ventana de confirmación
                new WindowMessage("Usuario Agregado Exitosamente");

                //Limpia los campos del formulario            
                biblioteca.LogIn.Formulario.getInstancia().limpiarCampos();

                //Regresa si fue posible crear usuario o no
                return true;
            }
        }catch(Exception ex){
            new WindowError("Ocurrió un error. Intente nuevamente");
            System.out.println("Error: "+ex);
            
            //Regresa si fue posible crear usuario o no
            return false;
        }
    }
    
    
    
    //Valida si el usuario ya existe en la base de datos
    public static void validarUsuario(String usuario, String contraseña){
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
        
            //Busca si ya existe el usuario
            PreparedStatement busquedaUsuarios = conexion.prepareStatement("select * from usuarios where Usuario='"+usuario+"' and Contraseña='"+contraseña+"'");
            ResultSet consultaUsuarios = busquedaUsuarios.executeQuery();
            
            //Si existe
            if(consultaUsuarios.next()){
                PreparedStatement tipoUsuario = conexion.prepareStatement("select tipoUsuario from usuarios where Usuario='"+usuario+"'"); 
                ResultSet consultaTipos = tipoUsuario.executeQuery();
                
                if(consultaTipos.next()){
                    String tipo = consultaTipos.getString("TipoUsuario");
                    System.out.println(tipo);
                    
                    new App(tipo);
                    
                    //Guarda los datos de sesión
                    Sesion.iniciarSesion(usuario); //Envia el usuario
                }
                


                biblioteca.LogIn.Formulario.getInstancia().limpiarCampos();
                new App("socio");
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

            //Busca si ya existe el autor, el genero y la editorial
            PreparedStatement buscarAutor = conexion.prepareStatement("select IdAutor FROM autores WHERE Nombre= ?");
            PreparedStatement buscarGenero = conexion.prepareStatement("select IdGenero FROM generos WHERE Genero= ?");
            PreparedStatement buscarEditorial = conexion.prepareStatement("select IdEditorial FROM editoriales WHERE Editorial= ?");
            buscarAutor.setString(1, autor);
            buscarGenero.setString(1, genero);
            buscarEditorial.setString(1, editorial);
            
            ResultSet consultaAutor = buscarAutor.executeQuery();
            ResultSet consultaGenero = buscarGenero.executeQuery();
            ResultSet consultaEditorial = buscarEditorial.executeQuery();
            
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
            
            //Busca si existe la editorial, sino, lo crea
            if(consultaEditorial.next()){ //Si lo encuentra en la tabla de autores
                IdEditorial = consultaAutor.getInt("IdEditorial");
            }else{ //Si no existe en la tabla
                PreparedStatement insertarEditorial = conexion.prepareStatement("insert into editoriales values (?,?,?,?)", Statement.RETURN_GENERATED_KEYS); //Crea un registro de editorial
                
                //Pasa los valores
                insertarEditorial.setString(1, "0");
                insertarEditorial.setString(2, editorial);
                insertarEditorial.setString(3, "Sobre la editorial");
                insertarEditorial.setString(4, "C:/xampp/htdocs/Imagenes/Editorial.jpg");
                
                insertarEditorial.executeUpdate();
                
                //Obtiene el id del autor
                ResultSet id = insertarEditorial.getGeneratedKeys();
                id.next();
                IdEditorial = id.getInt(1);
            }            
            
            //Inserta elementos a la base de datos de libros
            PreparedStatement insertarLibro = conexion.prepareStatement("insert into libros values (?,?,?,?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            
            //Pasa los valores
            insertarLibro.setString(1, "0");
            insertarLibro.setString(2, isbn);
            insertarLibro.setString(3, titulo);
            insertarLibro.setInt(4, IdAutor);
            insertarLibro.setString(5, portada);
            insertarLibro.setInt(6, año);
            insertarLibro.setInt(7, IdEditorial);
            insertarLibro.setInt(8, IdGenero);
            insertarLibro.setInt(9, paginas);
            insertarLibro.setString(10, descripcion);

            //Ejecuta los cambios
            insertarLibro.executeUpdate();

            //Obtiene el id del libro            
            ResultSet libroGenerado = insertarLibro.getGeneratedKeys();
            int idLibro = 0;
            if (libroGenerado.next()) {
                idLibro = libroGenerado.getInt(1);
            }
            
            //Agrega el libro y el administrador a la tabla de Libros-Administrador
            PreparedStatement libroAdmin = conexion.prepareStatement("insert into librosadministradores values (?,?,?)");
            //Pasa los valores
            libroAdmin.setInt(1, idLibro);
            libroAdmin.setInt(2, Sesion.getIdUsuario());
            libroAdmin.setDate(3, java.sql.Date.valueOf(fecha));
            
            //Ejecuta los cambios
            libroAdmin.executeUpdate();            

            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
            
            //Limpia los campos
            biblioteca.CRUD_Libro.getInstancia().limpiarCampos();

            new WindowMessage("Libro Agregado Exitosamente");
        }catch(Exception error){
            System.out.println("Error: "+error);
        }
    }
    
    //Obtiene los datos de los usuarios
    public void obtenerDatosUsuario(String usuario){
        try{
            //Inicia la conexión a la base de datoss
            Connection conexion = conectar.conectar();
            
            PreparedStatement consultaUsuario = conexion.prepareStatement("select * from ");
            
        }catch(Exception error){
            System.out.println("Error: "+error);
        }
    }
    
    //Elimina el usuario
    public void eliminarUsuario(String Usuario) {
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            
            //Busca en la base de datos de socios y de administradores
            PreparedStatement busquedaSocios = conexion.prepareStatement("select * from socios where Usuario='"+Usuario+"'");
            PreparedStatement busquedaAdmins = conexion.prepareStatement("select * from administradores where Usuario='"+Usuario+"'");
            ResultSet consultaSocios = busquedaSocios.executeQuery();
            ResultSet consultaAdmins = busquedaAdmins.executeQuery();
            
            //Si encuentra en la base de socios
            if(consultaSocios.next()){
                //Elimina el socio
                PreparedStatement EliminarSocio = conexion.prepareStatement("delete from socios where Usuario='" + Usuario + "'");

                int filasAfectadas = EliminarSocio.executeUpdate();
                if (filasAfectadas > 0) {
                    new WindowMessage("Usuario eliminado correctamente.");
                    new biblioteca.LogIn();
                } else {
                    System.out.println("No se encontró ningún registro con ese ID.");
                }
            }else if(consultaAdmins.next()){
                PreparedStatement EliminarAdmin = conexion.prepareStatement("delete from admin where Usuario='" + Usuario + "'");

                int filasAfectadas = EliminarAdmin.executeUpdate();

                if (filasAfectadas > 0) {
                    new WindowMessage("Usuario eliminado correctamente.");
                    new biblioteca.LogIn();
                } else {
                    System.out.println("No se encontró ningún registro con ese ID.");
                }
            }else{
                System.out.println("No se encontró el usuario");
            }
            
            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
            
        }catch(Exception error){
            System.out.println("Error: "+error);
        }
    }
}