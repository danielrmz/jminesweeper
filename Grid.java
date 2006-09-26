import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import javax.swing.border.Border;

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
	 * Total de Bombas
	 */
	private int nbombas = -1;
	
	/**
	 * Tiempo usado para poner todas las bombas
	 */
	private int time = 0;
	
	/**
	 * Timer 
	 */
	Timer tiempo = null;
	
	/**
	 *  Constructor 
	 *  @param rows Cantidad Renglones
	 *  @param cols Cantidad Columnas
	 */
	public Grid(int cols,int rows) {
		Border raisedbevel = BorderFactory.createLoweredBevelBorder();
		
		this.rows = rows;
		this.cols = cols;
		this.setLayout(new GridLayout(rows,cols,0,0));
		this.setBorder(raisedbevel);
		this.makeGrid();
		if(Main.debug){
			this.despliegaTabla();
		}
	}
	
	/**
	 * Constructor para un numero de bombas definidas
	 * @param cols columnas
	 * @param rows renglones
	 * @param nbombs cantidad de bombas
	 */
	public Grid(int cols, int rows,int nbombs) {
		Border raisedbevel = BorderFactory.createLoweredBevelBorder();
		
		this.rows = rows;
		this.cols = cols;
		this.nbombas = nbombs;
		this.setLayout(new GridLayout(rows,cols,0,0));
		this.setBorder(raisedbevel);
		this.makeGrid();
		if(Main.debug){
			this.despliegaTabla();
		}
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
		
		int min = (int)(this.rows * this.cols * 0.2); //-- Total de bombas a generar
		int max = (int)(this.rows * this.cols * 0.8); //-- Maximo de bombas posibles
		int total = min;
		
		//-- Si no excede el numero de bombas permitidas
		if(this.nbombas <= max && this.nbombas > min){
			total = this.nbombas;
		} else { this.nbombas = total; }
		String extras = "";
		if(total<10) extras = "00";
		else if(total<100) extras = "0";
		GameFrame.cbanderas.setText(extras+total);
		
		for(int i=0;i<data.length;i++){
			for(int j=0;j<data[i].length;j++){
				boolean bomb = (Math.random()>0.8)?true:false;
				if(bomb && total > 0){
					data[i][j] = -1;
					total--;
				}
			}
		}
		return data;
	}
	
	/**
	 * Descubre las bombas al terminar el juego
	 */
	private void uncoverBombs(){
		GameFrame.setActive(false);
		
		if(Main.buscaminas.sonido.getState()){
			new Sound("bomb.wav",true); //-- Reproducir en el constructor
		}
	
		for(int i=0;i<grid.length;i++){
			for(int j=0;j<grid[i].length;j++){
				int value = grid[i][j].getValue();
				int status = grid[i][j].getStatus();
				
				if(value == Boton.BOMB && status != Boton.FLAGED ){
					grid[i][j].setStatus(Boton.CLICKED);
				} else if (status == Boton.FLAGED && value != Boton.BOMB){
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
	 * Despliega la tabla en Consola para ver los valores de las casillas
	 */
	private void despliegaTabla(){
		String separadores = "";
		for(int i = 0; i<this.cols; i++){
			separadores += "===";
		}
		
		System.out.println(separadores);
		for(int i = 0; i<this.rows; i++){
			System.out.print(" ");
			for(int j = 0; j<this.cols; j++){
				String value = ((""+this.grid[i][j].getValue()).equals("-1"))?"*":this.grid[i][j].getValue()+"";
				System.out.print(value+"  ");
			}
			System.out.println();
		}
		System.out.println(separadores);
	}
	
	/**
	 * Abre las casillas vacias
	 * @param indi
	 * @param indj
	 */
	private void descubreCeros(int indi, int indj){
		//-- Se abre la casilla actual
		this.grid[indi][indj].setStatus(Boton.CLICKED);
		
		//-- Se verifica alrededor de ella para seguir abriendo
		for(int i=indi-1 ; i<=indi+1; i++){
			for(int j=indj-1; j<=indj+1; j++){
				try{
					//-- Si es una casilla vacia y no ha sido abierta se expande
					if(this.grid[i][j].getValue() == 0 && this.grid[i][j].getStatus() == Boton.UNCLICKED){
						descubreCeros(i, j);
					} 
					else if (this.grid[i][j].getValue()!=0 && this.grid[i][j].getStatus()==Boton.UNCLICKED){
						this.grid[i][j].setStatus(Boton.CLICKED);
					}
				}catch(ArrayIndexOutOfBoundsException aiobe){}
				
			}
		}
	}
	
	/**
	 * Resetea el grid de datos
	 */
	public void reset(){
		//-- Genera nuevas bombas
		int [][] data = this.enumeraTabla(this.randomBombs());
		
		//-- Resetea la cuadricula actual, dandole valores y reestableciendo estatus
		for(int i=0;i<this.grid.length;i++){
			for(int j=0;j<this.grid[i].length;j++){
				this.grid[i][j].setValue(data[i][j]);
				this.grid[i][j].setStatus(Boton.UNCLICKED);
			}
		}
		if(tiempo!=null){
			tiempo.stop();
			time = 0;
			GameFrame.ctiempo.setText("000");
		}
		if(Main.debug){
			this.despliegaTabla();
		}
	}
	
	public void setBombs(int bombs){
		this.nbombas = bombs;
	}
	
	/**
	 * Metodo del MouseInputListener que cambia el estatus del boton
	 */
	public void mousePressed(MouseEvent arg0) {
		Boton aux = (Boton)arg0.getSource();
		//-- Si esta activo el juego entonces que haga de acuerdo al boton que le pico
		if(GameFrame.getActive()){
			if(tiempo==null || !tiempo.isRunning()){
				if(tiempo==null){
				tiempo = new Timer(1000, new ActionListener(){ public void actionPerformed(ActionEvent arg0) {
				if(time!=999){
					time += 1;
					String extras = "";
					if(time<10) extras = "00";
					else if(time<100) extras = "0";
					GameFrame.ctiempo.setText(extras+time+"");
				}
			}});}
			tiempo.start();
			}
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
					Main.buscaminas.face.setIcon(Main.getIconImage("face_lose.jpg"));
				} else if(aux.getValue() == Boton.NUMBER){ 
					//-- Descubre 0s si es casilla vacia
					this.descubreCeros(aux.x,aux.y);
				}
			
			//-- Si es boton derecho poner bandera
			} else if (arg0.getButton() == MouseEvent.BUTTON3 && (aux.getStatus() == Boton.UNCLICKED || aux.getStatus() == Boton.FLAGED )){ 
				int action = (aux.getStatus() == Boton.UNCLICKED)?Boton.FLAGED:Boton.UNCLICKED;
				if(action == Boton.UNCLICKED) GameFrame.banderas++; else GameFrame.banderas--;
				String extras = "";
				if(GameFrame.banderas>=0 && GameFrame.banderas<10) extras = "00";
				else if(GameFrame.banderas>=0&&GameFrame.banderas<100) extras = "0";
				GameFrame.cbanderas.setText(extras+GameFrame.banderas);
				aux.setStatus(action);
				System.out.println(GameFrame.banderas);
				if(this.allBombsFlaged()){
					GameFrame.setActive(false);
					tiempo.stop();
					System.out.println("Ganaste. Juego Terminado. Tiempo: "+this.time+" segundos.");
					Main.buscaminas.face.setIcon(Main.getIconImage("cool.png"));
				}
				
				
			} else if(aux.getStatus() == Boton.FLAGED){ 
				//-- Si tiene bandera y le pico poner carilla sorprendida
				Main.buscaminas.face.setIcon(Main.getIconImage("face_surprised.jpg"));
			}
		}
	}
	
	/**
	 * Para cambiar la carilla si esta en surprised
	 */
	public void mouseReleased(MouseEvent arg0) {
		Boton aux = (Boton)arg0.getSource();
		//-- Si el juego esta activo y no le pico a la bomba
		if(aux.getValue() != Boton.DEAD && GameFrame.getActive()){
			Main.buscaminas.face.setIcon(Main.getIconImage("face_happy.jpg"));
		}
	}
	
	/**
	 * Metodo que verifica que si todas las bombas estan banderadas
	 * @return boolean
	 */
	public boolean allBombsFlaged(){
		int bflaged = 0;
		for(int i=0;i<this.grid.length;i++){
			for(int j=0;j<this.grid[i].length;j++){
				Boton aux = this.grid[i][j];
				if(aux.getStatus() == Boton.FLAGED && aux.getValue() == Boton.BOMB){
					bflaged++;
				}
			}	
		}
		return (GameFrame.banderas==0 && bflaged == this.nbombas)?true:false;
	}
	
	/**
	 * Cuenta las flags puestas en las bombas
	 * @return
	 */
	public int countFlags(){
		int bflaged = 0;
		for(int i=0;i<this.grid.length;i++){
			for(int j=0;j<this.grid[i].length;j++){
				Boton aux = this.grid[i][j];
				if(aux.getStatus() == Boton.FLAGED && aux.getValue() == Boton.BOMB){
					bflaged++;
				}
			}	
		}
		return bflaged;
	}
	
	/**
	 * Trae el numero de renglones
	 * @return
	 */
	public int getRows(){ 
		return this.rows; 
	}
	
	/**
	 * Trae el numero de columnas
	 * @return
	 */
	public int getCols(){ 
		return this.cols; 
	}
	
	/**
	 * Trae el numero de minas
	 * @return
	 */
	public int getMines(){ 
		return this.nbombas; 
	}
	
	/**
	 * Trae el tiempo total que duro la persona
	 * @return
	 */
	public int getTime(){
		return this.time;
	}
	
	/*
	 * Metodos sin usar del MouseListener
	 */
	public void mouseClicked(MouseEvent arg0) {}
	public void mouseEntered(MouseEvent arg0) {}
	public void mouseExited(MouseEvent arg0) {}
	public void mouseDragged(MouseEvent arg0) {}
	public void mouseMoved(MouseEvent arg0) {}

	
}
