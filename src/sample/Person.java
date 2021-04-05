package sample;

import java.time.LocalDate;
import javafx.beans.property.SimpleStringProperty;

public class Person {
    private SimpleStringProperty time, activity, description;

    public Person(String time) {
        this.time = new SimpleStringProperty(time);
        this.activity = new SimpleStringProperty(null);
        this.description = description;
    }

    public String getTime() {
        return time.get();
    }

    public SimpleStringProperty timeProperty() {
        return time;
    }

    public void setTime(String time) {
        this.time.set(time);
    }

    public String getActivity() {
        return activity.get();
    }

    public SimpleStringProperty activityProperty() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity.set(activity);
    }

    public String getDescription() {
        return description.get();
    }

    public SimpleStringProperty descriptionProperty() {
        return description;
    }

    public void setDescription(String descritpion) {
        this.description.set(descritpion);
    }
}
