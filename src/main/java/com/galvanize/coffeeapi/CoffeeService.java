package com.galvanize.coffeeapi;

import org.springframework.stereotype.Service;

@Service
public class CoffeeService {
    private CoffeeRepository coffeeRepository;

    public CoffeeService(CoffeeRepository coffeeRepository) {
        this.coffeeRepository = coffeeRepository;
    }

    public CoffeeList getCoffees() {
        return new CoffeeList(coffeeRepository.findAll());
    }

    public CoffeeList getCoffees(String name, String dairy) {
        return new CoffeeList(coffeeRepository.findByNameContainsAndDairyContains(name, dairy));
    }

    public Coffee addCoffee(Coffee coffee) {
        return coffeeRepository.save(coffee);
    }

    public Coffee getCoffee(String name) {
        return coffeeRepository.findByNameContains(name);
    }

    public Coffee updateCoffee(Coffee coffee, String name, String price) {
        return null;
    }

    public void delete(Coffee coffee) {

    }
}
