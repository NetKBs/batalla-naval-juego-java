
package views;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javafx.scene.control.Label;
import logic.*;

public class TableroControllerView implements Initializable {

    @FXML
    private GridPane gridJugador;
    @FXML
    private GridPane gridEnemigo;
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
    @FXML 
    private Label barcosJugadorRestantes;

    @FXML     
    private Label barcosEnemigoRestantes;

    private int barcosRestantesJugador;  
    private int barcosRestantesComputadora;
    
    private Juego juego = Juego.getInstance();
     
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        Map<String, Integer> cantidadesBarcos = juego.getCantidadesBarcos();

        labelPortaavion.setText(cantidadesBarcos.get("Portaaviones").toString());
        labelAcorazado.setText(cantidadesBarcos.get("Acorazado").toString());
        labelDestructor.setText(cantidadesBarcos.get("Destructor").toString());
        labelFragata.setText(cantidadesBarcos.get("Fragata").toString());
        labelSubmarino.setText(cantidadesBarcos.get("Submarino").toString());

        new TableroJugador(gridJugador);
        new TableroEnemigo(gridEnemigo);

        this.initGameTimer();
    }

      
    public void initGameTimer() {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
              
                int barcosJugador = 0;
                for(Barco barco: juego.getBarcosJugador()){
                    if (barco.getPiezasIntactas() > 0 ) {
                        barcosJugador ++;
                    }
                }
                
                int barcosComputador = 0;
                for(Barco barco: juego.getarcosComputadora()){
                    if (barco.getPiezasIntactas() > 0 ) {
                        barcosComputador ++;
                    }
                }
           

                
            }
        };

        timer.start();
    }
    
    
    private int barcosDestruidosJugador = 0;  
    private int barcosDestruidosComputadora = 0;

    private int barcosAnterioresJugador;  
    private int barcosAnterioresComputadora;

    private void actualizarLabels() {

        if(barcosRestantesJugador < barcosAnterioresJugador) {
            barcosDestruidosJugador++;     
        }

        if(barcosRestantesComputadora < barcosAnterioresComputadora) {
             barcosDestruidosComputadora++;  
        }   
        

        barcosJugadorRestantes.setText(barcosDestruidosJugador + "");
        barcosEnemigoRestantes.setText(barcosDestruidosComputadora + "");

        barcosAnterioresJugador = barcosRestantesJugador;  
        barcosAnterioresComputadora = barcosRestantesComputadora;
    }

    @FXML
    public void onAtacarButtonClick() {
        Fase fase = juego.getFase();
        if (fase == Fase.ESPERA) {
            juego.setFase(Fase.ATAQUE);
        }
    }

    @FXML
    public void onAbandonarButtonClick() {

        Stage stage = (Stage) gridJugador.getScene().getWindow();
        stage.close();

    }

}
