import javax.swing.*;

public class Boton extends JButton {

	private static final long serialVersionUID = 1L;
	
	public static final int BOMB = -1;
	public static final int NUMBER = 0;
	public static final int DEAD = -2;
	
	public static final int FLAGED = -1;
	public static final int UNCLICKED = 0;
	public static final int CLICKED = 1;
	
	public int status = 0; //-- Clicked o Unclicked [exceptuando el flag]
	public int value = 0;  //-- Valor del cuadro -2 bomba -1 flag x>=0 numero 
	
	public Boton(){
		super();
	}
	
	public Boton(int status, int value){
		//super(""+((value<1)?"":value+""));//-- De prueba al pasarle la cantidad
		super(""+value);
		this.status = (status != Boton.CLICKED && status != Boton.UNCLICKED && status!= Boton.FLAGED)?Boton.UNCLICKED:status;
		this.value = value;
	}
	
	public void setStatus(int status){
		this.status = (status != Boton.CLICKED && status != Boton.UNCLICKED && status!= Boton.FLAGED)?Boton.UNCLICKED:status;
		if(status==Boton.CLICKED && this.value<1){
			ImageIcon img = Boton.getTypeImage(this.value);
			this.setIcon(img);
		} else if(status == Boton.FLAGED){
			ImageIcon img = Boton.getImage(Main.ruta+"img/flag.jpg");
			this.setIcon(img);
		} else if(this.value>0) {
			this.setText(this.value+"");
		} else if(status==Boton.UNCLICKED) { 
			this.setIcon(Boton.getImage(Main.ruta+"img/boton.jpg"));
		}
	}

	public static ImageIcon getImage(String filename){
		//Toolkit toolkit = Toolkit.getDefaultToolkit();
		//Image image = toolkit.getImage(filename);
		ImageIcon image = new ImageIcon(filename);
		return image;
	}
	
	public static ImageIcon getTypeImage(int value){
		ImageIcon image = null;
		switch(value){
		case Boton.BOMB: 
			image = Boton.getImage(Main.ruta+"img/bomb.jpg");
			break;
		case Boton.NUMBER:
			break;
		case Boton.DEAD: 
			image = Boton.getImage(Main.ruta+"img/dead.jpg");
			break;
		default: 
			image = Boton.getImage(Main.ruta+"img/boton.jpg");
			break;
		}
		return image;
	}

}