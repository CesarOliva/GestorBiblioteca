package biblioteca;

//Se importan las librerias necesarias
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import javax.swing.filechooser.FileNameExtensionFilter;

import Conexion.Peticiones;
import Conexion.Sesion;

//"Librerias" personalizadas a importar
import elementos.RoundedButton;
import elementos.ConfirmationWindow;
import elementos.PlaceholderTextField;
import elementos.WindowError;
import java.time.LocalDate;

//Clase LibroIndividual extendida de JPanel. Panel personalizado
public class LibroIndividual extends JPanel{
    private JButton editar, eliminar, rentar;
    private int IdLibro, añoInt, paginasInt;
    private String isbn, titulo, autor, portada, año, editorial, genero, paginas, descripcion, admin;
    private JPanel panelVista, panelEditar;
    private JLabel Portada;
    private PlaceholderTextField Titulo, Autor, Año, Editorial, Genero, ISBN, Paginas;
    private JTextArea Descripcion;    
    private JScrollPane scroll;
    
    public LibroIndividual(int IdLibro, String isbn, String titulo, String autor, String portada, String año, 
        String editorial, String genero, String paginas, String descripcion, String admin){

        //Configuracion del panel
        setLayout(null);
        setBackground(Color.white);

        this.IdLibro = IdLibro;
        this.isbn = isbn;
        this.titulo = titulo;
        this.autor = autor;
        this.portada = portada;
        this.año = año;
        this.editorial = editorial;
        this.genero = genero;
        this.paginas = paginas;
        this.descripcion = descripcion;
        this.admin = admin;
        
        panelVista = new JPanel(null);
        panelVista.setBounds(0,0,650, 600);
        panelVista.setBackground(Color.white);
        
        //Creación de los elementos
        Portada = new JLabel();
        
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
        Titulo.setBounds(290, 50, 300, 30);
        
        JLabel Autor = new JLabel(autor);
        Autor.setFont(new Font("Poppins", Font.BOLD, 15));
        Autor.setBounds(290, 80, 300, 30);

        JLabel Año = new JLabel("Año de publicación: "+año);
        Año.setFont(new Font("Poppins", Font.PLAIN, 15));
        Año.setBounds(290, 110, 200, 30);
        
        JLabel Editorial = new JLabel("Editorial: "+editorial);
        Editorial.setFont(new Font("Poppins", Font.BOLD, 15));
        Editorial.setBounds(290, 140, 300, 30);
        
        JLabel Genero = new JLabel("Genero: "+genero);
        Genero.setFont(new Font("Poppins", Font.PLAIN, 15));
        Genero.setBounds(290, 170, 300, 30);

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
            mostrarEditar();
        });
        
        eliminar.addActionListener(e->{
            new ConfirmationWindow("Esta acción es permanente. Escribe tu contraseña para eliminar el libro",
                () -> Peticiones.eliminarLibro(IdLibro));
        });
        
        rentar.addActionListener(e->{
            Peticiones.PrestamoLibro(Sesion.getIdUsuario(), IdLibro, titulo); 
        });
        
        panelEditar = new JPanel(null);
        panelEditar.setBounds(0,0,650, 600);
        panelEditar.setBackground(Color.white);
        
        //Se agregan los elementos al panel
        panelVista.add(Portada);
        panelVista.add(Titulo);
        panelVista.add(Autor);
        panelVista.add(ISBN);
        panelVista.add(Año);
        panelVista.add(Editorial);
        panelVista.add(Descripcion);
        panelVista.add(Genero);
        panelVista.add(agregado);
        panelVista.add(Paginas);
        panelVista.add(editar);
        panelVista.add(rentar);
        panelVista.add(eliminar);
        
        add(panelVista);
        
        panelVista.setVisible(true);
        panelEditar.setVisible(false);
    }
                
    public void mostrarEditar(){
        panelVista.setVisible(false);
              
        //Creación de los elementos
        Portada = new JLabel();
        
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
        
        Titulo = new PlaceholderTextField(titulo, 100);
        Titulo.setFont(new Font("Poppins", Font.PLAIN, 12));        
        Titulo.setBounds(290, 50, 200, 30);
        
        Autor = new PlaceholderTextField(autor, 100);
        Autor.setFont(new Font("Poppins", Font.PLAIN, 12));
        Autor.setBounds(290, 90, 200, 30);
        Autor.setEnabled(false);

        Año = new PlaceholderTextField(año, 7);
        Año.setFont(new Font("Poppins", Font.PLAIN, 12));
        Año.setBounds(290, 130, 200, 30);
        
        Editorial = new PlaceholderTextField(editorial, 100);
        Editorial.setFont(new Font("Poppins", Font.PLAIN, 12));
        Editorial.setBounds(290, 170, 200, 30);
        Editorial.setEnabled(false);
        
        Genero = new PlaceholderTextField(genero, 100);
        Genero.setFont(new Font("Poppins", Font.PLAIN, 12));
        Genero.setBounds(290, 210, 200, 30);
        Genero.setEnabled(false);

        Descripcion = new JTextArea();
        Descripcion.setLineWrap(true); // Salto de línea automático
        Descripcion.setWrapStyleWord(true); // Corta palabras completas
        Descripcion.setFont(new Font("Poppins", Font.PLAIN, 12));
        Descripcion.setBounds(0, 0, 250, 100);
        Descripcion.setBackground(new Color(217, 217, 217));
        Descripcion.setMargin(new Insets(10, 10, 10, 10));
        Descripcion.setText(descripcion);
        
        //Scroll para la descriçión si es necesario
        scroll = new JScrollPane(Descripcion);
        scroll.setBounds(290, 250, 250, 100);

        ISBN = new PlaceholderTextField(isbn, 100);
        ISBN.setFont(new Font("Poppins", Font.PLAIN, 12));
        ISBN.setBounds(50, 370, 200, 30);

        Paginas = new PlaceholderTextField(paginas, 10);
        Paginas.setFont(new Font("Poppins", Font.PLAIN, 12));
        Paginas.setBounds(50, 410, 200, 30);

        JButton actualizar = new RoundedButton("Actualizar"); 
        actualizar.setFont(new Font("Poppins", Font.PLAIN, 14));
        actualizar.setForeground(Color.white);
        actualizar.setBackground(new Color(100, 149, 237));
        actualizar.setBounds(50, 450, 100, 30);  

        JButton cancelar = new RoundedButton("Cancelar"); 
        cancelar.setFont(new Font("Poppins", Font.PLAIN, 14));
        cancelar.setForeground(Color.white);
        cancelar.setBackground(Color.red);
        cancelar.setBounds(180, 450, 100, 30);   
        
        actualizar.addActionListener(e->{
            validarContenido();
        });
        
        cancelar.addActionListener(e->{
            panelEditar.setVisible(false);
            panelVista.setVisible(true);
            
            Peticiones.ObtenerLibroIndividual(IdLibro);
        });
        
        //Funcionalidad al clickear en la foto
        Portada.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                chooseFile();
            }
        });
        
        panelEditar.add(Portada);
        panelEditar.add(Titulo);
        panelEditar.add(Autor);
        panelEditar.add(ISBN);
        panelEditar.add(Año);
        panelEditar.add(Editorial);
        panelEditar.add(scroll);
        panelEditar.add(Genero);
        panelEditar.add(Paginas);
        panelEditar.add(actualizar);
        panelEditar.add(cancelar);

        add(panelEditar);
        panelEditar.setVisible(true);
    }
    
    private void validarContenido(){
        boolean correcto = true;
        
        //Valida que todos los elementos estén completos
        if(isbn.isEmpty()){ISBN.setForeground(Color.red); correcto=false;}
        if(titulo.isEmpty()){Titulo.setForeground(Color.red); correcto=false; }
        if(autor.isEmpty()){Autor.setForeground(Color.red); correcto=false;}
        if(año.isEmpty()){Año.setForeground(Color.red); correcto=false;}
        if(editorial.isEmpty()){Editorial.setForeground(Color.red); correcto=false;}
        if(genero.isEmpty()){Genero.setForeground(Color.red); correcto=false;}
        if(paginas.isEmpty()){Paginas.setForeground(Color.red); correcto=false;}
        if(Descripcion.getText().equals("")){scroll.setBorder(BorderFactory.createLineBorder(Color.RED, 1)); correcto=false;}

        //Detiene la validacion
        if(!correcto) return;

        //Obtiene los valores
        isbn = ISBN.getText().trim();
        titulo = Titulo.getText().trim();
        autor = Autor.getText().trim();
        editorial = Editorial.getText().trim();
        genero = Genero.getText().trim();
        descripcion = Descripcion.getText().trim();
        
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
            añoInt = Integer.parseInt(Año.getText());
            
            if(añoInt>LocalDate.now().getYear()){
                Año.setForeground(Color.red);                       
                correcto = false;
            }
        }catch(NumberFormatException ex){
            Año.setForeground(Color.red);
            correcto=false;
        }
        
        try{
            paginasInt = Integer.parseInt(Paginas.getText());
            if(paginasInt<=0){
                Paginas.setForeground(Color.red);
                correcto = false;
            }
        }catch(NumberFormatException ex){
            Paginas.setForeground(Color.red);
            correcto=false;
        }
        
        //Detiene la validación
        if(!correcto) return;
        
        if(correcto){
            Peticiones.actualizarLibro(IdLibro, isbn, titulo, autor, portada, añoInt, editorial, genero, paginasInt, descripcion, admin);
        }
    }

    
    //Cambiar la foto
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

            File Carpeta = new File("C:/xampp/htdocs/Imagenes");
            
            //Si no existe la carpeta la crea
            if(!Carpeta.exists()){
                Carpeta.mkdirs();
            }else{
                //Ruta donde se guardará la imagen
                String ruta = "C:/xampp/htdocs/Imagenes/"+titulo.trim().replace(" ","")+".jpg";
                File destino = new File(ruta);

                try{
                    Files.copy(archivoSeleccionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    //Reemplazar la imagen de selección por la imagen seleccionada
                    Image imagen = new ImageIcon(ruta).getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
                    Portada.setIcon(new ImageIcon(imagen));
                    repaint();
                }catch(Exception error){
                    System.out.println("Error: "+error);
                    new WindowError("Ha ocurrido un error. Intente nuevamente");
                }
            }
        }
    }

}