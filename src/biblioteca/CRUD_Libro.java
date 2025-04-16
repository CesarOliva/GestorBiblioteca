package biblioteca;

//Se importan las librerias necesarias
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import Conexion.Peticiones;

//"Librerias" necesarias a utilizar
import elementos.PlaceholderTextField;
import elementos.RoundedButton;
import elementos.WindowError;

//Clase CRUD_Libro extendido de JPanel. Panel personalizado
public class CRUD_Libro extends JPanel{
    private JLabel Portada;
    private ImageIcon ruta;
    private PlaceholderTextField Titulo, Autor, Año, Editorial, Genero, ISBN, Paginas;
    private JTextArea Descripcion;
    private JScrollPane scroll;
    private int año, paginas;
    private String rutaWeb;
    private static CRUD_Libro instancia;
    
    public CRUD_Libro(){
        //Configuracion del panel
        setLayout(null);
        setBackground(Color.white);
        
        instancia = this;

        //Creación de los elementos       
        ruta = new ImageIcon(getClass().getResource("/imagenes/addCover.png"));
        Image imgEscalada = ruta.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        Portada = new JLabel(new ImageIcon(imgEscalada));
        Portada.setBounds(50,50,200,300);

        Titulo = new PlaceholderTextField("Titulo", 50);
        Titulo.setFont(new Font("Poppins", Font.PLAIN, 12));        
        Titulo.setBounds(290, 50, 200, 30);
        
        Autor = new PlaceholderTextField("Autor", 50);
        Autor.setFont(new Font("Poppins", Font.PLAIN, 12));
        Autor.setBounds(290, 90, 200, 30);

        Año = new PlaceholderTextField("Año", 5);
        Año.setFont(new Font("Poppins", Font.PLAIN, 12));
        Año.setBounds(290, 130, 200, 30);
        
        Editorial = new PlaceholderTextField("Editorial", 30);
        Editorial.setFont(new Font("Poppins", Font.PLAIN, 12));
        Editorial.setBounds(290, 170, 200, 30);
        
        Genero = new PlaceholderTextField("Género", 500);
        Genero.setFont(new Font("Poppins", Font.PLAIN, 12));
        Genero.setBounds(290, 210, 200, 30);

        Descripcion = new JTextArea();
        Descripcion.setLineWrap(true); // Salto de línea automático
        Descripcion.setWrapStyleWord(true); // Corta palabras completas
        Descripcion.setFont(new Font("Poppins", Font.PLAIN, 12));
        Descripcion.setBounds(0,0, 250, 100);
        Descripcion.setBackground(new Color(217, 217, 217));
        Descripcion.setMargin(new Insets(10, 10, 10, 10));
        
        //Scroll para la descriçión si es necesario
        scroll = new JScrollPane(Descripcion);
        scroll.setBounds(290, 250, 250, 100);
        
        ISBN = new PlaceholderTextField("ISBN", 20);
        ISBN.setFont(new Font("Poppins", Font.PLAIN, 12));
        ISBN.setBounds(50, 370, 200, 30);

        Paginas = new PlaceholderTextField("Número de paginas", 4);
        Paginas.setFont(new Font("Poppins", Font.PLAIN, 12));
        Paginas.setBounds(50, 410, 200, 30);
        
        JButton listo = new RoundedButton("Agregar"); 
        listo.setFont(new Font("Poppins", Font.PLAIN, 14));
        listo.setForeground(Color.white);
        listo.setBackground(new Color(100, 149, 237));
        listo.setBounds(50, 450, 100, 30);
        
        Descripcion.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                scroll.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
            }       
        }); 
        
        //Funcionalidad al clickear en la foto
        Portada.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                chooseFile();
            }
        });
        
        resetPlaceholder(Año);
        resetPlaceholder(Autor);
        resetPlaceholder(Genero);
        resetPlaceholder(Paginas);
        
        //Funcionalidad del botón
        listo.addActionListener((ActionEvent e)->{
            validarContenido();
        });

        add(Portada);
        add(Titulo);
        add(Autor);
        add(ISBN);
        add(Año);
        add(Editorial);
        add(scroll);
        add(Genero);
        add(Paginas);
        add(listo);
    }
    
    private void validarContenido(){
        boolean correcto = true;
        
        //Valida que todos los elementos estén completos
        if(ISBN.validarContenido()){ISBN.setForeground(Color.red); correcto=false;}
        if(Titulo.validarContenido()){Titulo.setForeground(Color.red); correcto=false;}
        if(Autor.validarContenido()){Autor.setForeground(Color.red); correcto=false;}
        if(Año.validarContenido()){Año.setForeground(Color.red); correcto=false;}
        if(Editorial.validarContenido()){Editorial.setForeground(Color.red); correcto=false;}
        if(Genero.validarContenido()){Genero.setForeground(Color.red); correcto=false;}
        if(Paginas.validarContenido()){Paginas.setForeground(Color.red); correcto=false;}
        if(Descripcion.getText().equals("")){scroll.setBorder(BorderFactory.createLineBorder(Color.RED, 1)); correcto=false;}

        //Detiene la validacion
        if(!correcto) return;

        //Obtiene los valores
        String isbn = ISBN.getText().trim();
        String titulo = Titulo.getText().trim();
        String autor = Autor.getText().trim();
        String portada = rutaWeb;
        String editorial = Editorial.getText().trim();
        String genero = Genero.getText().trim();
        String descripcion = Descripcion.getText().trim();

        //Verifica si el autor y el genero no tienen numeros
        if(!autor.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")){
            Autor.setForeground(Color.red);
            correcto=false;
        }
        if(!genero.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")){
            Genero.setForeground(Color.red); 
            correcto=false;
        }

        //Validación de año y paginas
        try{
            año = Integer.parseInt(Año.getText());
            if(año>LocalDate.now().getYear()){
                Año.setForeground(Color.red);                       
                correcto = false;
            }
        }catch(NumberFormatException ex){
            Año.setForeground(Color.red);
            correcto=false;
        }
        
        try{
            paginas = Integer.parseInt(Paginas.getText());
            if(paginas<=0){
                Paginas.setForeground(Color.red);
                correcto = false;
            }
        }catch(NumberFormatException ex){
            Paginas.setForeground(Color.red);
            correcto=false;
        }
        
        //Detiene la validación
        if(!correcto) return;
        
        //Verifica que tenga portada
        if(rutaWeb==null || rutaWeb==""){
            new WindowError("Selecciona una imagen");
            correcto = false;
        }
        
        if(correcto){
            Peticiones.agregarLibro(isbn, titulo, autor, portada, año, editorial, genero, paginas, descripcion);
        }
    }
    
    private void chooseFile(){
        //Selección de archivo
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una imagen");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes JPG, PNG, WEBP", "jpg", "jpeg", "png", "webp");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(filtro);
        
        int resultado = fileChooser.showOpenDialog(null);
        
        //Si se selecciona un archivo
        if (resultado == JFileChooser.APPROVE_OPTION) {
            //Obtiene el archivo
            File archivoSeleccionado = fileChooser.getSelectedFile();
            
            //Ruta donde se guardará la imagen
            String destinoPath = "C:/xampp/htdocs/Imagenes/"+Titulo.getText().replace(" ", "")+".jpg";
            File destino = new File(destinoPath);
            
            try{
                Files.copy(archivoSeleccionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                rutaWeb = "http://localhost/Imagenes/"+Titulo.getText().replace(" ", "")+".jpg";
            }catch(IOException ex){
                ex.printStackTrace();
                System.out.println("Error al copiar la imagen");
            }

            //Reemplazar la imagen de selección por la imagen seleccionada
            Image imagen = new ImageIcon(destinoPath).getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            Portada.setIcon(new ImageIcon(imagen));
            repaint();
        }
    }
    
    private void resetPlaceholder(PlaceholderTextField tf){
        tf.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ((PlaceholderTextField)tf).resetPlaceholder();
            }       
        });        
    }    
    
    public void limpiarCampos(){
        ruta = new ImageIcon(getClass().getResource("/imagenes/addCover.png"));
        Image imagen = ruta.getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        Portada.setIcon(new ImageIcon(imagen));
        rutaWeb = "";
        
        Titulo.clear();
        Autor.clear();
        ISBN.clear();
        Editorial.clear();
        Año.clear();
        Genero.clear();
        Paginas.clear();
        Descripcion.setText("");
    }
    
    public static CRUD_Libro getInstancia(){
        return instancia;
    }
}