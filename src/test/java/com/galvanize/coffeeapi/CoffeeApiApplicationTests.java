package com.galvanize.coffeeapi;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoffeeApiApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	CoffeeRepository coffeeRepository;

	List<Coffee> coffees;

	@BeforeEach
	void setUp() {
		coffees = new ArrayList<>();
		String[] coffeeNames = {"Latte", "Cappuccino", "ColdBrew"};

		for (int i = 0; i < 3; i++) {
			Coffee coffee = new Coffee(coffeeNames[i], 4.25, false);
			coffees.add(coffee);
		}

		coffeeRepository.saveAll(coffees);
	}

	@AfterEach
	void tearDown() {
		coffeeRepository.deleteAll();
	}

	@Test
	void contextLoads() {
	}

	@Test
	void getCoffees_noParams_returnsAllCoffee() {
		String uri = "/coffees";
		ResponseEntity<CoffeeList> response = restTemplate.getForEntity(uri, CoffeeList.class);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(coffees.size(), response.getBody().getCoffees().size());
	}

	@Test
	void getCoffees_noParams_returnsNoContent() {
		coffeeRepository.deleteAll();
		String uri = "/coffees";
		ResponseEntity<CoffeeList> response = restTemplate.getForEntity(uri, CoffeeList.class);

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

}
