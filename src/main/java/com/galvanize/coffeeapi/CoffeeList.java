package com.galvanize.coffeeapi;

import java.util.List;

public class CoffeeList {
    private List<Coffee> coffees;

    public CoffeeList(List<Coffee> coffees) {
        this.coffees = coffees;
    }

    public List<Coffee> getCoffees() {
        return coffees;
    }

    public void setCoffees(List<Coffee> coffees) {
        this.coffees = coffees;
    }
}
