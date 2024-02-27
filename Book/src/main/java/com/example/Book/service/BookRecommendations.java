package com.example.Book.service;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Service;

@Service
public class BookRecommendations {
    public String getAuthor(JsonNode chosenBook){
        String author = chosenBook.path("volumeInfo").path("authors").get(0).asText("Author");
        return author;
    }

    public void authorCleanup(JsonNode authorRaw){
        for (int i = 0; i < Math.min(3, authorRaw.size()); i++){
            JsonNode book = authorRaw.get(i);
            JsonNode bookVolumeInfo = book.path("volumeInfo");
            System.out.println(bookVolumeInfo.path("title") + ", " + bookVolumeInfo.path("publishedDate").asText().substring(0,4));
        }
    }
}
