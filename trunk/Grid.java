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
	public int time = 0;
	
	/**
	 * Safe Click
	 */
	private boolean safeClicked = false;
	
	/**
	 * Indica si ya le pico a alguna casilla
	 */
	private boolean clicked = false;
	/**
	 * Timer 
	 */
	public Timer tiempo = null;
	
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
		
		int min = (int)(this.rows * this.cols * 0.15); //-- Total de bombas a generar
		int max = (int)(this.rows * this.cols * 0.8); //-- Maximo de bombas posibles
		int total = min;
		
		//-- Si no excede el numero de bombas permitidas
		if(this.nbombas <= max && this.nbombas > -1){
			total = this.nbombas;
		} else { this.nbombas = total; }
		String extras = "";
		if(total<10) extras = "00";
		else if(total<100) extras = "0";
		GameFrame.cbanderas.setText(extras+total);
		
		for(int i=0;i<data.length && total>0; i++){
			for(int j=0;j<data[i].length && total>0; j++){
				boolean bomb = (Math.random()>0.8)?true:false;
				if(bomb && total > 0){
					data[i][j] = -1;
					total--;
				}
				//-- si faltaron bombas
				if(i==data.length-1 && j == data[i].length-1 && total>0){
					i=0;
					j=0;
				}
					
			}
		}
		if(Main.debug)
			System.out.println("Minas restantes por alocar: "+total);
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
	 * Mueve la bomba a otro lugar
	 */
	private void moveBomb(Boton b){
		int[][] data = new int[this.rows][this.cols];
		boolean ok = false;
		//se busca otro lugar para la bomba
		for(int i=0; i<this.grid.length && !ok; i++){
			for(int j=0; j<this.grid[i].length && !ok; j++){
				if(this.grid[i][j].getValue() != Boton.BOMB){
					this.grid[i][j].setValue(Boton.BOMB);
					b.setValue(Boton.NUMBER);
					ok = true;
				}
			}
			
		}
		//-- se guardan los datos enu n arreglo
		for(int i=0; i<this.grid.length; i++){
			for(int j=0; j<this.grid[i].length; j++){
				int value = this.grid[i][j].getValue();
				value = (value>=0)?0:value;
				data[i][j] = value;
			}
		}
		//-- Se vuelve a ponderar la tabla
		data = this.enumeraTabla(data);
		//-- Se vacian los datos a los botones
		for(int i=0; i<this.grid.length; i++){
			for(int j=0; j<this.grid[i].length; j++){
				this.grid[i][j].setValue(data[i][j]);
			}
		}
	}
	
	/**
	 * Metodo que cuenta si la cuadricula ya ha sido clickeada exceptuando las bombas
	 * @return ok booleano
	 */
	private boolean buttonequalBomb(){
		int cleanbuttonclicked = 0;
		for(int i=0;i<this.grid.length;i++){
			for(int j=0;j<this.grid[i].length;j++){
				Boton auxclickeds = this.grid[i][j];
				if(auxclickeds.getStatus() == Boton.CLICKED && auxclickeds.getValue() >= Boton.NUMBER){
					cleanbuttonclicked++;
				}
			}	
		}
		return (((this.rows*this.cols) - cleanbuttonclicked) == this.nbombas)?true:false;
	}
	
	/**
	 * Flagea los botones restantes
	 */
	private void flagleftbuttons()
	{
		for(int i=0;i<this.grid.length;i++){
			for(int j=0;j<this.grid[i].length;j++){
				Boton checker = this.grid[i][j];
				if(checker.getStatus() == Boton.UNCLICKED)
					checker.setStatus(Boton.FLAGED);
			}
		}
	}
	
	/**
	 * Establece las variables y cosas de entorno al ya ganar
	 */
	private void win(){
		GameFrame.setActive(false);
		if(tiempo!=null){
			tiempo.stop();
		}
		if(Main.buscaminas.sonido.getState()){
			new Sound("win.wav",true); //-- Reproducir en el constructor
		}
		if(Main.debug){
			System.out.println("Ganaste. Juego Terminado. Tiempo: "+this.time+" segundos.");
		}
		Main.buscaminas.face.setIcon(Main.getIconImage("cool.png"));	
		
		//Se checa si es highscore
		Serial scores = new Serial("scores.ini");
		
		if(scores.getObject() != null){
			BestsFrame b = (BestsFrame)scores.getObject();
			int actual = Integer.parseInt(b.lista[GameFrame.nivel][1]);
			if(this.time<actual){
				BestsFrame n = new BestsFrame(true);
				n.setVisible(true);
				Main.buscaminas.setEnabled(false);
			}
		} else {
			
			BestsFrame n = new BestsFrame(true);
			n.setVisible(true);
			Main.buscaminas.setEnabled(false);
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
		
		this.safeClicked = false;
		this.clicked = false;
	}
	
	/**
	 * Establece las bombas del grid 
	 * @param bombs Bombas
	 */
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
			//-- Inicializacion del tiempo
			if(tiempo==null || !tiempo.isRunning()){
				if(tiempo==null){
				tiempo = new Timer(1000, new ActionListener(){ public void actionPerformed(ActionEvent arg0) {
				if(time!=999){
					time += 1;
					String extras = "";
					if(time<10) extras = "00";
					else if(time<100) extras = "0";
					GameFrame.ctiempo.setText(extras+time+"");
					if(Main.buscaminas.sonido.getState()){
						new Sound("timer.wav",true); //-- Reproducir en el constructor
					}
				}
			}});}
			tiempo.start();
			}
			//-- Boton Izquierdo y si no ha sido oprimido
			if(arg0.getButton() == MouseEvent.BUTTON1 && aux.getStatus()==Boton.UNCLICKED){
				aux.setStatus(Boton.CLICKED);
				//-- Si es una bomba cambiale el estatus a DEAD[para que tenga otro bg] y presionala
				if(aux.getValue() == Boton.BOMB){
					if(!this.safeClicked && !this.clicked){
						this.moveBomb(aux);
						aux.setStatus(Boton.CLICKED);
						this.safeClicked = true;
						//-- Descubre las bombas si el valor nuevo de la casilla es nulo
						if(aux.getValue() == Boton.NUMBER){
							this.descubreCeros(aux.x,aux.y);
						}
						
					} else {
						aux.setValue(Boton.DEAD);
						aux.setStatus(Boton.CLICKED);
						if(tiempo!=null) {
							tiempo.stop();
						}
						//-- Descubre las demas bombas
						this.uncoverBombs();
						//-- Cambia la carilla
						Main.buscaminas.face.setIcon(Main.getIconImage("face_lose.jpg"));
					}
				} else if(aux.getValue() == Boton.NUMBER){ 
					//-- Descubre 0s si es casilla vacia
					this.descubreCeros(aux.x,aux.y);
					if(this.buttonequalBomb()){
						this.win();
						this.flagleftbuttons();
					}
				} else if(aux.getValue() > Boton.NUMBER){  
					if(this.buttonequalBomb()){
						this.win();
						this.flagleftbuttons();
					}
				}
				this.clicked = true;
			//-- Si es boton derecho poner bandera
			} else if (arg0.getButton() == MouseEvent.BUTTON3 && (aux.getStatus() == Boton.UNCLICKED || aux.getStatus() == Boton.FLAGED )){ 
				
				int action = (aux.getStatus() == Boton.UNCLICKED)?Boton.FLAGED:Boton.UNCLICKED;
				if(action == Boton.UNCLICKED) GameFrame.banderas++; else GameFrame.banderas--;
				String extras = "";
				if(GameFrame.banderas>=0 && GameFrame.banderas<10) extras = "00";
				else if(GameFrame.banderas>=0&&GameFrame.banderas<100) extras = "0";
				GameFrame.cbanderas.setText(extras+GameFrame.banderas);
				aux.setStatus(action);
				
				if(this.allBombsFlaged()){
					this.win();
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
			Main.buscaminas.face.setIcon(Main.getIconImage("face_happy"+Boton.tematica+".jpg"));
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
	 * @return bflaged el total de bombas flageadas
	 */
	public int countBombedFlags(){
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
	 * Cuenta las flags puestas
	 * @return int el total de casillas banderadas
	 */
	public int countFlags(){
		int bflaged = 0;
		for(int i=0;i<this.grid.length;i++){
			for(int j=0;j<this.grid[i].length;j++){
				Boton aux = this.grid[i][j];
				if(aux.getStatus() == Boton.FLAGED ){
					bflaged++;
				}
			}	
		}
		return bflaged;
	}
	
	/**
	 * Trae si alguna bomba ya fue abierta
	 * @return ob Regresa si ya ha sido abierta una bomba
	 */
	public boolean hasOpenBomb(){
		for(int i=0;i<this.grid.length;i++){
			for(int j=0;j<this.grid[i].length;j++){
				Boton aux = this.grid[i][j];
				if(aux.getStatus() == Boton.CLICKED && aux.getValue() == Boton.BOMB){
					return true;
				}
			}	
		}
		return false;
	}
	
	/**
	 * Trae el numero de renglones
	 * @return rows
	 */
	public int getRows(){ 
		return this.rows; 
	}
	
	/**
	 * Trae el numero de columnas
	 * @return cols
	 */
	public int getCols(){ 
		return this.cols; 
	}
	
	/**
	 * Trae el numero de minas
	 * @return nbombas
	 */
	public int getMines(){ 
		return this.nbombas; 
	}
	
	/**
	 * Trae el tiempo total que duro la persona
	 * @return time
	 */
	public int getTime(){
		return this.time;
	}
	
	/**
	 * Arreglo que contiene los datos principals de los botones
	 * @return data
	 */
	public int[][][] getData(){
		int data[][][] = new int[this.rows][this.cols][2];
		for(int i=0;i<this.rows;i++){
			for(int j=0; j<this.cols; j++){
				data[i][j][0] = this.grid[i][j].getStatus(); //-- Status
				data[i][j][1] = this.grid[i][j].getValue();	 //-- Value
			}
		}
		return data;
	}
	
	/**
	 * Establece los datos a partir de un arreglo
	 * @param data datos[][][]
	 */
	public void setData(int[][][] data){
		for(int i=0;i<this.rows;i++){
			for(int j=0; j<this.cols; j++){
				this.grid[i][j].setValue(data[i][j][1]);	 //-- Value
				this.grid[i][j].setStatus(data[i][j][0]); //-- Status
			}
		}
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
