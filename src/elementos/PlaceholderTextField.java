package elementos;

//Importa las librerias necesarias
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

//Clase extendida de JTextField. TextField personalizado
public class PlaceholderTextField extends JTextField {
    private String placeholder;
    private Color placeholderColor = Color.GRAY;
    private Color textColor = Color.BLACK;

    //Agrega un placeholder a los textFields
    public PlaceholderTextField(String placeholder, int columns) {
        super(columns);
        this.placeholder = placeholder;
        
        setMargin(new Insets(0, 10, 0, 10));
        setText(placeholder);
        setForeground(placeholderColor);

        //Cuando esté el elemento en focus
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //Si está el texto del placeholder
                if (getText().equals(placeholder)) {
                    //Limpia el textField para escribir
                    setText("");
                    setForeground(textColor);
                }
            }

            @Override
            //Si al terminar el evento de focus el texto está vacio
            public void focusLost(FocusEvent e) {
                if (getText().isEmpty()) {
                    //Agrega el texto del placeHolder
                    setText(placeholder);
                    setForeground(placeholderColor);
                }
            }
        });
    }

    //Devuelve si el contenido está vacio o tiene el texto del placeholder    
    public boolean validarContenido(){
        return getText().equals(placeholder) || getText().trim().isEmpty();
    }
    
    //Vuelve a poner el placeholder
    public void resetPlaceholder() {
        setForeground(Color.black);
    }
}
