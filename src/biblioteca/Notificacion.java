package biblioteca;

//Clase Notificacion. Esqueleto de la vista de notificaciones
public class Notificacion {
    private String mensaje, tipo;
    private int id;

    public Notificacion(int id, String mensaje, String tipo){
        this.id = id;
        this.mensaje = mensaje;
        this.tipo = tipo;
    }
    
    //Obtener los datos
    public int getId(){ return id;}
    
    public String getMensaje(){ return mensaje;}
    
    public String getTipo(){ return tipo;}
}