package paquete;

import javax.print.DocFlavor;
import java.io.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;

public class EscribeArchivos {

    private static int nro = 3;
    private File doc;
    private BufferedWriter bw;

    private static EscribeArchivos instance = null;

    public static EscribeArchivos getInstance(){
        if(instance == null)
            instance = new EscribeArchivos();
        return instance;
    }

//    public void creaArch(int largoPalabra) {
//        FileReader fileReader=null;
//        PrintWriter printWriter = null;
//
//
//        try {
//            File archivoLectura = new File("src/assets/datos.txt");
//
//            fileReader= new FileReader(archivoLectura);
//            BufferedReader bufferedReader = new BufferedReader(fileReader, largoPalabra);
//            FileWriter archivoSalida= new FileWriter("src/assets/arch"+nro+".txt");
//            nro = nro + 2;
//            printWriter= new PrintWriter(archivoSalida);
//            String str;
//            char[] cad = new char[largoPalabra];
//            while ((bufferedReader.read(cad, 0,largoPalabra)) != -1){
//                str=String.valueOf(cad);
//                printWriter.print(Lectura.getInstance().getTablaHuffman().get(str)+"\n");
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }finally {
//            try {
//                if (null != fileReader)
//                    fileReader.close();
//            } catch (Exception e2) {
//                e2.printStackTrace();
//            }try{
//                if(printWriter!=null)
//                    printWriter.close();
//            }catch (Exception e3) {
//                e3.printStackTrace();
//            }
//            System.out.println("archivo creado");
//        }
//    }

    public void creaArchHuffman() {

        FileReader fileReader = null;
        PrintWriter printWriter = null;
        int cantSimbolos = 0;
        String ruta_archivo;

        int longSimbolo = Lectura.getInstance().getSimboloMasLargo() - 1;
        int longCodigo = Huffman.getCodigoMasLargo() - 1;

        char simboloCad[] = new char [longSimbolo];
//        char codigoCad[] = new char [longCodigo];


        String simbolo = "";
        String codigo = "";
        //String simbolo[] = new String[longSimbolo];


        //ruta_archivo = "C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\archivo.dat";
        ruta_archivo = "C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\archivo.dat";

        ObjectOutputStream file = null;
        try {
            file = new ObjectOutputStream(new FileOutputStream(ruta_archivo));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Map<String, String> tablaCodificaHuffman = Lectura.getInstance().getTablaCodificaHuffman();
        ArrayList<String> indice = Lectura.getInstance().getIndice();

        //File doc = new File("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
        //File doc = new File("InformationTheoryTP2/InformationTheoryTP2/src/assets/datos.txt");
        File doc = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");

        Scanner lector = null;
        try {
            lector = new Scanner(doc);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        FileWriter archivoSalida;
      /*  try {
            //FileWriter archivoSalida= new FileWriter("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.txt");
            //FileWriter archivoSalida= new FileWriter("InformationTheoryTP2/src/assets/huffman.txt");
            archivoSalida = new FileWriter("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.txt");
            printWriter = new PrintWriter(archivoSalida);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        // ESCRIBE EL HEADER
        cantSimbolos = Lectura.getInstance().getCantSimbolos();
        try {
            file.writeObject(String.valueOf(cantSimbolos));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //printWriter.print(cantSimbolos + "\n");
        for (int i = 0; i < cantSimbolos; i++) {
            //System.out.println(indice.get(i)+" "+tablaCodificaHuffman.get(indice.get(i))+"\n");
           // printWriter.print(indice.get(i) + " " + tablaCodificaHuffman.get(indice.get(i)) + "\n");
            try {

//                simboloCad = indice.get(i).toCharArray();
//                simboloCad[longSimbolo] = '\0';

//                codigoCad = tablaCodificaHuffman.get(indice.get(i)).toCharArray();
//                //codigoCad[longCodigo] = '\n';
                //System.out.println(simboloCad.length);
//                file.writeObject(simboloCad);
//                file.writeObject(codigoCad);

                simbolo = indice.get(i);
                for(int j = indice.get(i).length(); j <= longSimbolo; j++){
                        simbolo = simbolo + " ";
                }

                codigo = tablaCodificaHuffman.get(indice.get(i));
                for(int j = tablaCodificaHuffman.get(indice.get(i)).length(); j <= longCodigo; j++){
                    codigo = codigo + " ";
                }
                //System.out.println("SimboloLong: "+simbolo.length());
                file.writeObject(simbolo+codigo);

         //       file.writeObject('\n');
                //file.writeObject(indice.get(i) + " " + tablaCodificaHuffman.get(indice.get(i)) + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            //printWriter.print(tablaCodificaHuffman.get(indice.get(i))+"\n");
        }

        while (lector.hasNext()) {       // toma las palabras con los signos de puntuacion pegados.
            simbolo = lector.next();
            //aux = lector.next().toCharArray();
            //System.out.println(simbolo+": "+Lectura.getInstance().getTablaCodificaHuffman().get(simbolo)+"\n");
            //printWriter.print(Lectura.getInstance().getTablaCodificaHuffman().get(simbolo));
            try {
                file.writeObject(Lectura.getInstance().getTablaCodificaHuffman().get(simbolo));
                //file.writeObject(Lectura.getInstance().getTablaCodificaHuffman().get(aux));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

      /*  try {
            archivoSalida.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }*/

        System.out.println("archivo creado");
    }


    public void creaArchShannon() {

        FileReader fileReader=null;
        PrintWriter printWriter = null;
        FileWriter archivoSalida;
        File doc = new File("InformationTheoryTP2/src/assets/datos.txt");
        Scanner lector = null;

        Map <String, String> tablaCodificaShannon = Lectura.getInstance().getTablaCodificaShannon();
        ArrayList<String> indice = Lectura.getInstance().getIndice();
        int cantSimbolos = 0;

        try {
            lector = new Scanner(doc);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String simbolo = "";

        try {
            archivoSalida= new FileWriter("InformationTheoryTP2/src/assets/shannon.txt");
            printWriter= new PrintWriter(archivoSalida);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // ESCRIBE EL HEADER
        cantSimbolos = Lectura.getInstance().getCantSimbolos();
        printWriter.print(cantSimbolos+"\n");
        for(int i=0; i < cantSimbolos; i++){
            printWriter.print(indice.get(i)+" ");
            printWriter.print(tablaCodificaShannon.get(indice.get(i))+"\n");
        }

        while(lector.hasNext()) {       // toma las palabras con los signos de puntuacion pegados.
            simbolo = lector.next();
            //System.out.println(simbolo+": "+Lectura.getInstance().getTablaHuffman().get(simbolo)+"\n");
            printWriter.print(Lectura.getInstance().getTablaCodificaShannon().get(simbolo)+"\n");
        }

        try {
            archivoSalida.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("archivo creado");
    }


        public static void escrbir(String fichero, int []datos){

        }


}
