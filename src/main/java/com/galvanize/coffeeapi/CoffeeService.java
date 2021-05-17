package com.galvanize.coffeeapi;

import org.springframework.stereotype.Service;

@Service
public class CoffeeService {
    private CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public CoffeeList getCoffees() {
        return null;
    }

    public CoffeeList getCoffees(String name, String lactoseFree) {
        return null;
    }

    public Coffee addCoffee(Coffee coffee) {
        return null;
    }

    public Coffee getCoffee(String name) {
        return null;
    }

    public Coffee updateCoffee(Coffee coffee, String name, String price) {
        return null;
    }

    public void delete(Coffee coffee) {

    }
}
