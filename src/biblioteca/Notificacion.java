package biblioteca;

//Clase Notificacion. Esqueleto de la vista de notificaciones
public class Notificacion {
    private String mensaje;
    private int id;

    public Notificacion(int id, String mensaje){
        this.id = id;
        this.mensaje = mensaje;
    }
    
    //Obtener los datos
    public int getId(){ return id;}
    
    public String getMensaje(){ return mensaje;}
    
}