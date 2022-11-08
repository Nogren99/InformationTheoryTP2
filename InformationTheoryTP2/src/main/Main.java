package main;

import paquete.EscribeArchivos;
import paquete.Huffman;
import paquete.Lectura;

import java.io.IOException;

public class Main {

    public static void main(String [] args){
        Huffman huffman = new Huffman();
        try {
            Lectura.getInstance().leeArch();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        huffman.creaArbolHuffman();
        System.out.println(Lectura.getInstance().getTablaHuffman());
        EscribeArchivos.getInstance().creaArch();
        Lectura.getInstance().getTablaHuffman().clear();

        //System.out.println(Lectura.getInstance().getDiccionario());
    }
}
