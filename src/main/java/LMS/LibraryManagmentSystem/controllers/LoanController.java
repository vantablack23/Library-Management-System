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
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String addLoan(@ModelAttribute LoanModel newLoan, Model model){
        String error = loanService.saveLoan(newLoan);
        if(!error.isEmpty()){
            model.addAttribute("errorMessage", error);
            model.addAttribute("newLoan", newLoan);
            return "add_loan";
        }
        return "redirect:/librarian/user/profile/"+newLoan.getUser();
    }

    @GetMapping("/returnLoan")
    public String returnLoan(@RequestParam String bookId, RedirectAttributes redirectAttributes){

        String error = loanService.returnLoan(Long.parseLong(bookId));

        redirectAttributes.addFlashAttribute("error", error);


        return "redirect:/profile";
    }

    @GetMapping("/loans/user/{userId}")
    public ResponseEntity<List<Loan>> listLoans(@PathVariable long userId){
        return new ResponseEntity<>(loanRepository.findByUser_Id(userId), HttpStatus.OK);
    }


    @GetMapping("/librarian/user/profile/{id}/add_loan_page")
    public String addLoanPage(Model model, @PathVariable long id){
        LoanModel newLoan = new LoanModel();
        newLoan.setUser(id);
        model.addAttribute("newLoan", newLoan);
        return "add_loan";
    }

}
