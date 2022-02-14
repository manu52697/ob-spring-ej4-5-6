package com.example.obspringej456.controllers;

import com.example.obspringej456.entities.Laptop;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class LaptopControllerTest {

    private TestRestTemplate testRestTemplate;

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp(){
        restTemplateBuilder = restTemplateBuilder.rootUri("http://localhost:" + port);
        testRestTemplate = new TestRestTemplate(restTemplateBuilder);
    }

    @Test
    void findAll() {
        ResponseEntity<Laptop[]> response = testRestTemplate.getForEntity("/api/laptops", Laptop[].class);

        Laptop[] responseBody = response.getBody();
        if(responseBody == null){
            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        }else {
            assertEquals(HttpStatus.OK, response.getStatusCode());
        }

    }

    @Test
    void findById() {

        ResponseEntity<Laptop> response = testRestTemplate.getForEntity("/api/laptops/1", Laptop.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, response.getBody().getId());
    }

    @Test
    void create() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String body = """
                {
                  "manufacturer": "TestManufacturer",
                  "model": "TestModel",
                  "os": "TestOS",
                  "ramSize": 8,
                  "releaseDate": "2022-02-11"
                }
                """;

        HttpEntity<String> request =new HttpEntity<>(body, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange(
                "/api/laptops",HttpMethod.POST,
                request, Laptop.class);
        Laptop responseBody = response.getBody();
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertEquals(responseBody.getManufacturer(),"TestManufacturer");
        assertNotNull(responseBody.getId());
        assertEquals(responseBody.getModel(),"TestModel");
        assertEquals(responseBody.getOs(),"TestOS");
    }

    @Test
    void update(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String body = """
                {
                  "id": 1,
                  "manufacturer": "TestManufacturer",
                  "model": "TestModel",
                  "os": "TestOS",
                  "ramSize": 8,
                  "releaseDate": "2022-02-11"
                }
                """;

        HttpEntity<String> request =new HttpEntity<>(body, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange(
                "/api/laptops/1", HttpMethod.PUT, request, Laptop.class);
        Laptop responseBody = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1L, responseBody.getId());
        assertEquals("TestManufacturer", responseBody.getManufacturer());

    }

    @Test
    void delete(){
        // Insert the test laptop to delete

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        String body = """
                {
                  "manufacturer": "TestManufacturer",
                  "model": "TestModel",
                  "os": "TestOS",
                  "ramSize": 12,
                  "releaseDate": "2022-02-11"
                }
                """;

        HttpEntity<String> request =new HttpEntity<>(body, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange(
                "/api/laptops",HttpMethod.POST,
                request, Laptop.class);
        Laptop responseBody = response.getBody();
        //assertNotNull(responseBody.getId());
        Long testID = responseBody.getId();

        String testURL = "/api/laptops/3" + testID;
        System.out.println(testID);

        // Test a correct request
        HttpEntity<String> deleteRequest = new HttpEntity<>("", headers);
        ResponseEntity<String> deleteResponse = testRestTemplate.exchange(
                testURL, HttpMethod.DELETE,
                        deleteRequest, String.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());

        // Test trying to delete a non existent id

        deleteResponse = testRestTemplate.exchange(
                testURL, HttpMethod.DELETE,
                deleteRequest, String.class);
        assertEquals(HttpStatus.NOT_FOUND, deleteResponse.getStatusCode());

        // test trying to delete a laptop using a bad url

        deleteResponse = testRestTemplate.exchange(
                "/api/laptops/-1", HttpMethod.DELETE,
                request, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, deleteResponse.getStatusCode());
    }

    @Test
    void deleteAll(){

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));

        // populate the db for the test
        String body = """
                {
                  "manufacturer": "TestManufacturer",
                  "model": "TestModel",
                  "os": "TestOS",
                  "ramSize": 12,
                  "releaseDate": "2022-02-11"
                }
                """;

        HttpEntity<String> request =new HttpEntity<>(body, headers);
        ResponseEntity<Laptop> response = testRestTemplate.exchange(
                "/api/laptops",HttpMethod.POST,
                request, Laptop.class);



        HttpEntity<String> deleteRequest = new HttpEntity<>("", headers);
        // Test deleting all when there is something stored
        ResponseEntity<String> deleteResponse = testRestTemplate.exchange(
                "/api/laptops", HttpMethod.DELETE,deleteRequest, String.class);
        assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());


        // Test deleting all when there is nothing stored
        deleteResponse = testRestTemplate.exchange(
                "/api/laptops", HttpMethod.DELETE,deleteRequest, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, deleteResponse.getStatusCode());




    }

}
