package main;

import paquete.EscribeArchivos;
import paquete.Huffman;
import paquete.Lectura;

import paquete.bits;
import java.io.IOException;
import paquete.ShannonFano;
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

        huffman.creaArbolHuffman();
       // System.out.println(Lectura.getInstance().getTablaHuffman());
        EscribeArchivos.getInstance().creaArch();
        Lectura.getInstance().getTablaHuffman().clear();

        //System.out.println(Lectura.getInstance().getDiccionario());*/
        bits b = new bits();
        b.escribir();
        b.leer();


   /*     ShannonFano.codificar(0,Lectura.getInstance().getCantSimbolos()-1);
        ShannonFano.creaTablaShannon();
        System.out.println("Tabla Shannon");
        System.out.println(Lectura.getInstance().getTablaShannon());
        //huffman.creaArbolHuffman();
//        System.out.println("Tabla UFFFF");
//        System.out.println(Lectura.getInstance().getTablaHuffman());

        //System.out.println(Lectura.getInstance().getTablaHuffman());
        //EscribeArchivos.getInstance().creaArch();
        Lectura.getInstance().getTablaHuffman().clear();
        //System.out.println(Lectura.getInstance().getDiccionario());*/

    }
}

