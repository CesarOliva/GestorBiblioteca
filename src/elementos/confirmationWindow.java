package elementos;

//Importa las librerias necesarias
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import Conexion.Sesion;

//Clase confirmationWindow extendida de JFrame. Frame personalizado.
public class confirmationWindow extends JFrame{
    JTextField confirmacion;
    
    public confirmationWindow(String text, Runnable accion){
        //Configuración de la ventana 
        setTitle("Confirmación");
        setSize(300, 180);
        setResizable(false);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        //Creación del Panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setLayout(null);

        JTextPane message = new JTextPane();
        message.setContentType("text/html");
        message.setText("<html>"
                + "<p style='font-family:Inter; font-size:10px; font-weight:400; color:#000; text-align:center'>"+text+"</p>");
        message.setBounds(50, 5, 200, 80);
        message.setEditable(false);
        message.setFocusable(false);
        message.setBackground(Color.white);

        confirmacion = new JTextField(50);
        confirmacion.setFont(new Font("Poppins", Font.PLAIN, 12));
        confirmacion.setForeground(Color.black);
        confirmacion.setBounds(30, 100, 150, 30);
        
        JButton button = new RoundedButton("OK");
        button.setBackground(new Color(100, 149, 237));
        button.setForeground(Color.WHITE);
        button.setBounds(200, 100, 70, 30);
        
        button.addActionListener((ActionEvent e)->{
            if(getText().equals(Sesion.getContraseña())){
                //Ejecuta la función
                accion.run();
                dispose();
            }
            else{
                new WindowError("Contraseña Incorrecta");
                dispose();
            }
        });
        
        
        //Agrega los elementos al panel 
        panel.add(message);
        panel.add(confirmacion);
        panel.add(button);

        //Hace visible el frame
        add(panel);
        setVisible(true);
    }
    
    public String getText(){
        return confirmacion.getText();
    }
}
