package com.bilan.postsApp;

import java.time.LocalDateTime;

public class DaybooksItem {

    private int id;
    private String title;

    private String description;

    private LocalDateTime dateTime;

    public DaybooksItem(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "DaybooksItem:" +
                "\n\ttitle='" + title + '\'' +
                "\n\t, description='" + description + '\'' +
                "\n\n";
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }
}
