package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
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
import javafx.util.Duration;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;


public class Controller {
    @FXML
    private ComboBox comboBox;
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private AnchorPane newPanel;
    @FXML
    private AnchorPane mondayPanel;
    @FXML
    private TableView<Activity> tableView;
    @FXML
    private TableView<Activity> tableView1;
    @FXML
    Label dateTime;

    private final ObservableList<Activity> data = FXCollections.observableArrayList();

    TableColumn timeCol = new TableColumn("Time");
    TableColumn activityCol = new TableColumn("Activity");
    TableColumn descriptionCol = new TableColumn("Description");

    public void initialize() {

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
                "Thursday",
                "Friday",
                "Saturday",
                "Sunday"
        );
        comboBox.setPromptText("What´s the day today?");


        activityCol.setCellFactory(TextFieldTableCell.forTableColumn());
        activityCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Activity, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Activity, String> t) {
                        ((Activity) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setActivity(t.getNewValue());
                    }
                }

        );
        descriptionCol.setCellFactory(TextFieldTableCell.forTableColumn());
        descriptionCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Activity, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Activity, String> t) {
                        ((Activity) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setDescription(t.getNewValue());
                    }
                }

        );
        timeCol.setCellFactory(TextFieldTableCell.forTableColumn());
        timeCol.setOnEditCommit(
                new EventHandler<TableColumn.CellEditEvent<Activity, String>>() {
                    @Override
                    public void handle(TableColumn.CellEditEvent<Activity, String> t) {
                        ((Activity) t.getTableView().getItems().get(
                                t.getTablePosition().getRow())
                        ).setTime(t.getNewValue());
                    }
                }

        );

        initClock();
        setClick();
    }




    public void setClick() {
        activityCol.setCellFactory(tc -> {
            TableCell<Activity, String> cell = new TableCell<Activity, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(e -> {
                if (!cell.isEmpty()) {
                    String userId = cell.getText();
                    System.out.println(userId);
                }
            });

            return cell;
        });
    }

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    }


    public void addNewActivity(ActionEvent actionEvent) {
        Dialog<Activity> dialog = new Dialog<>();
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
                    if (!timeTextField.getText().equals("") && !activityTextField.getText().equals("") && !descriptionTextField.getText().equals("")) {
                        data.add(new Activity(timeTextField.getText(), activityTextField.getText(), descriptionTextField.getText()));
                    } else {
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

    public void editBtn(ActionEvent actionEvent) {
        if (tableView.isEditable()) {
            tableView.setEditable(false);
        } else {
            tableView.setEditable(true);
        }
    }
    public void newClick (ActionEvent actionEvent) {
        comboBox.onMouseClickedProperty();
        newPanel.setVisible(false);
        mondayPanel.setVisible(true);
    }

    public void newWindowBtn(ActionEvent actionEvent) {
        mainPanel.setVisible(false);
        newPanel.setVisible(true);
    }
    public void returnValue (ActionEvent actionEvent){
        newPanel.setVisible(true);
        mondayPanel.setVisible(false);
    }

}
