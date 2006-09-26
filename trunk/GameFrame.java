import java.awt.*;
import java.awt.event.*;
import java.io.File;

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
	 * Menu Bar
	 */
	private JMenuBar menubar = new JMenuBar();
	
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
	 * Menu Item Guardar
	 */
	private JMenuItem guardar = new JMenuItem("Guardar");
	
	/**
	 * Menu Item Abrir
	 */
	private JMenuItem abrir = new JMenuItem("Abrir");
	
	/**
	 * Menu Item Sonido
	 */
	public JCheckBoxMenuItem sonido = new JCheckBoxMenuItem("Sonido");
	
	/**
	 * Panel del grid y de los contadores/carita
	 */
	public JPanel principal;
	
	/**
	 * Estatus del juego, activo o inactivo
	 */
	public static boolean ACTIVE = true;
	
	/**
	 * Face to restart
	 */
	public JButton face = new JButton();

	/**
	 * Grid de Botones / Datos
	 */
	public Grid grid;
	
	/**
	 * Contador de banderas actuales
	 */
	public static int banderas = 0;
	
	/**
	 * Display del reloj
	 */
	public static JLabel ctiempo = new JLabel("000");
	
	/**
	 * Display de banderas 
	 */
	public static JLabel cbanderas = new JLabel("000");
	
	/**
	 * Constructor, Inicializa el frame de la aplicación
	 */
	public GameFrame() {
		BorderLayout b = new BorderLayout();
		b.setHgap(2);
		b.setVgap(2);
		BorderLayout bb = new BorderLayout();
		bb.setHgap(3);
		bb.setVgap(3);
		
		//-- Preferencias de la pantalla
		this.setTitle("Buscaminas");
		this.getContentPane().setLayout(b);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setIconImage(Main.getImage("logo.png"));
		this.setLocation(new Point(20,20));
		
		principal = new JPanel(bb);
		
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
		
		archivo.add(guardar);
		archivo.add(abrir);
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
		guardar.addActionListener(this);
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
		face.setIcon(Main.getIconImage("face_happy.jpg"));
		face.setBorder(BorderFactory.createEmptyBorder());
		face.setBorderPainted(false);
		face.addActionListener(this);
		face.setFocusable(false);
		
		this.getContentPane().add(new JLabel(" "),BorderLayout.WEST);
		this.getContentPane().add(new JLabel(" "),BorderLayout.EAST);
		
		this.getContentPane().add(principal,BorderLayout.CENTER);
		JPanel segments = new JPanel(new BorderLayout());
		Border lbevel = BorderFactory.createLoweredBevelBorder();
		segments.setBorder(lbevel);
		JPanel ab = new JPanel();
		ab.add(cbanderas);
		ab.setBackground(Color.BLACK);
		JPanel ac = new JPanel();
		ac.add(ctiempo);
		ac.setBackground(Color.BLACK);
		
		segments.add(ab,BorderLayout.WEST);
		segments.add(ac,BorderLayout.EAST);
		ctiempo.setFont(new Font("MS Dialog", Font.BOLD, 20));
		cbanderas.setFont(new Font("MS Dialog", Font.BOLD, 20));
		ctiempo.setForeground(Color.RED);
		cbanderas.setForeground(Color.RED);
		
		segments.add(face,BorderLayout.CENTER);
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
		if(e.getSource()!=salir && e.getSource() != preferencias){
			(new File(Main.RUTA+"preferencias.ini")).delete();
		}
		if(e.getSource()==salir){
			this.dispose();
		} else if (e.getSource() == nuevo || e.getSource() == face){
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
				run.exec("hh ms-its:"+Main.RUTA+"winmine.chm");
			} catch (Exception ex) {}
		} else if(e.getSource() == sobre){
			Main.buscaminas.setEnabled(false);
			AboutFrame a = new AboutFrame();
			a.setVisible(true);
		} else if(e.getSource() == preferencias){
			Main.buscaminas.setEnabled(false);
			PreferencesFrame a; 
			Serial pref = new Serial("preferencias.ini");
			a = (pref.getObject()!=null)?(PreferencesFrame)pref.getObject():new PreferencesFrame();
			a.setVisible(true);
		} else if(e.getSource() == guardar){
			//new Serial("Grid.obj",this.grid);
			//System.out.println("Juego Guardado");
		}
	}
	
	/**
	 * Nivel de Juego, 
	 * se crea un nuevo grid, y se añade al canvas, se modifica también el tamaño del juego
	 */
	public void setLevel(int nivel){
		if(this.grid !=null && this.grid.tiempo != null) {
			this.grid.tiempo.stop();
			GameFrame.ctiempo.setText("000");
		}
		switch(nivel){
		case 1: //-- Principiante 
			this.grid = new Grid(9,9); //cols,rows
			this.setSize(165,238);
			break;
		case 2: //-- Intermedio
			this.grid = new Grid(16,16);
			this.setSize(280,340);
			break;
		case 3: //-- Avanzado
			this.grid = new Grid(30,16);
			this.setSize(500,330);
			break;
		case 4: 
			
			int rows = PreferencesFrame.rows;
			int cols = PreferencesFrame.cols;
			int mines = PreferencesFrame.mines;
			this.grid = new Grid(cols,rows,mines);
			this.setSize(16*cols+40,16*rows+80);
			break;
		}
		
		GameFrame.banderas = this.grid.getMines();
		
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
		face.setIcon(Main.getIconImage("face_happy.jpg"));
		GameFrame.setActive(true);
		GameFrame.banderas = this.grid.getMines();
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
