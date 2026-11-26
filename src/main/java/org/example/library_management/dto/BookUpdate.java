package org.example.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookUpdate {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotNull
    private Integer publisher;

    public BookUpdate(String title, String author, Integer publisher) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getPublisher() {
        return publisher;
    }

    public void setPublisher(Integer publisher) {
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return "BookUpdate{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publisher=" + publisher +
                '}';
    }
}
