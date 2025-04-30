package biblioteca;

//Se importan las librerias necesarias
import javax.swing.UIManager;
import com.formdev.flatlaf.FlatLightLaf;
import Conexion.Peticiones;

//Clase principal que ejecuta el Inicio de sesión. 
public class ejecuter {
    public static void main(String[] args) {
        //Ejecuta el inicio de sesión al iniciar el programa        
        new LogIn();
        
        try {
            // Establece el LookAndFeel
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.out.println("Error"+e);
        }
        
        //Al iniciar checa multas y las genera
        Peticiones.generarMultas();
    }
}