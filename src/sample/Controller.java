package sample;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.swing.*;
import java.util.Optional;


public class Controller {
    @FXML private ComboBox comboBox;
    @FXML private AnchorPane mainPanel;
    @FXML private AnchorPane newPanel;
    @FXML private TableView<Activity> tableView;

    private final ObservableList<Activity> data = FXCollections.observableArrayList();


        public void initialize() {

            tableView.setEditable(true);
            TableColumn timeCol = new TableColumn("Time");
            TableColumn activityCol = new TableColumn("Activity");
            TableColumn descriptionCol = new TableColumn("Description");

            timeCol.setMinWidth(100);
            timeCol.setCellValueFactory(
                    new PropertyValueFactory<Activity, String>("time"));

            activityCol.setMinWidth(100);
            activityCol.setCellValueFactory(
                    new PropertyValueFactory<Activity, String>("activity"));

            descriptionCol.setMinWidth(200);
            descriptionCol.setCellValueFactory(
                    new PropertyValueFactory<Activity, String>("description"));

            tableView.setItems(data);
            tableView.getColumns().addAll(timeCol, activityCol, descriptionCol);

            // ComboBox<String> comboBox = new ComboBox<>();

            comboBox.getItems().addAll(
                    "Monday",
                    "Tuesday",
                    "Wednesday",
                    "Thursday" ,
                    "Friday",
                    "Saturday",
                    "Sunday"
            );
            comboBox.setPromptText("What´s the day today?");

        }



        public void addNewActivity (ActionEvent actionEvent){
            Dialog<Activity> dialog= new Dialog<>();
            dialog.setTitle("Add new Activity");
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
            TextField timeTextField = new TextField("time");
            TextField activityTextField = new TextField("activity");
            TextField descriptionTextField = new TextField("description");

            dialogPane.setContent(new VBox(8, timeTextField, activityTextField, descriptionTextField));
            Platform.runLater(timeTextField::requestFocus);
            final Button btOk = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
            btOk.addEventFilter(
                    ActionEvent.ACTION,
                    event -> {
                        if (!timeTextField.getText().equals("")&&!activityTextField.getText().equals("")&&!descriptionTextField.getText().equals("")) {
                            data.add(new Activity(timeTextField.getText(), activityTextField.getText(), descriptionTextField.getText()));
                        } else{
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Incorrect Information");
                            alert.setHeaderText(null);
                            alert.setContentText("Type the time, activity and description");
                            alert.showAndWait();
                            event.consume();
                        }
                    }
            );
            dialog.showAndWait();
        }







        public void newWindowBtn (ActionEvent actionEvent){
            mainPanel.setVisible(false);
            newPanel.setVisible(true);
        }
    }
