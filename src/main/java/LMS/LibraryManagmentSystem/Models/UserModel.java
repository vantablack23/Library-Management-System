package LMS.LibraryManagmentSystem.Models;

import LMS.LibraryManagmentSystem.entity.Authority;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UserModel {
    private String email;
    private String password;
    private Authority authority;
    private String name;
    private String lastName;
    private LocalDate joined;
}
