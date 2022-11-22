package paquete;

import java.util.ArrayList;
import java.util.Map;

public class ShannonFano {

	public static ArrayList<String> indice = Lectura.getInstance().getIndice();
	public static Map<String, Register> diccionario = Lectura.getInstance().getDiccionario();

	private static int codigoMasLargo = 0;
	private static void codifica(int inicio, int fin){

		if(inicio!=fin) {
			int y=inicio;
			int z=fin;
			double suma_izq=0;
			double suma_der=0;

			while(y<=z){
				if(suma_izq<=suma_der){
					suma_izq += diccionario.get(indice.get(y)).getFrec();
					y++;
				}
				else{
					suma_der += diccionario.get(indice.get(z)).getFrec();
					z--;
				}
			}
			for(int i=inicio;i<y;i++){
				String codigo = diccionario.get(indice.get(i)).getCodigo();
				diccionario.get(indice.get(i)).setCodigo(codigo+"1");
			}
			for(int i=y;i<=fin;i++){
				String codigo = diccionario.get(indice.get(i)).getCodigo();
				diccionario.get(indice.get(i)).setCodigo(codigo+"0");
			}
			codifica(inicio,y-1);
			codifica(y,fin);
		}
	}

	public static void codificaShannon(int inicio, int fin){
		Map <String, String> tablaShannon = Lectura.getInstance().getTablaCodificaShannon();

		codifica(inicio,fin);
		for(int i=0;i<Lectura.getInstance().cantSimbolos;i++){

			String simbolo = diccionario.get(indice.get(i)).getSimbolo();
			String codigo = diccionario.get(indice.get(i)).getCodigo();

			if(codigo.length() > codigoMasLargo)
				codigoMasLargo = codigo.length();

			tablaShannon.put(simbolo,codigo);
		}
	}

	public static int getCodigoMasLargo() {
		return codigoMasLargo;
	}
}
