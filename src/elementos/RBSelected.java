package elementos;

// Se importan las librerías a usar
import com.formdev.flatlaf.extras.FlatSVGIcon;
import javax.swing.*;
import java.awt.*;

//Clase heredada de JRadioButton. RadioButton personalizado
public class RBSelected extends JRadioButton {
    public RBSelected(String text) {
        super(text);

        // Cargar imágenes en FlatLaf
        FlatSVGIcon normalSVG = new FlatSVGIcon("elementos/normal.svg", 20, 20);
        FlatSVGIcon selectedSVG = new FlatSVGIcon("elementos/selected.svg", 20, 20);

        // Aplicar iconos personalizados
        setIcon(normalSVG);
        setSelectedIcon(selectedSVG);

        // Configuración del checkbox
        setOpaque(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setFont(new Font("Poppins", Font.PLAIN, 14));
        setForeground(Color.WHITE);
    }
}