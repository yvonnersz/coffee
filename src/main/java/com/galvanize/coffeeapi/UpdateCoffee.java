package com.galvanize.coffeeapi;

public class UpdateCoffee {
    private String price;

    public UpdateCoffee() {}

    public UpdateCoffee(String name, String price) {
        this.price = price;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
