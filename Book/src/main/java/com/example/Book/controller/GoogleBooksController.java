package com.example.Book.controller;

import org.springframework.beans.factory.annotation.Autowired;  //Automatic dependency injection
import org.springframework.web.bind.annotation.GetMapping;      //Map HTTP GET requests onto specific methods
import org.springframework.web.bind.annotation.RequestMapping;  //Map web requests onto specific handler classes/methods
import org.springframework.web.bind.annotation.RequestParam;    //Bind request parameters to method parameters
import org.springframework.web.bind.annotation.RestController;  //Indicate a particular class serves as a controller

import com.example.Book.service.BookRecommendations;
import com.example.Book.service.GoogleBooksAPI;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
@RequestMapping("/api/books")
public class GoogleBooksController {
    private final GoogleBooksAPI googleBooksAPI; 
    private final BookRecommendations bookRecommendations;

    @Autowired GoogleBooksController(GoogleBooksAPI googleBooksAPI, BookRecommendations bookRecommendations){
        this.googleBooksAPI = googleBooksAPI;
        this.bookRecommendations = bookRecommendations;
    }

    @GetMapping("/search")
    public JsonNode searchBooks(@RequestParam("query") String query){
        JsonNode chosenBook = googleBooksAPI.bookResults(query);
        String bookAuthor = bookRecommendations.getAuthor(chosenBook);
        JsonNode authorRecommendations = googleBooksAPI.authorRecs(bookAuthor);
        bookRecommendations.authorCleanup(authorRecommendations);
        return authorRecommendations;
    }
}
