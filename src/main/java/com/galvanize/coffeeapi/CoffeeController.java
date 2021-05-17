package com.galvanize.coffeeapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeController {
    private CoffeeService coffeeService;

    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/coffees")
    public ResponseEntity<CoffeeList> getCoffees() {
        CoffeeList coffees = coffeeService.getCoffees();

        if (coffees.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(coffees);
        }
    }
}
