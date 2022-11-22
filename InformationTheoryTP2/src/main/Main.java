package main;


import paquete.*;


import java.io.IOException;


public class Main {

    public static void main(String [] args){

        Calculos calculos = new Calculos();
        try {
            Lectura.getInstance().leeArch();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
//---------------------------------------------HUFFMAN--------------------------------------------------
        Huffman.codificaHuffman();
        //EscribeArchivos.getInstance().creaArchHuffman();

        try {
//            EscribeArchivos.comprimir("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt", "C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.huf", Lectura.getInstance().getTablaCodificaHuffman());
//            EscribeArchivos.descomprimir("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.huf", "C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\descomprimidoHuffman.txt");
            EscribeArchivos.creaArch("InformationTheoryTP2/src/assets/datos.txt", "InformationTheoryTP2/src/assets/huffman.huf", Lectura.getInstance().getTablaCodificaHuffman());

           // EscribeArchivos.descomprimir("InformationTheoryTP2/src/assets/huffman.huf", "InformationTheoryTP2/src/assets/descomprimidoHuffman.txt");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//---------------------------------------------SHANNON--------------------------------------------------
        ShannonFano.codificaShannon(0,Lectura.getInstance().getCantSimbolos()-1);

        try {
            EscribeArchivos.creaArch("InformationTheoryTP2/src/assets/datos.txt", "InformationTheoryTP2/src/assets/shannon.fan", Lectura.getInstance().getTablaCodificaShannon());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        calculos.calculaCantInfo();
        System.out.println("LongMedia Huffman: "+calculos.longitudMedia(Lectura.getInstance().getTablaCodificaHuffman()));
        System.out.println("Rendimiento Huffman: "+calculos.rendimiento(Lectura.getInstance().getTablaCodificaHuffman()));
        System.out.println("Redundancia Huffman: "+calculos.redundancia(Lectura.getInstance().getTablaCodificaHuffman()));
        //System.out.println("Entropia arbol: "+ Huffman.calculaEntropia());
        //System.out.println("Rendimiento huffman"+ Huffman.rendimiento()); // arbol

        System.out.println("LongMedia Shannon: "+calculos.longitudMedia(Lectura.getInstance().getTablaCodificaShannon()));
        System.out.println("Rendimiento Shannon: "+calculos.rendimiento(Lectura.getInstance().getTablaCodificaShannon()));
        System.out.println("Redundancia Shannon: "+calculos.redundancia(Lectura.getInstance().getTablaCodificaShannon()));

        System.out.println("Peso de la tabla en compresion por Huffman: "+ calculos.tamanoTablaHuffman()+" bytes");
        System.out.println("Peso de la tabla en compresion por Shannon: "+ calculos.tamanoTablaShannon()+" bytes");
    }
}

