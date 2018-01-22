package org.techknights.ergo.Retrofit.SingleViews;

/**
 * Created by Hansa on 1/22/2018.
 */

public class ProjectData {
    String name;
    String category;
    String start_date;
    String end_date;

    public ProjectData(String name, String category, String start_date, String end_date) {
        this.name = name;
        this.category = category;
        this.start_date = start_date;
        this.end_date = end_date;
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
