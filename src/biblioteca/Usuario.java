package biblioteca;

//Librerias necesarias a utilizar
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.nio.file.*;
import javax.swing.filechooser.*;

import Conexion.Sesion;
import Conexion.Peticiones;

//"librerias" personalizadas a importar
import elementos.RoundedButton;
import elementos.PlaceholderTextField;

//Clase Usuario extendida de JPanel. Panel Personalizado
public class Usuario extends JPanel {
    JLabel fotoUsuario;
    
    public Usuario(String Nombre, String Usuario, String Fecha, String foto) {
        //Configuración del panel
        setLayout(null);
        setBackground(Color.white);

        JLabel nombre = new JLabel("Nombre:"+Nombre);
        nombre.setBounds(400, 15 , 400, 150);

        JLabel usuario = new JLabel("Usuario:"+Usuario);
        usuario.setBounds(400, 55,400,150);
        
        JLabel fecha = new JLabel("Usuario desde: "+Fecha);
        fecha.setBounds(400,95,400,150);

        ImageIcon icon = new ImageIcon(foto);
        Image imagenEscalada = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        fotoUsuario = new JLabel(new ImageIcon(imagenEscalada));
        fotoUsuario.setBounds(-20, 55, 400, 200);
        

        JButton btnEditar = new RoundedButton("Editar");
        btnEditar.setFont(new Font("Poppins", Font.PLAIN, 15));
        btnEditar.setBackground(Color.WHITE);
        btnEditar.setForeground(new Color(100, 149, 237));
        btnEditar.setBounds(100, 405, 150, 30);

        JButton btnEliminar = new RoundedButton("Eliminar");
        btnEliminar.setFont(new Font("Poppins", Font.PLAIN, 15));
        btnEliminar.setBackground(Color.WHITE);
        btnEliminar.setForeground(new Color(100, 149, 237));
        btnEliminar.setBounds(300, 405, 150, 30);

//        btnEditar.addActionListener((ActionEvent e) -> {
//            Nombre = new PlaceholderTextField("Nombre", 50);
//            Nombre.setBounds(150, 20, 150, 20);
//            
//            Contraseña = new PlaceholderTextField("Contraseña", 50);
//            Contraseña.setBounds(150,50,150,20);
//            
//            editarFot = new RoundedButton("Editar Fotografia");
//            editarFot.setFont(new Font("Poppins", Font.PLAIN, 15));
//            editarFot.setBackground(Color.WHITE);
//            editarFot.setForeground(new Color(100, 149, 237));
//            editarFot.setBounds(50, 1005, 150, 30);
//            
//            editarFot.addActionListener((ActionEvent ex) -> {
//               chooseFile(); 
//
//            });
//            //BOTON PARA GUARDAR LOS CAMBIOS
//            guardarCambios = new RoundedButton("Guardar Cambios");
//               btnEditar.setFont(new Font("Poppins", Font.PLAIN, 15));
//               btnEditar.setBackground(Color.WHITE);
//               btnEditar.setForeground(new Color(100, 149, 237));
//               btnEditar.setBounds(100, 405, 150, 30);
//               
//               //FALTA PONER SET VISIBLE
//            
//        });

        btnEliminar.addActionListener((ActionEvent e) -> {
                new Peticiones();
                new LogIn();
        });

        add(fotoUsuario);
        add(nombre);
        add(usuario);
        add(fecha);
        add(btnEditar);
        add(btnEliminar);
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
            String destinoPath = "C:/xampp/htdocs/Imagenes/Foto_"+Sesion.getUsuario()+".jpg";
            File destino = new File(destinoPath);
            
            try{
                Files.copy(archivoSeleccionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                String rutaWeb = "http://localhost/Imagenes/"+Sesion.getUsuario()+".jpg";
            }catch(IOException ex){
                ex.printStackTrace();
                System.out.println("Error al copiar la imagen");
            }

            //Reemplazar la imagen de selección por la imagen seleccionada
            Image imagen = new ImageIcon(destinoPath).getImage().getScaledInstance(200, 300, Image.SCALE_SMOOTH);
            fotoUsuario.setIcon(new ImageIcon(imagen));
            repaint();
        }
    }
           
}