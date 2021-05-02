package com.example.pizzaplanetapp;

public class MenuItem {
    // Member variables representing the title and information about the sport.
    private String title;
    private String description;
    private String detail;
    private String imageResource;


    MenuItem() { //needed for Firebase
    }

    MenuItem(String title, String description, String detail, String imageResource) {
        this.title = title;
        this.description = description;
        this.detail = detail;
        this.imageResource = imageResource;
    }

    public String getTitle() {
        return title;
    }

    public String getDetail() {
        return detail;
    }

    public String getDescription() {
        return description;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }


}
