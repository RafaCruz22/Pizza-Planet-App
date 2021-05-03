package com.example.pizzaplanetapp;

public class MenuItem {
    // Member variables representing the title and information about the item.
    private String title;
    private String description;
    private String detail;
    private String imageResource;

    MenuItem() { //needed for Firebase
    }


    /**
     * Constructor for the MenuItem data model.
     **/


    //imageResource changed from int to String for support of firebase
    //locally we would use the int imageResource, return the int
    // and pull from strings.
    MenuItem(String title, String description, String detail, String imageResource) {
        this.title = title;
        this.description = description;
        this.detail = detail;
        this.imageResource = imageResource;

    }

    /**
     * Gets the image of the Meal.
     *
     * @return The image of the meal.
     */
    public String getImageResource() {
        return imageResource;
    }


    /**
     * Gets the title of the Meal.
     *
     * @return The title of the Meal.
     */

    public String getTitle() {
        return title;
    }


    /**
     * Gets the info about the Meal.
     *
     * @return The info about the MEal.
     */
    //temp if needed
    public String getDescription() {
        return description;
    }

    /**
     * Gets the detail about the Meal.
     *
     * @return The detail about the meal.
     */

    public String getDetail() {
        return detail;
    }


}