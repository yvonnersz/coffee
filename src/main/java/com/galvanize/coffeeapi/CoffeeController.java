package com.galvanize.coffeeapi;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CoffeeController {
    private CoffeeService coffeeService;

    public CoffeeController(CoffeeService coffeeService) {
        this.coffeeService = coffeeService;
    }

    @GetMapping("/coffees")
    public ResponseEntity<CoffeeList> getCoffees(@RequestParam(required = false) String name,
                                                 @RequestParam(required = false) String dairy) {
        CoffeeList coffees;

        if (name == null && dairy == null) {
            coffees = coffeeService.getCoffees();
        } else {
            coffees = coffeeService.getCoffees(name, dairy);
        }

        if (coffees.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(coffees);
        }
    }
}
