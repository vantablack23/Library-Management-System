package LMS.LibraryManagmentSystem.Models;

import LMS.LibraryManagmentSystem.entity.Status;
import lombok.Data;

@Data
public class LoanModel {
   // private LocalDateTime timestamp;
    private long user;
    private long book;
    private Status status;
}
