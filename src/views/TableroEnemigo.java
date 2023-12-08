package views;

import javafx.animation.AnimationTimer;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import logic.Casilla;
import logic.Fase;
import logic.Juego;
import logic.Tablero;

public class TableroEnemigo {
    GridPane gridEnemigo;

    private int pointerX = -1;
    private int pointerY = -1;

    private Juego juego = Juego.getInstance();

    TableroEnemigo(GridPane gridEnemigo) {
        this.gridEnemigo = gridEnemigo;

        Tablero tableroComputadora = new Tablero(10, 10);

        double gridWidth = gridEnemigo.getPrefWidth();
        double gridHeight = gridEnemigo.getPrefHeight();

        int boardColumns = tableroComputadora.getAncho();
        int boardRows = tableroComputadora.getAlto();

        gridEnemigo.setAlignment(Pos.CENTER);

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
                    }
                });

                gridEnemigo.add(rectangle, x, y);
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

    }

    private void initAnimationTimer() {
        AnimationTimer animationTimer = new AnimationTimer() {
            public void handle(long now) {

                Tablero tablero = juego.getTablero();
                Fase fase = juego.getFase();

                for (int x = 0; x < tablero.getAncho(); x++) {
                    for (int y = 0; y < tablero.getAlto(); y++) {
                        Casilla casilla = tablero.getCasilla(x, y);
                        Rectangle rectangulo = (Rectangle) gridEnemigo.getChildren().get(x * tablero.getAlto() + y);

                        if (casilla.getFueAtacada()) {
                            rectangulo.setFill(javafx.scene.paint.Color.RED);
                        } else {
                            rectangulo.setFill(javafx.scene.paint.Color.WHITE);
                        }

                    }
                }

                if (pointerX != -1 && pointerY != -1 && fase == Fase.ATAQUE) {
                    this.dibujarAtaqueBarcos();
                }

            }

            public void dibujarAtaqueBarcos() {

                ObservableList<Node> gridChildren = gridEnemigo.getChildren();
                Color color = Color.LIGHTYELLOW;

                Rectangle rectangulo = (Rectangle) gridChildren
                        .get(pointerX * juego.getTablero().getAlto() + pointerY);
                rectangulo.setFill(color);
            }
        };

        animationTimer.start();
    }
}
