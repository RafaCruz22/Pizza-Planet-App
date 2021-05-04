package com.example.pizzaplanetapp;

public class CartItem {

    private String title;
    private String price;
    private String imageResource;

    CartItem () {//needed for firebase

    }

    public CartItem(String title, String price, String imageResource) {
        this.title = title;
        this.price = price;
        this.imageResource = imageResource;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
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

    public void setImageResource(String imageResource) {
        this.imageResource = imageResource;
    }


}
