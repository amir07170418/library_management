package org.example.library_management.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class BookUpdate {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotNull
    private Integer publishYear;

    public BookUpdate(String title, String author, Integer publishYear) {
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
    }

    public BookUpdate() {
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

    public Integer getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(Integer publishYear) {
        this.publishYear = publishYear;
    }

    @Override
    public String toString() {
        return "BookUpdate{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishYear=" + publishYear +
                '}';
    }
}
