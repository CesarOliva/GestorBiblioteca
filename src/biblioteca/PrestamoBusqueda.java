package biblioteca;

import java.time.LocalDate;

//Clase LibroBusqueda. Esqueleto de la vista de los libros en busquedas
public class PrestamoBusqueda {
    private String titulo, autor, portada;
    private Boolean devuelto;
    private int idLibro, idPrestamo;
    private LocalDate fechaPrestamo, fechaDevolucion;
    
    public PrestamoBusqueda(int idLibro, String titulo, String autor, String portada, 
            int IdPrestamo, LocalDate fechaPrestamo, LocalDate fechaDevolucion, Boolean devuelto) {
        
        this.idLibro = idLibro;
        this.titulo = titulo;
        this.autor = autor;
        this.portada = portada;
        this.idPrestamo = IdPrestamo;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaDevolucion = fechaDevolucion;
        this.devuelto = devuelto;
    }

    //Obtener los datos
    public int getIdLibro() { return idLibro; }
    
    public String getTitulo() { return titulo; }
    
    public String getAutor() { return autor; }
    
    public String getPortada() { return portada; }
    
    public int getIdPrestamo() { return idPrestamo; }
    
    public LocalDate getFechaPrestamo() { return fechaPrestamo; }
    
    public LocalDate getFechaDevolucion() { return fechaDevolucion; }
    
    public Boolean getEstado() { return devuelto; }
}
