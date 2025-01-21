package LMS.LibraryManagmentSystem.repositories;

import LMS.LibraryManagmentSystem.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusRepository extends JpaRepository<Status, Long> {

}
