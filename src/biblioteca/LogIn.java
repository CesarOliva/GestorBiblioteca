package biblioteca;

//Importa las librerias necesarias
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat; //Formateo de decimales para porcentajes
import java.text.DecimalFormatSymbols;
import java.util.Locale;
import net.miginfocom.swing.MigLayout; //Posición de los elementos
import org.jdesktop.animation.timing.*; //Animaciones
import com.formdev.flatlaf.extras.FlatSVGIcon;

//peticiones a la base de datos
import Conexion.Peticiones;

//"Librerias" personalizadas importadas
import elementos.PlaceholderTextField;
import elementos.PlaceholderPassField;
import elementos.RoundedButton;

//Clase LogIn extendida de JFrame. Frame personalizado.
public class LogIn extends JFrame {
    private boolean logInVista = true; //indica en que panel está
    private JPanel panel;
    private JButton registrarseBtn, iniciarBtn;
    private static LogIn instancia;
    
    //Constructor
    public LogIn() {
        //Configuración de la ventana
        setTitle("Inicio de sesión y registro");
        setLayout(new BorderLayout());
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        
        instancia = this;
        
        panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(new MigLayout("fill, insets 0"));

        cambiarVista();
        
        //Agrega el panel al frame y lo hace visible
        add(panel, BorderLayout.CENTER);
        setVisible(true);

        //Focus en el panel al iniciar el programa
        SwingUtilities.invokeLater(() -> {
            panel.requestFocusInWindow(); 
        });  
    }
    
    //Permite alternar entre login y registro
    private void cambiarVista() {
        //Ocupa todo el espacio, sin margenes internos
        MigLayout layout = new MigLayout("fill, insets 0");

        Instrucciones cover = new Instrucciones();
        Formulario form = new Formulario();
       
        //Configuración de la animación
        TimingTarget target = new TimingTargetAdapter() {
            @Override
            public void timingEvent(float fraction) {
                double fractionCover;
                double fractionLogin;
                double size = 40; // Tamaño base = 40%
                
                //Cambios de tamaño
                if (fraction <= 0.5f) { //Antes de la mitad de la animación
                    size += fraction * 30; // Aumenta el tamaño gradualmente
                } else {
                    size += 30 - fraction * 30; // Vuelve al tamaño original
                }
                
                //Cambia de posición dependiendo del panel
                if (logInVista) { //Si está la vista del logIn
                    fractionCover = 1f - fraction;
                    fractionLogin = fraction;
                    //Animación de cambio
                    if (fraction >= 0.5f) {
                        cover.registroDer(fractionCover * 100);
                    } else {
                        cover.loginDer(fractionLogin * 100);
                    }
                } else{ //Si está la vista de registro
                    fractionCover = fraction;
                    fractionLogin = 1f - fraction;
                    //Animación de cambio
                    if (fraction <= 0.5f) {
                        cover.registroIzq(fraction * 100);
                    } else {
                        cover.loginIzq((1f - fraction) * 100);
                    }
                }
                
                //Cambia el contenido a la mitad de la posicion
                if (fraction >= 0.5f) {
                    form.showRegister(logInVista);
                }
                
                //Formatea los decimales para evitar fallos en las posiciones
                DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US)); //formateo de decimales                
                fractionCover = Double.valueOf(df.format(fractionCover));
                fractionLogin = Double.valueOf(df.format(fractionLogin));
                
                //Va cambiando las posiciones y el tamaño
                layout.setComponentConstraints(cover, "width " + size + "%, pos " + fractionCover + "al 0 n 100%");
                layout.setComponentConstraints(form, "width " + 60 + "%, pos " + fractionLogin + "al 0 n 100%");
                panel.revalidate();
            }

