package views;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class InicioControllerView implements Initializable {

    @FXML
    private Label welcomeText;
    @FXML
    private Button BotonJugar;
    @FXML
    private TextField Usuario;

    @FXML

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    @FXML
    protected void onExitButtonClick() {
        Stage stage = (Stage) welcomeText.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void onJugarButtonClick() throws IOException {
        try {
            // Cargar el nuevo FXML (tablero.fxml)

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/tablero.fxml"));
    
            AnchorPane tableroPane = loader.load();

            // Crear una nueva escena
            Scene tableroScene = new Scene(tableroPane);
            // Obtener el escenario actual (ventana)
            Stage stage2 = (Stage) BotonJugar.getScene().getWindow();
            // Configurar la nueva escena en el escenario actual
            stage2.setScene(tableroScene);
            stage2.setTitle("Batalla Naval - Tablero");
            
        } finally {
        }
    }
}
