

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;


/**
 * Clase que crea la cuadricula
 * @author Revolutionary Software Developers
 */
public class Grid extends JPanel implements MouseInputListener {
	
	private static final long serialVersionUID = 1L;
	
	protected int rows;
	protected int cols;
	
	public Boton[][] grid; // Arreglo de botones
	
	/**
	 *  Constructor vacio
	 */
	public Grid(int rows,int cols) {
		this.rows = rows;
		this.cols = cols;
		this.setLayout(new GridLayout(rows,cols));
		this.makeGrid();
	}
	
	/**
	 * Crea la matriz de botones.
	 *
	 */
	private void makeGrid(){
		int [][] data = this.randomBombs();
		this.grid = new Boton[this.rows][this.cols];
		
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Image image = toolkit.getImage(Main.ruta+"img/rifle.gif");
		Cursor cursor = toolkit.createCustomCursor(image, new Point(0,0), "rifle");
		
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[i].length;j++){
				this.grid[i][j] = new Boton(Boton.UNCLICKED,data[i][j]);
				this.grid[i][j].addMouseListener(this);
				this.grid[i][j].setCursor(cursor);
	            this.add(this.grid[i][j]);
			}
		}
	}
	
	/**
	 * Genera las bombas aleatoriamente
	 * la cantidad de bombas es el 30% del total.
	 */
	private int[][] randomBombs(){
		int [][]data = new int[this.rows][this.cols];
		int total = (int)(this.rows * this.cols * 0.3); //-- Total de bombas a generar
		
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[i].length;j++){
				boolean bomb = (Math.random()>0.5)?true:false;
				if(bomb){
					data[i][j] = -1;
					total--;
				}
			}
		}
		return data;
	}
	
	/**
	 * Resetea el GRID
	 */
	
	public void reset(){
		this.makeGrid();
	}
	
	/**
	 * Descubre las bombas 
	 */
	public void uncoverBombs(){
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[i].length;j++){
				int value = grid[i][j].value;
				if(value == Boton.BOMB){
					grid[i][j].setStatus(Boton.CLICKED);
				}
			}
		}
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
				this.uncoverBombs();
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
