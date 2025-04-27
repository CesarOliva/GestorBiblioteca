package biblioteca;

//Se importan las librerias necesarias
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import javax.swing.filechooser.*;
import com.formdev.flatlaf.extras.FlatSVGIcon;

import Conexion.Sesion;
import Conexion.Peticiones;

//"Librerias" necesarias a importar
import elementos.RoundedButton;
import elementos.PlaceholderTextField;
import elementos.PlaceholderPassField;
import elementos.WindowError;
import elementos.ConfirmationWindow;
import elementos.CustomScroll;

//Clase Usuario extendida de JPanel. Panel personalizado
public class Usuario extends JPanel {
    private JLabel fotoUsuario, lblNombre;
    private PlaceholderTextField texNombre, texUsuario;
    private JPasswordField PassContraseña;
    private String contraseñaActual, Usuario;
    private JPanel editarUsuario, verUsuario, notificaciones;   
    private ImageIcon ruta;
    private Image imagen; 
    
    private Usuario instancia;

    public Usuario(String Nombre, String Usuario, String Contraseña, String Fecha, String foto) {
        //Configuración del panel
        setLayout(null);
        setBackground(Color.white);
        setPreferredSize(new Dimension(550, 600));
        
        //Guarda la contraseña actual
        this.contraseñaActual = Contraseña;
        this.Usuario = Usuario;
        
        instancia = this;

        //Creación del panel de verUsuario
        verUsuario = new JPanel(null);
        verUsuario.setBounds(0,0,650,300);
        verUsuario.setBackground(Color.white);

        //Creación de los elementos
        fotoUsuario = new JLabel();
        
        //Intente cargar la imagen, de lo contrario cargar una imagen default
        File archivo = new File(foto);
        if (archivo.exists()) {
            imagen = new ImageIcon(foto).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            fotoUsuario.setIcon(new ImageIcon(imagen));
        }else{
            imagen = new ImageIcon(getClass().getResource("/imagenes/Perfil.jpg")).getImage()
                        .getScaledInstance(200, 200, Image.SCALE_SMOOTH);
            fotoUsuario.setIcon(new ImageIcon(imagen));
        }
        fotoUsuario.setBounds(70, 55, 200, 200);

        lblNombre = new JLabel("Nombre: " + Nombre);
        lblNombre.setFont(new Font("Poppins", Font.PLAIN, 14));
        lblNombre.setBounds(300, 70, 400, 30);

        JLabel lblUsuario = new JLabel("Usuario: " + Usuario);
        lblUsuario.setFont(new Font("Poppins", Font.PLAIN, 14));
        lblUsuario.setBounds(300, 115, 400, 30);

        JLabel lblFecha = new JLabel("Usuario desde: " + Fecha);
        lblFecha.setFont(new Font("Poppins", Font.PLAIN, 14));
        lblFecha.setBounds(300, 160, 400, 30);

        JLabel lblContraseña = new JLabel("Contraseña: " + "******");
        lblContraseña.setFont(new Font("Poppins", Font.PLAIN, 14));        
        lblContraseña.setBounds(300, 205, 400, 30);
        
        JButton btnEditar = new RoundedButton("Editar");
        btnEditar.setFont(new Font("Poppins", Font.PLAIN, 14));
        btnEditar.setForeground(Color.WHITE);
        btnEditar.setBackground(new Color(100, 149, 237));
        btnEditar.setBounds(70, 270, 95, 30);

        JButton btnEliminar = new RoundedButton("Eliminar");
        btnEliminar.setFont(new Font("Poppins", Font.PLAIN, 14));
        btnEliminar.setForeground(Color.WHITE);
        btnEliminar.setBackground(Color.red);
        btnEliminar.setBounds(175, 270, 95, 30);
        
        //Se agregan los elementos al panel de verUsuario
        verUsuario.add(fotoUsuario);
        verUsuario.add(lblNombre);
        verUsuario.add(lblUsuario);
        verUsuario.add(lblFecha);
        verUsuario.add(lblContraseña);
        verUsuario.add(btnEditar);
        verUsuario.add(btnEliminar);
        
        
        //Panel de editar usuario
        editarUsuario = new JPanel(null);
        editarUsuario.setBounds(0,0,650,300);
        editarUsuario.setBackground(Color.white);
        editarUsuario.setVisible(false);
       
        // Creación de los elementos        
        fotoUsuario = new JLabel(new ImageIcon(imagen));
        fotoUsuario.setBounds(70, 55, 200, 200);
        
        JLabel nombreEdit = new JLabel("Nombre:");
        nombreEdit.setBounds(300, 95, 100, 30);
        nombreEdit.setFont(new Font("Poppins", Font.PLAIN, 14));

        texNombre = new PlaceholderTextField(Nombre, 100);
        texNombre.setText(Nombre);
        texNombre.setFont(new Font("Poppins", Font.PLAIN, 14));
        texNombre.setBounds(400, 95, 200, 30);

        JLabel usuarioEdit = new JLabel("Usuario:");
        usuarioEdit.setBounds(300, 140, 200, 30);
        usuarioEdit.setFont(new Font("Poppins", Font.PLAIN, 14));
        
        texUsuario = new PlaceholderTextField(Usuario, 100);
        texUsuario.setFont(new Font("Poppins", Font.PLAIN, 14));
        texUsuario.setBackground(Color.WHITE);
        texUsuario.setBounds(400, 140, 200, 30);
        texUsuario.setEnabled(false);

        JLabel contraEdit = new JLabel("Contraseña: ");
        contraEdit.setFont(new Font("Poppins", Font.PLAIN, 14));
        contraEdit.setBounds(300, 185, 400, 30);

        PassContraseña = new PlaceholderPassField("******", 30);
        PassContraseña.setFont(new Font("Poppins", Font.PLAIN, 14));
        PassContraseña.setBackground(Color.WHITE);
        PassContraseña.setBounds(400, 185, 200, 30);
        
        JButton btnGuardar = new RoundedButton("Guardar");
        btnGuardar.setFont(new Font("Poppins", Font.PLAIN, 14));
        btnGuardar.setBackground(new Color(100, 149, 237));
        btnGuardar.setForeground(Color.white);
        btnGuardar.setBounds(70, 270, 95, 30);

        JButton btnCancelar = new RoundedButton("Cancelar");
        btnCancelar.setFont(new Font("Poppins", Font.PLAIN, 14));
        btnCancelar.setBackground(Color.red);
        btnCancelar.setForeground(Color.white);
        btnCancelar.setBounds(174, 270, 96, 30);

        //Se agregan los elementos al panel de editarUsuario
        editarUsuario.add(fotoUsuario);
        editarUsuario.add(nombreEdit);
        editarUsuario.add(texNombre);
        editarUsuario.add(usuarioEdit);
        editarUsuario.add(texUsuario);
        editarUsuario.add(contraEdit);
        editarUsuario.add(PassContraseña);
        editarUsuario.add(btnGuardar);
        editarUsuario.add(btnCancelar);

        
        //Funcionalidad al clickear la foto
        fotoUsuario.addMouseListener(new MouseAdapter(){
            @Override
            public void mouseClicked(MouseEvent e){
                    chooseFile();
            }
        });

        //Panel de notificaciones
        notificaciones = new JPanel();
        notificaciones.setLayout(new BoxLayout(notificaciones, BoxLayout.Y_AXIS));
        notificaciones.setAlignmentY(Component.TOP_ALIGNMENT);
        notificaciones.setBounds(0,300,650,300);
        notificaciones.setBackground(Color.white);
        
        //Muestra las notificaciones
        cargarNotificaciones();
        
        //Funcionalidad de todos los botones     
        //Alternan entre el panel de ver y de editar usuario
        btnEditar.addActionListener(e -> {
            verUsuario.setVisible(false);
            notificaciones.setVisible(false);
            editarUsuario.setVisible(true);
        });

        btnCancelar.addActionListener(e -> {
            texNombre.setText(Usuario);
            texNombre.resetPlaceholder();
            
            verUsuario.setVisible(true);
            notificaciones.setVisible(true);
            editarUsuario.setVisible(false);
        });

        btnGuardar.addActionListener(e -> {
            validarDatosUsuario();
        });

        btnEliminar.addActionListener(e -> {
            new ConfirmationWindow("Esta acción es permanente. Escribe tu contraseña para eliminar la cuenta",
                () -> Peticiones.eliminarUsuario(Sesion.getUsuario()));
        });
        
        JScrollPane scrollPane = new CustomScroll(notificaciones);
        scrollPane.setBounds(0,300,640,260);
        
        //Agrega los paneles al panel principal
        add(verUsuario);
        add(scrollPane);
        add(editarUsuario);
    }
    
