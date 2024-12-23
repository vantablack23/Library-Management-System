package LMS.LibraryManagmentSystem.controllers;

import LMS.LibraryManagmentSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import LMS.LibraryManagmentSystem.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/")
    public String getHomePage(){
        return "home";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model, Authentication authentication){
        Optional<User> userDetails = userRepository.findByEmail(authentication.getName());
        User user = userDetails.get();
        model.addAttribute("userDetails", user);
        return "profile_page";
    }
}
