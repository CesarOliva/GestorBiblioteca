package biblioteca;

//Se importan las librerias necesarias
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.io.File;  
import java.awt.event.*;
import java.nio.file.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import Conexion.Peticiones;
import Conexion.Sesion;

//"Librerias" necesarias a importar
import elementos.RoundedButton;
import elementos.CustomScroll;
import elementos.PlaceholderTextField;
import elementos.WindowError;

//Clase Autor extendida de JPanel. Panel personalizado.
public class Autor extends JPanel {
    private JPanel panelLibros, datos, panelEditar;
    private JScrollPane scrollPane;
    private JLabel fotoEditorial;
    private int offset=0, limite=10, idAutor;    
    private String autor;
    
    public Autor(int idAutor, String autor, String biografia, String foto) {
        //Configuración del panel
        setLayout(new BorderLayout());
        setBackground(Color.white);
        
        this.autor = autor;
        this.idAutor = idAutor;
        
        //Carga el panel de datos como primera vista
        mostrarPanelDatos(idAutor, autor, biografia, foto);
    }
    
    private void mostrarPanelDatos(int idAutor, String autor, String biografia, String foto){
               //Panel de datos
        datos = new JPanel(null);
        datos.setPreferredSize(new Dimension(620, 300));
        datos.setMinimumSize(new Dimension(620, 300));
        datos.setMaximumSize(new Dimension(620, 300));
        datos.setBackground(Color.white);
        
        fotoEditorial = new JLabel();
        
        //Intente cargar la imagen, de lo contrario cargar una imagen default
        File archivo = new File(foto);
        if (archivo.exists()) {
            Image imagen = new ImageIcon(foto).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            fotoEditorial.setIcon(new ImageIcon(imagen));
        }else{
            Image imagen = new ImageIcon(getClass().getResource("/imagenes/Perfil.jpg")).getImage()
                        .getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            fotoEditorial.setIcon(new ImageIcon(imagen));
        }
        fotoEditorial.setBounds(50, 50, 200, 200);

        JLabel lblNombre = new JLabel(autor);
        lblNombre.setFont(new Font("Poppins", Font.PLAIN, 14));
        lblNombre.setBounds(290, 50, 300, 30);

        JTextArea txtBiografia = new JTextArea(biografia);
        txtBiografia.setLineWrap(true);
        txtBiografia.setWrapStyleWord(true);
        txtBiografia.setFont(new Font("Poppins", Font.PLAIN, 12));
        txtBiografia.setBounds(285, 80, 280, 300);
        txtBiografia.setEditable(false);
        txtBiografia.setFocusable(false);
        txtBiografia.setBackground(Color.white);
        
        JButton editar = new RoundedButton("Editar");
        editar.setFont(new Font("Poppins", Font.PLAIN, 15));
        editar.setForeground(Color.WHITE);
        editar.setBackground(new Color(100, 149, 237));
        editar.setBounds(100, 260, 100, 30);
        
        //Se muestre un botón si es administrador
        if(Sesion.getTipo().equals("administrador")){
            editar.setVisible(true);
        }else{
            editar.setVisible(false);
        }
        
        editar.addActionListener(e->{
            mostrarPanelEditar(idAutor, autor, biografia, foto);
        });

        //Se agregan los elementos
        datos.add(fotoEditorial);
        datos.add(lblNombre);
        datos.add(txtBiografia);
        datos.add(editar);
        
        //Panel de libros
        panelLibros = new JPanel();
        panelLibros.setLayout(new BoxLayout(panelLibros, BoxLayout.Y_AXIS));        
        panelLibros.setBackground(Color.white);
        
        panelLibros.add(datos);

        // Cargar los 10 libros mas recientes al iniciar
        cargarLibros(Peticiones.librosAutor(idAutor, autor, limite, offset));
        
        //Scrollpane del panelLibros
        scrollPane = new CustomScroll(panelLibros);
        
        //Creación del panel de editar
        panelEditar = new JPanel(null);
        
        //Agrega los paneles al panel principal
        add(scrollPane, BorderLayout.CENTER);
        add(panelEditar, BorderLayout.NORTH);
        scrollPane.setVisible(true);
    }
    
