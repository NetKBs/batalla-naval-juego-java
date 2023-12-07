package logic;

enum Direccion{
    HORIZONTAL,
    VERTICAL
}

public abstract class Barco {
    private Direccion direccion;
    private Number casillas;
    private Number disparos;
    private int[] extension = new int[2]; // a-b
    private Number piezasIntactas;

    public Barco(Direccion direccion, Number casillas, Number disparos, int puntoA, int puntoB) {
        this.direccion = direccion;
        this.casillas = casillas;
        this.piezasIntactas = casillas;
        this.disparos = disparos;
        this.extension[0] = puntoA;
        this.extension[1] = puntoB;
    }
    
    public int[] getExtension() {
        return extension;
    }
    
    public Number getPiezasIntactas() {
        return piezasIntactas;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public Number getCasillas() {
        return casillas;
    }

    public Number getDisparos() {
        return disparos;
    }
    
    public void setExtension(int[]  extension) {
        this.extension = extension;
    }
    
    public void setPiezasIntactas(Number piezasIntactas) {
        this.piezasIntactas = piezasIntactas;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void setCasillas(Number casillas) {
        this.casillas = casillas;
    }

    public void setDisparos(Number disparos) {
        this.disparos = disparos;
    }
    
}
