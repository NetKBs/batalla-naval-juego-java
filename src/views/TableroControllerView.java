
package views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class TableroControllerView implements Initializable {

    @FXML
    private GridPane gridJugador;
    @FXML
    private Label BarcosActivos;

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
      
            Stage stage = (Stage) BarcosActivos.getScene().getWindow();
            stage.close();
        
       
        
    }

}
