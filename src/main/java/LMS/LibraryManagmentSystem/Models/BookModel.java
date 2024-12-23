package LMS.LibraryManagmentSystem.Models;

import lombok.Data;

@Data
public class BookModel {
    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private int numOfPages;
    private long location;
}
