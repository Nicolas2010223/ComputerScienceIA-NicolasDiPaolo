package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;



public class Controller {
    @FXML
    private AnchorPane mainPanel;
    @FXML
    private AnchorPane newPanel;
    @FXML
    private TableView<Person> tableView;
    @FXML
    private TableColumn<Person, String> time;
    @FXML
    private TableColumn<Person, String> activity;
    @FXML
    private TableColumn<Person, String> description;
    public static class TableViewSample extends Application {

        private TableView table = new TableView();
        public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void start(Stage stage) {
            Scene scene = new Scene(new Group());
            stage.setTitle("Schedule");
            stage.setWidth(300);
            stage.setHeight(500);

           ObservableList<Activity> data =
                    FXCollections.observableArrayList(
                            new Activity(8,  "CS", "CS 2" ),
            new Activity(6,  "Alvaroooo", "Te quiero" )



                    );

            final Label label = new Label("Address Book");
            label.setFont(new Font("Arial", 20));

            table.setEditable(true);

            TableColumn time = new TableColumn("Time");
            time.setCellValueFactory(
                    new PropertyValueFactory<Activity, String>("8"));
            TableColumn activity = new TableColumn("Activity");
            TableColumn description = new TableColumn("Description");

            table.setItems(data);
            table.getColumns().addAll(time, activity, description);

            final VBox vbox = new VBox();
            vbox.setSpacing(5);
            vbox.setPadding(new Insets(10, 0, 0, 10));
            vbox.getChildren().addAll(label, table);

            ((Group) scene.getRoot()).getChildren().addAll(vbox);

            stage.setScene(scene);
            stage.show();
        }
    }



        public void newWindowBtn (ActionEvent actionEvent){
            mainPanel.setVisible(false);
            newPanel.setVisible(true);
        }
    }
