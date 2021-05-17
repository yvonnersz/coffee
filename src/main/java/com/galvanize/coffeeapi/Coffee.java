package com.galvanize.coffeeapi;

import java.util.List;

public class Coffee {
    private String name;
    private double price;
    private boolean lactoseFree;
    private List<String> ingredients;

    public Coffee(String name, double price, boolean lactoseFree) {
        this.name = name;
        this.price = price;
        this.lactoseFree = lactoseFree;
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

    public boolean isLactoseFree() {
        return lactoseFree;
    }

    public void setLactoseFree(boolean lactoseFree) {
        this.lactoseFree = lactoseFree;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
