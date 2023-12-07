/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package views;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author net
 */
public class TableroControllerView implements Initializable {
    
    @FXML
    private Label NombreJugador;
    //private TableroBatalla tableroModel; // Agregamos una referencia al modelo

   /* public void setTableroModel(TableroBatalla tableroModel) {
        this.tableroModel = tableroModel;
    }*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        
        
    }
    
    @FXML
    public void onAbandonarButtonClick() {
         Stage stage = (Stage) NombreJugador.getScene().getWindow();
        stage.close();
    }
   
}
