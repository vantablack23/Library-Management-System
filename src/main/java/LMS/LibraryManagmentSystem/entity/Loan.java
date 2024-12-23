package LMS.LibraryManagmentSystem.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "loans")
@AllArgsConstructor
@NoArgsConstructor
public class Loan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private LocalDateTime timestamp;
    private LocalDateTime returnDate;

    @ManyToOne()
    private User user;

    @ManyToOne()
    private Book book;

    @ManyToOne(fetch = FetchType.EAGER) // Each user can have one authority
    @JoinColumn(name = "status_id") // Foreign key column
    private Status status;
}
