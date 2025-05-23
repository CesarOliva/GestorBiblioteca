package Conexion;

//Importa las librerias necesarias
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.io.*;
import java.nio.file.*;
import biblioteca.*;

//"Librerias" personalizadas a importar
import elementos.WindowMessage;
import elementos.WindowError;
import elementos.ButtonSounds;

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
                ButtonSounds.play("/sounds/Error.wav");                                                
                new WindowError("Usuario ya registrado. Intente con otro");
                return false;
            }else{
                //Insertar elementos a la base de datos de socios}
                PreparedStatement insertarUsuario = conexion.prepareStatement("insert into usuarios values(?,?,?,?,?,?,?)", 
                    Statement.RETURN_GENERATED_KEYS);

                //Pasa los valores
                insertarUsuario.setInt(1, 0);
                insertarUsuario.setString(2, nombre);
                
                File Carpeta = new File("C:/xampp/htdocs/Imagenes");
                //Si no existe la carpeta la crea
                if(!Carpeta.exists()){
                    Carpeta.mkdirs();
                }
                
                //si es usuario admin
                if(usuario.contains("admin:")){
                    //quitar el "admin:" del usuario
                    String[] admin = usuario.split("admin:");
                    insertarUsuario.setString(3, admin[1]);
                    
                    //Ruta donde se guardará la imagen
                    String ruta = "C:/xampp/htdocs/Imagenes/"+admin[1]+".jpg";
                    File destino = new File(ruta);

                    Files.copy(Peticiones.class.getResourceAsStream("/imagenes/Perfil.jpg"), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    
                    insertarUsuario.setString(6, "C:/xampp/htdocs/Imagenes/"+admin[1]+".jpg"); 
                    insertarUsuario.setString(7, "administrador");
                }else{
                    insertarUsuario.setString(3, usuario);

                    //Ruta donde se guardará la imagen
                    String ruta = "C:/xampp/htdocs/Imagenes/"+usuario+".jpg";
                    File destino = new File(ruta);

                    Files.copy(Peticiones.class.getResourceAsStream("/imagenes/Perfil.jpg"), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                                        
                    insertarUsuario.setString(6, "C:/xampp/htdocs/Imagenes/"+usuario+".jpg"); 
                    insertarUsuario.setString(7, "socio");
                }
                
                insertarUsuario.setString(4, contraseña);
                insertarUsuario.setDate(5, java.sql.Date.valueOf(fecha));

                //Ejecuta los cambios
                insertarUsuario.executeUpdate();
                
                //Obtiene el id del usuario
                ResultSet id = insertarUsuario.getGeneratedKeys();
                id.next();
                int IdUsuario = id.getInt(1);

                //Ventana de confirmación
                new WindowMessage("Usuario Agregado Exitosamente");
                
                //Crea una notificacion.
                PreparedStatement insertarNotificacion = conexion.prepareStatement("insert into notificaciones "
                        + "(IdUsuario, Mensaje) values (?,?)");
                
                //Pasa los valores
                insertarNotificacion.setInt(1, IdUsuario);
                insertarNotificacion.setString(2, "Termine de configurar su cuenta agregando una foto.");
                
                insertarNotificacion.executeUpdate();

                //Limpia los campos del formulario            
                biblioteca.LogIn.Formulario.getInstancia().limpiarCampos();

                //Cierra la conexión a la base de datos
                conectar.cerrarConexion();
                
                //Regresa si fue posible crear usuario o no
                return true;
            }
        }catch(IOException | SQLException ex){
            new WindowError("Ocurrió un error. Intente nuevamente.");
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
            PreparedStatement busquedaUsuarios = conexion.prepareStatement("select * from usuarios where "
                + "Usuario='"+usuario+"' and Contraseña='"+contraseña+"'");
            ResultSet consultaUsuarios = busquedaUsuarios.executeQuery();
            
            //Si existe
            if(consultaUsuarios.next()){
                PreparedStatement tipoUsuario = conexion.prepareStatement("select tipoUsuario from usuarios "
                    + "where Usuario='"+usuario+"'"); 
                ResultSet consultaTipos = tipoUsuario.executeQuery();
                
                if(consultaTipos.next()){
                    String tipo = consultaTipos.getString("TipoUsuario");
                    
                    //Guarda los datos de sesión
                    PreparedStatement busqueda = conexion.prepareStatement("select IdUsuario from usuarios where "
                        + "Usuario='"+usuario+"'");

                    ResultSet resultado = busqueda.executeQuery();

                    //Obtiene el id
                    if (resultado.next()) {
                        IdUsuarioActivo = resultado.getInt("IdUsuario");
                    }
                    
                    //Envia el usuario activo
                    Sesion.iniciarSesion(IdUsuarioActivo); 
                    
                    //Abre la aplicación segun el tipo de usuario que es
                    new App(tipo);
                    ButtonSounds.play("/sounds/InicioDeSesion.wav");                
                }
                
                biblioteca.LogIn.Formulario.getInstancia().limpiarCampos();
                biblioteca.LogIn.getInstancia().cerrar();
            }else{ //Si no encuentra el usuario o no coincide la contraseña
                new WindowError("Usuario o contraseña incorrectos");
                ButtonSounds.play("/sounds/Error.wav");                                
            }
            
            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
        }catch(SQLException error){
            new WindowError("Ocurrió un error. Intente nuevamente.");
            System.out.println("Error: "+error);
        }
    }
    
    
        
    //Obtiene las notificaciones
    public static ArrayList<Notificacion> obtenerNotificaciones(int IdUsuario){
        //Crea una lista de notificaciones
        ArrayList<Notificacion> notificaciones = new ArrayList<>();
        
        try{
            //Inicia la conexión
            Connection conexion = conectar.conectar();
            PreparedStatement busquedaNoti = conexion.prepareStatement("select * from notificaciones where "
                + "IdUsuario='"+IdUsuario+"' order by IdNotificacion desc");
            ResultSet consultaNoti = busquedaNoti.executeQuery();
            
            int id=0;
            String mensaje="";
            //Recorre todas las notificaciones consultadas
            while(consultaNoti.next()){
                mensaje = consultaNoti.getString("Mensaje");
                id = consultaNoti.getInt("IdNotificacion");
                
                notificaciones.add(new Notificacion(id, mensaje));
            }

            //Cierra la conexion
            conectar.cerrarConexion();
        }catch(SQLException error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");            
        }
        
        return notificaciones;
    }
    
    
    
    //Elimina notificación
    public static void eliminarNotificacion(int idNotificacion,  Usuario usuario){
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            
            PreparedStatement eliminarNoti = conexion.prepareStatement("delete from notificaciones where "
                + "IdNotificacion='"+idNotificacion+"'");

            int filasAfectadas = eliminarNoti.executeUpdate();

            if (filasAfectadas > 0) {
                usuario.cargarNotificaciones();
            } else {
                new WindowError("Ocurrió un error. Intente nuevamente.");
            }
            
            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
       
        }catch(SQLException error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");
        }
    }
    
    
 
    //Actualiza los datos del usuario
    public static void actualizarUsuario(String usuario, String nombre, String contraseña) {
        try {
            //Inicia la conexión
            Connection conexion = conectar.conectar();
            
            PreparedStatement actualizarUsuario = conexion.prepareStatement("update usuarios set Nombre=?, "
                + "Contraseña=? where Usuario=?");
            
            actualizarUsuario.setString(1, nombre);
            actualizarUsuario.setString(2, contraseña);
            actualizarUsuario.setString(3, usuario);

            actualizarUsuario.executeUpdate();
            
            Sesion.iniciarSesion(IdUsuarioActivo);
            App.cambiarVista(new Usuario(Sesion.getNombre(), Sesion.getUsuario(), Sesion.getContraseña(), 
                Sesion.getFechaCreacion(), Sesion.getFoto()), Menu.inicio);
            
            new WindowMessage("Datos actualizados correctamente");
            
            //Cierra la conexion
            conectar.cerrarConexion();
        } catch (SQLException error) {
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");            
        }
    }
    

    
    //Verificar adeudos
    public static boolean verificarAdeudos(int IdUsuario){
        boolean adeudo = false;
        try {
            //Inicia la conexión
            Connection conexion = conectar.conectar();

            //Verifica que no tenga adeudos
            PreparedStatement verificarAdeudos = conexion.prepareStatement("select * from multas where "
                + "IdSocio='"+Sesion.getIdUsuario()+"' and Pagado=0");
            ResultSet consultaAdeudos = verificarAdeudos.executeQuery();
            
            if(consultaAdeudos.next()){
                adeudo =  true;
                return adeudo;
            }
            
            PreparedStatement consulta = conexion.prepareStatement("select count(*) from prestamos where "
                + "IdSocio='"+IdUsuario+"' and Devuelto=0");
            ResultSet resultado = consulta.executeQuery();
        
            if (resultado.next()) {
                int cantidad = resultado.getInt(1);
                adeudo = cantidad > 0; // Si tiene al menos un préstamo pendiente, hay adeudo
            }
        
            conectar.cerrarConexion();
        }catch(SQLException error){
            new WindowError("Ocurrio un error. Intente nuevamente.");
            System.out.println("Error: "+error);
        }
        return adeudo;
    }    
    
    
    
    //Elimina el usuario
    public static void eliminarUsuario(String usuario) {
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            
            //Elimina las notificaciones 
            PreparedStatement eliminarNotificaciones = conexion.prepareStatement("delete from notificaciones where "
                + "IdUsuario='"+Sesion.getIdUsuario()+"'");
            eliminarNotificaciones.executeUpdate();
            
            //Elimina el usuario
            PreparedStatement eliminarUsuario = conexion.prepareStatement("delete from usuarios where Usuario='"+usuario+"'");

            int filasAfectadas = eliminarUsuario.executeUpdate();

            if (filasAfectadas > 0) {
                //Muestra que eliminó el usuario
                new WindowMessage("Usuario eliminado correctamente.");
                biblioteca.App.getInstancia().cerrar();
                new biblioteca.LogIn();
            } else {
                new WindowError("Ocurrió un error. Intente nuevamente.");
            }
            
            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
        }catch(SQLException error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");
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
                PreparedStatement insertarAutor = conexion.prepareStatement("insert into autores values (?,?,?,?)", 
                    Statement.RETURN_GENERATED_KEYS); //Crea un registro del autor
                
                //Pasa los valores
                insertarAutor.setString(1, "0");
                insertarAutor.setString(2, autor);
                insertarAutor.setString(3, "Sobre el autor");
                
                //Ruta donde se guardará la imagen
                String ruta = "C:/xampp/htdocs/Imagenes/"+autor.replace(" ","")+".jpg";
                File destino = new File(ruta);

                Files.copy(Peticiones.class.getResourceAsStream("/imagenes/Perfil.jpg"), destino.toPath(), 
                    StandardCopyOption.REPLACE_EXISTING);
                
                insertarAutor.setString(4, "C:/xampp/htdocs/Imagenes/"+autor.replace(" ","_")+".jpg");
                
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
                PreparedStatement insertarGenero = conexion.prepareStatement("insert into generos values (?,?)", 
                    Statement.RETURN_GENERATED_KEYS); //Crea un registro del genero
                
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
                PreparedStatement insertarEditorial = conexion.prepareStatement("insert into editoriales values (?,?,?,?)", 
                    Statement.RETURN_GENERATED_KEYS); //Crea un registro de editorial
                
                //Pasa los valores
                insertarEditorial.setString(1, "0");
                insertarEditorial.setString(2, editorial);
                insertarEditorial.setString(3, "Sobre la editorial");
                
                //Ruta donde se guardará la imagen
                String ruta = "C:/xampp/htdocs/Imagenes/"+editorial.replace(" ","_")+".jpg";
                File destino = new File(ruta);

                Files.copy(Peticiones.class.getResourceAsStream("/imagenes/Perfil.jpg"), destino.toPath(), 
                    StandardCopyOption.REPLACE_EXISTING);

                insertarEditorial.setString(4, "C:/xampp/htdocs/Imagenes/"+editorial.replace(" ","")+".jpg");
                
                insertarEditorial.executeUpdate();
                
                //Obtiene el id del autor
                ResultSet id = insertarEditorial.getGeneratedKeys();
                id.next();
                IdEditorial = id.getInt(1);
            }            
            
            //Inserta elementos a la base de datos de libros
            PreparedStatement insertarLibro = conexion.prepareStatement("insert into libros values (?,?,?,?,?,?,?,?,?,?)", 
                Statement.RETURN_GENERATED_KEYS);
            
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
            PreparedStatement insertarNotificacion = conexion.prepareStatement("insert into notificaciones "
                + "(IdUsuario, Mensaje) values (?,?)");

            //Pasa los valores
            insertarNotificacion.setInt(1, IdUsuarioActivo);
            insertarNotificacion.setString(2, "Agregaste el libro '"+titulo+"' a la base de datos.");

            insertarNotificacion.executeUpdate();
            
            //Limpia los campos
            biblioteca.CRUD_Libro.getInstancia().limpiarCampos();
            
            //Agrega la vista del libro Individual
            ObtenerLibroIndividual(idLibro);

            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();

            new WindowMessage("Libro Agregado Exitosamente");
        }catch(IOException | SQLException error){
            new WindowError("Ocurrió un error. Intente nuevamente.");
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

            App.cambiarVista(new LibroIndividual(IdLibro, ISBN, Titulo, Autor, Portada, Año, Editorial, Genero, Paginas, Descripcion, Usuario), null);            
            
            //Cierra la conexion
            conectar.cerrarConexion();
        }catch(SQLException error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");
        }
    }
    
    
    
    //Obtiene todos los libros y regresa la lista
    public static ArrayList<LibroBusqueda> obtenerLibrosRecientes(int limite, int offset){
        ArrayList<LibroBusqueda> libros = new ArrayList<>();
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            
            PreparedStatement busquedaLibros = conexion.prepareStatement("select * from libros order by IdLibro desc limit ? offset ?");
            //El limite de elementos a mostrar en paginasción y la posición de busqueda
            busquedaLibros.setInt(1, limite);
            busquedaLibros.setInt(2, offset);
            
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
        }catch(SQLException error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");
        }
        //Retorna la lista de libros
        return libros;
    }
    
    
    
    //Contar la cantidad de libros en la base de datos
    public static int contarLibros() {
        int total = 0;
        
        try {
            Connection conexion = conectar.conectar();
            
            //Cuenta los elementos de la tabla de libros
            PreparedStatement busquedaLibros = conexion.prepareStatement("select count(*) as total from libros");
            ResultSet consultaLibros = busquedaLibros.executeQuery();
            if (consultaLibros.next()) {
                total = consultaLibros.getInt("total");
            }

            //Cierra la conexión
            conectar.cerrarConexion();
        } catch (SQLException error) {
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");
        }
        return total;
    }
    
    
    
    //Obtener libro por titulo o autor
    public static ArrayList<LibroBusqueda> busquedaLibro(String texto){
        ArrayList<LibroBusqueda> libros = new ArrayList<>();
        try{
            //Inicia la conexion;
            Connection conexion = conectar.conectar();
            
            PreparedStatement busquedaLibro = conexion.prepareStatement("select l.IdLibro, l.Titulo, a.Nombre AS Autor, l.Descripcion, l.Portada " +
                     "from libros l inner join autores a on l.IdAutor = a.IdAutor where l.Titulo like ? or a.Nombre like ? limit 10");
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
        }catch(SQLException error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");
        }

        //Retorna la lista de libros
        return libros;
    }
    
    
    
    //Prestamo de libros
    public static void PrestamoLibro(int IdUsuario, int IdLibro, String titulo){
        try{
            //Inicia la conexión
            Connection conexion = conectar.conectar();
            
            //Verifica cuántos préstamos activos tiene el usuario
            PreparedStatement conteoPrestamos = conexion.prepareStatement("select count(*) from prestamos where IdSocio=? AND Devuelto=0");
            conteoPrestamos.setInt(1, IdUsuario);

            ResultSet resultado = conteoPrestamos.executeQuery();
            
            resultado.next();
            
            int prestamosActivos = resultado.getInt(1);
            if (prestamosActivos >= 3) {
                new WindowError("Límite de 3 prestamos activos alcanzado.");
                conectar.cerrarConexion();
                return;
            }
            
            //Verifica que no tenga adeudos
            PreparedStatement verificarAdeudos = conexion.prepareStatement("select * from multas where IdSocio='"+IdUsuario+"' and Pagado=0");
            ResultSet consultaAdeudos = verificarAdeudos.executeQuery();
            
            if(consultaAdeudos.next()){
                new WindowError("No puedes rentar libros. Tienes multas sin pagar.");
                conectar.cerrarConexion();
                return;
            }
            
            //Si tiene menos de 3 prestamos activos
            PreparedStatement insertarPrestamo = conexion.prepareStatement("insert into prestamos values (?,?,?,?,?,?)", Statement.RETURN_GENERATED_KEYS);
            
            LocalDate vuelta = LocalDate.now().plusDays(7);
            //Pasa los valores
            insertarPrestamo.setString(1, "0");
            insertarPrestamo.setInt(2, IdUsuario);
            insertarPrestamo.setInt(3, IdLibro);
            insertarPrestamo.setDate(4, java.sql.Date.valueOf(fecha));
            insertarPrestamo.setDate(5, java.sql.Date.valueOf(vuelta));
            insertarPrestamo.setBoolean(6, false);

            //Ejecuta los cambios
            insertarPrestamo.executeUpdate();
            
            //Crea una notificacion.
            PreparedStatement insertarNotificacion = conexion.prepareStatement("insert into notificaciones (IdUsuario, Mensaje) values (?,?)");

            //Pasa los valores
            insertarNotificacion.setInt(1, IdUsuario);
            insertarNotificacion.setString(2, "Libro '"+titulo+"' a devolver antes del "+vuelta);

            insertarNotificacion.executeUpdate();
            
            new WindowMessage("Libro '"+titulo+"' a devolver antes del "+vuelta);
            
            biblioteca.LibroIndividual.rentar.setVisible(false);
            biblioteca.LibroIndividual.devolver.setVisible(true);


            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
        }catch(SQLException error){
            new WindowError("Ocurrió un error. Intente nuevamente.");
            System.out.println("Error: "+error);
        }
    }
    
    
    
    //Obtener los prestamos de usuarios
    public static ArrayList<PrestamoBusqueda> obtenerPrestamos(int IdUsuario){
        ArrayList<PrestamoBusqueda> libros = new ArrayList<>();
        try{
            //Inicia la conexión
            Connection conexion = conectar.conectar();
            
            PreparedStatement busquedaPrestamos = conexion.prepareStatement("select * from prestamos where IdSocio='"+IdUsuario+"' order by IdPrestamo desc");
            ResultSet consultaPrestamos = busquedaPrestamos.executeQuery();
            
            while(consultaPrestamos.next()){
                int IdLibro = consultaPrestamos.getInt("IdLibro");
                
                PreparedStatement busquedaLibro = conexion.prepareStatement("select * from libros where IdLibro='"+IdLibro+"'");
                ResultSet consultaLibro = busquedaLibro.executeQuery();
                
                String titulo="", portada="", autor="";
                if(consultaLibro.next()){
                    titulo = consultaLibro.getString("Titulo");
                    portada = consultaLibro.getString("Portada");
                    int IdAutor = consultaLibro.getInt("IdAutor");
                    
                    PreparedStatement busquedaAutor = conexion.prepareStatement("select * from autores where IdAutor='"+IdAutor+"'");
                    ResultSet consultaAutor = busquedaAutor.executeQuery();
                    
                    if(consultaAutor.next()){
                        autor = consultaAutor.getString("Nombre");
                    }
                }
                
                int IdPrestamo = consultaPrestamos.getInt("IdPrestamo");
                
                Date DatePrestamo = consultaPrestamos.getDate("FechaPrestamo");
                LocalDate fechaPrestamo = DatePrestamo != null ? DatePrestamo.toLocalDate() : null;
                
                Date DateVuelta = consultaPrestamos.getDate("FechaVuelta");
                LocalDate fechaDevolucion = DateVuelta != null ? DateVuelta.toLocalDate() : null;
                boolean devuelto = consultaPrestamos.getBoolean("Devuelto");
                
                libros.add(new PrestamoBusqueda(IdLibro, titulo, autor, portada, IdPrestamo, fechaPrestamo, fechaDevolucion, devuelto));
            }
            
            //Cierra la conexion
            conectar.cerrarConexion();
        }catch(SQLException error){
            new WindowError("Ocurrió un error. Intente nuevamente.");
            System.out.println("Error: "+error);
        }
        
        return libros;
    }
    
    
    
    //Devolver libro
    public static void devolverLibro(int IdLibro){
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            
            //Actualizar prestamo
            PreparedStatement actualizarPrestamo = conexion.prepareStatement("update prestamos set Devuelto=1 where IdLibro=?"
                + " and IdSocio=? and Devuelto=0");
            actualizarPrestamo.setInt(1, IdLibro);
            actualizarPrestamo.setInt(2, Sesion.getIdUsuario());
            
            int filasActualizadas = actualizarPrestamo.executeUpdate();

            if(filasActualizadas>0){
                PreparedStatement obtenerPrestamo = conexion.prepareStatement("select IdPrestamo from prestamos where IdLibro=? "
                    + "and IdSocio=? and Devuelto=1 order by FechaPrestamo desc limit 1");
                obtenerPrestamo.setInt(1, IdLibro);
                obtenerPrestamo.setInt(2, Sesion.getIdUsuario());

                ResultSet consultaPrestamo = obtenerPrestamo.executeQuery();
                if (consultaPrestamo.next()) {
                    int idPrestamo = consultaPrestamo.getInt("IdPrestamo");

                    PreparedStatement devolverPrestamo = conexion.prepareStatement("insert into devoluciones (IdPrestamo, FechaDevolucion) values (?,?)");
                    devolverPrestamo.setInt(1, idPrestamo);
                    devolverPrestamo.setDate(2, java.sql.Date.valueOf(fecha));
                    
                    devolverPrestamo.executeUpdate();
                    
                    new WindowMessage("Libro devuelto exitosamente");
                    
                    biblioteca.LibroIndividual.rentar.setVisible(true);
                    biblioteca.LibroIndividual.devolver.setVisible(false);
                }
            }
            
            //Cierra la conexion
            conectar.cerrarConexion();
        }catch(SQLException error){
            new WindowError("Ocurrió un error. Intente nuevamente.");
            System.out.println("Error: "+error);            
        }
    }
    
    
    
    
    //Obtener la devolución
    public static LocalDate obtenerDevolucion(int idPrestamo){
        LocalDate fechaDevolucion;
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            
            PreparedStatement busquedaDevolucion = conexion.prepareStatement("select FechaDevolucion from devoluciones "
                + "where IdPrestamo='"+idPrestamo+"'");
            ResultSet consultaDevolucion = busquedaDevolucion.executeQuery();
            
            if(consultaDevolucion.next()){
                Date DateVuelta = consultaDevolucion.getDate("FechaDevolucion");
                fechaDevolucion = DateVuelta != null ? DateVuelta.toLocalDate() : null;
                
                return fechaDevolucion;
            }
            
            //Cierra la conexion
            conectar.cerrarConexion();
        }catch(SQLException error){
            new WindowError("Ocurrió un error. Intente nuevamente.");
            System.out.println("Error: "+error);            
        }
        return fecha;
    }
    
    
    
    //Verificar si el libro está rentado
    public static boolean verificarLibroRentado(int idUsuario, int idLibro){
        boolean rentado = false;
        try{
            Connection conexion = conectar.conectar();
         
            PreparedStatement libroRentado = conexion.prepareStatement("select * from prestamos where IdSocio = ? "
                + "and IdLibro = ? and Devuelto=0");
            libroRentado.setInt(1, idUsuario);
            libroRentado.setInt(2, idLibro);
         
            ResultSet consultaLibro = libroRentado.executeQuery();
         
            if(consultaLibro.next()){
                rentado = true;
            }
         
            conectar.cerrarConexion();
        }catch(SQLException error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");
        }
        return rentado;
    }

    
    
    //Generar multas
    public static void generarMultas(){
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            
            PreparedStatement busquedaPrestamo = conexion.prepareStatement("select * from prestamos where Devuelto=0");
            ResultSet consultaPrestamo = busquedaPrestamo.executeQuery();
            
            while(consultaPrestamo.next()){
                Date DatePrestamo = consultaPrestamo.getDate("FechaVuelta");
                LocalDate fechaVuelta = DatePrestamo != null ? DatePrestamo.toLocalDate() : null;
                
                if (!fecha.isBefore(fechaVuelta)) {
                    int IdPrestamo = consultaPrestamo.getInt("IdPrestamo");
                    int IdSocio = consultaPrestamo.getInt("IdSocio");

                    PreparedStatement busquedaMulta = conexion.prepareStatement("select * from multas where IdSocio='"+IdSocio+"' "
                        + "and IdPrestamo='"+IdPrestamo+"'");
                    ResultSet consultaMulta = busquedaMulta.executeQuery();
                    
                    if(!consultaMulta.next()){
                        PreparedStatement insertarMulta = conexion.prepareStatement("insert into multas "
                            + "(IdSocio, IdPrestamo, Monto, Pagado) values (?,?,?,?)");

                        insertarMulta.setInt(1, IdSocio);
                        insertarMulta.setInt(2, IdPrestamo);
                        insertarMulta.setInt(3, 400);
                        insertarMulta.setInt(4, 0);

                        insertarMulta.executeUpdate();

                        PreparedStatement insertarNotificacion = conexion.prepareStatement("insert into notificaciones "
                            + "(IdUsuario, Mensaje) values (?,?)");

                        insertarNotificacion.setInt(1, IdSocio);
                        insertarNotificacion.setString(2, "Se ha generado una multa a tu cuenta");

                        insertarNotificacion.executeUpdate();
                    }
                }
            }
            
            //Cierra la conexion
            conectar.cerrarConexion();
        }catch(SQLException error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");            
        }
    }

    
    
    //Obtener los datos del autor
    public static void obtenerAutor(String autor){
        try{
            //Inicia la conexión
            Connection conexion = conectar.conectar();
            
            PreparedStatement busquedaAutor = conexion.prepareStatement("select * from autores where Nombre='"+autor+"'");
            ResultSet consultaAutor = busquedaAutor.executeQuery();
            
            if(consultaAutor.next()){
                int idAutor = consultaAutor.getInt("IdAutor");
                String biografia = consultaAutor.getString("Biografia");
                String foto = consultaAutor.getString("Foto");
                
                App.cambiarVista(new Autor(idAutor, autor, biografia, foto), null);
            }
            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
        }catch(SQLException error){
            new WindowError("Ocurrió un error. Intente nuevamente.");            
            System.out.println("Error: "+error);
        }        
    }
    
    
    
    //Obtener los libros del autor
    public static ArrayList<LibroBusqueda> librosAutor(int idAutor, String autor, int limite, int offset){
        ArrayList<LibroBusqueda> libros = new ArrayList<>();
        
        try{
            //Inicia la conexión
            Connection conexion = conectar.conectar();

            PreparedStatement librosAutor = conexion.prepareStatement("select * from libros where IdAutor='"+idAutor+"' "
                + "order by IdLibro desc limit ? offset ?");
            librosAutor.setInt(1, limite);
            librosAutor.setInt(2, offset);
            ResultSet consultaLibro = librosAutor.executeQuery();

            while(consultaLibro.next()){
                int idLibro = consultaLibro.getInt("IdLibro");
                String titulo = consultaLibro.getString("Titulo"); 
                String portada = consultaLibro.getString("Portada"); 
                String descripcion = consultaLibro.getString("Descripcion"); 

                libros.add(new LibroBusqueda(idLibro, titulo, autor, portada, descripcion));
            }
        }catch(SQLException error){
            new WindowError("Ocurrió un error. Intente nuevamente.");            
            System.out.println("Error: "+error);
        }
        return libros;
    }
    
    
    
        //Contar la cantidad de libros poren la base de datos
    public static int contarLibrosAutor(int IdAutor) {
        int total = 0;
        
        try {
            Connection conexion = conectar.conectar();
            
            //Cuenta los elementos de la tabla de libros
            PreparedStatement busquedaLibros = conexion.prepareStatement("select count(*) as Total from libros "
                + "where IdAutor='"+IdAutor+"'");
            ResultSet consultaLibros = busquedaLibros.executeQuery();
            if (consultaLibros.next()) {
                total = consultaLibros.getInt("Total");
            }

            //Cierra la conexión
            conectar.cerrarConexion();
        } catch (SQLException error) {
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");
        }
        return total;
    }

    
    
    
    //Obtener los datos del autor
    public static void obtenerEditorial(String editorial){
        try{
            //Inicia la conexión
            Connection conexion = conectar.conectar();
            
            PreparedStatement busquedaEditorial = conexion.prepareStatement("select * from editoriales where "
                + "Editorial='"+editorial+"'");
            ResultSet consultaEditorial = busquedaEditorial.executeQuery();
            
            if(consultaEditorial.next()){
                int idEditorial = consultaEditorial.getInt("IdEditorial");
                String descripcion = consultaEditorial.getString("Descripcion");
                String foto = consultaEditorial.getString("Foto");
                App.cambiarVista(new Editorial(idEditorial, editorial, descripcion, foto), null);
            }
            
            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
        }catch(SQLException error){
            new WindowError("Ocurrió un error. Intente nuevamente.");            
            System.out.println("Error: "+error);
        }
    }
    
    
    
    //Obtener los libros del autor
    public static ArrayList<LibroBusqueda> librosEditorial(int idEditorial, int limite, int offset){
        ArrayList<LibroBusqueda> libros = new ArrayList<>();
        
        try{
            //Inicia la conexión
            Connection conexion = conectar.conectar();

            PreparedStatement librosEditorial = conexion.prepareStatement("select * from libros where "
                + "IdEditorial='"+idEditorial+"' order by IdLibro desc limit ? offset ?");
            librosEditorial.setInt(1, limite);
            librosEditorial.setInt(2, offset);
            
            ResultSet consultaLibro = librosEditorial.executeQuery();

            while(consultaLibro.next()){
                int idLibro = consultaLibro.getInt("IdLibro");
                String titulo = consultaLibro.getString("Titulo"); 
                String portada = consultaLibro.getString("Portada"); 
                int IdAutor = consultaLibro.getInt("IdAutor"); 
                String descripcion = consultaLibro.getString("Descripcion"); 
                
                PreparedStatement busquedaAutor = conexion.prepareStatement("select Nombre from autores "
                    + "where IdAutor='"+IdAutor+"'");
                ResultSet consultaAutor = busquedaAutor.executeQuery();
                
                if(consultaAutor.next()){
                    String autor = consultaAutor.getString("Nombre");
                    libros.add(new LibroBusqueda(idLibro, titulo, autor, portada, descripcion));
                }
            }
        }catch(SQLException error){
            new WindowError("Ocurrió un error. Intente nuevamente.");            
            System.out.println("Error: "+error);
        }
        return libros;
    }
    
    
    
    //Contar la cantidad de libros poren la base de datos
    public static int contarLibrosEditorial(int IdEditorial) {
        int total = 0;
        
        try {
            Connection conexion = conectar.conectar();
            
            //Cuenta los elementos de la tabla de libros
            PreparedStatement busquedaLibros = conexion.prepareStatement("select count(*) as Total "
                + "from libros where IdEditorial='"+IdEditorial+"'");
            ResultSet consultaLibros = busquedaLibros.executeQuery();
            if (consultaLibros.next()) {
                total = consultaLibros.getInt("Total");
            }

            //Cierra la conexión
            conectar.cerrarConexion();
        } catch (Exception error) {
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");
        }
        return total;
    }
    

    
    //Elimina el libro
    public static void eliminarLibro(int idLibro) {
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
            
            PreparedStatement eliminarLibro = conexion.prepareStatement("delete from libros where IdLibro='"+idLibro+"'");
            PreparedStatement eliminarLibroAdministrador = conexion.prepareStatement("delete from "
                + "librosadministradores where IdLibro='"+idLibro+"'");

            int filasLA = eliminarLibroAdministrador.executeUpdate();
            int filasLibro = eliminarLibro.executeUpdate();

            if (filasLibro > 0 && filasLA >0) {
                //Muestra que eliminó el libro
                new WindowMessage("Libro eliminado correctamente.");
                App.cambiarVista(new Busqueda(), Menu.busqueda);
            } else {
                new WindowError("Ocurrió un error. Intente nuevamente.");
            }
            
            //Cierra la conexión a la base de datos
            conectar.cerrarConexion();
        }catch(SQLException error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");
        }
    }        
    
    
        
    //Actualizar datos de la editorial
    public static void actualizarEditorial(int idEditorial, String editorial, String descripcion, String foto){
        try {
            //Inicia la conexión
            Connection conexion = conectar.conectar();
            
            PreparedStatement actualizarEditorial = conexion.prepareStatement("update editoriales set Editorial=?, "
                + "Descripcion=? where IdEditorial=?");
            
            actualizarEditorial.setString(1, editorial);
            actualizarEditorial.setString(2, descripcion);
            actualizarEditorial.setInt(3, idEditorial);

            actualizarEditorial.executeUpdate();
            
            new WindowMessage("Datos actualizados correctamente");
            App.cambiarVista(new Editorial(idEditorial, editorial, descripcion, foto), null);
            
            //Cierra la conexion
            conectar.cerrarConexion();
        } catch (Exception error) {
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");            
        }        
    }
    
    
        
    //Actualizar datos de la editorial
    public static void actualizarAutor(int idAutor, String autor, String biografia, String foto){
        try {
            //Inicia la conexión
            Connection conexion = conectar.conectar();
            
            PreparedStatement actualizarEditorial = conexion.prepareStatement("update autores set Nombre=?, Biografia=? where IdAutor=?");
            
            actualizarEditorial.setString(1, autor);
            actualizarEditorial.setString(2, biografia);
            actualizarEditorial.setInt(3, idAutor);

            actualizarEditorial.executeUpdate();
            
            new WindowMessage("Datos actualizados correctamente");
            App.cambiarVista(new Autor(idAutor, autor, biografia, foto), null);
            
            //Cierra la conexion
            conectar.cerrarConexion();
        } catch (SQLException error) {
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");            
        }        
    }
    
    
    
    //Actualizar libro
    public static void actualizarLibro(int IdLibro, String isbn, String titulo, String autor, String portada, 
        int año, String editorial, String genero, int paginas, String descripcion, String admin){
        
        try{
            //Inicia la conexión
            Connection conexion = conectar.conectar();
            
            PreparedStatement actualizarLibro = conexion.prepareStatement("update libros set ISBN=?, Titulo=?, "
                + "Año=?, Paginas=?, Descripcion=? where idLibro=?");
            
            actualizarLibro.setString(1, isbn);
            actualizarLibro.setString(2, titulo);
            actualizarLibro.setInt(3, año);
            actualizarLibro.setInt(4, paginas);
            actualizarLibro.setString(5, descripcion);
            actualizarLibro.setInt(6, IdLibro);

            actualizarLibro.executeUpdate();
            
            new WindowMessage("Datos actualizados correctamente");
            String añoString = ""+año;
            String paginasString = ""+paginas;
            
            App.cambiarVista(new LibroIndividual(IdLibro, isbn, titulo, autor, portada, añoString, editorial, 
                genero, paginasString, descripcion, admin), null);
            
            //Cierra la conexion
            conectar.cerrarConexion();            
        }catch(SQLException error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");            
        }
    }
    
    
    
    //Obtener los libros subidos por los administradores
    public static ArrayList<LibrosAdministradores> librosAdministradores(){
        ArrayList<LibrosAdministradores> admins = new ArrayList<>();
        
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();
        
            //Buscar en la tabla de usuarios los usuarios de tipo administrador
            PreparedStatement busquedaAdmins = conexion.prepareStatement("select * from usuarios where "
                + "TipoUsuario='administrador'");
            ResultSet consultaAdmins = busquedaAdmins.executeQuery();
            
            //Guarda los administradores
            ArrayList<Integer> administradores = new ArrayList<>();
            
            while(consultaAdmins.next()){
                int IdAdmin = consultaAdmins.getInt("IdUsuario");
                
                //agregar los IdUsuario a un arreglo
                administradores.add(IdAdmin);
            }
            
            //hacer la busqueda por cada uno de los administradores
            for(int IdAdmin : administradores){
                PreparedStatement cuentaAdmin = conexion.prepareStatement("select count(*) from librosadministradores "
                    + "where IdAdmin='"+IdAdmin+"'");
                ResultSet consultaCuenta = cuentaAdmin.executeQuery();
                
                if(consultaCuenta.next()){
                    int cantidad = consultaCuenta.getInt(1);
                    
                    //los que tengan una cuenta > 0, agregarlos a una clase librosAdministradores que tenga la cantidad y el nombre
                    if(cantidad > 0){
                        PreparedStatement obtenerAdmin = conexion.prepareStatement("select Usuario from usuarios where "
                            + "IdUsuario='"+IdAdmin+"'");
                        ResultSet consultaObtener = obtenerAdmin.executeQuery();         
                        
                        if(consultaObtener.next()){
                            //Obtener el usuario
                            String usuario = consultaObtener.getString("Usuario");
                            admins.add(new LibrosAdministradores(usuario, cantidad));
                        }
                    }
                }
            }
            
            //Cierra la conexion
            conectar.cerrarConexion();
        }catch(SQLException error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");            
        }
        
        //retornar el arrayList
        return admins;
    }
    
    
    
    //Obtener los generos de los libros
    public static ArrayList<LibrosGeneros> obtenerGeneros(){
        ArrayList<LibrosGeneros> generos = new ArrayList<>();
        
        try{
            //Inicia la conexion
            Connection conexion = conectar.conectar();

            PreparedStatement busquedaGenero = conexion.prepareStatement("select g.Genero, count(*) "
                + "as Cantidad from prestamos p join libros l "
                + "on p.IdLibro = l.IdLibro join generos g on l.IdGenero = g.IdGenero "
                + "group by g.Genero order by Cantidad desc");
            ResultSet consultaGenero = busquedaGenero.executeQuery();

            // Recorre el resultado
            while (consultaGenero.next()) {
                String genero = consultaGenero.getString("Genero");
                int cantidad = consultaGenero.getInt("Cantidad");
                generos.add(new LibrosGeneros(genero, cantidad));
            }
            
            //Cierra la conexion
            conectar.cerrarConexion();            
        }catch(SQLException error){
            System.out.println("Error: "+error);
            new WindowError("Ocurrió un error. Intente nuevamente.");            
        }
        //Retorna el genero y la cantidad de libros del genero
        return generos;
    }    
}