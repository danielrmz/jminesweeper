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
		this.setTitle("Sobre el Buscaminas...");
		this.setLayout(new BorderLayout());
		this.setResizable(false);
		ImageIcon logo = Main.getIconImage("logo.png");
		this.setIconImage(Main.getImage("logo.png"));
		this.setSize(420,350);
		this.addWindowListener(this);
		this.setLocation(new Point(200,200));
		
		ImageIcon img = Main.getIconImage("banner.png");
		JLabel top = new JLabel(img);
		top.setSize(415,70);
		this.add(top,BorderLayout.NORTH);
		
		JPanel centro = new JPanel(new BorderLayout());
		ImageIcon java = Main.getIconImage("java.gif");
		
		JLabel l = new JLabel(logo);
		l.setSize(new Dimension(32,60));
		JLabel j = new JLabel(java);
		JPanel left = new JPanel(new BorderLayout());
		
		j.setSize(new Dimension(34,63));
		left.add(l,BorderLayout.NORTH);
		left.add(j,BorderLayout.SOUTH);
		
		centro.add(left,BorderLayout.EAST);
		
		this.add(centro,BorderLayout.CENTER);
	}
	
	/**
	 * On Close
	 * @param WindowEvent
	 */
	public void windowClosing(WindowEvent arg0) {
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
