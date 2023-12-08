
package views;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import logic.*;

enum Fase {
    DESPLIGUE,
    ATAQUE,
}

public class TableroControllerView implements Initializable {

    @FXML
    private GridPane gridJugador;
    @FXML
    private Label labelPortaavion;
    @FXML
    private Label labelAcorazado;
    @FXML
    private Label labelDestructor;
    @FXML
    private Label labelFragata;
    @FXML
    private Label labelSubmarino;

    private Fase fase = Fase.DESPLIGUE;
    private int pointerX = -1;
    private int pointerY = -1;
    private Direccion pointerDireccion = Direccion.HORIZONTAL;
    private Tablero tablero;
    private Queue<Barco> barcosDisponibles = new LinkedList<Barco>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double gridWidth = gridJugador.getPrefWidth();
        double gridHeight = gridJugador.getPrefHeight();

        int boardColumns = 10;
        int boardRows = 10;

        this.tablero = new Tablero(boardColumns, boardRows);

        Map<String, Integer> shipCounts = new HashMap<>();
        shipCounts.put("Acorazado", 1);
        shipCounts.put("Destructor", 2);
        shipCounts.put("Fragata", 3);
        shipCounts.put("Submarino", 5);
        shipCounts.put("Portaaviones", 1);

        labelPortaavion.setText(shipCounts.get("Portaaviones").toString());
        labelAcorazado.setText(shipCounts.get("Acorazado").toString());
        labelDestructor.setText(shipCounts.get("Destructor").toString());
        labelFragata.setText(shipCounts.get("Fragata").toString());
        labelSubmarino.setText(shipCounts.get("Submarino").toString());

        for (Map.Entry<String, Integer> entry : shipCounts.entrySet()) {
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

        gridJugador.setAlignment(Pos.CENTER);

        for (int x = 0; x < boardColumns; x++) {
            for (int y = 0; y < boardRows; y++) {
                final int finalX = x;
                final int finalY = y;

                Rectangle rectangle = new Rectangle();
                rectangle.setWidth(gridWidth / boardColumns);
                rectangle.setHeight(gridHeight / boardRows);

                rectangle.setFill(javafx.scene.paint.Color.WHITE);
                rectangle.setStroke(javafx.scene.paint.Color.BLACK);

                rectangle.setOnMouseEntered(e -> this.handleMouseEntered(rectangle, finalX, finalY));
                rectangle.setOnMouseExited(e -> this.handleMouseExited(rectangle, finalX, finalY));
                rectangle.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        this.handleMouseClickPrimary();
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        this.handleMouseClickSecondary();
                    }
                });

                gridJugador.add(rectangle, x, y);
            }
        }

        this.initAnimationTimer();
    }

    private void handleMouseEntered(Rectangle rectangulo, int x, int y) {
        this.pointerX = x;
        this.pointerY = y;
    }

    private void handleMouseExited(Rectangle rectangulo, int x, int y) {
        this.pointerX = -1;
        this.pointerY = -1;
    }

    private void handleMouseClickPrimary() {
        if (fase != Fase.DESPLIGUE) {
            return;
        }

        ArrayList<Coord> rectangulosBarco = getPosicionesBarcoActual();

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
            fase = Fase.ATAQUE;
        }
    }

    private void handleMouseClickSecondary() {
        if (pointerDireccion == Direccion.HORIZONTAL) {
            pointerDireccion = Direccion.VERTICAL;
        } else if (pointerDireccion == Direccion.VERTICAL) {
            pointerDireccion = Direccion.HORIZONTAL;
        }
    }

    private void initAnimationTimer() {
        AnimationTimer animationTimer = new AnimationTimer() {
            public void handle(long now) {

                for (int x = 0; x < tablero.getAncho(); x++) {
                    for (int y = 0; y < tablero.getAlto(); y++) {
                        Casilla casilla = tablero.getCasilla(x, y);
                        Rectangle rectangulo = (Rectangle) gridJugador.getChildren().get(x * tablero.getAlto() + y);

                        if (casilla.getTieneBarco()) {
                            rectangulo.setFill(javafx.scene.paint.Color.BLUE);
                        } else {
                            rectangulo.setFill(javafx.scene.paint.Color.WHITE);
                        }

                    }
                }

                if (pointerX != -1 && pointerY != -1 && fase == Fase.DESPLIGUE) {
                    this.dibujarColocacionBarcos();
                }

            }

            public void dibujarColocacionBarcos() {
                ObservableList<Node> gridChildren = gridJugador.getChildren();
                Barco currentBarco = barcosDisponibles.peek();
                Color color = Color.LIGHTBLUE;
                ArrayList<Coord> rectangulosBarco = getPosicionesBarcoActual();

                if (rectangulosBarco.size() < currentBarco.getCasillas()) {
                    color = Color.LIGHTCORAL;
                }

                if (hayColision(rectangulosBarco)) {
                    color = Color.LIGHTCORAL;
                }

                for (int i = 0; i < rectangulosBarco.size(); i++) {
                    Coord coord = rectangulosBarco.get(i);
                    Rectangle rectangulo = (Rectangle) gridChildren.get(coord.x * tablero.getAlto() + coord.y);
                    rectangulo.setFill(color);
                }
            }
        };

        animationTimer.start();
    }

    private ArrayList<Coord> getPosicionesBarcoActual() {
        ArrayList<Coord> rectangulosBarco = new ArrayList<Coord>();

        Barco currentBarco = barcosDisponibles.peek();

        if (pointerDireccion == Direccion.HORIZONTAL) {
            for (int c = 0; c < currentBarco.getCasillas(); c++) {
                int x = pointerX + c;

                if (x < 0 || x >= tablero.getAncho()) {
                    continue;
                }

                rectangulosBarco.add(new Coord(x, pointerY));
            }
        } else if (pointerDireccion == Direccion.VERTICAL) {
            for (int c = 0; c < currentBarco.getCasillas(); c++) {
                int y = pointerY + c;
                if (y < 0 || y >= tablero.getAlto()) {
                    continue;
                }
                rectangulosBarco.add(new Coord(pointerX, y));
            }
        }

        return rectangulosBarco;
    }

    private boolean hayColision(ArrayList<Coord> posiciones) {
        for (Coord coord : posiciones) {
            Casilla casilla = tablero.getCasilla(coord.x, coord.y);
            if (casilla.getTieneBarco()) {
                return true;
            }
        }

        return false;
    }

    @FXML
    public void onAbandonarButtonClick() {

        Stage stage = (Stage) gridJugador.getScene().getWindow();
        stage.close();

    }

}
