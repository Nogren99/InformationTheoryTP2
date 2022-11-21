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
    //public int cantCaracteres;
    public int simboloMasLargo = 0;

    public void setCantSimbolos(int cantSimbolos) {
        this.cantSimbolos = cantSimbolos;
    }

//    public int getCantCaracteres() {
//        return cantCaracteres;
//    }

    public Lectura() {
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
        return simboloMasLargo;
    }

    public void setSimboloMasLargo(int simboloMasLargo) {
        this.simboloMasLargo = simboloMasLargo;
    }

    public void setDiccionario(Map<String, Register> diccionario) {
        this.diccionario = diccionario;
    }

//    public void cargaAlfabeto(){
//        this.alfabeto.put('A',0); //agregar a medida que se lee
//        this.alfabeto.put('B',1);
//        this.alfabeto.put('C',2);
//    }

//    public void leeArch() throws noSePudoLeerException {
//        FileReader fr;
//        char c1,c2;
//        int i=0, j=-1;
//
//        try {
//            fr = new FileReader("src/assets/datos.txt");
//            //fr = new FileReader("E:\\Programas\\Github\\InformationTheory\\InformationTheoryTP1\\src\\assets\\datos.txt");
//            c1 = (char) fr.read();
//            c2 = (char) fr.read();
//
//            while(i<9999) {
//                matriz[alfabeto.get(c2)][alfabeto.get(c1)]++;
//                c1=c2;
//                c2 = (char) fr.read();
//                i++;
//            }
//            System.out.println("Size:" +alfabeto.size());
//            System.out.println(alfabeto);
//        } catch (Exception ex) {
//            throw new noSePudoLeerException("Error al leer");
//        }
//    }

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


//    public void calculaProb() throws noSePudoLeerException {
//        FileReader fr;
//        char c;
//        int i=0;
//        try {
//            fr = new FileReader("src/assets/datos.txt");
//            //fr = new FileReader("E:\\Programas\\Github\\InformationTheory\\InformationTheoryTP1\\src\\assets\\datos.txt");
//            while(i<9999) {
//                c = (char) fr.read();
//                this.vecProb[alfabeto.get(c)]++;
//                i++;
//            }
//            for (i=0;i<alfabeto.size();i++){
//                this.vecProb[i]/= 10000;
//                System.out.println(i+" --> " + this.vecProb[i]);
//            }
//        } catch (Exception ex) {
//            throw new noSePudoLeerException("Error al leer");
//        }
//    }

    public void leeArch () throws IOException {

        //File doc = new File("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
        //File doc = new File("InformationTheoryTP2/src/assets/datos.txt");
        File doc = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");

        //File doc = new File("E:\\Programas\\Github\\InformationTheory\\InformationTheoryTP1\\src\\assets\\datos.txt");
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

            //System.out.println(simbolo);
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
        //       FileReader fr = null;
//            try {
//                fr = new FileReader("InformationTheoryTP2/src/assets/datos.txt");
//                //fr = new FileReader("src/assets/datos.txt");
//
//            } catch (FileNotFoundException ex) {
//                throw new RuntimeException(ex);
//            }
//
//            try {
//                 c = (char) fr.read();
//            } catch (IOException ex) {
//                throw new RuntimeException(ex);
//            }

//            while (c != (char) -1) {
//
//                sb.setLength(0);
//                while(Character.isLetterOrDigit(c) || Character.isAlphabetic(c)){
//               //while( (c <= 'Z' && c >= 'A') || (c <= 'Ñ' && c >= 'á') || (c <= 'z' && c >= 'a') || (c <= '9' && c >= '0')){
//                    sb.append(c);
//                    c = (char) fr.read();
//                }
//
//                //System.out.println(c+"->"+Character.isLetterOrDigit(c));
//                simbolo = sb.toString();
//                //System.out.println(simbolo);
//                if(sb.length() > 0){
//                    if(!diccionario.containsKey(simbolo)){
//                        indice.add(simbolo);
//                        diccionario.put(simbolo, new Register(simbolo, 1));
//                    }
//                    else {
//                        actual= diccionario.get(simbolo);
//                        actual.setFrec(actual.getFrec()+1);
//                    }
//                }
        //c = (char) fr.read();
    }

    public double[] getVecProb() {
        return vecProb;
    }


}