    public void cargarNotificaciones(){
        notificaciones.removeAll();
        
        //Creación de los elementos
        ArrayList<Notificacion> notificacionesList = Peticiones.obtenerNotificaciones(Sesion.getIdUsuario());
        
        for(Notificacion notificacion : notificacionesList){
            JPanel fila = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
            fila.setMaximumSize(new Dimension(Short.MAX_VALUE, 80));
            fila.setBackground(Color.white);
            fila.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

            JPanel card = new JPanel(null);
            card.setBackground(new Color(217, 217, 217));
            card.setPreferredSize(new Dimension(500, 60));
            card.setMaximumSize(new Dimension(500, 60));
            card.setMinimumSize(new Dimension(500, 60));
            card.setBounds(30, 50, 500, 60);

            JLabel icono = new JLabel(new FlatSVGIcon("imagenes/notificacion.svg", 40, 40));
            icono.setBounds(10,10,40,40);

            JLabel mensaje = new JLabel(notificacion.getMensaje());
            mensaje.setBounds(60, 20, 400, 20);
            mensaje.setFont(new Font("Poppins", Font.PLAIN, 14));

            JLabel borrar = new JLabel(new FlatSVGIcon("imagenes/borrar.svg", 30, 30));
            borrar.setBounds(460, 15, 30, 30);
            
            borrar.addMouseListener(new MouseAdapter(){
                //Funcionalidad al clickear
                @Override
                public void mouseClicked(MouseEvent e){
                    Peticiones.eliminarNotificacion(notificacion.getId(), instancia);
                }
                @Override
                public void mouseEntered(MouseEvent e) {
                    borrar.setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    borrar.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            });

            card.add(icono);
            card.add(mensaje);
            card.add(borrar);
            fila.add(card);
            
            notificaciones.add(fila);
        }
        notificaciones.revalidate();
        notificaciones.repaint();
    }

    //Valida los datos al actualizar
    private void validarDatosUsuario(){
        boolean correcto = true;

        if(texNombre.getText().equals("")){texNombre.setForeground(Color.red); correcto=false;}

        if(!correcto) return;

        String nombre = texNombre.getText().trim();
        String contraseña = new String (PassContraseña.getPassword()).trim();
        if(contraseña.isEmpty()){contraseña = contraseñaActual;}

        if(!nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+")){
            texNombre.setForeground(Color.red);
            correcto=false;
        }

        if(!correcto) return;

        if(correcto){
            Peticiones.actualizarUsuario(Usuario, nombre, contraseña);
            editarUsuario.setVisible(false);
            verUsuario.setVisible(true);
            lblNombre.setText(nombre);
        }
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
            String destinoPath = "C:/xampp/htdocs/Imagenes/"+Usuario+".jpg";
            File destino = new File(destinoPath);
            
            try {
                Files.copy(archivoSeleccionado.toPath(), destino.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Image imagen = new ImageIcon(destinoPath).getImage().getScaledInstance(200, 200, Image.SCALE_SMOOTH);
                fotoUsuario.setIcon(new ImageIcon(imagen));
                fotoUsuario.setBounds(50, 55, 200, 200);
                
                repaint();                
            } catch (Exception error) {
                new WindowError("Ha ocurrido un error. Intente nuevamente");
                System.out.println("Error: "+error);
            }
        }
    }
}