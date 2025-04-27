package elementos;

//Importa las librerias necesarias
import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.*;

//Clase extendida de JButton. Botón personalizado
public class RoundedButton extends JButton {
    public RoundedButton(String text) {
        super(text);
        setContentAreaFilled(false); // Evita el fondo por defecto
        setFocusPainted(false); // Quita el borde de selección

        setUI(new BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                AbstractButton button = (AbstractButton) c;
                ButtonModel model = button.getModel();
                Graphics2D g2 = (Graphics2D) g.create();

                if (!model.isEnabled()) {
                    g2.setColor(new Color(200, 200, 200));
                    g2.fillRect(0, 0, c.getWidth(), c.getHeight());

                    g2.setColor(Color.black);
                    FontMetrics fm = g2.getFontMetrics();
                    int x = (c.getWidth() - fm.stringWidth(button.getText())) / 2;
                    int y = (c.getHeight() + fm.getAscent()) / 2 - 2;
                    g2.drawString(button.getText(), x, y);
                } else {
                    super.paint(g, c);
                }

                g2.dispose();
            }
        });
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
