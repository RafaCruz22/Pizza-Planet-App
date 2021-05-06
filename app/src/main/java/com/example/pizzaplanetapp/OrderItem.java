package com.example.pizzaplanetapp;

public class OrderItem {

    private String title;
    private String price;
    private String totalItems;
    private String orderTotal;

    OrderItem () {//needed for firebase

    }

    public OrderItem(String title, String price, String imageResource,String totalItems, String orderTotal) {
        this.title = title;
        this.price = price;
        this.totalItems = totalItems;
        this.orderTotal = orderTotal;
    }

    public String getTotalItems() {
        return totalItems;
    }

    public void setTotalItems(String totalItems) {
        this.totalItems = totalItems;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
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

    public void setPrice(String price) {
        this.price = price;
    }

}
