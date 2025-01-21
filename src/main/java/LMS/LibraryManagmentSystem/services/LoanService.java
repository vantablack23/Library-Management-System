package LMS.LibraryManagmentSystem.services;

import LMS.LibraryManagmentSystem.Models.LoanModel;
import LMS.LibraryManagmentSystem.entity.Book;
import LMS.LibraryManagmentSystem.entity.Loan;
import LMS.LibraryManagmentSystem.entity.User;
import LMS.LibraryManagmentSystem.repositories.BookRepository;
import LMS.LibraryManagmentSystem.repositories.LoanRepository;
import LMS.LibraryManagmentSystem.repositories.StatusRepository;
import LMS.LibraryManagmentSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LoanService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private BookService bookService;

    public String saveLoan(LoanModel loanModel){
        Loan newLoan = new Loan();
        newLoan.setTimestamp(LocalDateTime.now());
        newLoan.setReturnDate(newLoan.getTimestamp().plusDays(30));

        User user = userRepository.findById(loanModel.getUser()).orElse(null);
        if(user == null){
            return "Given user doesn't exist";
        }
        newLoan.setUser(user);

        Book book = bookRepository.findById(loanModel.getBook()).orElse(null);
        if(book == null){
            return "Given book doesn't exist";
        } else if (!book.isAvailable()) {
            return "Given book isn't available";
        }
        bookService.updateBookAvaibility(book, false);
        newLoan.setBook(book);

        newLoan.setStatus(statusRepository.findById(Long.parseLong("2")).orElse(null));

        loanRepository.save(newLoan);

        return "";
    }

    public String returnLoan(long bookId){
        List<Loan> foundLoan = loanRepository.findLoansByBookIdAndStatus(bookId);

        if(foundLoan.isEmpty()){
            return "This book isn't borrowed";
        }

        Loan loan = foundLoan.getFirst();

        System.out.println(loan.getId());

        loan.setStatus(statusRepository.findById(Long.parseLong("1")).orElse(null));

        bookService.updateBookAvaibility(loan.getBook(), true);
        loanRepository.save(loan);

        return "Book returned successfully";
    }
}
