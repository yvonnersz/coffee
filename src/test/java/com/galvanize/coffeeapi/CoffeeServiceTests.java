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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
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
            Coffee coffee = new Coffee("Latte" + i, 4.25, "true");
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
        CoffeeList actual = coffeeService.getCoffees(coffee.getName(), coffee.getDairy());

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

    @Test
    void addCoffee_withCoffeeParams_returnsAddedCoffee() {
        Coffee coffee = coffees.get(0);

        when(coffeeRepository.save(any(Coffee.class))).thenReturn(coffee);
        Coffee actual = coffeeService.addCoffee(coffee);

        assertNotNull(actual);
        assertEquals(coffee, actual);
    }

    @Test
    void getCoffee_withName_returnsMatchingCoffee() {
        Coffee coffee = coffees.get(0);

        when(coffeeRepository.findByNameContains(anyString())).thenReturn(coffee);
        Coffee actual = coffeeService.getCoffee(coffee.getName());

        assertNotNull(actual);
        assertEquals(coffee, actual);
    }

    @Test
    void getCoffee_withName_returnsNoContent() {
        when(coffeeRepository.findByNameContains(anyString())).thenReturn(null);
        Coffee actual = coffeeService.getCoffee("noMatchingCoffee");

        assertNull(actual);
    }

    @Test
    void updateCoffee_withParams_returnsUpdatedCoffee() {
        Coffee coffee = coffees.get(0);
        coffee.setPrice(5.25);

        when(coffeeRepository.findByNameContains(anyString())).thenReturn(coffee);
        when(coffeeRepository.save(any(Coffee.class))).thenReturn(coffee);


        Coffee actual = coffeeService.updateCoffee(coffee.getName(), "5.25");

        assertNotNull(actual);
        assertEquals(coffee, actual);
    }

    @Test
    void updateCoffee_withParams_returnsNull() {
        when(coffeeRepository.findByNameContains(anyString())).thenReturn(null);

        Coffee actual = coffeeService.updateCoffee("noMatchingCoffee", "5.25");

        assertNull(actual);
    }

    @Test
    void deleteCoffee_withCoffee_successful() {
        Coffee coffee = coffees.get(0);
        when(coffeeRepository.findByNameContains(anyString())).thenReturn(coffee);

        coffeeService.delete(coffee.getName());

        verify(coffeeRepository).delete(any(Coffee.class));
    }

    @Test
    void deleteCoffee_withCoffee_unsuccessful() {
        assertThrows(InvalidCoffeeInput.class, () -> {
            coffeeService.delete(null);
        });
    }
}
