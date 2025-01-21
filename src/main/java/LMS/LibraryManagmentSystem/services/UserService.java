package LMS.LibraryManagmentSystem.services;

import LMS.LibraryManagmentSystem.entity.User;
import LMS.LibraryManagmentSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> searchUsers(String authority, String searchString){
        return userRepository.findUsersByAuthorityAndIdOrFullNameContaining(authority, searchString);
    }
}
