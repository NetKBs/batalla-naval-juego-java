package logic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Computadora {

    private List<Coordenada> ataquesCoordenadas = new ArrayList<>();
    private Barco[][] tablero = new Barco[10][10];
    // Cantidad de cada tipo de barco posicionados
    private int portaaviones = 0; // max 1
    private int acorazado = 0; // max 1
    private int destructores = 0; // max 2
    private int fragatas = 0; // max 3
    private int submarinos = 0; // max 5

    public Computadora() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tablero[i][j] = null; // Inicializa posición como null
            }
        }
    }

    public void propagarTablero() {
        while (portaaviones != 0) {
            Portaaviones barco = new Portaaviones(obtenerDireccionAleatoria());
            Coordenada coords = obtenerPosicionBarcoValida(barco);
            
            if (coords != null) {
                portaaviones++;
                barco.id = portaaviones;
                
            }
        }
    }
    
    public Coordenada obtenerPosicionBarcoValida(Barco barco) {
        Coordenada coords_propuestas = generarCoordenadas();
        int fila = coords_propuestas.fila;
        int columna = coords_propuestas.columna;
        
        if (posicionValidaParaBarcos(fila + (barco.getCasillas()-1), columna)) { // extender adelante
            return new Coordenada(fila + (barco.getCasillas()-1), columna);
            
        } else if (posicionValidaParaBarcos(fila, columna + (barco.getCasillas()-1))){ // extender hacia atrás
            return new Coordenada(fila, columna + (barco.getCasillas()-1));
            
        } else {
            return null;
        }
        
        
    }

    public void agregarBarco(Barco barco, int fila, int columna) {
        tablero[fila][columna] = barco;
    }

    public boolean posicionValidaParaBarcos(int fila, int columna) {
        return tablero[fila][columna] == null;
    }

    Direccion obtenerDireccionAleatoria() {
        Direccion[] direcciones = Direccion.values();
        Random random = new Random();
        int index = random.nextInt(direcciones.length);
        return direcciones[index];
    }

    public Coordenada atacarAleatoriamente() {

        Coordenada coord = generarCoordenadas();

        while (verificarCoordenada(coord.fila, coord.columna, ataquesCoordenadas)) {
            coord.fila = (int) (Math.random() * 10);
            coord.columna = (int) (Math.random() * 10);
        }

        ataquesCoordenadas.add(coord);
        return coord;
    }

    private Coordenada generarCoordenadas() {
        int fila = (int) (Math.random() * 10);
        int columna = (int) (Math.random() * 10);

        return new Coordenada(fila, columna);
    }

    private boolean verificarCoordenada(int fila, int columna, List<Coordenada> listaCoordenadas) {
        for (Coordenada coordenada : listaCoordenadas) {
            if (coordenada.fila == fila && coordenada.columna == columna) {
                return true;
            }
        }
        return false;
    }

    /*
      public static void main(String[] args) {
          Computadora ia = new Computadora();
          Coordenada coords;
          coords = ia.atacarAleatoriamente();
          System.out.println("Columna: " + coords.columna + " Fila: " + coords.fila);
      }*/
}
