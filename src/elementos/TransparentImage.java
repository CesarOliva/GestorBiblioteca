package elementos;

//Se importan las librerias a usar
import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

public class TransparentImage extends ImageIcon {

    public TransparentImage(String path, float opacity) {
        super(TransparentImage.makeTransparent(new ImageIcon(TransparentImage.class.getResource(path)).getImage(), opacity));
    }

    private static Image makeTransparent(Image img, float opacity) {
        BufferedImage bufferedImage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TRANSLUCENT);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, opacity));
        g2d.drawImage(img, 0, 0, null);
        g2d.dispose();
        return bufferedImage;
    }
}
