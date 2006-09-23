import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * Clase que crea la cuadricula de botones
 * @author Revolution Software Developers
 */
public class Grid extends JPanel implements MouseListener {
	/**
	 * Constante de eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Cantidad de Renglones
	 */
	private int rows;
	
	/**
	 * Cantidad de Columnas
	 */
	private int cols;
	
	/**
	 * Arreglo de Botones
	 */
	private Boton[][] grid; // Arreglo de botones
	
	/**
	 *  Constructor 
	 *  @param rows Cantidad Renglones
	 *  @param cols Cantidad Columnas
	 */
	public Grid(int rows,int cols) {
		this.rows = rows;
		this.cols = cols;
		this.setLayout(new GridLayout(rows,cols));
		this.makeGrid();
	}
	
	/**
	 * Crea la matriz de botones.
	 */
	private void makeGrid(){
		int [][] data = this.enumeraTabla(this.randomBombs());
		this.grid = new Boton[this.rows][this.cols];
		
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[i].length;j++){
				this.grid[i][j] = new Boton(Boton.UNCLICKED,data[i][j], i, j);
				this.grid[i][j].addMouseListener(this);
				 this.add(this.grid[i][j]);
			}
		}
	}
	
	/**
	 * Genera las bombas aleatoriamente
	 * la cantidad de bombas es el 30% del total.
	 * 
	 * @return bombas aleatorias
	 */
	private int[][] randomBombs(){
		int [][]data = new int[this.rows][this.cols];
		int total = (int)(this.rows * this.cols * 0.3); //-- Total de bombas a generar
		
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[i].length;j++){
				boolean bomb = (Math.random()>0.8)?true:false;
				if(bomb){
					data[i][j] = -1;
					total--;
				}
			}
		}
		return data;
	}
	
	/**
	 * Descubre las bombas 
	 */
	private void uncoverBombs(){
		Frame.setActive(false);
		
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[i].length;j++){
				int value = grid[i][j].getValue();
				if(value == Boton.BOMB){
					grid[i][j].setStatus(Boton.CLICKED);
				}
			}
		}
	}
	
	/**
	 * Pondera la celda con la cantidad de bombas alrededor
	 * 
	 * @param datos
	 * @param indiceI
	 * @param indiceJ
	 * @return total de bombas alrededor
	 */
	private int ponderaTabla(int[][] datos, int indiceI, int indiceJ){
		int numero = 0;
		for(int i=(indiceI-1); i<=(indiceI+1); i++ ){
			for(int j=(indiceJ-1); j<=(indiceJ+1); j++){
				try{
					if(datos[i][j]==Boton.BOMB){
						numero++;
					}
				}catch(ArrayIndexOutOfBoundsException aiobe){}
			}
			
		}
		return numero;
	}
	
	/**
	 * Enumera la tabla con las bombas alrededor
	 * @param datos
	 * @return matriz
	 */
	private int[][] enumeraTabla(int[][] datos){
		int[][] matriz = datos;
		for (int i=0; i<datos.length; i++){
			for(int j=0; j<datos[i].length; j++){
				if(matriz[i][j] != Boton.BOMB){
					matriz[i][j]=ponderaTabla(matriz, i, j);
				}
			}
		}
		return matriz;
	}
	
	/**
	 * Despliega la tabla en Consola
	 */
	private void despliegaTabla(){
		for(int i = 0; i<this.cols; i++){
			for(int j = 0; j<this.cols-1; j++){
				System.out.print(this.grid[i][j].getValue()+"  ");
			}
			System.out.println(this.grid[i][this.cols-1].getValue());
		}
	}
	
	/**
	 * Abre las casillas vacias
	 * @param indi
	 * @param indj
	 */
	private void descubreCeros(int indi, int indj){
		this.grid[indi][indj].setStatus(Boton.CLICKED);
		for(int i=indi-1 ; i<=indi+1; i++){
			for(int j=indj-1; j<=indj+1; j++){
				try{
					if(this.grid[i][j].getValue() == 0 && this.grid[i][j].getStatus() == Boton.UNCLICKED){
						descubreCeros(i, j);
					} 
				}catch(ArrayIndexOutOfBoundsException aiobe){}
				
			}
		}
	}
	
	/**
	 * Resetea el grid de datos
	 */
	public void reset(){
		int [][] data = this.enumeraTabla(this.randomBombs());
		for(int i=0;i<this.grid.length;i++){
			for(int j=0;j<this.grid[i].length;j++){
				this.grid[i][j].setValue(data[i][j]);
				this.grid[i][j].setStatus(Boton.UNCLICKED);
			}
		}
		
	}
	/**
	 * Metodo del MouseInputListener que cambia el estatus del boton
	 */
	public void mousePressed(MouseEvent arg0) {
		Boton aux = (Boton)arg0.getSource();
		//-- Si esta activo el juego entonces que haga de acuerdo al boton que le pico
		if(Frame.getActive()){
			//-- Boton Izquierdo y si no ha sido oprimido
			if(arg0.getButton() == MouseEvent.BUTTON1 && aux.getStatus()==Boton.UNCLICKED){
				aux.setStatus(Boton.CLICKED);
				//-- Si es una bomba cambiale el estatus a DEAD[para que tenga otro bg] y presionala
				if(aux.getValue() == Boton.BOMB){
					aux.setValue(Boton.DEAD);
					aux.setStatus(Boton.CLICKED);
					//-- Descubre las demas bombas
					this.uncoverBombs();
					//-- Cambia la carilla
					Frame.face.setIcon(Main.getIconImage("face_lose.jpg"));
				} else if(aux.getValue() == Boton.NUMBER){ 
					//-- Descubre 0s si es casilla vacia
					this.descubreCeros(aux.x,aux.y);
				}
			
			//-- Si es boton derecho poner bandera
			} else if (arg0.getButton() == MouseEvent.BUTTON3 && (aux.getStatus() == Boton.UNCLICKED || aux.getStatus() == Boton.FLAGED )){ 
				int action = (aux.getStatus() == Boton.UNCLICKED)?Boton.FLAGED:Boton.UNCLICKED;
				//TODO: Restar o aumentar las banderas aqui de acuerdo a lo q le haya picado
				aux.setStatus(action);
			} else { 
				//-- Si tiene bandera y le pico poner carilla sorprendida
				Frame.face.setIcon(Main.getIconImage("face_surprised.jpg"));
			}
		}
	}
	
	/**
	 * Metodos sin usar del MouseListener
	 */
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseReleased(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseDragged(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent arg0) {}
	//TODO: Ver si se pueden quitar estos con el MouseInputListener de JAVAX
}
