package com.example.restuarantreviewer;

public class Restaurant {

    private String name;
    private String cuisine;
    private String price;

    public Restaurant(String name, String cuisine, String price) {
        this.name = name;
        this.cuisine = cuisine;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}

