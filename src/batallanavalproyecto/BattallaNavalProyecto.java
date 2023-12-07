package batallanavalproyecto;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import logic.Tablero;
import views.InicioControllerView;
import views.TableroControllerView;

public class BattallaNavalProyecto extends Application {

  

    @Override
    public void start(Stage stage) throws IOException {
      
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/inicio.fxml"));
        Scene scene = new Scene(loader.load());  
        stage.setScene(scene);
        stage.setTitle("Batalla Naval - Inicio");
        stage.show();
         
        
    }


    public static void main(String[] args) {

        launch();
    }

}
