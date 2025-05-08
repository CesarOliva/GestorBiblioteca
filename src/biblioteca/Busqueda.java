package biblioteca;

//Se importan las librerias necesarias 
import javax.swing.*;
import java.awt.*;
import javax.swing.event.DocumentListener;
import javax.swing.event.DocumentEvent;
import java.util.ArrayList;
import Conexion.Peticiones;
import static biblioteca.Menu.inicio;
import elementos.ButtonSounds;
import java.io.File;

//"Libererias" personalizadas a importar
import elementos.PlaceholderTextField;
import elementos.RoundedButton;
import elementos.CustomScroll;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Clase Busqueda extendida de JPanel. Panel personalizado
public class Busqueda extends JPanel{
    private PlaceholderTextField busquedaTF;
    private JPanel panelResultados;
    private Timer timer;
    private JScrollPane scrollPane;
    private int offset=0, limite=10;

    public Busqueda() {
        //Configuración del panel
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Panel de búsqueda
        JPanel panelBusqueda = new JPanel(null);
        panelBusqueda.setPreferredSize(new Dimension(550, 80));
        panelBusqueda.setBackground(Color.white);
        
        //Creación de los elementos
        busquedaTF = new PlaceholderTextField("Buscar libros por titulo o autor", 100);
        busquedaTF.setFont(new Font("Poppins", Font.PLAIN, 14));
        busquedaTF.setBounds(100, 20, 400, 40);
        
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
                mostrarLibros(Peticiones.obtenerLibrosRecientes(limite, offset));
            } else {
                String texto = busquedaTF.getText().trim();
                mostrarLibros(Peticiones.busquedaLibro(texto));
            }
        });
        timer.setRepeats(false);        
        
        //Se agregan los elementos al panel de busqueda
        panelBusqueda.add(busquedaTF);

        // Creacion del panel de resultados
        panelResultados = new JPanel();
        panelResultados.setLayout(new BoxLayout(panelResultados, BoxLayout.Y_AXIS));
        panelResultados.setBackground(Color.white);
        
        //Scrollpane por si no cabe la busqueda en el panel
        scrollPane = new CustomScroll(panelResultados);
        scrollPane.setPreferredSize(new Dimension(620, 600));

        // Cargar los 10 libros mas recientes al iniciar
        mostrarLibros(Peticiones.obtenerLibrosRecientes(limite, offset));

        //Agrega los paneles al panel principal
        add(panelBusqueda, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
    
    //Mostrar todos los libros o la busqueda
    private void mostrarLibros(ArrayList<LibroBusqueda> libros) {
        //Elimina el contenido del panel
        panelResultados.removeAll();
        
        //Si no encuentra libros
        if(libros.isEmpty()){
            JLabel vacio = new JLabel("No se encontraron busquedas coincidentes");
            vacio.setFont(new Font("Poppins", Font.PLAIN, 20));
            vacio.setAlignmentX(Component.CENTER_ALIGNMENT);
            vacio.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            
            panelResultados.add(vacio);
        }
                
        //Obtiene la lista de los libros
        for(LibroBusqueda libro : libros){
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
            Autor.setBounds(153, 30, 200, 30);

            JTextArea Descripcion = new JTextArea(libro.getDescripcion());
            Descripcion.setFont(new Font("Poppins", Font.PLAIN, 12));
            Descripcion.setEditable(false);
            Descripcion.setFocusable(false);
            Descripcion.setLineWrap(true); // Salto de línea automático
            Descripcion.setWrapStyleWord(true); // Corta palabras completas
            Descripcion.setBounds(148, 57, 330, 120);

            JButton verMas = new RoundedButton("Ver mas");
            verMas.setForeground(Color.white);
            verMas.setBackground(new Color(100, 149, 237));
            verMas.setFont(new Font("Poppins", Font.PLAIN, 14));
            verMas.setBounds(153, 180, 100, 30);
            verMas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            

            int idLibro = libro.getId();
            verMas.addActionListener(e -> {
                Peticiones.ObtenerLibroIndividual(idLibro);
                 ButtonSounds.play("/sounds/busqueda.wav");
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
            card.add(Descripcion);
            card.add(verMas);

            //Agrega el contenido del card a la fila y la fina al panel
            fila.add(card);
            panelResultados.add(fila);
        }
        
        JPanel paginacion = new JPanel();
        paginacion.setLayout(null);
        paginacion.setBackground(Color.white);
        paginacion.setPreferredSize(new Dimension(550, 60));
        
        //Botones para paginación      
        JButton anterior;
        anterior = new RoundedButton("Anterior");
        anterior.setForeground(Color.white);
        anterior.setBackground(new Color(100, 149, 237));
        anterior.setFont(new Font("Poppins", Font.PLAIN, 12));
        anterior.setBounds(65, 0, 100, 40);
        anterior.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        if(offset>0){
            anterior.addActionListener(e->{
                ButtonSounds.play("/sounds/CambioDePagina.wav");                                            
                //Posición de busqueda de libro
               offset-=limite;
               if(offset<0){
                   offset=0;
               }
               
               mostrarLibros(Peticiones.obtenerLibrosRecientes(limite, offset));
            });            
        }
        
        JButton siguiente = new RoundedButton("Siguiente");
        siguiente.setForeground(Color.white);
        siguiente.setBackground(new Color(100, 149, 237));
        siguiente.setFont(new Font("Poppins", Font.PLAIN, 12));
        siguiente.setBounds(465, 0, 100, 40);
        siguiente.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        
        siguiente.addActionListener(e->{
            ButtonSounds.play("/sounds/CambioDePagina.wav");            
            //Posición de busqueda de libro
            offset+=limite;
            mostrarLibros(Peticiones.obtenerLibrosRecientes(limite, offset));
        });
        
        // Desactiva botón anterior
        anterior.setEnabled(offset > 0);

        // Desactiva botón siguiente
        siguiente.setEnabled(offset + limite < Peticiones.contarLibros());
        
        if(!busquedaTF.validarContenido()){
            anterior.setEnabled(false);
            siguiente.setEnabled(false);
        }
        
        //Agregar los botones al panel de paginacion y el panel al panel de resultados
        paginacion.add(siguiente);
        paginacion.add(anterior);
        
        panelResultados.add(paginacion);
        
        panelResultados.revalidate();
        panelResultados.repaint();   
        
        SwingUtilities.invokeLater(() -> {
            //Manda el scroll al inicio
            scrollPane.getVerticalScrollBar().setValue(0);
        });
    }
}