package elementos;

//Se importan las librerias necesarias
import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

//Clase MusicPlayer. Se encarga de controlar la musica de fonfo
public class MusicPlayer {
    public Clip clip;
    public static boolean playing = false;
    public float volumen = 0.5f; // Volumen medio por defecto (rango: 0.0 a 1.0)
    
    public void cargarMusica(String nombreArchivo) {
        try {
            InputStream audioStream = getClass().getResourceAsStream("/sounds/" + nombreArchivo);
            if (audioStream == null) {
                throw new IOException("Archivo no encontrado: /sounds/" + nombreArchivo);
            }
            
            AudioInputStream ais = AudioSystem.getAudioInputStream(audioStream);
            clip = AudioSystem.getClip();
            clip.open(ais);
            
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar música", e);
        }
    }
    public void reproducir() {
        if (clip != null && !playing) {
            clip.loop(Clip.LOOP_CONTINUOUSLY); // Repetir indefinidamente
            clip.start();
            playing = true;
        }
    }

    public void pausar() {
        if (clip != null && playing) {
            clip.stop();
            playing = false;
        }
    }

    public void detener() {
        if (clip != null) {
            clip.stop();
            clip.setFramePosition(0);
            playing = false;
        }
    }

    public void setVolumen(float volumen) {
        if (volumen < 0f) volumen = 0f;
        if (volumen > 1f) volumen = 1f;
        
        this.volumen = volumen;
        if (clip != null) {
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            float dB = (float) (Math.log(volumen) / Math.log(10.0) * 20.0);
            gainControl.setValue(dB);
        }
    }
    
    public static boolean estaReproduciendo() {
        return playing;
    }

    public int getVolumen() {
        return (int)(this.volumen * 100);
    }
}