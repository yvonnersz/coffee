package com.galvanize.coffeeapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
            if (name == null) name = "";
            if (dairy == null) dairy = "";
            coffees = coffeeService.getCoffees(name, dairy);
        }

        if (coffees.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(coffees);
        }
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    void InvalidCoffeeInput(InvalidCoffeeInput e) {}

    @PostMapping("/coffees")
    public Coffee addCoffee(@RequestBody Coffee coffee) {
        return coffeeService.addCoffee(coffee);
    }

    @GetMapping("/coffees/{name}")
    public ResponseEntity<Coffee> getCoffee(@PathVariable String name) {
        Coffee coffee = coffeeService.getCoffee(name);

        if (coffee == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(coffee);
        }
    }

    @PatchMapping("/coffees/{name}")
    public ResponseEntity<Coffee> updateCoffee(@PathVariable String name, @RequestBody UpdateCoffee request) {
        Coffee coffee = coffeeService.updateCoffee(name, request.getPrice());

        if (coffee == null) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(coffee);
        }
    }

    @DeleteMapping("/coffees/{name}")
    public ResponseEntity deleteCoffee(@PathVariable String name) {
        Coffee coffee = coffeeService.getCoffee(name);

        if (coffee != null) {
            coffeeService.delete(name);
            return ResponseEntity.accepted().build();
        } else {
            return ResponseEntity.noContent().build();
        }
    }
}
