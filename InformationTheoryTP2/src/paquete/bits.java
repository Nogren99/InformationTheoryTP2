package paquete;

import java.io.*;
import java.util.Scanner;

public class bits {

    private String ruta_archivo = "C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\archivo.dat";

    public void escribir() {
        try {
            File doccc = new File("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.txt");

            ObjectOutputStream file = new ObjectOutputStream(new FileOutputStream(this.ruta_archivo));
            file.writeObject(doccc);
            System.out.println("archivo binario creado");
            file.close();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void leer()
    {
        try {
            File docccc;
            //Stream para leer archivo
            ObjectInputStream file = new ObjectInputStream(new FileInputStream( this.ruta_archivo ));
            //Se lee el objeto de archivo y este debe convertirse al tipo de clase que corresponde
            docccc = (File) file.readObject();
            //se cierra archivo
            file.close();

            Scanner lector = null;
            lector = new Scanner(docccc);
            String simbolo="";
            while(lector.hasNext()) {       // toma las palabras con los signos de puntuacion pegados.
                simbolo = lector.next();
                System.out.println(simbolo);
            }
            //Se utilizan metodos de la clase asi como variables guardados en el objeto
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

}