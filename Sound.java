import java.applet.*;
import java.net.*;

/**
 * Clase manejadora de sonidos en una aplicación
 * @author Revolution Software Developers
 */
public class Sound {
	/**
	 * Clip de la cancion en memoria
	 */
	private AudioClip song;
	
	/**
	 * URL Fisico del clip de audio
	 */
	private URL songPath;
	
	/**
	 * Constructor del Audio Clip,
	 * @param filename
	 */
	public Sound(String filename) {
		String ruta = Main.ruta+filename;
		
		try {
			songPath = new URL("file:" + ruta); 
			song = Applet.newAudioClip(songPath); 
		} catch(Exception e){
			System.out.println("Sonido: " + ruta + " no encontrado. El sonido no se reproducira");
		} 
	}
	
	/**
	 * Constructor con la opcion de reproducir en ese instante
	 * @param filename 
	 */
	public Sound(String filename, boolean reproduce){
		String ruta = Main.ruta+filename;
		try {
			songPath = new URL("file:" + ruta); 
			song = Applet.newAudioClip(songPath); 
		} catch(Exception e){
			System.out.println("Sonido: " + ruta + " no encontrado. El sonido no se reproducira");
		} 
		
		if(reproduce){
			this.playSoundOnce();
		}
	}
	
	/**
	 * Reproduce el sonido infinitamente
	 */
	public void playSound() {
		song.loop(); 
	}
	
	/**
	 * Detiene el audio
	 */
	public void stopSound() {
		song.stop(); 
	}
	
	/**
	 * Reproduce el sonido finitamente 1 vez
	 */
	public void playSoundOnce() {
		song.play(); 
	}
	
	/**
	 * Reproduce el sonido cierto numero de veces
	 */
	public void playSoundBy(int times){
		for(int i = 0; i < times; i++){
			song.play();
		}
	}
}