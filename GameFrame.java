import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.Border;

import com.sun.java.swing.plaf.windows.*;

/**
 * Clase Frame donde se crea la cuadricula y los menus de acceso al juego
 * @author Revolution Software Developers
 */
public class GameFrame extends JFrame implements ActionListener {
	
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
	 * Menu Item Mejores marcas
	 */
	private JMenuItem mejores = new JMenuItem("Mejores marcas");
	
	/**
	 * Estatus del juego, activo o inactivo
	 */
	public static boolean ACTIVE = true;
	
	/**
	 * Panel del grid y de los contadores/carita
	 */
	public JPanel principal = new JPanel(new BorderLayout());
	
	/**
	 * Face to restart
	 */
	public static JButton face = new JButton();
	
	/**
	 * Mines Segment Led
	 */
	public SevenSegment minesleft;
	
	/**
	 * Time Segment Led
	 */
	public SevenSegment time;
	
	/**
	 * Constructor, Inicializa el frame de la aplicación
	 */
	public GameFrame() {
		//-- Preferencias de la pantalla
		this.setTitle("Buscaminas");
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(Main.getImage("logo.gif"));
		this.setLocation(new Point(20,20));
		
		//-- Menus
		JMenu archivo = new JMenu("Archivo");
		JMenu ayuda = new JMenu("Ayuda");
		
		//-- Mnemonicos para el acceso rapido a los menus
		archivo.setMnemonic('A');
		ayuda.setMnemonic('y');
		nuevo.setMnemonic('N');
		contenido.setMnemonic('C');
		sobre.setMnemonic('S');
		principiantes.setMnemonic('P');
		intermedios.setMnemonic('I');
		expertos.setMnemonic('E');
		preferencias.setMnemonic('R');
		salir.setMnemonic('S');
		sonido.setMnemonic('O');
		mejores.setMnemonic('M');
		//-- Items del Menu
		archivo.add(nuevo);
		nuevo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		contenido.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
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
		sobre.addActionListener(this);
		
		//-- Se agrega el menu a la barra de menu
		menubar.add(archivo);
		menubar.add(ayuda);
		
		//-- Se establecen los paneles
		this.setJMenuBar(menubar);
		
		//-- Estadisticas
		GameFrame.face.setIcon(Main.getIconImage("face_happy.jpg"));
		GameFrame.face.setBorderPainted(false);
		GameFrame.face.addActionListener(this);
		GameFrame.face.setFocusable(false);
		
		this.getContentPane().add(principal,BorderLayout.CENTER);
		JPanel segments = new JPanel(new BorderLayout());
		segments.add(GameFrame.face,BorderLayout.CENTER);
		this.setVisible(true);
		Graphics g = this.getGraphics();
		System.out.println(g);
		minesleft = new SevenSegment(this,16,32);
		this.setVisible(false);
		segments.add(minesleft,BorderLayout.EAST);
		segments.add(GameFrame.face,BorderLayout.CENTER);
		principal.add(segments,BorderLayout.NORTH);
		
		//-- Se crea el grid de botones de acuerdo al nivel
		this.setLevel(1);
		
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
		} else if (e.getSource() == nuevo || e.getSource() == GameFrame.face){
			this.gameRestart(true);
		} else if(e.getSource() == principiantes){
			this.setLevel(1);
		} else if(e.getSource() == intermedios){
			this.setLevel(2);
		} else if(e.getSource() == expertos){
			this.setLevel(3);
		} else if(e.getSource() == contenido){
			Runtime run = Runtime.getRuntime();
			try { 
				//-- Se ejecuta internamente el comando html helper dada la ruta del archivo
				run.exec("hh ms-its:"+Main.ruta+"winmine.chm");
			} catch (Exception ex) {}
		} else if(e.getSource() == sobre){
			Main.buscaminas.setEnabled(false);
			AboutFrame a = new AboutFrame();
			a.setVisible(true);
			
		}
	}
	
	/**
	 * Nivel de Juego, 
	 * se crea un nuevo grid, y se añade al canvas, se modifica también el tamaño del juego
	 */
	private void setLevel(int nivel){
		switch(nivel){
		case 1: //-- Principiante 
			this.grid = new Grid(9,9);
			this.setSize(150,230);
			break;
		case 2: //-- Intermedio
			this.grid = new Grid(16,16);
			this.setSize(270,330);
			break;
		case 3: //-- Avanzado
			this.grid = new Grid(16,30);
			this.setSize(510,330);
			break;
		}
		
		//-- Se remueve el componente de grid viejo
		if(principal.getComponents().length>1)
			principal.remove(1); //[Grid]
		
		//-- Se agrega
		principal.add(this.grid,BorderLayout.CENTER);
		
		//-- Se actualiza la interfaz
		SwingUtilities.updateComponentTreeUI(this);
		
		//-- El juego se activa [por si habia perdido]
		this.gameRestart(false);
	}
	
	/**
	 * Se encarga de poner el juego activo, asi como reiniciar los contadores
	 */
	private void gameRestart(boolean gridreset){
		GameFrame.face.setIcon(Main.getIconImage("face_happy.jpg"));
		GameFrame.setActive(true);
		if(gridreset){
			this.grid.reset();
		}
		//TODO: Agregar inicializacion de contadores aqui
	}
	
	/**
	 * Trae si el juego esta activo o no
	 * @return
	 */
	public static boolean getActive(){
		return GameFrame.ACTIVE;
	}
	
	/**
	 * Activa el juego
	 * @param active
	 */
	public static void setActive(boolean active){
		GameFrame.ACTIVE = active;
	}
}
