package biblioteca;

//Clase LibrosGeneros. Retorno de los elementos a insertar en la gráfica de estadistica
public class LibrosGeneros {
    private int cantidad;
    private String genero;
    
    public LibrosGeneros(String genero, int cantidad){
        this.cantidad = cantidad;
        this.genero = genero;
    }
    
    //Obtener los datos
    public int getCantidad() { return cantidad; }
    
    public String getGenero() { return genero; }
    
}
