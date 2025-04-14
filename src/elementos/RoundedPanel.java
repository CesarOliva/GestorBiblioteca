package elementos;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {
	private Image imagenFondo;
	private int ancho, alto;

	// Constructor para redondear la imagen
	public RoundedPanel(String rutaImagen, int ancho, int alto) {
    	this.imagenFondo = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
    	this.ancho = ancho;
    	this.alto = alto;
    	setOpaque(false);
    	setPreferredSize(new Dimension(ancho, alto));  // Establecer el tamaño del panel
	}

	@Override
	protected void paintComponent(Graphics g) {
    	super.paintComponent(g);
    	// Crear un objeto Graphics2D para mayor control
    	Graphics2D g2d = (Graphics2D) g;
    	// Establecer suavizado de bordes
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

    	// Redondear los bordes del panel
    	g2d.setClip(new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 4, 4));  // 20 es el radio de redondeo

    	// Dibujar la imagen dentro del panel
    	g2d.drawImage(imagenFondo, 0, 0, ancho, alto, this);  // Dibuja la imagen redimensionada
	}
}
