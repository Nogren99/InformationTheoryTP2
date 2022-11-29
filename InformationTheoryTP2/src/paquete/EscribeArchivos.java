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

    //----------------------------------------COMPRIME----------------------------
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

        writer.writeInt(cantSimbolos);

        for (Map.Entry<String, String> codAct : tabla.entrySet()) {
            escribirSimbolo(writer, codAct.getKey());
            writer.writeByte(codAct.getValue().length());
            escribeBin(writer, codAct.getValue());
        }
    }

    private static void escribeCuerpoArch(String nombreArchivo,DataOutputStream writer, Map<String, String> tabla) throws IOException {

        Scanner reader;
        try {
            reader = new Scanner(new FileInputStream(nombreArchivo));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String palabra;
        while (reader.hasNext()) {
            palabra = reader.next();
            escribeBin(writer, tabla.get(palabra));
        }
    }

    private static void escribirSimbolo(DataOutputStream writer, String palabra) throws IOException {
        palabra += ' ';                                             // termina el string con un espacio para la posterior lectura
        byte[] bytes = palabra.getBytes(StandardCharsets.UTF_8);
        for (byte b : bytes) {
            writer.writeByte(b);
        }
    }

    private static void escribeBin(DataOutputStream writer, String codigo) throws IOException {

        int posCod = 0;
        boolean vector[] = new boolean[8];
        int posVec = 0;
        byte b = 0;
        while (posCod < codigo.length()) {
            vector[posVec] = codigo.charAt(posCod) == '1';
            posVec++;
            if (posVec == 8) {
                for (int j = 0; j < posVec; j++) {
                    b = (byte) (b << 1);
                    if (vector[j]) {
                        b = (byte) (b | 1);
                    }
                }
                writer.writeByte(b);
                writer.flush();
                b = 0;
                posVec = 0;
            }
            posCod++;
        }
        if (posVec != 8 && posVec != 0) {
            for (int i = 0; i < posVec; i++) {
                b = (byte) (b << 1);
                if (vector[i])
                    b = (byte) (b | 1);
            }
            b = (byte) (b << (8 - posVec));
            writer.writeByte(b);
            writer.flush();
        }
    }
//----------------------------------------DESCOMPRIME----------------------------
    private static Map<String, String> leeTabla(DataInputStream reader) throws IOException {

        int cantSimbolos = reader.readInt();
        Map<String, String> tabla = new HashMap<String, String>();

        for (int i = 0; i < cantSimbolos; i++) {
            String simbolo = leerSimbolo(reader);
            int tamanoCod = reader.readByte();
            String codigo = leerBin(reader, tamanoCod);
            tabla.put(codigo,simbolo);
        }
        return tabla;
    }

    public static void descomprimir(String nombreComprimido, String nombreDescomprimido) throws IOException {

        DataInputStream reader = new DataInputStream(new FileInputStream(nombreComprimido));
        FileWriter writer = new FileWriter(nombreDescomprimido);

        Map<String, String> tabla = leeTabla(reader);  // key-> codigo, value-> simbolo

        byte b;
        boolean bool;
        String codigo = "";
        int i = 0, j = 0;

        while (reader.available() > 0) {
            b = reader.readByte();
            j = 0;
            bool = false;
            while (j < 8 && !bool) {
                if((b & 0x80) == 0)
                    codigo += '0';
                else
                    codigo += '1';
                b = (byte) (b << 1);

                if (tabla.containsKey(codigo)) {
                    writer.write(tabla.get(codigo)+" ");
                    writer.flush();
                    codigo = "";
                    bool = true;
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

}
