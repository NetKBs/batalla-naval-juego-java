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

/**
 *
 * @author net
 */
public class TableroControllerView implements Initializable {
    @FXML
    private GridPane gridPane;
    @FXML
    private Label NombreJugador;

    //private TableroBatalla tableroModel; // Agregamos una referencia al modelo

   /* public void setTableroModel(TableroBatalla tableroModel) {
        this.tableroModel = tableroModel;
    }*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (Node node : gridPane.getChildren()) {
            if (node instanceof Rectangle) {
                setDraggable((Rectangle) node);
            }
        }
    }
    private void setDraggable(Rectangle rectangle) {
        double[] dragContext = new double[2];

        rectangle.setOnDragDetected(event -> {
            Dragboard db = rectangle.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.put(DataFormat.PLAIN_TEXT, "");
            db.setContent(content);

            // Guarda la posición del ratón en relación con el Rectangle.
            dragContext[0] = event.getX() - rectangle.getBoundsInParent().getMinX();
            dragContext[1] = event.getY() - rectangle.getBoundsInParent().getMinY();

            event.consume();
        });

        rectangle.setOnDragOver(event -> {
            if (event.getGestureSource() != rectangle && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        rectangle.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (db.hasString()) {
                // Calcula las nuevas posiciones de columna y fila.
                int newColumnIndex = (int) ((event.getSceneX() - dragContext[0]) / gridPane.getWidth() * gridPane.getColumnCount());
                int newRowIndex = (int) ((event.getSceneY() - dragContext[1]) / gridPane.getHeight() * gridPane.getRowCount());

                // Actualiza las propiedades GridPane.columnIndex y GridPane.rowIndex.
                GridPane.setColumnIndex(rectangle, newColumnIndex);
                GridPane.setRowIndex(rectangle, newRowIndex);

                success = true;
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }

    public void onAbandonarButtonClick(ActionEvent actionEvent) {
    }
}
