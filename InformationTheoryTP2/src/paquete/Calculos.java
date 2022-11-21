package paquete;

import java.util.ArrayList;
import java.util.Map;

public class Calculos {
    public double entropiaFuente (){
        double entropia=0;
        double vecProb[]= Lectura.getInstance().getVecProb();
        for (int i=0; i<3; i++){
            entropia += vecProb[i]*( Math.log10(1/vecProb[i]) / Math.log10(3));
        }
        return entropia;
    }

    public void calculaCantInfo(){

        Map <String, Register> codigo = Lectura.getInstance().getCodigo();
        ArrayList<String> indice = Lectura.getInstance().getIndice();
        this.entropiaN = 0;
        double cantInfo = 0, probabilidad;
        double frectotal = 0;
        int n = Lectura.getInstance().getIndice().size();

        for (int i=0; i<n;i++){
            frectotal += codigo.get(indice.get(i)).getFrec();
        }

        for (int i=0; i<n;i++){
            codigo.get(indice.get(i)).setProb((double) codigo.get(indice.get(i)).getFrec()/frectotal);
            probabilidad = codigo.get(indice.get(i)).getProb();
            cantInfo += Math.log10(1/probabilidad) / Math.log10(3);
            this.entropiaN += probabilidad*(Math.log10(1/probabilidad) / Math.log10(3));
        }

        System.out.println("Entropia 2a: " +this.entropiaN);
        System.out.println("CantInfo 2a: " +cantInfo);

    }

    public double longitudMedia (){

        Map<String, Register> codigo = Lectura.getInstance().getCodigo();
        ArrayList<String> indice = Lectura.getInstance().getIndice();

        double probabilidad = 0, longMedia = 0.0;
        int longitud = 0, n = Lectura.getInstance().getIndice().size();

        for (int i=0; i<n;i++){
            longitud = indice.get(i).length();
            probabilidad = codigo.get(indice.get(i)).getProb();
            longMedia += longitud * probabilidad;
        }
        return longMedia;
    }
}
