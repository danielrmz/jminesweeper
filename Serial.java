import java.io.*;

/**
 * Clase que engloba la serializacion de los objetos para posteriormente traerlos
 * de nuevo, y obtener su informacion sin preocuparnos
 * por tener que dividir la informacion y meterla de nuevo
 * a otro objeto.
 * @author Revolution Software Developers
 */
public class Serial {
	public static boolean debug = false;
	private Object base = null;
	
	/**
	 * Constructor para Serializar
	 */
	public Serial(String filename, Object base){
		this.base = base;
		new Serialize(filename,base);
	}
	
	/**
	 * Contructor para deserializar
	 * @param filename
	 */
	public Serial(String filename){
		new Unserialize(filename);
	}
	
	
	public Object getObject(){
		return this.base;
	}
	
	private class Serialize {
		public Serialize(String filename, Object base) {
			if(!filename.equals("")){
				FileOutputStream fos = null;
				ObjectOutputStream out = null;
				if(Serial.debug)
					System.out.println("Serializando objeto... ("+filename+")");
				try {
					fos = new FileOutputStream(filename);
					out = new ObjectOutputStream(fos);
					out.writeObject(base);
					out.close();
				} catch (IOException ex){
					ex.printStackTrace();
				}
				if(Serial.debug)
					System.out.println("Serializacion Completa");
			}
		}
	}
	
	private class Unserialize {
		public Unserialize(String filename){
			if(!filename.equals("")){
				FileInputStream fis = null;
				ObjectInputStream in = null;
				if(Serial.debug)
					System.out.println("Deserializando... ("+filename+")");
				try {
					fis = new FileInputStream(filename);
					in = new ObjectInputStream(fis);
					base = in.readObject();
					in.close();
				} catch (FileNotFoundException ex){
					if(Serial.debug)
						System.out.println("Archivo no encontrado");
				} catch (IOException ex) {
					ex.printStackTrace();
				} catch (ClassNotFoundException ex){
					ex.printStackTrace();
				} 
				if(Serial.debug)
					System.out.println("Deserializacion Completa");
			}
		}
	}
}
