package com.galvanize.coffeeapi;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CoffeeController.class)
public class CoffeeControllerTests {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    CoffeeService coffeeService;

    List<Coffee> coffees;

    @BeforeEach
    void setUp() {
        coffees = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            Coffee coffee = new Coffee("Latte " + i, 4.25, false);
            coffees.add(coffee);
        }
    }

    @Test
    void getCoffees_noParams_returnsAllCoffees() throws Exception {
        when(coffeeService.getCoffees()).thenReturn(new CoffeeList(coffees));

        mockMvc.perform(get("/coffees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coffees", hasSize(3)));
    }

    @Test
    void getCoffees_noParams_returnsNoContent() throws Exception {
        when(coffeeService.getCoffees()).thenReturn(new CoffeeList(new ArrayList<>()));

        mockMvc.perform(get("/coffees"))
                .andExpect(status().isNoContent());
    }


//    @Test
//    void getCoffees_byNameAndLactoseFree() {
//        Coffee coffee = coffees.get(0);
//        mockMvc.perform(get("/coffees?name=" + coffee.getName() + "&lactosefree=" + coffee.isLactoseFree()))
//                .andExpect(status().isOk())
//    }
}
