import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;

import javax.swing.ImageIcon;

/**
 * Inicializa la aplicacion del buscaminas
 * @author Revolutionary Software Developers
 */
public class Main {
	public static String ruta = "";
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		File dir1 = new File ("");
		ruta = dir1.getAbsolutePath()+"/";
		
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
