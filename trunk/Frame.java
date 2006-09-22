import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.sun.java.swing.plaf.windows.*;


public class Frame extends JFrame implements ActionListener {
	/**
	 * Variable identificadora del Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Grid de Botones / Datos
	 */
	private Grid grid;
	
	/**
	 * Menu Bar
	 */
	JMenuBar menubar = new JMenuBar();
	
	
	/**
	 * Menu Item Nuevo
	 */
	private JMenuItem nuevo = new JMenuItem("Nuevo");
	
	/**
	 * Menu Item Principiantes
	 */
	private JMenuItem principiantes = new JMenuItem("Principiantes");
	
	/**
	 * Menu Item Intermedios
	 */
	private JMenuItem intermedios = new JMenuItem("Intermedios");
	
	/**
	 * Menu Item Experto
	 */
	private JMenuItem expertos = new JMenuItem("Expertos");
	
	/**
	 * Menu Item Preferencias
	 */
	private JMenuItem preferencias = new JMenuItem("Preferencias...");
	
	/**
	 * Menu Item Sonido
	 */
	private JCheckBoxMenuItem sonido = new JCheckBoxMenuItem("Sonido");
	
	/**
	 * Menu Item Salir
	 */
	private JMenuItem salir = new JMenuItem("Salir");
	
	/**
	 * Menu Item de Ayuda
	 */
	private JMenuItem contenido = new JMenuItem("Contenido");
	
	/**
	 * Menu Item Sobre
	 */
	private JMenuItem sobre = new JMenuItem("Sobre...");
	
	/**
	 * Constructor, Inicializa el frame de la aplicación
	 */
	public Frame() {
		//-- Preferencias de la pantalla
		this.setBackground(Color.white);
		this.setSize(470,400);
		this.setTitle("Buscaminas");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		//-- Menus
		JMenu archivo = new JMenu("Archivo");
		JMenu ayuda = new JMenu("Ayuda");
		
		archivo.setMnemonic('A');
		ayuda.setMnemonic('y');
		
		//-- Items del Menu
		archivo.add(nuevo);
		nuevo.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_N,
                java.awt.Event.CTRL_MASK));
		archivo.addSeparator();
		archivo.add(principiantes);
		archivo.add(intermedios);
		archivo.add(expertos);
		archivo.add(preferencias);
		archivo.addSeparator();
		archivo.add(sonido);
		archivo.addSeparator();
		archivo.add(salir);
		
		ayuda.add(contenido);
		ayuda.addSeparator();
		ayuda.add(sobre);
		
		//-- Listeners del menu
		nuevo.addActionListener(this);
		principiantes.addActionListener(this);
		intermedios.addActionListener(this);
		expertos.addActionListener(this);
		preferencias.addActionListener(this);
		sonido.addActionListener(this);
		salir.addActionListener(this);
		
		contenido.addActionListener(this);
		ayuda.addActionListener(this);
		
		//-- Se agrega el menu a la barra de menu
		menubar.add(archivo);
		menubar.add(ayuda);
		
		//-- Se crea el grid de botones
		this.grid = new Grid(10,10);
		
		//-- Se establecen los paneles
		this.setJMenuBar(menubar);
		this.getContentPane().add(this.grid,BorderLayout.CENTER);

	    try { 
	    	UIManager.setLookAndFeel(new WindowsLookAndFeel());
	    	SwingUtilities.updateComponentTreeUI(this);
	    } catch (UnsupportedLookAndFeelException e){
	    	System.out.println("Error: Windows LookAndFeel no esta soportado");
	    	this.dispose();
	    }
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if(e.getSource()==salir){
			this.dispose();
		} else if (e.getSource() == nuevo){
			this.grid.reset();
		}
	}
	
}
