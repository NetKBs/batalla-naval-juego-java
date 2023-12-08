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

    public Barco[][] getTablero() {
        return tablero;
    }
    
    public void propagarTablero() {

        while (portaaviones == 0) {
            Portaaviones barco = new Portaaviones(obtenerDireccionAleatoria());
            Coordenada coords_iniciales = generarCoordenadas();;
            boolean coordsFinalesValidacion = verificarPosicionBarcoFinal(barco, coords_iniciales);

            if (coordsFinalesValidacion) {
                portaaviones++;
                barco.id = portaaviones;
                agregarBarco(barco, coords_iniciales.fila, coords_iniciales.columna);
            }
        }

        while (acorazado == 0) {
            Acorazado barco = new Acorazado(obtenerDireccionAleatoria());
            Coordenada coords_iniciales = generarCoordenadas();;
            boolean coordsFinalesValidacion = verificarPosicionBarcoFinal(barco, coords_iniciales);

            if (coordsFinalesValidacion) {
                acorazado++;
                barco.id = acorazado;
                agregarBarco(barco, coords_iniciales.fila, coords_iniciales.columna);
            }
        }

        while (destructores < 2) {
            Destructor barco = new Destructor(obtenerDireccionAleatoria());
            Coordenada coords_iniciales = generarCoordenadas();;
            boolean coordsFinalesValidacion = verificarPosicionBarcoFinal(barco, coords_iniciales);

            if (coordsFinalesValidacion) {
                destructores++;
                barco.id = destructores;
                agregarBarco(barco, coords_iniciales.fila, coords_iniciales.columna);
            }
        }

        while (fragatas < 3) {
            Fragata barco = new Fragata(obtenerDireccionAleatoria());
            Coordenada coords_iniciales = generarCoordenadas();;
            boolean coordsFinalesValidacion = verificarPosicionBarcoFinal(barco, coords_iniciales);

            if (coordsFinalesValidacion) {
                fragatas++;
                barco.id = fragatas;
                agregarBarco(barco, coords_iniciales.fila, coords_iniciales.columna);
            }
        }

        while (submarinos < 5) {
            Submarino barco = new Submarino(obtenerDireccionAleatoria());
            Coordenada coords_iniciales = generarCoordenadas();
            boolean coordsFinalesValidacion = verificarPosicionBarcoFinal(barco, coords_iniciales);

            if (coordsFinalesValidacion) {
                submarinos++;
                barco.id = submarinos;
                agregarBarco(barco, coords_iniciales.fila, coords_iniciales.columna);
            }
        }

    }

    public boolean verificarPosicionBarcoFinal(Barco barco, Coordenada coords_propuestas) {

        int fila = coords_propuestas.fila;
        int columna = coords_propuestas.columna;

        if (barco.getDireccion() == Direccion.HORIZONTAL) {
            int columna_final = columna + barco.getCasillas();

            for (int columna_index = columna; columna_index < columna_final; columna_index++) {
                if (!posicionValidaParaBarcos(fila, columna_index)) {
                    return false;
                }
            }

        } else { // Vertical
            int fila_final = fila + barco.getCasillas();

            for (int fila_index = fila; fila_index < fila_final; fila_index++) {
                if (!posicionValidaParaBarcos(fila_index, columna)) {
                    return false;
                }
            }
        }

        return true;

    }

    public boolean posicionValidaParaBarcos(int fila, int columna) {

        if (fila >= 10 || columna >= 10) { // fuera de limites
            return false;

        } else {
            return tablero[fila][columna] == null; // nulo o no
        }
    }

    public void agregarBarco(Barco barco, int fila, int columna) {

        if (barco.getDireccion() == Direccion.HORIZONTAL) {
            int columna_final = columna + barco.getCasillas();

            for (int columna_index = columna; columna_index < columna_final; columna_index++) {
                tablero[fila][columna_index] = barco;
            }

        } else { // Vertical
            int fila_final = fila + barco.getCasillas();

            for (int fila_index = fila; fila_index < fila_final; fila_index++) {
                tablero[fila_index][columna] = barco;
            }
        }
    }

    Direccion obtenerDireccionAleatoria() {
        Direccion[] direcciones = Direccion.values();
        Random random = new Random();
        int index = random.nextInt(direcciones.length);
        return direcciones[index];
    }

    public Coordenada atacarAleatoriamente() {

        Coordenada coord = generarCoordenadas();

        while (verificarCoordenadaDeAtaque(coord.fila, coord.columna)) {
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

    private boolean verificarCoordenadaDeAtaque(int fila, int columna) {
        for (Coordenada coordenada : ataquesCoordenadas) {
            if (coordenada.fila == fila && coordenada.columna == columna) {
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Computadora ia = new Computadora();
       
        // Pruebas de ataque
        Coordenada Ataquecoord;  
        
        for (int i = 0; i < 10; i++) {
            Ataquecoord = ia.atacarAleatoriamente();
            System.out.println("fila: " + Ataquecoord.fila + " columna:" + Ataquecoord.columna);
        }
        System.out.println("\n");

        // Pruebas de propagacion
        ia.propagarTablero();
        Barco[][] tablero = ia.getTablero();

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero[i][j] != null) {

                    if (tablero[i][j] instanceof Portaaviones) {
                        System.out.print("P" + " "); // P for Portaaviones

                    } else if (tablero[i][j] instanceof Acorazado) {
                        System.out.print("A" + " ");

                    } else if (tablero[i][j] instanceof Destructor) {
                        System.out.print("D" + " ");

                    } else if (tablero[i][j] instanceof Fragata) {
                        System.out.print("F" + " ");
                    } else if (tablero[i][j] instanceof Submarino) {
                        System.out.print("S" + " ");
                    }

                } else {
                    System.out.print("*" + " ");
                }

            }
            System.out.println();
        }
        
        // Verificacion de instancias compartidas
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (tablero[i][j] != null) {

                    if (tablero[i][j] instanceof Portaaviones) {
                        int disparos = tablero[i][j].disparos - 1;
                        tablero[i][j].disparos = disparos;
                        System.out.println("Tipo: Portaaviones Id: " + tablero[i][j].id + " Coords: " + i + "," + j);
                        System.out.println("disparos: " + disparos);
                        System.out.println("\n");

                    } else if (tablero[i][j] instanceof Destructor) {
                        int disparos = tablero[i][j].disparos - 1;
                        tablero[i][j].disparos = disparos;
                        System.out.println("Tipo: Destructor Id: " + tablero[i][j].id  + " Coords: " + i + "," + j);
                        System.out.println("disparos: " + disparos);
                        System.out.println("\n");

                    }

                }

            }

        }

    }
}
