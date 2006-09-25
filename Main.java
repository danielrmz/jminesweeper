import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;

/**
 * Inicializa la aplicacion del buscaminas, 
 * para versiones mas recientes visite <a href="http://code.google.com/p/jminesweeper/" target="_blank">http://code.google.com/p/jminesweeper/</a>
 * @author Revolution Software Developers
 * @version 0.5 Revision 18
 */
public class Main {
	
	/**
	 * Ruta de la Aplicación
	 */
	public static final String RUTA = (new File ("")).getAbsolutePath()+"/";
	
	/**
	 * Version
	 */
	public static final String VERSION = "0.5 Revisión 18";
	/**
	 * GameFrame
	 */
	public static GameFrame buscaminas;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		buscaminas = new GameFrame();
		buscaminas.setVisible(true);
	}

	/**
	 * Regresa una imagen del directorio img/ dando nadamas su nombre
	 * @param filename
	 * @return image
	 */
	public static Image getImage(String filename){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(Main.RUTA+"img/"+filename);
		return image;
	}
	
	/**
	 * Regresa el ImageIcon de una imagen especificada
	 * @param filename
	 * @return image
	 */
	public static ImageIcon getIconImage(String filename){	
		ImageIcon image = new ImageIcon(Main.RUTA+"img/"+filename);
		return image;
	}
	
}
