package biblioteca;

//Clase LibrosAdministradores. Retorno de los elementos a insertar en la gráfica de estadistica
public class LibrosAdministradores {
    private int cantidad;
    private String admin;
    
    public LibrosAdministradores(String admin, int cantidad){
        this.cantidad = cantidad;
        this.admin = admin;
    }
    
    //Obtener los datos
    public int getCantidad() { return cantidad; }
    
    public String getAdministrador() { return admin; }
    
}
