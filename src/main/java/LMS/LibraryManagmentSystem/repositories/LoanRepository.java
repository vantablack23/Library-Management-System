package LMS.LibraryManagmentSystem.repositories;

import LMS.LibraryManagmentSystem.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser_Id(long id);

    @Query("SELECT l FROM Loan l WHERE l.book.id = :bookId AND l.status.status IN ('BORROWED', 'HELD')")
    List<Loan> findLoansByBookIdAndStatus(@Param("bookId") long bookId);
}
