package com.galvanize.coffeeapi;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class CoffeeList {
    private List<Coffee> coffees;

    public CoffeeList() {}

    public CoffeeList(List<Coffee> coffees) {
        this.coffees = coffees;
    }

    public List<Coffee> getCoffees() {
        return coffees;
    }

    public void setCoffees(List<Coffee> coffees) {
        this.coffees = coffees;
    }

    @JsonIgnore
    public boolean isEmpty() {
        return coffees.isEmpty();
    }
}
