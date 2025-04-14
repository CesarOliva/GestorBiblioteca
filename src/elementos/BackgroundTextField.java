package elementos;

//Importa las librerias necesarias
import javax.swing.*;
import java.awt.*;

//Clase extendida de JTextField. TextField personalizado
public class BackgroundTextField extends JTextField {
    public BackgroundTextField() {

        setForeground(Color.WHITE);
        setBackground(new Color(0,0,0, 100));
        setFont(new Font("Poppins", Font.PLAIN, 12));
        setMargin(new Insets(0, 10, 0, 10));
        
        //Cambia el color del |
        setCaretColor(Color.WHITE);
        setOpaque(false);        
    }
}
