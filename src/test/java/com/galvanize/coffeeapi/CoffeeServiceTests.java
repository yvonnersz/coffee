package com.galvanize.coffeeapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

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

    @Test
    void getCoffees_noParams_returnsAllCoffee() {
        when(coffeeRepository.findAll()).thenReturn(coffees);
        CoffeeList actual = coffeeService.getCoffees();

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertEquals(coffees.size(), actual.getCoffees().size());
    }

    @Test
    void getCoffees_noParams_returnsNoContent() {
        when(coffeeRepository.findAll()).thenReturn(new ArrayList<>());
        CoffeeList actual = coffeeService.getCoffees();

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }
}
