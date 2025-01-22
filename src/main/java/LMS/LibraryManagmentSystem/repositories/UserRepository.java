package LMS.LibraryManagmentSystem.repositories;

import LMS.LibraryManagmentSystem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.authority.authority = :authority")
    List<User> findUsersWithAuthority(@Param("authority") String authority);

    @Query("SELECT u FROM User u WHERE " +
            "u.authority.authority = :authority AND (" +
            "CAST(u.id AS string) LIKE CONCAT('%', :searchString, '%') OR " +
            "LOWER(CONCAT(u.name, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :searchString, '%')))")
    List<User> findUsersByAuthorityAndIdOrFullNameContaining(
            @Param("authority") String authority,
            @Param("searchString") String searchString);

    @Query("SELECT u FROM User u WHERE " +
            "(CAST(u.id AS string) LIKE CONCAT('%', :searchString, '%') OR " +
            "LOWER(CONCAT(u.name, ' ', u.lastName)) LIKE LOWER(CONCAT('%', :searchString, '%')))")
    List<User> findUsersByIdOrFullNameContaining(
            @Param("searchString") String searchString);

}