            //Realiza el cambio entre login y registor
            @Override
            public void end() {
                logInVista = !logInVista;
            }
        };
        
        Animator animator = new Animator(800, target); //Animación de 800ms
        //Émpieza y termina mas suave
        animator.setAcceleration(0.5f);
        animator.setDeceleration(0.5f);
        
        form.setAnimator(animator);
        form.showRegister(!logInVista);
        cover.login(logInVista);
        
        //Inicia la animación
        cover.addEvent(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                if (!animator.isRunning()) {
                    animator.start();
                }
            }
        });

        //agrega el elemento de fondo
        panel.setLayout(layout);
        panel.add(cover, "width " + 40 + "%, pos " + (logInVista ? "1al" : "0al") + " 0 n 100%");
        panel.add(form, "width " + 60 + "%, pos " + (logInVista ? "0al" : "1al") + " 0 n 100%");
    }
    
    
    
    //Clase Interna Instrucciones
    public class Instrucciones extends JPanel {
        private ActionListener event;
        private MigLayout layout;

        private JLabel titulo, texto, texto2, texto3;
        private JButton boton;
        private boolean logInVista;
        
        public Instrucciones() {
            setBackground(new Color(100, 149, 237));        
  
            layout = new MigLayout("wrap, fillx, insets 0", "[center]", "80[]30[]0[]30[]20[]10");
            setLayout(layout);

            titulo = new JLabel("¡Bienvenido!");
            titulo.setForeground(Color.WHITE);
            titulo.setFont(new Font("Dytam", Font.PLAIN, 35));

            texto = new JLabel("Ingrese sus datos para usar");
            texto.setFont(new Font("Poppins", Font.PLAIN, 18));
            texto.setForeground(Color.white);

            texto2 = new JLabel("todas las funciones del programa");
            texto2.setFont(new Font("PopPins", Font.PLAIN, 18));
            texto2.setForeground(Color.white);
            
            texto3 = new JLabel("todas las funciones del programa");
            texto3.setFont(new Font("Poppins", Font.PLAIN, 18));
            texto3.setForeground(Color.white);

            boton = new RoundedButton("Registrarse");
            boton.setBackground(Color.WHITE);
            boton.setFont(new Font("Poppins", Font.PLAIN, 18));
            boton.setForeground(new Color(100, 149, 237));

            JLabel imagen = new JLabel(new FlatSVGIcon("imagenes/login.svg", 150, 200));

            boton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent ae) {
                    event.actionPerformed(ae);
                }
            });

            //Agrega los elementos al panel
            add(titulo);
            add(texto);
            add(texto2);
            add(texto3);
            add(boton, "w 150, h 40");
            add(imagen);
        } 
    
        public void addEvent(ActionListener event) {
            this.event = event;
        }

        //Metodos de animación de posición
        public void registroIzq(double v) {
            DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
            v = Double.valueOf(df.format(v));
            login(false);
            //Va cambiando las posiciones
            layout.setComponentConstraints(titulo, "pad 0 -" + v + "% 0 0");
            layout.setComponentConstraints(texto, "pad 0 -" + v + "% 0 0");
            layout.setComponentConstraints(texto2, "pad 0 -" + v + "% 0 0");
            layout.setComponentConstraints(texto3, "pad 0 -" + v + "% 0 0");
            layout.setComponentConstraints(boton, "pad 0 -" + v + "% 0 0");
        }

        public void registroDer(double v) {
            DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
            v = Double.valueOf(df.format(v));
            login(false);
            //Va cambiando las posiciones
            layout.setComponentConstraints(titulo, "pad 0 -" + v + "% 0 0");
            layout.setComponentConstraints(texto, "pad 0 -" + v + "% 0 0");
            layout.setComponentConstraints(texto2, "pad 0 -" + v + "% 0 0");
            layout.setComponentConstraints(texto3, "pad 0 -" + v + "% 0 0");
            layout.setComponentConstraints(boton, "pad 0 -" + v + "% 0 0");
        }

        public void loginIzq(double v) {
            DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
            v = Double.valueOf(df.format(v));
            login(true);
            //Va cambiando las posiciones
            layout.setComponentConstraints(titulo, "pad 0 " + v + "% 0 " + v + "%");
            layout.setComponentConstraints(texto, "pad 0 " + v + "% 0 " + v + "%");
            layout.setComponentConstraints(texto2, "pad 0 " + v + "% 0 " + v + "%");
            layout.setComponentConstraints(texto3, "pad 0 " + v + "% 0 " + v + "%");
            layout.setComponentConstraints(boton, "pad 0 -" + v + "% 0 0");
        }

        public void loginDer(double v) {
            DecimalFormat df = new DecimalFormat("##0.###", DecimalFormatSymbols.getInstance(Locale.US));
            v = Double.valueOf(df.format(v));
            login(true);
            //Va cambiando las posiciones
            layout.setComponentConstraints(titulo, "pad 0 " + v + "% 0 " + v + "%");
            layout.setComponentConstraints(texto, "pad 0 " + v + "% 0 " + v + "%");
            layout.setComponentConstraints(texto2, "pad 0 " + v + "% 0 " + v + "%");
            layout.setComponentConstraints(texto3, "pad 0 " + v + "% 0 " + v + "%");
            layout.setComponentConstraints(boton, "pad 0 -" + v + "% 0 0");
        }

        //Cambia el contenido de los paneles
        public void login(boolean login) {
            if (this.logInVista != login) {
                if (login) {
                    titulo.setText("Bienvenido!");
                    texto.setText("Ingrese sus datos para usar");
                    texto2.setText("todas las funciones del programa");
                    texto3.setText("¿No está registrado? Pruebe");
                    boton.setText("Registrarse");
                } else {
                    titulo.setText("Hola!");
                    texto.setText("Registrese con sus datos para usar");
                    texto2.setText("todas las funciones del programa");
                    texto3.setText("¿Ya tienes una cuenta? Intente");
                    boton.setText("Iniciar Sesión");
                }
                this.logInVista = login;
            }
        }
    }
    
    //Clase del Panel de LogIn y Registro
    public class Formulario extends JPanel {
        private JPanel login, registro;
        private PlaceholderTextField txtNombreR;
        private PlaceholderTextField txtUserR;
        private JPasswordField txtPassR;
        private PlaceholderTextField txtUserL;
        private JPasswordField txtPassL;
        private Animator animator;
        private boolean encontrado;
        private static Formulario instancia;
    
        public Formulario() {
            //Establece el layout
            setLayout(new CardLayout());
            instancia = this;

            //Creación del panel de logIn
            login = new JPanel();
            login.setBackground(Color.white);
            login.setLayout(new MigLayout("wrap", "push[center]push", "175[]25[]10[]10[]100[]push"));

            //Creación del panel de Registro        
            registro = new JPanel();
            registro.setLayout(new MigLayout("wrap", "push[center]push", "150[]25[]10[]10[]10[]90[]push"));
            registro.setBackground(Color.white);

            //Agrega los cards al panel
            add(registro, "card2");
            add(login, "card3");

            //Muestra un panel visible y otro oculto
            login.setVisible(false);
            registro.setVisible(true);

            //Elementos del registro
            JLabel registrarse = new JLabel("Registrarse");
            registrarse.setFont(new Font("Dytam", Font.PLAIN, 35));
            registrarse.setForeground(new Color(100, 149, 237));

            txtNombreR = new PlaceholderTextField("Nombre",20);
            txtNombreR.setFont(new Font("Poppins", Font.PLAIN, 14));

            txtUserR = new PlaceholderTextField("Usuario", 20);
            txtUserR.setFont(new Font("Poppins", Font.PLAIN, 14));

            txtPassR = new PlaceholderPassField("Contraseña", 20);
            txtPassR.setFont(new Font("Poppins", Font.PLAIN, 14));
            
            registrarseBtn = new RoundedButton("Registrarse");
            registrarseBtn.setBackground(new Color(100, 149, 237));
            registrarseBtn.setForeground(Color.WHITE);
            registrarseBtn.setFont(new Font("Poppins", Font.PLAIN, 18));

            JLabel logo = new JLabel(new FlatSVGIcon("imagenes/logoLateral.svg", 168, 60));
            JLabel logo2 = new JLabel(new FlatSVGIcon("imagenes/logoLateral.svg", 168, 60));
 

            
            registro.add(registrarse);
            registro.add(txtNombreR, "w 50%, h 30");
            registro.add(txtUserR, "w 50%, h 30");
            registro.add(txtPassR, "w 50%, h 30");
            registro.add(registrarseBtn, "w 30%, h 40");
            registro.add(logo2, "w 190, h 60");


            //Elementos del LogIn
            JLabel logIn = new JLabel("Iniciar Sesion");
            logIn.setFont(new Font("Dytam", 1, 35));
            logIn.setForeground(new Color(100, 149, 237));

            txtUserL = new PlaceholderTextField("Usuario", 20);
            txtUserL.setFont(new Font("Poppins", Font.PLAIN, 14));

            txtPassL = new PlaceholderPassField("Contraseña", 20);
            txtPassL.setFont(new Font("Poppins", Font.PLAIN, 14));

            iniciarBtn = new RoundedButton("Iniciar Sesión");
            iniciarBtn.setBackground(new Color(100, 149, 237));
            iniciarBtn.setForeground(Color.WHITE);
            iniciarBtn.setFont(new Font("Poppins", Font.PLAIN, 18));    
            
            resetPlaceholder(txtNombreR);
            resetPlaceholder(txtUserR);
            
            //Funcionalidad del botón de logIn
            iniciarBtn.addActionListener((ActionEvent e)->{
                validarLogIn();
            });

            login.add(logIn);
            login.add(txtUserL, "w 50%, h 30");
            login.add(txtPassL, "w 50%, h 30");
            login.add(iniciarBtn, "w 30%, h 40");
            login.add(logo, "w 190, h 60");
        }

        //Obtiene una instancia de la clase LogIn
        public static Formulario getInstancia(){
            return instancia;
        }
        
        //Resetea el textField
        private void resetPlaceholder(PlaceholderTextField tf){
            tf.addFocusListener(new FocusAdapter() {
                @Override
                public void focusGained(FocusEvent e) {
                    ((PlaceholderTextField)tf).resetPlaceholder();
                }       
            });        
        }    
        
        //Metodos que validan los datos de los campos del formulario
        public boolean validarRegistro(){
            String nombre = txtNombreR.getText().trim();
            String user = txtUserR.getText().trim();
            String pass = new String(txtPassR.getPassword());

            //Verifica que nombre solo tenga letras y espacios
            encontrado = !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ\\s]+");
            
            //Verifica que estén todos los campos seleccionados
            if(txtNombreR.validarContenido() || txtUserR.validarContenido() || pass.isEmpty() || encontrado || user.contains(" ")){
                if(txtNombreR.validarContenido() || encontrado) txtNombreR.setForeground(Color.red);
                if(txtUserR.validarContenido() || user.contains(" ")) txtUserR.setForeground(Color.red);
                if(pass.isEmpty()) txtPassR.setForeground(Color.red);
                return false;
            }else{
                //Agrega el usuario a la base de datos
                return Peticiones.agregarUsuario(nombre, user, pass);
            }
        }
        
        //Valida que exista el usuario y coincida con la contraseña
        public void validarLogIn(){
            String user = txtUserL.getText();
            String pass = new String(txtPassL.getPassword());
            
            //Verifica que estén todos los campos seleccionados
            if(txtUserL.validarContenido() || pass.isEmpty()){
                if(txtUserL.validarContenido()) txtUserL.setForeground(Color.red);
                if(pass.isEmpty()) txtPassL.setForeground(Color.red);
            }else{
                //Valida que el usuario exista
                Peticiones.validarUsuario(user, pass);
            }
        }

        //Limpia los campos
        public void limpiarCampos() {
            txtUserR.setText("Usuario");
            txtUserR.setForeground(Color.gray);
            txtNombreR.setText("Nombre");
            txtNombreR.setForeground(Color.gray);
            txtPassR.setText("");
            ((PlaceholderPassField)txtPassR).resetPlaceholder();
            
            txtUserL.setText("Usuario");
            txtUserL.setForeground(Color.gray);
            txtPassL.setText("");
            ((PlaceholderPassField)txtPassL).resetPlaceholder();            
        }

        //Animación de cambio de vista
        public void setAnimator(Animator animator) {
            this.animator = animator;
            registrarseBtn.addActionListener(e -> {
                //valida la condición de la validación de registro
                if(validarRegistro()){
                    if(!animator.isRunning()) {
                        animator.start();
                    }
                }
            });
        }    
        
        //Alterna la vista entre registro y logIn
        public void showRegister(boolean show) {
            if (show) {
                registro.setVisible(true);
                login.setVisible(false);
            } else {
                registro.setVisible(false);
                login.setVisible(true);
            }
        }
         
    }
    
    //Obtiene una instancia de la clase LogIn
    public static LogIn getInstancia(){
        return instancia;
    }

    //Cierra la ventana
    public void cerrar(){
        dispose();
    }
}