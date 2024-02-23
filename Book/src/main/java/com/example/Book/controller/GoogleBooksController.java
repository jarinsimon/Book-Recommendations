package com.example.Book.controller;

import org.springframework.beans.factory.annotation.Autowired;  //Automatic dependency injection
import org.springframework.web.bind.annotation.GetMapping;      //Map HTTP GET requests onto specific methods
import org.springframework.web.bind.annotation.RequestMapping;  //Map web requests onto specific handler classes/methods
import org.springframework.web.bind.annotation.RequestParam;    //Bind request parameters to method parameters
import org.springframework.web.bind.annotation.RestController;  //Indicate a particular class serves as a controller
import org.springframework.web.client.RestTemplate;

import com.example.Book.service.GoogleBooksAPI;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/books")
public class GoogleBooksController {
    private final GoogleBooksAPI googleBooksAPI; 

    @Autowired GoogleBooksController(GoogleBooksAPI googleBooksAPI){
        this.googleBooksAPI = googleBooksAPI;
    }

    @GetMapping("/search")
    public JsonNode searchBooks(@RequestParam("query") String query){
        return googleBooksAPI.bookResults(query);
    }
}
