package LMS.LibraryManagmentSystem.controllers;

import LMS.LibraryManagmentSystem.Models.LoanModel;
import LMS.LibraryManagmentSystem.entity.Loan;
import LMS.LibraryManagmentSystem.repositories.LoanRepository;
import LMS.LibraryManagmentSystem.repositories.UserRepository;
import LMS.LibraryManagmentSystem.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller
public class LoanController {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanService loanService;

    @Autowired
    private LoanRepository loanRepository;

    @PostMapping("/addLoan")
    public String addLoan(@RequestBody LoanModel loanModel){
        loanService.saveLoan(loanModel);
        return "home";
    }

    @GetMapping("/loans/user/{userId}")
    public ResponseEntity<List<Loan>> listLoans(@PathVariable long userId){
        return new ResponseEntity<>(loanRepository.findByUser_Id(userId), HttpStatus.OK);
    }

}
