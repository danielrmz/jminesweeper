import java.io.BufferedReader;
import java.io.IOException;
import java.io.*;


public class Prueba {
	
	public static BufferedReader jin = new BufferedReader(new InputStreamReader(System.in));
	
	public static int nbombas = 7;
	public static int bomba = 9;
	public static int tamano = 5;
	private static int[][] smatriz = {{bomba,0,0,0,0},
									  {0,bomba,bomba,0,0},
									  {bomba,0,bomba,0,0},
									  {bomba,0,0,bomba,bomba},
									  {0,0,0,0,bomba}};
	
	public static void generaBombas(int[][] datos){
		int contador = 0;
		double probabilidad = 5; 
		for(int i=0; i<tamano; i++){
			for(int j=0; j<tamano; j++){
				int aleatorio = (int)(Math.random()*11);
				if(aleatorio < probabilidad && contador<nbombas){
					datos[i][j] = 9;
					contador++;
				}
				else{
					datos[i][j] = 0;
				}
			}
		}
		
	}
	
	public static int ponderaTabla(int[][] datos, int indiceI, int indiceJ){
		int numero = 0;
		for(int i=(indiceI-1); i<=(indiceI+1); i++ ){
			for(int j=(indiceJ-1); j<=(indiceJ+1); j++){
				try{
					if(datos[i][j]==bomba){
						numero++;
					}
				}catch(ArrayIndexOutOfBoundsException aiobe){}
			}
			
		}
		return numero;
	}
	
	public static void enumeraTabla(int[][] datos){
		int[][] matriz = datos;
		for (int i=0; i<tamano; i++){
			for(int j=0; j<tamano; j++){
				if(matriz[i][j] != bomba){
					matriz[i][j]=ponderaTabla(matriz, i, j);
				}
			}
		}
	}
	
	public static void despliegaTabla(int[][] datos){
		for(int i = 0; i<tamano; i++){
			for(int j = 0; j<tamano-1; j++){
				System.out.print(datos[i][j]+"  ");
			}
			System.out.println(datos[i][tamano-1]);
		}
	}
	
	public static void descubreCeros(int[][] datos, int indi, int indj){
		datos[indi][indj] = 8;
		for(int i=indi-1 ; i<=indi+1; i++){
			for(int j=indj-1; j<=indj+1; j++){
				try{
					if(datos[i][j]==0){
						descubreCeros(datos, i, j);
					}
				}catch(ArrayIndexOutOfBoundsException aiobe){}
				
			}
		}
	}
	
	public static void main(String args[])throws IOException{
		int[][] matriz = new int[tamano][tamano];
		generaBombas(matriz);
		enumeraTabla(matriz);
		despliegaTabla(matriz);
		int i = Integer.parseInt(jin.readLine());
		int j = Integer.parseInt(jin.readLine());
		descubreCeros(matriz,i,j);
		despliegaTabla(matriz);
		
	}

}
