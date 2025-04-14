package elementos;

//Se importan las librerias a utilizar
import java.awt.*;
import javax.swing.*;
import javax.swing.plaf.basic.BasicScrollBarUI;

//Clase CustomScroll extendida de JScrollPane. ScrollPane personalizado.
public class CustomScroll extends JScrollPane {
    public CustomScroll(JPanel panel) {
        super(panel);
        
        // Aplica diseño personalizado
        getVerticalScrollBar().setUI(new CustomScrollBar());
        getHorizontalScrollBar().setUI(new CustomScrollBar());
        verticalScrollBar.setUnitIncrement(20);
        verticalScrollBar.setBlockIncrement(50);

        setBorder(null);
        setOpaque(false);
        getViewport().setOpaque(false);
    }

    //Personalizar el ScrollBar
    private static class CustomScrollBar extends BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors() {
            this.thumbColor = new Color(164, 169, 171);
            this.trackColor = new Color(204, 210, 213);
        }
        @Override
        protected JButton createDecreaseButton(int orientation) {
            return new JButton() {
                {
                    setPreferredSize(new Dimension(0, 0));
                }
            };
        }

        @Override
        protected JButton createIncreaseButton(int orientation) {
            return new JButton() {
                {
                    setPreferredSize(new Dimension(0, 0));
                }
            };
        }
    }
}
