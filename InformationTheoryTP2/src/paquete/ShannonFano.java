package paquete;

import paquete.Lectura;
import paquete.Register;

import java.util.ArrayList;
import java.util.Map;

public class ShannonFano {

	public static ArrayList<String> indice = Lectura.getInstance().getIndice();
	public static Map<String, Register> diccionario = Lectura.getInstance().getDiccionario();

	public static void codificar(int inicio,int fin){
		if(inicio!=fin) {
			int y=inicio;
			int z=fin;
			double suma_izq=0;
			double suma_der=0;
			//Primero divide en dos conjuntos de aproximadamente la misma probabilidad
			while(y<=z){
				if(suma_izq<=suma_der){
					suma_izq += diccionario.get(indice.get(y)).getFrec();
					//suma_izq +=caracteres.get(y).getProbabilidad();
					y++;
				}
				else{
					suma_der += diccionario.get(indice.get(z)).getFrec();
					z--;
				}
			}
			//Agrega  un 0 a los de la izquierda, y un 1 a los de la derecha
			for(int i=inicio;i<y;i++){
				//String codigo = caracteres.get(i).getCodigo();
				//caracteres.get(i).setCodigo(codigo+"0");
				String codigo = diccionario.get(indice.get(i)).getCodigo();
				diccionario.get(indice.get(i)).setCodigo(codigo+"1");
			}
			for(int i=y;i<=fin;i++){
				//String codigo = caracteres.get(i).getCodigo();
				//caracteres.get(i).setCodigo(codigo+"1");
				String codigo = diccionario.get(indice.get(i)).getCodigo();
				diccionario.get(indice.get(i)).setCodigo(codigo+"0");
			}
			//Llama recursivamente con el conjunto izquierdo y con el derecho
			codificar(inicio,y-1);
			codificar(y,fin);
		}

	}
	public static void creaTablaShannon(){
		Map <String, String> tablaShannon = Lectura.getInstance().getTablaShannon();

		for(int i=0;i<Lectura.getInstance().cantSimbolos;i++){

			String simbolo = diccionario.get(indice.get(i)).getSimbolo();
			String codigo = diccionario.get(indice.get(i)).getCodigo();

			tablaShannon.put(simbolo,codigo);
		}
	}

//	public static void codificar(ArrayList<Simbolo> caracteres, int inicio,int fin){
//		if(inicio!=fin) {
//		    int y=inicio;
//	        int z=fin;
//	        double suma_izq=0;
//	        double suma_der=0;
//	        //Primero divide en dos conjuntos de aproximadamente la misma probabilidad
//	        while(y<=z){
//	            if(suma_izq<=suma_der){
//	                    suma_izq+=caracteres.get(y).getProbabilidad();
//	                    y++;
//	            }
//	            else{
//	                    suma_der+=caracteres.get(z).getProbabilidad();
//	                    z--;
//	            }
//	            }
//	        //Agrega  un 0 a los de la izquierda, y un 1 a los de la derecha
//	            for(int i=inicio;i<y;i++){
//	            	String codigo = caracteres.get(i).getCodigo();
//	                caracteres.get(i).setCodigo(codigo+"0");
//	            }
//	            for(int i=y;i<=fin;i++){
//	            	String codigo = caracteres.get(i).getCodigo();
//	                caracteres.get(i).setCodigo(codigo+"1");
//	            }
//	            //Llama recursivamente con el conjunto izquierdo y con el derecho
//	            codificar(caracteres,inicio,y-1);
//	            codificar(caracteres,y,fin);
//		}
//
//	    }
}
