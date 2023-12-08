package logic;

public abstract class Barco {
    private Direccion direccion;
    private int casillas;
    private int disparos;

    public Barco(Direccion direccion, int casillas, int disparos) {
        this.direccion = direccion;
        this.casillas = casillas;
        this.disparos = disparos;
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

    public void setDireccion(Direccion direccion) {
        this.direccion = direccion;
    }

    public void setDisparos(int disparos) {
        this.disparos = disparos;
    }

}
