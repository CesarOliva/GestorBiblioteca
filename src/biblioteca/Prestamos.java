package biblioteca;

//Se importan las librerias necesarias
import java.awt.*;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.ArrayList;
import java.io.File;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import Conexion.Peticiones;
import Conexion.Sesion;
import elementos.ButtonSounds;

//"Librerias" necesarias a importar
import elementos.CustomScroll;
import elementos.PlaceholderTextField;
import elementos.RoundedButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Prestamos extends JPanel{
    private Timer timer;
    private JPanel panelResultados;
    private JScrollPane scrollPane;
    
    public Prestamos(){
        setLayout(new BorderLayout());
        setBackground(Color.white);
        
        JPanel panelDatos = new JPanel(null);
        panelDatos.setPreferredSize(new Dimension(550, 80));
        panelDatos.setBackground(Color.white);
    
        //Creacion de los elementos
        PlaceholderTextField busqueda = new PlaceholderTextField("Busca un prestamo", 50);
        busqueda.setFont(new Font("Poppins", Font.PLAIN, 14));
        busqueda.setBounds(100, 20, 400, 40);
        busqueda.setEnabled(false);
        
        // Configurar el DocumentListener para búsqueda en tiempo real
        busqueda.getDocument().addDocumentListener(new DocumentListener() {
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
            if (busqueda.validarContenido()) {
                mostrarLibros(Peticiones.obtenerPrestamos(Sesion.getIdUsuario()));
            } else {
                String texto = busqueda.getText().trim();
                //mostrarLibros(Peticiones.busquedaPrestamo(texto));
            }
        });
        timer.setRepeats(false);        
               
        panelDatos.add(busqueda);
        
        //Panel de libros obtenidos        
        panelResultados = new JPanel();
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        panelResultados.setBackground(Color.white);
        
        mostrarLibros(Peticiones.obtenerPrestamos(Sesion.getIdUsuario()));
        
        //Scrollpane por si no cabe la busqueda en el panel
        scrollPane = new CustomScroll(panelResultados);
        
        add(panelDatos, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        
    }
    
    private void mostrarLibros(ArrayList<PrestamoBusqueda> prestamos) {
        //Elimina el contenido del panel
        panelResultados.removeAll();
                
        //Obtiene la lista de los libros
        //Obtiene la lista de los libros
        for(PrestamoBusqueda libro : prestamos){
            //Fila de cada libro
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            fila.setBackground(Color.white);
            fila.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            //Configuración del panel
            JPanel card = new JPanel();
            card.setLayout(null);
            card.setPreferredSize(new Dimension(500, 220));
            card.setBounds(50, 0, 500, 220);

            //Creación de los elementos
            String portada = libro.getPortada();
            
            //Intente cargar la imagen, de lo contrario cargar una imagen default
            File archivo = new File(portada);
            JLabel Portada = new JLabel();
            if (archivo.exists()) {
                Image imagen = new ImageIcon(portada).getImage().getScaledInstance(133, 200, Image.SCALE_SMOOTH);
                Portada.setIcon(new ImageIcon(imagen));
            }else{
                Image imagen = new ImageIcon(getClass().getResource("/imagenes/addCover.png")).getImage()
                            .getScaledInstance(133, 200, Image.SCALE_SMOOTH);
                Portada.setIcon(new ImageIcon(imagen));
            }
            Portada.setBounds(10,10,133,200);
            

            JLabel Titulo = new JLabel(libro.getTitulo());
            Titulo.setFont(new Font("Poppins", Font.PLAIN, 14));        
            Titulo.setBounds(153, 10, 400, 20);

            JLabel Autor = new JLabel(libro.getAutor());
            Autor.setFont(new Font("Poppins", Font.BOLD, 14));
            Autor.setBounds(153, 40, 200, 20);

            JLabel FechaPrestamo = new JLabel("Fecha de prestamo: "+libro.getFechaPrestamo().toString());
            FechaPrestamo.setFont(new Font("Poppins", Font.PLAIN, 14));
            FechaPrestamo.setBounds(153, 70, 400, 20);

            JLabel FechaDevolucion = new JLabel("A devolver antes del: "+libro.getFechaDevolucion().toString());
            FechaDevolucion.setFont(new Font("Poppins", Font.PLAIN, 14));
            FechaDevolucion.setBounds(153, 100, 400, 20);

            long diferenciaDias = ChronoUnit.DAYS.between(LocalDate.now(), libro.getFechaDevolucion());

            JLabel estado = new JLabel("");
            estado.setFont(new Font("Poppins", Font.BOLD, 14));
            estado.setBounds(153, 130, 400, 30);
            
            if(!libro.getEstado()){
                if(diferenciaDias>0){
                    estado.setText("Tienes "+diferenciaDias+" dias para devolverlo");
                }else{
                    estado.setText("Entrega atrasada por "+Math.abs(diferenciaDias)+" dias");
                    estado.setForeground(Color.red);
                }                
            }else{
                estado.setText("Libro devuelto el: "+Peticiones.obtenerDevolucion(libro.getIdPrestamo()));
            }

            JButton verMas = new RoundedButton("Ver mas");
            verMas.setForeground(Color.white);
            verMas.setBackground(new Color(100, 149, 237));
            verMas.setFont(new Font("Poppins", Font.PLAIN, 14));
            verMas.setBounds(153, 180, 100, 30);
            verMas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            verMas.addActionListener(e -> {
    ButtonSounds.play("/sounds/BotonesMenu.wav");
});

            int idLibro = libro.getIdLibro();
            verMas.addActionListener(e -> {
                Peticiones.ObtenerLibroIndividual(idLibro);
            });
            
            Autor.addMouseListener(new MouseAdapter(){
                @Override
                public void mouseClicked(MouseEvent e){
                    Peticiones.obtenerAutor(libro.getAutor());
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    Autor.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    Autor.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });


            //agrega el contenido al card
            card.add(Portada);
            card.add(Titulo);
            card.add(Autor);
            card.add(FechaPrestamo);
            card.add(FechaDevolucion);
            card.add(estado);
            card.add(verMas);

            //Agrega el contenido del card a la fila y la fina al panel
            fila.add(card);
            panelResultados.add(fila);
        }

        panelResultados.revalidate();
        panelResultados.repaint();

        SwingUtilities.invokeLater(() -> {
            scrollPane.getVerticalScrollBar().setValue(0);
        });
    }
}