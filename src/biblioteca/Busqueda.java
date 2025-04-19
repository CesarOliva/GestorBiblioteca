package biblioteca;

import java.sql.*;
import Conexion.Conexion;

//Se importan las librerias necesarias 
import javax.swing.*;
import java.awt.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.awt.event.ActionEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import Conexion.Peticiones;

//"Libererias" personalizadas a importar
import elementos.PlaceholderTextField;
import elementos.RoundedButton;
import elementos.CustomScroll;

//Clase Busqueda extendida de JPanel. Panel personalizado
public class Busqueda extends JPanel{
    private PlaceholderTextField busquedaTF;
    private JPanel panelResultados;
    private Timer timer;
    private JScrollPane scrollPane;

    public Busqueda() {
        //Configuración del panel
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(null);
        panelBusqueda.setPreferredSize(new Dimension(550, 120));
        panelBusqueda.setBackground(Color.white);
        
        //Creación de los elementos
        busquedaTF = new PlaceholderTextField("Buscar libros por titulo", 100);
        busquedaTF.setFont(new Font("Poppins", Font.PLAIN, 14));
        busquedaTF.setBounds(100, 30, 400, 40);
        
        // Configurar el DocumentListener para búsqueda en tiempo real
        busquedaTF.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                timer.restart();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                timer.restart();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                timer.restart();
            }
        });

        // Timer para retrasar la búsqueda mientras se escribe
        timer = new Timer(300, e -> {
            if (busquedaTF.validarContenido()) {
                cargarTodosLosLibros();
            } else {
                String texto = busquedaTF.getText().trim();
                mostrarBusqueda(texto);
            }
        });
        timer.setRepeats(false);        
        
        //Se agregan los elementos al panel de busqueda
        panelBusqueda.add(busquedaTF);

        // Creacion del panel de resultados
        panelResultados = new JPanel();
        panelResultados.setLayout(new GridLayout(0, 1, 0, 20));
        panelResultados.setBackground(Color.white);
        
        //Scrollpane por si no cabe la busqueda en el panel
        scrollPane = new CustomScroll(panelResultados);

        // Cargar todos los libros al iniciar
        cargarTodosLosLibros();

        //Agrega los paneles al panel principal
        add(panelBusqueda, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    //Muestra todos los libros
    private void cargarTodosLosLibros() {
        ArrayList<LibroBusqueda> libros = Peticiones.obtenerLibrosRecientes();
        
        //Elimina el contenido del panel
        panelResultados.removeAll();
                
        //Obtiene la lista de los libros
        for(LibroBusqueda libro : libros){
            //Fila de cada libro
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            fila.setBackground(Color.white);

            //Configuración del panel
            JPanel card = new JPanel();
            card.setLayout(null);
            card.setForeground(Color.gray);
            card.setPreferredSize(new Dimension(500, 220));
            card.setBounds(50, 0, 500, 220);

            //Creación de los elementos
            String portada = libro.getPortada();
            Image imagen = new ImageIcon(portada).getImage().getScaledInstance(133, 200, Image.SCALE_SMOOTH);
            JLabel Portada = new JLabel(new ImageIcon(imagen));
            Portada.setBounds(10,12,133,200);

            JLabel Titulo = new JLabel(libro.getTitulo());
            Titulo.setFont(new Font("Poppins", Font.PLAIN, 14));        
            Titulo.setBounds(150, 0, 200, 30);

            JLabel Autor = new JLabel(libro.getAutor());
            Autor.setFont(new Font("Poppins", Font.PLAIN, 14));
            Autor.setBounds(150, 30, 200, 30);

            JTextArea Descripcion = new JTextArea(libro.getDescripcion());
            Descripcion.setFont(new Font("Poppins", Font.PLAIN, 12));
            Descripcion.setEditable(false);
            Descripcion.setFocusable(false);
            Descripcion.setBackground(Color.white);
            Descripcion.setLineWrap(true); // Salto de línea automático
            Descripcion.setWrapStyleWord(true); // Corta palabras completas
            Descripcion.setBounds(150, 60, 280, 60);

            JButton verMas = new RoundedButton("Ver mas");
            verMas.setForeground(Color.white);
            verMas.setBackground(new Color(100, 149, 237));
            verMas.setFont(new Font("Poppins", Font.PLAIN, 14));
            verMas.setBounds(150, 120, 100, 30);

            //agrega el contenido al card
            card.add(Portada);
            card.add(Titulo);
            card.add(Autor);
            card.add(Descripcion);
            card.add(verMas);

            //Agrega el contenido del card a la fila y la fina al panel
            fila.add(card);
            panelResultados.add(fila);
        }
        panelResultados.revalidate();
        panelResultados.repaint();   
        
        SwingUtilities.invokeLater(() -> {
            //Manda el scroll al inicio
            scrollPane.getVerticalScrollBar().setValue(0);
        });
    }
    
    private void mostrarBusqueda(String texto){
        ArrayList<LibroBusqueda> libros = Peticiones.busquedaLibro(texto);
        
        //Elimina el contenido del panel
        panelResultados.removeAll();
                
        //Obtiene la lista de los libros
        for(LibroBusqueda libro : libros){
            //Fila de cada libro
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            fila.setBackground(Color.white);

            //Configuración del panel
            JPanel card = new JPanel();
            card.setLayout(null);
            card.setForeground(Color.gray);
            card.setPreferredSize(new Dimension(500, 220));
            card.setBounds(50, 0, 500, 220);

            //Creación de los elementos
            String portada = libro.getPortada();
            Image imagen = new ImageIcon(portada).getImage().getScaledInstance(133, 200, Image.SCALE_SMOOTH);
            JLabel Portada = new JLabel(new ImageIcon(imagen));
            Portada.setBounds(10,12,133,200);

            JLabel Titulo = new JLabel(libro.getTitulo());
            Titulo.setFont(new Font("Poppins", Font.PLAIN, 14));        
            Titulo.setBounds(150, 0, 200, 30);

            JLabel Autor = new JLabel(libro.getAutor());
            Autor.setFont(new Font("Poppins", Font.PLAIN, 14));
            Autor.setBounds(150, 30, 200, 30);

            JTextArea Descripcion = new JTextArea(libro.getDescripcion());
            Descripcion.setFont(new Font("Poppins", Font.PLAIN, 12));
            Descripcion.setEditable(false);
            Descripcion.setFocusable(false);
            Descripcion.setBackground(Color.white);
            Descripcion.setLineWrap(true); // Salto de línea automático
            Descripcion.setWrapStyleWord(true); // Corta palabras completas
            Descripcion.setBounds(150, 60, 280, 60);

            JButton verMas = new RoundedButton("Ver mas");
            verMas.setForeground(Color.white);
            verMas.setBackground(new Color(100, 149, 237));
            verMas.setFont(new Font("Poppins", Font.PLAIN, 14));
            verMas.setBounds(150, 120, 100, 30);

            //agrega el contenido al card
            card.add(Portada);
            card.add(Titulo);
            card.add(Autor);
            card.add(Descripcion);
            card.add(verMas);

            //Agrega el contenido del card a la fila y la fina al panel
            fila.add(card);
            panelResultados.add(fila);
        }
        panelResultados.revalidate();
        panelResultados.repaint();   
        
        SwingUtilities.invokeLater(() -> {
            //Manda el scroll al inicio
            scrollPane.getVerticalScrollBar().setValue(0);
        });      
    }
}