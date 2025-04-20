package biblioteca;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.*;
import javax.swing.filechooser.*;

import Conexion.Sesion;
import Conexion.Peticiones;
import elementos.RoundedButton;
import elementos.PlaceholderTextField;
import elementos.WindowError;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Usuario extends JPanel {
    private JPanel panelVer, panelEditar, panelNoti;
    private JLabel fotoUsuario, lblNoti;
    private PlaceholderTextField texNombre, texUsuario, texContraseña;
    private ImageIcon ruta;
    private JTextField txtcontenido;

    public Usuario(String Nombre, String Usuario, String Fecha, String Contraseña, String foto) {
        setLayout(null);
        setBackground(Color.white);
        setPreferredSize(new Dimension(800, 500));

        panelVer = new JPanel(null);
        panelVer.setBounds(0, 0, 800, 500);
        panelVer.setBackground(Color.white);

        panelEditar = new JPanel(null);
        panelEditar.setBounds(0, 0, 800, 500);
        panelEditar.setBackground(Color.white);
        panelEditar.setVisible(false);

        panelNoti = new JPanel(null);
        panelNoti.setBounds(0,350,800,150);
        panelNoti.setBackground(Color.LIGHT_GRAY);
        
        // PANEL VER
        JLabel lblNombre = new JLabel("Nombre:  " + Nombre);
        lblNombre.setBounds(400, 15, 400, 150);
        panelVer.add(lblNombre);

        JLabel lblUsuario = new JLabel("Usuario:  " + Usuario);
        lblUsuario.setBounds(400, 55, 400, 150);
        panelVer.add(lblUsuario);

        JLabel lblFecha = new JLabel("Usuario desde:  " + Sesion.getFechaCreacion());
        lblFecha.setBounds(400, 95, 400, 150);
        panelVer.add(lblFecha);

        JLabel lblContraseña = new JLabel("Contraseña:  " + "****");
        lblContraseña.setBounds(400, 140, 400, 150);
        panelVer.add(lblContraseña);

        ImageIcon icon = new ImageIcon(foto);
        Image imagenEscalada = icon.getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
        fotoUsuario = new JLabel(new ImageIcon(imagenEscalada));
        fotoUsuario.setBounds(-80, 55, 400, 200);
        panelVer.add(fotoUsuario);

        JButton btnEditar = new RoundedButton("Editar");
        btnEditar.setFont(new Font("Poppins", Font.PLAIN, 15));
        btnEditar.setBackground(Color.WHITE);
        btnEditar.setForeground(new Color(100, 149, 237));
        btnEditar.setBounds(100, 280, 150, 30);
        panelVer.add(btnEditar);

        JButton btnEliminar = new RoundedButton("Eliminar");
        btnEliminar.setFont(new Font("Poppins", Font.PLAIN, 15));
        btnEliminar.setBackground(Color.WHITE);
        btnEliminar.setForeground(new Color(100, 149, 237));
        btnEliminar.setBounds(300, 280, 150, 30);
        panelVer.add(btnEliminar);

       
        // PANEL EDITAR
        
        JLabel nombreEdit = new JLabel("Nombre: ");
        nombreEdit.setBounds(340,-10, 200, 100);
        panelEditar.add(nombreEdit);
        
        JLabel usuarioEdit = new JLabel("Usuario: ");
        usuarioEdit.setBounds(340,30, 200, 100);
        panelEditar.add(usuarioEdit);
        
        JLabel contraEdit = new JLabel("Contraseña: ");
        contraEdit.setBounds(330,70,200,100);
        panelEditar.add(contraEdit);
        
        texNombre = new PlaceholderTextField("Nombre", 30);
        texNombre.setText(Nombre);
        texNombre.setBounds(400, 30, 200, 30);
        panelEditar.add(texNombre);

        texUsuario = new PlaceholderTextField("Usuario", 30);
        texUsuario.setText(Usuario);
        texUsuario.setBounds(400, 70, 200, 30);
        panelEditar.add(texUsuario);

        texContraseña = new PlaceholderTextField("******", 30);
        texContraseña.setText(Contraseña);
        texContraseña.setBounds(400, 110, 200, 30);
        panelEditar.add(texContraseña);
        
        fotoUsuario = new JLabel(new ImageIcon(imagenEscalada));
        fotoUsuario.setBounds(350, 50, 200, 200);
        panelEditar.add(fotoUsuario);

        JButton btnGuardar = new RoundedButton("Guardar");
        btnGuardar.setFont(new Font("Poppins", Font.PLAIN, 15));
        btnGuardar.setBackground(Color.WHITE);
        btnGuardar.setForeground(new Color(100, 149, 237));
        btnGuardar.setBounds(100, 405, 150, 30);
        panelEditar.add(btnGuardar);

        JButton btnCancelar = new RoundedButton("Cancelar");
        btnCancelar.setFont(new Font("Poppins", Font.PLAIN, 15));
        btnCancelar.setBackground(Color.WHITE);
        btnCancelar.setForeground(new Color(100, 149, 237));
        btnCancelar.setBounds(300, 405, 150, 30);
        panelEditar.add(btnCancelar);

        // Eventos
        btnEditar.addActionListener(e -> {
            
        fotoUsuario.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
            }
        });
            panelVer.setVisible(false);
            panelEditar.setVisible(true);
            chooseFile();
            
        });

        btnCancelar.addActionListener(e -> {
            panelEditar.setVisible(false);
            panelVer.setVisible(true);
        });

        btnGuardar.addActionListener(e -> {
            String nuevoNombre = texNombre.getText();
            String nuevoUsuario = texUsuario.getText();
            String nuevaContraseña = texContraseña.getText();
            
            Peticiones.actualizarUsuario(Sesion.getUsuario(), nuevoNombre, nuevaContraseña);

            panelVer.add(fotoUsuario);
            panelEditar.setVisible(false);
            panelVer.setVisible(true);
        });

        btnEliminar.addActionListener(e -> {
            new Peticiones();
            new LogIn();
        });
        
         //PANEL NOTIFICAIONES
        
        JLabel lblNoti = new JLabel("Notificaciones");
        lblNoti.setBounds(10, 10, 200, 30);
        panelNoti.add(lblNoti);
        panelNoti.add(lblNoti);

        for (int i = 0; i < 3; i++) {
            JTextField campo = new JTextField();
            campo.setBounds(10, 50 + i * 40, 300, 30);
            panelNoti.add(campo);
        }

        panelVer.add(panelNoti);
        add(panelVer);
        add(panelEditar);
    }

    private void chooseFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una imagen");
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Imágenes JPG, PNG, WEBP", "jpg", "jpeg", "png", "webp");
        fileChooser.setAcceptAllFileFilterUsed(false);
        fileChooser.setFileFilter(filtro);

        int resultado = fileChooser.showOpenDialog(null);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            String destinoPath = "C:/xampp/htdocs/Imagenes/Foto_" + Sesion.getUsuario() + ".jpg";
            File destino = new File(destinoPath);
            try {
                Files.copy(archivoSeleccionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Image imagen = new ImageIcon(destinoPath).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                fotoUsuario.setIcon(new ImageIcon(imagen));
                        fotoUsuario.setBounds(-80, 55, 400, 200);
                repaint();                
            } catch (IOException ex) {
                ex.printStackTrace();
                System.out.println("Error al copiar la imagen");
            }
        }
    }   
}