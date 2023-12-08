
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
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import logic.*;

enum Fase {
    DESPLIGUE,
    DISPARO,
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
        double gridAncho = gridJugador.getPrefWidth();
        double gridAlto = gridJugador.getPrefHeight();

        int columnasTablero = 10;
        int filasTablero = 10;

        this.tablero = new Tablero(columnasTablero, filasTablero);

        Map<String, Number> cantidadesBarcos = new HashMap<String, Number>();

        cantidadesBarcos.put("Portaaviones", 1);
        // cantidadesBarcos.put("Acorazado", 1);
        // cantidadesBarcos.put("Destructor", 2);
        // cantidadesBarcos.put("Fragata", 3);
        // cantidadesBarcos.put("Submarino", 5);

        labelPortaavion.setText(cantidadesBarcos.get("Portaaviones").toString());
        // labelAcorazado.setText(cantidadesBarcos.get("Acorazado").toString());
        // labelDestructor.setText(cantidadesBarcos.get("Destructor").toString());
        // labelFragata.setText(cantidadesBarcos.get("Fragata").toString());
        // labelSubmarino.setText(cantidadesBarcos.get("Submarino").toString());

        for (Map.Entry<String, Number> entry : cantidadesBarcos.entrySet()) {
            String barco = entry.getKey();
            Integer cantidad = entry.getValue().intValue();

            for (int j = 0; j < cantidad; j++) {
                switch (barco) {
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

        for (int x = 0; x < columnasTablero; x++) {
            for (int y = 0; y < filasTablero; y++) {
                final int finalX = x;
                final int finalY = y;

                Rectangle rectangulo = new Rectangle();
                rectangulo.setWidth(gridAncho / columnasTablero);
                rectangulo.setHeight(gridAlto / filasTablero);

                rectangulo.setFill(javafx.scene.paint.Color.WHITE);
                rectangulo.setStroke(javafx.scene.paint.Color.BLACK);

                rectangulo.setOnMouseEntered(e -> this.handleMouseEntered(rectangulo, finalX, finalY));
                rectangulo.setOnMouseExited(e -> this.handleMouseExited(rectangulo, finalX, finalY));
                rectangulo.setOnMouseClicked(e -> {
                    if (e.getButton() == MouseButton.PRIMARY) {
                        this.handleMouseClickPrimary(rectangulo, finalX, finalY);
                    } else if (e.getButton() == MouseButton.SECONDARY) {
                        this.handleMouseClickSecondary(rectangulo, finalX, finalY);
                    }
                });

                gridJugador.add(rectangulo, x, y);
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

    private void handleMouseClickPrimary(Rectangle rectangulo, int x, int y) {

    }

    private void handleMouseClickSecondary(Rectangle rectangulo, int x, int y) {
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
                        Rectangle rectangulo = (Rectangle) gridJugador.getChildren().get(x * tablero.getAlto() + y);
                        rectangulo.setFill(javafx.scene.paint.Color.WHITE);
                    }
                }

                if (pointerX != -1 && pointerY != -1 && fase == Fase.DESPLIGUE) {
                    this.dibujarColocacionBarcos();
                }

            }

            public void dibujarColocacionBarcos() {
                Casilla casilla = tablero.getCasilla(pointerX, pointerY);

                if (casilla.getTieneBarco()) {
                    return;
                }

                ObservableList<Node> gridChildren = gridJugador.getChildren();
                Barco currentBarco = barcosDisponibles.peek();

                ArrayList<Coord> rectangulosBarco = new ArrayList<Coord>();

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

                Color color = Color.LIGHTBLUE;

                if (rectangulosBarco.size() < currentBarco.getCasillas()) {
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

    @FXML
    public void onAbandonarButtonClick() {

        Stage stage = (Stage) gridJugador.getScene().getWindow();
        stage.close();

    }

}
