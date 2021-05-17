package com.galvanize.coffeeapi;

public class UpdateCoffee {
    private String name;
    private String price;

    public UpdateCoffee() {}

    public UpdateCoffee(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
