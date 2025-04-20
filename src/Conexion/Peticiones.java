package Conexion;

//Importa las librerias necesarias
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import biblioteca.App;
import biblioteca.LibroBusqueda;
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
    
    private static int IdUsuarioActivo;
    
    
    
    //METODOS DE USUARIO
    
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
                PreparedStatement insertarUsuario = conexion.prepareStatement("insert into usuarios values(?,?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);

                //Pasa los valores
                insertarUsuario.setInt(1, 0);
                insertarUsuario.setString(2, nombre);

                //si es usuario admin
                if(usuario.contains("admin:")){
                    //quitar el "admin:" del usuario
                    String[] admin = usuario.split("admin:");
                    insertarUsuario.setString(3, admin[1]);
                    insertarUsuario.setString(7, "administrador");
                }else{
                    insertarUsuario.setString(3, usuario);
                    insertarUsuario.setString(7, "socio");
                }
                
                insertarUsuario.setString(4, contraseña);
                insertarUsuario.setDate(5, java.sql.Date.valueOf(fecha));
                insertarUsuario.setString(6, "C:/xampp/htdocs/Imagenes/Usuario.jpg");

                //Ejecuta los cambios
                insertarUsuario.executeUpdate();
                
                //Obtiene el id del usuario
                ResultSet id = insertarUsuario.getGeneratedKeys();
                id.next();
                int IdUsuario = id.getInt(1);

                //Ventana de confirmación
                new WindowMessage("Usuario Agregado Exitosamente");
                
                //Crea una notificacion.
                PreparedStatement insertarNotificacion = conexion.prepareStatement("insert into notificaciones (IdUsuario, Mensaje, Tipo) values (?,?,?)");
                
                //Pasa los valores
                insertarNotificacion.setInt(1, IdUsuario);
                insertarNotificacion.setString(2, "Termine de configurar su cuenta agregando una foto.");
                insertarNotificacion.setString(3, "Informativa");
                
                insertarNotificacion.executeUpdate();

                //Limpia los campos del formulario            
                biblioteca.LogIn.Formulario.getInstancia().limpiarCampos();

                //Cierra la conexión a la base de datos
                conectar.cerrarConexion();
                
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
                    
                    //Guarda los datos de sesión
                    PreparedStatement busqueda = conexion.prepareStatement("select IdUsuario from usuarios where Usuario='"+usuario+"'");

                    ResultSet resultado = busqueda.executeQuery();

                    //Obtiene el id
                    if (resultado.next()) {
                        IdUsuarioActivo = resultado.getInt("IdUsuario");
                    }
                    
                    //Envia el usuario activo
                    Sesion.iniciarSesion(IdUsuarioActivo); 
                    
                    //Abre la aplicación segun el tipo de usuario que es
                    new App(tipo);
                }
                
                biblioteca.LogIn.Formulario.getInstancia().limpiarCampos();
                biblioteca.LogIn.getInstancia().cerrar();
            }else{ //Si no encuentra el usuario o no coincide la contraseña
                new WindowError("Usuario o contraseña incorrectos");
            }
            
            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
        }catch(Exception error){
            new WindowError("Ocurrió un error. Intente nuevamente");
            System.out.println("Error: "+error);
        }
    }
    
    
        
    //Obtiene los datos de los usuarios
    public static void obtenerDatosUsuario(int IdUsuario){        
        try{
            //Inicia la conexión
            Connection conexion = conectar.conectar();

            PreparedStatement busquedaUsuario = conexion.prepareStatement("select Nombre, Usuario, FechaCreacion, Foto from usuarios where IdUsuario='"+IdUsuario+"'");
            ResultSet consultaUsuario = busquedaUsuario.executeQuery();
            
            String nombre="", usuario="", fechaCreacion="", foto="";

            if(consultaUsuario.next()){
                nombre = consultaUsuario.getString("Nombre");
                usuario = consultaUsuario.getString("Usuario");
                fechaCreacion = consultaUsuario.getString("FechaCreacion");
                foto = consultaUsuario.getString("Foto");
            }
            
            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
        }catch(Exception error){
            new WindowError("Ha ocurrido un error. Intente nuevamente");
            System.out.println("Error: "+error);
        }
    }
    
    
    
    //Obtiene las notificaciones
    public static void obtenerNotificaciones(int IdUsuario){
        try{
            //Inicia la conexión
            Connection conexion = conectar.conectar();

            PreparedStatement busquedaNoti = conexion.prepareStatement("select * from notificaciones where IdUsuario='"+IdUsuario+"'");
            ResultSet consultaNoti = busquedaNoti.executeQuery();
            
            String mensaje="";
            //Recorre todas las notificaciones consultadas
            while(consultaNoti.next()){
                mensaje = consultaNoti.getString("Mensaje");
                System.out.println(mensaje);
            }

            //Cierra la conexion
            conectar.cerrarConexion();
        }catch(Exception error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente");            
        }
    }
    
    
 
    //Actualiza los datos del usuario
    public static void actualizarUsuario(String usuario, String nombre, String contraseña) {
        try {
            Connection conexion = conectar.conectar();
            PreparedStatement pst = conexion.prepareStatement("UPDATE usuarios SET Nombre=?, Contraseña=? WHERE Usuario=?");
            pst.setString(1, nombre);
            pst.setString(2, contraseña);
            pst.setString(3, usuario);

            pst.executeUpdate();
            System.out.println("Datos actualizados");
            } catch (Exception e) {
                System.out.println(e);
            }
    }
    

    
    //Elimina el usuario
    public static void eliminarUsuario(String usuario) {
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            
            //Busca en la base de datos de usuarios
            PreparedStatement busquedaUsuarios = conexion.prepareStatement("select * from usuarios where Usuario='"+usuario+"'");
            ResultSet consultaUsuarios = busquedaUsuarios.executeQuery();
            
            if(consultaUsuarios.next()){
                //Elimina el usuario
                PreparedStatement eliminarUsuario = conexion.prepareStatement("delete from usuarios where Usuario='"+usuario+"'");

                int filasAfectadas = eliminarUsuario.executeUpdate();
                
                if (filasAfectadas > 0) {
                    new WindowMessage("Usuario eliminado correctamente.");
                    new biblioteca.LogIn();
                } else {
                    new WindowError("Usuario referenciado incorrectamente");
                }
            }else{
                new WindowError("Usuario referenciado incorrectamente");
            }
            
            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
        }catch(Exception error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente");
        }
    }    
    
    
    
    //METODOS DE LIBROS

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
            
            int IdAutor;
            
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
            
            int IdGenero;
            
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
            
            int IdEditorial;
            
            //Busca si existe la editorial, sino, lo crea
            if(consultaEditorial.next()){ //Si lo encuentra en la tabla de autores
                IdEditorial = consultaEditorial.getInt("IdEditorial");
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
            libroAdmin.setInt(2, IdUsuarioActivo);
            libroAdmin.setDate(3, java.sql.Date.valueOf(fecha));
            
            //Ejecuta los cambios
            libroAdmin.executeUpdate();            
            
                            
            //Crea una notificacion.
            PreparedStatement insertarNotificacion = conexion.prepareStatement("insert into notificaciones (IdUsuario, Mensaje, Tipo) values (?,?,?)");

            //Pasa los valores
            insertarNotificacion.setInt(1, IdUsuarioActivo);
            insertarNotificacion.setString(2, "Agregaste el libro '"+titulo+"' a la base de datos.");
            insertarNotificacion.setString(3, "Informativa");

            insertarNotificacion.executeUpdate();
            
            //Limpia los campos
            biblioteca.CRUD_Libro.getInstancia().limpiarCampos();
            
            //Agrega la vista del libro Individual
            ObtenerLibroIndividual(idLibro);

            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();

            new WindowMessage("Libro Agregado Exitosamente");
        }catch(Exception error){
            new WindowError("Ocurrió un error. Intente nuevamente");
            System.out.println("Error: "+error);
        }
    }

    
    
    //Obtener los datos de los libros
    public static void ObtenerLibroIndividual(int IdLibro){
        try{
            //Inicia la conexión
            Connection conexion = conectar.conectar();
            
            PreparedStatement busquedaLibro = conexion.prepareStatement("select * from libros where IdLibro='"+IdLibro+"'");
            ResultSet consultaLibro = busquedaLibro.executeQuery();
            
            String ISBN="", Titulo="", IdAutor="", Portada="", Año="",IdEditorial="", IdGenero="", Paginas="", Descripcion="";
            if(consultaLibro.next()){
                ISBN = consultaLibro.getString("ISBN");
                Titulo = consultaLibro.getString("Titulo");
                IdAutor = consultaLibro.getString("IdAutor");
                Portada = consultaLibro.getString("Portada");
                Año = consultaLibro.getString("Año");
                IdEditorial = consultaLibro.getString("IdEditorial");
                IdGenero = consultaLibro.getString("IdGenero");
                Paginas = consultaLibro.getString("Paginas");
                Descripcion = consultaLibro.getString("Descripcion");
            }
            
            String Autor="", Editorial="", Genero="";
            
            PreparedStatement busquedaAutor = conexion.prepareStatement("select Nombre from autores where IdAutor='"+IdAutor+"'");
            PreparedStatement busquedaEditorial = conexion.prepareStatement("select Editorial from editoriales where IdEditorial='"+IdEditorial+"'");
            PreparedStatement busquedaGenero = conexion.prepareStatement("select Genero from generos where IdGenero='"+IdGenero+"'");
            ResultSet consultaAutor = busquedaAutor.executeQuery();
            ResultSet consultaEditorial = busquedaEditorial.executeQuery();
            ResultSet consultaGenero = busquedaGenero.executeQuery();

            if(consultaAutor.next()){
                Autor = consultaAutor.getString("Nombre");
            }
            if(consultaEditorial.next()){
                Editorial = consultaEditorial.getString("Editorial");
            }
            if(consultaGenero.next()){
                Genero = consultaGenero.getString("Genero");
            }
            
            PreparedStatement busquedaIdAdmin = conexion.prepareStatement("select IdAdmin from librosadministradores where IdLibro='"+IdLibro+"'");
            ResultSet consultaIdAdmin = busquedaIdAdmin.executeQuery();
            
            String IdAdmin="";
            if(consultaIdAdmin.next()){
                IdAdmin = consultaIdAdmin.getString("IdAdmin");
            }
            
            PreparedStatement busquedaUsuario = conexion.prepareStatement("select Usuario from usuarios where IdUsuario='"+IdAdmin+"'");
            ResultSet consultaUsuario = busquedaUsuario.executeQuery();
            
            String Usuario="";
            if(consultaUsuario.next()){
                Usuario = consultaUsuario.getString("Usuario");
            }

            App.cambiarVista(new LibroIndividual(ISBN, Titulo, Autor, Portada, Año, Editorial, Genero, Paginas, Descripcion, Usuario), null);            
            
            //Cierra la conexion
            conectar.cerrarConexion();
        }catch(Exception error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente");
        }
    }
    
    
    
    //Obtiene todos los libros y regresa la lista
    public static ArrayList<LibroBusqueda> obtenerLibrosRecientes(){
        ArrayList<LibroBusqueda> libros = new ArrayList<>();
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            
            PreparedStatement busquedaLibros = conexion.prepareStatement("select * from libros");
            ResultSet consultaLibros = busquedaLibros.executeQuery();
            
            while(consultaLibros.next()){
                int id = consultaLibros.getInt("IdLibro"); 
                String titulo = consultaLibros.getString("Titulo"); 
                String IdAutor = consultaLibros.getString("IdAutor"); 
                String portada = consultaLibros.getString("Portada"); 
                String descripcion = consultaLibros.getString("Descripcion"); 
                
                PreparedStatement busquedaAutor = conexion.prepareStatement("select Nombre from autores where IdAutor='"+IdAutor+"'");
                ResultSet consultaAutor = busquedaAutor.executeQuery();
                
                String autor ="";
                if(consultaAutor.next()){
                    autor = consultaAutor.getString("Nombre");
                }
                libros.add(new LibroBusqueda(id, titulo, autor, portada, descripcion));
            }
            
            //Cerrar la conexión
            conectar.cerrarConexion();
        }catch(Exception error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente");
        }
        //Retorna la lista de libros
        return libros;
    }
    
    
    
    //Obtener libro por titulo o autor
    public static ArrayList<LibroBusqueda> busquedaLibro(String texto){
        ArrayList<LibroBusqueda> libros = new ArrayList<>();
        try{
            //Inicia la conexion;
            Connection conexion = conectar.conectar();
            
            PreparedStatement busquedaLibro = conexion.prepareStatement("select l.IdLibro, l.Titulo, a.Nombre AS Autor, l.Descripcion, l.Portada " +
                     "from libros l inner join autores a on l.IdAutor = a.IdAutor where l.Titulo like ? or a.Nombre like ?");
            busquedaLibro.setString(1, "%"+texto+"%");
            busquedaLibro.setString(2, "%"+texto+"%");

            ResultSet consultaLibro = busquedaLibro.executeQuery();
            
            while(consultaLibro.next()){
                int id = consultaLibro.getInt("IdLibro"); 
                String titulo = consultaLibro.getString("Titulo"); 
                String autor = consultaLibro.getString("Autor"); 
                String portada = consultaLibro.getString("Portada"); 
                String descripcion = consultaLibro.getString("Descripcion"); 
                
                libros.add(new LibroBusqueda(id, titulo, autor, portada, descripcion));
            }
            
            //Cerrar la conexion
            conectar.cerrarConexion();
        }catch(Exception error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente");
        }

        //Retorna la lista de libros
        return libros;
    }
}