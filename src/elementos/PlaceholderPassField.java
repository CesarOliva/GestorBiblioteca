package elementos;

//Importa las librerias necesarias
import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

//Clase extendida de JPasswordField. PasswordField personalizado
public class PlaceholderPassField extends JPasswordField {
    private String placeholder;
    private boolean mostrar;
    private Color placeholderColor = Color.GRAY;
    private Color textColor = Color.BLACK;

    //Agrega un placeholder a los PasswordFields
    public PlaceholderPassField(String placeholder, int columns) {
        super(columns);
        this.placeholder = placeholder;
        this.mostrar=  true;
        
        setMargin(new Insets(0, 10, 0, 10));
        
        setText(placeholder);
        setForeground(placeholderColor);
        setEchoChar((char) 0); //Muestra texto normal

        //Cuando esté el elemento en focus
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                //Si está el texto del placeholder
                if (mostrar) {
                    //Limpia el passField para escribir
                    setText("");
                    setForeground(textColor);
                    setEchoChar('*');
                    mostrar = false;
                }
            }

            @Override
            //Si al terminar el evento de focus el texto está vacio
            public void focusLost(FocusEvent e) {
                if (getPassword().length==0) {
                    //Agrega el texto del placeHolder
                    setText(placeholder);
                    setForeground(placeholderColor);
                    setEchoChar((char) 0);
                    mostrar = true;
                }
            }
        });
    }
    
    @Override
    public char[] getPassword(){
        if(mostrar){
            return new char[0]; //Si el placeholde está activo, devuelve vacio
        }
        return super.getPassword();
    }
 
    //Vuelve a poner el placeholder
    public void resetPlaceholder() {
        setText(placeholder);
        setEchoChar((char)0);
        setForeground(Color.GRAY);
        mostrar = true;
    }
}