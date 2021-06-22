package sample;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
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

import java.io.*;
import java.util.*;

import javax.swing.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class Controller {
    @FXML public ProgressBar MyProgressBar;
    @FXML
    private ComboBox comboBox;
    @FXML
    private ComboBox comboBox1;
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
    @FXML
    Label newActivityLabel;
    @FXML CheckBox activityCompletedChkBox;




    public  ObservableList<Activity> Monday = FXCollections.observableArrayList();
    private ObservableList<Activity> Tuesday = FXCollections.observableArrayList();
    private ObservableList<Activity> Wednesday = FXCollections.observableArrayList();
    private ObservableList<Activity> Thursday = FXCollections.observableArrayList();
    private ObservableList<Activity> Friday = FXCollections.observableArrayList();
    private ObservableList<Activity> Saturday = FXCollections.observableArrayList();
    private ObservableList<Activity> Sunday = FXCollections.observableArrayList();
    private ArrayList<ObservableList<Activity>> days = new ArrayList<>();
    private String[] dayNames = {"Monday","Tuesday","Wednesday","Thursday","Friday","Saturday","Sunday"};
    //days.get(0) = monday etc


    //etc
    TableColumn timeCol = new TableColumn("Time");
    TableColumn activityCol = new TableColumn("Activity");
    TableColumn descriptionCol = new TableColumn("Description");


    public Controller() throws FileNotFoundException {
    }

    public void initialize() {
        days.add(Monday);
        days.add(Tuesday);
        days.add(Wednesday);
        days.add(Thursday);
        days.add(Friday);
        days.add(Saturday);
        days.add(Sunday);


        Gson gson = new GsonBuilder().setPrettyPrinting().create();


        //open and read Json for any previously saved data.

        for(int i =0; i<7;i++){

            try (Reader reader = new FileReader(dayNames[i] + ".json")) {
            // Convert JSON File to Java Object
                ArrayList<Activity> input = gson.fromJson(reader, new TypeToken<ArrayList<Activity>>() {}.getType());

                days.set(i,FXCollections.observableArrayList(input));


            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


        timeCol.setMinWidth(100);
        timeCol.setCellValueFactory(
                new PropertyValueFactory<Activity, String>("time"));

        activityCol.setMinWidth(100);
        activityCol.setCellValueFactory(
                new PropertyValueFactory<Activity, String>("activity"));

        descriptionCol.setMinWidth(200);
        descriptionCol.setCellValueFactory(
                new PropertyValueFactory<Activity, String>("description"));

        tableView.setItems(days.get(0));
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

        // you can find the selected day to access which activities there are.
        //you have an arraylist of activities so you know how many activities there are. X



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

        //when the program starts figure out which day it is to get the list of activities from that day.
       int i = 0; //the correct day number after you find it //0 is monmday 1 is tuesday etc
        for (Activity ac: days.get(i)) {
            myQueue.add(ac);
        }
        // System.out.println((String) myQueue.pop());
                          //day  //activity number
       // String cActivity = (String) myQueue.pop();
        cActivity = myQueue.pop();
       newActivityLabel.setText(cActivity.getActivity());
        //label text =  myQueue.pop()

    }
    public static Activity cActivity;
    public static LinkedList<Activity> myQueue = new LinkedList<>();

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
    //in the initualizer or after all activities are made. or when a new activity is made
    //queue

    //get today , e/g monday
    //get the list of activities for monday

    //add all activites to the queue one by one
    //dequeue to add it to the to do text.


    //in the checkbox action
    //when the tickbox is ticked the activity is complete.
    //dequeue to get the next item in the queue and show it in the to do box.

    private void initClock() {
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            dateTime.setText(LocalDateTime.now().format(formatter));
            updateProgressBar();//this checks how many activities are completed today and updates the progress bar.
            //Activity.resetNext()

            //loop while Activity.hasNext();
                //currentActivity = Activity.getNext
                //Output currentActivity
                //if currentActivity.isResolved=true && activity.hasNext=true
                    //then continue with next activity
                //else if  currentActivity.isResolved = false
                    //do nothing and wait with current activity
                //else if currentActivity.isResolved=true && activity.hasNext=false
                    //break because there are no move activites
                 // End if
            //End loop

            checkIfOnTask();
        }), new KeyFrame(javafx.util.Duration.seconds(1)));
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();




    }
    public void checkIfOnTask() {

        //find the current task based on the times of activities and compare to system time
        //make a pop up window appear to ask the client if they are on task

        // copy time, activity and description table to the main panel
        //loop
        //if task.completed is false then
        //display the first task on the day
        //if task.completed is true then
        //display the second task on the day
        // end loop if all tasks are completed
        //return "You have finished all of your activities"


        //clicnet will tick true /false to change that activities boolean.

    }

    public void updateProgressBar(){


           // JProgressBar progressBar = this.progressBar;
        //current progress = 100  / total tasks  * tasks compelted.
        //progress
      }



        //get today from the system. (monday tuesday etc.)
        //or ask the user what day it is from the combo box [0, 6]
        //integer = get the total number of items in days.get(day)
        //get the total completedOrNot boolean from the activities.

        ///progress bar = 100% / integer of total number of items. multiplied by how many items are complete.


    private void getClass(Activity cActivity) {
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
                        //if the day selected in monday add it to monday
                        //if the day selected in tuesday add it to tuesday



                        {
                }
                        days.get(comboBox.getSelectionModel().getSelectedIndex()).add(new Activity(timeTextField.getText(), activityTextField.getText(), descriptionTextField.getText()));


                        //combobox index 0-6
                        //data[combobox.selected].add

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

    public void DaySelectedAction (ActionEvent actionEvent) { //when you click the day combo box.
        tableView.setItems(days.get(comboBox.getSelectionModel().getSelectedIndex()));
        comboBox.onMouseClickedProperty();
        mainPanel.setVisible(false);
        newPanel.setVisible(true);
    }
    public void newClick2 (ActionEvent actionEvent) {  //day button
        comboBox.onMouseClickedProperty();
        //when you click a button change the data of the table to the correct arraylist

    }

    public void newWindowBtn(ActionEvent actionEvent) {
        mainPanel.setVisible(false);
        newPanel.setVisible(true);
    }
    public void returnValue (ActionEvent actionEvent){
        newPanel.setVisible(true);
        mondayPanel.setVisible(false);
    }

    public void saveObjects(ActionEvent actionEvent) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        for(int i =0; i<7;i++){
            try (FileWriter writer = new FileWriter(dayNames[i] +".json")) {
                gson.toJson(days.get(i), writer);
                System.out.println("Saved.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void activityCompletedBtn(ActionEvent actionEvent) {

        try{
            cActivity.setCompleted(true); //set the activity to completed.
            cActivity = myQueue.pop(); //get the next activity
            newActivityLabel.setText(cActivity.getActivity());
            updateProgressBar();
            activityCompletedChkBox.setSelected(false); //un tick the checkbox
        }catch(Exception e){
            newActivityLabel.setText("No more activities for today!");
        }


    }
}
