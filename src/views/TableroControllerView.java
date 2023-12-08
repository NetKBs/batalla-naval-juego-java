
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

    private int pointerX = -1;
    private int pointerY = -1;
    private Direccion pointerDireccion = Direccion.HORIZONTAL;

    private Juego juego = Juego.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Tablero tablero = juego.getTablero();

        double gridWidth = gridJugador.getPrefWidth();
        double gridHeight = gridJugador.getPrefHeight();

        int boardColumns = tablero.getAncho();
        int boardRows = tablero.getAlto();

        Map<String, Integer> cantidadesBarcos = juego.getCantidadesBarcos();

        labelPortaavion.setText(cantidadesBarcos.get("Portaaviones").toString());
        labelAcorazado.setText(cantidadesBarcos.get("Acorazado").toString());
        labelDestructor.setText(cantidadesBarcos.get("Destructor").toString());
        labelFragata.setText(cantidadesBarcos.get("Fragata").toString());
        labelSubmarino.setText(cantidadesBarcos.get("Submarino").toString());

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
        juego.colocarBarco(pointerX, pointerY, pointerDireccion);
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

                Tablero tablero = juego.getTablero();
                Fase fase = juego.getFase();

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
                Barco currentBarco = juego.getBarcosDisponibles().peek();
                Color color = Color.LIGHTBLUE;
                ArrayList<Coord> rectangulosBarco = juego.getPosicionesBarcoActual(pointerX, pointerY,
                        pointerDireccion);

                if (rectangulosBarco.size() < currentBarco.getCasillas()) {
                    color = Color.LIGHTCORAL;
                }

                if (juego.hayColision(rectangulosBarco)) {
                    color = Color.LIGHTCORAL;
                }

                for (int i = 0; i < rectangulosBarco.size(); i++) {
                    Coord coord = rectangulosBarco.get(i);
                    Rectangle rectangulo = (Rectangle) gridChildren
                            .get(coord.x * juego.getTablero().getAlto() + coord.y);
                    rectangulo.setFill(color);
                }
            }
        };

        animationTimer.start();
    }

    @FXML
    public void onAbandonarButtonClick() {

        Stage stage = (Stage) gridJugador.getScene().getWindow();
        stage.close();

    }

}
