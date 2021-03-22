package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class Controller {
    @FXML private AnchorPane mainPanel;
    @FXML private AnchorPane newPanel;


    public void newWindowBtn(ActionEvent actionEvent) {
        mainPanel.setVisible(false);
        newPanel.setVisible(true);
    }
}
