package paquete;

public class Register implements Comparable{

    private String simbolo;
    private String codigo;
    int frec=0;
    double prob = 0.0;

    public Register(String simbolo, int frec) {
        this.simbolo = simbolo;
        this.frec = frec;
        this.codigo = "";
    }

    public String getSimbolo() {
        return simbolo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public void setMensaje(String mensaje) {
        this.simbolo = mensaje;
    }

    public int getFrec() {
        return frec;
    }

    public void setFrec(int frec) {
        this.frec = frec;
    }

    public double getProb() {
        return prob;
    }

    public void setProb(double prob) {
        this.prob = prob;
    }

    @Override
    public String toString() {
        return simbolo +" --> " + frec;
    }

    @Override
    public int compareTo(Object o) {
        Register r = (Register) o;
        if (this.getFrec() > r.getFrec())
            return 1;
        else
        if(this.getFrec() < r.getFrec())
            return -1;
        else
            return 0;
    }
 }