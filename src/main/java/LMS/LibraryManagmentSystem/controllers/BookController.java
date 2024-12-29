package LMS.LibraryManagmentSystem.controllers;

import LMS.LibraryManagmentSystem.Models.BookModel;
import LMS.LibraryManagmentSystem.entity.Book;
import LMS.LibraryManagmentSystem.entity.Location;
import LMS.LibraryManagmentSystem.repositories.BookRepository;
import LMS.LibraryManagmentSystem.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/addBookPage")
    public String getAddBookPage(Model model){
        model.addAttribute("newBook", new BookModel());

        List<Location> locations = locationRepository.findAll();
        model.addAttribute("locations", locations);
        return "add_book";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute BookModel bookModel){
        Book newBook = new Book();
        newBook.setAuthor(bookModel.getAuthor());
        newBook.setIsbn(bookModel.getIsbn());
        newBook.setDescription(bookModel.getDescription());
        newBook.setPublisher(bookModel.getPublisher());
        newBook.setTitle(bookModel.getTitle());
        newBook.setNumOfPages(bookModel.getNumOfPages());

        Location location = locationRepository.findById(bookModel.getLocation()).orElse(null);
        newBook.setLocation(location);

        bookRepository.save(newBook);

        return "redirect:/";
    }

//    @PostMapping("/book/add")
//    public Book addBook(@RequestBody BookModel bookModel){
//        Book newBook = new Book();
//        newBook.setAuthor(bookModel.getAuthor());
//        newBook.setIsbn(bookModel.getIsbn());
//        newBook.setDescription(bookModel.getDescription());
//        newBook.setPublisher(bookModel.getPublisher());
//        newBook.setTitle(bookModel.getTitle());
//        newBook.setNumOfPages(bookModel.getNumOfPages());
//
//        Location location = locationRepository.findById(bookModel.getLocation()).orElse(null);
//        newBook.setLocation(location);
//        return bookRepository.save(newBook);
//    }
}
