package LMS.LibraryManagmentSystem.repositories;

import LMS.LibraryManagmentSystem.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUser_Id(long id);
}
