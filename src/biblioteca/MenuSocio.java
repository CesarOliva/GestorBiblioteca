package biblioteca;

//Se importan las librerias necesarias
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;

//"Librerias" personalizadas a importar
import elementos.RoundedButton;

public class MenuSocio extends JPanel{
    JPanel menu;
    
    public MenuSocio(){
        //Se crea el panel del lado izquierdo (Menú) que contiene los botones que cambian la vista
        menu = new JPanel();
        menu.setLayout(null);
        menu.setPreferredSize(new Dimension(250, 600));
        menu.setBackground(new Color(100, 149, 237));

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

        JButton agregarLibro = new RoundedButton("Agregar Libro");
        agregarLibro.setFont(new Font("Poppins", Font.PLAIN, 15));
        agregarLibro.setBackground(Color.WHITE);
        agregarLibro.setForeground(new Color(100, 149, 237));
        agregarLibro.setBounds(50, 115, 150, 30);
        
        JButton estadisticas = new RoundedButton("Estadisticas");
        estadisticas.setFont(new Font("Poppins", Font.PLAIN, 15));
        estadisticas.setBackground(Color.WHITE);
        estadisticas.setForeground(new Color(100, 149, 237));
        estadisticas.setBounds(50, 160, 150, 30);
        
        JButton logOut = new RoundedButton("Cerrar Sesión");
        logOut.setFont(new Font("Poppins", Font.PLAIN, 15));
        logOut.setBackground(Color.WHITE);
        logOut.setForeground(new Color(100, 149, 237));
        logOut.setBounds(50, 205, 150, 30);

        //Cambio de vista al presionar los botones
        inicio.addActionListener((ActionEvent e) -> {
            //Ejecuta el metodo cambiarVista
            App.cambiarVista(new CRUD_Libro(), inicio);
        });
        busqueda.addActionListener((ActionEvent e) -> {
            //Ejecuta el metodo cambiarVista
            App.cambiarVista(new CRUD_Libro(), busqueda);
        });
        agregarLibro.addActionListener((ActionEvent e) -> {
            //Ejecuta el metodo cambiarVista
            App.cambiarVista(new CRUD_Libro(), agregarLibro);
        });
        estadisticas.addActionListener((ActionEvent e) -> {
            //Ejecuta el metodo cambiarVista
            App.cambiarVista(new CRUD_Libro(), estadisticas);
        });
        logOut.addActionListener((ActionEvent e) -> {
            //Ejecuta el metodo cambiarVista
            App.cambiarVista(new CRUD_Libro(), logOut);
        });

        //Se agregan los botones al panel del menú
        menu.add(inicio);
        menu.add(busqueda);
        menu.add(agregarLibro);
        menu.add(estadisticas);
        menu.add(logOut);
    }
}            