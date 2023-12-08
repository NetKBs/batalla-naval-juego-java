package logic;

enum Direccion{
    HORIZONTAL,
    VERTICAL
}

public abstract class Barco {
    private Direccion direccion;
    private int casillas;
    public int disparos;
    private int piezasIntactas;
    
    public int id;

    public Barco(Direccion direccion, int casillas, int disparos) {
        this.id = 0;
        this.direccion = direccion;
        this.casillas = casillas;
        this.piezasIntactas = casillas;
        this.disparos = disparos;
     
    }
    
   
    public int getPiezasIntactas() {
        return piezasIntactas;
    }

    public Direccion getDireccion() {
        return direccion;
    }

    public int getCasillas() {
        return casillas;
    }

    public int getDisparos() {
        return disparos;
    }
  
    
    public void setPiezasIntactas(int piezasIntactas) {
        this.piezasIntactas = piezasIntactas;
    }

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void setCasillas(int casillas) {
        this.casillas = casillas;
    }

    public void setDisparos(int disparos) {
        this.disparos = disparos;
    }
    
}
