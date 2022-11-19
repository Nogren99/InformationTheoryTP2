package paquete;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class bits {

    //private String ruta_archivo = "C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\archivo.dat";
    private String ruta_archivo;
    public void escribir() {
        try {
            //File doccc = new File("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.txt");

            //ruta_archivo = "C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.dat";
            //File doccc = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.txt");

            ruta_archivo = "InformationTheoryTP2/src/assets/huffman.dat";
            File doccc = new File("InformationTheoryTP2/src/assets/huffman.txt");

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

    public void decodifica(){

        FileReader fr = null;
        PrintWriter printWriter = null;
        char c;
        String codigo = "";
        Map<String, String> tabla = new HashMap<String, String>();  //CODIGO - SIMBOLO
        //Map<String, String> tabla = Lectura.getInstance().getTablaCodificaHuffman();
        FileWriter fw = null;
        //this.ruta_archivo = "C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.dat";
        this.ruta_archivo = "InformationTheoryTP2/src/assets/huffman.dat";

        int i = 0, cantCaracteres = 0;
        try {
            //fw = new FileWriter(new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\decodificacionHuffman.txt"));
            fw = new FileWriter(new File("InformationTheoryTP2/src/assets/decodificacionHuffman.txt"));
            printWriter=new PrintWriter(fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        try {
            File docccc;
            //Stream para leer archivo
            ObjectInputStream file = new ObjectInputStream(new FileInputStream( this.ruta_archivo ));
            //Se lee el objeto de archivo y este debe convertirse al tipo de clase que corresponde
            docccc = (File) file.readObject();
            //se cierra archivo
            file.close();
            String codigoo,palabraa;
            int palabras;


            //Armo hashmap (codigo - simbolo) leyendo huffman.dat

            Scanner lector = new Scanner(docccc);
            codigoo=lector.next();
            palabras=parseInt(codigoo);
            //System.out.println("codigoo : "+palabras);
            //palabras++;
            //System.out.println("a ver si suma:"+palabras);
            while(lector.hasNext() && i<palabras ) {       //
                palabraa = lector.next();
                System.out.println("aca hay una palabra :"+palabraa+"|");
                //Ultima palabra de la tabla: palparado

                codigoo = lector.next();
                System.out.println("aca hay uncodigo :"+codigoo+"|");
                System.out.println("---");

                //Map tabla (CODIGO - SIMBOLO)
                tabla.put(codigoo,palabraa);

                i++;
            }


            fr = new FileReader(docccc);
            c = (char) fr.read();

            while (c != -1){

                while(c != -1 && !tabla.containsKey(codigo)){
                    //System.out.println(codigo+"\n");
                    codigo = codigo+c;
                    c = (char) fr.read();
                }
                if(tabla.containsKey(codigo)){
                    System.out.println(codigo+"\n");
                    fw.write(tabla.get(codigo)+" ");
                }
                codigo = "";
                c = (char) fr.read();
            }

            fr.close();
            fw.close();
            //Se utilizan metodos de la clase asi como variables guardados en el objeto
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }


    }

}