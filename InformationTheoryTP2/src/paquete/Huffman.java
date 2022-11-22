package paquete;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Map;
import java.util.PriorityQueue;

class Nodo {
    double prob;
    int frec;
    String simbolo;
    Nodo izq;
    Nodo der;
}

class compare implements Comparator<Nodo> {
    public int compare(Nodo a, Nodo b) {
        return a.frec - b.frec;
    }
}

public class Huffman {

    private static int codigoMasLargo = 0;
    private static Nodo arbol;
    public static void cargaTabla(Nodo raiz, String s) {

        if (raiz.izq == null && raiz.der == null ) {
            Lectura.getInstance().getTablaCodificaHuffman().put(raiz.simbolo,s);
            if(s.length() > codigoMasLargo)
                codigoMasLargo = s.length();
            return;
        }
        //Cada vez que va a la izquierda agrega un 0 al codigo
        cargaTabla(raiz.izq, s + "0");
        //Cada vez que va a la derecha agrega un 1 al codigo
        cargaTabla(raiz.der, s + "1");
    }

    public static void codificaHuffman() {

        int n = Lectura.getInstance().getCantSimbolos();
        Map <String, Register> codigo = Lectura.getInstance().getDiccionario();
        ArrayList<String> indice = Lectura.getInstance().getIndice();

        PriorityQueue<Nodo> cola = new PriorityQueue<Nodo>(n, new compare());
        for (int i = 0; i < n; i++) {
            Nodo nodo = new Nodo();
            nodo.simbolo = codigo.get(indice.get(i)).getSimbolo();
            nodo.frec = codigo.get(indice.get(i)).getFrec();
            nodo.izq = null;
            nodo.der = null;
            cola.add(nodo);
        }
        Nodo raiz = null;

        while (cola.size() > 1) {

            Nodo der = cola.poll();
            Nodo izq = cola.poll();
            Nodo temp = new Nodo();

            temp.frec = der.frec + izq.frec;
            temp.simbolo = "-";
            temp.izq = izq;
            temp.der = der;
            raiz = temp;
            cola.add(temp);
        }
        arbol = raiz;
        cargaTabla(raiz, "");
        //System.out.println("Entropia = " +CalculaEntropia(raiz));
    }

    public static int getCodigoMasLargo() {
        return codigoMasLargo+1;
    }

    public static void setProbabilidad(){
        Map <String, Register> diccionario = Lectura.getInstance().getDiccionario();
        ArrayList<String> indice = Lectura.getInstance().getIndice();
        int n = Lectura.getInstance().getIndice().size();

        int frectotal = 0;

        for (int i=0; i<n;i++){
            frectotal += diccionario.get(indice.get(i)).getFrec();
        }

        recorreArbol(arbol,frectotal);
    }

    private static void recorreArbol(Nodo raiz,int frecTotal){

        if(raiz != null){
            raiz.prob =(double) raiz.frec/frecTotal;
            recorreArbol(raiz.izq,frecTotal);
            recorreArbol(raiz.der,frecTotal);
        }
    }
    public static double calculaEntropia(){
        setProbabilidad();
        return entropia(arbol);
    }

    private static double entropia(Nodo raiz) {
        if (raiz == null)
            return 0;
        else {
            if (raiz.izq == null && raiz.der == null)
                return (-Math.log(raiz.prob) / Math.log(2)) * raiz.prob;
            else
                return entropia(raiz.izq) + entropia(raiz.der);
        }
    }

    public static double rendimiento(){
        return calculaEntropia()/ Calculos.longitudMedia(Lectura.getInstance().getTablaCodificaHuffman());
    }

}
