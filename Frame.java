import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import com.sun.java.swing.plaf.windows.*;

/**
 * Clase Frame donde se crea la cuadricula y los menus de acceso al juego
 * @author Revolutionary Software Developers
 */
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
	 * Estatus del juego, activo o inactivo
	 */
	public static boolean ACTIVE = true;
	
	/**
	 * Panel del grid y de los contadores/carita
	 */
	public JPanel principal = new JPanel(new GridLayout(1,3));
	
	/**
	 * Face to restart
	 */
	public static JButton face = new JButton();
	
	/**
	 * Constructor, Inicializa el frame de la aplicación
	 */
	public Frame() {
		//-- Preferencias de la pantalla
		this.setBackground(Color.white);
		this.setTitle("Buscaminas");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(Main.getImage("logo.gif"));
		
		//-- Menus
		JMenu archivo = new JMenu("Archivo");
		JMenu ayuda = new JMenu("Ayuda");
		
		//-- Mnemonicos para el acceso rapido a los menus
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
		
		//-- Se crea el grid de botones de acuerdo al nivel
		this.setLevel(1);
		
		//-- Se establecen los paneles
		this.setJMenuBar(menubar);
		
		//-- Estadisticas
		Frame.face.setIcon(Main.getIconImage("face_happy.jpg"));
		Frame.face.setBorderPainted(false);
		Frame.face.addActionListener(this);
		
		principal.add(Frame.face);
		this.getContentPane().add(principal,BorderLayout.NORTH);
		
		//-- Look n' Feel a la Windows Style
	    try { 
	    	UIManager.setLookAndFeel(new WindowsLookAndFeel());
	    	SwingUtilities.updateComponentTreeUI(this);
	    } catch (UnsupportedLookAndFeelException e){
	    	System.out.println("Error: Windows LookAndFeel no esta soportado");
	    	this.dispose();
	    }
	}

	/**
	 * Action Performed al hacer click en algun item del menu
	 * @param e Action Event
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource()==salir){
			this.dispose();
		} else if (e.getSource() == nuevo || e.getSource() == Frame.face){
			Frame.face.setIcon(Main.getIconImage("face_happy.jpg"));
			Frame.ACTIVE = true;
			this.grid.reset();
		} else if(e.getSource() == principiantes){
			this.setLevel(1);
		} else if(e.getSource() == intermedios){
			this.setLevel(2);
		} else if(e.getSource() == expertos){
			this.setLevel(3);
		}
	}
	
	/**
	 * Nivel de Juego
	 */
	public void setLevel(int nivel){
		switch(nivel){
		case 1: //-- Principiante 
			this.grid = new Grid(9,9);
			this.setSize(180,230);
			break;
		case 2: //-- Intermedio
			this.grid = new Grid(16,16);
			this.setSize(300,330);
			break;
		case 3: //-- Avanzado
			this.grid = new Grid(16,30);
			this.setSize(600,330);
			break;
		}
		this.getContentPane().removeAll();
		Frame.face.setIcon(Main.getIconImage("face_happy.jpg"));
		this.getContentPane().add(principal,BorderLayout.NORTH);
		
		this.getContentPane().add(this.grid,BorderLayout.CENTER);
		SwingUtilities.updateComponentTreeUI(this);
		Frame.ACTIVE = true;
	}
	
}
