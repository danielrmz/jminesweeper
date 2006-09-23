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
		//super(""+value);
		this.status = (status != Boton.CLICKED && status != Boton.UNCLICKED && status!= Boton.FLAGED)?Boton.UNCLICKED:status;
		this.value = value;
		this.x = x;
		this.y = y;
		this.setIcon(Main.getIconImage("boton.jpg"));
		this.setBorderPainted(false);
	}
	
	public int getStatus(){
		return this.status;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public void setValue(int value){
		this.value = value;
		//this.setText(value+"");
	}
	
	public void setStatus(int status){
		int aux = (this.status == Boton.FLAGED && status == Boton.CLICKED)?Boton.FLAGED:Boton.CLICKED;
		this.status = (status != Boton.CLICKED && status != Boton.UNCLICKED && status!= Boton.FLAGED)?Boton.UNCLICKED:status;
		
		if(status==Boton.CLICKED && this.value<1){
			if(aux == Boton.FLAGED && this.value == Boton.BOMB){
				this.status = Boton.FLAGED;
			}
			this.setTypeImage();	
			
		} else if(status == Boton.FLAGED){
			
			ImageIcon img = Main.getIconImage("flag.jpg");
			this.setIcon(img);
			
		} else if(status==Boton.UNCLICKED) {
			
			this.setIcon(Main.getIconImage("boton.jpg"));
			
		} else if(this.value>0) {
			ImageIcon img = Main.getIconImage(this.value+".jpg");
			this.setIcon(img);
			//this.setText(value+"");
		} 
	}

	public void setTypeImage(){
		ImageIcon image = null;
		switch(this.value){
		case Boton.BOMB: 
			if(this.status != Boton.FLAGED){
				image = Main.getIconImage("bomb.jpg");
			} else { 
				image = Main.getIconImage("flagedbomb.jpg");
			}
			break;
		case Boton.DEAD: 
			image = Main.getIconImage("selectedbomb.jpg");
			break;
		
		case Boton.NUMBER:
			image = Main.getIconImage("blank.jpg");
			break;
		default: 
			image = Main.getIconImage("boton.jpg");
			break;
		}
		this.setIcon(image);
	}

}