package Parte2Calculos;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        int m1= 3;
        int m2= 4;
        int m3= 4;
        double[] entrada1 = {0.2,0.1,0.3,0.3,0.1};
        double[] entrada2 = {0.25,0.33,0.27,0.15};
        double[] entrada3 = {0.15,0.1,0.2,0.25,0.14,0.16};


        double[][] canal1 = {{0.3,0.12,0.532},{0.16,0.4,0.532},{0.3,0.12,0.532},{0.12,0.4,0.532},{0.3,0.16,0.532}};
        double[][] canal2 = {{0.2,0.12,0.2,0.459},{0.12,0.3,0.2,0.459},{0.12,0.2,0.2,0.459},{0.12,0.2,0.12,0.459}};
        double[][] canal3 = {{0.2,0.12,0.2,0.4482},{0.12,0.12,0.3,0.4482},{0.2,0.2,0.12,0.4482},{0.12,0.3,0.2,0.4482},{0.2,0.12,0.12,0.4482},{0.2,0.12,0.3,0.4482}};



        System.out.println("========== CANAL 1 ==========");
        calculosCanales(m1, entrada1, canal1);


        System.out.println("========== CANAL 2 ==========");
        calculosCanales(m2, entrada2, canal2);


        System.out.println("========== CANAL 3 ==========");
        calculosCanales(m3, entrada3, canal3);
    }

    private static void calculosCanales(int m, double[] entrada, double[][] canal) {
        double[] salida;
        double[][] matrizS;
        double[][] matrizSucesoSimul = new double[entrada.length][m];
        salida = probSalida(canal,entrada,m);
        for(int i=0;i<salida.length;i++) {
            System.out.print("P(B"+(i+1)+") = "+salida[i]+ "    ");
        }
        System.out.println();

        for(int i=0;i<entrada.length;i++)
            for (int j = 0; j < m; j++) {
                matrizSucesoSimul[i][j] = canal[i][j]*entrada[i];
            }

        System.out.println("Entropia a priori H(S): "+ entropia(entrada));
        System.out.println("Entropia salida H(B): "+ entropia(salida));
        System.out.println("Entropia a fin H(S,B): "+ entropiaAFin(matrizSucesoSimul));
        System.out.println("Equivocacion H(S/B) = "+equivocacion(canal,entrada,salida));
        System.out.println("Informacion Mutua I(S,B) = "+informacionMutua(canal,entrada,salida));
        matrizS = matrizSalida(canal,entrada,salida);
        System.out.println("Equivocacion: H(B/S) =  "+equivocacion(matrizS,salida,entrada));
        System.out.println("Informacion Mutua: I(B,S) = "+informacionMutua(matrizS,salida,entrada));


        System.out.println();
    }

    public static double[][] matrizSalida(double[][] matriz,double[] entrada,double[] salida){
        double[][] invertida = new double[salida.length][entrada.length];

        for(int i=0;i<salida.length;i++){
            for(int j=0;j<entrada.length;j++){
                invertida[i][j] = (matriz[j][i]*entrada[j])/salida[i];
            }
        }

        return invertida;
    }
    public static double[] probSalida(double[][] matriz,double[] alfabeto,int m) {
        double[] salida = new double[m];

        for(int i=0;i<m;i++) {
            salida[i]=0;
            for(int j=0;j<alfabeto.length;j++) {
                salida[i] += alfabeto[j]*matriz[j][i];
            }
        }
        return salida;
    }
    public static double probabilidad(int i,int j,double[][] matriz,double[] entrada,double[] salida) {
        double resultado;

        resultado = (matriz[i][j]*entrada[i])/salida[j];

        return resultado;
    }
    public static double entropiaPosteriori(int j,double[][] matriz,double[] entrada,double[] salida) {
        double acum = 0;
        for(int i=0;i<entrada.length;i++){
            double prob = probabilidad(i,j,matriz,entrada,salida);
            if(prob!=0)
                acum+=prob*(-Math.log(prob)/Math.log(2));
        }
        return acum;
    }
    public static double entropia(double[] entrada) {
        double entropia = 0;
        for (double prob : entrada) {
            entropia += prob * (-Math.log(prob) / Math.log(2));
        }
        return entropia;
    }

    public static double entropiaAFin(double[][] entrada) {
        double entropia = 0;
        for (double[] Vecprob : entrada) {
            for (double prob: Vecprob){ //Este doble for recorre TODA la matriz
                entropia += prob * (-Math.log(prob) / Math.log(2));
            }
        }
        return entropia;
    }

    public static double equivocacion(double[][] matriz,double[] entrada,double[] salida) {
        double acum = 0;
        for(int i=0;i<salida.length;i++) {
            acum+= entropiaPosteriori(i,matriz,entrada,salida)*salida[i];
        }
        return acum;
    }
    public static double informacionMutua(double[][] matriz,double[] entrada,double[] salida) {
        return entropia(entrada) - equivocacion(matriz,entrada,salida);
    }
}