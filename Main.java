import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import javax.swing.ImageIcon;

/**
 * Inicializa la aplicacion del buscaminas, 
 * para versiones mas recientes visite <a href="http://code.google.com/p/jminesweeper/" target="_blank">http://code.google.com/p/jminesweeper/</a>
 * @author Revolution Software Developers
 * @version 0.5 Revision 27
 */
public class Main {
	
	/**
	 * Ruta de la Aplicación
	 */
	public static final String RUTA = (new File ("")).getAbsolutePath()+"/";
	
	/**
	 * Version
	 */
	public static final String VERSION = "0.5 Revisión 27";
	/**
	 * GameFrame
	 */
	public static GameFrame buscaminas;
	
	/**
	 * Debug mode
	 */
	public static boolean debug = false;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		PreferencesFrame prefs = (PreferencesFrame)new Serial("preferencias.ini").getObject();
		buscaminas = new GameFrame();
		if(prefs!=null){
			//-- Cargar preferencias si existen
			int rows = Integer.parseInt(prefs.txtAlto.getText());
			int cols = Integer.parseInt(prefs.txtAlto.getText());
			int mines = Integer.parseInt(prefs.txtMinas.getText());
			PreferencesFrame.rows = rows;
			PreferencesFrame.cols = cols;
			PreferencesFrame.mines = mines;
			buscaminas.setLevel(4);
		} 
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
