package com.example.Book.controller;

import org.springframework.beans.factory.annotation.Autowired;  //Automatic dependency injection
import org.springframework.web.bind.annotation.GetMapping;      //Map HTTP GET requests onto specific methods
import org.springframework.web.bind.annotation.RequestMapping;  //Map web requests onto specific handler classes/methods
import org.springframework.web.bind.annotation.RequestParam;    //Bind request parameters to method parameters
import org.springframework.web.bind.annotation.RestController;  //Indicate a particular class serves as a controller
import org.springframework.web.client.RestTemplate;             //Make HTTP Requests to external servers
import org.springframework.beans.factory.annotation.Value;      //Add values from property filess/env variables

import com.fasterxml.jackson.core.JsonProcessingException;      //Handle exceptions during JSON Processing    
import com.fasterxml.jackson.databind.JsonNode;                 //Manipulation of JSON data in Java applications
import com.fasterxml.jackson.databind.ObjectMapper;             //Convert JSON data to/from Java objects

@RestController
@RequestMapping("/api/google-books")
public class GoogleBooksAPI {
    private final RestTemplate restTemplate;

    @Autowired
    public GoogleBooksAPI(RestTemplate restTemplate){
        this.restTemplate = restTemplate;
    }

    @Value("${google.api.key}")
    private String apiKey;

    @GetMapping("/search")
    public String searchBooks(@RequestParam("query") String query){
        final String uri = "https://www.googleapis.com/books/v1/volumes?q=" + query + "&key=" + apiKey;
        String ResponseBody = restTemplate.getForObject(uri, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(ResponseBody);
            JsonNode itemsNode = rootNode.path("items");
            Integer counter = 0;

            //Select title, subtitle, and author for the frist 10 results
            if (itemsNode.isArray()){
                Integer size = itemsNode.size();
                System.out.println("Number of items in itemsNode: " + size);
                for (JsonNode item : itemsNode){
                    counter++;
                    JsonNode volumeInfoNode = item.path("volumeInfo");
                    String title = volumeInfoNode.path("title").asText("No Title");
                    String subtitle = volumeInfoNode.path("subtitle").asText("No Subtitle");
                    JsonNode authorsNode = volumeInfoNode.path("authors");
                    StringBuilder authors = new StringBuilder();

                    for (JsonNode authorNode : authorsNode) {
                        if (authors.length() > 0){
                            authors.append(", ");
                        }
                        authors.append(authorNode.asText());
                    }
                    System.out.println(counter + ". Title: " + title);
                    System.out.println("Subtitle: " + subtitle);
                    System.out.println("Authors: " + authors);
                    System.out.println();
                }
            }
        } catch (JsonProcessingException e){
            e.printStackTrace();
        }
        
        return ResponseBody;
    }
}
