package com.galvanize.coffeeapi;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CoffeeApiApplicationTests {

	@Autowired
	TestRestTemplate restTemplate;
	RestTemplate patchRestTemplate;

	@Autowired
	CoffeeRepository coffeeRepository;

	List<Coffee> coffees;

	@BeforeEach
	void setUp() {
		this.patchRestTemplate = restTemplate.getRestTemplate();
		HttpClient httpClient = HttpClientBuilder.create().build();
		this.patchRestTemplate.setRequestFactory(new HttpComponentsClientHttpRequestFactory(httpClient));

		coffees = new ArrayList<>();
		String[] coffeeNames = {"Latte", "Cappuccino", "ColdBrew"};

		for (int i = 0; i < 3; i++) {
			Coffee coffee = new Coffee(coffeeNames[i], 4.25, true);
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

	@Test
	void getCoffees_withQueries_returnsMatchingCoffee() {
		Coffee coffee = coffees.get(0);
		String uri = "/coffees?name=" + coffee.getName() + "&dairy=" + coffee.isDairy();

		ResponseEntity<CoffeeList> response = restTemplate.getForEntity(uri, CoffeeList.class);

		List<Coffee> filteredCoffees = filterCoffee(coffee.getName(), coffee.isDairy());

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(filteredCoffees.size(), response.getBody().getCoffees().size());
	}

	@Test
	void getCoffees_withQueries_returnsNoContent() {
		Coffee coffee = coffees.get(0);
		String uri = "/coffees?name=noMatchingCoffee&dairy=false";

		ResponseEntity<CoffeeList> response = restTemplate.getForEntity(uri, CoffeeList.class);

		List<Coffee> filteredCoffees = filterCoffee(coffee.getName(), coffee.isDairy());

		assertNotNull(response);
		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	void addCoffee_withCoffeeParams_returnsNewlyAddedCoffee() {
		Coffee coffee = new Coffee("Frappuccino", 3.20, true);
		String uri = "/coffees";

		ResponseEntity<Coffee> response = restTemplate.postForEntity(uri, coffee, Coffee.class);

		assertNotNull(response);
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals(coffee.getName(), response.getBody().getName());
	}

	@Test
	void addCoffee_withCoffeeParams_returnsBadRequest() {
		String uri = "/coffees";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> request = new HttpEntity<>("", headers);

		ResponseEntity<Coffee> response = restTemplate.postForEntity(uri, request, Coffee.class);

		assertNotNull(response);
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	void updateCoffee_withParams_returnsUpdatedCoffee() {
		Coffee coffee = coffees.get(0);
		String uri = "/coffees/" + coffee.getName();
		String body = "{\"price\": \"4.25\"}";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> request = new HttpEntity<>(body, headers);

		ResponseEntity<Coffee> response = patchRestTemplate.exchange(uri,
				HttpMethod.PATCH, request, Coffee.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void updateCoffee_withParams_returnsBadRequest() {
		Coffee coffee = coffees.get(0);
		String uri = "/coffees/" + coffee.getName();
		String body = "";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<?> request = new HttpEntity<>(body, headers);

		ResponseEntity<Coffee> response = patchRestTemplate.exchange(uri,
				HttpMethod.PATCH, request, Coffee.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}









	public List<Coffee> filterCoffee(String name, boolean dairy) {
		List<Coffee> filteredCoffees = new ArrayList<>();

		for(Coffee coffee : coffees) {
			if (coffee.getName() == name && coffee.isDairy() == dairy) {
				filteredCoffees.add(coffee);
			}
		}

		return filteredCoffees;
	}

}
