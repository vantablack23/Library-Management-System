package LMS.LibraryManagmentSystem.controllers;

import LMS.LibraryManagmentSystem.Models.BookModel;
import LMS.LibraryManagmentSystem.entity.Book;
import LMS.LibraryManagmentSystem.entity.Location;
import LMS.LibraryManagmentSystem.repositories.BookRepository;
import LMS.LibraryManagmentSystem.repositories.LocationRepository;
import LMS.LibraryManagmentSystem.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private BookService bookService;

    @GetMapping("/booksManagement")
    public String getAddBookPage(Model model){
        model.addAttribute("newBook", new BookModel());

        List<Location> locations = locationRepository.findAll();
        model.addAttribute("locations", locations);

        return "book_management";
    }

    @GetMapping("/search/booksManagement")
    public String searchBooksManagement(@RequestParam String query, RedirectAttributes redirectAttributes) {
        List<Book> foundBooks = bookService.searchBooks(query);

        redirectAttributes.addFlashAttribute("foundBooks", foundBooks);

        return "redirect:/booksManagement";
    }

    @PostMapping("/addBook")
    public String addBook(@ModelAttribute BookModel bookModel, RedirectAttributes redirectAttributes){
        Book newBook = new Book();
        newBook.setAuthor(bookModel.getAuthor());
        newBook.setIsbn(bookModel.getIsbn());
        newBook.setDescription(bookModel.getDescription());
        newBook.setPublisher(bookModel.getPublisher());
        newBook.setTitle(bookModel.getTitle());
        newBook.setNumOfPages(bookModel.getNumOfPages());
        newBook.setAvailable(true);

        Location location = locationRepository.findById(bookModel.getLocation()).orElse(null);
        newBook.setLocation(location);

        bookRepository.save(newBook);

        redirectAttributes.addFlashAttribute("errorBook", "Successfully added new book");

        return "redirect:/booksManagement";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam String query, Model model) {
        List<Book> foundBooks = bookService.searchBooks(query);

        model.addAttribute("foundBooks", foundBooks);

        return "home";
    }

    @GetMapping("/deleteBook/{bookId}")
    public String deleteBook(@PathVariable Long bookId, RedirectAttributes redirectAttributes){
        try{
            bookRepository.deleteById(bookId);
            redirectAttributes.addFlashAttribute("errorDeleteBook", "Book was successfully deleted");
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("errorDeleteBook", "Can not delete book when borrowed");
        }
        return "redirect:/booksManagement";
    }
}
