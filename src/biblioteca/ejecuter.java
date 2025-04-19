package biblioteca;

//Se importan las librerias necesarias
import com.formdev.flatlaf.FlatLightLaf;
import javax.swing.UIManager;

//Clase principal que ejecuta el Inicio de sesión. 
public class ejecuter {
    public static void main(String[] args) {
        //Ejecuta el inicio de sesión al iniciar el programa        
        new App("");
        
        try {
            // Establece el LookAndFeel
            UIManager.setLookAndFeel(new FlatLightLaf());
        } catch (Exception e) {
            System.out.println("Error"+e);
        }
    }
}