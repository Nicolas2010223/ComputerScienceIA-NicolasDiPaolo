package sample;

public class Activity {
    private String time;
    private String activity;
    private String description;
    private Boolean completed = false;

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    public Boolean getCompleted() {
        return completed;
    }


    //boolean completed or not. Starts at false. When it is completed change to true.
    //loop activity
    //activityCompleted if true
    //reset
    //activity.getNext()

    //at the beginning of every week (monday) yuo can reset all activities to false.
    //or just make a button for the client to click at the start of the week... (Easier)

    public Activity(String time, String activity, String description) {
        this.time = time;
        this.activity = activity;
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
