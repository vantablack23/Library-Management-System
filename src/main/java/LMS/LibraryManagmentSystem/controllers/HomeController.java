package LMS.LibraryManagmentSystem.controllers;

import LMS.LibraryManagmentSystem.Models.BookModel;
import LMS.LibraryManagmentSystem.Models.LocationModel;
import LMS.LibraryManagmentSystem.entity.Loan;
import LMS.LibraryManagmentSystem.entity.Location;
import LMS.LibraryManagmentSystem.repositories.LoanRepository;
import LMS.LibraryManagmentSystem.repositories.LocationRepository;
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

    @Autowired
    private LocationRepository locationRepository;

    @GetMapping("/")
    public String getHomePage(){
        return "home";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model, Authentication authentication){
        Optional<User> userDetails = userRepository.findByEmail(authentication.getName());
        User user = userDetails.get();
        model.addAttribute("userDetails", user);

        if(user.getAuthority().getAuthority().equals("Librarian")){
            model.addAttribute("readers", userRepository.findUsersWithAuthority("Reader"));
            model.addAttribute("newLocation", new LocationModel());
            model.addAttribute("newBook", new BookModel());

            List<Location> locations = locationRepository.findAll();
            model.addAttribute("locations", locations);

            return "librarian_profile_page";
        }
        else {
            List<Loan> loans = loanRepository.findByUser_Id(user.getId());
            model.addAttribute("userLoans", loans);

            return "profile_page";
        }
    }
}
