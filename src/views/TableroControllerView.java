
package views;

import javafx.scene.paint.Paint;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;


public class TableroControllerView implements Initializable {

    
   

    @FXML
    private Label NombreJugador;
    @FXML
    private GridPane ZonaJugador;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        for (int i = 0; i < 10; i++) { 
      for (int j = 0; j < 10; j++) {

          Rectangle rect = new Rectangle(40,40);
          rect.setFill(Paint.valueOf("#000"));
      


          ZonaJugador.add(rect, i, j);
      }
  }  
        
    }

    @FXML
    public void onAbandonarButtonClick() {
        Stage stage = (Stage) NombreJugador.getScene().getWindow();
        stage.close();
    }

}
