package org.techknights.ergo.Retrofit.ListViews;

/**
 * Created by Hansa on 1/21/2018.
 */

public class ProjectOfUser {

    String id;
    String name;
    String category;
    String start_date;
    String end_date;

    public ProjectOfUser(String id, String name, String category, String start_date, String end_date) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
}
