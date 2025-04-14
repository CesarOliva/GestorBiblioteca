package elementos;

//Se importan las librerias a utilizar;
import javax.swing.*;
import java.util.Hashtable;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;

//Clase SliderSize heredada de JSlider. SLider Personalizado
public class SliderSize extends JSlider {
    public SliderSize() {
        super(0, 100, 50);

        // Hacer que avance de 25 en 25
        setMajorTickSpacing(25);
        setPaintTicks(true);
        setPaintLabels(true);
        setOpaque(false);
        setFocusable(false);

        // Cambiar etiquetas de los valores
        Hashtable<Integer, JLabel> etiquetas = new Hashtable<>();
        
        JLabel mchico = new JLabel("Muy Chico");
        mchico.setFont(new Font("Poppins", Font.PLAIN, 14));
        mchico.setForeground(Color.WHITE);
        
        JLabel chico = new JLabel("Chico");
        chico.setFont(new Font("Poppins", Font.PLAIN, 14));
        chico.setForeground(Color.WHITE);
        
        JLabel mediano = new JLabel("Mediano");
        mediano.setFont(new Font("Poppins", Font.PLAIN, 14));
        mediano.setForeground(Color.WHITE);
        
        JLabel grande = new JLabel("Grande");
        grande.setFont(new Font("Poppins", Font.PLAIN, 14));
        grande.setForeground(Color.WHITE);
        
        JLabel mgrande = new JLabel("Muy Grande");
        mgrande.setFont(new Font("Poppins", Font.PLAIN, 14));
        mgrande.setForeground(Color.WHITE);
        
        //Reemplaza el numero por el label
        etiquetas.put(0, mchico);
        etiquetas.put(25, chico);
        etiquetas.put(50, mediano);
        etiquetas.put(75, grande);
        etiquetas.put(100, mgrande);
        setLabelTable(etiquetas);

        // Aplica diseño personalizado
        setUI(new personalizar(this));
        //Cambia el color del tick 
        UIManager.put("Slider.tickColor", Color.WHITE);
    }

    // Personalizar el Slider editando los elementos defecto del Slider
    private static class personalizar extends BasicSliderUI {
        public personalizar(JSlider slider) {
            super(slider);
        }

        //Cambia el diseño del thumb
        @Override
        public void paintThumb(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Cambia el color y tamaño del thumb
            int thumbSize = 18;
            g2.setColor(Color.red); 
            g2.fillOval(thumbRect.x, thumbRect.y, thumbSize, thumbSize);
        }

        //Cambia el diseño de la barra
        @Override
        public void paintTrack(Graphics g) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int trackHeight = 8;
            int x = trackRect.x;
            int y = trackRect.y + trackRect.height / 2 - trackHeight / 2;
            int width = trackRect.width;

            // Pinta la parte seleccionada de la barra
            int selectedWidth = thumbRect.x - (x-9); 
            g2.setColor(Color.red);
            g2.fillRoundRect(x, y, selectedWidth, trackHeight, trackHeight, trackHeight);

            // Pinta el borde en toda la barra
            g2.setColor(Color.red);
            g2.setStroke(new BasicStroke(1));
            g2.drawRoundRect(x, y, width, trackHeight, trackHeight, trackHeight);
        }
    }
}