package LMS.LibraryManagmentSystem.controllers;

import LMS.LibraryManagmentSystem.Models.UserModel;
import LMS.LibraryManagmentSystem.entity.User;
import LMS.LibraryManagmentSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;


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
