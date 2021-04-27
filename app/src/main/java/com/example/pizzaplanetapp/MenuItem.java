package com.example.pizzaplanetapp;

public class MenuItem {
    // Member variables representing the title and information about the sport.
    private String title;
    private String description;
    private String detail;
    private final int imageResource;


    /**
     * Constructor for the MenuItem data model.
     *
     * @param title       The name if the sport.
     * @param description Information about the sport.
     */


    MenuItem(String title, String description, String detail, int imageResource) {
        this.title = title;
        this.description = description;
        this.detail = detail;
        this.imageResource = imageResource;

    }

    public int getImageResource() {
        return imageResource;
    }


    /**
     * Gets the title of the Meal.
     *
     * @return The title of the Meal.
     */
    String getTitle() {
        return title;
    }


    /**
     * Gets the info about the Meal.
     *
     * @return The info about the MEal.
     */
    String getInfo() {
        return description;
    }


    String getDetail() {
        return detail;
    }


}
