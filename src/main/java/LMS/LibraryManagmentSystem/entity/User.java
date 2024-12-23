package LMS.LibraryManagmentSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String email;
    private String password;

    private String name;
    private String lastName;

    private LocalDate joined;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<Loan> loans;

    @ManyToOne(fetch = FetchType.EAGER) // Each user can have one authority
    @JoinColumn(name = "authority_id") // Foreign key column
    private Authority authority;

}
