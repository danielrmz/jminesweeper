import java.awt.*;
import java.awt.event.*;
import java.io.File;

import javax.swing.*;

/**
 * Clase que maneja los highscores de los juegos
 * @author Revolution Software Developers
 */
public class BestsFrame extends JDialog implements WindowListener, ActionListener {
	/**
	 * Listado de Ganadores con scores mas altos
	 */
	public String lista[][] = null; //Tipo, {Nombre, Tiempo}
	
	/**
	 * Constante de Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Boton de resetear resultados
	 */
	private JButton resetear = new JButton("Resetear");
	
	/**
	 * Boton de cerrar dialogo
	 */
	private JButton cerrar = new JButton("Cerrar");
	
	/**
	 * Boton de guardar score
	 */
	private JButton guardar = new JButton("Guardar");
	
	/**
	 * Nombre del jugador
	 */
	private JTextField nombre = new JTextField("",4);
	
	/**
	 * Constructor
	 * @param input decide si se agregara un textbox para agregar el nombre del mas alto
	 */
	public BestsFrame(boolean input){
		this.setTitle("Mejores marcas");
		this.getContentPane().setLayout(new BorderLayout());
		this.setResizable(false);
		this.setSize(320,150);
		this.addWindowListener(this);
		this.setLocation(new Point(200,200));
		this.init();
		
		JPanel scores = new JPanel(new GridLayout(3,3));
		JPanel buttons= new JPanel(new GridLayout(2,3));
		
		JLabel p = new JLabel("Principiante");
		JLabel i = new JLabel("Intermedios");
		JLabel e = new JLabel("Expertos");
		
		JLabel pn = new JLabel(lista[0][0]);
		JLabel in = new JLabel(lista[1][0]);
		JLabel en = new JLabel(lista[2][0]);
		
		JLabel pt = new JLabel(lista[0][1] + " s");
		JLabel it = new JLabel(lista[1][1] + " s");
		JLabel et = new JLabel(lista[2][1] + " s");
		
		scores.add(p);
		scores.add(pt);
		scores.add(pn);
		scores.add(i);
		scores.add(it);
		scores.add(in);
		scores.add(e);
		scores.add(et);
		scores.add(en);
		
		this.getContentPane().add(scores,BorderLayout.CENTER);
		
		if(!input){
			buttons.add(new JLabel(" "));
			buttons.add(new JLabel(" "));
			buttons.add(new JLabel(" "));
			buttons.add(resetear);
			buttons.add(new JLabel(" "));
			buttons.add(cerrar);
			
		} else { 
			buttons.add(new JLabel("Nombre:"));
			buttons.add(nombre);
			buttons.add(guardar);
			buttons.add(new JLabel(" "));
			buttons.add(new JLabel(" "));
			buttons.add(new JLabel(" "));
			guardar.addActionListener(this);
		}
		
		
		resetear.addActionListener(this);
		cerrar.addActionListener(this);
		this.getContentPane().add(buttons,BorderLayout.SOUTH);

	}
	
	/**
	 * Cierra la ventana
	 */
	private void close(){
		Main.buscaminas.setEnabled(true);
		this.dispose();
	}
	
	/**
	 * Inicializa las listas de ranks
	 */
	private void init(){
		if(this.lista==null) {
			this.lista = new String[3][2];
			
			Serial bf = new Serial("scores.ini");
			BestsFrame frame = (BestsFrame)bf.getObject();
			
			if(frame != null && frame.lista != null) {
				this.lista = frame.lista;
			} else {
				for(int i = 0; i<3; i++){
					lista[i][0] = "Desconocido";
					lista[i][1] = "999";
				}
			}
		}
	}
	
	/**
	 * Acciones de la ventana
	 */
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == cerrar){
			this.close();
		} else if(e.getSource() == resetear){
			new File(Main.RUTA+"scores.ini").delete();
			this.close();
		} else if(e.getSource() == guardar){
			
			int nivel = GameFrame.nivel - 1; //Porque se guarda el nivel en el rango 1..4 y en el arreglo empieza en 0
			String nombre = this.nombre.getText();
			int tmp = Main.buscaminas.grid.getTime();
			if(tmp==0) this.close();
			String tiempo = tmp + "";
			nombre = (nombre.equals(""))?"Desconocido":nombre;
			this.lista[nivel][0] = nombre;
			this.lista[nivel][1] = tiempo;
			new Serial("scores.ini",this);
			this.close();
		}
	}
	
	/**
	 * Al cerrar la ventana
	 */
	public void windowClosing(WindowEvent arg0) {
		this.close();
	}
	
	public void windowOpened(WindowEvent arg0) {}
	public void windowClosed(WindowEvent arg0) {}
	public void windowIconified(WindowEvent arg0) {}
	public void windowDeiconified(WindowEvent arg0) {}
	public void windowActivated(WindowEvent arg0) {}
	public void windowDeactivated(WindowEvent arg0) {}
	
}
