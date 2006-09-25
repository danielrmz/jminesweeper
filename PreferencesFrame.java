import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Preferencias del Juego
 * @author Revolution Software Developers
 */
public class PreferencesFrame extends JDialog implements WindowListener, ActionListener {

	/**
	 * Constante de Eclipse
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Boton de OK
	 */
	private JButton cmdOk = new JButton("OK");
	
	/**
	 * Boton de cancelar
	 */
	private JButton cmdCancel = new JButton("Cancelar");
	
	/**
	 * Alto del grid
	 */
	public JTextField txtAlto = new JTextField(Main.buscaminas.grid.getRows()+"",4);
	
	/**
	 * Ancho del Grid
	 */
	public JTextField txtAncho = new JTextField(Main.buscaminas.grid.getCols()+"",4);
	
	/**
	 * Cantidad de Minas
	 */
	public JTextField txtMinas = new JTextField(Main.buscaminas.grid.getMines()+"",4);
	
	public static int rows = 0;
	public static int cols = 0;
	public static int mines = 0;
	/**
	 * Constructor
	 */
	public PreferencesFrame() {
		//-- Preferencias de la pantalla
		this.setTitle("Preferencias");
		this.getContentPane().setLayout(new BorderLayout());
		this.setResizable(false);
		this.setSize(200,120);
		this.addWindowListener(this);
		this.setLocation(new Point(200,200));
		
		JPanel contenido = new JPanel(new GridLayout(3,2));
		JLabel lblAlto = new JLabel("Alto");
		JLabel lblAncho = new JLabel("Ancho");
		JLabel lblMinas = new JLabel("Minas");
		
		cmdOk.addActionListener(this);
		cmdCancel.addActionListener(this);
		
		JPanel pnlAlto = new JPanel(new FlowLayout());
		pnlAlto.add(lblAlto);
		pnlAlto.add(txtAlto);
		contenido.add(pnlAlto);
		contenido.add(cmdOk);
		JPanel pnlAncho = new JPanel(new FlowLayout());
		pnlAncho.add(lblAncho);
		pnlAncho.add(txtAncho);
		contenido.add(pnlAncho);
		contenido.add(cmdCancel);
		
		JPanel pnlMinas = new JPanel(new FlowLayout());
		pnlMinas.add(lblMinas);
		pnlMinas.add(txtMinas);
		contenido.add(pnlMinas);
		contenido.add(new JLabel(" "));
		
		this.getContentPane().add(contenido,BorderLayout.CENTER);
	}
	
	/**
	 * On Close
	 * @param WindowEvent
	 */
	public void windowClosing(WindowEvent arg0) {
		Main.buscaminas.setEnabled(true);
		this.dispose();
	}
	
	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == cmdOk){
			PreferencesFrame.rows = Integer.parseInt(txtAlto.getText());
			PreferencesFrame.cols = Integer.parseInt(txtAncho.getText());
			PreferencesFrame.mines = Integer.parseInt(txtMinas.getText());
			if(rows>=9 && cols >=9 && mines >= 10 && rows<50 && cols<50 && mines<(rows*cols*0.8)){
				Main.buscaminas.setLevel(4);
				new Serial("preferencias.ini",this);
			} else { 
				System.out.println("Error: tamaño inválido, no puede exceder un grid de 50x50");
			}
		}
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