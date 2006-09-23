import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Sobre el Juego
 * @author Revolution Software Developers
 */
public class AboutFrame extends JFrame implements WindowListener {

	/**
	 * Constante de Eclipse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 */
	public AboutFrame() {
		//-- Preferencias de la pantalla
		this.setTitle("Buscaminas");
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		this.setIconImage(Main.getImage("logo.gif"));
		this.setSize(420,350);
		this.addWindowListener(this);
		this.setLocation(new Point(200,200));
	}
	
	/**
	 * On Close
	 * @param WindowEvent
	 */
	public void windowClosing(WindowEvent arg0) {
		System.out.println("Closing...");
		Main.buscaminas.setEnabled(true);
		this.dispose();
	}
	
	public void windowOpened(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowActivated(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}

}
