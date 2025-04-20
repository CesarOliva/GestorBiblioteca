package biblioteca;

//Se importan las librerias necesarias
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import Conexion.Sesion;

//"Librerias" personalizadas a importar
import elementos.RoundedButton;

public class Menu extends JPanel{
    public JPanel menu;
    private JButton agregarLibro, prestamos;
    
    public Menu(String tipoUsuario){
        //Se crea el panel del lado izquierdo (Menú) que contiene los botones que cambian la vista
        menu = new JPanel();
        menu.setLayout(null);
        menu.setPreferredSize(new Dimension(250, 600));
        menu.setBackground(new Color(100, 149, 237));

        
        JLabel logo = new JLabel(new FlatSVGIcon("imagenes/LogoArriba.svg", 143, 130));
        logo.setBounds(50, 20, 143, 550);

        //Creación y diseño de los botones
        JButton inicio = new RoundedButton("Inicio");
        inicio.setFont(new Font("Poppins", Font.PLAIN, 15));
        inicio.setBackground(Color.WHITE);
        inicio.setForeground(new Color(100, 149, 237));
        inicio.setBounds(50, 27, 150, 30);

        JButton busqueda = new RoundedButton("Busqueda");
        busqueda.setFont(new Font("Poppins", Font.PLAIN, 15));
        busqueda.setBackground(Color.WHITE);
        busqueda.setForeground(new Color(100, 149, 237));
        busqueda.setBounds(50, 71, 150, 30);

        if(tipoUsuario.equals("administrador")){
            agregarLibro = new RoundedButton("Agregar Libro");
            agregarLibro.setFont(new Font("Poppins", Font.PLAIN, 15));
            agregarLibro.setBackground(Color.WHITE);
            agregarLibro.setForeground(new Color(100, 149, 237));
            agregarLibro.setBounds(50, 115, 150, 30);
            
            agregarLibro.addActionListener((ActionEvent e) -> {
                //Ejecuta el metodo cambiarVista
                App.cambiarVista(new CRUD_Libro(), agregarLibro);
            });
            menu.add(agregarLibro);
        }else{
            prestamos = new RoundedButton("Préstamos");
            prestamos.setFont(new Font("Poppins", Font.PLAIN, 15));
            prestamos.setBackground(Color.WHITE);
            prestamos.setForeground(new Color(100, 149, 237));
            prestamos.setBounds(50, 115, 150, 30);
            
            prestamos.addActionListener((ActionEvent e) -> {
                //Ejecuta el metodo cambiarVista
                App.cambiarVista(null, prestamos);
            });
            menu.add(prestamos);
        }
        
        JButton estadisticas = new RoundedButton("Estadísticas");
        estadisticas.setFont(new Font("Poppins", Font.PLAIN, 15));
        estadisticas.setBackground(Color.WHITE);
        estadisticas.setForeground(new Color(100, 149, 237));
        estadisticas.setBounds(50, 160, 150, 30);
        
        JButton logOut = new RoundedButton("Cerrar Sesión");
        logOut.setFont(new Font("Poppins", Font.PLAIN, 15));
        logOut.setBackground(Color.red);
        logOut.setForeground(Color.white);
        logOut.setBounds(50, 205, 150, 30);

        //Cambio de vista al presionar los botones
        inicio.addActionListener((ActionEvent e) -> {
            //Ejecuta el metodo cambiarVista
            App.cambiarVista(new Usuario(Sesion.getNombre(), Sesion.getUsuario(), Sesion.getContraseña(), Sesion.getFechaCreacion(), Sesion.getFoto()), inicio);
        });
        busqueda.addActionListener((ActionEvent e) -> {
            //Ejecuta el metodo cambiarVista
            App.cambiarVista(new Busqueda(), busqueda);
        });
        estadisticas.addActionListener((ActionEvent e) -> {
            //Ejecuta el metodo cambiarVista
            App.cambiarVista(new Estadisticas(), estadisticas);
        });
        logOut.addActionListener((ActionEvent e) -> {
            Sesion.cerrarSesion();
            new LogIn();
            App.getInstancia().cerrar();
        });

        //Se agregan los botones al panel del menú
        menu.add(logo);
        menu.add(inicio);
        menu.add(busqueda);
        menu.add(estadisticas);
        menu.add(logOut);
    }
}            