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

    public static void descomprimir(String nombreComprimido, String nombreDescomprimido) throws IOException {

        FileInputStream reader = new FileInputStream(nombreComprimido);
        Map<String, String> tabla = leerTabla(reader);

        FileOutputStream writer = new FileOutputStream(nombreDescomprimido);

        byte b = 0;
        int cantBits = 0;
        String codigo = "";
        while (reader.available() > 0) {
            if (cantBits == 0) {
                cantBits = 8;
                b = reader.readNBytes(1)[0];
            }

            codigo += (b & 0x80) == 0 ? '0' : '1';
            b = (byte) (b << 1);
            cantBits--;

            if (tabla.containsKey(codigo)) {
                String palabra = tabla.get(codigo);
                writer.write(palabra.getBytes());
                writer.write(' ');
                codigo = "";
            }
        }

        writer.close();
        reader.close();
}
    private static Map<String, String> leerTabla(FileInputStream reader){
        //int cantSimbolos = Lectura.getInstance().getCantSimbolos();
        Map<String, String> tabla = new HashMap<>();
        String n;
        int cantSimbolos;
        //byte[] bval = new byte [Lectura.getInstance().simboloMasLargo];
        byte[] bCodigo = new byte[Huffman.getCodigoMasLargo()];
        byte[] bSimbolo = new byte[Lectura.getInstance().simboloMasLargo];

        String simbolo,codigo;

        try {
            cantSimbolos = reader.read();
//            bval = reader.readNBytes(4);
//            n = new String(bval);
//            cantSimbolos = Integer.valueOf(n);

            //System.out.println("Simbolos "+ cantSimbolos);
            for (int i=0; i<176; i++){
                bSimbolo = reader.readNBytes(Lectura.getInstance().simboloMasLargo);
                bCodigo = reader.readNBytes(Huffman.getCodigoMasLargo());
                simbolo = new String(bSimbolo);
                codigo = new String(bCodigo);
                //int j = 0;
                //while(j < Lectura.getInstance().simboloMasLargo )
                //bval =  reader.readNBytes(Lectura.getInstance().simboloMasLargo);
                //palabra = new String (bval);
                tabla.put(codigo,simbolo);
               // System.out.println("simbolo: "+simbolo+ "codigo: "+codigo);
            }

            //System.out.println("valor: "+bval.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    private static void escribeTabla(FileOutputStream writer, Map<String, String> tabla){

        int cantSimbolos = Lectura.getInstance().getCantSimbolos();
        ArrayList<String> indice = Lectura.getInstance().getIndice();

        StringBuilder cadenabinaria = new StringBuilder();
        //byte[] n = new byte[15];
        String simbolo,codigo;
        try {
            writer.write(cantSimbolos);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (int i = 0; i < cantSimbolos; i++) {

            byte[] bSimbolo = new byte[Lectura.getInstance().simboloMasLargo];

            char cad[] = new char[Lectura.getInstance().simboloMasLargo];
            //cad[Lectura.getInstance().simboloMasLargo-1] = '\0';

            for(int j = 0; j < indice.get(i).length(); j++){
                cad[j] = indice.get(i).charAt(j);
            }

            simbolo = String.copyValueOf(cad);
            //System.out.println("aux"+ aux);
            bSimbolo = simbolo.getBytes();
            //bSimbolo = indice.get(i).getBytes();

            byte[] bCodigo = new byte[Huffman.getCodigoMasLargo()];
            char cod[] = new char[Huffman.getCodigoMasLargo()];
            //cod[Huffman.getCodigoMasLargo()-1] = '\0';

            for(int j = 0; j < tabla.get(indice.get(i)).length() ; j++){
                cod[j] = tabla.get(indice.get(i)).charAt(j);
            }
            codigo = String.copyValueOf(cod);
            bCodigo = codigo.getBytes();
//            System.out.println(simbolo);
//            System.out.println(codigo);
            try {
                writer.write(bSimbolo);
                writer.write(bCodigo);
               // System.out.println(Arrays.toString(bval));
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

        Map<String, String> tablaCodificaHuffman = Lectura.getInstance().getTablaCodificaHuffman();
        ArrayList<String> indice = Lectura.getInstance().getIndice();

        //File doc = new File("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
        File doc = new File("InformationTheoryTP2/src/assets/datos.txt");
        //File doc = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");

        Scanner lector = null;
        try {
            lector = new Scanner(doc);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        String simbolo = "";

        FileWriter archivoSalida;
        try {
            //archivoSalida= new FileWriter("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.txt");
            archivoSalida= new FileWriter("InformationTheoryTP2/src/assets/huffman.txt");
            //archivoSalida = new FileWriter("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.txt");
            printWriter = new PrintWriter(archivoSalida);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        // ESCRIBE EL HEADER
        cantSimbolos = Lectura.getInstance().getCantSimbolos();
        printWriter.print(cantSimbolos + "\n");
        for (int i = 0; i < cantSimbolos; i++) {
            //System.out.println(indice.get(i)+" "+tablaCodificaHuffman.get(indice.get(i))+"\n");
            printWriter.print(indice.get(i) + " " + tablaCodificaHuffman.get(indice.get(i)) + "\n");
            //printWriter.print(tablaCodificaHuffman.get(indice.get(i))+"\n");
        }

        while (lector.hasNext()) {       // toma las palabras con los signos de puntuacion pegados.
            simbolo = lector.next();
            //System.out.println(simbolo+": "+Lectura.getInstance().getTablaCodificaHuffman().get(simbolo)+"\n");
            printWriter.print(Lectura.getInstance().getTablaCodificaHuffman().get(simbolo));
        }

        try {
            archivoSalida.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("archivo creado");
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


 /*   public void creaArchHuffman() {

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

        File doc = new File("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
        //File doc = new File("InformationTheoryTP2/InformationTheoryTP2/src/assets/datos.txt");
        //File doc = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");

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
    }*/

   /* public void creaArch(){

        Map<String, String> tablaCodificaHuffman = Lectura.getInstance().getTablaCodificaHuffman();
        ArrayList<String> indice = Lectura.getInstance().getIndice();
        int cantSimbolos = Lectura.getInstance().getCantSimbolos();

        String simbolo;
        StringBuilder cadenabinaria = new StringBuilder();
//        String nuevocod;
//        FileReader entrada = null;

        File doc = new File("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
        //File doc = new File("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
        Scanner lector = null;
        try {
            lector = new Scanner(doc);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try {
            //FileOutputStream fos = new FileOutputStream("C:\\Users\\marti\\OneDrive\\Documentos\\GitHub\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.huf");
            FileOutputStream fos = new FileOutputStream("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.huf");
            BufferedOutputStream salida = new BufferedOutputStream(fos);

            // escribri la tabla

            //------------------

            //entrada = new FileReader("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\datos.txt");
            //car = entrada.read();
            while (lector.hasNext()) {       // toma las palabras con los signos de puntuacion pegados.
                simbolo = lector.next();
                //System.out.println(simbolo+": "+tablaCodificaHuffman.get(simbolo));
                cadenabinaria.append(tablaCodificaHuffman.get(simbolo));
            }

            byte[] bval = new BigInteger(cadenabinaria.toString(), 2).toByteArray();
            // System.out.println(bval[0]);
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
    }*/

//    public void leeBin() {
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream("C:\\Users\\ACER\\repoTaller\\InformationTheoryTP2\\InformationTheoryTP2\\src\\assets\\huffman.huf");
//        } catch (FileNotFoundException e) {
//            throw new RuntimeException(e);
//        }
//        BufferedInputStream entrada = new BufferedInputStream(fis);
//        byte[] bval;
//        String cosa;
//        try {
//           // cosa = Lectura.getInstance().getTablaCodificaHuffman().get();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println(cosa);
//    }



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
