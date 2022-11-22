package main;


import paquete.*;


import java.io.IOException;


public class Main {

    public static void main(String [] args){
       //Huffman huffman = new Huffman();
        Calculos calculos = new Calculos();
        try {
            Lectura.getInstance().leeArch();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Huffman.codificaHuffman();

        //huffman
        EscribeArchivos.getInstance().creaArchHuffman();
        try {
            EscribeArchivos.comprimir("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt", "C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.huf", Lectura.getInstance().getTablaCodificaHuffman());
            EscribeArchivos.descomprimir("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.huf", "C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\descomprimidoHuffman.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //Lectura.getInstance().getTablaCodificaHuffman().clear();

        ShannonFano.codificaShannon(0,Lectura.getInstance().getCantSimbolos()-1);
        calculos.calculaCantInfo();

        System.out.println("LongMedia Huffman: "+calculos.longitudMedia(Lectura.getInstance().getTablaCodificaHuffman()));
        System.out.println("Rendimiento huffman: "+calculos.rendimiento(Lectura.getInstance().getTablaCodificaHuffman()));
        System.out.println("Entropia arbol: "+ Huffman.calculaEntropia());
        System.out.println("Rendimiento huffman"+ Huffman.rendimiento()); // arbol

//        System.out.println("LongMedia Shannon: "+calculos.longitudMedia(Lectura.getInstance().getTablaCodificaShannon()));
//        System.out.println("Rendimiento Shannon: "+calculos.rendimiento(Lectura.getInstance().getTablaCodificaShannon()));

    }
}

