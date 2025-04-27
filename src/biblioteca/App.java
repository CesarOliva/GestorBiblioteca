package biblioteca;

//Se importan las librerias a usar
import javax.swing.*;
import java.awt.*;
import Conexion.Sesion;

//Clase App heredada de un JFrame. Frame personalizado
public class App extends JFrame{
    private static JPanel contentPanel; //cambia el contenido
    private static JButton botonActivo = null; //Boton que guardará el panel activo
    private static JPanel menu;
    private static App instancia;
    
    //Constructor
    public App(String tipoUsuario){
        //Se crea el frame que contendrá dos paneles y se hacen sus configuraciones
        setTitle("BiblioTEC");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setResizable(false);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);
        
        instancia = this;
        
        //Creación del panel de la derecha donde se verá la vista
        contentPanel = new JPanel();
        contentPanel.setLayout(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);

        //Agrega el usuario como primer vista a cargar
        contentPanel.add(new JPanel());
        menu = new Menu(tipoUsuario).menu;
        
        //Se agregan los paneles al frame y lo hace visible
        add(menu, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
        setVisible(true);
        
}

    //Metodo que muestra el panel con el contenido del codigo
    //Cambia el diseño del boton activo
    public static void cambiarVista(JPanel panel, JButton boton) {
        //Restaurar el botón anterior
        if (botonActivo != null) { 
            botonActivo.setBackground(Color.WHITE);
            botonActivo.setForeground(new Color(100, 149, 237));
        }
        
        //Cambiar el diseño al nuevo botón seleccionado
        if(boton!=null){
            boton.setBackground(new Color(100, 149, 237));
            boton.setForeground(Color.WHITE);
        }
        
        botonActivo = boton;

        //Elimina el contenido del panel de contenido
        contentPanel.removeAll();
        //Agrega el contenido del codigo a ejecutar al panel de contenido
        contentPanel.add(panel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    public static App getInstancia(){
        return instancia;
    }
    
    public void cerrar(){
        dispose();
        Sesion.cerrarSesion();
    }
}