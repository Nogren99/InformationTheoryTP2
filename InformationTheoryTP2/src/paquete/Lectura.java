package paquete;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Lectura {

    public int matriz[][] = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    private static Lectura instance = null;
    public Map <Character, Integer> alfabeto = new HashMap<Character, Integer>();
    public double vecProb[] = new double[27];
    public ArrayList<String> indice = new ArrayList<String>();
    public Map <String, Register> diccionario = new HashMap<String, Register>();
    public Map <String, String> tablaCodificaHuffman = new HashMap<String, String>();
    public Map <String, String> tablaCodificaShannon = new HashMap<String, String>();
    public int cantSimbolos;

    public int cantCaracteres = 0;
    public int simboloMasLargo = 0;

    public void setCantSimbolos(int cantSimbolos) {
        this.cantSimbolos = cantSimbolos;
    }

//    public int getCantCaracteres() {
//        return cantCaracteres;
//    }

    public Lectura() {
    }

    public int getCantCaracteres() {
        return cantCaracteres;
    }

    public int getCantSimbolos() {
        return cantSimbolos;
    }

    public int[][] getMatriz() {
        return matriz;
    }

    public Map<Character, Integer> getAlfabeto() {
        return alfabeto;
    }

    public Map<String, Register> getDiccionario() {
        return diccionario;
    }

    public ArrayList<String> getIndice() {
        return indice;
    }

    public Map<String, String> getTablaCodificaHuffman() {
        return tablaCodificaHuffman;
    }

    public Map<String, String> getTablaCodificaShannon() {
        return tablaCodificaShannon;
    }

    public static Lectura getInstance() {
        if (instance == null)
            instance = new Lectura();
        return instance;
    }

    public int getSimboloMasLargo() {
        return simboloMasLargo+1;
    }

    public void setSimboloMasLargo(int simboloMasLargo) {
        this.simboloMasLargo = simboloMasLargo;
    }

    public void setDiccionario(Map<String, Register> diccionario) {
        this.diccionario = diccionario;
    }

    public void muestraMatriz(int matriz [][]){
        char aux = 'A';
        System.out.println("Matriz de apariciones condicionadas:");
        System.out.println("     A      B      C");
        for(int i=0;i<3;i++){
            System.out.print(aux+" |");
            aux++;
            for(int j=0;j<3;j++){
                System.out.printf("%04d | ",matriz[i][j]);
            }
            System.out.println();
        }
    }

    public void muestraMatriz(float matriz [][]){
        char aux = 'A';
        System.out.println("Matriz de probabilidades:");
        System.out.println("       A          B          C");
        for(int i=0;i<3;i++){
            System.out.print(aux+" |");
            aux++;
            for(int j=0;j<3;j++){
                System.out.printf("%f | ",matriz[i][j]);
            }
            System.out.println();
        }
    }


    public void leeArch () throws IOException {


        File doc = new File("InformationTheoryTP2/src/assets/datos.txt");

        String mensaje="", str,simbolo;
        int frec = 0;
        int j=0;
        Register actual;
        this.indice.clear();
        this.diccionario.clear();

        char c = (char) -1;
        String vacio = "";
        StringBuilder sb = new StringBuilder();
        Scanner lector = new Scanner(doc);


        while(lector.hasNext()) {       // toma las palabras con los signos de puntuacion pegados.
            simbolo = lector.next();

            if(simbolo.length() > this.simboloMasLargo)
                this.simboloMasLargo = simbolo.length();
            this.cantCaracteres+=simbolo.length();
            if(!diccionario.containsKey(simbolo)){
                indice.add(simbolo);
                diccionario.put(simbolo, new Register(simbolo, 1));
            }
            else {
                actual= diccionario.get(simbolo);
                actual.setFrec(actual.getFrec()+1);
            }
        }

        this.cantSimbolos = this.indice.size();
    }

    public double[] getVecProb() {
        return vecProb;
    }


}
