package elementos;

//Importa las librerias necesarias
import javax.swing.JButton;
import java.awt.*;

//Clase extendida de JButton. Botón personalizado
public class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Evita el fondo por defecto
        setFocusPainted(false); // Quita el borde de selección
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar fondo redondeado
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10); // Esquinas redondeadas
        super.paintComponent(g);
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar borde redondeado
        g2.setColor(getForeground());
        g2.drawRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
    }
}
