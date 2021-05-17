package com.galvanize.coffeeapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
public class CoffeeServiceTests {
    @Mock
    CoffeeRepository coffeeRepository;

    CoffeeService coffeeService;

    List<Coffee> coffees;

    @BeforeEach
    void setUp() {
        coffeeService = new CoffeeService(coffeeRepository);

        coffees = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Coffee coffee = new Coffee("Latte" + i, 4.25, true);
            coffees.add(coffee);
        }
    }
}
