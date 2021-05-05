package com.example.pizzaplanetapp;

public class MenuItem {
    // Member variables representing the title and information about the item.
    private String title;
    private String description;
    private String detail;
    private String imageResource;
    private String price;

    MenuItem() { //needed for Firebase
    }
    /**
     * Constructor for the MenuItem data model.
     **/


    /**
     * Gets the price of the Meal.
     *
     * @return The price of the meal.
     */
    public String getPrice() {
        return price;
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