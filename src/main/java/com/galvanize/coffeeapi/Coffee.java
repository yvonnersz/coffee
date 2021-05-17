package com.galvanize.coffeeapi;

import java.util.List;

public class Coffee {
    private String name;
    private double price;
    private boolean dairy;
    private List<String> ingredients;

    public Coffee(String name, double price, boolean dairy) {
        this.name = name;
        this.price = price;
        this.dairy = dairy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isDairy() {
        return dairy;
    }

    public void setDairy(boolean dairy) {
        this.dairy = dairy;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
