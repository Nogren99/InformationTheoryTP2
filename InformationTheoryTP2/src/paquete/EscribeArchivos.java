package paquete;

import javax.print.DocFlavor;
import java.io.*;
import java.math.BigInteger;
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
        int cantSimbolos = 0;
        String ruta_archivo;
        Map<String, String> tablaCodificaHuffman = Lectura.getInstance().getTablaCodificaHuffman();
        ArrayList<String> indice = Lectura.getInstance().getIndice();
        String simbolo = "";
        String codigo = "";

//        int longSimbolo = Lectura.getInstance().getSimboloMasLargo() - 1;
//        int longCodigo = Huffman.getCodigoMasLargo() - 1;


        ruta_archivo = "C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.dat";
       // ruta_archivo = "C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\archivo.dat";

        ObjectOutputStream file = null;
        try {
            file = new ObjectOutputStream(new FileOutputStream(ruta_archivo));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //File doc = new File("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
        //File doc = new File("InformationTheoryTP2/InformationTheoryTP2/src/assets/datos.txt");
        File doc = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");

        Scanner lector = null;
        try {
            lector = new Scanner(doc);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // ESCRIBE EL HEADER
        cantSimbolos = Lectura.getInstance().getCantSimbolos();
        try {
            file.writeObject(String.valueOf(cantSimbolos));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        //printWriter.print(cantSimbolos + "\n");
        for (int i = 0; i < cantSimbolos; i++) {
            try {
                simbolo = indice.get(i);
                codigo = tablaCodificaHuffman.get(indice.get(i));

                file.writeObject(simbolo);
                file.writeObject(codigo);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        while (lector.hasNext()) {       // toma las palabras con los signos de puntuacion pegados.
            simbolo = lector.next();
            try {
                file.writeObject(Lectura.getInstance().getTablaCodificaHuffman().get(simbolo));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        System.out.println("archivo creado");
    }

    public void creaArch(){

        Map<String, String> tablaCodificaHuffman = Lectura.getInstance().getTablaCodificaHuffman();
        ArrayList<String> indice = Lectura.getInstance().getIndice();
        int cantSimbolos = Lectura.getInstance().getCantSimbolos();

        String simbolo;
        StringBuilder cadenabinaria = new StringBuilder();
//        String nuevocod;
//        FileReader entrada = null;

        File doc = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
        Scanner lector = null;
        try {
            lector = new Scanner(doc);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }


        try {
            FileOutputStream fos = new FileOutputStream("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.huf");
            BufferedOutputStream salida = new BufferedOutputStream(fos);

            // escribri la tabla

            //------------------

            //entrada = new FileReader("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
            //car = entrada.read();
            while (lector.hasNext()) {       // toma las palabras con los signos de puntuacion pegados.
                simbolo = lector.next();
                System.out.println(simbolo+": "+tablaCodificaHuffman.get(simbolo));
                cadenabinaria.append(tablaCodificaHuffman.get(simbolo));
            }

            byte[] bval = new BigInteger(cadenabinaria.toString(), 2).toByteArray();
            try {
                salida.write(bval);
                lector.close();
                //entrada.close();
                salida.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } catch (FileNotFoundException e) {
            System.out.println("NO SE ENCONTRADO EL ARCHIVO DE ENTRADA Verifique que exista el archivo");
        }
    }

    public void leeBin(){

    }
//    private static void recontruir(String nombrearch, Nodo raiz) throws IOException {
//        int car;
//        String simbolo;
//        StringBuilder cadenabinaria = new StringBuilder();
//        String nuevocod;
//        FileReader entrada = null;
//
//        //File doc = new File("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
//        //File doc = new File("InformationTheoryTP2/InformationTheoryTP2/src/assets/datos.txt");
//        File doc = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
//
//        Scanner lector = null;
//        try {
//            lector = new Scanner(doc);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//
//        try {
//            FileOutputStream fos = new FileOutputStream(nombrearch.substring(0, nombrearch.length() - 3) + "huf");
//            BufferedOutputStream salida = new BufferedOutputStream(fos);
//
//            entrada = new FileReader(nombrearch);
//            car = entrada.read();
//
//            while (car != -1) {
//                //nuevocod = Util.buscaArbol(raiz, (char) car);
//                nuevocod = Lectura.getInstance().getTablaCodificaHuffman().get();
//                if (nuevocod != null) { //Nunca debería ser null
//                    cadenabinaria.append(nuevocod);
//                }
//                car = entrada.read(); //por la lectura anticipada
//            }
//            byte[] bval = new BigInteger(cadenabinaria.toString(), 2).toByteArray();
//            salida.write(bval);
//            entrada.close();
//            salida.close();
//        } catch (FileNotFoundException e) {
//            System.out.println("NO SE ENCONTRADO EL ARCHIVO DE ENTRADA Verifique que exista el archivo");
//        }
//    }

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
