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
        stage.show();
         
        
    }

    public void onJugarButtonClick() throws IOException {
        // Cargar el nuevo FXML (tablero.fxml)
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("tablero.fxml"));
        AnchorPane tableroPane = fxmlLoader.load();

        // Obtener el controlador del tablero
        TableroControllerView tableroController = fxmlLoader.getController();

        // Crear una instancia de TableroModel
        Tablero tableroModel = new Tablero(10, 10);

        // Pasar el modelo al controlador del tablero
        //tableroController.setTableroModel(tableroModel);

    }
    
    public void path() {
         String  path = getClass().getResource("/views/inicio.fxml").getPath();
         System.out.println(path);

    }

    public static void main(String[] args) {
        /*BattallaNavalProyecto f = new BattallaNavalProyecto();
        f.path();
        System.out.println("hola");*/
        launch();
    }

}
