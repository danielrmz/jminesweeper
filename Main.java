import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;

/**
 * Inicializa la aplicacion del buscaminas
 * @author Revolutionary Software Developers
 */
public class Main {
	public static String ruta = "c:/fundamentosjava/JMinesweeper/";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Frame buscaminas = new Frame();
		buscaminas.setVisible(true);
	}

	/**
	 * Metodos Auxiliares fijos
	 */
	public static Image getImage(String filename){
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(Main.ruta+"img/"+filename);
		return image;
	}
	
	public static ImageIcon getIconImage(String filename){	
		ImageIcon image = new ImageIcon(Main.ruta+"img/"+filename);
		return image;
	}
	
}
