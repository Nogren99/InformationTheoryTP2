package main;

import paquete.*;

import java.io.IOException;
import java.sql.SQLOutput;

public class Main {

    public static void main(String [] args){
        Huffman huffman = new Huffman();
        try {
            Lectura.getInstance().leeArch();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ShannonFano.codificar(0,Lectura.getInstance().getCantSimbolos()-1);
        ShannonFano.creaTablaShannon();
        System.out.println("Tabla Shannon");
        System.out.println(Lectura.getInstance().getTablaShannon());
        EscribeArchivos.getInstance().creaArchShannon();
       // huffman.creaArbolHuffman();
//        System.out.println("Tabla UFFFF");
//        System.out.println(Lectura.getInstance().getTablaHuffman());

        //System.out.println(Lectura.getInstance().getTablaHuffman());
        //EscribeArchivos.getInstance().creaArchHuffman();
        Lectura.getInstance().getTablaHuffman().clear();
        //System.out.println(Lectura.getInstance().getDiccionario());
    }
}
