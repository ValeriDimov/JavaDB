package softuni.exam.instagraphlite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import softuni.exam.instagraphlite.models.entities.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    @Transactional
    @Query("SELECT u FROM User u" +
            " JOIN u.posts p" +
            " JOIN p.picture pi" +
            " ORDER BY p.size DESC, u.id ASC, pi.size ASC")
    List<User> findAllCustomRequest();
}
