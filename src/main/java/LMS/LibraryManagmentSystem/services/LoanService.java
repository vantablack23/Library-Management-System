package LMS.LibraryManagmentSystem.services;

import LMS.LibraryManagmentSystem.Models.LoanModel;
import LMS.LibraryManagmentSystem.entity.Loan;
import LMS.LibraryManagmentSystem.repositories.BookRepository;
import LMS.LibraryManagmentSystem.repositories.LoanRepository;
import LMS.LibraryManagmentSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LoanService
{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoanRepository loanRepository;

    public void saveLoan(LoanModel loanModel){
        Loan newLoan = new Loan();
        newLoan.setTimestamp(LocalDateTime.now());
        newLoan.setReturnDate(newLoan.getTimestamp().plusDays(30));
        newLoan.setUser(userRepository.findById(loanModel.getUser()).orElse(null));
        newLoan.setBook(bookRepository.findById(loanModel.getBook()).orElse(null));
        newLoan.setStatus(loanModel.getStatus());

        loanRepository.save(newLoan);
    }


}
