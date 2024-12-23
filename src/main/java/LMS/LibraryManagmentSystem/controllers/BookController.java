package LMS.LibraryManagmentSystem.controllers;

import LMS.LibraryManagmentSystem.Models.BookModel;
import LMS.LibraryManagmentSystem.entity.Book;
import LMS.LibraryManagmentSystem.entity.Location;
import LMS.LibraryManagmentSystem.repositories.BookRepository;
import LMS.LibraryManagmentSystem.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LocationRepository locationRepository;

    @PostMapping("/book/add")
    public Book addBook(@RequestBody BookModel bookModel){
        Book newBook = new Book();
        newBook.setAuthor(bookModel.getAuthor());
        newBook.setIsbn(bookModel.getIsbn());
        newBook.setDescription(bookModel.getDescription());
        newBook.setPublisher(bookModel.getPublisher());
        newBook.setTitle(bookModel.getTitle());
        newBook.setNumOfPages(bookModel.getNumOfPages());

        Location location = locationRepository.findById(bookModel.getLocation()).orElse(null);
        newBook.setLocation(location);
        return bookRepository.save(newBook);
    }
}
