package elementos;

//Se importan las librerias necesarias
import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;

//Clase ButtonSounds. Reproduce los sonidos de los botones
public class ButtonSounds {
    
    //Reproduce audio
    public static void play(String audio){
        try {
            //Carga el archivo
            URL path = ButtonSounds.class.getResource(audio);
            AudioInputStream audioIn = AudioSystem.getAudioInputStream(path);
            Clip clip = AudioSystem.getClip();
            clip.open(audioIn);
            clip.start();
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException error) {
            System.out.println("Error: "+error);
        }
    }
}

