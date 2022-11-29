package paquete;

import java.io.*;
import java.nio.charset.StandardCharsets;
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

//    public static void descomprimir(String nombreComprimido, String nombreDescomprimido) throws IOException {
//
//        FileInputStream reader = new FileInputStream(nombreComprimido);
//        Map<String, String> tabla = leerTabla(reader);
//
//        FileOutputStream writer = new FileOutputStream(nombreDescomprimido);
//
//        byte b = 0;
//        int cantBits = 0;
//        String codigo = "";
//        while (reader.available() > 0) {
//            if (cantBits == 0) {
//                cantBits = 8;
//                b = reader.readNBytes(1)[0];
//            }
//
//            codigo += (b & 0x80) == 0 ? '0' : '1';
//            b = (byte) (b << 1);
//            cantBits--;
//
//            if (tabla.containsKey(codigo)) {
//                String palabra = tabla.get(codigo);
//                writer.write(palabra.getBytes());
//                writer.write(' ');
//                codigo = "";
//            }
//        }
//
//        writer.close();
//        reader.close();
//    }
//    private static Map<String, String> leerTabla(FileInputStream reader){
//        //int cantSimbolos = Lectura.getInstance().getCantSimbolos();
//        Map<String, String> tabla = new HashMap<>();
//        String n;
//        int cantSimbolos;
//        //byte[] bval = new byte [Lectura.getInstance().simboloMasLargo];
//        byte[] bCodigo = new byte[Huffman.getCodigoMasLargo()];
//        byte[] bSimbolo = new byte[Lectura.getInstance().simboloMasLargo];
//        String simbolo,codigo,aux;
//        StringBuilder stB1 = new StringBuilder();
//        StringBuilder stB2 = new StringBuilder();
//
//        try {
//            //cantSimbolos = reader.readNBytes(4);
////            bval = reader.readNBytes(4);
////            n = new String(bval);
////            cantSimbolos = Integer.valueOf(n);
//
//            for (int i=0; i<176; i++){
//                bSimbolo = reader.readNBytes(Lectura.getInstance().simboloMasLargo);
//                bCodigo = reader.readNBytes(Huffman.getCodigoMasLargo());
//                simbolo = new String(bSimbolo);
//                if(simbolo.length() != Lectura.getInstance().simboloMasLargo)
//                    reader.readNBytes(1);
//                codigo = new String(bCodigo);
//                if(!Character.isDigit(codigo.charAt(0)))
//
//                //System.out.println("simbolo: "+simbolo);
////                int j = 0;
////                while (j < simbolo.length() && Character.isLetterOrDigit(simbolo.charAt(j))){
////                //while (j < simbolo.length() && Character.isAlphabetic(simbolo.charAt(j))){
////                //while (j < simbolo.length() && simbolo.charAt(j) != ' '){
////                    stB1.append(String.valueOf(simbolo.charAt(j)));
////                    j++;
////                }
////                simbolo = stB1.toString();
////                j = 0;
////
////                while (j < codigo.length() && Character.isLetterOrDigit(codigo.charAt(j))){
////                //while (j < codigo.length() && Character.isAlphabetic(codigo.charAt(j))){
////                //while (j < codigo.length() && codigo.charAt(j) != ' '){
////                    stB2.append(String.valueOf(codigo.charAt(j)));
////                    j++;
////                }
////                codigo = stB2.toString();
//
//                //int j = 0;
//                //while(j < Lectura.getInstance().simboloMasLargo )
//                //bval =  reader.readNBytes(Lectura.getInstance().simboloMasLargo);
//                //palabra = new String (bval);
//               // tabla.put(codigo,simbolo);
//                System.out.println("simbolo: "+simbolo+ "\tcodigo: "+codigo);
//            }
//
//            //System.out.println("valor: "+bval.toString());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        //System.out.println(tabla);
//        return tabla;
//    }

    public static void comprimir(String nombreArchivo, String nombreArchivoSalida, Map<String, String> tabla) throws IOException {

        File archivo = new File(nombreArchivo);
        Scanner scanner = new Scanner(archivo);
        DataOutputStream writer = new DataOutputStream(new FileOutputStream(nombreArchivoSalida));

        escribeTabla(writer,tabla);
        escribeCuerpoArch(nombreArchivo,writer,tabla);
        writer.close();
    }

    private static void escribeTabla(DataOutputStream writer,Map<String, String> tabla) throws IOException {

        int cantSimbolos = Lectura.getInstance().getCantSimbolos();


        // Se escribe la tabla.
        writer.writeInt(cantSimbolos); // 1. Entero con la cantidad de palabras distintas del archivo.

        for (Map.Entry<String, String> codAct : tabla.entrySet()) {
            escribirSimbolo(writer, codAct.getKey()); // 2. Caracteres correspondientes a la palabra (terminada en ' ').
            writer.writeByte(codAct.getValue().length()); // 3. Byte con la longitud del codigo de Huffman de la palabra.
            escribeBin(writer, codAct.getValue()); // 4. Codigo de Huffman de la palabra (en bits).
        }
    }

    public static void escribeCuerpoArch(String nombreArchivo,DataOutputStream writer, Map<String, String> tabla) throws IOException {

        Scanner reader;
        try {
            reader = new Scanner(new FileInputStream(nombreArchivo));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        // Archivo comprimido.
        String palabra;
        while (reader.hasNext()) {
            palabra = reader.next();
            escribeBin(writer, tabla.get(palabra));
        }
    }

    private static void escribirSimbolo(DataOutputStream writer, String palabra) throws IOException {
        palabra += ' ';         // termina el string con un espacio para la posterior lectura
        byte[] bytes = palabra.getBytes(StandardCharsets.UTF_8);
        for (byte b : bytes) {
            writer.writeByte(b);
        }
    }

    private static void escribeBin(DataOutputStream writer, String codigo) throws IOException {

        boolean vector[] = new boolean[8];  // 1 byte que se escribe en el archivo
        int i = 0;
        int carActual = 0;
        byte byteAEscribir = 0;
        while (carActual < codigo.length()) {
            vector[i] = codigo.charAt(carActual) == '1';
            i++;
            if (i == 8) {
                for (int j = 0; j < i; j++) {
                    byteAEscribir = (byte) (byteAEscribir << 1);
                    if (vector[j]) {
                        byteAEscribir = (byte) (byteAEscribir | 1);
                    }
                }
                writer.writeByte(byteAEscribir);
                writer.flush();
                byteAEscribir = 0;
                i = 0;
            }
            carActual++;
        }
        if (i != 8 && i != 0) {
            for (int j = 0; j < i; j++) {
                byteAEscribir = (byte) (byteAEscribir << 1);
                if (vector[j]) {
                    byteAEscribir = (byte) (byteAEscribir | 1);
                }
            }
            byteAEscribir = (byte) (byteAEscribir << (8 - i));
            writer.writeByte(byteAEscribir);
            writer.flush();
        }
    }
//----------------------------------------DESCOMPRIME----------------------------

    public static void descomprimir(String nombreComprimido, String nombreDescomprimido) throws IOException {

        DataInputStream reader = new DataInputStream(new FileInputStream(nombreComprimido));
        FileWriter writer = new FileWriter(nombreDescomprimido);

        // Se lee la tabla.
        int cantSimbolos = reader.readInt(); // 1. Entero con la cantidad de palabras distintas del archivo.
        //TreeMap<String, String> tabla = new TreeMap<String, String>();
        Map<String, String> tabla = new HashMap<String, String>();

        for (int i = 0; i < cantSimbolos; i++) {
            String simbolo = leerSimbolo(reader); // 2. Caracteres correspondientes al simbolo (terminada en ' ').
            int tamanoCod = reader.readByte(); // 3. Byte con la tamano del codigo de Huffman del simbolo.
            String codigo = leerBin(reader, tamanoCod); // 4. Codigo de Huffman del simbolo (en bits).
            tabla.put(simbolo, codigo);
        }

        // Se descomprime el archivo.
        byte byteLeido;
        boolean escribio;
        String codigo = "";
        int i = 0, j = 0;
        while (reader.available() > 0) {
            byteLeido = reader.readByte();
            j = 0;
            escribio = false;
            while (j < 8 && !escribio) {
                codigo = codigo + ((byteLeido & 0x80) == 0 ? '0' : '1');
                byteLeido = (byte) (byteLeido << 1);
                if (tabla.containsValue(codigo)) {
                    for (Map.Entry<String, String> entry : tabla.entrySet()) {
                        if (entry.getValue().equals(codigo)) {
                            writer.write(entry.getKey()+" ");
                            writer.flush();
                            codigo = "";
                            escribio = true;
                        }
                    }
                }
                j++;
            }
        }
        reader.close();
        writer.close();
    }

    private static String leerBin(DataInputStream reader, int tamano) throws IOException {
        byte b;
        String codigo = "";
        double cantBytes = Math.ceil((double) tamano/8);
        int cond;

        for (int i = 0; i < cantBytes; i++) {
            b = reader.readByte();
            cond = 0;
            if(tamano-8*i >= 8)
                cond = 8;
            else
                cond = tamano-8*i;

            for (int j = 0; j < cond; j++) {
                codigo += (b & 0x80) >> 7;
                b = (byte) (b << 1);
            }
        }

        return codigo;
    }

    private static String leerSimbolo(DataInputStream reader) throws IOException {

        byte letra = reader.readByte();
        byte[] bytes = new byte[50];
        int i = 0;
        String simbolo;

        while (letra != ' ') {
            bytes[i] = (byte) letra;
            i++;
            letra = reader.readByte();
        }
        simbolo = new String(bytes, 0, i, StandardCharsets.UTF_8);
        
        return simbolo;
    }




//    public static void comprime(String nombreArchivo, String nombreArchivoSalida, Map<String, String> tabla) throws IOException {
//
//        File archivo = new File(nombreArchivo);
//        Scanner scanner = new Scanner(archivo);
//        DataOutputStream writer = new DataOutputStream(new FileOutputStream(nombreArchivoSalida));
//
//        byte aux = 0;
//        int cantBits = 0;
//
//        //escribeTabla(writer, tabla);
//        escribeTabla(writer,tabla);
//
//        while (scanner.hasNextLine()) {
//            Scanner scanner2 = new Scanner(scanner.nextLine());
//            while (scanner2.hasNext()) {
//                String palabra = scanner2.next();
//                String codigo = tabla.get(palabra);
//                for (int i = 0; i < codigo.length(); i++) {
//                    aux = (byte) (aux << 1);
//                    if (codigo.charAt(i) == '1') {
//                        aux = (byte) (aux | 1);
//                    }
//                    cantBits++;
//                    if (cantBits == 8) {
//                        writer.write(aux);
//                        aux = 0;
//                        cantBits = 0;
//                    }
//                }
//            }
//            scanner2.close();
//        }
//
//        if (cantBits != 0) {
//            aux = (byte) (aux << (8 - cantBits));
//            writer.write(aux);
//        }
//
//        scanner.close();
//        writer.close();
//    }


//    public void creaArchHuffman() {
//
//        FileReader fileReader = null;
//        PrintWriter printWriter = null;
//        int cantSimbolos = 0;
//        int cantTotalCod = 0;
//        Map<String, String> tablaCodificaHuffman = Lectura.getInstance().getTablaCodificaHuffman();
//        ArrayList<String> indice = Lectura.getInstance().getIndice();
//
//        File doc = new File("InformationTheoryTP2/src/assets/datos.txt");
//
//        Scanner lector = null;
//        try {
//            lector = new Scanner(doc);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        String simbolo = "";
//
//        FileWriter archivoSalida;
//        try {
//            archivoSalida= new FileWriter("InformationTheoryTP2/src/assets/huffman.txt");
//            printWriter = new PrintWriter(archivoSalida);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        // ESCRIBE EL HEADER
//        cantSimbolos = Lectura.getInstance().getCantSimbolos();
//        printWriter.print(cantSimbolos + "\n");
//        for (int i = 0; i < cantSimbolos; i++) {
//            printWriter.print(indice.get(i) + " " + tablaCodificaHuffman.get(indice.get(i)) + "\n");
//        }
//
//        while (lector.hasNext()) {
//            simbolo = lector.next();
//            printWriter.print(Lectura.getInstance().getTablaCodificaHuffman().get(simbolo));
//            cantTotalCod += Lectura.getInstance().getTablaCodificaHuffman().get(simbolo).length();
//        }
//
//        try {
//            archivoSalida.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("archivo creado");
//        //System.out.println("Cant bits escritos Huffman "+ cantTotalCod);
//    }
//
//    public void creaArchShannon() {
//
//        FileReader fileReader = null;
//        PrintWriter printWriter = null;
//        int cantSimbolos = 0;
//        int cantTotalCod = 0;
//        Map<String, String> tablaCodificaShannon = Lectura.getInstance().getTablaCodificaShannon();
//        ArrayList<String> indice = Lectura.getInstance().getIndice();
//
//        File doc = new File("InformationTheoryTP2/src/assets/datos.txt");
//
//        Scanner lector = null;
//        try {
//            lector = new Scanner(doc);
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        String simbolo = "";
//
//        FileWriter archivoSalida;
//        try {
//            archivoSalida= new FileWriter("InformationTheoryTP2/src/assets/shannon.txt");
//            printWriter = new PrintWriter(archivoSalida);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        // ESCRIBE EL HEADER
//        cantSimbolos = Lectura.getInstance().getCantSimbolos();
//        printWriter.print(cantSimbolos + "\n");
//        for (int i = 0; i < cantSimbolos; i++) {
//            printWriter.print(indice.get(i) + " " + tablaCodificaShannon.get(indice.get(i)) + "\n");
//        }
//
//        while (lector.hasNext()) {       // toma las palabras con los signos de puntuacion pegados.
//            simbolo = lector.next();
//            printWriter.print(tablaCodificaShannon.get(simbolo));
//            cantTotalCod += Lectura.getInstance().getTablaCodificaShannon().get(simbolo).length();
//        }
//
//        try {
//            archivoSalida.close();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        System.out.println("archivo creado");
//        //System.out.println("Cant bits escritos Shannon"+ cantTotalCod);
//    }
//        public static void escrbir(String fichero, int []datos){
//
//        }


}
