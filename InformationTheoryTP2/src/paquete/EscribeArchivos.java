package paquete;

import java.io.*;
import java.util.*;

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


    private static void escribeTabla(FileOutputStream writer, Map<String, String> tabla){

        int cantSimbolos = Lectura.getInstance().getCantSimbolos();
        ArrayList<String> indice = Lectura.getInstance().getIndice();
        String simbolo,codigo;

        try {
            writer.write(cantSimbolos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < cantSimbolos; i++) {

            byte[] bSimbolo = new byte[Lectura.getInstance().simboloMasLargo];
            char cad[] = new char[Lectura.getInstance().simboloMasLargo];
            for(int j = 0; j < indice.get(i).length(); j++){
                cad[j] = indice.get(i).charAt(j);
            }

            simbolo = String.copyValueOf(cad);
            bSimbolo = simbolo.getBytes();

            byte[] bCodigo = new byte[Huffman.getCodigoMasLargo()];
            char cod[] = new char[Huffman.getCodigoMasLargo()];

            for(int j = 0; j < tabla.get(indice.get(i)).length() ; j++){
                cod[j] = tabla.get(indice.get(i)).charAt(j);
            }
            codigo = String.copyValueOf(cod);
            bCodigo = codigo.getBytes();

            try {
                writer.write(bSimbolo);
                writer.write(bCodigo);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public static void creaArch(String nombreArchivo, String nombreArchivoSalida, Map<String, String> tabla) throws IOException {

        File archivo = new File(nombreArchivo);
        Scanner scanner = new Scanner(archivo);
        FileOutputStream writer = new FileOutputStream(nombreArchivoSalida);
        byte aux = 0;
        int cantBits = 0;

        escribeTabla(writer, tabla);

        while (scanner.hasNextLine()) {
            Scanner scanner2 = new Scanner(scanner.nextLine());
            while (scanner2.hasNext()) {
                String palabra = scanner2.next();
                String codigo = tabla.get(palabra);
                for (int i = 0; i < codigo.length(); i++) {
                    aux = (byte) (aux << 1);
                    if (codigo.charAt(i) == '1') {
                        aux = (byte) (aux | 1);
                    }
                    cantBits++;
                    if (cantBits == 8) {
                        writer.write(aux);
                        aux = 0;
                        cantBits = 0;
                    }
                }
            }
            scanner2.close();
        }

        if (cantBits != 0) {
            aux = (byte) (aux << (8 - cantBits));
            writer.write(aux);
        }

        scanner.close();
        writer.close();
    }


    public void creaArchHuffman() {

        FileReader fileReader = null;
        PrintWriter printWriter = null;
        int cantSimbolos = 0;
        int cantTotalCod = 0;
        Map<String, String> tablaCodificaHuffman = Lectura.getInstance().getTablaCodificaHuffman();
        ArrayList<String> indice = Lectura.getInstance().getIndice();

        File doc = new File("InformationTheoryTP2/src/assets/datos.txt");

        Scanner lector = null;
        try {
            lector = new Scanner(doc);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String simbolo = "";

        FileWriter archivoSalida;
        try {
            archivoSalida= new FileWriter("InformationTheoryTP2/src/assets/huffman.txt");
            printWriter = new PrintWriter(archivoSalida);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // ESCRIBE EL HEADER
        cantSimbolos = Lectura.getInstance().getCantSimbolos();
        printWriter.print(cantSimbolos + "\n");
        for (int i = 0; i < cantSimbolos; i++) {
            printWriter.print(indice.get(i) + " " + tablaCodificaHuffman.get(indice.get(i)) + "\n");
        }

        while (lector.hasNext()) {
            simbolo = lector.next();
            printWriter.print(Lectura.getInstance().getTablaCodificaHuffman().get(simbolo));
            cantTotalCod += Lectura.getInstance().getTablaCodificaHuffman().get(simbolo).length();
        }

        try {
            archivoSalida.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("archivo creado");
        //System.out.println("Cant bits escritos Huffman "+ cantTotalCod);
    }

    public void creaArchShannon() {

        FileReader fileReader = null;
        PrintWriter printWriter = null;
        int cantSimbolos = 0;
        int cantTotalCod = 0;
        Map<String, String> tablaCodificaShannon = Lectura.getInstance().getTablaCodificaShannon();
        ArrayList<String> indice = Lectura.getInstance().getIndice();

        File doc = new File("InformationTheoryTP2/src/assets/datos.txt");

        Scanner lector = null;
        try {
            lector = new Scanner(doc);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String simbolo = "";

        FileWriter archivoSalida;
        try {
            archivoSalida= new FileWriter("InformationTheoryTP2/src/assets/shannon.txt");
            printWriter = new PrintWriter(archivoSalida);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // ESCRIBE EL HEADER
        cantSimbolos = Lectura.getInstance().getCantSimbolos();
        printWriter.print(cantSimbolos + "\n");
        for (int i = 0; i < cantSimbolos; i++) {
            printWriter.print(indice.get(i) + " " + tablaCodificaShannon.get(indice.get(i)) + "\n");
        }

        while (lector.hasNext()) {       // toma las palabras con los signos de puntuacion pegados.
            simbolo = lector.next();
            printWriter.print(tablaCodificaShannon.get(simbolo));
            cantTotalCod += Lectura.getInstance().getTablaCodificaShannon().get(simbolo).length();
        }

        try {
            archivoSalida.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("archivo creado");
        //System.out.println("Cant bits escritos Shannon"+ cantTotalCod);
    }
        public static void escrbir(String fichero, int []datos){

        }


}
