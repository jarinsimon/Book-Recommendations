package com.example.Book.service;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class BookSelect {
    public void printBooks(JsonNode bookJson){
        Integer counter = 0;
        //Select title, subtitle, and author for the first 10 results
        if (bookJson.isArray()){
            for (JsonNode item : bookJson){
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
    }

    public JsonNode chooseBook(JsonNode bookJson){
        Scanner scan = new Scanner(System.in);
        System.out.println("Enter the book you would like to select: ");
        int bookNumber = scan.nextInt();
        if (bookNumber <= bookJson.size()){
            System.out.println("Book #" + bookNumber + " has been chosen!\nBook Information:");
            JsonNode chosenBook = bookJson.get(bookNumber-1);
            JsonNode bookVolumeInfo = chosenBook.path("volumeInfo");
            String title = bookVolumeInfo.path("title").asText("No Title");
            String subtitle = bookVolumeInfo.path("subtitle").asText("No Subtitle");
            String author = bookVolumeInfo.path("authors").get(0).asText("Author");
            String publishDate = bookVolumeInfo.path("publishedDate").asText("No Published Date");
            JsonNode isbn = bookVolumeInfo.path("industryIdentifiers");

            System.out.println("Title: " + title + ", Subtitle: " + subtitle + ", Author: " + author + ", Publish Date: " + publishDate.substring(0,4));
            for (JsonNode isbnNumber: isbn){
                String type = isbnNumber.path("type").asText("No ISBN");
                String number = isbnNumber.path("identifier").asText("No ISBN Number");
                System.out.println(type + ": " + number);
            }
            scan.close();
            return chosenBook;
        }
        scan.close();
        return bookJson;
    }
}