    private void mostrarPanelEditar(int IdAutor, String autor, String biografia, String foto){
        scrollPane.setVisible(false);
        
        panelEditar.setPreferredSize(new Dimension(620, 300));
        panelEditar.setBackground(Color.white);
        
        fotoEditorial = new JLabel();        
        //Intente cargar la imagen, de lo contrario cargar una imagen default
        File archivo = new File(foto);
        if (archivo.exists()) {
            Image imagen = new ImageIcon(foto).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            fotoEditorial.setIcon(new ImageIcon(imagen));
        }else{
            Image imagen = new ImageIcon(getClass().getResource("/imagenes/Perfil.jpg")).getImage()
                        .getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            fotoEditorial.setIcon(new ImageIcon(imagen));
        }
        fotoEditorial.setBounds(50, 50, 200, 200);
        
        //Funcionalidad al clickear la foto
        fotoEditorial.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                    chooseFile();
            }
        });

        PlaceholderTextField Nombre = new PlaceholderTextField(autor, 100);
        Nombre.setText(autor);
        Nombre.setEnabled(false);
        Nombre.setFont(new Font("Poppins", Font.PLAIN, 12));
        Nombre.setBounds(290, 50, 200, 30);

        JTextArea txtDesc = new JTextArea(biografia);
        txtDesc.setLineWrap(true);
        txtDesc.setWrapStyleWord(true);
        txtDesc.setFont(new Font("Poppins", Font.PLAIN, 12));
        txtDesc.setBounds(290, 90, 280, 300);
        txtDesc.setEditable(true);
        txtDesc.setBackground(Color.lightGray);
        
        JButton actualizar = new RoundedButton("Actualizar");
        actualizar.setFont(new Font("Poppins", Font.PLAIN, 15));
        actualizar.setForeground(Color.WHITE);
        actualizar.setBackground(new Color(100, 149, 237));
        actualizar.setBounds(30, 260, 120, 30);
        
        JButton cancelar = new RoundedButton("Cancelar");
        cancelar.setFont(new Font("Poppins", Font.PLAIN, 15));
        cancelar.setForeground(Color.WHITE);
        cancelar.setBackground(Color.red);
        cancelar.setBounds(170, 260, 100, 30);
        
        cancelar.addActionListener(e->{
            Nombre.setText(autor);
            txtDesc.setText(biografia);
            
            panelEditar.setVisible(false);
            scrollPane.setVisible(true);
        });
        
        actualizar.addActionListener(e->{
            String nombre = Nombre.getText();
            String desc = txtDesc.getText();
            Peticiones.actualizarAutor(IdAutor, nombre, desc, foto);
        });
        
        panelEditar.add(fotoEditorial);
        panelEditar.add(Nombre);
        panelEditar.add(txtDesc);
        panelEditar.add(actualizar);
        panelEditar.add(cancelar);
        
        panelEditar.setVisible(true);
    }
    
    //Mostrar todos los libros o la busqueda
    private void cargarLibros(ArrayList<LibroBusqueda> libros) {
        panelLibros.removeAll();
        
        panelLibros.add(datos);        
        
        //Obtiene la lista de los libros
        for(LibroBusqueda libro : libros){
            //Fila de cada libro
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            fila.setBackground(Color.white);
            fila.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            //Configuración del panel
            JPanel card = new JPanel(null);
            card.setPreferredSize(new Dimension(500, 220));
            card.setMinimumSize(new Dimension(500, 220));
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

            int idLibro = libro.getId();
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
            card.add(Descripcion);
            card.add(verMas);

            //Agrega el contenido del card a la fila y la fina al panel
            fila.add(card);
            panelLibros.add(fila);
        }
        
        //Panel de paginacion
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
        
        if(offset>0){
            anterior.addActionListener(e->{
                //Posición de busqueda de libro
                offset-=limite;
                if(offset<0){
                    offset=0;
                }
               
                cargarLibros(Peticiones.librosAutor(idAutor, autor, limite, offset));
            });            
        }
        
        JButton siguiente = new RoundedButton("Siguiente");
        siguiente.setForeground(Color.white);
        siguiente.setBackground(new Color(100, 149, 237));
        siguiente.setFont(new Font("Poppins", Font.PLAIN, 12));
        siguiente.setBounds(465, 0, 100, 40);
        
        siguiente.addActionListener(e->{
            //Posición de busqueda de libro
            offset+=limite;
            cargarLibros(Peticiones.librosAutor(idAutor, autor, limite, offset));            
        });
        
        // Desactiva botón anterior
        anterior.setEnabled(offset > 0);

        // Desactiva botón siguiente
        siguiente.setEnabled(offset + limite < Peticiones.contarLibrosAutor(idAutor));
        
        //Agregar los botones al panel de paginacion y el panel al panel de resultados
        paginacion.add(siguiente);
        paginacion.add(anterior);
        
        panelLibros.add(paginacion);
        
        panelLibros.revalidate();
        panelLibros.repaint();   
        
        SwingUtilities.invokeLater(() -> {
            //Manda el scroll al inicio
            scrollPane.getVerticalScrollBar().setValue(0);
        });
    }
    
    //Metodo para escoger la nueva imagen
    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una imagen");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes JPG, PNG, WEBP", "jpg", "jpeg", "png", "webp");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(filtro);

        int resultado = fileChooser.showOpenDialog(null);
        
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            String destinoPath = "C:/xampp/htdocs/Imagenes/"+autor.trim().replace(" ","")+".jpg";
            File destino = new File(destinoPath);
            
            try {
                Files.copy(archivoSeleccionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Image imagen = new ImageIcon(destinoPath).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                fotoEditorial.setIcon(new ImageIcon(imagen));
                fotoEditorial.setBounds(50, 55, 200, 200);
                
                repaint();                
            } catch (Exception error) {
                new WindowError("Ha ocurrido un error. Intente nuevamente");
                System.out.println("Error: "+error);
            }
        }
    }
}