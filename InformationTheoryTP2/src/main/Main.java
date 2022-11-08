package main;

import paquete.Lectura;

import java.io.IOException;

public class Main {

    public static void main(String [] args){

        try {
            Lectura.getInstance().leeArch();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println(Lectura.getInstance().getCodigo());
    }
}
