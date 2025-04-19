package biblioteca;

//Clase LibroBusqueda. Esqueleto de la vista de los libros en busquedas
public class LibroBusqueda {
    private String id, titulo, autor, portada, descripcion;

    public LibroBusqueda(String id, String titulo, String autor, String portada, String descripcion) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.portada = portada;
        this.descripcion = descripcion;
    }

    //Obtener los datos
    public String getId() { return id; }
    
    public String getTitulo() { return titulo; }
    
    public String getAutor() { return autor; }
    
    public String getPortada() { return portada; }
    
    public String getDescripcion() { return descripcion; }

}
