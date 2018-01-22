package org.techknights.ergo.Retrofit.ListViews;

/**
 * Created by Hansa on 1/22/2018.
 */

public class MilestonesOfUser {
    String id;
    String description;
    String start_date;
    String end_date;

    public MilestonesOfUser(String id, String description, String start_date, String end_date) {
        this.id = id;
        this.description = description;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public String getStart_date() {
        return start_date;
    }

    public String getEnd_date() {
        return end_date;
    }
}
