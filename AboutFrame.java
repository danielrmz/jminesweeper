import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

/**
 * Sobre el Juego
 * @author Revolution Software Developers
 */
public class AboutFrame extends JDialog implements WindowListener, ActionListener {

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
		this.getContentPane().setLayout(new BorderLayout());
		this.setResizable(false);
		ImageIcon logo = Main.getIconImage("logo.png");
		this.setSize(420,250);
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
		JPanel auxleft = new JPanel(new BorderLayout());
		
		j.setSize(new Dimension(34,63));
		auxleft.add(l,BorderLayout.NORTH);
		auxleft.add(j,BorderLayout.SOUTH);
		left.add(new JLabel("    "),BorderLayout.WEST);
		left.add(new JLabel("  "),BorderLayout.EAST);
		
		left.add(auxleft,BorderLayout.CENTER);
		
		centro.add(left,BorderLayout.WEST);
		centro.add(new JLabel(" "),BorderLayout.NORTH);
		JPanel contenido = new JPanel(new GridLayout(6,1));
		contenido.add(new JLabel("Revsoft � JMinesweeper "));
		contenido.add(new JLabel("Versi�n "+Main.VERSION));
		contenido.add(new JLabel("Derechos Reservados 2006  "));
		contenido.add(new JLabel("Revolution Software Developers"));
		JPanel auxcontenido = new JPanel(new BorderLayout());
		auxcontenido.add(contenido,BorderLayout.CENTER);
		auxcontenido.add(new JLabel("   "),BorderLayout.WEST);
		centro.add(auxcontenido,BorderLayout.CENTER);
		JPanel auxcerrar = new JPanel(new FlowLayout());
		JButton cerrar = new JButton("Cerrar");
		cerrar.addActionListener(this);
		contenido.add(auxcerrar);
		auxcerrar.add(cerrar);
		centro.add(auxcerrar,BorderLayout.SOUTH);
		this.add(centro,BorderLayout.CENTER);
	}
	
	/**
	 * On Close
	 */
	public void windowClosing(WindowEvent arg0) {
		Main.buscaminas.setEnabled(true);
		this.dispose();
	}
	
	/**
	 * Para cerrar la pantalla
	 */
	public void actionPerformed(ActionEvent arg0) {
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
