package paquete;

import java.util.ArrayList;
import java.util.Map;

public class Calculos {

    private double entropia;

    public double entropiaFuente() {
        double entropia = 0;
        double vecProb[] = Lectura.getInstance().getVecProb();
        for (int i = 0; i < 3; i++) {
            entropia += vecProb[i] * (Math.log10(1 / vecProb[i]) / Math.log10(3));
        }
        return entropia;
    }

    public void calculaCantInfo() {

        Map<String, Register> diccionario = Lectura.getInstance().getDiccionario();
        ArrayList<String> indice = Lectura.getInstance().getIndice();

        this.entropia = 0;
        double cantInfo = 0, probabilidad;
        double frectotal = 0;
        int n = Lectura.getInstance().getIndice().size();

        for (int i = 0; i < n; i++) {
            frectotal += diccionario.get(indice.get(i)).getFrec();
        }

        for (int i = 0; i < n; i++) {
            diccionario.get(indice.get(i)).setProb((double) diccionario.get(indice.get(i)).getFrec() / frectotal);
            probabilidad = diccionario.get(indice.get(i)).getProb();
            cantInfo += Math.log10(1 / probabilidad) / Math.log10(2);
            this.entropia += probabilidad * (Math.log10(1 / probabilidad) / Math.log10(2));
        }

        System.out.println("Entropia: " + this.entropia);
//        System.out.println("CantInfo: " +cantInfo);
    }

    public static double longitudMedia(Map<String, String> tabla) {

        Map<String, Register> diccionario = Lectura.getInstance().getDiccionario();
        ArrayList<String> indice = Lectura.getInstance().getIndice();

        double probabilidad = 0, longMedia = 0.0;
        int longitud = 0, n = Lectura.getInstance().getIndice().size();

        for (int i = 0; i < n; i++) {
            longitud = tabla.get(indice.get(i)).length();
            //longitud = indice.get(i).length();
            probabilidad = diccionario.get(indice.get(i)).getProb();
            longMedia += longitud * probabilidad;
        }
        return longMedia;
    }

    public double getEntropia() {
        return entropia;
    }

    public double rendimiento(Map <String, String> tabla){
        return this.entropia/longitudMedia(tabla);
    }

}
