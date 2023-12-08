package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

public class Juego {

    private static Juego instance = null;

    private Fase fase = Fase.DESPLIGUE;
    private Tablero tablero;

    private Map<String, Integer> cantidadesBarcos = new HashMap<>();
    private Queue<Barco> barcosDisponibles = new LinkedList<Barco>();

    private Turno turno;

    private Juego() {
        int boardColumns = 10;
        int boardRows = 10;

        this.tablero = new Tablero(boardColumns, boardRows);

        cantidadesBarcos.put("Acorazado", 1);
        cantidadesBarcos.put("Destructor", 2);
        cantidadesBarcos.put("Fragata", 3);
        cantidadesBarcos.put("Submarino", 5);
        cantidadesBarcos.put("Portaaviones", 1);

        for (Map.Entry<String, Integer> entry : cantidadesBarcos.entrySet()) {
            String shipType = entry.getKey();
            int count = entry.getValue();

            for (int j = 0; j < count; j++) {
                switch (shipType) {
                    case "Portaaviones":
                        barcosDisponibles.add(new Portaaviones(Direccion.HORIZONTAL));
                        break;
                    case "Acorazado":
                        barcosDisponibles.add(new Acorazado(Direccion.HORIZONTAL));
                        break;
                    case "Destructor":
                        barcosDisponibles.add(new Destructor(Direccion.HORIZONTAL));
                        break;
                    case "Fragata":
                        barcosDisponibles.add(new Fragata(Direccion.HORIZONTAL));
                        break;
                    case "Submarino":
                        barcosDisponibles.add(new Submarino(Direccion.HORIZONTAL));
                        break;
                }
            }
        }
    }

    public void colocarBarco(int x, int y, Direccion direccion) {
        if (fase != Fase.DESPLIGUE) {
            return;
        }

        ArrayList<Coord> rectangulosBarco = getPosicionesBarcoActual(x, y, direccion);

        Barco currentBarco = barcosDisponibles.peek();

        if (rectangulosBarco.size() < currentBarco.getCasillas() || hayColision(rectangulosBarco)) {
            return;
        }

        for (Coord coord : rectangulosBarco) {
            Casilla casilla = this.tablero.getCasilla(coord.x, coord.y);
            casilla.setTieneBarco(true);
        }

        barcosDisponibles.poll();

        if (barcosDisponibles.size() == 0) {
            fase = Fase.ESPERA;
        }
    }

    public boolean hayColision(ArrayList<Coord> posiciones) {
        for (Coord coord : posiciones) {
            Casilla casilla = tablero.getCasilla(coord.x, coord.y);
            if (casilla.getTieneBarco()) {
                return true;
            }
        }

        return false;
    }

    public ArrayList<Coord> getPosicionesBarcoActual(int posX, int posY, Direccion pointerDireccion) {
        ArrayList<Coord> rectangulosBarco = new ArrayList<Coord>();

        Barco currentBarco = barcosDisponibles.peek();

        if (pointerDireccion == Direccion.HORIZONTAL) {
            for (int c = 0; c < currentBarco.getCasillas(); c++) {
                int x = posX + c;

                if (x < 0 || x >= tablero.getAncho()) {
                    continue;
                }

                rectangulosBarco.add(new Coord(x, posY));
            }
        } else if (pointerDireccion == Direccion.VERTICAL) {
            for (int c = 0; c < currentBarco.getCasillas(); c++) {
                int y = posY + c;
                if (y < 0 || y >= tablero.getAlto()) {
                    continue;
                }
                rectangulosBarco.add(new Coord(posX, y));
            }
        }

        return rectangulosBarco;
    }

    public static Juego getInstance() {
        if (instance == null) {
            instance = new Juego();
        }

        return instance;
    }

    public Map<String, Integer> getCantidadesBarcos() {
        return cantidadesBarcos;
    }

    public Tablero getTablero() {
        return tablero;
    }

    public Fase getFase() {
        return fase;
    }

    public Turno getTurno() {
        return turno;
    }

    public void cambiarTurno() {
        if (turno == Turno.COMPUTADORA) {
            turno = Turno.JUGADOR;
        } else {
            turno = Turno.COMPUTADORA;
        }
    }

    public void setFase(Fase nuevaFase) {
        if (fase == Fase.ESPERA && nuevaFase == Fase.ATAQUE) {
            // TURNO ALEATORIO
            turno = Turno.values()[(int) (Math.random() * Turno.values().length)];
        }

        fase = nuevaFase;
    }

    public Queue<Barco> getBarcosDisponibles() {
        return barcosDisponibles;
    }
}
