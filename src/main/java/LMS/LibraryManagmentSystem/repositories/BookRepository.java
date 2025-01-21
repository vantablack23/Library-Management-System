package LMS.LibraryManagmentSystem.repositories;

import LMS.LibraryManagmentSystem.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

        @Query("SELECT b FROM Book b WHERE " +
                "LOWER(b.author) LIKE LOWER(CONCAT('%', :searchString, '%')) OR " +
                "LOWER(b.description) LIKE LOWER(CONCAT('%', :searchString, '%')) OR " +
                "LOWER(b.title) LIKE LOWER(CONCAT('%', :searchString, '%'))")
        List<Book> findByAuthorDescriptionOrTitleContaining(@Param("searchString") String searchString);

}
