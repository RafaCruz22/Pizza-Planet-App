package com.example.pizzaplanetapp;

public class CartItem {

    private String title;
    private String price;
    private String imageResource;
    private String position;

    CartItem () {//needed for firebase

    }

    public CartItem(String title, String price, String imageResource,String position) {
        this.title = title;
        this.price = price;
        this.imageResource = imageResource;
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public String getPrice() {
        return price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageResource() {
        return imageResource;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }


}
