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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String addUser(@ModelAttribute UserModel userModel, RedirectAttributes redirectAttributes){
        User newUser = new User();

        Optional<User> checkEmail = userRepository.findByEmail(userModel.getEmail());
        if(checkEmail.isPresent()){
            redirectAttributes.addFlashAttribute("errorAddUser", "User with given email already exists");
            return "redirect:/addUserPage";
        }

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

        if(user.getAuthority().getAuthority().equals("Librarian")){
            return "librarian_profile_page";
        } else if (user.getAuthority().getAuthority().equals("Admin")) {
            return "admin_profile_page";
        }
        return "librarian_profile_page";
    }

    @GetMapping("/userManagementPage")
    public String userManagementPage(Model model){
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        return "user_management_page";
    }

    @GetMapping("/userManagementPage/search")
    public String searchUsers(@RequestParam String query, Model model) {
        List<User> users = userService.searchUsers("",query);
        model.addAttribute("users", users);

        return "user_management_page";
    }

    @PostMapping("/userManagementPage/updateUser/{userId}")
    public String updateUserAuthority(@PathVariable Long userId, @RequestParam String authority, RedirectAttributes redirectAttributes){

        try {
            userService.updateUserAuthority(userId, authority);
            redirectAttributes.addFlashAttribute("errorUpdateAuthority", "User's authority was successfully updated");
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("errorUpdateAuthority", "Failed to update user's authority");
        }
        return "redirect:/userManagementPage";
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

    @GetMapping("/userManagementPage/deleteUser/{userId}")
    public String deleteUser(@PathVariable Long userId, RedirectAttributes redirectAttributes){
        try{
            userRepository.deleteById(userId);
            redirectAttributes.addFlashAttribute("errorDeleteUser", "User was successfully deleted");
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("errorDeleteUser", "Failed to delete user");
        }
        return "redirect:/userManagementPage";
    }

}
