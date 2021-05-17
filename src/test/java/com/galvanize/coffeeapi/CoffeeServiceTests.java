package com.galvanize.coffeeapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
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

    @Test
    void getCoffee_withNameAndDairy_returnsMatchingCoffees() {
        Coffee coffee = coffees.get(0);

        when(coffeeRepository.findByNameContainsAndDairyContains(anyString(), anyString())).thenReturn(Arrays.asList(coffee));
        CoffeeList actual = coffeeService.getCoffees(coffee.getName(), Boolean.toString(coffee.isDairy()));

        assertNotNull(actual);
        assertFalse(actual.isEmpty());
        assertEquals(1, actual.getCoffees().size());
    }

    @Test
    void getCoffee_withNameAndDairy_returnsNoContent() {
        when(coffeeRepository.findByNameContainsAndDairyContains(anyString(), anyString())).thenReturn(new ArrayList<>());
        CoffeeList actual = coffeeService.getCoffees("noMatchingCoffee", Boolean.toString(true));

        assertNotNull(actual);
        assertTrue(actual.isEmpty());
    }
}
