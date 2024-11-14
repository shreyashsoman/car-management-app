package shreyash.io.Car_Management_Application.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import shreyash.io.Car_Management_Application.models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}
