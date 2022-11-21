package main;


import paquete.*;


import java.io.IOException;


public class Main {

    public static void main(String [] args){
       //Huffman huffman = new Huffman();
        try {
            Lectura.getInstance().leeArch();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //System.out.println(Lectura.getInstance().getDiccionario());
        Huffman.codificaHuffman();
        //System.out.println(Lectura.getInstance().tablaCodificaHuffman);
        //System.out.println(Lectura.getInstance().getTablaHuffman());
        EscribeArchivos.getInstance().creaArchHuffman();
        //Lectura.getInstance().getTablaCodificaHuffman().clear();

        //System.out.println(Lectura.getInstance().getDiccionario());*/

        //bits b = new bits();
       // b.escribir();
       // b.decodifica();

        //b.leer();

//        ShannonFano.codificaShannon(0,Lectura.getInstance().getCantSimbolos()-1);
//        System.out.println("Tabla Shannon");
//        System.out.println(Lectura.getInstance().getTablaShannon());
//
//        EscribeArchivos.getInstance().creaArchShannon();

        //Lectura.getInstance().getTablaShannon().clear();


    }
}

