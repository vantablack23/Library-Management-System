package LMS.LibraryManagmentSystem.controllers;

import LMS.LibraryManagmentSystem.entity.Loan;
import LMS.LibraryManagmentSystem.repositories.LoanRepository;
import LMS.LibraryManagmentSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import LMS.LibraryManagmentSystem.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoanRepository loanRepository;

    @GetMapping("/")
    public String getHomePage(){
        return "home";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model, Authentication authentication){
        Optional<User> userDetails = userRepository.findByEmail(authentication.getName());
        User user = userDetails.get();
        model.addAttribute("userDetails", user);

        List<Loan> loans = loanRepository.findByUser_Id(user.getId());
        model.addAttribute("userLoans", loans);

        return "profile_page";
    }
}
