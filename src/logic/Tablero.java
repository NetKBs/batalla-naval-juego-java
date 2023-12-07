package logic;

public class Tablero {

    private Number ancho;
    private Number alto;
    private Casilla[][] casillas;

    public Tablero(int ancho, int alto) {
        this.ancho = ancho;
        this.alto = alto;

        for (int x = 0; x < ancho; x++) {
            for (int y = 0; y < ancho; y++) {
                this.casillas[x][y] = new Casilla();
            }
        }
    }

    public Number getAncho() {
        return ancho;
    }

    public Number getAlto() {
        return alto;
    }

    public Casilla getCasilla(int x, int y) {
        return casillas[x][y];
    }
}
