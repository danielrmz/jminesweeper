import javax.swing.*;

public class Boton extends JButton {

	private static final long serialVersionUID = 1L;
	
	public static final int BOMB = -1;
	public static final int NUMBER = 0;
	public static final int DEAD = -2;
	
	public static final int FLAGED = -1;
	public static final int UNCLICKED = 0;
	public static final int CLICKED = 1;
	
	private int status = 0; //-- Clicked, Unclicked o Flagged
	private int value = 0;  //-- Valor del bomb -1, number x>=0, dead -2 [bomba con crucecita]
	
	public int x;
	public int y;
	
	/*
	 * Constructor
	 */
	public Boton(int status, int value, int x, int y){
		super(""+value);
		this.status = (status != Boton.CLICKED && status != Boton.UNCLICKED && status!= Boton.FLAGED)?Boton.UNCLICKED:status;
		this.value = value;
		this.setIcon(Main.getIconImage("boton.jpg"));
	}
	
	public int getStatus(){
		return this.status;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public void setValue(int value){
		this.value = value;
		this.setText(value+"");
	}
	
	public void setStatus(int status){
		this.status = (status != Boton.CLICKED && status != Boton.UNCLICKED && status!= Boton.FLAGED)?Boton.UNCLICKED:status;
		
		if(status==Boton.CLICKED && this.value<1){
			
			this.setTypeImage();	
			
		} else if(status == Boton.FLAGED){
			
			ImageIcon img = Main.getIconImage("flag.jpg");
			this.setIcon(img);
			
		} else if(status==Boton.UNCLICKED) {
			
			this.setIcon(Main.getIconImage("boton.jpg"));
			
		} else if(this.value>0) {
			ImageIcon img = Main.getIconImage(this.value+".jpg");
			this.setIcon(img);
			this.setText(this.value+"");
			
		} 
	}

	public void setTypeImage(){
		ImageIcon image = null;
		switch(this.value){
		case Boton.BOMB: 
			image = Main.getIconImage("bomb.jpg");
			break;
		case Boton.DEAD: 
			image = Main.getIconImage("dead.jpg");
			break;
		default: 
			image = Main.getIconImage("boton.jpg");
			break;
		}
		this.setIcon(image);
	}

}