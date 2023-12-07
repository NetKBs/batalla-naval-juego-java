package logic;

enum Direccion{
    HORIZONTAL,
    VERTICAL
}

public abstract class Barco {
    private Direccion direccion;
    private Number casillas;
    private Number disparos;

    public Barco(Direccion direccion, Number casillas, Number disparos) {
        this.direccion = direccion;
        this.casillas = casillas;
        this.disparos = disparos;
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
