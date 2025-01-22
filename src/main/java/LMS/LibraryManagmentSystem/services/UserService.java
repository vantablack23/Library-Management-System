package LMS.LibraryManagmentSystem.services;

import LMS.LibraryManagmentSystem.entity.Authority;
import LMS.LibraryManagmentSystem.entity.User;
import LMS.LibraryManagmentSystem.repositories.AuthorityRepository;
import LMS.LibraryManagmentSystem.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthorityRepository authorityRepository;

    public List<User> searchUsers(String authority, String searchString){
        if(authority.isEmpty()){
            return userRepository.findUsersByIdOrFullNameContaining(searchString);
        }
        return userRepository.findUsersByAuthorityAndIdOrFullNameContaining(authority, searchString);
    }

    public void updateUserAuthority(Long userId, String authority){
        User user = userRepository.findById(userId).orElse(null);
        Authority authority1 = authorityRepository.findById(Long.parseLong(authority)).orElse(null);
        user.setAuthority(authority1);
        userRepository.save(user);
    }
}
