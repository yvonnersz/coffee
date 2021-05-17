package com.galvanize.coffeeapi;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    ObjectMapper mapper = new ObjectMapper();

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
                .andExpect(jsonPath("$.coffees", hasSize(coffees.size())));
    }

    @Test
    void getCoffees_noParams_returnsNoContent() throws Exception {
        when(coffeeService.getCoffees()).thenReturn(new CoffeeList(new ArrayList<>()));

        mockMvc.perform(get("/coffees"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCoffees_byNameAndDairy_returnsMatchingCoffees() throws Exception {
        Coffee coffee = coffees.get(0);

        when(coffeeService.getCoffees(anyString(), anyString())).thenReturn(new CoffeeList(Arrays.asList(coffee)));
        mockMvc.perform(get("/coffees?name=" + coffee.getName() + "&dairy=" + coffee.isDairy()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coffees", hasSize(1)));
    }

    @Test
    void getCoffees_byNameAndDairy_returnsNoContent() throws Exception {
        when(coffeeService.getCoffees(anyString(), anyString())).thenReturn(new CoffeeList(new ArrayList<>()));
        mockMvc.perform(get("/coffees?name=noMatchingCoffee&dairy=false"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCoffees_byName_returnsMatchingCoffees() throws Exception {
        Coffee coffee = coffees.get(0);

        when(coffeeService.getCoffees(anyString(), anyString())).thenReturn(new CoffeeList(Arrays.asList(coffee)));
        mockMvc.perform(get("/coffees?name=noMatchingCoffee"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coffees", hasSize(1)));
    }

    @Test
    void getCoffees_byName_returnsNoContent() throws Exception {
        when(coffeeService.getCoffees(anyString(), anyString())).thenReturn(new CoffeeList(new ArrayList<>()));
        mockMvc.perform(get("/coffees?name=noMatchingCoffee"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getCoffees_byDairy_returnsMatchingCoffees() throws Exception {
        when(coffeeService.getCoffees(anyString(), anyString())).thenReturn(new CoffeeList(coffees));

        mockMvc.perform(get("/coffees?dairy=false"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.coffees", hasSize(3)));
    }

    @Test
    void getCoffees_byDairy_returnsNoContent() throws Exception {
        when(coffeeService.getCoffees(anyString(), anyString())).thenReturn(new CoffeeList(new ArrayList<>()));

        mockMvc.perform(get("/coffees?dairy=true"))
                .andExpect(status().isNoContent());
    }

    @Test
    void addCoffee_withParams_returnsAddedCoffee() throws Exception {
        Coffee coffee = coffees.get(0);

        when(coffeeService.addCoffee(any(Coffee.class))).thenReturn(coffee);

        mockMvc.perform(post("/coffees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(coffee)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(coffee.getName()));
    }

    @Test
    void addCoffee_withBadParams_returnsBadRequest() throws Exception {
        when(coffeeService.addCoffee(any(Coffee.class))).thenThrow(InvalidCoffeeInput.class);

        mockMvc.perform(post("/coffees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(""))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getCoffee_byName_returnsMatchingCoffee() throws Exception {
        Coffee coffee = coffees.get(0);

        when(coffeeService.getCoffee(anyString())).thenReturn(coffee);

        mockMvc.perform(get("/coffees/" + coffee.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name").value(coffee.getName()));
    }

    @Test
    void getCoffee_byName_returnsNoContent() throws Exception {
        when(coffeeService.getCoffee(anyString())).thenReturn(null);

        mockMvc.perform(get("/coffees/noMatchingCoffee"))
                .andExpect(status().isNoContent());
    }

//    @Test
//    void updateCoffee_withCoffeeName_returnsUpdatedCoffee() throws Exception {
//        Coffee coffee = coffees.get(0);
//
//        mockMvc.perform(patch("/coffees/" + coffee.getName())
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"name\": \"Cappuccino\", \"price\": \"5.25\"}"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("name").value("Cappuccino"));
//    }
}
