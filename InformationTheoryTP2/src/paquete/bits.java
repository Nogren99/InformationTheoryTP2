package paquete;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static java.lang.Integer.parseInt;

public class bits {

    private static String ruta_archivo;
    //private String ruta_archivo;
    public void escribir() {
        try {
            //File doccc = new File("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.txt");

            //ruta_archivo = "C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.dat";
            File doccc = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.txt");

//            ruta_archivo = "InformationTheoryTP2/src/assets/huffman.dat";
//            File doccc = new File("InformationTheoryTP2/src/assets/huffman.txt");

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
        String cantCaracteres;
        //Stream para leer archivo
        ObjectInputStream file = new ObjectInputStream(new FileInputStream( this.ruta_archivo ));

        char c;
        String codigo = "";
        Map<String, String> tabla = new HashMap<String, String>();  //CODIGO - SIMBOLO
        String codigoo = "";
        String palabraa="";
        String dato ="";
        int palabras, i =0;

        //Se lee el objeto de archivo y este debe convertirse al tipo de clase que corresponde
        try {
            cantCaracteres = (String) file.readObject();
            while (i<parseInt(cantCaracteres)){
                palabraa = (String)file.readObject();
                codigoo = (String) file.readObject();
                tabla.put(codigoo, palabraa);
                //System.out.println("Palabra "+palabraa);
                //System.out.println("Codigo "+codigoo);
                i++;
            }
            while ((dato = (String)file.readObject()) != null){
                System.out.print(tabla.get(dato)+" ");
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        //se cierra archivo
        try {
            file.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    } catch (FileNotFoundException e) {
        throw new RuntimeException(e);
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    }

    public static void decodificaHuffman(){

        FileReader fr = null;
        PrintWriter printWriter = null;
        char c;
        String codigo = "";
        Map<String, String> tabla = new HashMap<String, String>();  //CODIGO - SIMBOLO
        //Map<String, String> tabla = Lectura.getInstance().getTablaCodificaHuffman();
        FileWriter fw = null;
        ruta_archivo = "InformationTheoryTP2/src/assets/huffman.txt";

        int i = 0, cantCaracteres = 0;
        try {
            fw = new FileWriter(new File("InformationTheoryTP2/src/assets/decodificacionHuffman.txt"));

            printWriter=new PrintWriter(fw);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            File docccc;
            //Stream para leer archivo
            ObjectInputStream file = new ObjectInputStream(new FileInputStream( ruta_archivo ));
            //Se lee el objeto de archivo y este debe convertirse al tipo de clase que corresponde
            docccc = (File) file.readObject();
            //se cierra archivo
            file.close();
            String codigoo,palabraa;
            int palabras;


            //Armo hashmap (codigo - simbolo)

            Scanner lector = new Scanner(docccc);
            codigoo = lector.next();
            palabras = parseInt(codigoo);
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

            String codigazo=lector.next();
            int aux=codigazo.length()-1;
            i=0;

            while (i<aux){

                String codigoActual="";
                while(!tabla.containsKey(codigoActual) && i<aux){
                    System.out.println("i:"+i+"c:"+codigazo.charAt(i));
                    c=codigazo.charAt(i);
                    codigoActual=codigoActual+c;
                    i++;
                    //System.out.println("asi va el codigo lol:"+codigoActual);
                }
                //System.out.println("[codigoactual FINwhile] "+codigoActual+"");
                if(tabla.containsKey(codigoActual)){
                    System.out.println("palabra encontrada -->"+tabla.get(codigoActual)+"\n");
                    if(tabla.get(codigoActual).contains("."))
                        printWriter.print(tabla.get(codigoActual)+ "\n");
                    else
                        printWriter.print(tabla.get(codigoActual)+ " ");

                }

            }

            fw.close();
        } catch (ClassNotFoundException ex) {
            System.out.println(ex);
        } catch (IOException ex) {
            System.out.println(ex);
        }


    }

}