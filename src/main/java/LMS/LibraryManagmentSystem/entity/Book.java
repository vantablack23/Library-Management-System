package LMS.LibraryManagmentSystem.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@Table(name = "books")
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String isbn;
    private String title;
    private String author;
    private String publisher;
    private String description;
    private int numOfPages;

    @OneToMany(mappedBy = "book")
    @JsonIgnore
    private List<Loan> loans;

    @ManyToOne(fetch = FetchType.EAGER) // Each user can have one authority
    @JoinColumn(name = "location_id")
    private Location location;
}
