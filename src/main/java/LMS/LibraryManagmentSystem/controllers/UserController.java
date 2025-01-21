package LMS.LibraryManagmentSystem.controllers;

import LMS.LibraryManagmentSystem.Models.UserModel;
import LMS.LibraryManagmentSystem.entity.Loan;
import LMS.LibraryManagmentSystem.entity.User;
import LMS.LibraryManagmentSystem.repositories.AuthorityRepository;
import LMS.LibraryManagmentSystem.repositories.LoanRepository;
import LMS.LibraryManagmentSystem.repositories.UserRepository;
import LMS.LibraryManagmentSystem.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private LoanRepository loanRepository;

    @PostMapping("/register")
    public User register(@RequestBody UserModel userModel){
        User newUser = new User();
        newUser.setEmail(userModel.getEmail());
        newUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
        newUser.setAuthority(userModel.getAuthority());
        newUser.setName(userModel.getName());
        newUser.setLastName(userModel.getLastName());
        newUser.setJoined(LocalDate.now());
        return userRepository.save(newUser);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id){
        Optional<User> foundUser = userRepository.findById(id);
        return foundUser.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/username")
    public ResponseEntity<Optional<User>> currentUserData(Authentication authentication) {
        Optional<User> userDetails = userRepository.findByEmail(authentication.getName());
        return new ResponseEntity<>(userDetails, HttpStatus.OK);
    }

    @GetMapping("/addUserPage")
    public String getAddUserPage(Model model){
        model.addAttribute("newUser", new UserModel());
        return "register";
    }

    @PostMapping("/addUser")
    public String addUser(@ModelAttribute UserModel userModel){
        User newUser = new User();
        newUser.setEmail(userModel.getEmail());
        newUser.setPassword(passwordEncoder.encode(userModel.getPassword()));
        newUser.setAuthority(authorityRepository.findById(Long.parseLong("1")).orElse(null));
        newUser.setName(userModel.getName());
        newUser.setLastName(userModel.getLastName());
        newUser.setJoined(LocalDate.now());
        userRepository.save(newUser);

        return "redirect:/login";
    }

    @GetMapping("/reader/search")
    public String searchReaders(@RequestParam String query, Model model, Authentication authentication) {
        Optional<User> userDetails = userRepository.findByEmail(authentication.getName());
        User user = userDetails.get();
        model.addAttribute("userDetails", user);

        List<User> readers = userService.searchUsers("Reader", query);
        model.addAttribute("readers", readers);

        return "librarian_profile_page";
    }

    @GetMapping("/librarian/user/profile/{id}")
    public String userProfileForLibrarian(@PathVariable Long id, Model model){
        Optional<User> user = userRepository.findById(id);
        User reader = user.get();
        model.addAttribute("reader", reader);

        List<Loan> loans = loanRepository.findByUser_Id(reader.getId());
        model.addAttribute("userLoans", loans);

        return "librarian_user_profile_page";
    }

//    @GetMapping("/loanedBooks")
//    public ResponseEntity<Optional<Book>> getLoanedBooks(Authentication authentication){
//        User user = userRepository.findByEmail(authentication.getName()).get();
//
//    }

//    @GetMapping("/getUser")
//    public Object getCurrentUser(Authentication authentication) {
//        return new ResponseEntity<>(userService.getLoggedUser(authentication), HttpStatus.OK);
//    }

}
