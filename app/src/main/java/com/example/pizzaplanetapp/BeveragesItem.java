package com.example.pizzaplanetapp;

public class BeveragesItem {

    // Member variables representing the title and information about the item.
    private String title;
    private String description;
    private String detail;
    private String imageResource;
    private String price;

    BeveragesItem() { //needed for Firebase
    }

    //imageResource changed from int to String for support of firebase HTTPS
    //locally we would use the int imageResource, return the int
    // and pull from strings.
    BeveragesItem(String title, String description, String detail, String imageResource, String price) {
        this.title = title;
        this.description = description;
        this.detail = detail;
        this.imageResource = imageResource;
        this.price = price;

    }

    public String getPrice() {
        return price;
    }

    public String getImageResource() {
        return imageResource;
    }

    public String getTitle() {
        return title;
    }

    //temp if needed
    public String getDescription() {
        return description;
    }

    public String getDetail() {
        return detail;
    }



}
