package biblioteca;

//Se importan las librerias necesarias
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import Conexion.Peticiones;
import Conexion.Sesion;

//"Librerias" personalizadas a importar
import elementos.RoundedButton;
import elementos.confirmationWindow;

//Clase LibroIndividual extendida de JPanel. Panel personalizado
public class LibroIndividual extends JPanel{
    JButton editar, eliminar, rentar;
    
    public LibroIndividual(int IdLibro, String isbn, String titulo, String autor, String portada, String año, 
        String editorial, String genero, String paginas, String descripcion, String admin){
        
        //Configuracion del panel
        setLayout(null);
        setBackground(Color.white);

        
        //Creación de los elementos
        JLabel Portada = new JLabel();
        
        //Intente cargar la imagen, de lo contrario cargar una imagen default
        File archivo = new File(portada);
        if (archivo.exists()) {
            Image imagen = new ImageIcon(portada).getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            Portada.setIcon(new ImageIcon(imagen));
        }else{
            Image imagen = new ImageIcon(getClass().getResource("/imagenes/addCover.png")).getImage()
                        .getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            Portada.setIcon(new ImageIcon(imagen));
        }
        Portada.setBounds(50, 50, 200, 300);
        
        JLabel Titulo = new JLabel(titulo);
        Titulo.setFont(new Font("Poppins", Font.PLAIN, 15));        
        Titulo.setBounds(290, 50, 200, 30);
        
        JLabel Autor = new JLabel(autor);
        Autor.setFont(new Font("Poppins", Font.BOLD, 15));
        Autor.setBounds(290, 80, 200, 30);

        JLabel Año = new JLabel("Año de publicación: "+año);
        Año.setFont(new Font("Poppins", Font.PLAIN, 15));
        Año.setBounds(290, 110, 200, 30);
        
        JLabel Editorial = new JLabel(editorial);
        Editorial.setFont(new Font("Poppins", Font.BOLD, 15));
        Editorial.setBounds(290, 140, 200, 30);
        
        JLabel Genero = new JLabel("Genero: "+genero);
        Genero.setFont(new Font("Poppins", Font.PLAIN, 15));
        Genero.setBounds(290, 170, 200, 30);

        JTextArea Descripcion = new JTextArea();
        Descripcion.setLineWrap(true); // Salto de línea automático
        Descripcion.setWrapStyleWord(true); // Corta palabras completas
        Descripcion.setFont(new Font("Poppins", Font.PLAIN, 14));
        Descripcion.setBounds(285, 200, 300, 200);
        Descripcion.setBackground(Color.white);
        Descripcion.setEditable(false);
        Descripcion.setFocusable(false);
        Descripcion.setText(descripcion);

        JLabel agregado = new JLabel("Agregado por: "+admin);
        agregado.setFont(new Font("Poppins", Font.PLAIN, 15));
        agregado.setBounds(50, 360, 200, 30);

        JLabel ISBN = new JLabel("ISBN: "+isbn);
        ISBN.setFont(new Font("Poppins", Font.PLAIN, 15));
        ISBN.setBounds(50, 390, 200, 30);

        JLabel Paginas = new JLabel("Número de paginas: "+paginas);
        Paginas.setFont(new Font("Poppins", Font.PLAIN, 15));
        Paginas.setBounds(50, 420, 200, 30);

        editar = new RoundedButton("Editar"); 
        editar.setFont(new Font("Poppins", Font.PLAIN, 15));
        editar.setForeground(Color.white);
        editar.setBackground(new Color(100, 149, 237));
        editar.setBounds(50, 460, 100, 30);       

        eliminar = new RoundedButton("Eliminar"); 
        eliminar.setFont(new Font("Poppins", Font.PLAIN, 15));
        eliminar.setForeground(Color.white);
        eliminar.setBackground(Color.red);
        eliminar.setBounds(180, 460, 100, 30);                
        
        rentar = new RoundedButton("Rentar"); 
        rentar.setFont(new Font("Poppins", Font.PLAIN, 15));
        rentar.setForeground(Color.white);
        rentar.setBackground(new Color(100, 149, 237));
        rentar.setBounds(50, 460, 100, 30);       
        
        if(Sesion.getTipo().equals("administrador")){
            editar.setVisible(true);
            eliminar.setVisible(true);
            rentar.setVisible(false);
        }else{
            editar.setVisible(false);
            eliminar.setVisible(false);
            rentar.setVisible(true);
        }
        
        
        //Funcionalidad de los elementos
        Autor.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Peticiones.obtenerAutor(autor);
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
        
        Editorial.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                Peticiones.obtenerEditorial(editorial);
            }
            @Override
            public void mouseEntered(MouseEvent e) {
                Editorial.setCursor(new Cursor(Cursor.HAND_CURSOR));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                Editorial.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
        
        agregado.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                System.out.println(admin);
                //new Usuario();
            }
        });
        
        editar.addActionListener(e->{
            
        });
        
        eliminar.addActionListener(e->{
            new confirmationWindow("Esta acción es permanente. Escribe tu contraseña para eliminar el libro",
                () -> Peticiones.eliminarLibro(IdLibro));
        });
        
        rentar.addActionListener(e->{
            Peticiones.PrestarLibroIndividual(Sesion.getIdUsuario(), IdLibro, titulo); 
        });
        
        //Se agregan los elementos al panel
        add(Portada);
        add(Titulo);
        add(Autor);
        add(ISBN);
        add(Año);
        add(Editorial);
        add(Descripcion);
        add(Genero);
        add(agregado);
        add(Paginas);
        add(editar);
        add(rentar);
        add(eliminar);
    }
}