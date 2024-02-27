package com.example.Book.service;

import org.springframework.beans.factory.annotation.Autowired;  //Automatic dependency injection
import org.springframework.beans.factory.annotation.Value;      //Add values from property filess/env variables
import org.springframework.web.client.RestTemplate;             //Make HTTP Requests to external servers
import org.springframework.stereotype.Service;                  //Class is a service component

import com.example.Book.service.BookSelect;

import com.fasterxml.jackson.core.JsonProcessingException;      //Handle exceptions during JSON Processing    
import com.fasterxml.jackson.databind.JsonNode;                 //Manipulation of JSON data in Java applications
import com.fasterxml.jackson.databind.ObjectMapper;             //Convert JSON data to/from Java objects

@Service
public class GoogleBooksAPI {
    private final RestTemplate restTemplate;
    private final BookSelect bookSelect;

    @Autowired
    public GoogleBooksAPI(RestTemplate restTemplate, BookSelect bookSelect){
        this.restTemplate = restTemplate;
        this.bookSelect = bookSelect;
    }

    @Value("${google.api.key}")
    private String apiKey;

    public JsonNode bookResults(String query){
        JsonNode jsonNode = searchBooks(query);
        bookSelect.printBooks(jsonNode);
        JsonNode chosenBook = bookSelect.chooseBook(jsonNode);
        return chosenBook;
    }

    private JsonNode searchBooks(String query){
        String uri = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&key=" + apiKey;
        String ResponseBody = restTemplate.getForObject(uri, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(ResponseBody);
            return rootNode.path("items");
            
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return objectMapper.createObjectNode();
        }
    }

    public JsonNode authorRecs(String bookAuthor){
        String uri = "https://www.googleapis.com/books/v1/volumes?q=inauthor:" + bookAuthor + "&key=" + apiKey;
        String ResponseBody = restTemplate.getForObject(uri, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(ResponseBody);
            return rootNode.path("items");
        } catch (JsonProcessingException e){
            e.printStackTrace();
            return objectMapper.createObjectNode();
        }
    }
}
