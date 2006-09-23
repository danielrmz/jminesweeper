import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;

/**
 * Inicializa la aplicacion del buscaminas
 * @author Revolution Software Developers
 * @version 1.0 Revision 12
 */
public class Main {
	
	/**
	 * Ruta de la Aplicación
	 */
	public static String ruta = (new File ("")).getAbsolutePath()+"/";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		GameFrame buscaminas = new GameFrame();
		buscaminas.setVisible(true);
	}

	/**
	 * Regresa una imagen del directorio img/ dando nadamas su nombre
	 * @param filename
	 * @return image
	 */
	public static Image getImage(String filename){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(Main.ruta+"img/"+filename);
		return image;
	}
	
	/**
	 * Regresa el ImageIcon de una imagen especificada
	 * @param filename
	 * @return image
	 */
	public static ImageIcon getIconImage(String filename){	
		ImageIcon image = new ImageIcon(Main.ruta+"img/"+filename);
		return image;
	}
	
}
