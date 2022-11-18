package paquete;

import java.io.Serializable;

public class MiClase implements Serializable {

    //Esta variable es para identificar el archivo cuando lo vayamos a reconstruir del *.DAT
    private static final long serialVersionUID = 666L;

    //Para guardar el nombre de objeto de la clase
    private String Nombre_Objeto="";

    /* Constructor de la clase */
    public MiClase(String Nombre){
        this.Nombre_Objeto = Nombre;
    }

    public String getNombreObjeto()
    {
        return this.Nombre_Objeto;
    }
    //algunos metodos
    public int Suma(int a , int b)
    {
        return a + b;
    }

}