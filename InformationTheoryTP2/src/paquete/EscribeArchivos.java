package paquete;

import java.io.*;
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
        FileReader fileReader=null;
        PrintWriter printWriter = null;

        //File doc = new File("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
        //File doc = new File("InformationTheoryTP2/src/assets/datos.txt");
        File doc = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");

        Scanner lector = null;
        try {
            lector = new Scanner(doc);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String simbolo = "";

        try {
            //FileWriter archivoSalida= new FileWriter("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.txt");
            //FileWriter archivoSalida= new FileWriter("InformationTheoryTP2/src/assets/huffman.txt");
            FileWriter archivoSalida= new FileWriter("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.txt");
            printWriter= new PrintWriter(archivoSalida);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(lector.hasNext()) {       // toma las palabras con los signos de puntuacion pegados.
            simbolo = lector.next();
            //System.out.println(simbolo+": "+Lectura.getInstance().getTablaHuffman().get(simbolo)+"\n");
            printWriter.print(Lectura.getInstance().getTablaHuffman().get(simbolo)+"\n");
        }
            System.out.println("archivo creado");
        }


    public void creaArchShannon() {
        FileReader fileReader=null;
        PrintWriter printWriter = null;

        File doc = new File("InformationTheoryTP2/src/assets/datos.txt");
        Scanner lector = null;
        try {
            lector = new Scanner(doc);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String simbolo = "";

        try {
            FileWriter archivoSalida= new FileWriter("InformationTheoryTP2/src/assets/shannon.txt");
            printWriter= new PrintWriter(archivoSalida);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        while(lector.hasNext()) {       // toma las palabras con los signos de puntuacion pegados.
            simbolo = lector.next();
            //System.out.println(simbolo+": "+Lectura.getInstance().getTablaHuffman().get(simbolo)+"\n");
            printWriter.print(Lectura.getInstance().getTablaShannon().get(simbolo)+"\n");
        }
        System.out.println("archivo creado");
    }


        public static void escrbir(String fichero, int []datos){

        }


}
