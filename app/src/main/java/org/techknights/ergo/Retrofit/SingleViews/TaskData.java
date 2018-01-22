package org.techknights.ergo.Retrofit.SingleViews;

/**
 * Created by Hansa on 1/21/2018.
 */

public class TaskData {

    String name;
    String description;
    String status;
    String start_date;
    String end_date;

    public TaskData(String name, String description, String status, String start_date, String end_date) {
        this.name = name;
        this.description = description;
        this.status = status;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
}
