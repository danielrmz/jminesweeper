

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.event.MouseInputListener;

public class Frame extends JFrame implements MouseInputListener {

	private static final long serialVersionUID = 1L;
	private Grid grid;
	
	/**
	 * Constructor, Inicializa el frame
	 *
	 */
	public Frame() {
		setBackground(Color.white);
		setSize(470,400);
		setTitle("Buscaminas");
		setLayout(new BorderLayout());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		JPanel top = new JPanel(new BorderLayout());
		JPanel game = new JPanel(new BorderLayout());
		
		this.grid = new Grid(10,10,this);
		game.add(this.grid,BorderLayout.CENTER);
		
		
		top.add(new Button("Empezar"),BorderLayout.CENTER);
		this.add(top,BorderLayout.NORTH);
		this.add(game,BorderLayout.CENTER);
	}

	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		Boton aux = (Boton)arg0.getSource();
		if(arg0.getButton() == MouseEvent.BUTTON1){
			aux.setStatus(Boton.CLICKED);
			if(aux.value == Boton.BOMB){
				aux.value = Boton.DEAD;
				aux.setStatus(Boton.CLICKED);
				
				this.grid.uncoverBombs();
			}
		} else if (arg0.getButton() == MouseEvent.BUTTON3){ 
			int action = (aux.status == Boton.UNCLICKED)?Boton.FLAGED:Boton.UNCLICKED;
			aux.setStatus(action);
		}
	}

	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
