package elementos;

//Importa las librerías necesarias 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;

//Clase WindowError del tipo JFrame. Frame personalizado
public class WindowMessage extends JFrame {
    private Timer timer;
    
    public WindowMessage(String text) {
        //Configuración de la ventana 
        setTitle("Información");
        setSize(300, 180);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        //Creación del Panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        //Creación de los elementos de error
        JLabel icon = new JLabel(new FlatSVGIcon("imagenes/info.svg", 50, 50));
        icon.setBounds(30, 20, 50, 50);

        JTextPane message = new JTextPane();
        message.setContentType("text/html");
        message.setText("<html>"
                + "<p style='font-family:Inter; font-size:10px; font-weight:400; color:#000; text-align:center'>"+text+"</p>");
        message.setBounds(100, 5, 150, 80);
        message.setEditable(false);
        message.setFocusable(false);
        message.setBackground(Color.white);

        JButton button = new RoundedButton("OK");

        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setBounds(115, 100, 70, 30);
        
        button.addActionListener((ActionEvent e)->{
            dispose();
        });
        
        //Crea un timer de 5 segundos para cerrar la ventana
        timer = new Timer(5000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                dispose();
            }
        });
        timer.start();
        
        //Agrega los elementos al panel 
        panel.add(icon);
        panel.add(message);
        panel.add(button);

        //Hace visible el frame
        add(panel);
        setVisible(true);
    }
}