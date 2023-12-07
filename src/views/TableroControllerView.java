package views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.shape.*;

public class TableroControllerView implements Initializable {

    @FXML
    private GridPane gridJugador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        double gridAncho = gridJugador.getPrefWidth();
        double gridAlto = gridJugador.getPrefHeight();

        int columnasTablero = 10;
        int filasTablero = 10;

        for (int x = 0; x < columnasTablero; x++) {
            for (int y = 0; y < filasTablero; y++) {
                Rectangle rectangulo = new Rectangle();
                rectangulo.setWidth(gridAncho / columnasTablero - 2);
                rectangulo.setHeight(gridAlto / filasTablero - 2);

                rectangulo.applyCss();

                gridJugador.add(rectangulo, x, y);
                gridJugador.setAlignment(Pos.CENTER);

            }
        }
    }

    @FXML
    public void onAbandonarButtonClick() {

    }

}
