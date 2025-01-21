package LMS.LibraryManagmentSystem.services;

import LMS.LibraryManagmentSystem.entity.Book;
import LMS.LibraryManagmentSystem.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    public List<Book> searchBooks(String searchString){
        return bookRepository.findByAuthorDescriptionOrTitleContaining(searchString);
    }

    public void updateBookAvaibility(Book book, boolean available){
        book.setAvailable(available);
        bookRepository.save(book);
    }
}
