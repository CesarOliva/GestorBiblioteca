package elementos;

//Se importan las librerias a utilizar
import javax.swing.*;
import java.awt.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;
import javax.swing.plaf.basic.ComboPopup;

//Clase heredada de JCHeckBox. CheckBox personalizado
public class CustomCB extends JComboBox {
    public CustomCB(String[] lista) {
        super(lista);

        setBackground(Color.WHITE);
        setForeground(Color.black);
        setFont(new Font("Poppins", Font.PLAIN, 13));
        setFocusable(false);

        // Aplica diseño personalizado
        setUI(new personalizar());
    }
    
    // Personalizar el ComboBox editando los elementos defecto del comboBox
    private static class personalizar extends BasicComboBoxUI {
        @Override
        protected ComboPopup createPopup() {
            BasicComboPopup popup = new BasicComboPopup(comboBox) {
                @Override
                protected JScrollPane createScroller() {
                    JScrollPane scrollPane = super.createScroller();
                    // Modificar el scroll
                    scrollPane.getVerticalScrollBar().setUI(new CustomScrollBar());
                    scrollPane.setBackground(Color.GRAY); 

                    return scrollPane;
                }
            };
            return popup;
        }

        // Clase para personalizar el scroll del ComboBox
        private static class CustomScrollBar extends BasicScrollBarUI {
            @Override
            //Color de los elementos del scroll
            protected void configureScrollBarColors() {
                this.thumbColor = new Color(164, 169, 171);
                this.trackColor = new Color(204, 210, 213);
            }
            
            // Oculta el botón superior
            @Override
            protected JButton createDecreaseButton(int orientation) {
                return new JButton() {
                    {
                        setPreferredSize(new Dimension(0, 0));
                    }
                };
            }
            
            // Oculta el botón superior
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
}
