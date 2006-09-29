import java.awt.*;
import javax.swing.*;

/**
 * Clase manejadora del boton, contiene las caracteristicas de un boton de Buscaminas
 * @author Revolution Software Developers
 *
 */
public class Boton extends JButton {
	/**
	 * Estatica de Eclipse
	 */
	private static final long serialVersionUID = 1L;
	
	//-- Constantes del valor que puede tener el boton
	/**
	 * Constante que indica el valor que tiene una casilla si es una bomba
	 */
	public static final int BOMB = -1;
	
	/**
	 * Constante que indica el valor que tiene una casilla si es una casilla blanca
	 */
	public static final int NUMBER = 0;
	
	/**
	 * Constante que indica el valor que una casilla estaba con bandera y fue clickeada
	 */
	public static final int DEAD = -2;

	//-- Variables de Estatus
	/**
	 * Constante que indica que la casilla esta banderada
	 */
	public static final int FLAGED = -1;
	
	/**
	 * Constante que indica que la casilla no se le ha dado click
	 */
	public static final int UNCLICKED = 0;
	
	/**
	 * Constante que indica que la casilla ya ha sido oprimida
	 */
	public static final int CLICKED = 1;
	
	
	/**
	 * Variable que se agrega a los archivos de imagen al cargar para que sea consistentes
	 * con la tematica elegida
	 */
	public static String tematica = "";
	
	/**
	 * Variable local del estatus actual
	 * Clicked, Unclicked o Flagged
	 */
	private int status = 0;
	
	/**
	 * Variable local que tiene el valor
	 * Valor del bomb -1, number x>=0, dead -2 [bomba con crucecita]
	 */
	private int value = 0; 
	
	/**
	 * Subindice i del arreglo grid
	 */
	public int x;
	
	/**
	 * Subindice j del arreglo grid
	 */
	public int y;
	
	/**
	 * Constructor
	 * @param status estatus del boton
	 * @param value valor del boton
	 * @param x i del arreglo
	 * @param y j del arreglo
	 */
	public Boton(int status, int value, int x, int y){
		//-- Por si mete un numero fuera de las constantes permitidas
		this.status = (status != Boton.CLICKED && status != Boton.UNCLICKED && status!= Boton.FLAGED)?Boton.UNCLICKED:status;
		
		//-- Valor del boton
		this.value = value;
		
		//-- Parametros del arreglo contenedor de botones
		this.x = x;
		this.y = y;
		
		//-- Configuraciones del boton
		ImageIcon img = Main.getIconImage("boton"+Boton.tematica+".png");
		img = (img==null)?Main.getIconImage("boton.png"):img;
		this.setIcon(img);
		this.setBorder(null);
		this.setBorderPainted(false);
		this.setCursor("cursor"+Boton.tematica+".gif");
		this.setFocusable(false);
	}
	
	/**
	 * Establece el cursor del boton
	 * @param img Nombre de la imagen del cursor
	 */
	private void setCursor(String img){
		Cursor cursor = Toolkit.getDefaultToolkit().createCustomCursor(Main.getImage(img), new Point(0,5), "cursor");
		super.setCursor(cursor);
	}
	
	/**
	 * Establece un tipo de imagen al boton, dependiendo de su estado y de su valor
	 */
	private void setTypeImage(){
		ImageIcon image = null;
		switch(this.value){
		case Boton.BOMB: 
			if(this.status != Boton.FLAGED){
				image = Main.getIconImage("bomb"+tematica+".png");
				if(image==null){
					image = Main.getIconImage("bomb.png");
				}
			} 
			break;
		case Boton.DEAD: 
			image = Main.getIconImage("selectedbomb"+Boton.tematica+".png");
			if(image==null){
				image = Main.getIconImage("selectedbomb.png");
			}
			break;
		
		case Boton.NUMBER:
			image = Main.getIconImage("blank.png");
			if(image==null){
				image = Main.getIconImage("blank.png");
			}
			break;
		default: 
			image = Main.getIconImage("boton"+Boton.tematica+".png");
			if(image==null){
				image = Main.getIconImage("boton.png");
			}
			break;
		}
		this.setIcon(image);
	}
	
	/**
	 * Regresa el estatus del boton
	 * @return status
	 */
	public int getStatus(){
		return this.status;
	}
	
	/**
	 * Regresa el valor del boton
	 * @return value
	 */
	public int getValue(){
		return this.value;
	}
	
	/**
	 * Establece el valor al botno
	 * @param value
	 */
	public void setValue(int value){
		this.value = value;
	}
	
	/**
	 * Establece el estatus al boton, y le cambia su caratula
	 * @param status
	 */
	public void setStatus(int status){
		int aux = (this.status == Boton.FLAGED && status == Boton.CLICKED)?Boton.FLAGED:Boton.CLICKED;
		this.status = (status != Boton.CLICKED && status != Boton.UNCLICKED && status!= Boton.FLAGED)?Boton.UNCLICKED:status;
		
		if(status==Boton.CLICKED && this.value<1){
			if(aux == Boton.FLAGED && this.value == Boton.BOMB){
				this.status = Boton.FLAGED;
			}
			this.setTypeImage();	
			
		} else if(status == Boton.CLICKED && this.value >=1 && aux == Boton.FLAGED ){
		
			ImageIcon img = Main.getIconImage("flagedbomb"+Boton.tematica+".png");
			if(img==null){
				img=  img = Main.getIconImage("flagedbomb.png");
			}
			this.setIcon(img);
		
		} else if(status == Boton.FLAGED){
			
			ImageIcon img = Main.getIconImage("flag"+Boton.tematica+".png");
			if(img==null){
				img=  img = Main.getIconImage("flag.png");
			}
			this.setIcon(img);
			
		} else if(status==Boton.UNCLICKED) {
			
			this.setIcon(Main.getIconImage("boton"+Boton.tematica+".png"));
			
		} else if(this.value>0) {
			ImageIcon img = Main.getIconImage(this.value+tematica+".png");
			if(img==null){
				img=  img = Main.getIconImage(this.value+".png");
			}
			this.setIcon(img);
		} 
	}

}