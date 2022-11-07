package paquete;

import Exepciones.noSePudoLeerException;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Lectura {

    public int matriz[][] = new int[][]{{0, 0, 0}, {0, 0, 0}, {0, 0, 0}};
    private static Lectura instance = null;
    public Map <Character, Integer> alfabeto = new HashMap<Character, Integer>();
    public double vecProb[] = new double[27];
    public ArrayList<String> indice = new ArrayList<String>();
    public Map <String, Register> codigo = new HashMap<String, Register>();
    public Map <String, String> tablaHuffman = new HashMap<String, String>();
    public int cantSimbolos;
    public int cantCaracteres;

    public void setCantSimbolos(int cantSimbolos) {
        this.cantSimbolos = cantSimbolos;
    }

    public int getCantCaracteres() {
        return cantCaracteres;
    }

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

    public Map<String, Register> getCodigo() {
        return codigo;
    }

    public ArrayList<String> getIndice() {
        return indice;
    }

    public Map<String, String> getTablaHuffman() {
        return tablaHuffman;
    }

    public static Lectura getInstance() {
        if (instance == null)
            instance = new Lectura();
        return instance;
    }

    public void setCodigo(Map<String, Register> codigo) {
        this.codigo = codigo;
    }

    public void cargaAlfabeto(){
        this.alfabeto.put('A',0); //agregar a medida que se lee
        this.alfabeto.put('B',1);
        this.alfabeto.put('C',2);
    }

    public void leeArch() throws noSePudoLeerException {
        FileReader fr;
        char c1,c2;
        int i=0, j=-1;

        try {
            fr = new FileReader("src/assets/datos.txt");
            //fr = new FileReader("E:\\Programas\\Github\\InformationTheory\\InformationTheoryTP1\\src\\assets\\datos.txt");
            c1 = (char) fr.read();
            c2 = (char) fr.read();

            while(i<9999) {
                matriz[alfabeto.get(c2)][alfabeto.get(c1)]++;
                c1=c2;
                c2 = (char) fr.read();
                i++;
            }
            System.out.println("Size:" +alfabeto.size());
            System.out.println(alfabeto);
        } catch (Exception ex) {
            throw new noSePudoLeerException("Error al leer");
        }
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


    public void calculaProb() throws noSePudoLeerException {
        FileReader fr;
        char c;
        int i=0;
        try {
            fr = new FileReader("src/assets/datos.txt");
            //fr = new FileReader("E:\\Programas\\Github\\InformationTheory\\InformationTheoryTP1\\src\\assets\\datos.txt");
            while(i<9999) {
                c = (char) fr.read();
                this.vecProb[alfabeto.get(c)]++;
                i++;
            }
            for (i=0;i<alfabeto.size();i++){
                this.vecProb[i]/= 10000;
                System.out.println(i+" --> " + this.vecProb[i]);
            }
        } catch (Exception ex) {
            throw new noSePudoLeerException("Error al leer");
        }
    }

    public void separaTexto (int n){

        File doc = new File("src/assets/datos.txt");
        //File doc = new File("E:\\Programas\\Github\\InformationTheory\\InformationTheoryTP1\\src\\assets\\datos.txt");
        String mensaje="", str,simbolo;
        int frec = 0;
        int j=0;
        Register actual;
        this.indice.clear();
        this.codigo.clear();
        this.cantCaracteres = n;
        try {
            BufferedReader obj = new BufferedReader(new FileReader(doc));
            while ((str = obj.readLine()) != null)
                mensaje += str;

            while (j<10000) {
                if (n==3 && j==9999) {
                    simbolo = mensaje.substring(j);
                }
                else if (n==7 && j==9996)
                    simbolo = mensaje.substring(j, j+4);
                else
                    simbolo = mensaje.substring(j, j + n);
                if(!codigo.containsKey(simbolo)){
                    indice.add(simbolo);
                    codigo.put(simbolo, new Register(simbolo, 1));
                }
                else {
                    actual=codigo.get(simbolo);
                    actual.setFrec(actual.getFrec()+1);
                }
                j += n;
            }
            System.out.println(codigo.toString());
        }

        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.cantSimbolos = this.indice.size();
    }
    public double[] getVecProb() {
        return vecProb;
    }


}
