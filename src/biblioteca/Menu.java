package biblioteca;

//Se importan las librerias necesarias
import java.awt.*;
import javax.swing.*;
import java.awt.event.ActionEvent;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import Conexion.Sesion;
import elementos.ButtonSounds;
import elementos.MusicPlayer;

//"Librerias" personalizadas a importar
import elementos.RoundedButton;

public class Menu extends JPanel{
    public JPanel menu;
    public static JButton busqueda, inicio, btnPlayPause;
    
    public Menu(String tipoUsuario){
        //Se crea el panel del lado izquierdo (Menú) que contiene los botones que cambian la vista
        menu = new JPanel();
        menu.setLayout(null);
        menu.setPreferredSize(new Dimension(250, 600));
        menu.setBackground(new Color(100, 149, 237));
        
        JLabel logo = new JLabel(new FlatSVGIcon("imagenes/LogoArriba.svg", 143, 130));
        logo.setBounds(55, 20, 143, 130);

        //Creación y diseño de los botones
        inicio = new RoundedButton("Inicio");
        inicio.setFont(new Font("Poppins", Font.PLAIN, 15));
        inicio.setBackground(Color.WHITE);
        inicio.setForeground(new Color(100, 149, 237));
        inicio.setBounds(50, 170, 150, 30);
        inicio.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));


        busqueda = new RoundedButton("Búsqueda");
        busqueda.setFont(new Font("Poppins", Font.PLAIN, 15));
        busqueda.setBackground(Color.WHITE);
        busqueda.setForeground(new Color(100, 149, 237));
        busqueda.setBounds(50, 214, 150, 30);
        busqueda.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        //Dependiendo si es administrador o socio muestra un botón u otro
        if(tipoUsuario.equals("administrador")){
            JButton agregarLibro = new RoundedButton("Agregar Libro");
            agregarLibro.setFont(new Font("Poppins", Font.PLAIN, 15));
            agregarLibro.setBackground(Color.WHITE);
            agregarLibro.setForeground(new Color(100, 149, 237));
            agregarLibro.setBounds(50, 262, 150, 30);
            agregarLibro.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            agregarLibro.addActionListener((ActionEvent e) -> {
                //Ejecuta el metodo cambiarVista
                App.cambiarVista(new CRUD_Libro(), agregarLibro);
                ButtonSounds.play("/sounds/BotonesMenu.wav");            
            });
            menu.add(agregarLibro);
        }else{
            JButton prestamos = new RoundedButton("Préstamos");
            prestamos.setFont(new Font("Poppins", Font.PLAIN, 15));
            prestamos.setBackground(Color.WHITE);
            prestamos.setForeground(new Color(100, 149, 237));
            prestamos.setBounds(50, 262, 150, 30);
            prestamos.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            
            prestamos.addActionListener((ActionEvent e) -> {
                //Ejecuta el metodo cambiarVista
                App.cambiarVista(new Prestamos(), prestamos);
                ButtonSounds.play("/sounds/BotonesMenu.wav");            
            });
            menu.add(prestamos);
        }
        
        JButton estadisticas = new RoundedButton("Estadísticas");
        estadisticas.setFont(new Font("Poppins", Font.PLAIN, 15));
        estadisticas.setBackground(Color.WHITE);
        estadisticas.setForeground(new Color(100, 149, 237));
        estadisticas.setBounds(50, 306, 150, 30);
        estadisticas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        //Slider de Volumen
        JSlider volumen = new JSlider(0, 100, 50);
        volumen.setPreferredSize(new Dimension(150, 10));
        volumen.setBounds(80, 430, 120, 30);

        btnPlayPause = new JButton("");
        btnPlayPause.setBackground(Color.WHITE);
        btnPlayPause.setForeground(Color.black);
        btnPlayPause.setBorder(BorderFactory.createEmptyBorder());
        btnPlayPause.setBounds(50, 430, 30, 30);
        btnPlayPause.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        actualizarIcono();
        
        JButton logOut = new RoundedButton("Cerrar Sesión");
        logOut.setFont(new Font("Poppins", Font.PLAIN, 15));
        logOut.setBackground(Color.red);
        logOut.setForeground(Color.white);
        logOut.setBounds(50, 490, 150, 30);
        logOut.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        //Sección de usuario como primera vista
        App.cambiarVista(new Usuario(Sesion.getNombre(), Sesion.getUsuario(), Sesion.getContraseña(), Sesion.getFechaCreacion(), Sesion.getFoto()), inicio);

        
        //Cambio de vista al presionar los botones
        inicio.addActionListener((ActionEvent e) -> {
            //Ejecuta el metodo cambiarVista
            App.cambiarVista(new Usuario(Sesion.getNombre(), Sesion.getUsuario(), Sesion.getContraseña(), Sesion.getFechaCreacion(), Sesion.getFoto()), inicio);
            ButtonSounds.play("/sounds/BotonesMenu.wav");                        
        });
        busqueda.addActionListener((ActionEvent e) -> {
            //Ejecuta el metodo cambiarVista
            App.cambiarVista(new Busqueda(), busqueda);
            ButtonSounds.play("/sounds/BotonesMenu.wav");            
        });
        estadisticas.addActionListener((ActionEvent e) -> {
            //Ejecuta el metodo cambiarVista
            App.cambiarVista(new Estadisticas(), estadisticas);
            ButtonSounds.play("/sounds/BotonesMenu.wav");            
        });
        
        MusicPlayer reproductor = new MusicPlayer();
        
        logOut.addActionListener((ActionEvent e) -> {
            //Cierra la sesión
            Sesion.cerrarSesion();
            new LogIn();
            App.getInstancia().cerrar();
            reproductor.detener();
        });
        
        //Inicializamos el reproductor
        try {
            reproductor.cargarMusica("background_music.wav");
            // Sincroniza el slider con el volumen inicial
            volumen.setValue((int)(reproductor.getVolumen() * 100));            
        } catch (Exception error) {
            System.out.println("Error: "+error);
        }

        btnPlayPause.addActionListener(e -> {
            if (reproductor.estaReproduciendo()) {
                reproductor.pausar();
            } else {
                reproductor.reproducir();
            }
            actualizarIcono();
        });
        
        volumen.addChangeListener(e -> {
            float volume = volumen.getValue() / 100f;
            reproductor.setVolumen(volume);
        });

        //Se agregan los botones al panel del menú
        menu.add(logo);
        menu.add(inicio);
        menu.add(busqueda);
        menu.add(estadisticas);
        menu.add(volumen);
        menu.add(btnPlayPause);
        menu.add(logOut);
    }
    
    //Metodo para actualizar Iconos de play y stop
    private void actualizarIcono() {
        btnPlayPause.setText(MusicPlayer.estaReproduciendo() ? "◼" : "▶");
    }
    
}            