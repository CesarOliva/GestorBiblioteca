package biblioteca;

//Se importan las librerias necesarias
import java.awt.*;
import javax.swing.*;
import Conexion.Sesion;

//"Librerias" personalizadas a importar

import elementos.RoundedButton;
public class LibroIndividual extends JPanel{
    public LibroIndividual(String isbn, String titulo, String autor, String portada, String año, 
        String editorial, String genero, String paginas, String descripcion, String admin){
        //Configuracion del panel
        setLayout(null);
        setBackground(Color.white);

        //Creación de los elementos       
        Image imagen = new ImageIcon(portada).getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
        JLabel Portada = new JLabel(new ImageIcon(imagen));
        Portada.setBounds(50,50,200,300);
        
        JLabel Titulo = new JLabel(titulo);
        Titulo.setFont(new Font("Poppins", Font.PLAIN, 15));        
        Titulo.setBounds(290, 50, 200, 30);
        
        JLabel Autor = new JLabel(autor);
        Autor.setFont(new Font("Poppins", Font.PLAIN, 15));
        Autor.setBounds(290, 80, 200, 30);

        JLabel Año = new JLabel("Año de publicación: "+año);
        Año.setFont(new Font("Poppins", Font.PLAIN, 15));
        Año.setBounds(290, 110, 200, 30);
        
        JLabel Editorial = new JLabel(editorial);
        Editorial.setFont(new Font("Poppins", Font.PLAIN, 15));
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
        
        JButton editar = new RoundedButton("Editar"); 
        editar.setFont(new Font("Poppins", Font.PLAIN, 15));
        editar.setForeground(Color.white);
        editar.setBackground(new Color(100, 149, 237));
        editar.setBounds(50, 460, 100, 30);       

        JButton eliminar = new RoundedButton("Eliminar"); 
        eliminar.setFont(new Font("Poppins", Font.PLAIN, 15));
        eliminar.setForeground(Color.white);
        eliminar.setBackground(Color.red);
        eliminar.setBounds(180, 460, 100, 30);       
        
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
        add(eliminar);
    }
}